package com.gabid.ezaciancraft.api.essentia;

import com.gabid.ezaciancraft.api.common.blocks.tileentity.EzacianCustomJarFillableTE;
import com.gabid.ezaciancraft.common.blocks.tileentity.TileEntityWirelessEssentiaInterfaceInput;
import com.gabid.ezaciancraft.common.blocks.tileentity.TileEntityWirelessEssentiaInterfaceOutput;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.util.ForgeDirection;
import thaumcraft.api.WorldCoordinates;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.IAspectContainer;
import thaumcraft.api.aspects.IAspectSource;
import thaumcraft.api.aspects.IEssentiaTransport;
import thaumcraft.common.lib.network.PacketHandler;
import thaumcraft.common.lib.network.fx.PacketFXEssentiaSource;
import thaumcraft.common.tiles.TileJarFillable;
import thaumcraft.common.tiles.TileMirrorEssentia;
import thaumcraft.common.tiles.TileTube;
import thaumcraft.common.tiles.TileTubeBuffer;

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

                    double distScore = i * i + width * width + height * height;

                    if(teEssentia instanceof IEssentiaTransport) {
                        WorldCoordinates searcherCoords = new WorldCoordinates(searcherX, searcherY, searcherZ, world.provider.dimensionId);
                        IEssentiaTransport iEssentia = (IEssentiaTransport) teEssentia;

                        double score;
                        double fillPenalty = getFillPenalty(teEssentia, iEssentia, facing);
                        double suctionBonus = getSuctionBonus(iEssentia, facing);
                        score = distScore + fillPenalty - suctionBonus;

                        if(iEssentia.getEssentiaAmount(facing.getOpposite()) < getSourceCapacity(teEssentia)) {
                            sources.add(new ScoredSource(searcherCoords, score));
                        }
                    }

                    if(teEssentia instanceof TileMirrorEssentia) {
                        WorldCoordinates searcherCoords = new WorldCoordinates(searcherX, searcherY, searcherZ, world.provider.dimensionId);

                        double score;
                        score = distScore ;

                        sources.add(new ScoredSource(searcherCoords, score));
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

        TileEntityWirelessEssentiaInterfaceOutput originWEI = (TileEntityWirelessEssentiaInterfaceOutput) originTE;

        int remaining = Math.min(toInsert, originWEI.getStoredAmount());

        for (ScoredSource s : sources) {
            totalWeight += 1.0 / (1.0 + s.score);
        }

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

                    if (packet == null) continue;
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

            if(sourcesAC instanceof IAspectSource && teSources instanceof TileMirrorEssentia) {
                fillEssentiaWireless(originWEI, (TileMirrorEssentia) teSources, toInsert);
            }
        }
    }

    public static void fillEssentiaWireless(TileEntityWirelessEssentiaInterfaceOutput originWEI, TileMirrorEssentia mirrorSource, int toInsert) {
        if(mirrorSource != null) {
            World localWorldDim = DimensionManager.getWorld(mirrorSource.linkDim);
            if(localWorldDim != null) {
                TileEntity teLinkedMirror = localWorldDim.getTileEntity(mirrorSource.linkX, mirrorSource.linkY, mirrorSource.linkZ);
                if(teLinkedMirror instanceof TileMirrorEssentia) {
                    TileMirrorEssentia linkedMirror = (TileMirrorEssentia) teLinkedMirror;
                    ForgeDirection linkedMirrorFacing = ForgeDirection.getOrientation(linkedMirror.blockMetadata % 6);
                    int remaining = Math.min(toInsert, originWEI.getStoredAmount());

                    if(linkedMirrorFacing != ForgeDirection.UNKNOWN && originWEI.hasWorldObj() && linkedMirror.hasWorldObj()) {
                        LOG.info("found mirror at: [{}, {}, {}] dim: {}, facing: {}", mirrorSource.linkX, mirrorSource.linkY, mirrorSource.linkZ, mirrorSource.linkDim, linkedMirrorFacing);

                        List<ScoredSource> linkedMirrorSources = getNearestEssentiaHandler(localWorldDim, linkedMirror.xCoord, linkedMirror.yCoord, linkedMirror.zCoord, linkedMirrorFacing, wirelessOutputInterfaceWorkRadius);
                        double totalWeight = 0;

                        for (ScoredSource s : linkedMirrorSources) {
                            totalWeight += 1.0 / (1.0 + s.score);
                        }

                        for(ScoredSource s : linkedMirrorSources) {
                            if (remaining <= 0) break;

                            TileEntity teSources = localWorldDim.getTileEntity(s.pos.x, s.pos.y, s.pos.z);

                            IEssentiaTransport sourcesET = null;
                            if (teSources instanceof IEssentiaTransport) sourcesET = (IEssentiaTransport) teSources;

                            IAspectContainer sourcesAC = null;
                            if (teSources instanceof IAspectContainer) sourcesAC = (IAspectContainer) teSources;

                            if (sourcesET != null && sourcesAC != null) {
                                int sourceCurrentStored = sourcesET.getEssentiaAmount(linkedMirrorFacing.getOpposite());
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

                                Aspect targetAspect = sourcesET.getEssentiaType(linkedMirrorFacing.getOpposite());

                                Aspect originCurrentAspect = originWEI.getStoredAspect();

                                if (targetAspect != null && targetAspect != originWEI.getStoredAspect()) {
                                    continue;
                                }

                                sourcesAC.addToContainer(originWEI.getStoredAspect(), amount);
                                if(originWEI.takeFromContainer(originWEI.getStoredAspect(), amount)) {
                                    remaining -= amount;
                                }

                                if (originCurrentAspect == null) return;

                                ConnectionKey key = new ConnectionKey(originWEI, teSources);
                                long now = System.currentTimeMillis();

                                connectionDelay.entrySet().removeIf(entry -> now > entry.getValue() + ESSENTIA_DELAY_MILLIS);

                                if (!connectionDelay.containsKey(key) || now > connectionDelay.get(key)) {
                                    long delay = (long)(ESSENTIA_DELAY_MILLIS / (1.0 + s.score * 0.1));
                                    connectionDelay.put(key, now + delay);

                                    SimpleNetworkWrapper LOCAL_NETWORK = PacketHandler.INSTANCE;
                                    PacketFXEssentiaSource packetIn = createEssentiaFX(originWEI, new WorldCoordinates(mirrorSource), originCurrentAspect);
                                    PacketFXEssentiaSource packetOut = createEssentiaFX(linkedMirror, new WorldCoordinates(teSources), originCurrentAspect);

                                    if (packetIn == null) continue;
                                    LOCAL_NETWORK.sendToAllAround(packetIn,
                                            new NetworkRegistry.TargetPoint(
                                                    originWEI.getWorldObj().provider.dimensionId,
                                                    originWEI.xCoord,
                                                    originWEI.yCoord,
                                                    originWEI.zCoord,
                                                    32f
                                            )
                                    );

                                    if (packetOut == null) continue;
                                    LOCAL_NETWORK.sendToAllAround(packetOut,
                                            new NetworkRegistry.TargetPoint(
                                                    linkedMirror.getWorldObj().provider.dimensionId,
                                                    linkedMirror.xCoord,
                                                    linkedMirror.yCoord,
                                                    linkedMirror.zCoord,
                                                    32f
                                            )
                                    );
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public static void drainEssentiaWireless(TileEntity originTE, World world, List<ScoredSource> sources, ForgeDirection facing, int toExtract) {
        double totalWeight = 0;

        TileEntityWirelessEssentiaInterfaceInput originWEI = (TileEntityWirelessEssentiaInterfaceInput) originTE;

        int currentStoredWEI = Math.min(toExtract, originWEI.getMaxCapacityEssentiaVis() - originWEI.getCurrentStoredVis());

        for (ScoredSource s : sources) {
            totalWeight += 1.0 / (1.0 + s.score);
        }

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

                    if (packet == null) continue;
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

            if(sourcesAC instanceof IAspectSource && teSources instanceof TileMirrorEssentia) {
                drainEssentiaWireless(originWEI, (TileMirrorEssentia) teSources, toExtract);
            }
        }
    }

    public static void drainEssentiaWireless(TileEntityWirelessEssentiaInterfaceInput originWEI, TileMirrorEssentia mirrorSource, int toExtract) {
        if(mirrorSource != null) {
            World localWorldDim = DimensionManager.getWorld(mirrorSource.linkDim);
            if (localWorldDim != null) {
                TileEntity teLinkedMirror = localWorldDim.getTileEntity(mirrorSource.linkX, mirrorSource.linkY, mirrorSource.linkZ);
                if (teLinkedMirror instanceof TileMirrorEssentia) {
                    TileMirrorEssentia linkedMirror = (TileMirrorEssentia) teLinkedMirror;
                    ForgeDirection linkedMirrorFacing = ForgeDirection.getOrientation(linkedMirror.blockMetadata % 6);
                    int currentStoredWEI = Math.min(toExtract, originWEI.getMaxCapacityEssentiaVis() - originWEI.getCurrentStoredVis());

                    if (linkedMirrorFacing != ForgeDirection.UNKNOWN && originWEI.hasWorldObj() && linkedMirror.hasWorldObj()) {
                        LOG.info("found mirror at: [{}, {}, {}] dim: {}, facing: {}", mirrorSource.linkX, mirrorSource.linkY, mirrorSource.linkZ, mirrorSource.linkDim, linkedMirrorFacing);

                        List<ScoredSource> linkedMirrorSources = getNearestEssentiaHandler(localWorldDim, linkedMirror.xCoord, linkedMirror.yCoord, linkedMirror.zCoord, linkedMirrorFacing, wirelessOutputInterfaceWorkRadius);

                        double totalWeight = 0;

                        for (ScoredSource s : linkedMirrorSources) {
                            totalWeight += 1.0 / (1.0 + s.score);
                        }

                        for(ScoredSource s : linkedMirrorSources) {
                            if (currentStoredWEI > originWEI.getMaxCapacityEssentiaVis()) break;

                            TileEntity teSources = localWorldDim.getTileEntity(s.pos.x, s.pos.y, s.pos.z);

                            IEssentiaTransport sourcesET = null;
                            if (teSources instanceof IEssentiaTransport) sourcesET = (IEssentiaTransport) teSources;

                            IAspectContainer sourcesAC = null;
                            if (teSources instanceof IAspectContainer) sourcesAC = (IAspectContainer) teSources;

                            if(sourcesET != null && sourcesAC != null) {
                                int available = sourcesET.getEssentiaAmount(linkedMirrorFacing.getOpposite());
                                if (available <= 0) continue;

                                Aspect sourceAspect = sourcesET.getEssentiaType(linkedMirrorFacing.getOpposite());
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

                                ConnectionKey key = new ConnectionKey(originWEI, teSources);
                                long now = System.currentTimeMillis();

                                if (!connectionDelay.containsKey(key) || now > connectionDelay.get(key)) {
                                    long delay = (long)(ESSENTIA_DELAY_MILLIS / (1.0 + s.score * 0.1));
                                    connectionDelay.put(key, now + delay);

                                    SimpleNetworkWrapper LOCAL_NETWORK = PacketHandler.INSTANCE;
                                    PacketFXEssentiaSource packetIn = createEssentiaFX(mirrorSource, new WorldCoordinates(originWEI), sourceAspect);
                                    PacketFXEssentiaSource packetOut = createEssentiaFX(teSources, new WorldCoordinates(linkedMirror), sourceAspect);

                                    if (packetIn == null) continue;
                                    LOCAL_NETWORK.sendToAllAround(packetIn,
                                            new NetworkRegistry.TargetPoint(
                                                    mirrorSource.getWorldObj().provider.dimensionId,
                                                    mirrorSource.xCoord,
                                                    mirrorSource.yCoord,
                                                    mirrorSource.zCoord,
                                                    32f
                                            )
                                    );

                                    if (packetOut == null) continue;
                                    LOCAL_NETWORK.sendToAllAround(packetOut,
                                            new NetworkRegistry.TargetPoint(
                                                    linkedMirror.getWorldObj().provider.dimensionId,
                                                    linkedMirror.xCoord,
                                                    linkedMirror.yCoord,
                                                    linkedMirror.zCoord,
                                                    32f
                                            )
                                    );
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private static PacketFXEssentiaSource createEssentiaFX(TileEntity source, WorldCoordinates destiny, Aspect originCurrentAspect) {
        if (source == null || destiny == null) return null;

        int color = (originCurrentAspect != null) ? originCurrentAspect.getColor() : 0xFFFFFF;

        byte dx;
        byte dy;
        byte dz;

        dx = (byte) Math.max(-127, Math.min(127, destiny.x - source.xCoord));
        dy = (byte) Math.max(-127, Math.min(127, destiny.y - source.yCoord));
        dz = (byte) Math.max(-127, Math.min(127, destiny.z - source.zCoord));

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
        public WorldCoordinates pos;
        public double score;

        public ScoredSource(WorldCoordinates pos, double score) {
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