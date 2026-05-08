package com.gabid.ezaciancraft.common.blocks.tileentity;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import thaumcraft.api.ThaumcraftApiHelper;
import thaumcraft.api.TileThaumcraft;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.aspects.IAspectContainer;
import thaumcraft.api.aspects.IEssentiaTransport;

public class TileEntityWirelessEssentiaInterfaceBase extends TileThaumcraft implements IEssentiaTransport, IAspectContainer {

    public int metaFacing = 3;
    public ForgeDirection facing = ForgeDirection.NORTH;

    protected AspectList essentiaList = new AspectList();
    protected int maxCapacityEssentiaVis = 8;

    public TileEntityWirelessEssentiaInterfaceBase() {
        this.blockMetadata = 0;
    }

    @Override
    public void writeCustomNBT(NBTTagCompound nbttagcompound) {
        if (this.essentiaList != null)
            this.essentiaList.writeToNBT(nbttagcompound);

        nbttagcompound.setInteger("facing", this.facing.ordinal());
        nbttagcompound.setInteger("meta", this.blockMetadata);
        super.writeCustomNBT(nbttagcompound);
    }

    @Override
    public void readCustomNBT(NBTTagCompound nbttagcompound) {
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
        return false;
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
        TileEntity connectedTE = ThaumcraftApiHelper.getConnectableTile(this.worldObj, this.xCoord, this.yCoord, this.zCoord, this.facing);
        if (connectedTE instanceof IEssentiaTransport) {
            IEssentiaTransport pip = (IEssentiaTransport) connectedTE;
            return pip.getSuctionType(forgeDirection);
        }
        return null;
    }

    @Override
    public int getSuctionAmount(ForgeDirection forgeDirection) {
        return 0;
    }

    @Override
    public int takeEssentia(Aspect aspect, int i, ForgeDirection forgeDirection) {
        return 0;
    }

    @Override
    public int addEssentia(Aspect aspect, int i, ForgeDirection forgeDirection) {
        return 0;
    }

    @Override
    public Aspect getEssentiaType(ForgeDirection forgeDirection) {
        return null;
    }

    @Override
    public int getEssentiaAmount(ForgeDirection forgeDirection) {
        return 0;
    }

    @Override
    public int getMinimumSuction() {
        return 0;
    }

    @Override
    public boolean renderExtendedTube() {
        return false;
    }

    @Override
    public AspectList getAspects() {
        return null;
    }

    @Override
    public void setAspects(AspectList aspectList) {

    }

    @Override
    public boolean doesContainerAccept(Aspect aspect) {
        return false;
    }

    @Override
    public int addToContainer(Aspect aspect, int i) {
        return 0;
    }

    @Override
    public boolean takeFromContainer(Aspect aspect, int i) {
        return false;
    }

    @Override
    public boolean takeFromContainer(AspectList aspectList) {
        return false;
    }

    @Override
    public boolean doesContainerContainAmount(Aspect aspect, int i) {
        return false;
    }

    @Override
    public boolean doesContainerContain(AspectList aspectList) {
        return false;
    }

    @Override
    public int containerContains(Aspect aspect) {
        return 0;
    }
}
