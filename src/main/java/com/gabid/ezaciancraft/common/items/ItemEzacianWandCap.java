package com.gabid.ezaciancraft.common.items;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import thaumcraft.common.items.wands.ItemWandCap;

import java.util.List;

import static com.gabid.ezaciancraft.CoreMod.MODID;
import static com.gabid.ezaciancraft.registry.EzacianCraftCreativeTab.EZACIANCRAFT_TAB;

public class ItemEzacianWandCap extends ItemWandCap {

    private final IIcon[] capsIcons = new IIcon[2];

    public ItemEzacianWandCap() {
        this.setCreativeTab(EZACIANCRAFT_TAB);
    }

    @Override
    public void registerIcons(IIconRegister registar) {
        this.capsIcons[0] = registar.registerIcon(new ResourceLocation(MODID, "wand_cap_shadow_void_metal_inert").toString());
        this.capsIcons[1] = registar.registerIcon(new ResourceLocation(MODID, "wand_cap_shadow_void_metal").toString());
    }

    @Override
    public void getSubItems(Item item, CreativeTabs tab, List list) {
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

    @Override
    public IIcon getIconFromDamage(int meta) {
        return this.capsIcons[meta];
    }
}
