package com.gabid.ezaciancraft.common.items.armor;

import com.gabid.ezaciancraft.api.common.items.ItemCustomFortressArmor;
import com.gabid.ezaciancraft.api.registry.EzacianCraftMiscRegistry;
import com.gabid.ezaciancraft.registry.EzacianCraftCreativeTab;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import thaumcraft.api.IWarpingGear;
import thaumcraft.api.aspects.Aspect;

import static com.gabid.ezaciancraft.CoreMod.MODID;
import static com.gabid.ezaciancraft.api.EzacianCraftGeneralLang.*;

public class ItemMagicAlloyFortressArmor extends ItemCustomFortressArmor implements IWarpingGear {
    public ItemMagicAlloyFortressArmor(ArmorMaterial enumarmormaterial, int armorType, int render) {
        super(enumarmormaterial, armorType, render, "magicAlloy", MODID);
        if(render == 0) {
            this.setUnlocalizedName(UNLOCALE_MAGIC_ALLOY_FORTRESS_ARMOR_HELMET);
        } else if(render == 1) {
            this.setUnlocalizedName(UNLOCALE_MAGIC_ALLOY_FORTRESS_ARMOR_CHESTPLATE);
        } else if(render == 2) {
            this.setUnlocalizedName(UNLOCALE_MAGIC_ALLOY_FORTRESS_ARMOR_LEGGINGS);
        }
        this.setCreativeTab(EzacianCraftCreativeTab.EZACIANCRAFT_TAB);
    }

    @Override
    public int getWarp(ItemStack itemStack, EntityPlayer entityPlayer) {
        return 4;
    }

    @Override
    public EnumRarity getRarity(ItemStack stack) {
        return EzacianCraftMiscRegistry.beyondRarity;
    }

    @Override
    public void onArmorTick(World world, EntityPlayer player, ItemStack itemStack) {
        if (itemStack.isItemDamaged() && player != null && player.ticksExisted % 80 == 0) {
            itemStack.damageItem(-1, player);
        }
        super.onArmorTick(world, player, itemStack);
    }

    @Override
    public int getVisDiscount(ItemStack itemStack, EntityPlayer entityPlayer, Aspect aspect) {
        return 2;
    }
}
