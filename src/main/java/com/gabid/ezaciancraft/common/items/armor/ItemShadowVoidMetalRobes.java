package com.gabid.ezaciancraft.common.items.armor;

import static com.gabid.ezaciancraft.CoreMod.MODID;
import static com.gabid.ezaciancraft.api.EzacianCraftGeneralLang.*;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import com.gabid.ezaciancraft.api.common.items.ItemCustomRobesArmor;
import com.gabid.ezaciancraft.api.registry.EzacianCraftMiscRegistry;
import com.gabid.ezaciancraft.registry.EzacianCraftCreativeTab;

import thaumcraft.api.aspects.Aspect;

public class ItemShadowVoidMetalRobes extends ItemCustomRobesArmor {

    public ItemShadowVoidMetalRobes(ArmorMaterial enumarmormaterial, int armorType, int render) {
        super(enumarmormaterial, armorType, render, MODID, 0x484848);
        if (render == 0) {
            this.setUnlocalizedName(UNLOCALE_SHADOW_VOID_METAL_ROBES_ARMOR_HELMET);
        } else if (render == 1) {
            this.setUnlocalizedName(UNLOCALE_SHADOW_VOID_METAL_ROBES_ARMOR_CHESTPLATE);
        } else if (render == 2) {
            this.setUnlocalizedName(UNLOCALE_SHADOW_VOID_METAL_ROBES_ARMOR_LEGGINGS);
        }
        this.setCreativeTab(EzacianCraftCreativeTab.EZACIANCRAFT_TAB);
    }

    @Override
    public int getWarp(ItemStack itemStack, EntityPlayer entityPlayer) {
        return 8;
    }

    @Override
    public EnumRarity getRarity(ItemStack stack) {
        return EzacianCraftMiscRegistry.beyondRarity;
    }

    @Override
    public void onArmorTick(World world, EntityPlayer player, ItemStack itemStack) {
        if (itemStack.isItemDamaged() && player != null && player.ticksExisted % 10 == 0) {
            itemStack.damageItem(-1, player);
        }
        super.onArmorTick(world, player, itemStack);
    }

    @Override
    public int getVisDiscount(ItemStack itemStack, EntityPlayer entityPlayer, Aspect aspect) {
        return 10;
    }
}
