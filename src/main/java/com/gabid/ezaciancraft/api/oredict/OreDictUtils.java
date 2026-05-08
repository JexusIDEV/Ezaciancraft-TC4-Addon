package com.gabid.ezaciancraft.api.oredict;

import net.minecraft.item.ItemStack;

import java.util.List;

public class OreDictUtils {
    public static List<String> getOreDictName(ItemStack check) {
        return OreDictCache.getOreDictName(check);
    }
}
