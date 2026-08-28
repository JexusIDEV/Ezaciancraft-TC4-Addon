package com.gabid.ezaciancraft.registry;

import com.gabid.ezaciancraft.api.EzacianToolMaterials;
import com.gabid.ezaciancraft.api.aspects.wand.CustomWandRodPrimalUpdate;
import com.gabid.ezaciancraft.api.common.items.CustomItemWandCap;
import com.gabid.ezaciancraft.api.common.items.CustomItemWandRodStaff;
import com.gabid.ezaciancraft.api.common.items.EzacianCustomItemJarFilled;
import com.gabid.ezaciancraft.api.registry.EzacianCraftMiscRegistry;
import com.gabid.ezaciancraft.common.items.ItemEzacianPlates;
import com.gabid.ezaciancraft.api.common.items.ItemCustomFortressArmor;
import com.gabid.ezaciancraft.common.items.armor.ItemMagicAlloyFortressArmor;
import com.gabid.ezaciancraft.common.items.armor.ItemMagicAlloyTravelerBoots;
import com.gabid.ezaciancraft.common.items.armor.ItemShadowVoidMetalRobes;
import com.gabid.ezaciancraft.common.items.debug.ItemDebugger;
import com.gabid.ezaciancraft.common.items.tools.*;
import com.gabid.ezaciancraft.common.items.vegetal.ItemAspectSeed;
import com.gabid.ezaciancraft.common.items.weapons.VoidZephyrSwordItem;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;

import static com.gabid.ezaciancraft.CoreMod.MODID;
import static com.gabid.ezaciancraft.api.EzacianCraftGeneralLang.*;
import static com.gabid.ezaciancraft.registry.EzacianCraftCreativeTab.EZACIANCRAFT_TAB;

public class EzacianCraftItems {

    //debug - extra
    public static Item ezaciancraftTabIconItem;
    public static Item debugItem;

    //resources
    public static Item ezacianPlates;
    public static Item aspectSeeds;

    //*Other
    public static EzacianCustomItemJarFilled itemFilledJarCrystalyium;
    public static EzacianCustomItemJarFilled itemFilledJarShadowVoidMetal;
    public static EzacianCustomItemJarFilled itemFilledJarMagicAlloy;

    //*tools
    public static Item voidZephyrSword;
    public static Item voidCorePickaxe;
    public static Item voidStreamAxe;
    public static Item voidTerraShatterShovel;
    public static Item voidGrowthHoe;
    public static Item voidStaffOfPrimalReconstructor;

    //armors
    public static Item magicAlloyTravellerBoots;

    public static Item magicAlloyFortressArmorHelmet;
    public static Item magicAlloyFortressArmorChest;
    public static Item magicAlloyFortressArmorLeggings;

    public static Item shadowVoidMetalRobesArmorHelmet;
    public static Item shadowVoidMetalRobesArmorChest;
    public static Item shadowVoidMetalRobesArmorLeggings;

    //*wand caps and staffs etc
    public static CustomItemWandCap shadowVoidMetalCap;
    public static CustomItemWandRodStaff advancedPrimalWandStaffRod;
    public static CustomItemWandCap magicAlloyCap;

