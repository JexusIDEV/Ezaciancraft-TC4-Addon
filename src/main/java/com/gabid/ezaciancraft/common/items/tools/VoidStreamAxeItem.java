package com.gabid.ezaciancraft.common.items.tools;

import com.gabid.ezaciancraft.api.EzacianToolMaterials;
import com.gabid.ezaciancraft.api.common.items.EzacianToolHelper;
import com.gabid.ezaciancraft.api.common.items.IEzacianTool;
import com.gabid.ezaciancraft.lib.nbt.NBTHelper;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;
import thaumcraft.api.IRepairable;
import thaumcraft.api.IWarpingGear;
import thaumcraft.common.blocks.BlockJar;
import thaumcraft.common.items.equipment.ItemElementalAxe;

import java.util.List;

import static com.gabid.ezaciancraft.CoreMod.MODID;
import static com.gabid.ezaciancraft.api.EzacianCraftGeneralLang.UNLOCALE_VOID_STREAM_AXE;
import static com.gabid.ezaciancraft.api.EzacianCraftNBTConstants.TOOL_MODE;
import static com.gabid.ezaciancraft.api.EzacianCraftTranslations.*;
import static com.gabid.ezaciancraft.registry.EzacianCraftCreativeTab.EZACIANCRAFT_TAB;

public class VoidStreamAxeItem extends ItemElementalAxe implements IWarpingGear, IRepairable, IEzacianTool {
    public IIcon[] icons = new IIcon[3];

    public VoidStreamAxeItem() {
        super(EzacianToolMaterials.toolMatVoidElemental);
        this.setUnlocalizedName(UNLOCALE_VOID_STREAM_AXE);
        this.setCreativeTab(EZACIANCRAFT_TAB);
    }

    @Override
    public void registerIcons(IIconRegister register) {
        this.icons[0] = register.registerIcon(new ResourceLocation(MODID, UNLOCALE_VOID_STREAM_AXE + "Single").toString());
        this.icons[1] = register.registerIcon(new ResourceLocation(MODID, UNLOCALE_VOID_STREAM_AXE + "Area").toString());
        this.icons[2] = register.registerIcon(new ResourceLocation(MODID, UNLOCALE_VOID_STREAM_AXE + "Column").toString());
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
        if (stack.isItemDamaged() && player != null && player.ticksExisted % 10 == 0 && player instanceof EntityLivingBase) {
            stack.damageItem(-level.rand.nextInt(3), (EntityLivingBase) player);
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
                info = StatCollector.translateToLocal(ezacianToolModeSingleTranslation);
                break;
            case 1:
                info = StatCollector.translateToLocal(ezacianToolModeAreaTranslation);
                break;
            case 2:
                info = StatCollector.translateToLocal(ezacianToolModeTreeTranslation);
                break;
        }
        tooltips.add(StatCollector.translateToLocal(ezacianToolModeTranslation) + ": " + info);
    }

    @Override
    public float getDigSpeed(ItemStack stack, Block block, int meta) {
        if (block instanceof BlockJar && !ForgeHooks.isToolEffective(stack, block, meta)) {
            return this.efficiencyOnProperMaterial;
        }
        return super.getDigSpeed(stack, block, meta);
    }

    @Override
    public boolean onBlockStartBreak(ItemStack stack, int x, int y, int z, EntityPlayer player) {
        World world = player.worldObj;
        Material mat = world.getBlock(x, y, z).getMaterial();
        if (EzacianToolHelper.isRightMaterial(mat, EzacianToolHelper.materialsAxe))
            return false;

        MovingObjectPosition block = EzacianToolHelper.raytraceFromEntity(world, player, true, 4.5);
        if (block == null)
            return false;

        switch (this.getMode(stack)) {
            case 0:
                return super.onBlockStartBreak(stack, x, y, z, player);
            case 1: {
                if (player.isSneaking()) {
                    return super.onBlockStartBreak(stack, x, y, z, player);
                } else {
                    EzacianToolHelper.removeAOEBlocks(stack, player, world, x, y, z, 2);
                }
                break;
            }
            case 2: {
                EzacianToolHelper.breakVeinWood(stack, world, player, x, y, z);
                break;
            }
        }
        return false;
    }

    @Override
    public void setMode(ItemStack stack, int mode) {
        if (!NBTHelper.containerNBTIsNotNull(stack)) {
            NBTHelper.setDefaultContainerNBT(stack, TOOL_MODE, 0);
        }

        if (mode > -1 && mode < 3) {
            stack.getTagCompound().setInteger(TOOL_MODE, mode);
        } else {
            stack.getTagCompound().setInteger(TOOL_MODE, 0);
        }
    }

    @Override
    public void changeMode(ItemStack stack) {
        if (!NBTHelper.containerNBTIsNotNull(stack)) {
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
