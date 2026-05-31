package com.gabid.ezaciancraft.registry;

import com.gabid.ezaciancraft.api.aspects.CustomWandRodPrimalUpdate;
import com.gabid.ezaciancraft.api.common.items.CustomItemWandCap;
import com.gabid.ezaciancraft.api.common.items.CustomItemWandRodStaff;
import com.gabid.ezaciancraft.api.common.items.EzacianCustomItemJarFilled;
import com.gabid.ezaciancraft.api.registry.EzacianCraftMiscRegistry;
import com.gabid.ezaciancraft.common.items.ItemEzacianPlates;
import com.gabid.ezaciancraft.common.items.ItemEzacianWandCap;
import com.gabid.ezaciancraft.common.items.ItemEzacianWandStaffRod;
import com.gabid.ezaciancraft.common.items.armor.ItemMagicAlloyTravelerBoots;
import com.gabid.ezaciancraft.common.items.debug.ItemDebugger;
import com.gabid.ezaciancraft.common.items.tools.*;
import com.gabid.ezaciancraft.common.items.weapons.VoidZephyrSwordItem;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import thaumcraft.api.wands.StaffRod;
import thaumcraft.api.wands.WandCap;
import thaumcraft.api.wands.WandRod;

import static com.gabid.ezaciancraft.CoreMod.MODID;
import static com.gabid.ezaciancraft.api.EzacianCraftGeneralLang.*;
import static com.gabid.ezaciancraft.registry.EzacianCraftCreativeTab.EZACIANCRAFT_TAB;

public class EzacianCraftItems {

    //debug - extra
    public static Item ezaciancraftTabIconItem;
    public static Item debugItem;

    //resources
    public static Item ezacianPlates;

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

        //wand caps and staffs etc
        shadowVoidMetalCap = new CustomItemWandCap(UNLOCALE_SHADOW_VOID_METAL, MODID, EnumRarity.epic, EZACIANCRAFT_TAB, "shadow_void_metal", 0.35f, 5, true);
        GameRegistry.registerItem(shadowVoidMetalCap, shadowVoidMetalCap.getUnlocalizedName(), MODID);

        magicAlloyCap = new CustomItemWandCap(UNLOCALE_MAGIC_ALLOY, MODID, EzacianCraftMiscRegistry.beyondRarity, EZACIANCRAFT_TAB, "magic_alloy", 0.15f, 5, true);
        GameRegistry.registerItem(magicAlloyCap, magicAlloyCap.getUnlocalizedName(), MODID);

        advancedPrimalWandStaffRod = new CustomItemWandRodStaff(UNLOCALE_ADVANCED_PRIMAL_STAFF_WAND_ROD, MODID, EnumRarity.epic, EZACIANCRAFT_TAB, "advanced_primal", 250, 10, new CustomWandRodPrimalUpdate(.75f, 60), true, true);
        GameRegistry.registerItem(advancedPrimalWandStaffRod, advancedPrimalWandStaffRod.getUnlocalizedName(), MODID);
    }
}
