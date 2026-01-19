package com.gabid.ezaciancraft.common.items.tools;

import com.gabid.ezaciancraft.api.EzacianToolMaterials;
import com.gabid.ezaciancraft.api.common.items.EzacianToolHelper;
import com.gabid.ezaciancraft.api.common.items.IEzacianTool;
import com.gabid.ezaciancraft.lib.nbt.NBTHelper;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.util.ForgeDirection;
import thaumcraft.api.IWarpingGear;
import thaumcraft.common.blocks.BlockJar;
import thaumcraft.common.items.equipment.ItemElementalPickaxe;

import java.util.List;

import static com.gabid.ezaciancraft.CoreMod.MODID;
import static com.gabid.ezaciancraft.api.EzacianCraftGeneralLang.UNLOCALE_VOID_CORE_PICKAXE;
import static com.gabid.ezaciancraft.api.EzacianCraftNBTConstants.TOOL_MODE;
import static com.gabid.ezaciancraft.api.EzacianCraftTranslations.*;
import static com.gabid.ezaciancraft.registry.EzacianCraftCreativeTab.EZACIANCRAFT_TAB;

public class VoidCorePickaxeItem extends ItemElementalPickaxe implements IWarpingGear, IEzacianTool {
    public IIcon[] icons = new IIcon[3];

    public VoidCorePickaxeItem() {
        super(EzacianToolMaterials.toolMatVoidElemental);
        this.setUnlocalizedName(UNLOCALE_VOID_CORE_PICKAXE);
        this.setCreativeTab(EZACIANCRAFT_TAB);
    }

    @Override
    public void getSubItems(Item item, CreativeTabs tabs, List items) {
        items.add(NBTHelper.setDefaultContainerNBT(new ItemStack(item, 1), TOOL_MODE, 0));
    }

    @Override
    public void registerIcons(IIconRegister register) {
        this.icons[0] = register.registerIcon(new ResourceLocation(MODID, UNLOCALE_VOID_CORE_PICKAXE+"Single").toString());
        this.icons[1] = register.registerIcon(new ResourceLocation(MODID, UNLOCALE_VOID_CORE_PICKAXE+"Area").toString());
        this.icons[2] = register.registerIcon(new ResourceLocation(MODID, UNLOCALE_VOID_CORE_PICKAXE+"Column").toString());
    }

    @Override
    public IIcon getIconIndex(ItemStack stack) {
        int current = this.getMode(stack);
        return this.icons[current];
    }

    @Override
    public IIcon getIcon(ItemStack stack, int pass) {
        int current = this.getMode(stack);
        return this.icons[current];
    }

    @Override
    public EnumRarity getRarity(ItemStack p_77613_1_) {
        return EnumRarity.epic;
    }

    @Override
    public int getWarp(ItemStack itemStack, EntityPlayer entityPlayer) {
        return 3;
    }

    @Override
    public void onUpdate(ItemStack stack, World level, Entity player, int a, boolean b) {
        if(stack.isItemDamaged() && player != null && player.ticksExisted % 10 == 0 && player instanceof EntityLivingBase) {
            stack.damageItem(-1, (EntityLivingBase) player);
        }
        super.onUpdate(stack, level, player, a, b);
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List tooltips, boolean flag) {
        super.addInformation(stack, player, tooltips, flag);
        int toolModeData = this.getMode(stack);
        String info = "";
        switch (toolModeData) {
            case 0:
                info = ezacianToolModeSingleTranslation;
                break;
            case 1:
                info = ezacianToolModeAreaTranslation;
                break;
            case 2:
                info = ezacianToolModeColumnTranslation;
                break;
        }
        tooltips.add(ezacianToolModeTranslation+": " + info);
    }

    @Override
    public float getDigSpeed(ItemStack stack, Block block, int meta) {
        if(block instanceof BlockJar && !ForgeHooks.isToolEffective(stack, block, meta)) {
            return this.efficiencyOnProperMaterial;
        }
        return super.getDigSpeed(stack, block, meta);
    }

    @Override
    public boolean onBlockStartBreak(ItemStack itemstack, int x, int y, int z, EntityPlayer player) {
        World world = player.worldObj;
        Material mat = world.getBlock(x, y, z).getMaterial();
        if (!EzacianToolHelper.isRightMaterial(mat, EzacianToolHelper.materialsPick))
            return false;

        MovingObjectPosition block = EzacianToolHelper.raytraceFromEntity(world, player, true, 4.5);
        if (block == null)
            return false;

        ForgeDirection direction = ForgeDirection.getOrientation(block.sideHit);
        int fortune = EnchantmentHelper.getFortuneModifier(player);
        boolean silk = EnchantmentHelper.getSilkTouchModifier(player);

        switch (this.getMode(itemstack)) {
            case 0:
                break;
            case 1: {
                boolean doX = direction.offsetX == 0;
                boolean doY = direction.offsetY == 0;
                boolean doZ = direction.offsetZ == 0;

                EzacianToolHelper.removeBlocksInIteration(player, world, x, y, z, doX ? -2 : 0, doY ? -1 : 0, doZ ? -2 : 0, doX ? 3 : 1, doY ? 4 : 1, doZ ? 3 : 1, null, EzacianToolHelper.materialsPick, silk, fortune);

                break;
            }
            case 2: {
                int xo = -direction.offsetX;
                int yo = -direction.offsetY;
                int zo = -direction.offsetZ;

                EzacianToolHelper.removeBlocksInIteration(player, world, x, y, z, xo >= 0 ? 0 : -10, yo >= 0 ? 0 : -10, zo >= 0 ? 0 : -10, xo > 0 ? 10 : 1, yo > 0 ? 10 : 1, zo > 0 ? 10 : 1, null, EzacianToolHelper.materialsPick, silk, fortune);
                break;
            }
        }
        return false;
    }

    @Override
    public void setMode(ItemStack stack, int mode) {
        if(!NBTHelper.containerNBTIsNotNull(stack)) {
            NBTHelper.setDefaultContainerNBT(stack, TOOL_MODE, 0);
        }

        if(mode >-1 && mode < 3) {
            stack.getTagCompound().setInteger(TOOL_MODE, mode);
        } else {
            stack.getTagCompound().setInteger(TOOL_MODE, 0);
        }
    }

    @Override
    public void changeMode(ItemStack stack) {
        if(!NBTHelper.containerNBTIsNotNull(stack)) {
            NBTHelper.setDefaultContainerNBT(stack, TOOL_MODE, 0);
        }
        int current = stack.getTagCompound().getInteger(TOOL_MODE);
        current++;
        this.setMode(stack, current);
    }

    @Override
    public int getMode(ItemStack stack) {
        if (!NBTHelper.containerNBTIsNotNull(stack)) {
            NBTHelper.setDefaultContainerNBT(stack, TOOL_MODE, 0);
        }
        return stack.getTagCompound().getInteger(TOOL_MODE);
    }
}
