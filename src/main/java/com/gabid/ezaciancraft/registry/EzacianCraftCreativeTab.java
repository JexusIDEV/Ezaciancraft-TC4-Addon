package com.gabid.ezaciancraft.registry;

import com.gabid.ezaciancraft.api.EzacianCraftGeneralLang;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;

import static com.gabid.ezaciancraft.api.EzacianCraftGeneralLang.UNLOCALE_EZACIANCRAFT_CREATIVE_TAB;
import static com.gabid.ezaciancraft.api.EzacianCraftGeneralLang.UNLOCALE_EZACIANCRAFT_RESOURCES_CREATIVE_TAB;
import static com.gabid.ezaciancraft.registry.EzacianCraftItems.ezaciancraftTabIconItem;

public class EzacianCraftCreativeTab {
    public static final CreativeTabs EZACIANCRAFT_TAB = new CreativeTabs(CreativeTabs.creativeTabArray.length, UNLOCALE_EZACIANCRAFT_CREATIVE_TAB) {
        @SideOnly(Side.CLIENT)
        public Item getTabIconItem()
        {
            return ezaciancraftTabIconItem;
        }
    };

    public static final CreativeTabs EZACIANCRAFT_RESOURCES_TAB = new CreativeTabs(CreativeTabs.creativeTabArray.length, UNLOCALE_EZACIANCRAFT_RESOURCES_CREATIVE_TAB) {
        @SideOnly(Side.CLIENT)
        public Item getTabIconItem()
        {
            return ezaciancraftTabIconItem;
        }
    };
}
