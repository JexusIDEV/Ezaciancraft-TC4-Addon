package com.gabid.ezaciancraft.registry;

import com.gabid.ezaciancraft.common.items.vegetal.ItemAspectSeed;
import com.gabid.ezaciancraft.lib.research.ResearchUtils;
import nemexlib.api.thaumcraft.aspects.Aspects;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.crafting.CrucibleRecipe;
import thaumcraft.api.crafting.IArcaneRecipe;
import thaumcraft.api.crafting.InfusionRecipe;
import thaumcraft.api.crafting.ShapedArcaneRecipe;
import thaumcraft.api.research.ResearchCategories;
import thaumcraft.api.research.ResearchItem;
import thaumcraft.api.research.ResearchPage;
import thaumcraft.common.config.ConfigBlocks;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.gabid.ezaciancraft.CoreMod.MODID;
import static com.gabid.ezaciancraft.api.EzacianCraftGeneralLang.*;
import static com.gabid.ezaciancraft.registry.EzacianCraftAspects.REPLICATIO;
import static thaumcraft.api.aspects.Aspect.*;

public class EzacianCraftResearches {

    public static final String EZACIANCRAFT_CATEGORY_ID = "EZACIANCRAFT";

    public static final int taintResearchesColumnPos = 0;
    public static final int taintResearchesRowPos = 2;

    public static final int crystalyiumResearchesColumnPos = 3;
    public static final int crystalyiumResearchesRowPos = -2;

    public static final int shadowVoidMetalColumnPos = -3;
    public static final int shadowVoidMetalRowPos = 0;

    public static final int magicAlloyColumnPos = shadowVoidMetalColumnPos;
    public static final int magicAlloyRowPos = shadowVoidMetalRowPos - 4;

    public static HashMap<String, Object> recipes = new HashMap<>();

    public static void registerAllResearches() {
        setupResearchCategories();
        setupAllEzacianResearches();
    }

    private static void setupResearchCategories() {
        ResearchCategories.registerCategory(EZACIANCRAFT_CATEGORY_ID, new ResourceLocation(MODID, "textures/items/ezacianSymbol.png"), new ResourceLocation("thaumcraft", "textures/gui/gui_researchbackeldritch.png"));
    }

