package com.gabid.ezaciancraft.common.blocks.vegetal;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import thaumcraft.api.TileThaumcraft;
import thaumcraft.api.WorldCoordinates;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.aspects.IAspectContainer;

import java.util.Random;

import static com.gabid.ezaciancraft.common.blocks.vegetal.BlockAspectCrop.aspectCoordsData;

public class TileEntityAspectCrop extends TileThaumcraft implements IAspectContainer {

    protected Aspect mainAspect;
    protected int aspectAmount = 1;
    protected int maxCapacity = 8;
    protected int ticks = 0;

    public TileEntityAspectCrop() {}

    @Override
    public void writeCustomNBT(NBTTagCompound nbttagcompound) {
        super.writeCustomNBT(nbttagcompound);
        if(this.mainAspect != null)
            nbttagcompound.setString("aspect", this.mainAspect.getTag());
        nbttagcompound.setInteger("amount", this.aspectAmount);
    }

    @Override
    public void readCustomNBT(NBTTagCompound nbttagcompound) {
        super.readCustomNBT(nbttagcompound);
        if (nbttagcompound.hasKey("aspect")) {
            this.mainAspect = Aspect.getAspect(nbttagcompound.getString("aspect"));
        } else {
            this.mainAspect = null;
        }

        this.aspectAmount = nbttagcompound.getInteger("amount");
    }

    @Override
    public AspectList getAspects() {
        if (this.mainAspect == null || this.aspectAmount <= 0) {
            return new AspectList();
        }

        return new AspectList().add(this.mainAspect, this.aspectAmount);
    }

    public void setCropAspect(Aspect aspect) {
        if(!this.worldObj.isRemote) {
            this.mainAspect = aspect;
            this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
            this.markDirty();
            this.worldObj.markBlockRangeForRenderUpdate(this.xCoord, this.yCoord, this.zCoord, this.xCoord, this.yCoord, this.zCoord);
        }
    }

    @Override
    public void setAspects(AspectList aspectList) {
    }

    @Override
    public boolean doesContainerAccept(Aspect aspect) {
        return aspect == this.mainAspect;
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
        return aspect == this.mainAspect;
    }

    @Override
    public boolean doesContainerContain(AspectList aspectList) {
        Aspect[] arr$ = aspectList.getAspects();

        for (Aspect tt : arr$) {
            if (tt == this.mainAspect) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int containerContains(Aspect aspect) {
        return this.aspectAmount;
    }

    public void addAspect(int amt) {
        if(this.worldObj.isRemote) return;
        if(this.mainAspect == null) return;
        if(this.aspectAmount + amt > 8) return;
        this.aspectAmount += amt;
        this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
        this.markDirty();
        this.worldObj.markBlockRangeForRenderUpdate(this.xCoord, this.yCoord, this.zCoord, this.xCoord, this.yCoord, this.zCoord);
    }

    public Aspect getMainAspect() {
        return this.mainAspect;
    }

    public int getAspectAmount() {
        return this.aspectAmount;
    }

    public int getMaxCapacity() {
        return this.maxCapacity;
    }

    public void onGrow(int meta, int stages, int x, int y, int z, World world, Random rand) {
        int grows = MathHelper.getRandomIntegerInRange(rand, 1, 3);
        if(meta < stages-1) {
            meta += grows;
            world.setBlockMetadataWithNotify(x, y, z, meta, 2);
            this.markDirty();
            world.markBlockRangeForRenderUpdate(x, y, z, x, y, z);
        } else {
            world.setBlockMetadataWithNotify(x, y, z, meta, stages-1);
            this.markDirty();
            world.markBlockRangeForRenderUpdate(this.xCoord, this.yCoord, this.zCoord, this.xCoord, this.yCoord, this.zCoord);
        }
    }
}
