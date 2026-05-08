package com.gabid.ezaciancraft.api.common.blocks.container;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import thaumcraft.common.container.SlotLimitedByWand;
import thaumcraft.common.items.wands.ItemWandCasting;

public class SlotAnyWand extends SlotLimitedByWand {
    public SlotAnyWand(IInventory inv, int slot, int x, int y) {
        super(inv, slot, x, y);
    }

    @Override
    public boolean isItemValid(ItemStack stack) {
        return stack != null && stack.getItem() != null && stack.getItem() instanceof ItemWandCasting;
    }
}
