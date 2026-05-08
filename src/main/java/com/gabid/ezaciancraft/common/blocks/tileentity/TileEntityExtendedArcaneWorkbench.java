package com.gabid.ezaciancraft.common.blocks.tileentity;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import thaumcraft.common.items.wands.ItemWandCasting;
import thaumcraft.common.tiles.TileArcaneWorkbench;

import static com.gabid.ezaciancraft.api.EzacianCraftGeneralLang.UNLOCALE_EXTENDED_ARCANE_WORKBENCH;
import static com.gabid.ezaciancraft.common.blocks.BlockExtendedArcaneWorkbench.multiblockBlueprint;
import static com.gabid.ezaciancraft.common.blocks.BlockExtendedArcaneWorkbench.multiblockMetaDatas;

public class TileEntityExtendedArcaneWorkbench extends TileArcaneWorkbench {

    public TileEntityExtendedArcaneWorkbench() {
    }

    @Override
    public String getInventoryName() {
        return StatCollector.translateToLocal("container." + UNLOCALE_EXTENDED_ARCANE_WORKBENCH + ".title");
    }

    @Override
    public boolean hasCustomInventoryName() {
        return false;
    }

    @Override
    public int getInventoryStackLimit() {
        return 64;
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer player) {
        return true;
    }

    @Override
    public void openInventory() {

    }

    @Override
    public void closeInventory() {

    }

    @Override
    public boolean func_94041_b(int i, ItemStack itemstack) {
        if (i == 10) {
            return this.func_70301_a(10).getItem() instanceof ItemWandCasting;
        } else {
            return true;
        }
    }

    @Override
    public void markDirty() {
        super.markDirty();
        if (this.eventHandler != null) {
            this.eventHandler.onCraftMatrixChanged(this);
        }
    }

    public void destroyMultiblock() {
        int x = this.xCoord;
        int y = this.yCoord;
        int z = this.zCoord;
        for (int xOffset = -1; xOffset <= 1; xOffset++) {
            for (int zOffset = -1; zOffset <= 1; zOffset++) {
                if (this.getWorldObj().getBlock(x + xOffset, y, z + zOffset) == this.getBlockType()) {
                    int currentBlockMeta = this.getWorldObj().getBlockMetadata(x + xOffset, y, z + zOffset);
                    this.getWorldObj().setBlock(x + xOffset, y, z + zOffset, Block.getBlockFromItem(new ItemStack(multiblockBlueprint[currentBlockMeta]).getItem()), multiblockMetaDatas[currentBlockMeta], 3);
                }
            }
        }
        this.getWorldObj().playSoundEffect(x + .5f, y + .5f, z + .5f, "thaumcraft:craftfail", 1f, 1f);
    }
}
