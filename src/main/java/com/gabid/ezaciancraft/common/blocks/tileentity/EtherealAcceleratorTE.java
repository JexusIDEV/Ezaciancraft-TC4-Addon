package com.gabid.ezaciancraft.common.blocks.tileentity;

import com.pengu.thaumcraft.additions.tileentity.TileTileBooster;
import com.pengu.util.ForgeDirectionHelper;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import thaumcraft.api.aspects.Aspect;

public class EtherealAcceleratorTE extends TileEntity {

    private int accelerationSpeed;

    public EtherealAcceleratorTE(int _accelerationSpeed) {
        this.accelerationSpeed = _accelerationSpeed;
    }

    private void accelerateTEs() {
        if(this.worldObj.isRemote) return;
        ForgeDirection[] directions = ForgeDirection.VALID_DIRECTIONS;
        int detectedDirs = directions.length;

        for(int i = 0; i < detectedDirs; ++i) {
            ForgeDirection dir  = directions[i];
            int x = this.xCoord + ForgeDirectionHelper.getForgeDirectionXApplyed(dir);
            int y = this.yCoord + ForgeDirectionHelper.getForgeDirectionYApplyed(dir);
            int z = this.zCoord + ForgeDirectionHelper.getForgeDirectionZApplyed(dir);
            TileEntity te = this.worldObj.getTileEntity(x, y, z) != null && !(this.worldObj.getTileEntity(x, y, z) instanceof EtherealAcceleratorTE || this.worldObj.getTileEntity(x, y, z) instanceof TileTileBooster) ? this.worldObj.getTileEntity(x, y, z) : null;
            if (te != null && te.canUpdate()) {
                for(int h = 0; h < this.accelerationSpeed; ++i) {
                    te.updateEntity();
                }
            }
        }
    }

    @Override
    public void updateEntity() {
        super.updateEntity();
        this.accelerateTEs();
    }

    @Override
    public void writeToNBT(NBTTagCompound nbttagcompound) {
        nbttagcompound.setInteger("accEASpeed", this.accelerationSpeed);
        super.writeToNBT(nbttagcompound);
    }

    @Override
    public void readFromNBT(NBTTagCompound nbttagcompound) {
        this.accelerationSpeed = nbttagcompound.getInteger("accEASpeed");
        super.readFromNBT(nbttagcompound);
    }

    public int getAccelerationSpeed() {
        return this.accelerationSpeed;
    }

    public void setAccelerationSpeed(int accelerationSpeed) {
        this.accelerationSpeed = accelerationSpeed;
    }
}
