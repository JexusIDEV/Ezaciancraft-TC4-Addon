package com.gabid.ezaciancraft.common.items.tools;

import com.gabid.ezaciancraft.api.EzacianToolMaterials;
import com.gabid.ezaciancraft.api.common.items.EzacianToolHelper;
import com.gabid.ezaciancraft.api.common.items.IEzacianTool;
import com.gabid.ezaciancraft.lib.nbt.NBTHelper;
import com.google.common.collect.ImmutableSet;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.*;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import thaumcraft.api.IArchitect;
import thaumcraft.api.IRepairable;
import thaumcraft.api.IWarpingGear;
import thaumcraft.common.Thaumcraft;
import thaumcraft.common.lib.utils.InventoryUtils;

import java.util.List;
import java.util.Set;

import static com.gabid.ezaciancraft.CoreMod.MODID;
import static com.gabid.ezaciancraft.api.EzacianCraftGeneralLang.*;
import static com.gabid.ezaciancraft.api.EzacianCraftNBTConstants.TOOL_MODE;
import static com.gabid.ezaciancraft.api.EzacianCraftTranslations.*;
import static com.gabid.ezaciancraft.api.EzacianCraftTranslations.ezacianToolModeTranslation;
import static com.gabid.ezaciancraft.registry.EzacianCraftCreativeTab.EZACIANCRAFT_TAB;

//adapting from original class, due for the own area destroy and putting blocks - conflict within the thaumic tinkerer's idea

public class VoidEarthMoverShovelItem extends ItemSpade implements IWarpingGear, IRepairable, IEzacianTool {
    public IIcon[] icons = new IIcon[3];

    public VoidEarthMoverShovelItem() {
        super(EzacianToolMaterials.toolMatVoidElemental);
        this.setUnlocalizedName(UNLOCALE_VOID_TERRA_SHATTER_SHOVEL);
        this.setCreativeTab(EZACIANCRAFT_TAB);
    }

    @Override
    public Set<String> getToolClasses(ItemStack stack) {
        return ImmutableSet.of("shovel");
    }

    @Override
    public void getSubItems(Item item, CreativeTabs tabs, List items) {
        items.add(NBTHelper.setDefaultContainerNBT(new ItemStack(item, 1), TOOL_MODE, 0));
    }

