package com.gabid.ezaciancraft.common.items;

import com.gabid.ezaciancraft.api.EzacianToolMaterials;
import com.google.common.collect.Multimap;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import thaumcraft.api.IWarpingGear;
import thaumcraft.common.items.equipment.ItemElementalPickaxe;
import thaumcraft.common.items.equipment.ItemElementalSword;

import static com.gabid.ezaciancraft.CoreMod.MODID;
import static com.gabid.ezaciancraft.api.EzacianCraftGeneralLang.UNLOCALE_VOID_CORE_PICKAXE;
import static com.gabid.ezaciancraft.api.EzacianCraftGeneralLang.UNLOCALE_VOID_ZEPHYR_SWORD;
import static com.gabid.ezaciancraft.registry.EzacianCraftCreativeTab.EZACIANCRAFT_TAB;

public class VoidZephyrSwordItem extends ItemElementalSword implements IWarpingGear {

    public IIcon icon;

    public VoidZephyrSwordItem() {
        super(EzacianToolMaterials.toolMatVoidElemental);
        this.setUnlocalizedName(UNLOCALE_VOID_ZEPHYR_SWORD);
        this.setTextureName(new ResourceLocation(MODID, UNLOCALE_VOID_ZEPHYR_SWORD).toString());
        this.setCreativeTab(EZACIANCRAFT_TAB);
    }

    @Override
    public void registerIcons(IIconRegister register) {
        this.icon = register.registerIcon(new ResourceLocation(MODID, UNLOCALE_VOID_ZEPHYR_SWORD).toString());
    }

    @Override
    public IIcon getIconFromDamage(int dmg) {
        return this.icon;
    }

    @Override
    public EnumRarity getRarity(ItemStack p_77613_1_) {
        return EnumRarity.epic;
    }

    @Override
    public int getWarp(ItemStack itemStack, EntityPlayer entityPlayer) {
        return 4;
    }

    @Override
    public void onUpdate(ItemStack stack, World level, Entity player, int a, boolean b) {
        if(stack.isItemDamaged() && player != null && player.ticksExisted % 10 == 0 && player instanceof EntityLivingBase) {
            stack.damageItem(-1, (EntityLivingBase) player);
        }
        super.onUpdate(stack, level, player, a, b);
    }
}
