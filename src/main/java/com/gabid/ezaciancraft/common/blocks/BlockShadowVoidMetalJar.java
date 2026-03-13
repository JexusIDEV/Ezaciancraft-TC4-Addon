package com.gabid.ezaciancraft.common.blocks;

import com.gabid.ezaciancraft.api.common.blocks.EzacianCustomBlockJar;
import com.gabid.ezaciancraft.api.common.blocks.tileentity.EzacianCustomJarFillableTE;
import com.gabid.ezaciancraft.registry.EzacianCraftItems;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;

import static com.gabid.ezaciancraft.CoreMod.MODID;
import static com.gabid.ezaciancraft.api.EzacianCraftGeneralLang.UNLOCALE_CRYSTALYIUM_JAR;
import static com.gabid.ezaciancraft.api.EzacianCraftGeneralLang.UNLOCALE_SHADOW_VOID_METAL_JAR;
import static com.gabid.ezaciancraft.registry.EzacianCraftCreativeTab.EZACIANCRAFT_TAB;

public class BlockShadowVoidMetalJar extends EzacianCustomBlockJar {

    public BlockShadowVoidMetalJar() {
        super(1024, "shadowVoidMetal", MODID, EZACIANCRAFT_TAB);
        this.setBlockName(UNLOCALE_SHADOW_VOID_METAL_JAR);
    }

    @Override
    public ArrayList<ItemStack> setupDrops(EzacianCustomJarFillableTE jarTE, ArrayList<ItemStack> drops, Item filledJar) {
        return super.setupDrops(jarTE, drops, EzacianCraftItems.itemFilledJarShadowVoidMetal);
    }
}