    @Override
    public void registerIcons(IIconRegister register) {
        this.icons[0] = register.registerIcon(new ResourceLocation(MODID, UNLOCALE_VOID_TERRA_SHATTER_SHOVEL+"Single").toString());
        this.icons[1] = register.registerIcon(new ResourceLocation(MODID, UNLOCALE_VOID_TERRA_SHATTER_SHOVEL+"Area").toString());
        this.icons[2] = register.registerIcon(new ResourceLocation(MODID, UNLOCALE_VOID_TERRA_SHATTER_SHOVEL+"Column").toString());
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
    public boolean onBlockStartBreak(ItemStack itemstack, int x, int y, int z, EntityPlayer player) {
        World world = player.worldObj;
        Material mat = world.getBlock(x, y, z).getMaterial();
        if (!EzacianToolHelper.isRightMaterial(mat, EzacianToolHelper.materialsShovel))
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

                EzacianToolHelper.removeBlocksInIteration(player, world, x, y, z, doX ? -2 : 0, doY ? -1 : 0, doZ ? -2 : 0, doX ? 3 : 1, doY ? 4 : 1, doZ ? 3 : 1, null, EzacianToolHelper.materialsShovel, silk, fortune);
                break;
            }
            case 2: {
                int xo = -direction.offsetX;
                int yo = -direction.offsetY;
                int zo = -direction.offsetZ;

                EzacianToolHelper.removeBlocksInIteration(player, world, x, y, z, xo >= 0 ? 0 : -10, yo >= 0 ? 0 : -10, zo >= 0 ? 0 : -10, xo > 0 ? 10 : 1, yo > 0 ? 10 : 1, zo > 0 ? 10 : 1, null, EzacianToolHelper.materialsShovel, silk, fortune);
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

    @Override
    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float p_77648_8_, float p_77648_9_, float p_77648_10_) {
        int xm = ForgeDirection.getOrientation(side).offsetX;
        int ym = ForgeDirection.getOrientation(side).offsetY;
        int zm = ForgeDirection.getOrientation(side).offsetZ;
        Block bi = world.getBlock(x, y, z);
        int md = world.getBlockMetadata(x, y, z);
        TileEntity te = world.getTileEntity(x, y, z);
        if (te == null) {
            for (int aa = -1; aa <= 1; ++aa) {
                for (int bb = -1; bb <= 1; ++bb) {
                    int xx = 0;
                    int yy = 0;
                    int zz = 0;
                    byte o = getOrientation(stack);
                    int l;
                    if (o == 1) {
                        yy = bb;
                        if (side <= 1) {
                            l = MathHelper.floor_double((double) (player.rotationYaw * 4.0F / 360.0F) + 0.5) & 3;
                            if (l != 0 && l != 2) {
                                zz = aa;
                            } else {
                                xx = aa;
                            }
                        } else if (side <= 3) {
                            zz = aa;
                        } else {
                            xx = aa;
                        }
                    } else if (o == 2) {
                        if (side <= 1) {
                            l = MathHelper.floor_double((double) (player.rotationYaw * 4.0F / 360.0F) + 0.5) & 3;
                            yy = bb;
                            if (l != 0 && l != 2) {
                                zz = aa;
                            } else {
                                xx = aa;
                            }
                        } else {
                            zz = bb;
                            xx = aa;
                        }
                    } else if (side <= 1) {
                        xx = aa;
                        zz = bb;
                    } else if (side <= 3) {
                        xx = aa;
                        yy = bb;
                    } else {
                        zz = aa;
                        yy = bb;
                    }

                    Block b2 = world.getBlock(x + xx + xm, y + yy + ym, z + zz + zm);
                    if (world.isAirBlock(x + xx + xm, y + yy + ym, z + zz + zm) || b2 == Blocks.vine || b2 == Blocks.tallgrass || b2.getMaterial() == Material.water || b2 == Blocks.lava || b2.isReplaceable(world, x + xx + xm, y + yy + ym, z + zz + zm)) {
                        if (!player.capabilities.isCreativeMode && !InventoryUtils.consumeInventoryItem(player, Item.getItemFromBlock(bi), md)) {
                            if (bi == Blocks.grass && (player.capabilities.isCreativeMode || InventoryUtils.consumeInventoryItem(player, Item.getItemFromBlock(Blocks.dirt), 0))) {
                                world.playSound((double) (x + xx + xm), (double) (y + yy + ym), (double) (z + zz + zm), bi.stepSound.func_150496_b(), 0.6F, 0.9F + world.rand.nextFloat() * 0.2F, false);
                                world.setBlock(x + xx + xm, y + yy + ym, z + zz + zm, Blocks.dirt, 0, 3);
                                stack.damageItem(1, player);
                                Thaumcraft.proxy.blockSparkle(world, x + xx + xm, y + yy + ym, z + zz + zm, 3, 4);
                                player.swingItem();
                            }
                        } else {
                            world.playSound((double) (x + xx + xm), (double) (y + yy + ym), (double) (z + zz + zm), bi.stepSound.func_150496_b(), 0.6F, 0.9F + world.rand.nextFloat() * 0.2F, false);
                            world.setBlock(x + xx + xm, y + yy + ym, z + zz + zm, bi, md, 3);
                            stack.damageItem(1, player);
                            Thaumcraft.proxy.blockSparkle(world, x + xx + xm, y + yy + ym, z + zz + zm, 8401408, 4);
                            player.swingItem();
                        }
                    }
                }
            }
        }
        return false;
    }

    public boolean showAxis(ItemStack stack, World world, EntityPlayer player, int side, IArchitect.EnumAxis axis) {
        return false;
    }

    public static byte getOrientation(ItemStack stack) {
        return stack.hasTagCompound() && stack.getTagCompound().hasKey("or") ? stack.getTagCompound().getByte("or") : 0;
    }

    public static void setOrientation(ItemStack stack, byte o) {
        if (!stack.hasTagCompound()) {
            stack.setTagCompound(new NBTTagCompound());
        }

        if (stack.hasTagCompound()) {
            stack.getTagCompound().setByte("or", (byte)(o % 3));
        }

    }
}
