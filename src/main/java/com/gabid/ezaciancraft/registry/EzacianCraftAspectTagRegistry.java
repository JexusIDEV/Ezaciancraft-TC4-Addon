package com.gabid.ezaciancraft.registry;

import static com.gabid.ezaciancraft.registry.EzacianCraftAspects.REPLICATIO;
import static thaumcraft.api.aspects.Aspect.DARKNESS;
import static thaumcraft.api.aspects.Aspect.EARTH;
import static thaumcraft.api.aspects.Aspect.ELDRITCH;
import static thaumcraft.api.aspects.Aspect.ENTROPY;
import static thaumcraft.api.aspects.Aspect.EXCHANGE;
import static thaumcraft.api.aspects.Aspect.MAGIC;
import static thaumcraft.api.aspects.Aspect.METAL;
import static thaumcraft.api.aspects.Aspect.ORDER;
import static thaumcraft.api.aspects.Aspect.PLANT;
import static thaumcraft.api.aspects.Aspect.TAINT;
import static thaumcraft.api.aspects.Aspect.VOID;

import net.minecraft.item.ItemStack;

import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.aspects.AspectList;

/// class which set the aspects of blocks or items what doesn't have their aspects by the research system.
public class EzacianCraftAspectTagRegistry {

    public static void postInitAddExtraTagObjects() {
        // primordial pearl with primordium
        // AspectHelper.addNewAspectTagsToObject(ConfigItems.itemEldritchObject, 3, new AspectList().add(PRIMORDIUM,
        // 16));
    }

    public static void initObjectAspects() {
        // void seed ore
        ThaumcraftApi.registerObjectTag(
            new ItemStack(EzacianCraftBlocks.voidSeedOre, 1, 0),
            new AspectList().add(EARTH, 1)
                .add(TAINT, 1)
                .add(ELDRITCH, 1)
                .add(DARKNESS, 1)
                .add(VOID, 1));

        // shadow void
        ThaumcraftApi.registerObjectTag(
            new ItemStack(EzacianCraftResources.shadowVoidMetalResources.getResourceOreBlock(), 1, 0),
            new AspectList().add(TAINT, 3)
                .add(MAGIC, 3)
                .add(METAL, 2)
                .add(DARKNESS, 2)
                .add(ELDRITCH, 1)
                .add(VOID, 1)
                .add(EARTH, 3));
        ThaumcraftApi.registerObjectTag(
            new ItemStack(EzacianCraftResources.shadowVoidMetalResources.getResourceMetal(), 1, 0),
            new AspectList().add(TAINT, 3)
                .add(MAGIC, 3)
                .add(METAL, 2)
                .add(DARKNESS, 2)
                .add(ELDRITCH, 1)
                .add(VOID, 1));
        ThaumcraftApi.registerObjectTag(
            new ItemStack(EzacianCraftResources.shadowVoidMetalResources.getResourceMetal(), 1, 2),
            new AspectList().add(TAINT, 3)
                .add(MAGIC, 3)
                .add(METAL, 6)
                .add(ORDER, 2)
                .add(DARKNESS, 2)
                .add(ELDRITCH, 1)
                .add(VOID, 1));
        ThaumcraftApi.registerObjectTag(
            new ItemStack(EzacianCraftResources.shadowVoidMetalResources.getResourceBlock(), 1, 0),
            new AspectList().add(TAINT, 3 * 9)
                .add(MAGIC, 3 * 9)
                .add(METAL, 2 * 9)
                .add(DARKNESS, 2 * 9)
                .add(ELDRITCH, 9)
                .add(VOID, 9));

        // CRYSTALYIUM
        ThaumcraftApi.registerObjectTag(
            new ItemStack(EzacianCraftResources.crudeCrystalyiumResources.getResourceOreBlock(), 1, 0),
            new AspectList().add(REPLICATIO, 4)
                .add(MAGIC, 4)
                .add(ENTROPY, 4)
                .add(METAL, 1)
                .add(EXCHANGE, 2 * 9)
                .add(EARTH, 3));
        ThaumcraftApi.registerObjectTag(
            new ItemStack(EzacianCraftResources.crudeCrystalyiumResources.getResourceMetal(), 1, 0),
            new AspectList().add(REPLICATIO, 4)
                .add(MAGIC, 4)
                .add(ENTROPY, 4)
                .add(METAL, 1)
                .add(EXCHANGE, 2));
        ThaumcraftApi.registerObjectTag(
            new ItemStack(EzacianCraftResources.crudeCrystalyiumResources.getResourceMetal(), 1, 2),
            new AspectList().add(REPLICATIO, 4)
                .add(MAGIC, 4)
                .add(ENTROPY, 4)
                .add(METAL, 4)
                .add(ORDER, 2)
                .add(EXCHANGE, 2));
        ThaumcraftApi.registerObjectTag(
            new ItemStack(EzacianCraftResources.crudeCrystalyiumResources.getResourceBlock(), 1, 0),
            new AspectList().add(REPLICATIO, 4 * 9)
                .add(EXCHANGE, 2 * 9)
                .add(MAGIC, 4 * 9)
                .add(ENTROPY, 4 * 9)
                .add(METAL, 9));

        ThaumcraftApi
            .registerObjectTag(new ItemStack(EzacianCraftItems.aspectSeeds, 1, 0), new AspectList().add(PLANT, 1));
    }

    /*
     * public static void addNewAspectTagsToObject(Item item, int meta, AspectList aspectList) {
     * if (ThaumcraftApi.exists(item, meta) && aspectList != null) {
     * AspectList oldAsp = ThaumcraftApi.objectTags.get(Arrays.asList(item, meta));
     * ThaumcraftApi.objectTags.remove(Arrays.asList(item, meta));
     * AspectList newAsp = oldAsp.copy();
     * newAsp.add(aspectList);
     * ThaumcraftApi.objectTags.put(Arrays.asList(item, meta), newAsp);
     * }
     * }
     */
}
