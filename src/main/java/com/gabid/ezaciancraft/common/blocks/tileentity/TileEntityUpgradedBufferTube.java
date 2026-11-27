package com.gabid.ezaciancraft.common.blocks.tileentity;

import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

import lombok.Getter;
import lombok.Setter;
import thaumcraft.api.ThaumcraftApiHelper;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.IEssentiaTransport;
import thaumcraft.common.tiles.TileTubeBuffer;

@Setter
@Getter
public class TileEntityUpgradedBufferTube extends TileTubeBuffer {

    public int maxAmount = 32;
    protected int ticks;

    public TileEntityUpgradedBufferTube() {}

    public int addToContainer(Aspect asp, int amount) {
        if (amount != 1) {
            return amount;
        } else if (this.aspects.visSize() < this.maxAmount) {
            this.aspects.add(asp, amount);
            this.markDirty();
            this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
            return 0;
        } else {
            return amount;
        }
    }

    @Override
    public void updateEntity() {
        ++this.ticks;
        if (this.ticks < 0 || this.ticks % 20 == 0) {
            this.getBellows();
        }

        if (!this.worldObj.isRemote && this.ticks % 5 == 0) {
            int amount = this.aspects.visSize();
            if (amount < this.maxAmount) {
                this.fillBuffer();
            }
        }
    }

    private void fillBuffer() {
        TileEntity te;
        IEssentiaTransport transport;
        ForgeDirection[] directions = ForgeDirection.VALID_DIRECTIONS;

        for (ForgeDirection dir : directions) {
            te = ThaumcraftApiHelper.getConnectableTile(this.worldObj, this.xCoord, this.yCoord, this.zCoord, dir);
            if (te != null) {
                transport = (IEssentiaTransport) te;
                if (transport.getEssentiaAmount(dir.getOpposite()) > 0
                    && transport.getSuctionAmount(dir.getOpposite()) < this.getSuctionAmount(dir)
                    && this.getSuctionAmount(dir) >= transport.getMinimumSuction()) {
                    Aspect ta = transport.getEssentiaType(dir.getOpposite());
                    this.addToContainer(ta, transport.takeEssentia(ta, 1, dir.getOpposite()));
                    return;
                }
            }
        }
    }
}
