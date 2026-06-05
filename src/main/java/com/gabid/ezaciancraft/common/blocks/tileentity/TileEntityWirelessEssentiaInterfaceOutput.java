package com.gabid.ezaciancraft.common.blocks.tileentity;

import com.gabid.ezaciancraft.api.essentia.EzacianEssentiaWirelessHandler;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.scoreboard.Score;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import thaumcraft.api.ThaumcraftApiHelper;
import thaumcraft.api.TileThaumcraft;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.aspects.IAspectContainer;
import thaumcraft.api.aspects.IEssentiaTransport;
import thaumcraft.common.tiles.TileMirrorEssentia;

import java.util.ArrayList;
import java.util.List;

import static com.gabid.ezaciancraft.CoreMod.LOG;
import static com.gabid.ezaciancraft.config.EzacianCraftConfiguration.wirelessOutputInterfaceWorkRadius;

public class TileEntityWirelessEssentiaInterfaceOutput extends TileThaumcraft implements IEssentiaTransport, IAspectContainer {

    public int metaFacing = 3;
    public ForgeDirection facing = ForgeDirection.NORTH;
    public ForgeDirection frontFace;

    protected AspectList essentiaList = new AspectList();
    private Aspect storedAspect = null;
    private int storedAmount = 0;
    private final int maxAmount = 8;

    protected long ticks;

    protected List<EzacianEssentiaWirelessHandler.ScoredSource> essentiaSources = new ArrayList<>();

    public TileEntityWirelessEssentiaInterfaceOutput() {
        this.frontFace = facing.getOpposite();
    }

    @Override
    public void writeCustomNBT(NBTTagCompound nbttagcompound) {
        nbttagcompound.setInteger("storedVis", this.storedAmount);

        if (this.storedAspect != null)
            nbttagcompound.setString("storedAspect", this.storedAspect.getTag());

        if (this.essentiaList != null)
            this.essentiaList.writeToNBT(nbttagcompound);

        nbttagcompound.setInteger("facing", this.facing.ordinal());
        nbttagcompound.setInteger("meta", this.blockMetadata);
        super.writeCustomNBT(nbttagcompound);
    }

    @Override
    public void readCustomNBT(NBTTagCompound nbttagcompound) {
        this.storedAmount = nbttagcompound.getInteger("storedVis");

        this.storedAspect = Aspect.getAspect(nbttagcompound.getString("storedAspect"));
        this.essentiaList.readFromNBT(nbttagcompound);
        int face = nbttagcompound.getInteger("facing");
        this.facing = ForgeDirection.getOrientation(face);
        this.metaFacing = this.facing.ordinal();
        this.blockMetadata = nbttagcompound.getInteger("meta");
        super.readCustomNBT(nbttagcompound);
    }

    @Override
    public boolean isConnectable(ForgeDirection forgeDirection) {
        return forgeDirection == this.facing;
    }

    @Override
    public boolean canInputFrom(ForgeDirection forgeDirection) {
        return forgeDirection == this.facing;
    }

    @Override
    public boolean canOutputTo(ForgeDirection forgeDirection) {
        return false;
    }

    @Override
    public void setSuction(Aspect aspect, int i) {

    }

    @Override
    public Aspect getSuctionType(ForgeDirection forgeDirection) {
        TileEntity pipeToFind = ThaumcraftApiHelper.getConnectableTile(this.worldObj, this.xCoord, this.yCoord, this.zCoord, this.facing);
        if(pipeToFind instanceof IEssentiaTransport) {
            return ((IEssentiaTransport) pipeToFind).getSuctionType(this.facing.getOpposite());
        }
        return null;
    }

    @Override
    public int getSuctionAmount(ForgeDirection forgeDirection) {
        return 128;
    }

    @Override
    public int takeEssentia(Aspect aspect, int i, ForgeDirection forgeDirection) {
        return 0;
    }

    @Override
    public int addEssentia(Aspect aspect, int amount, ForgeDirection face) {
        return this.canInputFrom(face) ? amount - this.addToContainer(aspect, amount) : 0;
    }

    @Override
    public Aspect getEssentiaType(ForgeDirection forgeDirection) {
        return this.storedAspect;
    }

    @Override
    public int getEssentiaAmount(ForgeDirection forgeDirection) {
        return this.storedAmount;
    }

