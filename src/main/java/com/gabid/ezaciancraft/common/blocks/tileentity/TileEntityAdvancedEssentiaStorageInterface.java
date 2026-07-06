package com.gabid.ezaciancraft.common.blocks.tileentity;

import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import thaumcraft.api.ThaumcraftApiHelper;
import thaumcraft.api.TileThaumcraft;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.IEssentiaTransport;
import thaumcraft.common.tiles.TileTube;

public class TileEntityAdvancedEssentiaStorageInterface extends TileThaumcraft implements IEssentiaTransport {

    protected TileEntityAdvancedEssentiaStorage storageReference = null;
    protected Aspect readAspect = null;

    public TileEntityAdvancedEssentiaStorageInterface() {

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
        if (!canOutputTo(forgeDirection) && this.getStorageReference() == null) return 0;

        Aspect available = this.getSuctionType(forgeDirection);

        if (available == null || aspect != available) return 0;

        int stored = this.getStorageReference().aspects.getAmount(available);
        if (stored <= 0) return 0;

        this.getStorageReference().aspects.removeAspect(available, amount);
        this.markDirty();
        this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);

        return amount;
    }

    @Override
    public int addEssentia(Aspect aspect, int i, ForgeDirection forgeDirection) {
        return 0;
    }

    @Override
    public Aspect getEssentiaType(ForgeDirection forgeDirection) {
        TileEntity pipeToFind;
        for(ForgeDirection dirSearch : ForgeDirection.VALID_DIRECTIONS) {
            if(dirSearch == forgeDirection) {
                pipeToFind = ThaumcraftApiHelper.getConnectableTile(this.worldObj, this.xCoord, this.yCoord, this.zCoord, forgeDirection);
                if(pipeToFind instanceof TileTube) {
                    TileTube tube = (TileTube) pipeToFind;
                    this.readAspect = tube.getEssentiaType(forgeDirection.getOpposite());
                    return this.readAspect;
                }
            }
        }
        return null;
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
        super.updateEntity();
        if(this.getStorageReference() != null) {
            if(this.getStorageReference().getStorageTicks() % 5 == 0) {
                if(this.blockMetadata == 1) {
                    this.insertToContainer();
                }
            }
        }
    }

    private void insertToContainer() {
        for (ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
            if (!this.canInputFrom(dir)) continue;
            TileEntity te = ThaumcraftApiHelper.getConnectableTile(
                    this.worldObj, this.xCoord, this.yCoord, this.zCoord, dir);

            if (te instanceof TileTube) {
                TileTube other = (TileTube) te;
                Aspect aspect = other.getSuctionType(dir.getOpposite());

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

    public void setStorageReference(TileEntityAdvancedEssentiaStorage storage) {
        this.storageReference = storage;
    }

    public TileEntityAdvancedEssentiaStorage getStorageReference() {
        return this.storageReference;
    }
}
