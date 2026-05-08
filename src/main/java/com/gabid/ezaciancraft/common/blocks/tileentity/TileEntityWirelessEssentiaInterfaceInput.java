package com.gabid.ezaciancraft.common.blocks.tileentity;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;

public class TileEntityWirelessEssentiaInterfaceInput extends TileEntityWirelessEssentiaInterfaceBase {

    public TileEntityWirelessEssentiaInterfaceInput() {
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
    public boolean canOutputTo(ForgeDirection forgeDirection) {
        return forgeDirection == this.facing;
    }

    @Override
    public int takeEssentia(Aspect aspect, int amount, ForgeDirection face) {
        return this.canOutputTo(this.facing) && this.takeFromContainer(aspect, amount) ? amount : 0;
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
        if (this.essentiaList.getAmount(tt) >= am) {
            this.essentiaList.remove(tt, am);
            this.markDirty();
            this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
            return true;
        }
        return false;
    }

    @Override
    public boolean doesContainerContainAmount(Aspect aspect, int i) {
        return this.essentiaList.getAmount(aspect) >= i;
    }

    @Override
    public int containerContains(Aspect aspect) {
        return this.essentiaList.getAmount(aspect);
    }
}
