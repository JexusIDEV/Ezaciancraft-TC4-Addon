package com.gabid.ezaciancraft.common.blocks.tileentity;

import com.gabid.ezaciancraft.api.aspects.ExtendedAspectList;
import com.gabid.ezaciancraft.api.aspects.IExtendedAspectContainer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import thaumcraft.api.ThaumcraftApiHelper;
import thaumcraft.api.TileThaumcraft;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.aspects.IEssentiaTransport;

public class TileEntityAdvancedEssentiaStorage extends TileThaumcraft implements IEssentiaTransport, IExtendedAspectContainer {
    public final ExtendedAspectList aspects = new ExtendedAspectList();
    public boolean isBreaking = false;

    public TileEntityAdvancedEssentiaStorage() {}

    @Override
    public void writeCustomNBT(NBTTagCompound tag) {
        super.writeCustomNBT(tag);
        this.aspects.writeToNBT(tag);
    }

    @Override
    public void readCustomNBT(NBTTagCompound tag) {
        super.readCustomNBT(tag);
        this.aspects.readFromNBT(tag);
    }

    @Override
    public ExtendedAspectList getExtendedAspects() {
        return this.aspects;
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
        return true;
    }

    @Override
    public int addToContainer(Aspect aspect, int amount) {
        if (amount <= 0) return 0;

        this.aspects.addAspect(aspect, amount);

        this.markDirty();
        this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);

        return 0;
    }

    @Override
    public boolean takeFromContainer(Aspect aspect, int amount) {
        if (this.aspects.getAmount(aspect) >= amount) {
            this.aspects.removeAspect(aspect, amount);

            this.markDirty();
            this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);

            return true;
        }
        return false;
    }

    @Override
    public boolean takeFromContainer(AspectList aspectList) {
        if (!doesContainerContain(aspectList)) return false;

        for (Aspect asp : aspectList.getAspects()) {
            this.aspects.removeAspect(asp, aspectList.getAmount(asp));
        }

        this.markDirty();
        this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);

        return true;
    }

    @Override
    public boolean doesContainerContainAmount(Aspect aspect, int i) {
        return this.aspects.getAmount(aspect) >= i;
    }

    @Override
    public boolean doesContainerContain(AspectList aspectList) {
        for (Aspect asp : aspectList.getAspects()) {
            if (this.aspects.getAmount(asp) < aspectList.getAmount(asp)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public int containerContains(Aspect aspect) {
        return this.aspects.getAmount(aspect);
    }

    @Override
    public boolean isConnectable(ForgeDirection forgeDirection) {
        return false;
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
        return null;
    }

    @Override
    public int getSuctionAmount(ForgeDirection forgeDirection) {
        return 0;
    }

    @Override
    public int takeEssentia(Aspect aspect, int amount, ForgeDirection forgeDirection) {
        return 0;
    }

    @Override
    public int addEssentia(Aspect aspect, int amount, ForgeDirection forgeDirection) {
        return this.canInputFrom(forgeDirection) ? amount - this.addToContainer(aspect, amount) : 0;
    }

    @Override
    public Aspect getEssentiaType(ForgeDirection forgeDirection) {
        TileEntity te = ThaumcraftApiHelper.getConnectableTile(
                this.worldObj, this.xCoord, this.yCoord, this.zCoord, forgeDirection);

        if (te instanceof IEssentiaTransport) {
            IEssentiaTransport other = (IEssentiaTransport) te;

            Aspect requested = other.getSuctionType(forgeDirection.getOpposite());

            if (requested != null && this.aspects.getAmount(requested) > 0) {
                return requested;
            } else {
                return null;
            }
        }

        return this.getDominantAspect();
    }

    @Override
    public int getEssentiaAmount(ForgeDirection forgeDirection) {
        Aspect type = this.getEssentiaType(forgeDirection);
        return type != null ? this.aspects.getAmount(type) : 0;
    }

    @Override
    public int getMinimumSuction() {
        return 0;
    }

    @Override
    public boolean renderExtendedTube() {
        return false;
    }

    public Aspect getDominantAspect() {
        Aspect best = null;
        int max = 0;

        for (Aspect asp : this.getExtendedAspects().getStoredAspects()) {
            int amt = this.aspects.getAmount(asp);
            if (amt > max) {
                max = amt;
                best = asp;
            }
        }
        return best;
    }
}