package com.gabid.ezaciancraft.api.essentia;

import com.gabid.ezaciancraft.CoreMod;
import com.gabid.ezaciancraft.api.common.blocks.tileentity.EzacianCustomJarFillableTE;
import com.gabid.ezaciancraft.common.blocks.tileentity.TileEntityWirelessEssentiaInterfaceInput;
import com.gabid.ezaciancraft.common.blocks.tileentity.TileEntityWirelessEssentiaInterfaceOutput;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import thaumcraft.api.WorldCoordinates;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.IAspectContainer;
import thaumcraft.api.aspects.IEssentiaTransport;
import thaumcraft.common.lib.network.PacketHandler;
import thaumcraft.common.lib.network.fx.PacketFXEssentiaSource;
import thaumcraft.common.tiles.*;

import java.util.*;

import static com.gabid.ezaciancraft.CoreMod.LOG;
import static com.gabid.ezaciancraft.config.EzacianCraftConfiguration.*;

///@api This is a Handler API what is based by the base Thaumcraft handler, but using the score system for searching, draining and filling valid interfaces in the mod.
///@api used mostly for my version of wireless essentia, a system which exists in TC 6.
public class EzacianEssentiaWirelessHandler {
    public static final long ESSENTIA_DELAY_MILLIS = 150;
    public static int maxEssentiaThreads = wirelessInterfacesMaxWorkThreads;
    private static final HashMap<ConnectionKey, Long> connectionDelay = new HashMap<>();

    public static List<ScoredSource> getNearestEssentiaHandler(World world, int originX, int originY, int originZ, ForgeDirection facing, int searchRadius) {
        if (facing == ForgeDirection.UNKNOWN) return Collections.emptyList();

        ArrayList<ScoredSource> sources = new ArrayList<>();

        for(int i = 1; i <= searchRadius; i++) {
            for(int width = -searchRadius; width <= searchRadius; width++) {
                for(int height = -searchRadius; height <= searchRadius; height++) {
                    int forwardX = originX + facing.offsetX * i;
                    int forwardY = originY + facing.offsetY * i;
                    int forwardZ = originZ + facing.offsetZ * i;

                    int searcherX = forwardX;
                    int searcherY = forwardY + height;
                    int searcherZ = forwardZ;

                    if (facing.offsetX != 0) {
                        searcherZ = forwardZ + width;
                    } else if (facing.offsetZ != 0) {
                        searcherX = forwardX + width;
                    } else if (facing.offsetY != 0) {
                        searcherX = forwardX + width;
                        searcherZ = forwardZ + height;
                        searcherY = forwardY;
                    }

                    TileEntity teEssentia = world.getTileEntity(searcherX, searcherY, searcherZ);

                    if(teEssentia == null || teEssentia instanceof TileTube || teEssentia instanceof TileTubeBuffer)
                        continue;

                    if(teEssentia instanceof IEssentiaTransport) {
                        WorldCoordinates searcherCoords = new WorldCoordinates(searcherX, searcherY, searcherZ, world.provider.dimensionId);
                        IEssentiaTransport iEssentia = (IEssentiaTransport) teEssentia;

                        double distScore = i * i + width * width + height * height;
                        double score;
                        double fillPenalty = getFillPenalty(teEssentia, iEssentia, facing);
                        double suctionBonus = getSuctionBonus(iEssentia, facing);
                        score = distScore + fillPenalty - suctionBonus;

                        if(iEssentia.getEssentiaAmount(facing.getOpposite()) < getSourceCapacity(teEssentia)) {
                            sources.add(new ScoredSource(searcherCoords, score));
                        }
                    }
                }
            }
        }
        sources.sort(Comparator.comparingDouble(a -> a.score));

        int limit = Math.min(maxEssentiaThreads, sources.size());

        List<ScoredSource> result = new ArrayList<>();

        for (int i = 0; i < limit; i++) {
            result.add(sources.get(i));
        }

        if(!result.isEmpty())
            LOG.info("Selected {} sources out of {}", result.size(), sources.size());

        return result;
    }

    public static int getSourceCapacity(TileEntity sourceTE) {
        int capacity = 0;
        if(sourceTE instanceof TileJarFillable) {
            capacity = ((TileJarFillable) sourceTE).maxAmount;
        } else if (sourceTE instanceof EzacianCustomJarFillableTE) {
            capacity = ((EzacianCustomJarFillableTE) sourceTE).getMaxAmount();
        }

        return capacity;
    }

