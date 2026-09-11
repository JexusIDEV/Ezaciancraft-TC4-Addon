package com.gabid.ezaciancraft.common.blocks.vegetal;

import com.gabid.ezaciancraft.api.common.blocks.EzacianCustomBlockCrop;
import com.gabid.ezaciancraft.common.items.vegetal.ItemAspectSeed;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.common.items.ItemCrystalEssence;

import java.util.ArrayList;
import java.util.Random;

import static com.gabid.ezaciancraft.CoreMod.MODID;
import static com.gabid.ezaciancraft.api.EzacianCraftGeneralLang.UNLOCALE_ASPECT_CROP_BASENAME;
import static com.gabid.ezaciancraft.lib.world.ContainerInventoryHelperUtils.dropItems;
import static com.gabid.ezaciancraft.registry.EzacianCraftItems.aspectSeeds;
import static thaumcraft.common.config.ConfigItems.itemCrystalEssence;

public class BlockAspectCrop extends EzacianCustomBlockCrop {
    protected IIcon colouredParts;

    public BlockAspectCrop() {
        super(UNLOCALE_ASPECT_CROP_BASENAME, 4, null, null);
    }

    @Override
    public Item getItemDropped(int meta, Random rand, int p_149650_3_) {
        return null;
    }

    @Override
    public boolean hasTileEntity(int metadata) {
        return true;
    }

    @Override
    public TileEntity createTileEntity(World world, int metadata) {
        return new TileEntityAspectCrop();
    }

    @Override
    public void registerBlockIcons(IIconRegister register) {
        super.registerBlockIcons(register);
        this.colouredParts = register.registerIcon(new ResourceLocation(MODID, "aspectCrop_stage_3_crystal").toString());
    }

    @Override
    public IIcon getIcon(IBlockAccess p_149673_1_, int p_149673_2_, int p_149673_3_, int p_149673_4_, int p_149673_5_) {
        return super.getIcon(p_149673_1_, p_149673_2_, p_149673_3_, p_149673_4_, p_149673_5_);
    }

    @Override
    public void breakBlock(World world, int x, int y, int z, Block block, int meta) {
        ArrayList<ItemStack> itemToDrop = new ArrayList<>();
        if(!world.isRemote) {
            TileEntityAspectCrop cropTE = (TileEntityAspectCrop) world.getTileEntity(x, y, z);
            if(cropTE != null) {
                AspectList asp = cropTE.getAspects();
                EntityClientPlayerMP plyr = Minecraft.getMinecraft().thePlayer;
                int fortune = 0;
                if(plyr != null) {
                    ItemStack heldStack = plyr.getCurrentEquippedItem();
                    if(heldStack != null) {
                        fortune = EnchantmentHelper.getFortuneModifier(plyr) + 1;
                    }
                }

                ItemAspectSeed itemSeed = (ItemAspectSeed) aspectSeeds;
                ItemStack stackSeed = new ItemStack(itemSeed);

                ItemCrystalEssence itemCrystal = (ItemCrystalEssence) itemCrystalEssence;
                ItemStack stackCrystal = new ItemStack(itemCrystal);

                if(asp != null) {
                    itemSeed.setAspects(stackSeed, new AspectList().add(asp.getAspects()[0],2));
                    itemToDrop.add(stackSeed);
                    int amountAspectBonus = cropTE.getAspects().size() - 1;
                    if(meta >= this.stages-1) {
                        itemCrystal.setAspects(stackCrystal, asp);
                        for (int i = 0; i < 4+(fortune+amountAspectBonus); ++i) {
                            if (world.rand.nextInt(15-fortune) <= meta) {
                                stackCrystal.stackSize++;
                            }
                        }
                        itemToDrop.add(stackCrystal);
                    }
                }
                dropItems(itemToDrop, world, x, y, z);
                super.breakBlock(world, x, y, z, block, meta);
            }
        }
    }

    @Override
    public void updateTick(World world, int x, int y, int z, Random rand) {
        super.updateTick(world, x, y, z, rand);

        TileEntity te = world.getTileEntity(x,y,z);
        if(te instanceof TileEntityAspectCrop) {
            TileEntityAspectCrop cropTE = (TileEntityAspectCrop) te;
            if(world.getBlockMetadata(x,y,z) >= this.stages-1) {
                if(cropTE.getAspectAmount(cropTE.getAspects().getAspects()[0]) < 8) {
                    cropTE.addAspect(1);
                    cropTE.setEnableFX(true);
                    cropTE.markDirty();
                    world.markBlockForUpdate(x, y, z);
                }
            }
        }
    }
}