    private static void setupAllEzacianResearches() {
        ResearchCategories.addResearch((new ResearchItem("GETTING_STARTED",
                EZACIANCRAFT_CATEGORY_ID,
                new AspectList(),
                0, 0, 0,
                new ResourceLocation(MODID, "textures/items/ezacianSymbol.png")
        )).setPages(ResearchUtils.createVariousPageIndex("GETTING_STARTED", 2)).setStub().setRound().setAutoUnlock().registerResearchItem());
        ResearchCategories.addResearch((new ResearchItem("MAGIC_AT_PHYSIC_WORLD",
                EZACIANCRAFT_CATEGORY_ID,
                new AspectList(),
                0, -2, 0,
                new ResourceLocation("thaumcraft", "textures/misc/r_aspects.png")
        )).setPages(ResearchUtils.createVariousPageIndex("MAGIC_AT_PHYSIC_WORLD", 5)).setRound().setAutoUnlock().setParents("GETTING_STARTED").registerResearchItem());

        ResearchCategories.addResearch((new ResearchItem("DECORATIVE_BLOCKS",
                EZACIANCRAFT_CATEGORY_ID,
                new AspectList(),
                1, -2, 0,
                new ResourceLocation(MODID, "textures/items/ezacianSymbol.png")
        )).setPages(
                ResearchUtils.createPageTranslation("DECORATIVE_BLOCKS", 1),
                new ResearchPage((IArcaneRecipe) recipes.get(UNLOCALE_EZACIAN_STONE_DECORATIVE + "_0"))
        ).setStub().setRound().setAutoUnlock().setParents("GETTING_STARTED").registerResearchItem());

        ResearchCategories.addResearch((new ResearchItem("VOIDNESS_MAGIC_AND_DARKNESS_REVELATIONS",
                EZACIANCRAFT_CATEGORY_ID,
                new AspectList()
                        .add(MIND, 8)
                        .add(ELDRITCH, 8)
                        .add(DARKNESS, 8)
                        .add(VOID, 8)
                        .add(TAINT, 6)
                ,
                -1, -2, 1,
                new ResourceLocation("thaumcraft", "textures/misc/r_eldritch.png")
        )).setPages(
                ResearchUtils.createVariousPageIndex("VOIDNESS_MAGIC_AND_DARKNESS_REVELATIONS", 3)
        ).setConcealed().setSecondary().setParents("GETTING_STARTED", "ELDRITCHMAJOR").registerResearchItem());
        ThaumcraftApi.addWarpToResearch("VOIDNESS_MAGIC_AND_DARKNESS_REVELATIONS", 8);

        //crystaylium stuff
        ResearchCategories.addResearch((new ResearchItem("CRYSTALYIUM",
                EZACIANCRAFT_CATEGORY_ID,
                new AspectList()
                        .add(REPLICATIO, 1)
                        .add(EXCHANGE, 1)
                        .add(ENERGY, 1)
                        .add(MAGIC, 1)
                ,
                crystalyiumResearchesColumnPos, crystalyiumResearchesRowPos, 2,
                new ItemStack(EzacianCraftResources.crudeCrystalyiumResources.getResourceMetal(), 1, 0)
        )).setPages(
                ResearchUtils.createPageTranslation("CRYSTALYIUM", 1),
                new ResearchPage((CrucibleRecipe) recipes.get("crudeCrystalyiumCluster")),
                new ResearchPage(new ItemStack(EzacianCraftResources.crudeCrystalyiumResources.getResourceMetal(), 1, 2))
        ).setConcealed().setItemTriggers(new ItemStack(EzacianCraftResources.crudeCrystalyiumResources.getResourceOreBlock(), 1, 0)).setAspectTriggers(REPLICATIO).setSpecial().setParents("GETTING_STARTED").registerResearchItem());

        ItemAspectSeed dumItemSeed = (ItemAspectSeed) EzacianCraftItems.aspectSeeds;
        ItemStack dummySeed = new ItemStack(dumItemSeed, 1, 0);
        if(!dummySeed.hasTagCompound()) {
            dummySeed.setTagCompound(new NBTTagCompound());
        }
        dumItemSeed.setAspects(dummySeed, new AspectList().add(AIR, 1));

        ArrayList<CrucibleRecipe> seedRecipes = new ArrayList<>();

        for(Aspect asp : aspects.values()) {
            seedRecipes.add((CrucibleRecipe) recipes.get("aspectSeed_"+asp.getName()));
        }

        ResearchCategories.addResearch((new ResearchItem("ASPECT_SEEDS",
                EZACIANCRAFT_CATEGORY_ID,
                new AspectList()
                        .add(AURA, 2)
                        .add(PLANT, 4)
                        .add(MAGIC, 2)
                        .add(EXCHANGE, 1)
                ,
                crystalyiumResearchesColumnPos, crystalyiumResearchesRowPos-2, 0,
                dummySeed
        )).setPages(
                ResearchUtils.createPageTranslation("ASPECT_SEEDS", 1),
                new ResearchPage((CrucibleRecipe[]) seedRecipes.toArray(new CrucibleRecipe[0]))
        ).setParents("DISTILESSENTIA", "THAUMATORIUM").setSecondary().setItemTriggers(new ItemStack(Items.wheat_seeds), new ItemStack(Items.melon_seeds), new ItemStack(Items.pumpkin_seeds)).setAspectTriggers(PLANT).setConcealed().registerResearchItem());

        ResearchCategories.addResearch((new ResearchItem("ALCHEMICAL_MIXER",
                EZACIANCRAFT_CATEGORY_ID,
                new AspectList()
                        .add(WATER, 1)
                        .add(MOTION, 1)
                        .add(EXCHANGE, 1)
                        .add(CRAFT, 1)
                        .add(MAGIC, 1)
                ,
                3, 0, 2,
                new ItemStack(EzacianCraftBlocks.alchemicalMixer, 1, 0)
        )).setPages(
                ResearchUtils.createPageTranslation("ALCHEMICAL_MIXER", 1),
                new ResearchPage((InfusionRecipe) recipes.get(UNLOCALE_ALCHEMICAL_MIXER)),
                ResearchUtils.createPageTranslation("ALCHEMICAL_MIXER", 2),
                ResearchUtils.createPageTranslation("ALCHEMICAL_MIXER", 3)
        ).setParents("CENTRIFUGE").registerResearchItem());

        ResearchCategories.addResearch((new ResearchItem("ALCHEMICAL_MIXER",
                EZACIANCRAFT_CATEGORY_ID,
                new AspectList()
                        .add(WATER, 1)
                        .add(MOTION, 1)
                        .add(EXCHANGE, 1)
                        .add(CRAFT, 1)
                        .add(MAGIC, 1)
                ,
                3, 0, 2,
                new ItemStack(EzacianCraftBlocks.alchemicalMixer, 1, 0)
        )).setPages(
                ResearchUtils.createPageTranslation("ALCHEMICAL_MIXER", 1),
                new ResearchPage((InfusionRecipe) recipes.get(UNLOCALE_ALCHEMICAL_MIXER)),
                ResearchUtils.createPageTranslation("ALCHEMICAL_MIXER", 2),
                ResearchUtils.createPageTranslation("ALCHEMICAL_MIXER", 3)
        ).setParents("CENTRIFUGE", "INFUSION").registerResearchItem());

        ResearchCategories.addResearch((new ResearchItem("ADVANCED_ESSENTIA_STORAGE",
                EZACIANCRAFT_CATEGORY_ID,
                new AspectList()
                        .add(WATER, 1)
                        .add(MOTION, 1)
                        .add(EXCHANGE, 1)
                        .add(CRAFT, 1)
                        .add(MAGIC, 1)
                ,
                3, 2, 2,
                new ItemStack(EzacianCraftBlocks.alchemyBlockExpert, 1, 0)
        )).setPages(
                ResearchUtils.createPageTranslation("ADVANCED_ESSENTIA_STORAGE", 1),
                ResearchUtils.createPageTranslation("ADVANCED_ESSENTIA_STORAGE", 2),
                new ResearchPage((InfusionRecipe) recipes.get(UNLOCALE_ADVANCED_ESSENTIA_STORAGE)),
                ResearchUtils.createPageTranslation("ADVANCED_ESSENTIA_STORAGE", 3),
                new ResearchPage((List) recipes.get(UNLOCALE_ADVANCED_ESSENTIA_STORAGE+"Multi"))
        ).setParents("MAGIC_ALLOY", "ADVALCHEMYFURNACE", "INFUSION").registerResearchItem().setSpecial());

        ResearchCategories.addResearch((new ResearchItem("WIRELESS_INTERFACES",
                EZACIANCRAFT_CATEGORY_ID,
                new AspectList()
                        .add(WATER, 1)
                        .add(AIR, 4)
                        .add(TRAVEL, 2)
                        .add(AURA, 1)
                        .add(CRAFT, 2)
                        .add(TOOL, 2)
                ,
                5, 0, 2,
                new ItemStack(EzacianCraftBlocks.wirelessEssentiaInterface, 1, 0)
        )).setPages(
                ResearchUtils.createPageTranslation("WIRELESS_INTERFACES", 1),
                new ResearchPage((InfusionRecipe) recipes.get(UNLOCALE_WIRELESS_ESSENTIA_INTERFACE+"Out")),
                new ResearchPage((InfusionRecipe) recipes.get(UNLOCALE_WIRELESS_ESSENTIA_INTERFACE+"In")),
                ResearchUtils.createPageTranslation("WIRELESS_INTERFACES", 2),
                ResearchUtils.createPageTranslation("WIRELESS_INTERFACES", 3)
        ).setParents("THAUMATORIUM", "MIRRORESSENTIA").setConcealed().setSecondary().registerResearchItem());

        ResearchCategories.addResearch((new ResearchItem("SHADOW_ALCHEMY_FURNACE",
                EZACIANCRAFT_CATEGORY_ID,
                new AspectList()
                        .add(WATER, 1)
                        .add(EXCHANGE, 1)
                        .add(MAGIC, 1)
                        .add(FIRE, 1)
                        .add(MECHANISM, 1)
                        .add(ENERGY, 1)
                        .add(ELDRITCH, 1)
                ,
                7, 0, 2,
                new ItemStack(EzacianCraftBlocks.shadowAlchemyFurnace, 1, 0)
        )).setPages(
                ResearchUtils.createPageTranslation("SHADOW_ALCHEMY_FURNACE", 1),
                new ResearchPage((InfusionRecipe) recipes.get(UNLOCALE_SHADOW_ALCHEMY_FURNACE)),
                ResearchUtils.createPageTranslation("SHADOW_ALCHEMY_FURNACE", 2)
        ).setParents("SHADOW_VOID_METAL", "CRYSTALYIUM", "DISTILESSENTIA", "PRIMPEARL", "INFUSION").setConcealed().registerResearchItem());
        ThaumcraftApi.addWarpToResearch("SHADOW_ALCHEMY_FURNACE", 3);

        ResearchCategories.addResearch((new ResearchItem("ADVANCED_ARCANE_WORKBENCH",
                EZACIANCRAFT_CATEGORY_ID,
                new AspectList()
                        .add(MAGIC, 4)
                        .add(TOOL, 2)
                        .add(AURA, 6)
                        .add(CRAFT, 4)
                ,
                9, 0, 2,
                new ItemStack(EzacianCraftBlocks.ezacianStoneDecorativeBlocks, 1, 0)
        )).setPages(
                ResearchUtils.createPageTranslation("ADVANCED_ARCANE_WORKBENCH", 1),
                new ResearchPage((List) recipes.get("AdvancedArcaneWorkbench")),
                ResearchUtils.createPageTranslation("ADVANCED_ARCANE_WORKBENCH", 2)
        ).setConcealed().setSecondary().setParents("DECORATIVE_BLOCKS", "CRYSTALYIUM", "SHADOW_VOID_METAL", "ROD_greatwood_staff").registerResearchItem());

        ItemStack[] taintStacks = {
                new ItemStack(ConfigBlocks.blockTaint, 1, 0),
                new ItemStack(ConfigBlocks.blockTaint, 1, 1),
                new ItemStack(ConfigBlocks.blockTaintFibres, 1, 0),
                new ItemStack(ConfigBlocks.blockTaintFibres, 1, 1),
                new ItemStack(ConfigBlocks.blockTaintFibres, 1, 2),
                new ItemStack(ConfigBlocks.blockTaintFibres, 1, 3)
        };

        ResearchCategories.addResearch((new ResearchItem("TAINT_LANDS",
                EZACIANCRAFT_CATEGORY_ID,
                new AspectList()
                        .add(TAINT, 1)
                        .add(EARTH, 1)
                        .add(LIFE, 1)
                ,
                taintResearchesColumnPos, taintResearchesRowPos, 2,
                new ItemStack(ConfigBlocks.blockTaint, 1, 0)
        )).setPages(
                ResearchUtils.createVariousPageIndex("TAINT_LANDS", 2)
        ).setItemTriggers(taintStacks).setRound().setLost().registerResearchItem());
        ThaumcraftApi.addWarpToResearch("TAINT_LANDS", 2);

        ResearchCategories.addResearch((new ResearchItem("VOID_SEED_ORE_CONVERSION",
                EZACIANCRAFT_CATEGORY_ID,
                new AspectList()
                        .add(EARTH, 1)
                        .add(DARKNESS, 1)
                        .add(ELDRITCH, 1)
                        .add(VOID, 1)
                        .add(TAINT, 1)
                        .add(CRAFT, 1)
                ,
                taintResearchesColumnPos + 1, taintResearchesRowPos + 2, 2,
                new ItemStack(EzacianCraftBlocks.voidSeedOre, 1, 1)
        )).setPages(
                ResearchUtils.createPageTranslation("VOID_SEED_ORE_CONVERSION", 1),
                new ResearchPage((CrucibleRecipe) recipes.get("voidSeedOreConversionRecipe"))
        ).setItemTriggers(
                new ItemStack(EzacianCraftBlocks.voidSeedOre, 1, 0)
        ).setParents("ELDRITCHMINOR", "TAINT_LANDS").setConcealed().setSecondary().registerResearchItem());
        ThaumcraftApi.addWarpToResearch("VOID_SEED_ORE_CONVERSION", 1);

        ResearchCategories.addResearch((new ResearchItem("SHADOW_VOID_METAL_ORE",
                EZACIANCRAFT_CATEGORY_ID,
                new AspectList()
                        .add(EARTH, 1)
                        .add(DARKNESS, 1)
                        .add(ELDRITCH, 1)
                        .add(VOID, 1)
                        .add(TAINT, 1)
                        .add(ORDER, 1)
                ,
                taintResearchesColumnPos - 1, taintResearchesRowPos + 2, 2,
                new ItemStack(EzacianCraftResources.shadowVoidMetalResources.getResourceOreBlock(), 1, 0)
        )).setPages(
                ResearchUtils.createPageTranslation("SHADOW_VOID_METAL_ORE", 1),
                new ResearchPage((CrucibleRecipe) recipes.get("shadowVoidMetalCluster")),
                new ResearchPage(new ItemStack(EzacianCraftResources.shadowVoidMetalResources.getResourceMetal(), 1, 2))
        ).setItemTriggers(
                new ItemStack(EzacianCraftResources.shadowVoidMetalResources.getResourceOreBlock(), 1, 0)
        ).setParents("SHADOW_VOID_METAL", "TAINT_LANDS").setConcealed().setSecondary().registerResearchItem());
        ThaumcraftApi.addWarpToResearch("SHADOW_VOID_METAL_ORE", 1);

        ResearchCategories.addResearch((new ResearchItem("CRYSTALYIUM_JAR",
                EZACIANCRAFT_CATEGORY_ID,
                new AspectList()
                        .add(WATER, 1)
                        .add(ELDRITCH, 1)
                        .add(DARKNESS, 1)
                        .add(VOID, 1)
                        .add(TAINT, 1)
                        .add(MAGIC, 1)
                ,
                crystalyiumResearchesColumnPos + 2, crystalyiumResearchesRowPos, 3,
                new ItemStack(EzacianCraftBlocks.crystalyiumJar, 1, 0)
        )).setPages(
                ResearchUtils.createPageTranslation("CRYSTALYIUM_JAR", 1),
                new ResearchPage((InfusionRecipe) recipes.get(UNLOCALE_CRYSTALYIUM_JAR)),
                new ResearchPage((ShapedArcaneRecipe) recipes.get(UNLOCALE_CRYSTALYIUM_JAR + "Void"))
        ).setParents("JARLABEL", "JARVOID", "CRYSTALYIUM", "INFUSION", "WARDEDARCANA").setSecondary().setConcealed().registerResearchItem());

        //shadow void metal stuff
        ResearchCategories.addResearch((new ResearchItem("SHADOW_VOID_METAL",
                EZACIANCRAFT_CATEGORY_ID,
                new AspectList()
                        .add(ELDRITCH, 1)
                        .add(DARKNESS, 1)
                        .add(VOID, 1)
                        .add(TAINT, 1)
                        .add(MAGIC, 1)
                        .add(METAL, 1)
                ,
                shadowVoidMetalColumnPos, shadowVoidMetalRowPos, 3,
                new ItemStack(EzacianCraftResources.shadowVoidMetalResources.getResourceMetal(), 1, 0)
        )).setPages(
                ResearchUtils.createPageTranslation("SHADOW_VOID_METAL", 1),
                ResearchUtils.createPageTranslation("SHADOW_VOID_METAL", 2),
                new ResearchPage((CrucibleRecipe) recipes.get("shadowVoidMetalCauldron"))
        ).setParents("VOIDMETAL", "VOIDNESS_MAGIC_AND_DARKNESS_REVELATIONS").setSpecial().registerResearchItem());
        ThaumcraftApi.addWarpToResearch("SHADOW_VOID_METAL", 5);

        ResearchCategories.addResearch((new ResearchItem("SHADOW_VOID_METAL_JAR",
                EZACIANCRAFT_CATEGORY_ID,
                new AspectList()
                        .add(WATER, 1)
                        .add(ELDRITCH, 1)
                        .add(DARKNESS, 1)
                        .add(VOID, 1)
                        .add(TAINT, 1)
                        .add(MAGIC, 1)
                ,
                shadowVoidMetalColumnPos, shadowVoidMetalRowPos + 2, 3,
                new ItemStack(EzacianCraftBlocks.shadowVoidMetalJar, 1, 0)
        )).setPages(
                ResearchUtils.createPageTranslation("SHADOW_VOID_METAL_JAR", 1),
                new ResearchPage((InfusionRecipe) recipes.get(UNLOCALE_SHADOW_VOID_METAL_JAR)),
                new ResearchPage((ShapedArcaneRecipe) recipes.get(UNLOCALE_SHADOW_VOID_METAL_JAR + "Void"))
        ).setConcealed().setSecondary().setParents("SHADOW_VOID_METAL", "PRIMPEARL", "CRYSTALYIUM_JAR").registerResearchItem());
        ThaumcraftApi.addWarpToResearch("SHADOW_VOID_METAL_JAR", 1);

        ResearchCategories.addResearch((new ResearchItem("ELEMENTAL_VOID_TOOLS",
                EZACIANCRAFT_CATEGORY_ID,
                new AspectList()
                        .add(TOOL, 1)
                        .add(EARTH, 1)
                        .add(FIRE, 1)
                        .add(WATER, 1)
                        .add(ENTROPY, 1)
                        .add(LIFE, 1)
                        .add(ELDRITCH, 1)
                        .add(MINE, 1)
                        .add(CRAFT, 1)
                ,
                shadowVoidMetalColumnPos - 3, shadowVoidMetalRowPos - 1, 3,
                new ItemStack(EzacianCraftItems.voidCorePickaxe, 1, 0)
        )).setPages(
                ResearchUtils.createPageTranslation("ELEMENTAL_VOID_TOOLS", 1),
                new ResearchPage((InfusionRecipe) recipes.get(UNLOCALE_VOID_CORE_PICKAXE)),
                new ResearchPage((InfusionRecipe) recipes.get(UNLOCALE_VOID_STREAM_AXE)),
                new ResearchPage((InfusionRecipe) recipes.get(UNLOCALE_VOID_TERRA_SHATTER_SHOVEL)),
                new ResearchPage((InfusionRecipe) recipes.get(UNLOCALE_VOID_GROWTH_HOE)),
                ResearchUtils.createPageTranslation("ELEMENTAL_VOID_TOOLS", 2)
        ).setConcealed().setParents("ELEMENTALPICK", "ELEMENTALAXE", "ELEMENTALSHOVEL", "ELEMENTALHOE", "SHADOW_VOID_METAL", "PRIMPEARL").registerResearchItem());
        ThaumcraftApi.addWarpToResearch("ELEMENTAL_VOID_TOOLS", 3);

        ResearchCategories.addResearch((new ResearchItem("ELEMENTAL_VOID_SWORD",
                EZACIANCRAFT_CATEGORY_ID,
                new AspectList()
                        .add(WEAPON, 1)
                        .add(TAINT, 1)
                        .add(AURA, 1)
                        .add(ELDRITCH, 1)
                        .add(FLIGHT, 1)
                        .add(AIR, 1)
                        .add(DEATH, 1)
                ,
                shadowVoidMetalColumnPos - 3, shadowVoidMetalRowPos - 2, 3,
                new ItemStack(EzacianCraftItems.voidZephyrSword, 1, 0)
        )).setPages(
                ResearchUtils.createPageTranslation("ELEMENTAL_VOID_SWORD", 1),
                new ResearchPage((InfusionRecipe) recipes.get(UNLOCALE_VOID_ZEPHYR_SWORD))
        ).setConcealed().setParents("ELEMENTALSWORD", "SHADOW_VOID_METAL", "PRIMPEARL").registerResearchItem());
        ThaumcraftApi.addWarpToResearch("ELEMENTAL_VOID_SWORD", 3);

        ResearchCategories.addResearch((new ResearchItem("SHADOW_VOID_METAL_ARMOR_ROBES",
                EZACIANCRAFT_CATEGORY_ID,
                new AspectList()
                        .add(ARMOR, 8)
                        .add(MAGIC, 6)
                        .add(TAINT, 6)
                        .add(ELDRITCH, 4)
                        .add(VOID, 4)
                        .add(DARKNESS, 4)
                        .add(CLOTH, 8)
                ,
                shadowVoidMetalColumnPos - 3, shadowVoidMetalRowPos, 3,
                new ItemStack(EzacianCraftItems.shadowVoidMetalRobesArmorHelmet, 1, 0)
        )).setPages(
                ResearchUtils.createPageTranslation("SHADOW_VOID_METAL_ARMOR_ROBES", 1),
                new ResearchPage((InfusionRecipe) recipes.get(UNLOCALE_SHADOW_VOID_METAL_ROBES_ARMOR_HELMET)),
                new ResearchPage((InfusionRecipe) recipes.get(UNLOCALE_SHADOW_VOID_METAL_ROBES_ARMOR_CHESTPLATE)),
                new ResearchPage((InfusionRecipe) recipes.get(UNLOCALE_SHADOW_VOID_METAL_ROBES_ARMOR_LEGGINGS))
        ).setConcealed().setParents("ARMORVOIDFORTRESS", "SHADOW_VOID_METAL", "CRYSTALYIUM", "PRIMPEARL").setSecondary().registerResearchItem());
        ThaumcraftApi.addWarpToResearch("SHADOW_VOID_METAL_ARMOR_ROBES", 6);

        ResearchCategories.addResearch((new ResearchItem("PRIMAL_VOID_STAFF_OF_RECONSTRUCTION",
                EZACIANCRAFT_CATEGORY_ID,
                new AspectList()
                        .add(WEAPON, 1)
                        .add(TAINT, 1)
                        .add(ELDRITCH, 1)
                        .add(VOID, 1)
                        .add(DARKNESS, 1)
                        .add(TOOL, 1)
                ,
                shadowVoidMetalColumnPos - 6, shadowVoidMetalRowPos, 3,
                new ItemStack(EzacianCraftItems.voidStaffOfPrimalReconstructor, 1, 0)
        )).setPages(
                ResearchUtils.createPageTranslation("PRIMAL_VOID_STAFF_OF_RECONSTRUCTION", 1),
                new ResearchPage((InfusionRecipe) recipes.get(UNLOCALE_STAFF_OF_PRIMAL_RECONSTRUCTOR)),
                ResearchUtils.createPageTranslation("PRIMAL_VOID_STAFF_OF_RECONSTRUCTION", 2),
                ResearchUtils.createPageTranslation("PRIMAL_VOID_STAFF_OF_RECONSTRUCTION", 3),
                ResearchUtils.createPageTranslation("PRIMAL_VOID_STAFF_OF_RECONSTRUCTION", 4),
                ResearchUtils.createPageTranslation("PRIMAL_VOID_STAFF_OF_RECONSTRUCTION", 5)
        ).setConcealed().setParents("ELEMENTAL_VOID_TOOLS", "ELEMENTAL_VOID_SWORD", "CRYSTALYIUM", "ROD_advanced_primal_staff", "PRIMPEARL", "PRIMALCRUSHER").registerResearchItem());
        ThaumcraftApi.addWarpToResearch("PRIMAL_VOID_STAFF_OF_RECONSTRUCTION", 6);

        ResearchCategories.addResearch((new ResearchItem("CAP_shadow_void_metal",
                EZACIANCRAFT_CATEGORY_ID,
                new AspectList()
                        .add(AURA, 1)
                        .add(TOOL, 1)
                        .add(MAGIC, 1)
                        .add(ELDRITCH, 1)
                        .add(DARKNESS, 1)
                        .add(VOID, 1)
                        .add(TAINT, 1)
                ,
                shadowVoidMetalColumnPos - 3, shadowVoidMetalRowPos + 1, 3,
                new ItemStack(EzacianCraftItems.shadowVoidMetalCap, 1, 0)
        )).setPages(
                ResearchUtils.createPageTranslationNoUpper("CAP_shadow_void_metal", 1),
                new ResearchPage((InfusionRecipe) recipes.get("shadowVoidMetalCap")),
                new ResearchPage((InfusionRecipe) recipes.get("shadowVoidMetalCapCharged"))
        ).setConcealed().setParents("SHADOW_VOID_METAL", "CAP_void").registerResearchItem());
        ThaumcraftApi.addWarpToResearch("CAP_shadow_void_metal", 2);

        ResearchCategories.addResearch((new ResearchItem("ROD_advanced_primal",
                EZACIANCRAFT_CATEGORY_ID,
                new AspectList()
                        .add(ORDER, 4)
                        .add(ENTROPY, 4)
                        .add(AIR, 4)
                        .add(FIRE, 4)
                        .add(WATER, 4)
                        .add(EARTH, 4)
                        .add(MAGIC, 4)
                        .add(TOOL, 4)
                        .add(TAINT, 2)
                ,
                shadowVoidMetalColumnPos - 3, shadowVoidMetalRowPos + 2, 3,
                new ItemStack(EzacianCraftItems.advancedPrimalWandStaffRod, 1, 0)
        )).setPages(
                ResearchUtils.createPageTranslationNoUpper("ROD_advanced_primal", 1),
                ResearchUtils.createPageTranslationNoUpper("ROD_advanced_primal", 2),
                new ResearchPage((InfusionRecipe) recipes.get("advancedPrimalWand"))
        ).setConcealed().setParents("SHADOW_VOID_METAL", "PRIMPEARL", "ROD_primal_staff").registerResearchItem());
        ThaumcraftApi.addWarpToResearch("ROD_advanced_primal", 4);

        ResearchCategories.addResearch((new ResearchItem("ROD_advanced_primal_staff",
                EZACIANCRAFT_CATEGORY_ID,
                new AspectList()
                        .add(AURA, 4)
                        .add(MAGIC, 4)
                        .add(TOOL, 2)
                ,
                shadowVoidMetalColumnPos - 4, shadowVoidMetalRowPos + 2, 3,
                new ItemStack(EzacianCraftItems.advancedPrimalWandStaffRod, 1, 1)
        )).setPages(
                ResearchUtils.createPageTranslationNoUpper("ROD_advanced_primal_staff", 1),
                new ResearchPage((InfusionRecipe) recipes.get("advancedPrimalStaff"))
        ).setConcealed().setSecondary().setParents("ROD_advanced_primal").registerResearchItem());
        ThaumcraftApi.addWarpToResearch("ROD_advanced_primal_staff", 6);

        //*MAGIC ALLOY
        ResearchCategories.addResearch((new ResearchItem("MAGIC_ALLOY",
                EZACIANCRAFT_CATEGORY_ID,
                new AspectList()
                        .add(MAGIC, 1)
                        .add(AURA, 1)
                        .add(ELDRITCH, 1)
                        .add(CRAFT, 1)
                        .add(METAL, 1)
                ,
                magicAlloyColumnPos, magicAlloyRowPos, 2,
                new ItemStack(EzacianCraftItems.ezacianPlates, 1, 0)
        )).setPages(
                ResearchUtils.createPageTranslation("MAGIC_ALLOY", 1),
                new ResearchPage((ShapedArcaneRecipe) recipes.get(UNLOCALE_MAGIC_ALLOY))
        ).setParents("SHADOW_VOID_METAL", "PRIMPEARL").setConcealed().setSecondary().setSpecial().registerResearchItem());
        ThaumcraftApi.addWarpToResearch("MAGIC_ALLOY", 1);

        ResearchCategories.addResearch((new ResearchItem("CAP_magic_alloy",
                EZACIANCRAFT_CATEGORY_ID,
                new AspectList()
                        .add(AURA, 1)
                        .add(TOOL, 1)
                        .add(MAGIC, 1)
                        .add(TAINT, 1)
                        .add(METAL, 1)
                ,
                magicAlloyColumnPos - 3, magicAlloyRowPos, 2,
                new ItemStack(EzacianCraftItems.magicAlloyCap, 1, 0)
        )).setPages(
                ResearchUtils.createPageTranslationNoUpper("CAP_magic_alloy", 1),
                new ResearchPage((ShapedArcaneRecipe) recipes.get("magicAlloyCap")),
                new ResearchPage((InfusionRecipe) recipes.get("magicAlloyCapCharged"))
        ).setConcealed().setParents("MAGIC_ALLOY", "CRYSTALYIUM", "CAP_shadow_void_metal").registerResearchItem());
        ThaumcraftApi.addWarpToResearch("CAP_magic_alloy", 2);

        ResearchCategories.addResearch((new ResearchItem("MAGIC_ALLOY_TRAVELLER_BOOTS",
                EZACIANCRAFT_CATEGORY_ID,
                new AspectList()
                        .add(ARMOR, 4)
                        .add(MAGIC, 2)
                        .add(AIR, 2)
                        .add(MOTION, 4)
                        .add(TRAVEL, 4)
                        .add(FLIGHT, 1)
                ,
                magicAlloyColumnPos - 3, magicAlloyRowPos - 3, 1,
                new ItemStack(EzacianCraftItems.magicAlloyTravellerBoots, 1, 0)
        )).setPages(
                ResearchUtils.createPageTranslation("MAGIC_ALLOY_TRAVELLER_BOOTS", 1),
                new ResearchPage((InfusionRecipe) recipes.get(UNLOCALE_MAGIC_ALLOY_TRAVELLER_BOOTS))
        ).setParents("MAGIC_ALLOY", "BOOTSTRAVELLER").setConcealed().setSecondary().registerResearchItem());

        ResearchCategories.addResearch((new ResearchItem("MAGIC_ALLOY_FORTRESS_ARMOR",
                EZACIANCRAFT_CATEGORY_ID,
                new AspectList()
                        .add(ARMOR, 8)
                        .add(MAGIC, 4)
                        .add(TAINT, 2)
                        .add(ELDRITCH, 4)
                        .add(METAL, 8)
                ,
                magicAlloyColumnPos - 3, magicAlloyRowPos - 2, 1,
                new ItemStack(EzacianCraftItems.magicAlloyFortressArmorHelmet, 1, 0)
        )).setPages(
                ResearchUtils.createPageTranslation("MAGIC_ALLOY_FORTRESS_ARMOR", 1),
                new ResearchPage((InfusionRecipe) recipes.get(UNLOCALE_MAGIC_ALLOY_FORTRESS_ARMOR_HELMET)),
                new ResearchPage((InfusionRecipe) recipes.get(UNLOCALE_MAGIC_ALLOY_FORTRESS_ARMOR_CHESTPLATE)),
                new ResearchPage((InfusionRecipe) recipes.get(UNLOCALE_MAGIC_ALLOY_FORTRESS_ARMOR_LEGGINGS))
        ).setParents("MAGIC_ALLOY", "ARMORFORTRESS").setConcealed().setSecondary().registerResearchItem());

        ResearchCategories.addResearch((new ResearchItem("MAGIC_ALLOY_JAR",
                EZACIANCRAFT_CATEGORY_ID,
                new AspectList()
                        .add(WATER, 1)
                        .add(EXCHANGE, 1)
                        .add(MAGIC, 1)
                        .add(ORDER, 1)
                        .add(AURA, 1)
                        .add(VOID, 1)
                ,
                magicAlloyColumnPos - 3, magicAlloyRowPos - 4, 3,
                new ItemStack(EzacianCraftBlocks.magicAlloyJar, 1, 0)
        )).setPages(
                ResearchUtils.createPageTranslation("MAGIC_ALLOY_JAR", 1),
                new ResearchPage((InfusionRecipe) recipes.get(UNLOCALE_MAGIC_ALLOY_JAR)),
                new ResearchPage((ShapedArcaneRecipe) recipes.get(UNLOCALE_MAGIC_ALLOY_JAR+"Void"))
        ).setParents("MAGIC_ALLOY", "SHADOW_VOID_METAL_JAR").setConcealed().registerResearchItem());
    }
}