    private static double getFillPenalty(TileEntity teEssentia, IEssentiaTransport iEssentia, ForgeDirection facing) {
        int stored = iEssentia.getEssentiaAmount(facing.getOpposite());

        int capacity = getSourceCapacity(teEssentia);

        if(capacity <= 0) {
            LOG.warn("¡Detected a minus or zero capacity in a source!");
            return 0;
        }
        double fillRatio = (double) stored / capacity;

        return fillRatio * wirelessInterfacesFillPenaltyMultiplier;
    }

    private static double getSuctionBonus(IEssentiaTransport iEssentia, ForgeDirection facing) {
        double suction = iEssentia.getSuctionAmount(facing.getOpposite());
        double minSuction = iEssentia.getMinimumSuction();

        if(suction < minSuction) {
            LOG.warn("¡Detected a minus suction in a source!");
            return 0;
        } else {
            double suctionNorm = suction / (suction + 10.0);

            return suctionNorm * wirelessInterfacesSuctionBonusMultiplier;
        }
    }

    public static void fillEssentiaWireless(TileEntity originTE, World world, List<ScoredSource> sources, ForgeDirection facing, int toInsert) {
        double totalWeight = 0;

        for (ScoredSource s : sources) {
            totalWeight += 1.0 / (1.0 + s.score);
        }

        TileEntityWirelessEssentiaInterfaceOutput originWEI = (TileEntityWirelessEssentiaInterfaceOutput) originTE;

        int remaining = Math.min(toInsert, originWEI.getStoredAmount());

        for (ScoredSource s : sources) {
            if (remaining <= 0) break;

            TileEntity teSources = world.getTileEntity(s.pos.x, s.pos.y, s.pos.z);

            IEssentiaTransport sourcesET = null;
            if (teSources instanceof IEssentiaTransport) sourcesET = (IEssentiaTransport) teSources;

            IAspectContainer sourcesAC = null;
            if (teSources instanceof IAspectContainer) sourcesAC = (IAspectContainer) teSources;

            if (sourcesET != null && sourcesAC != null) {
                int sourceCurrentStored = sourcesET.getEssentiaAmount(facing.getOpposite());
                int sourceCapacity = getSourceCapacity(teSources);

                if (sourceCapacity <= 0) continue;

                int sourceSpace = Math.max(0, sourceCapacity - sourceCurrentStored);
                if (sourceSpace == 0) continue;

                double weight = 1.0 / (1.0 + s.score);

                int amount = (int) ((weight / totalWeight) * toInsert);

                amount = Math.max(1, amount);
                amount = Math.min(amount, sourceSpace);
                amount = Math.min(amount, originWEI.getStoredAmount());

                if (amount <= 0) continue;

                Aspect targetAspect = sourcesET.getEssentiaType(facing.getOpposite());

                Aspect originCurrentAspect = originWEI.getStoredAspect();

                if (targetAspect != null && targetAspect != originWEI.getStoredAspect()) {
                    continue;
                }

                sourcesAC.addToContainer(originWEI.getStoredAspect(), amount);
                if(originWEI.takeFromContainer(originWEI.getStoredAspect(), amount)) {
                    remaining -= amount;
                }

                if (originCurrentAspect == null) return;

                ConnectionKey key = new ConnectionKey(originTE, teSources);
                long now = System.currentTimeMillis();

                connectionDelay.entrySet().removeIf(entry -> now > entry.getValue() + ESSENTIA_DELAY_MILLIS);

                if (!connectionDelay.containsKey(key) || now > connectionDelay.get(key)) {
                    long delay = (long)(ESSENTIA_DELAY_MILLIS / (1.0 + s.score * 0.1));
                    connectionDelay.put(key, now + delay);

                    SimpleNetworkWrapper LOCAL_NETWORK = PacketHandler.INSTANCE;
                    PacketFXEssentiaSource packet = createEssentiaFX(originTE, s.pos, originCurrentAspect);

                    LOCAL_NETWORK.sendToAllAround(packet,
                            new NetworkRegistry.TargetPoint(
                                    originTE.getWorldObj().provider.dimensionId,
                                    originTE.xCoord,
                                    originTE.yCoord,
                                    originTE.zCoord,
                                    32f
                            )
                    );
                }
            }
        }
    }

