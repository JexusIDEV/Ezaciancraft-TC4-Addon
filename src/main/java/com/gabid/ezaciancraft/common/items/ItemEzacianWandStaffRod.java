package com.gabid.ezaciancraft.common.items;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import thaumcraft.common.items.wands.ItemWandRod;

import java.util.List;

import static com.gabid.ezaciancraft.CoreMod.MODID;
import static com.gabid.ezaciancraft.registry.EzacianCraftCreativeTab.EZACIANCRAFT_TAB;

public class ItemEzacianWandStaffRod extends ItemWandRod {

    public IIcon[] rodIcons = new IIcon[2];

    public ItemEzacianWandStaffRod() {
        this.setCreativeTab(EZACIANCRAFT_TAB);
    }

    @Override
    public void registerIcons(IIconRegister registar) {
        this.rodIcons[0] = registar.registerIcon(new ResourceLocation(MODID, "wand_rod_advanced_primal").toString());
        this.rodIcons[1] = registar.registerIcon(new ResourceLocation(MODID, "staff_rod_advanced_primal").toString());
    }

    @Override
    public IIcon getIconFromDamage(int meta) {
        return this.rodIcons[meta];
    }

    @Override
    public void getSubItems(Item item, CreativeTabs tabs, List list) {
        list.add(new ItemStack(this, 1, 0));
        list.add(new ItemStack(this, 1, 1));
    }

    @Override
    public EnumRarity getRarity(ItemStack stack) {
        if(stack.getItemDamage() < 2) {
            return EnumRarity.epic;
        }
        return super.getRarity(stack);
    }
}
