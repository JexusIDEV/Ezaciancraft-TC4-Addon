package com.gabid.ezaciancraft.api.common.blocks.tileentity;

import net.minecraftforge.common.util.ForgeDirection;

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