    public static void drainEssentiaWireless(TileEntity originTE, World world, List<ScoredSource> sources, ForgeDirection facing, int toExtract) {
        double totalWeight = 0;

        for (ScoredSource s : sources) {
            totalWeight += 1.0 / (1.0 + s.score);
        }

        TileEntityWirelessEssentiaInterfaceInput originWEI = (TileEntityWirelessEssentiaInterfaceInput) originTE;

        int currentStoredWEI = Math.min(toExtract, originWEI.getMaxCapacityEssentiaVis() - originWEI.getCurrentStoredVis());

        for (ScoredSource s : sources) {
            if (currentStoredWEI > originWEI.getMaxCapacityEssentiaVis()) break;

            TileEntity teSources = world.getTileEntity(s.pos.x, s.pos.y, s.pos.z);

            IEssentiaTransport sourcesET = null;
            if (teSources instanceof IEssentiaTransport) sourcesET = (IEssentiaTransport) teSources;

            IAspectContainer sourcesAC = null;
            if (teSources instanceof IAspectContainer) sourcesAC = (IAspectContainer) teSources;

            if(sourcesET != null && sourcesAC != null) {
                int available = sourcesET.getEssentiaAmount(facing.getOpposite());
                if (available <= 0) continue;

                Aspect sourceAspect = sourcesET.getEssentiaType(facing.getOpposite());
                if (sourceAspect == null) continue;

                double weight = 1.0 / (1.0 + s.score);

                int amountToExtract = (int) ((weight / totalWeight) * toExtract);

                amountToExtract = Math.max(1, amountToExtract);
                amountToExtract = Math.min(amountToExtract, available);
                amountToExtract = Math.min(amountToExtract, currentStoredWEI);

                if (amountToExtract <= 0) continue;

                if (sourcesAC.takeFromContainer(sourceAspect, amountToExtract)) {
                    originWEI.addToContainer(sourceAspect, amountToExtract);
                    currentStoredWEI -= amountToExtract;
                }

                ConnectionKey key = new ConnectionKey(originTE, teSources);
                long now = System.currentTimeMillis();

                connectionDelay.entrySet().removeIf(entry -> now > entry.getValue() + ESSENTIA_DELAY_MILLIS);

                if (!connectionDelay.containsKey(key) || now > connectionDelay.get(key)) {
                    long delay = (long)(ESSENTIA_DELAY_MILLIS / (1.0 + s.score * 0.1));
                    connectionDelay.put(key, now + delay);

                    SimpleNetworkWrapper LOCAL_NETWORK = PacketHandler.INSTANCE;
                    PacketFXEssentiaSource packet = createEssentiaFX(world.getTileEntity(s.pos.x, s.pos.y, s.pos.z), new WorldCoordinates(originTE.xCoord, originTE.yCoord, originTE.zCoord, world.provider.dimensionId), sourceAspect);

                    LOCAL_NETWORK.sendToAllAround(packet,
                            new NetworkRegistry.TargetPoint(
                                    originTE.getWorldObj().provider.dimensionId,
                                    s.pos.x,
                                    s.pos.y,
                                    s.pos.z,
                                    32f
                            )
                    );
                }
            }
        }
    }

    private static PacketFXEssentiaSource createEssentiaFX(TileEntity source, WorldCoordinates destiny, Aspect originCurrentAspect) {
        int color = (originCurrentAspect != null) ? originCurrentAspect.getColor() : 0xFFFFFF;

        byte dx = (byte) (destiny.x - source.xCoord);
        byte dy = (byte) (destiny.y - source.yCoord);
        byte dz = (byte) (destiny.z - source.zCoord);

        return new PacketFXEssentiaSource(
                destiny.x,
                destiny.y,
                destiny.z,
                dx,
                dy,
                dz,
                color
        );
    }

    public static class ScoredSource {
        WorldCoordinates pos;
        double score;

        ScoredSource(WorldCoordinates pos, double score) {
            this.pos = pos;
            this.score = score;
        }
    }

    public static class ConnectionKey {
        int sx, sy, sz;
        int dx, dy, dz;

        public ConnectionKey(TileEntity source, TileEntity dest) {
            this.sx = source.xCoord;
            this.sy = source.yCoord;
            this.sz = source.zCoord;
            this.dx = dest.xCoord;
            this.dy = dest.yCoord;
            this.dz = dest.zCoord;
        }

        @Override
        public boolean equals(Object o) {
            if (!(o instanceof ConnectionKey)) return false;
            ConnectionKey k = (ConnectionKey) o;
            return sx == k.sx && sy == k.sy && sz == k.sz &&
                    dx == k.dx && dy == k.dy && dz == k.dz;
        }

        @Override
        public int hashCode() {
            return (sx * 31 + sy * 17 + sz) ^ (dx * 13 + dy * 7 + dz);
        }
    }
}