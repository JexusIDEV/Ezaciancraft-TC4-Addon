package com.gabid.ezaciancraft.api.common.items;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import thaumcraft.api.wands.IWandRodOnUpdate;
import thaumcraft.api.wands.StaffRod;
import thaumcraft.api.wands.WandCap;
import thaumcraft.api.wands.WandRod;
import thaumcraft.common.items.wands.ItemWandCap;

import java.util.List;

public class CustomItemWandRodStaff extends ItemWandCap {

    private IIcon[] rodStaffIcons;

    private final String modRoot;
    private final EnumRarity rarityCap;
    private final String tagRodStaff;

    public final WandRod rodWand;
    public final StaffRod staffWand;
    private final boolean withStaff;

    public CustomItemWandRodStaff(String _baseName, String _modRoot, EnumRarity _capRarity, CreativeTabs tabToRegister, String rodStaffTagNameAndTex, int aspectsCapacity, int craftCost, IWandRodOnUpdate specialUpdate, boolean _withStaff, boolean hasRunes) {
        this.modRoot = _modRoot;
        this.rarityCap = _capRarity;
        this.tagRodStaff = rodStaffTagNameAndTex;
        this.withStaff = _withStaff;
        this.setUnlocalizedName(_baseName+"Rod");
        this.setMaxStackSize(8);
        this.setHasSubtypes(true);
        this.setCreativeTab(tabToRegister);
        this.rodWand = new WandRod(rodStaffTagNameAndTex, aspectsCapacity, new ItemStack(this, 1, 1), craftCost, specialUpdate);
        this.rodWand.setTexture(new ResourceLocation(_modRoot, "textures/models/wand_rod_"+rodStaffTagNameAndTex+".png"));

        if(_withStaff) {
            this.staffWand = new StaffRod(rodStaffTagNameAndTex, aspectsCapacity * 2, new ItemStack(this, 1, 1), craftCost + (craftCost / 2), specialUpdate);
            this.staffWand.setTexture(new ResourceLocation(_modRoot, "textures/models/staff_rod_"+rodStaffTagNameAndTex+".png"));
            this.staffWand.setRunes(hasRunes);
        } else {
            this.staffWand = null;
        }
    }

    @Override
    public void getSubItems(Item item, CreativeTabs tab, List list) {
        if(this.withStaff) {
            list.add(new ItemStack(item, 1, 0));
            list.add(new ItemStack(item, 1, 1));
        } else {
            list.add(new ItemStack(item, 1, 0));
        }
    }

    @Override
    public void registerIcons(IIconRegister registar) {
        if(this.withStaff) {
            this.rodStaffIcons = new IIcon[2];
            this.rodStaffIcons[0] = registar.registerIcon(new ResourceLocation(this.modRoot, "wand_rod_"+this.tagRodStaff).toString());
            this.rodStaffIcons[1] = registar.registerIcon(new ResourceLocation(this.modRoot, "staff_rod_"+this.tagRodStaff).toString());
        } else {
            this.rodStaffIcons = new IIcon[1];
            this.rodStaffIcons[0] = registar.registerIcon(new ResourceLocation(this.modRoot, "wand_rod_"+this.tagRodStaff).toString());
        }
    }

    @Override
    public EnumRarity getRarity(ItemStack stack) {
        return this.rarityCap;
    }

    @Override
    public IIcon getIconFromDamage(int meta) {
        return this.rodStaffIcons[meta];
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        if(this.withStaff) {
            return this.getUnlocalizedName() + "." + this.getDamage(stack);
        } else {
            return this.getUnlocalizedName();
        }
    }
}
