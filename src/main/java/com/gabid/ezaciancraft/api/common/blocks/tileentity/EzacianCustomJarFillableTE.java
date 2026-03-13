package com.gabid.ezaciancraft.api.common.blocks.tileentity;

import com.gabid.ezaciancraft.lib.EzacianMathHelper;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import thaumcraft.api.ThaumcraftApiHelper;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.aspects.IAspectSource;
import thaumcraft.api.aspects.IEssentiaTransport;

public class EzacianCustomJarFillableTE extends EzacianCustomJarBaseTE implements IAspectSource, IEssentiaTransport {
    public Aspect aspect;
    public Aspect aspectFilter;
    public int amount;
    public int maxAmount;
    public int facing;
    private int count = 0;

    public EzacianCustomJarFillableTE() {
        this.aspect = null;
        this.aspectFilter = null;
        this.amount = 0;
        this.maxAmount = 8;
        this.facing = 2;
    }

    @Override
    public int getMinimumSuction() {
        return this.aspectFilter != null ? 128 : 64;
    }

    @Override
    public int getSuctionAmount(ForgeDirection loc) {
        if (this.amount < this.maxAmount) {
            return this.aspectFilter != null ? 128 : 64;
        } else {
            return 0;
        }
    }

    @Override
    public void readCustomNBT(NBTTagCompound nbttagcompound) {
        super.readCustomNBT(nbttagcompound);
        this.aspect = Aspect.getAspect(nbttagcompound.getString("Aspect"));
        this.aspectFilter = Aspect.getAspect(nbttagcompound.getString("AspectFilter"));
        this.amount = nbttagcompound.getInteger("Amount");
        if(this.maxAmount > 0) {
            this.maxAmount = nbttagcompound.getInteger("MaxAmount");
        }
        this.facing = nbttagcompound.getByte("facing");

        super.readCustomNBT(nbttagcompound);
    }

    @Override
    public void writeCustomNBT(NBTTagCompound nbttagcompound) {
        super.writeCustomNBT(nbttagcompound);
        if (this.aspect != null) {
            nbttagcompound.setString("Aspect", this.aspect.getTag());
        }
        if (this.aspectFilter != null) {
            nbttagcompound.setString("AspectFilter", this.aspectFilter.getTag());
        }
        nbttagcompound.setInteger("Amount", this.amount);
        if(this.maxAmount > 0) {
            nbttagcompound.setInteger("MaxAmount", this.maxAmount);
        }
        nbttagcompound.setByte("facing", (byte) this.facing);

        super.writeCustomNBT(nbttagcompound);
    }

    @Override
    public AspectList getAspects() {
        AspectList al = new AspectList();
        if (this.aspect != null && this.amount > 0) {
            al.add(this.aspect, this.amount);
        }

        return al;
    }

    @Override
    public void setAspects(AspectList aspects) {
    }

    @Override
    public int addToContainer(Aspect tt, int am) {
        if (am != 0) {
            if (this.amount < this.maxAmount && tt == this.aspect || this.amount == 0) {
                this.aspect = tt;
                int added = Math.min(am, this.maxAmount - this.amount);
                this.amount += added;
                am -= added;
            }
            this.getWorldObj().markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
            this.markDirty();
        }
        return am;
    }

    @Override
    public boolean takeFromContainer(Aspect tt, int am) {
        if (this.amount >= am && tt == this.aspect) {
            this.amount -= am;
            if (this.amount <= 0) {
                this.aspect = null;
                this.amount = 0;
            }
            this.getWorldObj().markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
            this.markDirty();
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean takeFromContainer(AspectList ot) {
        return false;
    }

    @Override
    public boolean doesContainerContainAmount(Aspect tag, int amt) {
        return this.amount >= amt && tag == this.aspect;
    }

    @Override
    public boolean doesContainerContain(AspectList ot) {
        Aspect[] arr$ = ot.getAspects();

        for (Aspect tt : arr$) {
            if (this.amount > 0 && tt == this.aspect) {
                return true;
            }
        }

        return false;
    }

    @Override
    public int containerContains(Aspect tag) {
        return 0;
    }

    @Override
    public boolean doesContainerAccept(Aspect tag) {
        return this.aspectFilter == null || tag.equals(this.aspectFilter);
    }

    @Override
    public boolean isConnectable(ForgeDirection face) {
        return face == ForgeDirection.UP;
    }

    @Override
    public boolean canInputFrom(ForgeDirection face) {
        return face == ForgeDirection.UP;
    }

    @Override
    public boolean canOutputTo(ForgeDirection face) {
        return face == ForgeDirection.UP;
    }

    @Override
    public void setSuction(Aspect aspect, int amount) {
    }

    @Override
    public boolean renderExtendedTube() {
        return true;
    }

    @Override
    public Aspect getSuctionType(ForgeDirection loc) {
        return this.aspectFilter != null ? this.aspectFilter : this.aspect;
    }

    @Override
    public Aspect getEssentiaType(ForgeDirection loc) {
        return this.aspect;
    }

    @Override
    public int getEssentiaAmount(ForgeDirection loc) {
        return this.amount;
    }

    @Override
    public int takeEssentia(Aspect aspect, int amount, ForgeDirection face) {
        return this.canOutputTo(face) && this.takeFromContainer(aspect, amount) ? amount : 0;
    }

    @Override
    public int addEssentia(Aspect aspect, int amount, ForgeDirection face) {
        return this.canInputFrom(face) ? amount - this.addToContainer(aspect, amount) : 0;
    }

    @Override
    public void updateEntity() {
        super.updateEntity();
        if (!this.worldObj.isRemote && ++this.count % 5 == 0 && this.amount < this.maxAmount) {
            this.fillJar();
        }

    }

    protected void fillJar() {
        TileEntity te = ThaumcraftApiHelper.getConnectableTile(this.worldObj, this.xCoord, this.yCoord, this.zCoord, ForgeDirection.UP);
        if (te != null) {
            IEssentiaTransport ic = (IEssentiaTransport)te;
            if (!ic.canOutputTo(ForgeDirection.DOWN)) {
                return;
            }

            Aspect ta = null;
            if (this.aspectFilter != null) {
                ta = this.aspectFilter;
            } else if (this.aspect != null && this.amount > 0) {
                ta = this.aspect;
            } else if (ic.getEssentiaAmount(ForgeDirection.DOWN) > 0 && ic.getSuctionAmount(ForgeDirection.DOWN) < this.getSuctionAmount(ForgeDirection.UP) && this.getSuctionAmount(ForgeDirection.UP) >= ic.getMinimumSuction()) {
                ta = ic.getEssentiaType(ForgeDirection.DOWN);
            }

            if (ta != null && ic.getSuctionAmount(ForgeDirection.DOWN) < this.getSuctionAmount(ForgeDirection.UP)) {
                this.addToContainer(ta, ic.takeEssentia(ta, 1, ForgeDirection.DOWN));
            }
        }
    }

    public int getMaxAmount() {
        return this.maxAmount;
    }

    public void setMaxAmount(int newMaxAmount) {
        if(EzacianMathHelper.isPowOfTwo(newMaxAmount)) {
            this.maxAmount = newMaxAmount;
        } else {
            throw new IllegalStateException("The new Max Amount must be a value of a pow of two...");
        }
    }
}