    @Override
    public int getMinimumSuction() {
        return 0;
    }

    @Override
    public boolean renderExtendedTube() {
        return true;
    }

    @Override
    public void updateEntity() {
        super.updateEntity();
        if(this.ticks < 0 || this.ticks == Long.MAX_VALUE) {
            this.ticks = 0;
        }

        if (!this.worldObj.isRemote && this.ticks++ % 5 == 0) {
            this.fillInterface();
            if ((this.storedAspect != null && this.essentiaList.visSize() > 0) || this.storedAmount > 0) {
                this.essentiaSources = EzacianEssentiaWirelessHandler.getNearestEssentiaHandler(this.worldObj, this.xCoord, this.yCoord, this.zCoord, this.facing.getOpposite(), wirelessOutputInterfaceWorkRadius);
                if(!this.essentiaSources.isEmpty()) {
                    EzacianEssentiaWirelessHandler.fillEssentiaWireless(this, this.worldObj, this.essentiaSources, this.facing, 1);
                }
            }
        }
    }

    @Override
    public AspectList getAspects() {
        AspectList al = new AspectList();
        if (this.storedAspect != null && this.essentiaList.visSize() > 0) {
            al.add(this.storedAspect, this.essentiaList.visSize());
        }

        return al;
    }

    @Override
    public void setAspects(AspectList aspectList) {

    }

    @Override
    public boolean doesContainerAccept(Aspect aspect) {
        return aspect == this.storedAspect;
    }

    @Override
    public int addToContainer(Aspect aspect, int amount) {
        if (amount != 0) {
            if (this.storedAmount < this.maxAmount && aspect == this.storedAspect || this.storedAmount == 0) {
                this.storedAspect = aspect;
                this.essentiaList.add(aspect, amount);
                int added = Math.min(amount, this.maxAmount - this.storedAmount);
                this.storedAmount += added;
                amount -= added;
            }
            this.getWorldObj().markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
            this.markDirty();
        }
        return amount;
    }

    @Override
    public boolean takeFromContainer(Aspect aspect, int amount) {
        if (this.storedAmount >= amount && aspect == this.storedAspect) {
            this.storedAmount -= amount;
            this.essentiaList.remove(aspect, amount);
            if (this.storedAmount <= 0) {
                this.storedAspect = null;
                this.storedAmount = 0;
            }
            this.getWorldObj().markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
            this.markDirty();
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean takeFromContainer(AspectList aspectList) {
        return false;
    }

    @Override
    public boolean doesContainerContainAmount(Aspect aspect, int amount) {
        return this.storedAmount >= amount && aspect == this.storedAspect;
    }

    @Override
    public boolean doesContainerContain(AspectList aspectList) {
        Aspect[] arr$ = aspectList.getAspects();

        for (Aspect tt : arr$) {
            if (this.storedAmount > 0 && tt == this.storedAspect) {
                return true;
            }
        }

        return false;
    }

    @Override
    public int containerContains(Aspect aspect) {
        return this.essentiaList.getAmount(aspect);
    }

    private void fillInterface() {
        TileEntity tubeTE;
        IEssentiaTransport iETube;

        tubeTE = ThaumcraftApiHelper.getConnectableTile(this.worldObj, this.xCoord, this.yCoord, this.zCoord, this.facing);
        if(tubeTE instanceof IEssentiaTransport) {
            iETube = (IEssentiaTransport) tubeTE;
            if(iETube.getEssentiaAmount(this.facing.getOpposite()) > 0 && iETube.getSuctionAmount(this.facing.getOpposite()) < this.getSuctionAmount(this.facing) && this.getSuctionAmount(this.facing) >= iETube.getMinimumSuction()) {
                Aspect aspect;
                if (this.storedAspect != null && this.storedAmount > 0) {
                    aspect = this.storedAspect;
                } else {
                    aspect = iETube.getEssentiaType(this.facing.getOpposite());
                }
                if(this.getStoredAmount() < this.getMaxAmount()) {
                    this.addToContainer(aspect, iETube.takeEssentia(aspect, 1, this.facing.getOpposite()));
                }
            }
        }
    }

    public int getStoredAmount() {
        return this.storedAmount;
    }

    public Aspect getStoredAspect() {
        return this.storedAspect;
    }

    public int getMaxAmount() {
        return this.maxAmount;
    }
}
