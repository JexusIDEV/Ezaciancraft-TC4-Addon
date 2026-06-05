package com.gabid.ezaciancraft.registry;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagByte;
import thaumcraft.common.config.ConfigItems;
import thaumcraft.common.items.wands.ItemWandCasting;

import java.util.List;

import static com.gabid.ezaciancraft.api.EzacianCraftGeneralLang.*;
import static com.gabid.ezaciancraft.registry.EzacianCraftItems.*;

public class EzacianCraftCreativeTab {
    public static final CreativeTabs EZACIANCRAFT_TAB = new CreativeTabs(CreativeTabs.creativeTabArray.length, UNLOCALE_EZACIANCRAFT_CREATIVE_TAB) {
        @SideOnly(Side.CLIENT)
        public Item getTabIconItem() {
            return ezaciancraftTabIconItem;
        }

        @SuppressWarnings("unchecked")
        @Override
        public void displayAllReleventItems(List list) {
            //shadow void metal
            ItemStack wand = new ItemStack(ConfigItems.itemWandCasting, 1, 50);
            ((ItemWandCasting) wand.getItem()).setCap(wand, shadowVoidMetalCap.cap);
            ((ItemWandCasting) wand.getItem()).setRod(wand, advancedPrimalWandStaffRod.rodWand);
            list.add(wand);

            ItemStack wandScepter = new ItemStack(ConfigItems.itemWandCasting, 1, 75);
            ((ItemWandCasting) wand.getItem()).setCap(wandScepter, shadowVoidMetalCap.cap);
            ((ItemWandCasting) wand.getItem()).setRod(wandScepter, advancedPrimalWandStaffRod.rodWand);
            wandScepter.setTagInfo("sceptre", new NBTTagByte((byte) 1));
            list.add(wandScepter);

            ItemStack staff = new ItemStack(ConfigItems.itemWandCasting, 1, 50);
            ((ItemWandCasting) wand.getItem()).setCap(staff, shadowVoidMetalCap.cap);
            ((ItemWandCasting) wand.getItem()).setRod(staff, advancedPrimalWandStaffRod.staffWand);
            list.add(staff);

            ItemStack staffScepter = new ItemStack(ConfigItems.itemWandCasting, 1, 75);
            ((ItemWandCasting) wand.getItem()).setCap(staffScepter, shadowVoidMetalCap.cap);
            ((ItemWandCasting) wand.getItem()).setRod(staffScepter, advancedPrimalWandStaffRod.staffWand);
            staffScepter.setTagInfo("sceptre", new NBTTagByte((byte) 1));
            list.add(staffScepter);

            //magic alloy
            ItemStack wandM = new ItemStack(ConfigItems.itemWandCasting, 1, 50);
            ((ItemWandCasting) wandM.getItem()).setCap(wandM, magicAlloyCap.cap);
            ((ItemWandCasting) wandM.getItem()).setRod(wandM, advancedPrimalWandStaffRod.rodWand);
            list.add(wandM);

            ItemStack wandScepterM = new ItemStack(ConfigItems.itemWandCasting, 1, 75);
            ((ItemWandCasting) wandM.getItem()).setCap(wandScepterM, magicAlloyCap.cap);
            ((ItemWandCasting) wandM.getItem()).setRod(wandScepterM, advancedPrimalWandStaffRod.rodWand);
            wandScepterM.setTagInfo("sceptre", new NBTTagByte((byte) 1));
            list.add(wandScepterM);

            ItemStack staffM = new ItemStack(ConfigItems.itemWandCasting, 1, 50);
            ((ItemWandCasting) wandM.getItem()).setCap(staffM, magicAlloyCap.cap);
            ((ItemWandCasting) wandM.getItem()).setRod(staffM, advancedPrimalWandStaffRod.staffWand);
            list.add(staffM);

            ItemStack staffScepterM = new ItemStack(ConfigItems.itemWandCasting, 1, 75);
            ((ItemWandCasting) wandM.getItem()).setCap(staffScepterM, magicAlloyCap.cap);
            ((ItemWandCasting) wandM.getItem()).setRod(staffScepterM, advancedPrimalWandStaffRod.staffWand);
            staffScepterM.setTagInfo("sceptre", new NBTTagByte((byte) 1));
            list.add(staffScepterM);

            super.displayAllReleventItems(list);
        }
    };

    public static final CreativeTabs EZACIANCRAFT_RESOURCES_TAB = new CreativeTabs(CreativeTabs.creativeTabArray.length, UNLOCALE_EZACIANCRAFT_RESOURCES_CREATIVE_TAB) {
        @SideOnly(Side.CLIENT)
        public Item getTabIconItem() {
            return ezaciancraftTabIconItem;
        }
    };

    public static final CreativeTabs EZACIANCRAFT_DECORATIVES_TAB = new CreativeTabs(CreativeTabs.creativeTabArray.length, UNLOCALE_EZACIANCRAFT_DECORATIVES_CREATIVE_TAB) {
        @SideOnly(Side.CLIENT)
        public Item getTabIconItem() {
            return ezaciancraftTabIconItem;
        }
    };
}
