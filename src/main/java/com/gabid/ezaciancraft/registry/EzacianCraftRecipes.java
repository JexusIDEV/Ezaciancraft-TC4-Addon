package com.gabid.ezaciancraft.registry;

import com.gabid.ezaciancraft.CoreMod;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.wands.WandTriggerRegistry;
import thaumcraft.common.config.ConfigBlocks;
import thaumcraft.common.config.ConfigItems;

import java.util.Arrays;

import static com.gabid.ezaciancraft.CoreMod.MODID;
import static com.gabid.ezaciancraft.api.EzacianCraftGeneralLang.*;
import static com.gabid.ezaciancraft.registry.EzacianCraftItems.advancedPrimalWandStaffRod;
import static thaumcraft.api.aspects.Aspect.*;

public class EzacianCraftRecipes {
    public static void setupRecipes() {
        registerWorkbenchRecipes();
        registerFurnaceRecipes();
        registerCauldronRecipes();
        registerArcaneWorkbenchRecipes();
        registerInfusionRecipes();
        registerMultiblockRecipes();
    }

    private static void registerWorkbenchRecipes() {
        //wondering for the recipes checkout the ResourceMaterialBuilder to see how is done the material registry recipes and more

        //voidblock
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(EzacianCraftBlocks.voidMetalBlock, 1),
                "###", "###", "###", '#', new ItemStack(ConfigItems.itemResource, 1, 16)));

        GameRegistry.addRecipe(
                new ShapelessOreRecipe(new ItemStack(ConfigItems.itemResource, 9, 16),
                        (new ItemStack(EzacianCraftBlocks.voidMetalBlock, 1, 0)))
        );

        //crystalyium


        //decoratives no magic

    }

    private static void registerFurnaceRecipes() {

    }

    private static void registerArcaneWorkbenchRecipes() {
        //JARS
        EzacianCraftResearches.recipes.put(UNLOCALE_SHADOW_VOID_METAL_JAR + "Void", ThaumcraftApi.addArcaneCraftingRecipe(
                "SHADOW_VOID_METAL_JAR",
                new ItemStack(EzacianCraftBlocks.shadowVoidMetalJar, 1, 3),
                new AspectList().add(ENTROPY, 32).add(WATER, 24),
                "A", "B", "C", 'A', new ItemStack(Blocks.obsidian, 1), 'B', new ItemStack(Items.blaze_powder, 1), 'C', new ItemStack(EzacianCraftBlocks.shadowVoidMetalJar, 1, 0)));

        EzacianCraftResearches.recipes.put(UNLOCALE_CRYSTALYIUM_JAR + "Void", ThaumcraftApi.addArcaneCraftingRecipe(
                "CRYSATLYIUM_JAR",
                new ItemStack(EzacianCraftBlocks.crystalyiumJar, 1, 3),
                new AspectList().add(ENTROPY, 16).add(WATER, 8),
                "A", "B", "C", 'A', new ItemStack(Blocks.obsidian, 1), 'B', new ItemStack(Items.blaze_powder, 1), 'C', new ItemStack(EzacianCraftBlocks.crystalyiumJar, 1, 0)));

        EzacianCraftResearches.recipes.put(UNLOCALE_MAGIC_ALLOY_JAR + "Void", ThaumcraftApi.addArcaneCraftingRecipe(
                "MAGIC_ALLOY_JAR",
                new ItemStack(EzacianCraftBlocks.magicAlloyJar, 1, 3),
                new AspectList().add(ENTROPY, 64).add(WATER, 48),
                "A", "B", "C", 'A', new ItemStack(Blocks.obsidian, 1), 'B', new ItemStack(Items.blaze_powder, 1), 'C', new ItemStack(EzacianCraftBlocks.magicAlloyJar, 1, 0)));

        //decoratives magic
        EzacianCraftResearches.recipes.put(UNLOCALE_EZACIAN_STONE_DECORATIVE + "_0", ThaumcraftApi.addArcaneCraftingRecipe(
                "DECORATIVE_BLOCKS",
                new ItemStack(EzacianCraftBlocks.ezacianStoneDecorativeBlocks, 8, 0),
                new AspectList().add(ORDER, 4).add(EARTH, 4),
                "###", "$#%", "###", '#', new ItemStack(Blocks.stone, 1), '$', new ItemStack(EzacianCraftResources.crudeCrystalyiumResources.getResourceMetal(), 1, 1), '%', new ItemStack(EzacianCraftResources.shadowVoidMetalResources.getResourceMetal(), 1, 1)
        ));

        //*MAGIC ALLOY
        EzacianCraftResearches.recipes.put(UNLOCALE_MAGIC_ALLOY, ThaumcraftApi.addArcaneCraftingRecipe(
                "MAGIC_ALLOY",
                new ItemStack(EzacianCraftItems.ezacianPlates, 3, 0),
                new AspectList().add(ORDER, 75).add(EARTH, 75).add(FIRE, 75).add(WATER, 75).add(ENTROPY, 75).add(AIR, 75),
                " A ", "$#%", " B ", 'A', new ItemStack(ConfigItems.itemEldritchObject, 1, 3), '$', new ItemStack(ConfigItems.itemResource, 1, 2), '#', new ItemStack(EzacianCraftResources.shadowVoidMetalResources.getResourceMetal(), 1, 0), '%', new ItemStack(ConfigItems.itemResource, 1, 16), 'B', new ItemStack(ConfigBlocks.blockCrystal, 1, 6)
        ));

        EzacianCraftResearches.recipes.put(UNLOCALE_MAGIC_ALLOY+"Cap", ThaumcraftApi.addArcaneCraftingRecipe(
                "CAP_magic_alloy",
                new ItemStack(EzacianCraftItems.magicAlloyCap, 1, 0),
                new AspectList().add(ORDER, 100).add(EARTH, 100).add(FIRE, 100).add(WATER, 100).add(ENTROPY, 100).add(AIR, 100),
                "$#$", "#&#", "   ", '$', new ItemStack(EzacianCraftResources.shadowVoidMetalResources.getResourceMetal(), 1, 1), '#', new ItemStack(EzacianCraftItems.ezacianPlates, 1, 0), '&', new ItemStack(EzacianCraftItems.shadowVoidMetalCap, 1, 0)
        ));
    }

    private static void registerCauldronRecipes() {
        //crystalyium
        EzacianCraftResearches.recipes.put("crudeCrystalyiumCluster", ThaumcraftApi.addCrucibleRecipe(
                "CRYSTALYIUM",
                new ItemStack(EzacianCraftResources.crudeCrystalyiumResources.getResourceMetal(), 1, 2),
                new ItemStack(EzacianCraftResources.crudeCrystalyiumResources.getResourceOreBlock(), 1, 0),
                new AspectList().add(ORDER, 1).add(METAL, 1)
        ));

        //shadow void
        EzacianCraftResearches.recipes.put("shadowVoidMetalCauldron", ThaumcraftApi.addCrucibleRecipe(
                "SHADOW_VOID_METAL",
                new ItemStack(EzacianCraftResources.shadowVoidMetalResources.getResourceMetal(), 1, 0),
                new ItemStack(ConfigItems.itemResource, 1, 16),
                new AspectList().add(DARKNESS, 24).add(TAINT, 2).add(MAGIC, 2).add(VOID, 16).add(ENTROPY, 6).add(ELDRITCH, 16)
        ));

        EzacianCraftResearches.recipes.put("shadowVoidMetalCluster", ThaumcraftApi.addCrucibleRecipe(
                "SHADOW_VOID_METAL",
                new ItemStack(EzacianCraftResources.shadowVoidMetalResources.getResourceMetal(), 1, 2),
                new ItemStack(EzacianCraftResources.shadowVoidMetalResources.getResourceOreBlock(), 1, 0),
                new AspectList().add(DARKNESS, 1).add(VOID, 1).add(ORDER, 1).add(ELDRITCH, 1).add(METAL, 1)
        ));

        //void
        EzacianCraftResearches.recipes.put("voidSeedOreConversionRecipe", ThaumcraftApi.addCrucibleRecipe(
                "VOID_SEED_ORE_CONVERSION",
                new ItemStack(ConfigItems.itemResource, 3, 17),
                new ItemStack(EzacianCraftBlocks.voidSeedOre, 1, 0),
                new AspectList().add(Aspect.CRAFT, 4).add(Aspect.ENTROPY, 4).add(ELDRITCH, 4)
        ));
    }

    private static void registerInfusionRecipes() {
        //mixer
        EzacianCraftResearches.recipes.put(UNLOCALE_ALCHEMICAL_MIXER,
                ThaumcraftApi.addInfusionCraftingRecipe(
                        "ALCHEMICAL_MIXER",
                        new ItemStack(EzacianCraftBlocks.alchemicalMixer, 1, 0),
                        2,
                        new AspectList()
                                .add(WATER, 8)
                                .add(EXCHANGE, 8)
                                .add(MAGIC, 8)
                                .add(CRAFT, 8)
                                .add(MOTION, 4)
                                .add(ORDER, 4)
                        ,
                        new ItemStack(ConfigBlocks.blockJar, 1, 0),
                        new ItemStack[]{
                                new ItemStack(Items.gold_ingot, 1),
                                new ItemStack(ConfigBlocks.blockTube, 1, 4),
                                new ItemStack(ConfigBlocks.blockWoodenDevice, 1, 6),
                                new ItemStack(Items.iron_ingot, 1)
                        }
                ));

        EzacianCraftResearches.recipes.put(UNLOCALE_WIRELESS_ESSENTIA_INTERFACE+"Out",
                ThaumcraftApi.addInfusionCraftingRecipe(
                        "WIRELESS_INTERFACES",
                        new ItemStack(EzacianCraftBlocks.wirelessEssentiaInterface, 1, 0),
                        3,
                        new AspectList()
                                .add(WATER, 8)
                                .add(AIR, 16)
                                .add(MAGIC, 4)
                                .add(AURA, 8)
                                .add(CRAFT, 8)
                                .add(TOOL, 8)
                                .add(TRAVEL, 32)
                        ,
                        new ItemStack(ConfigBlocks.blockTube, 1, 4),
                        new ItemStack[]{
                                new ItemStack(Items.gold_ingot, 1),
                                new ItemStack(ConfigItems.itemResource, 1, 8),
                                new ItemStack(ConfigBlocks.blockWoodenDevice, 1, 6),
                                new ItemStack(Items.iron_ingot, 1, 0),
                                new ItemStack(ConfigBlocks.blockMetalDevice, 1, 9),
                                new ItemStack(Blocks.dispenser, 1, 0)
                        }
                ));

        EzacianCraftResearches.recipes.put(UNLOCALE_WIRELESS_ESSENTIA_INTERFACE+"In",
                ThaumcraftApi.addInfusionCraftingRecipe(
                        "WIRELESS_INTERFACES",
                        new ItemStack(EzacianCraftBlocks.wirelessEssentiaInterface, 1, 1),
                        3,
                        new AspectList()
                                .add(WATER, 8)
                                .add(AIR, 16)
                                .add(MAGIC, 4)
                                .add(AURA, 8)
                                .add(CRAFT, 8)
                                .add(TOOL, 8)
                                .add(TRAVEL, 32)
                        ,
                        new ItemStack(ConfigBlocks.blockTube, 1, 4),
                        new ItemStack[]{
                                new ItemStack(Items.gold_ingot, 1),
                                new ItemStack(ConfigItems.itemResource, 1, 8),
                                new ItemStack(ConfigBlocks.blockWoodenDevice, 1, 6),
                                new ItemStack(Items.iron_ingot, 1, 0),
                                new ItemStack(ConfigBlocks.blockMetalDevice, 1, 9),
                                new ItemStack(Blocks.hopper, 1, 0)
                        }
                ));

        //AF MVE
        EzacianCraftResearches.recipes.put(UNLOCALE_SHADOW_ALCHEMY_FURNACE,
                ThaumcraftApi.addInfusionCraftingRecipe(
                        "SHADOW_ALCHEMY_FURNACE",
                        new ItemStack(EzacianCraftBlocks.shadowAlchemyFurnace, 1, 0),
                        8,
                        new AspectList()
                                .add(WATER, 64)
                                .add(EXCHANGE, 32)
                                .add(MAGIC, 32)
                                .add(CRAFT, 64)
                                .add(AIR, 64)
                                .add(FIRE, 64)
                                .add(ELDRITCH, 16)
                        ,
                        new ItemStack(ConfigBlocks.blockStoneDevice, 1, 0),
                        new ItemStack[]{
                                new ItemStack(EzacianCraftResources.shadowVoidMetalResources.getResourceBlock(), 1, 0),
                                new ItemStack(EzacianCraftResources.crudeCrystalyiumResources.getResourceMetal(), 1, 0),
                                new ItemStack(ConfigBlocks.blockCosmeticSolid, 1, 6),
                                new ItemStack(ConfigBlocks.blockMetalDevice, 1, 9),
                                new ItemStack(ConfigBlocks.blockMetalDevice, 1, 3),
                                new ItemStack(ConfigItems.itemResource, 1, 16),
                                new ItemStack(ConfigBlocks.blockCrystal, 1, 0),
                                new ItemStack(ConfigItems.itemEldritchObject, 1, 3),
                                new ItemStack(ConfigBlocks.blockCrystal, 1, 1)
                        }
                ));

        //crystalyium

        //tools
        EzacianCraftResearches.recipes.put(UNLOCALE_VOID_CORE_PICKAXE,
                ThaumcraftApi.addInfusionCraftingRecipe(
                        "ELEMENTAL_VOID_TOOLS",
                        new ItemStack(EzacianCraftItems.voidCorePickaxe, 1, 0),
                        10,
                        new AspectList()
                                .add(FIRE, 64)
                                .add(MINE, 64)
                                .add(MAGIC, 48)
                                .add(ELDRITCH, 48)
                                .add(TOOL, 48)
                                .add(EARTH, 32)
                                .add(ENTROPY, 32)
                                .add(TAINT, 16)
                        ,
                        new ItemStack(ConfigItems.itemPickVoid, 1, 0),
                        new ItemStack[]{
                                new ItemStack(ConfigItems.itemEldritchObject, 1, 3),
                                new ItemStack(ConfigItems.itemResource, 1, 16),
                                new ItemStack(ConfigBlocks.blockCrystal, 1, 1),
                                new ItemStack(Blocks.tnt, 1, 0),
                                new ItemStack(ConfigItems.itemPickElemental, 1, 0),
                                new ItemStack(Blocks.tnt, 1, 0),
                                new ItemStack(ConfigItems.itemFocusFire, 1, 0),
                                new ItemStack(EzacianCraftResources.shadowVoidMetalResources.getResourceMetal(), 1, 0)
                        }
                ));

        EzacianCraftResearches.recipes.put(UNLOCALE_VOID_STREAM_AXE,
                ThaumcraftApi.addInfusionCraftingRecipe(
                        "ELEMENTAL_VOID_TOOLS",
                        new ItemStack(EzacianCraftItems.voidStreamAxe, 1, 0),
                        10,
                        new AspectList()
                                .add(WATER, 64)
                                .add(TREE, 64)
                                .add(MAGIC, 48)
                                .add(ELDRITCH, 48)
                                .add(TOOL, 48)
                                .add(ENTROPY, 32)
                                .add(TAINT, 16)
                        ,
                        new ItemStack(ConfigItems.itemAxeVoid, 1, 0),
                        new ItemStack[]{
                                new ItemStack(ConfigItems.itemEldritchObject, 1, 3),
                                new ItemStack(ConfigItems.itemResource, 1, 16),
                                new ItemStack(ConfigBlocks.blockCrystal, 1, 2),
                                new ItemStack(ConfigItems.itemAxeElemental, 1, 0),
                                new ItemStack(ConfigBlocks.blockCustomPlant, 1, 1),
                                new ItemStack(EzacianCraftResources.shadowVoidMetalResources.getResourceMetal(), 1, 0)
                        }
                ));

        EzacianCraftResearches.recipes.put(UNLOCALE_VOID_TERRA_SHATTER_SHOVEL,
                ThaumcraftApi.addInfusionCraftingRecipe(
                        "ELEMENTAL_VOID_TOOLS",
                        new ItemStack(EzacianCraftItems.voidTerraShatterShovel, 1, 0),
                        10,
                        new AspectList()
                                .add(EARTH, 64)
                                .add(MINE, 64)
                                .add(MAGIC, 48)
                                .add(ELDRITCH, 48)
                                .add(TOOL, 48)
                                .add(ENTROPY, 32)
                                .add(CRAFT, 32)
                                .add(TAINT, 16)
                        ,
                        new ItemStack(ConfigItems.itemShovelVoid, 1, 0),
                        new ItemStack[]{
                                new ItemStack(ConfigItems.itemEldritchObject, 1, 3),
                                new ItemStack(ConfigItems.itemResource, 1, 16),
                                new ItemStack(ConfigBlocks.blockCrystal, 1, 3),
                                new ItemStack(Blocks.tnt, 1, 0),
                                new ItemStack(ConfigItems.itemShovelElemental, 1, 0),
                                new ItemStack(Blocks.tnt, 1, 0),
                                new ItemStack(ConfigItems.itemFocusExcavation, 1, 0),
                                new ItemStack(EzacianCraftResources.shadowVoidMetalResources.getResourceMetal(), 1, 0)
                        }
                ));

        EzacianCraftResearches.recipes.put(UNLOCALE_VOID_GROWTH_HOE,
                ThaumcraftApi.addInfusionCraftingRecipe(
                        "ELEMENTAL_VOID_TOOLS",
                        new ItemStack(EzacianCraftItems.voidGrowthHoe, 1, 0),
                        10,
                        new AspectList()
                                .add(HARVEST, 64)
                                .add(LIFE, 64)
                                .add(CROP, 64)
                                .add(EARTH, 32)
                                .add(PLANT, 32)
                                .add(TOOL, 32)
                                .add(MAGIC, 16)
                                .add(ELDRITCH, 16)
                                .add(TAINT, 8)
                        ,
                        new ItemStack(ConfigItems.itemHoeVoid, 1, 0),
                        new ItemStack[]{
                                new ItemStack(ConfigItems.itemEldritchObject, 1, 3),
                                new ItemStack(ConfigItems.itemResource, 1, 16),
                                new ItemStack(EzacianCraftResources.shadowVoidMetalResources.getResourceMetal(), 1, 0),
                                new ItemStack(ConfigItems.itemHoeElemental, 1, 0),
                                new ItemStack(ConfigBlocks.blockCrystal, 1, 4),
                                new ItemStack(ConfigBlocks.blockCrystal, 1, 5)
                        }
                ));

        //zephyr void
        EzacianCraftResearches.recipes.put(UNLOCALE_VOID_ZEPHYR_SWORD,
                ThaumcraftApi.addInfusionCraftingRecipe(
                        "ELEMENTAL_VOID_SWORD",
                        new ItemStack(EzacianCraftItems.voidZephyrSword, 1, 0),
                        10,
                        new AspectList()
                                .add(WEAPON, 20)
                                .add(TAINT, 8)
                                .add(AURA, 4)
                                .add(MAGIC, 8)
                                .add(ELDRITCH, 16)
                                .add(FLIGHT, 24)
                                .add(AIR, 24)
                                .add(UNDEAD, 24)
                                .add(DEATH, 16)
                        ,
                        new ItemStack(ConfigItems.itemSwordCrimson, 1, 0),
                        new ItemStack[]{
                                new ItemStack(ConfigItems.itemEldritchObject, 1, 3),
                                new ItemStack(ConfigBlocks.blockCrystal, 1, 0),
                                new ItemStack(Items.feather, 1, 0),
                                new ItemStack(ConfigItems.itemSwordElemental, 1, 0),
                                new ItemStack(ConfigItems.itemResource, 1, 16),
                                new ItemStack(EzacianCraftResources.shadowVoidMetalResources.getResourceMetal(), 1, 0)
                        }
                ));

        //primal
        EzacianCraftResearches.recipes.put(UNLOCALE_STAFF_OF_PRIMAL_RECONSTRUCTOR,
                ThaumcraftApi.addInfusionCraftingRecipe(
                        "PRIMAL_VOID_STAFF_OF_RECONSTRUCTION",
                        new ItemStack(EzacianCraftItems.voidStaffOfPrimalReconstructor, 1, 0),
                        12,
                        new AspectList()
                                .add(WEAPON, 128)
                                .add(TAINT, 32)
                                .add(AURA, 32)
                                .add(MAGIC, 64)
                                .add(ELDRITCH, 32)
                                .add(TOOL, 128)
                                .add(MINE, 92)
                        ,
                        advancedPrimalWandStaffRod.staffWand.getItem(),
                        new ItemStack[]{
                                new ItemStack(EzacianCraftResources.crudeCrystalyiumResources.getResourceMetal(), 1, 0),
                                new ItemStack(EzacianCraftItems.voidZephyrSword, 1, 0),
                                new ItemStack(EzacianCraftItems.voidCorePickaxe, 1, 0),
                                new ItemStack(EzacianCraftItems.voidStreamAxe, 1, 0),
                                new ItemStack(ConfigItems.itemEldritchObject, 1, 3),
                                new ItemStack(ConfigItems.itemPrimalCrusher, 1, 0),
                                new ItemStack(EzacianCraftItems.voidGrowthHoe, 1, 0),
                                new ItemStack(EzacianCraftItems.voidTerraShatterShovel, 1, 0)
                        }
                ));

        //shadow void metal caps
        EzacianCraftResearches.recipes.put("shadowVoidMetalCap",
                ThaumcraftApi.addInfusionCraftingRecipe(
                        "CAP_shadow_void_metal",
                        new ItemStack(EzacianCraftItems.shadowVoidMetalCap, 1, 0),
                        10,
                        new AspectList()
                                .add(MAGIC, 32)
                                .add(ELDRITCH, 32)
                                .add(DARKNESS, 32)
                                .add(TAINT, 16)
                                .add(METAL, 64)
                                .add(TOOL, 16)
                        ,
                        new ItemStack(ConfigItems.itemWandCap, 1, 8),
                        new ItemStack[]{
                                new ItemStack(EzacianCraftResources.shadowVoidMetalResources.getResourceMetal(), 1, 0),
                                new ItemStack(EzacianCraftResources.shadowVoidMetalResources.getResourceMetal(), 1, 1),
                                new ItemStack(EzacianCraftResources.shadowVoidMetalResources.getResourceMetal(), 1, 1)
                        }
                ));

        EzacianCraftResearches.recipes.put("shadowVoidMetalCapCharged",
                ThaumcraftApi.addInfusionCraftingRecipe(
                        "CAP_shadow_void_metal",
                        new ItemStack(EzacianCraftItems.shadowVoidMetalCap, 1, 1),
                        10,
                        new AspectList()
                                .add(MAGIC, 64)
                                .add(ELDRITCH, 64)
                                .add(DARKNESS, 64)
                                .add(VOID, 64)
                                .add(TAINT, 32)
                                .add(AURA, 64)
                                .add(ENERGY, 48)
                        ,
                        new ItemStack(EzacianCraftItems.shadowVoidMetalCap, 1, 0),
                        new ItemStack[]{
                                new ItemStack(ConfigItems.itemResource, 1, 14),
                                new ItemStack(ConfigItems.itemResource, 1, 14),
                                new ItemStack(ConfigItems.itemResource, 1, 14),
                                new ItemStack(ConfigItems.itemResource, 1, 14),
                                new ItemStack(ConfigItems.itemResource, 1, 14),
                                new ItemStack(ConfigItems.itemResource, 1, 14),
                        }
                ));

        EzacianCraftResearches.recipes.put("advancedPrimalWand",
                ThaumcraftApi.addInfusionCraftingRecipe(
                        "ROD_advanced_primal",
                        new ItemStack(advancedPrimalWandStaffRod, 1, 0),
                        16,
                        new AspectList()
                                .add(ORDER, 64)
                                .add(ENTROPY, 72)
                                .add(AIR, 64)
                                .add(FIRE, 64)
                                .add(WATER, 64)
                                .add(EARTH, 64)
                                .add(AURA, 64)
                                .add(MAGIC, 64)
                                .add(TOOL, 64)
                                .add(TAINT, 32)
                                .add(ELDRITCH, 32)
                                .add(VOID, 32)
                                .add(DARKNESS, 32)
                        ,
                        new ItemStack(ConfigItems.itemWandRod, 1, 100),
                        new ItemStack[]{
                                new ItemStack(ConfigItems.itemEldritchObject, 1, 3),
                                new ItemStack(EzacianCraftResources.shadowVoidMetalResources.getResourceMetal(), 1, 0),
                                new ItemStack(ConfigBlocks.blockCrystal, 1, 6),
                                new ItemStack(ConfigItems.itemResource, 1, 15),
                        }
                ));

        EzacianCraftResearches.recipes.put("advancedPrimalStaff",
                ThaumcraftApi.addInfusionCraftingRecipe(
                        "ROD_advanced_primal_staff",
                        new ItemStack(advancedPrimalWandStaffRod, 1, 1),
                        20,
                        new AspectList()
                                .add(AURA, 64)
                                .add(MAGIC, 64)
                                .add(TOOL, 64)
                        ,
                        new ItemStack(ConfigItems.itemResource, 1, 15),
                        new ItemStack[]{
                                new ItemStack(advancedPrimalWandStaffRod, 1, 0),
                                new ItemStack(ConfigBlocks.blockCrystal, 1, 6),
                                new ItemStack(EzacianCraftResources.shadowVoidMetalResources.getResourceMetal(), 1, 0),
                                new ItemStack(advancedPrimalWandStaffRod, 1, 0),
                                new ItemStack(EzacianCraftResources.shadowVoidMetalResources.getResourceMetal(), 1, 0),
                                new ItemStack(ConfigBlocks.blockCrystal, 1, 6),
                        }
                ));

        //jars
        EzacianCraftResearches.recipes.put(UNLOCALE_CRYSTALYIUM_JAR,
                ThaumcraftApi.addInfusionCraftingRecipe(
                        "CRYSTALYIUM_JAR",
                        new ItemStack(EzacianCraftBlocks.crystalyiumJar, 1, 0),
                        4,
                        new AspectList()
                                .add(WATER, 64)
                                .add(EXCHANGE, 32)
                                .add(MAGIC, 64)
                                .add(ORDER, 64)
                                .add(AURA, 64)
                        ,
                        new ItemStack(ConfigBlocks.blockJar, 1, 0),
                        new ItemStack[]{
                                new ItemStack(EzacianCraftResources.crudeCrystalyiumResources.getResourceMetal(), 1, 0),
                                new ItemStack(ConfigBlocks.blockCosmeticOpaque, 1, 2),
                                new ItemStack(EzacianCraftResources.crudeCrystalyiumResources.getResourceMetal(), 1, 0),
                                new ItemStack(ConfigBlocks.blockCosmeticOpaque, 1, 2)
                        }
                ));

        EzacianCraftResearches.recipes.put(UNLOCALE_SHADOW_VOID_METAL_JAR,
                ThaumcraftApi.addInfusionCraftingRecipe(
                        "SHADOW_VOID_METAL_JAR",
                        new ItemStack(EzacianCraftBlocks.shadowVoidMetalJar, 1, 0),
                        8,
                        new AspectList()
                                .add(WATER, 64)
                                .add(EXCHANGE, 32)
                                .add(MAGIC, 64)
                                .add(ORDER, 64)
                                .add(AURA, 64)
                                .add(ELDRITCH, 64)
                                .add(VOID, 64)
                        ,
                        new ItemStack(EzacianCraftBlocks.crystalyiumJar, 1, 0),
                        new ItemStack[]{
                                new ItemStack(EzacianCraftResources.shadowVoidMetalResources.getResourceMetal(), 1, 0),
                                new ItemStack(ConfigBlocks.blockCosmeticOpaque, 1, 2),
                                new ItemStack(ConfigItems.itemEldritchObject, 1, 3),
                                new ItemStack(ConfigBlocks.blockCosmeticOpaque, 1, 2)
                        }
                ));

        //*magic alloy
        EzacianCraftResearches.recipes.put(UNLOCALE_MAGIC_ALLOY+"CapCharged",
                ThaumcraftApi.addInfusionCraftingRecipe(
                        "CAP_magic_alloy",
                        new ItemStack(EzacianCraftItems.magicAlloyCap, 1, 1),
                        10,
                        new AspectList()
                                .add(MAGIC, 256)
                                .add(TAINT, 32)
                                .add(AURA, 128)
                                .add(ENERGY, 128)
                        ,
                        new ItemStack(EzacianCraftItems.magicAlloyCap, 1, 0),
                        new ItemStack[]{
                                new ItemStack(ConfigItems.itemEldritchObject, 1, 3),
                                new ItemStack(ConfigItems.itemResource, 1, 14),
                                new ItemStack(ConfigItems.itemResource, 1, 14),
                                new ItemStack(ConfigItems.itemResource, 1, 14),
                                new ItemStack(EzacianCraftResources.crudeCrystalyiumResources.getResourceMetal(), 1, 0),
                                new ItemStack(ConfigItems.itemResource, 1, 14),
                                new ItemStack(ConfigItems.itemResource, 1, 14),
                                new ItemStack(ConfigItems.itemResource, 1, 14),
                        }
                ));
        EzacianCraftResearches.recipes.put(UNLOCALE_MAGIC_ALLOY_TRAVELLER_BOOTS,
                ThaumcraftApi.addInfusionCraftingRecipe(
                        "MAGIC_ALLOY_TRAVELLER_BOOTS",
                        new ItemStack(EzacianCraftItems.magicAlloyTravellerBoots, 1, 0),
                        6,
                        new AspectList()
                                .add(ARMOR, 64)
                                .add(MAGIC, 48)
                                .add(AIR, 128)
                                .add(MOTION, 92)
                                .add(TRAVEL, 128)
                                .add(FLIGHT, 32)
                        ,
                        new ItemStack(ConfigItems.itemBootsTraveller, 1, 0),
                        new ItemStack[]{
                                new ItemStack(ConfigBlocks.blockCrystal, 1, 0),
                                new ItemStack(Items.feather, 1, 0),
                                new ItemStack(Items.feather, 1, 0),
                                new ItemStack(Items.fish, 1, 0),
                                new ItemStack(ConfigItems.itemResource, 1, 7),
                                new ItemStack(ConfigItems.itemResource, 1, 7),
                                new ItemStack(EzacianCraftItems.ezacianPlates, 1, 0),
                                new ItemStack(EzacianCraftItems.ezacianPlates, 1, 0),
                                new ItemStack(EzacianCraftResources.shadowVoidMetalResources.getResourceMetal(), 1, 0),
                        }
                ));

        EzacianCraftResearches.recipes.put(UNLOCALE_MAGIC_ALLOY_JAR,
                ThaumcraftApi.addInfusionCraftingRecipe(
                        "MAGIC_ALLOY_JAR",
                        new ItemStack(EzacianCraftBlocks.magicAlloyJar, 1, 0),
                        8,
                        new AspectList()
                                .add(WATER, 92)
                                .add(EXCHANGE, 64)
                                .add(MAGIC, 128)
                                .add(ORDER, 64)
                                .add(AURA, 64)
                                .add(VOID, 256)
                        ,
                        new ItemStack(EzacianCraftBlocks.shadowVoidMetalJar, 1, 0),
                        new ItemStack[]{
                                new ItemStack(EzacianCraftItems.ezacianPlates, 1, 0),
                                new ItemStack(ConfigBlocks.blockCosmeticOpaque, 1, 2),
                                new ItemStack(EzacianCraftItems.ezacianPlates, 1, 0),
                                new ItemStack(ConfigBlocks.blockCosmeticOpaque, 1, 2),
                                new ItemStack(ConfigItems.itemEldritchObject, 1, 3),
                                new ItemStack(ConfigBlocks.blockCosmeticOpaque, 1, 2),
                                new ItemStack(EzacianCraftResources.shadowVoidMetalResources.getResourceMetal(), 1, 0),
                                new ItemStack(ConfigBlocks.blockCosmeticOpaque, 1, 2),
                        }
                ));
    }

    private static void registerMultiblockRecipes() {
        ItemStack empty = new ItemStack(ConfigBlocks.blockHole, 1, 15);

        WandTriggerRegistry.registerWandBlockTrigger(CoreMod.thaumcraftMultiblockEvent, 0, EzacianCraftBlocks.ezacianStoneDecorativeBlocks, 0, MODID);
        EzacianCraftResearches.recipes.put("AdvancedArcaneWorkbench", Arrays.asList(
                new AspectList()
                        .add(Aspect.FIRE, 75)
                        .add(Aspect.EARTH, 75)
                        .add(Aspect.ORDER, 75)
                        .add(Aspect.AIR, 75)
                        .add(Aspect.ENTROPY, 75)
                        .add(Aspect.WATER, 75)
                , 3, 1, 3,
                Arrays.asList(
                        new ItemStack(ConfigBlocks.blockCosmeticSolid, 1, 7), new ItemStack(ConfigBlocks.blockCosmeticSolid, 1, 6), new ItemStack(ConfigBlocks.blockCosmeticSolid, 1, 7), new ItemStack(ConfigBlocks.blockCosmeticSolid, 1, 6), new ItemStack(EzacianCraftBlocks.ezacianStoneDecorativeBlocks, 1, 0), new ItemStack(ConfigBlocks.blockCosmeticSolid, 1, 6), new ItemStack(ConfigBlocks.blockCosmeticSolid, 1, 7), new ItemStack(ConfigBlocks.blockCosmeticSolid, 1, 6), new ItemStack(ConfigBlocks.blockCosmeticSolid, 1, 7)
                )
        ));
    }
}
