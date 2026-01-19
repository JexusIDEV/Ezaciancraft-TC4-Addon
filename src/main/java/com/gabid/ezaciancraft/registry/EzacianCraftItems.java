package com.gabid.ezaciancraft.registry;

import com.gabid.ezaciancraft.api.aspects.CustomWandRodPrimalUpdate;
import com.gabid.ezaciancraft.common.items.ItemEzacianWandCap;
import com.gabid.ezaciancraft.common.items.ItemEzacianWandStaffRod;
import com.gabid.ezaciancraft.common.items.tools.VoidCorePickaxeItem;
import com.gabid.ezaciancraft.common.items.tools.VoidEarthMoverShovelItem;
import com.gabid.ezaciancraft.common.items.tools.VoidGrowthHoeItem;
import com.gabid.ezaciancraft.common.items.tools.VoidStreamAxeItem;
import com.gabid.ezaciancraft.common.items.weapons.VoidZephyrSwordItem;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import thaumcraft.api.wands.StaffRod;
import thaumcraft.api.wands.WandCap;
import thaumcraft.api.wands.WandRod;
import thaumcraft.common.items.wands.WandRodPrimalOnUpdate;

import static com.gabid.ezaciancraft.CoreMod.MODID;

public class EzacianCraftItems {

    //debug - extra
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
    public static ItemEzacianWandStaffRod baseWandRod;
    public static WandCap WAND_CAP_SHADOW_VOID_METAL;
    public static WandRod WAND_ROD_ADVANCED_PRIMAL;
    public static StaffRod STAFF_ROD_ADVANCED_PRIMAL;

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
        WAND_CAP_SHADOW_VOID_METAL.setTexture(new ResourceLocation(MODID, "textures/models/wand_cap_shadow_void_metal.png"));

        baseWandRod = new ItemEzacianWandStaffRod();
        baseWandRod.setUnlocalizedName("ezacianWandRod");
        GameRegistry.registerItem(baseWandRod, baseWandRod.getUnlocalizedName(), MODID);

        WAND_ROD_ADVANCED_PRIMAL = new WandRod("advanced_primal", 250, new ItemStack(baseWandRod, 1, 0), 10, new CustomWandRodPrimalUpdate(.75f, 60));
        WAND_ROD_ADVANCED_PRIMAL.setTexture(new ResourceLocation(MODID, "textures/models/wand_rod_advanced_primal.png"));
        STAFF_ROD_ADVANCED_PRIMAL = new StaffRod("advanced_primal", 500, new ItemStack(baseWandRod, 1, 1), 15, new CustomWandRodPrimalUpdate(.75f, 60));
        STAFF_ROD_ADVANCED_PRIMAL.setTexture(new ResourceLocation(MODID, "textures/models/staff_rod_advanced_primal.png"));
        STAFF_ROD_ADVANCED_PRIMAL.setRunes(true);
    }
}
