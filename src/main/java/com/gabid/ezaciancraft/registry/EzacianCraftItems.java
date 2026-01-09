package com.gabid.ezaciancraft.registry;

import com.gabid.ezaciancraft.api.common.items.MetalResourceItem;
import com.gabid.ezaciancraft.common.items.*;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;

import static com.gabid.ezaciancraft.CoreMod.MODID;
import static com.gabid.ezaciancraft.api.EzacianCraftGeneralLang.UNLOCALE_SHADOW_VOID_METAL;
import static com.gabid.ezaciancraft.registry.EzacianCraftCreativeTab.EZACIANCRAFT_TAB;

public class EzacianCraftItems {

    //debug
    public static Item ezaciancraftTabIconItem;

    //resources
    public static Item shadowVoidMetal;

    //tools
    public static Item voidZephyrSword;
    public static Item voidCorePickaxe;
    public static Item voidStreamAxe;
    public static Item voidTerraShatterShovel;
    public static Item voidGrowthHoe;

    public static void setupItemsRegistry() {
        //debug
        ezaciancraftTabIconItem = new Item();
        ezaciancraftTabIconItem.setUnlocalizedName("ezacianIcon");
        ezaciancraftTabIconItem.setMaxStackSize(1);
        ezaciancraftTabIconItem.setTextureName(new ResourceLocation(MODID, "ezacianSymbol").toString());
        GameRegistry.registerItem(ezaciancraftTabIconItem, ezaciancraftTabIconItem.getUnlocalizedName(), MODID);

        //resources

        //tools
        voidZephyrSword = new VoidZephyrSwordItem();
        GameRegistry.registerItem(voidZephyrSword, voidZephyrSword.getUnlocalizedName(), MODID);

        voidCorePickaxe = new VoidCorePickaxeItem();
        GameRegistry.registerItem(voidCorePickaxe, voidCorePickaxe.getUnlocalizedName(), MODID);

        voidStreamAxe = new VoidStreamAxeItem();
        GameRegistry.registerItem(voidStreamAxe, voidStreamAxe.getUnlocalizedName(), MODID);

        voidTerraShatterShovel = new VoidEarthMoverShovelItem();
        GameRegistry.registerItem(voidTerraShatterShovel, voidTerraShatterShovel.getUnlocalizedName(), MODID);

        voidGrowthHoe = new VoidGrowthHoeItem();
        GameRegistry.registerItem(voidGrowthHoe, voidGrowthHoe.getUnlocalizedName(), MODID);
    }
}
