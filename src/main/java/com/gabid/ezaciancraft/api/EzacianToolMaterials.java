package com.gabid.ezaciancraft.api;

import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraftforge.common.util.EnumHelper;

public class EzacianToolMaterials {
    public static Item.ToolMaterial toolMatVoidElemental = EnumHelper.addToolMaterial("VOID_ELEMENTAL", 5, 5000, 20F, 4, 20);
    public static Item.ToolMaterial toolMatPrimalVoidElemental = EnumHelper.addToolMaterial("PRIMAL_VOID_ELEMENTAL", 10, 45200, 30F, 7, 60);

    public static ItemArmor.ArmorMaterial armorShadowVoidMetalMaterial = EnumHelper.addArmorMaterial("SHADOW_VOID_METAL", 380, new int[]{6, 9, 9, 0}, 80);
    public static ItemArmor.ArmorMaterial armorMagicAlloyMaterial = EnumHelper.addArmorMaterial("MAGIC_ALLOY", 800, new int[]{8, 12, 12, 6}, 80);
}