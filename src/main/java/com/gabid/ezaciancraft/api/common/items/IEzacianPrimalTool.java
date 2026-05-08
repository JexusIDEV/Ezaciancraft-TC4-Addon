package com.gabid.ezaciancraft.api.common.items;

import net.minecraft.item.ItemStack;

public interface IEzacianPrimalTool {
    void setBehaviour(ItemStack stack, int behaviour);

    void changeBehaviour(ItemStack stack);

    int getBehaviour(ItemStack stack);

    void setSubMode(ItemStack stack, int subMode);

    void changeSubMode(ItemStack stack);

    int getSubMode(ItemStack stack);

    void setAOE(ItemStack stack, int aoe);

    void changeAOE(ItemStack stack);

    int getAOE(ItemStack stack);
}
