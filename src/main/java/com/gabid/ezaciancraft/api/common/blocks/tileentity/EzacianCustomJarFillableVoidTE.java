package com.gabid.ezaciancraft.api.common.blocks.tileentity;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;
import thaumcraft.common.tiles.TileJarFillable;
import thaumcraft.common.tiles.TileJarFillableVoid;

public class EzacianCustomJarFillableVoidTE extends EzacianCustomJarFillableTE {

    public EzacianCustomJarFillableVoidTE() {
    }

    public int getMinimumSuction() {
        return this.aspectFilter != null ? (this.maxAmount / 4) + (this.maxAmount / 2) : this.maxAmount / 2;
    }

    public int getSuctionAmount(ForgeDirection loc) {
        return this.aspectFilter != null && this.amount < this.maxAmount ? (this.maxAmount / 4) + (this.maxAmount / 2) : this.maxAmount / 2;
    }
}
