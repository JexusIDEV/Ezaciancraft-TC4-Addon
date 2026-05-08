package com.gabid.ezaciancraft.common.blocks.tileentity;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import thaumcraft.api.ThaumcraftApiHelper;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.aspects.IEssentiaTransport;

public class TileEntityWirelessEssentiaInterfaceOutput extends TileEntityWirelessEssentiaInterfaceBase {

    private final long ticks = 0;

    public TileEntityWirelessEssentiaInterfaceOutput() {
    }

    @Override
    public void writeCustomNBT(NBTTagCompound nbttagcompound) {
        super.writeCustomNBT(nbttagcompound);
    }

    @Override
    public void readCustomNBT(NBTTagCompound nbttagcompound) {
        super.readCustomNBT(nbttagcompound);
    }

    @Override
    public boolean canInputFrom(ForgeDirection forgeDirection) {
        return forgeDirection == this.facing;
    }

    @Override
    public int getSuctionAmount(ForgeDirection forgeDirection) {
        return 128;
    }

    @Override
    public int addEssentia(Aspect aspect, int amount, ForgeDirection face) {
        return this.canInputFrom(face) ? amount - this.addToContainer(aspect, amount) : 0;
    }

    @Override
    public Aspect getEssentiaType(ForgeDirection forgeDirection) {
        return this.essentiaList.size() > 0 ? this.essentiaList.getAspects()[this.worldObj.rand.nextInt(this.essentiaList.getAspects().length)] : null;
    }

    @Override
    public int getEssentiaAmount(ForgeDirection forgeDirection) {
        return this.essentiaList.visSize();
    }

    @Override
    public AspectList getAspects() {
        return this.essentiaList;
    }

    @Override
    public boolean doesContainerAccept(Aspect aspect) {
        return true;
    }

    @Override
    public int addToContainer(Aspect tt, int am) {
        if (am != 1) {
            return am;
        } else if (this.essentiaList.visSize() < maxCapacityEssentiaVis) {
            this.essentiaList.add(tt, am);
            this.markDirty();
            this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
            return 0;
        } else {
            return am;
        }
    }

    @Override
    public boolean takeFromContainer(Aspect tt, int am) {
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
        return false;
    }

    @Override
    public int containerContains(Aspect aspect) {
        return this.essentiaList.getAmount(aspect);
    }

    @Override
    public void updateEntity() {
        if (!this.worldObj.isRemote && this.ticks % 5 == 0) {
            int amount = this.essentiaList.visSize();
            if (amount < this.maxCapacityEssentiaVis) {
                this.fillBuffer();
            }
        }
    }

    private void fillBuffer() {
        TileEntity te;
        IEssentiaTransport ic;

        te = ThaumcraftApiHelper.getConnectableTile(this.worldObj, this.xCoord, this.yCoord, this.zCoord, this.facing);
        if (te != null && this.blockMetadata == 0) {
            ic = (IEssentiaTransport) te;
            if (ic.getEssentiaAmount(this.facing.getOpposite()) > 0 && ic.getSuctionAmount(this.facing.getOpposite()) < this.getSuctionAmount(this.facing) && this.getSuctionAmount(this.facing) >= ic.getMinimumSuction()) {
                Aspect ta = ic.getEssentiaType(this.facing.getOpposite());
                this.addToContainer(ta, ic.takeEssentia(ta, 1, this.facing.getOpposite()));
            }
        }
    }
}
