package com.gabid.ezaciancraft.registry;

import com.gabid.ezaciancraft.api.registry.ResourceMaterialBuilder;
import net.minecraft.block.material.MapColor;

import static com.gabid.ezaciancraft.api.EzacianCraftGeneralLang.UNLOCALE_SHADOW_VOID_METAL;
import static com.gabid.ezaciancraft.registry.EzacianCraftCreativeTab.EZACIANCRAFT_RESOURCES_TAB;

public class EzacianCraftResources {
    public static ResourceMaterialBuilder shadowVoidResources = ResourceMaterialBuilder.createAFullSet(UNLOCALE_SHADOW_VOID_METAL, 1f, 0.25f, MapColor.blackColor, EZACIANCRAFT_RESOURCES_TAB);;

    public static void setupResources() {

    }
}
