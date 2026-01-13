package com.gabid.ezaciancraft.registry;

import net.minecraft.item.ItemStack;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;

import static thaumcraft.api.aspects.Aspect.*;

public class EzacianCraftAspectTagRegistry {

    public static void initObjectAspects() {
        //void seed ore
        ThaumcraftApi.registerObjectTag(new ItemStack(EzacianCraftBlocks.voidSeedOre, 1, 0), new AspectList()
                .add(EARTH, 1)
                .add(TAINT, 1)
                .add(ELDRITCH, 1)
                .add(DARKNESS, 1)
                .add(VOID, 1)
        );
        //shadow void
        ThaumcraftApi.registerObjectTag(new ItemStack(EzacianCraftResources.shadowVoidMetalResources.getResourceMetal(), 1, 0), new AspectList()
                        .add(TAINT, 3)
                        .add(MAGIC, 3)
                        .add(METAL, 2)
                        .add(DARKNESS, 2)
                        .add(ELDRITCH, 1)
                        .add(VOID, 1)
                );
        ThaumcraftApi.registerObjectTag(new ItemStack(EzacianCraftResources.shadowVoidMetalResources.getResourceBlock(), 1, 0), new AspectList()
                .add(TAINT, 3*9)
                .add(MAGIC, 3*9)
                .add(METAL, 2*9)
                .add(DARKNESS, 2*9)
                .add(ELDRITCH, 1*9)
                .add(VOID, 1*9)
        );

    }
}
