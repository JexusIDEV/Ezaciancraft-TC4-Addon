package com.gabid.ezaciancraft.common.blocks.tileentity;

import com.gabid.ezaciancraft.api.essentia.EzacianEssentiaWirelessHandler;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import thaumcraft.api.ThaumcraftApiHelper;
import thaumcraft.api.TileThaumcraft;
import thaumcraft.api.aspects.*;

import java.util.ArrayList;
import java.util.List;

import static com.gabid.ezaciancraft.config.EzacianCraftConfiguration.wirelessInputInterfaceWorkRadius;

public class TileEntityWirelessEssentiaInterfaceInput extends TileThaumcraft implements IEssentiaTransport, IAspectContainer {

    public int metaFacing = 3;
    public ForgeDirection facing = ForgeDirection.NORTH;

    protected AspectList essentiaList = new AspectList();
    protected int currentStoredVis = 0;
    protected int maxCapacityEssentiaVis = 8;

    protected List<EzacianEssentiaWirelessHandler.ScoredSource> essentiaSources = new ArrayList<>();

    protected long ticks;

    public TileEntityWirelessEssentiaInterfaceInput() {
    }

    @Override
    public void writeCustomNBT(NBTTagCompound nbttagcompound) {
        if (this.essentiaList != null)
            this.essentiaList.writeToNBT(nbttagcompound);
        nbttagcompound.setInteger("currentStoredVis", this.currentStoredVis);

        nbttagcompound.setInteger("facing", this.facing.ordinal());
        nbttagcompound.setInteger("meta", this.blockMetadata);
        super.writeCustomNBT(nbttagcompound);
    }

    @Override
    public void readCustomNBT(NBTTagCompound nbttagcompound) {
        this.essentiaList.readFromNBT(nbttagcompound);
        this.currentStoredVis = nbttagcompound.getInteger("currentStoredVis");
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
        return false;
    }

    @Override
    public boolean canOutputTo(ForgeDirection forgeDirection) {
        return forgeDirection == this.facing;
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
    public Aspect getEssentiaType(ForgeDirection forgeDirection) {
        return this.essentiaList.size() > 0 ? this.essentiaList.getAspects()[this.worldObj.rand.nextInt(this.essentiaList.getAspects().length)] : null;    }

    @Override
    public int getEssentiaAmount(ForgeDirection forgeDirection) {
        return this.essentiaList.visSize();
    }

    @Override
    public int getMinimumSuction() {
        return 1;
    }

    @Override
    public boolean renderExtendedTube() {
        return false;
    }

    @Override
    public int addEssentia(Aspect aspect, int amount, ForgeDirection forgeDirection) {
        return this.canInputFrom(this.facing) ? amount - this.addToContainer(aspect, amount) : 0;
    }

    @Override
    public int getSuctionAmount(ForgeDirection forgeDirection) {
        TileEntity pipeToFind = ThaumcraftApiHelper.getConnectableTile(this.worldObj, this.xCoord, this.yCoord, this.zCoord, this.facing);
        if(pipeToFind instanceof IEssentiaTransport) {
            return ((IEssentiaTransport) pipeToFind).getSuctionAmount(forgeDirection) - 1;
        }
        return 1;
    }

    @Override
    public int takeEssentia(Aspect aspect, int amount, ForgeDirection forgeDirection) {
        return this.canOutputTo(this.facing) && this.takeFromContainer(aspect, amount) ? amount : 0;
    }

    @Override
    public void updateEntity() {
        super.updateEntity();
        if(this.ticks < 0 || this.ticks == Long.MAX_VALUE) {
            this.ticks = 0;
        }

        if(!this.worldObj.isRemote && this.ticks++ % 5 == 0) {
            if (this.essentiaList.visSize() >= 0 || this.currentStoredVis < this.maxCapacityEssentiaVis) {
                this.essentiaSources = EzacianEssentiaWirelessHandler.getNearestEssentiaHandler(this.worldObj, this.xCoord, this.yCoord, this.zCoord, this.facing.getOpposite(), wirelessInputInterfaceWorkRadius);
                EzacianEssentiaWirelessHandler.drainEssentiaWireless(this, this.worldObj, this.essentiaSources, this.facing, 1);
            }
        }
    }

    @Override
    public AspectList getAspects() {
        return this.essentiaList;
    }

    @Override
    public void setAspects(AspectList aspectList) {

    }

    @Override
    public boolean doesContainerAccept(Aspect aspect) {
        return true;
    }

    @Override
    public int addToContainer(Aspect aspect, int amount) {
        if (amount != 0) {
            if (this.currentStoredVis < this.maxCapacityEssentiaVis || this.currentStoredVis == 0) {
                this.essentiaList.add(aspect, amount);
                int added = Math.min(amount, this.maxCapacityEssentiaVis - this.currentStoredVis);
                this.currentStoredVis += added;
                amount -= added;
            }
            this.getWorldObj().markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
            this.markDirty();
        }
        return amount;
    }

    @Override
    public boolean takeFromContainer(Aspect aspect, int amount) {
        if (this.essentiaList.getAmount(aspect) >= amount) {
            this.essentiaList.remove(aspect, amount);
            this.currentStoredVis -= amount;
            this.markDirty();
            this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
            return true;
        }
        return false;
    }

    @Override
    public boolean takeFromContainer(AspectList aspectList) {
        return false;
    }

    @Override
    public boolean doesContainerContainAmount(Aspect aspect, int i) {
        return this.essentiaList.getAmount(aspect) >= i;
    }

    @Override
    public boolean doesContainerContain(AspectList aspectList) {
        return true;
    }

    @Override
    public int containerContains(Aspect aspect) {
        return this.essentiaList.getAmount(aspect);
    }

    public int getCurrentStoredVis() {
        return this.currentStoredVis;
    }

    public int getMaxCapacityEssentiaVis() {
        return this.maxCapacityEssentiaVis;
    }
}
