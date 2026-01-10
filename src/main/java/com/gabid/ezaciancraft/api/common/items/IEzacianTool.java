package com.gabid.ezaciancraft.api.common.items;

import net.minecraft.item.ItemStack;

public interface IEzacianTool {
    void setMode(ItemStack stack, int mode);
    void changeMode(ItemStack stack);
    int getMode(ItemStack stack);
}
