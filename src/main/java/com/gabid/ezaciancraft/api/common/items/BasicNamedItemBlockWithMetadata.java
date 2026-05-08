package com.gabid.ezaciancraft.api.common.items;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlockWithMetadata;
import net.minecraft.item.ItemStack;

public class BasicNamedItemBlockWithMetadata extends ItemBlockWithMetadata {
    public BasicNamedItemBlockWithMetadata(Block blockDummy) {
        super(blockDummy, blockDummy);
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        return super.getUnlocalizedName() + "." + stack.getItemDamage();
    }
}
