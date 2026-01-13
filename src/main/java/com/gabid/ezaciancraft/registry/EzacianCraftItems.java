package com.gabid.ezaciancraft.registry;

import com.gabid.ezaciancraft.common.items.*;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.wands.WandCap;
import thaumcraft.common.config.ConfigItems;
import thaumcraft.common.items.wands.ItemWandCap;

import static com.gabid.ezaciancraft.CoreMod.MODID;
import static com.gabid.ezaciancraft.registry.EzacianCraftCreativeTab.*;

public class EzacianCraftItems {

    //debug
    public static Item ezaciancraftTabIconItem;

    //resources

    //*tools
    public static Item voidZephyrSword;
    public static Item voidCorePickaxe;
    public static Item voidStreamAxe;
    public static Item voidTerraShatterShovel;
    public static Item voidGrowthHoe;

    //*wand caps and staffs etc
    public static ItemEzacianWandCap baseWandCap;
    public static WandCap WAND_CAP_SHADOW_VOID_METAL;

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

        //wand caps and staffs etc

        baseWandCap = new ItemEzacianWandCap();
        baseWandCap.setUnlocalizedName("ezacianWandCap");
        GameRegistry.registerItem(baseWandCap, baseWandCap.getUnlocalizedName(), MODID);

        WAND_CAP_SHADOW_VOID_METAL = new WandCap("shadow_void_metal", .35f, new ItemStack(baseWandCap, 1, 1), 5);
        WAND_CAP_SHADOW_VOID_METAL.setTexture(new ResourceLocation(MODID, "textures/models/wand_cap_"+WAND_CAP_SHADOW_VOID_METAL.getTag()+".png"));
    }
}
