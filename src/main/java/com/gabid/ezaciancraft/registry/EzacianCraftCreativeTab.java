package com.gabid.ezaciancraft.registry;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagByte;
import thaumcraft.api.wands.StaffRod;
import thaumcraft.common.config.ConfigItems;
import thaumcraft.common.items.wands.ItemWandCasting;

import java.util.List;

import static com.gabid.ezaciancraft.api.EzacianCraftGeneralLang.*;
import static com.gabid.ezaciancraft.registry.EzacianCraftItems.*;

public class EzacianCraftCreativeTab {
    public static final CreativeTabs EZACIANCRAFT_TAB = new CreativeTabs(CreativeTabs.creativeTabArray.length, UNLOCALE_EZACIANCRAFT_CREATIVE_TAB) {
        @SideOnly(Side.CLIENT)
        public Item getTabIconItem()
        {
            return ezaciancraftTabIconItem;
        }

        @Override
        public void displayAllReleventItems(List list) {
            ItemStack wand = new ItemStack(ConfigItems.itemWandCasting, 1, 50);
            ((ItemWandCasting)wand.getItem()).setCap(wand, WAND_CAP_SHADOW_VOID_METAL);
            ((ItemWandCasting)wand.getItem()).setRod(wand, WAND_ROD_ADVANCED_PRIMAL);
            list.add(wand);

            ItemStack wandScepter = new ItemStack(ConfigItems.itemWandCasting, 1, 75);
            ((ItemWandCasting)wand.getItem()).setCap(wandScepter, WAND_CAP_SHADOW_VOID_METAL);
            ((ItemWandCasting)wand.getItem()).setRod(wandScepter, WAND_ROD_ADVANCED_PRIMAL);
            wandScepter.setTagInfo("sceptre",  new NBTTagByte((byte)1));
            list.add(wandScepter);

            ItemStack staff = new ItemStack(ConfigItems.itemWandCasting, 1, 50);
            ((ItemWandCasting)wand.getItem()).setCap(staff, WAND_CAP_SHADOW_VOID_METAL);
            ((ItemWandCasting)wand.getItem()).setRod(staff, STAFF_ROD_ADVANCED_PRIMAL);
            list.add(staff);

            ItemStack staffScepter = new ItemStack(ConfigItems.itemWandCasting, 1, 75);
            ((ItemWandCasting)wand.getItem()).setCap(staffScepter, WAND_CAP_SHADOW_VOID_METAL);
            ((ItemWandCasting)wand.getItem()).setRod(staffScepter, STAFF_ROD_ADVANCED_PRIMAL);
            staffScepter.setTagInfo("sceptre",  new NBTTagByte((byte)1));
            list.add(staffScepter);

            super.displayAllReleventItems(list);
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
