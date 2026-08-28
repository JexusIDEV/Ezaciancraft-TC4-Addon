package com.gabid.ezaciancraft.common.blocks.vegetal;

import cpw.mods.fml.common.network.NetworkRegistry;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import thaumcraft.api.TileThaumcraft;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.aspects.IAspectContainer;
import thaumcraft.common.lib.network.PacketHandler;
import thaumcraft.common.lib.network.fx.PacketFXBlockSparkle;

public class TileEntityAspectCrop extends TileThaumcraft implements IAspectContainer {

    protected AspectList storedAspects = new AspectList();
    protected int aspectAmount = 0;
    protected boolean enableFX = false;

    public TileEntityAspectCrop() {}

    @Override
    public void writeCustomNBT(NBTTagCompound nbttagcompound) {
        super.writeCustomNBT(nbttagcompound);
        this.storedAspects.writeToNBT(nbttagcompound);
        nbttagcompound.setInteger("amount", this.aspectAmount);
    }

    @Override
    public void readCustomNBT(NBTTagCompound nbttagcompound) {
        super.readCustomNBT(nbttagcompound);
        this.storedAspects.readFromNBT(nbttagcompound);
        this.aspectAmount = nbttagcompound.getInteger("amount");
    }

    @Override
    public AspectList getAspects() {
        return this.storedAspects != null ? this.storedAspects : new AspectList();
    }

    public void setCropAspect(AspectList aspectList) {
        this.storedAspects = aspectList;
    }

    @Override
    public void setAspects(AspectList aspectList) {
    }

    @Override
    public boolean doesContainerAccept(Aspect aspect) {
        return aspect.equals(this.storedAspects.getAspects()[0]);
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
        return aspect == this.storedAspects.getAspects()[0];
    }

    @Override
    public boolean doesContainerContain(AspectList aspectList) {
        Aspect[] arr$ = aspectList.getAspects();

        for (Aspect tt : arr$) {
            if (tt == this.storedAspects.getAspects()[0]) {
                return true;
            }
        }

        return false;
    }

    @Override
    public int containerContains(Aspect aspect) {
        return 1;
    }

    public int getAspectAmount(Aspect asp) {
        return this.aspectAmount = this.storedAspects.getAmount(asp);
    }

    public AspectList addAspect(int amt) {
        return this.storedAspects.add(this.storedAspects.getAspects()[0], amt);
    }

    public void setEnableFX(boolean enableFX) {
        this.enableFX = enableFX;
    }
}
