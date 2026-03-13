package com.gabid.ezaciancraft.common.blocks;

import com.gabid.ezaciancraft.api.common.blocks.EzacianCustomBlockJar;
import com.gabid.ezaciancraft.api.common.blocks.tileentity.EzacianCustomJarFillableTE;
import com.gabid.ezaciancraft.registry.EzacianCraftItems;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;

import static com.gabid.ezaciancraft.CoreMod.MODID;
import static com.gabid.ezaciancraft.api.EzacianCraftGeneralLang.UNLOCALE_CRYSTALYIUM_JAR;
import static com.gabid.ezaciancraft.registry.EzacianCraftCreativeTab.EZACIANCRAFT_TAB;

public class BlockCrystalyiumJar extends EzacianCustomBlockJar {

    public BlockCrystalyiumJar() {
        super(256, "crystalyium", MODID, EZACIANCRAFT_TAB);
        this.setBlockName(UNLOCALE_CRYSTALYIUM_JAR);
    }

    @Override
    public ArrayList<ItemStack> setupDrops(EzacianCustomJarFillableTE jarTE, ArrayList<ItemStack> drops, Item filledJar) {
        return super.setupDrops(jarTE, drops, EzacianCraftItems.itemFilledJarCrystalyium);
    }
}