    public static void setupItemsRegistry() {
        //debug
        ezaciancraftTabIconItem = new Item();
        ezaciancraftTabIconItem.setUnlocalizedName("ezacianIcon");
        ezaciancraftTabIconItem.setMaxStackSize(1);
        ezaciancraftTabIconItem.setTextureName(new ResourceLocation(MODID, "ezacianSymbol").toString());
        GameRegistry.registerItem(ezaciancraftTabIconItem, ezaciancraftTabIconItem.getUnlocalizedName(), MODID);

        debugItem = new ItemDebugger();
        GameRegistry.registerItem(debugItem, debugItem.getUnlocalizedName(), MODID);

        //resources
        ezacianPlates = new ItemEzacianPlates();
        GameRegistry.registerItem(ezacianPlates, ezacianPlates.getUnlocalizedName(), MODID);
        aspectSeeds = new ItemAspectSeed();
        GameRegistry.registerItem(aspectSeeds, aspectSeeds.getUnlocalizedName(), MODID);

        //other
        itemFilledJarCrystalyium = new EzacianCustomItemJarFilled(EzacianCraftBlocks.crystalyiumJar);
        GameRegistry.registerItem(itemFilledJarCrystalyium, UNLOCALE_CRYSTALYIUM_JAR, MODID);
        itemFilledJarShadowVoidMetal = new EzacianCustomItemJarFilled(EzacianCraftBlocks.shadowVoidMetalJar);
        GameRegistry.registerItem(itemFilledJarShadowVoidMetal, UNLOCALE_SHADOW_VOID_METAL_JAR, MODID);
        itemFilledJarMagicAlloy = new EzacianCustomItemJarFilled(EzacianCraftBlocks.magicAlloyJar);
        GameRegistry.registerItem(itemFilledJarMagicAlloy, UNLOCALE_MAGIC_ALLOY_JAR, MODID);

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

        voidStaffOfPrimalReconstructor = new VoidStaffOfPrimalReconstructorItem();
        GameRegistry.registerItem(voidStaffOfPrimalReconstructor, voidStaffOfPrimalReconstructor.getUnlocalizedName(), MODID);

        //armors
        magicAlloyTravellerBoots = new ItemMagicAlloyTravelerBoots();
        GameRegistry.registerItem(magicAlloyTravellerBoots, magicAlloyTravellerBoots.getUnlocalizedName(), MODID);

        //fortress
        magicAlloyFortressArmorHelmet = new ItemMagicAlloyFortressArmor(EzacianToolMaterials.armorMagicAlloyMaterial, 4, 0);
        GameRegistry.registerItem(magicAlloyFortressArmorHelmet, magicAlloyFortressArmorHelmet.getUnlocalizedName(), MODID);
        magicAlloyFortressArmorChest = new ItemMagicAlloyFortressArmor(EzacianToolMaterials.armorMagicAlloyMaterial, 4, 1);
        GameRegistry.registerItem(magicAlloyFortressArmorChest, magicAlloyFortressArmorChest.getUnlocalizedName(), MODID);
        magicAlloyFortressArmorLeggings = new ItemMagicAlloyFortressArmor(EzacianToolMaterials.armorMagicAlloyMaterial, 4, 2);
        GameRegistry.registerItem(magicAlloyFortressArmorLeggings, magicAlloyFortressArmorLeggings.getUnlocalizedName(), MODID);

        //shadow robe
        shadowVoidMetalRobesArmorHelmet = new ItemShadowVoidMetalRobes(EzacianToolMaterials.armorShadowVoidMetalMaterial, 4, 0);
        GameRegistry.registerItem(shadowVoidMetalRobesArmorHelmet, shadowVoidMetalRobesArmorHelmet.getUnlocalizedName(), MODID);
        shadowVoidMetalRobesArmorChest = new ItemShadowVoidMetalRobes(EzacianToolMaterials.armorShadowVoidMetalMaterial, 4, 1);
        GameRegistry.registerItem(shadowVoidMetalRobesArmorChest, shadowVoidMetalRobesArmorChest.getUnlocalizedName(), MODID);
        shadowVoidMetalRobesArmorLeggings = new ItemShadowVoidMetalRobes(EzacianToolMaterials.armorShadowVoidMetalMaterial, 4, 2);
        GameRegistry.registerItem(shadowVoidMetalRobesArmorLeggings, shadowVoidMetalRobesArmorLeggings.getUnlocalizedName(), MODID);

        //wand caps and staffs etc
        shadowVoidMetalCap = new CustomItemWandCap(UNLOCALE_SHADOW_VOID_METAL, MODID, EnumRarity.epic, EZACIANCRAFT_TAB, "shadow_void_metal", 0.35f, 5, true);
        GameRegistry.registerItem(shadowVoidMetalCap, shadowVoidMetalCap.getUnlocalizedName(), MODID);

        magicAlloyCap = new CustomItemWandCap(UNLOCALE_MAGIC_ALLOY, MODID, EzacianCraftMiscRegistry.beyondRarity, EZACIANCRAFT_TAB, "magic_alloy", 0.15f, 5, true);
        GameRegistry.registerItem(magicAlloyCap, magicAlloyCap.getUnlocalizedName(), MODID);

        advancedPrimalWandStaffRod = new CustomItemWandRodStaff(UNLOCALE_ADVANCED_PRIMAL_STAFF_WAND_ROD, MODID, EnumRarity.epic, EZACIANCRAFT_TAB, "advanced_primal", 250, 10, new CustomWandRodPrimalUpdate(.75f, 60), true, true);
        GameRegistry.registerItem(advancedPrimalWandStaffRod, advancedPrimalWandStaffRod.getUnlocalizedName(), MODID);
    }
}
