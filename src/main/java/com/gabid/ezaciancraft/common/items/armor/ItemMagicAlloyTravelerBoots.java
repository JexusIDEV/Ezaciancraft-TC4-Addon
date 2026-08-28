package com.gabid.ezaciancraft.common.items.armor;

import com.gabid.ezaciancraft.api.registry.EzacianCraftMiscRegistry;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import thaumcraft.api.IRepairable;
import thaumcraft.api.IRunicArmor;
import thaumcraft.api.IVisDiscountGear;
import thaumcraft.api.IWarpingGear;
import thaumcraft.api.aspects.Aspect;

import java.util.List;

import static com.gabid.ezaciancraft.CoreMod.MODID;
import static com.gabid.ezaciancraft.api.EzacianCraftGeneralLang.UNLOCALE_MAGIC_ALLOY_TRAVELLER_BOOTS;
import static com.gabid.ezaciancraft.api.EzacianToolMaterials.armorMagicAlloyMaterial;
import static com.gabid.ezaciancraft.registry.EzacianCraftCreativeTab.EZACIANCRAFT_TAB;

public class ItemMagicAlloyTravelerBoots extends ItemArmor implements IRepairable, IRunicArmor, IVisDiscountGear, IWarpingGear {

    private IIcon bootsItemIcon;

    public ItemMagicAlloyTravelerBoots() {
        super(armorMagicAlloyMaterial, 4, 3);
        this.setCreativeTab(EZACIANCRAFT_TAB);
        this.setMaxDamage(armorMagicAlloyMaterial.getDurability(0)/4);
        this.setUnlocalizedName(UNLOCALE_MAGIC_ALLOY_TRAVELLER_BOOTS);
    }

    @Override
    public void registerIcons(IIconRegister register) {
        this.bootsItemIcon = register.registerIcon(new ResourceLocation(MODID,"magicAlloyTravellerBoots").toString());
    }

    @Override
    public IIcon getIcon(ItemStack stack, int pass) {
        return this.bootsItemIcon;
    }

    @Override
    public IIcon getIconIndex(ItemStack stack) {
        return this.bootsItemIcon;
    }

    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type) {
        return new ResourceLocation(MODID, "textures/models/magicAlloyBootsTraveler.png").toString();
    }

    @Override
    public EnumRarity getRarity(ItemStack stack) {
        return EzacianCraftMiscRegistry.beyondRarity;
    }

    @Override
    public void onUpdate(ItemStack stack, World level, Entity player, int a, boolean b) {
        if (stack.isItemDamaged() && player != null && player.ticksExisted % 80 == 0 && player instanceof EntityLivingBase) {
            stack.damageItem(-1, (EntityLivingBase) player);
        }
        super.onUpdate(stack, level, player, a, b);
    }

    @Override
    public void onArmorTick(World world, EntityPlayer player, ItemStack itemStack) {
        if (!player.capabilities.isFlying && (player.moveForward != 0.0F || player.moveStrafing != 0.0F)) {
            if (player.worldObj.isRemote && !player.isSneaking()) {
                player.stepHeight = 1.0F;
            }

            if (player.onGround) {
                float bonus = player.isInWater() ? 0.35F / 2.0F : 0.35F;
                player.moveFlying(player.moveStrafing, player.moveForward, bonus);
            } else {
                player.jumpMovementFactor = 0.05F;
            }
        }

        player.fallDistance = Math.max(0, player.fallDistance - 1F);
    }

    @Override
    public int getRunicCharge(ItemStack itemStack) {
        return 0;
    }

    @Override
    public int getVisDiscount(ItemStack itemStack, EntityPlayer entityPlayer, Aspect aspect) {
        return 10;
    }

    @Override
    public int getWarp(ItemStack itemStack, EntityPlayer entityPlayer) {
        return 6;
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean flag) {
        list.add(EnumChatFormatting.DARK_PURPLE + StatCollector.translateToLocal("tc.visdiscount") + ": " + this.getVisDiscount(stack, player, (Aspect)null) + "%");
        super.addInformation(stack, player, list, flag);
    }
}
