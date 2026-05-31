package com.gabid.ezaciancraft.api.common.items;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import thaumcraft.api.wands.WandCap;
import thaumcraft.common.items.wands.ItemWandCap;

import java.util.List;

public class CustomItemWandCap extends ItemWandCap {

    private IIcon[] capsIcons;

    private final String modRoot;
    private final EnumRarity rarityCap;
    private final String tagCap;

    public final WandCap cap;
    private final boolean withInertCharged;

    public CustomItemWandCap(String _baseName, String _modRoot, EnumRarity _capRarity, CreativeTabs tabToRegister, String capTagNameAndTex, float capDiscount, int craftCost, boolean _withInertCharged) {
        this.modRoot = _modRoot;
        this.rarityCap = _capRarity;
        this.tagCap = capTagNameAndTex;
        this.withInertCharged = _withInertCharged;
        this.setUnlocalizedName(_baseName+"WandCap");
        this.setMaxStackSize(16);
        this.setHasSubtypes(true);
        this.setCreativeTab(tabToRegister);
        this.cap = new WandCap(capTagNameAndTex, capDiscount, new ItemStack(this, 1, 1), craftCost);
        this.cap.setTexture(new ResourceLocation(_modRoot, "textures/models/wand_cap_"+capTagNameAndTex+".png"));
    }

    @Override
    public void getSubItems(Item item, CreativeTabs tab, List list) {
        if(this.withInertCharged) {
            list.add(new ItemStack(item, 1, 0));
            list.add(new ItemStack(item, 1, 1));
        } else {
            list.add(new ItemStack(item, 1, 0));
        }
    }

    @Override
    public void registerIcons(IIconRegister registar) {
        if(this.withInertCharged) {
            this.capsIcons = new IIcon[2];
            this.capsIcons[0] = registar.registerIcon(new ResourceLocation(this.modRoot, "wand_cap_"+this.tagCap+"_inert").toString());
            this.capsIcons[1] = registar.registerIcon(new ResourceLocation(this.modRoot, "wand_cap_"+this.tagCap).toString());
        } else {
            this.capsIcons = new IIcon[1];
            this.capsIcons[0] = registar.registerIcon(new ResourceLocation(this.modRoot, "wand_cap_"+this.tagCap).toString());
        }
    }

    @Override
    public EnumRarity getRarity(ItemStack stack) {
        return this.rarityCap;
    }

    @Override
    public IIcon getIconFromDamage(int meta) {
        return this.capsIcons[meta];
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        if(this.withInertCharged) {
            return this.getUnlocalizedName() + "." + this.getDamage(stack);
        } else {
            return this.getUnlocalizedName();
        }
    }
}
