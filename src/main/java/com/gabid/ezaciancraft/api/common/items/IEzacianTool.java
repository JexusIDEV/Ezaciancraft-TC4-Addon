package com.gabid.ezaciancraft.api.common.items;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public interface IEzacianTool {
    void setMode(ItemStack stack, int mode);
    void changeMode(ItemStack stack);
    int getMode(ItemStack stack);
}
