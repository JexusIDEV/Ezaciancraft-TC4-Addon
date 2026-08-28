package com.gabid.ezaciancraft.api.common.items;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import thaumcraft.common.items.armor.ItemCultistRobeArmor;
import thaumcraft.common.items.armor.ItemFortressArmor;
import thaumcraft.common.items.armor.ItemRobeArmor;
import thaumcraft.common.items.armor.ItemVoidRobeArmor;

import java.util.List;

public class ItemCustomRobesArmor extends ItemVoidRobeArmor {

    protected final String modid;
    protected final int color;

    public ItemCustomRobesArmor(ArmorMaterial enumarmormaterial, int armorType, int render, String _modid, int _color) {
        super(enumarmormaterial, armorType, render);
        this.modid = _modid;
        this.color = _color;
    }

    public int func_82814_b(ItemStack par1ItemStack) {
        NBTTagCompound nbttagcompound = par1ItemStack.getTagCompound();
        if (nbttagcompound == null) {
            return this.color;
        } else {
            NBTTagCompound nbttagcompound1 = nbttagcompound.getCompoundTag("display");
            return nbttagcompound1 == null ? this.color : (nbttagcompound1.hasKey("color") ? nbttagcompound1.getInteger("color") : 6961280);
        }
    }

    public void func_82815_c(ItemStack par1ItemStack) {
        NBTTagCompound nbttagcompound = par1ItemStack.getTagCompound();
        if (nbttagcompound != null) {
            NBTTagCompound nbttagcompound1 = nbttagcompound.getCompoundTag("display");
            if (nbttagcompound1.hasKey("color")) {
                nbttagcompound1.removeTag("color");
            }
        }

    }

    public void func_82813_b(ItemStack par1ItemStack, int par2) {
        NBTTagCompound nbttagcompound = par1ItemStack.getTagCompound();
        if (nbttagcompound == null) {
            nbttagcompound = new NBTTagCompound();
            par1ItemStack.setTagCompound(nbttagcompound);
        }

        NBTTagCompound nbttagcompound1 = nbttagcompound.getCompoundTag("display");
        if (!nbttagcompound.hasKey("display")) {
            nbttagcompound.setTag("display", nbttagcompound1);
        }

        nbttagcompound1.setInteger("color", par2);
    }
}
