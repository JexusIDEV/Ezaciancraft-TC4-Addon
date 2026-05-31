package com.gabid.ezaciancraft.common.items.armor;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import thaumcraft.api.IRepairable;
import thaumcraft.api.IRunicArmor;
import thaumcraft.api.IVisDiscountGear;
import thaumcraft.api.aspects.Aspect;

import static com.gabid.ezaciancraft.CoreMod.MODID;
import static com.gabid.ezaciancraft.api.EzacianCraftGeneralLang.UNLOCALE_MAGIC_ALLOY_TRAVELLER_BOOTS;
import static com.gabid.ezaciancraft.api.EzacianToolMaterials.armorMagicAlloyMaterial;
import static com.gabid.ezaciancraft.registry.EzacianCraftCreativeTab.EZACIANCRAFT_TAB;

public class ItemMagicAlloyTravelerBoots extends ItemArmor implements IRepairable, IRunicArmor, IVisDiscountGear {

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
    public EnumRarity getRarity(ItemStack p_77613_1_) {
        return EnumRarity.epic;
    }

    @Override
    public void onUpdate(ItemStack stack, World level, Entity player, int a, boolean b) {
        if (stack.isItemDamaged() && player != null && player.ticksExisted % 5 == 0 && player instanceof EntityLivingBase) {
            stack.damageItem(-1, (EntityLivingBase) player);
        }
        super.onUpdate(stack, level, player, a, b);
    }

    @Override
    public void onArmorTick(World world, EntityPlayer player, ItemStack itemStack) {
        if (!player.capabilities.isFlying && player.moveForward > 0.0F) {
            if (player.worldObj.isRemote && !player.isSneaking()) {
                player.stepHeight = 1.0F;
            }

            if (player.onGround) {
                float bonus = player.isInWater() ? 0.35F / 2.0F : 0.35F;
                player.moveFlying(0.0F, 1.0F, bonus);
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
}
