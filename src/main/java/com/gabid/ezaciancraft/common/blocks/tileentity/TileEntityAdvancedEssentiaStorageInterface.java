package com.gabid.ezaciancraft.common.blocks.tileentity;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import thaumcraft.api.ThaumcraftApiHelper;
import thaumcraft.api.TileThaumcraft;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.IEssentiaTransport;
import thaumcraft.common.tiles.TileTube;

public class TileEntityAdvancedEssentiaStorageInterface extends TileThaumcraft implements IEssentiaTransport {

    private long ticks;

    protected int masterX;
    protected int masterY;
    protected int masterZ;

    public TileEntityAdvancedEssentiaStorageInterface() {}

    @Override
    public void writeCustomNBT(NBTTagCompound tag) {
        tag.setInteger("masterX", this.masterX);
        tag.setInteger("masterY", this.masterY);
        tag.setInteger("masterZ", this.masterZ);
        tag.setInteger("meta", this.blockMetadata);
        super.writeCustomNBT(tag);
    }

    @Override
    public void readCustomNBT(NBTTagCompound tag) {
        this.masterX = tag.getInteger("masterX");
        this.masterY = tag.getInteger("masterY");
        this.masterZ = tag.getInteger("masterZ");
        this.blockMetadata = tag.getInteger("meta");
        super.readCustomNBT(tag);
    }

    @Override
    public boolean isConnectable(ForgeDirection forgeDirection) {
        return forgeDirection != ForgeDirection.DOWN && forgeDirection != ForgeDirection.UP;
    }

    @Override
    public boolean canInputFrom(ForgeDirection forgeDirection) {
        return this.blockMetadata == 1;
    }

    @Override
    public boolean canOutputTo(ForgeDirection forgeDirection) {
        return this.blockMetadata == 2;
    }

    @Override
    public void setSuction(Aspect aspect, int i) {

    }

    @Override
    public Aspect getSuctionType(ForgeDirection forgeDirection) {
        TileEntity pipeToFind;
        for(ForgeDirection dirSearch : ForgeDirection.VALID_DIRECTIONS) {
            if(dirSearch == forgeDirection) {
                pipeToFind = ThaumcraftApiHelper.getConnectableTile(this.worldObj, this.xCoord, this.yCoord, this.zCoord, forgeDirection);
                if(pipeToFind instanceof TileTube) {
                    TileTube tube = (TileTube) pipeToFind;
                    return tube.getSuctionType(forgeDirection.getOpposite());
                }
            }
        }
        return null;
    }

    @Override
    public int getSuctionAmount(ForgeDirection forgeDirection) {
        if(this.blockMetadata == 1) {
            return 128;
        } else if(this.blockMetadata == 2) {
            return 0;
        } else {
            return 0;
        }
    }

    @Override
    public int takeEssentia(Aspect aspect, int amount, ForgeDirection forgeDirection) {
        if (!canOutputTo(forgeDirection)) return 0;

        TileEntityAdvancedEssentiaStorage storage = this.getStorageReference();
        if (storage == null) return 0;

        int stored = storage.aspects.getAmount(aspect);
        if (stored <= 0) return 0;

        int toTake = Math.min(amount, stored);

        storage.aspects.removeAspect(aspect, toTake);

        storage.markDirty();
        this.worldObj.markBlockForUpdate(storage.xCoord, storage.yCoord, storage.zCoord);

        return toTake;
    }

    @Override
    public int addEssentia(Aspect aspect, int amount, ForgeDirection dir) {
        if (!canInputFrom(dir)) return 0;

        TileEntityAdvancedEssentiaStorage storage = getStorageReference();
        if (storage == null) return 0;

        storage.addToContainer(aspect, amount);
        return 0;
    }

    @Override
    public Aspect getEssentiaType(ForgeDirection dir) {
        if (!canOutputTo(dir)) return null;

        TileEntityAdvancedEssentiaStorage storage = getStorageReference();
        if (storage == null) return null;

        TileEntity te = ThaumcraftApiHelper.getConnectableTile(
                this.worldObj, this.xCoord, this.yCoord, this.zCoord, dir);

        if (te instanceof IEssentiaTransport) {
            IEssentiaTransport other = (IEssentiaTransport) te;
            Aspect requested = other.getSuctionType(dir.getOpposite());

            if (requested != null && storage.aspects.getAmount(requested) > 0) {
                return requested;
            }
        }

        return storage.getDominantAspect();
    }

    @Override
    public int getEssentiaAmount(ForgeDirection forgeDirection) {
        TileEntity pipeToFind;
        for(ForgeDirection dirSearch : ForgeDirection.VALID_DIRECTIONS) {
            if(dirSearch == forgeDirection) {
                pipeToFind = ThaumcraftApiHelper.getConnectableTile(this.worldObj, this.xCoord, this.yCoord, this.zCoord, forgeDirection);
                if(pipeToFind instanceof TileTube) {
                    TileTube tube = (TileTube) pipeToFind;
                    return tube.getEssentiaAmount(forgeDirection.getOpposite());
                }
            }
        }
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
    public void updateEntity() {
        if(!this.worldObj.isRemote) {
            if(this.ticks++ % 5 == 0) {
                if(this.getStorageReference() != null && this.blockMetadata == 1) {
                    this.insertToContainer();
                }
            }
        }
        super.updateEntity();
    }

    private void insertToContainer() {
        for (ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
            if (!this.canInputFrom(dir)) continue;
            TileEntity te = ThaumcraftApiHelper.getConnectableTile(
                    this.worldObj, this.xCoord, this.yCoord, this.zCoord, dir);

            if (te instanceof TileTube) {
                TileTube other = (TileTube) te;
                Aspect aspect = other.getEssentiaType(dir.getOpposite());

                if (aspect == null) continue;

                if (other.getSuctionAmount(dir.getOpposite()) < this.getSuctionAmount(dir)) {
                    int taken = other.takeEssentia(aspect, 1, dir.getOpposite());
                    if (taken > 0) {
                        this.getStorageReference().addToContainer(aspect, taken);
                    }
                }
            }
        }
    }

    public TileEntityAdvancedEssentiaStorage getStorageReference() {
        TileEntity te = this.worldObj.getTileEntity(this.masterX, this.masterY, this.masterZ);
        return (te instanceof TileEntityAdvancedEssentiaStorage)
                ? (TileEntityAdvancedEssentiaStorage) te
                : null;
    }

    public void setMasterPos(int masX, int masY, int masZ) {
        this.masterX = masX;
        this.masterY = masY;
        this.masterZ = masZ;
    }
}
