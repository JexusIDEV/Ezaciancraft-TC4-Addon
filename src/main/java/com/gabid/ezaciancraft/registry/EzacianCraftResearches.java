package com.gabid.ezaciancraft.registry;

import com.gabid.ezaciancraft.lib.research.ResearchUtils;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.crafting.CrucibleRecipe;
import thaumcraft.api.crafting.InfusionRecipe;
import thaumcraft.api.research.ResearchCategories;
import thaumcraft.api.research.ResearchItem;
import thaumcraft.api.research.ResearchPage;
import thaumcraft.common.config.ConfigBlocks;

import java.util.HashMap;

import static com.gabid.ezaciancraft.CoreMod.MODID;
import static com.gabid.ezaciancraft.api.EzacianCraftGeneralLang.*;
import static thaumcraft.api.aspects.Aspect.*;

public class EzacianCraftResearches {

    public static final String EZACIANCRAFT_CATEGORY_ID = "EZACIANCRAFT";

    public static final int taintResearchesColumnPos = 0;
    public static final int taintResearchesRowPos = 2;

    public static final int shadowVoidMetalColumnPos = -3;
    public static final int shadowVoidMetalRowPos = 0;

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
        )).setPages(ResearchUtils.createVariousPageIndex("MAGIC_AT_PHYSIC_WORLD", 5)).setRound().setAutoUnlock().setParents(new String[]{"GETTING_STARTED"}).registerResearchItem());
        ResearchCategories.addResearch((new ResearchItem("VOIDNESS_MAGIC_AND_DARKNESS_REVELATIONS",
                EZACIANCRAFT_CATEGORY_ID,
                new AspectList()
                        .add(MIND,8)
                        .add(ELDRITCH,8)
                        .add(DARKNESS,8)
                        .add(VOID,8)
                        .add(TAINT,6)
                ,
                -1, -2, 1,
                new ResourceLocation("thaumcraft", "textures/misc/r_eldritch.png")
        )).setPages(
                ResearchUtils.createVariousPageIndex("VOIDNESS_MAGIC_AND_DARKNESS_REVELATIONS", 3)
        ).setConcealed().setSecondary().setParents("GETTING_STARTED", "ELDRITCHMAJOR").registerResearchItem());
        ThaumcraftApi.addWarpToResearch("VOIDNESS_MAGIC_AND_DARKNESS_REVELATIONS", 8);

        //currently in placholder
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
                new ItemStack(EzacianCraftBlocks.alchemicalMixer,1 , 0)
        )).setPages(
                ResearchUtils.createPageTranslation("ALCHEMICAL_MIXER", 1),
                new ResearchPage((InfusionRecipe) recipes.get(UNLOCALE_ALCHEMICAL_MIXER)),
                ResearchUtils.createPageTranslation("ALCHEMICAL_MIXER", 2),
                ResearchUtils.createPageTranslation("ALCHEMICAL_MIXER", 3)
        ).setParents("CENTRIFUGE").registerResearchItem());

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
        ).setHidden().setRound().setAspectTriggers(TAINT).registerResearchItem());
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
                taintResearchesColumnPos, taintResearchesRowPos+2, 0,
                new ItemStack(EzacianCraftBlocks.voidSeedOre, 1, 1)
        )).setPages(
                ResearchUtils.createPageTranslation("VOID_SEED_ORE_CONVERSION", 1),
                new ResearchPage((CrucibleRecipe) recipes.get("voidSeedOreConversionRecipe"))
        ).setConcealed().setParents("ELDRITCHMINOR", "TAINT_LANDS").setItemTriggers(
                new ItemStack(EzacianCraftBlocks.voidSeedOre, 1, 0)
        ).registerResearchItem());
        ThaumcraftApi.addWarpToResearch("VOID_SEED_ORE_CONVERSION", 1);

        ResearchCategories.addResearch((new ResearchItem("SHADOW_VOID_METAL",
                EZACIANCRAFT_CATEGORY_ID,
                new AspectList()
                        .add(ELDRITCH, 1)
                        .add(DARKNESS, 1)
                        .add(VOID, 1)
                        .add(TAINT, 1)
                        .add(MAGIC, 1)
                ,
                shadowVoidMetalColumnPos, shadowVoidMetalRowPos, 2,
                new ItemStack(EzacianCraftResources.shadowVoidMetalResources.getResourceMetal(), 1, 0)
        )).setPages(
                ResearchUtils.createPageTranslation("SHADOW_VOID_METAL", 1),
                ResearchUtils.createPageTranslation("SHADOW_VOID_METAL", 2),
                new ResearchPage((CrucibleRecipe) recipes.get("shadowVoidMetalCauldron"))
        ).setParents("VOIDMETAL", "VOIDNESS_MAGIC_AND_DARKNESS_REVELATIONS").registerResearchItem());
        ThaumcraftApi.addWarpToResearch("SHADOW_VOID_METAL", 5);

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
                shadowVoidMetalColumnPos-2, shadowVoidMetalRowPos-2, 3,
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
                        .add(DEATH, 1)
                ,
                shadowVoidMetalColumnPos, shadowVoidMetalRowPos-2, 3,
                new ItemStack(EzacianCraftItems.voidZephyrSword, 1, 0)
        )).setPages(
                ResearchUtils.createPageTranslation("ELEMENTAL_VOID_SWORD", 1)//,
                //new ResearchPage((InfusionRecipe) recipes.get(UNLOCALE_VOID_ZEPHYR_SWORD))
        ).setConcealed().setParents("ELEMENTALSWORD", "SHADOW_VOID_METAL", "PRIMPEARL").registerResearchItem());
        ThaumcraftApi.addWarpToResearch("ELEMENTAL_VOID_SWORD", 3);

        ResearchCategories.addResearch((new ResearchItem("CAP_shadow_void_metal",
                EZACIANCRAFT_CATEGORY_ID,
                new AspectList()
                        .add(AURA, 1)
                        .add(TOOL, 1)
                        .add(MAGIC, 1)
                        .add(ELDRITCH,1)
                        .add(DARKNESS,1)
                        .add(VOID,1)
                        .add(TAINT,1)
                ,
                shadowVoidMetalColumnPos-2, shadowVoidMetalRowPos, 3,
                EzacianCraftItems.WAND_CAP_SHADOW_VOID_METAL.getItem()
        )).setPages(
                ResearchUtils.createPageTranslation("CAP_shadow_void_metal", 1)
        ).setConcealed().setParents("SHADOW_VOID_METAL", "CAP_void").registerResearchItem());
        ThaumcraftApi.addWarpToResearch("CAP_shadow_void_metal", 2);
    }
}
