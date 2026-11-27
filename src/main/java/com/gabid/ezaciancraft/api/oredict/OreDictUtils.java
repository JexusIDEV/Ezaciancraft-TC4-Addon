package com.gabid.ezaciancraft.api.oredict;

import java.util.List;

import net.minecraft.item.ItemStack;

public class OreDictUtils {

    public static List<String> getOreDictName(ItemStack check) {
        return OreDictCache.getOreDictName(check);
    }
}
