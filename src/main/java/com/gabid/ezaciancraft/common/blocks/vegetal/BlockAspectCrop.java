package com.gabid.ezaciancraft.common.blocks.vegetal;

import com.gabid.ezaciancraft.api.common.blocks.EzacianCustomBlockCrop;
import com.gabid.ezaciancraft.common.items.vegetal.ItemAspectSeed;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import thaumcraft.api.WorldCoordinates;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.common.items.ItemCrystalEssence;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import static com.gabid.ezaciancraft.CoreMod.MODID;
import static com.gabid.ezaciancraft.api.EzacianCraftGeneralLang.UNLOCALE_ASPECT_CROP_BASENAME;
import static com.gabid.ezaciancraft.registry.EzacianCraftItems.aspectSeeds;
import static com.gabid.ezaciancraft.registry.EzacianCraftTypeRenders.ASPECT_CROP_BLOCK_RENDER_ID;
import static thaumcraft.common.config.ConfigItems.itemCrystalEssence;

public class BlockAspectCrop extends EzacianCustomBlockCrop {
    public IIcon colouredParts;
    public static HashMap<WorldCoordinates, AspectList> aspectCoordsData = new HashMap<>(); //used for getting the aspects when the block is broken, helps for the use of getDrops (more compatible) instead of breakBlock

    public BlockAspectCrop() {
        super(UNLOCALE_ASPECT_CROP_BASENAME, 4, aspectSeeds, itemCrystalEssence, 12, ASPECT_CROP_BLOCK_RENDER_ID);
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
    public void breakBlock(World world, int x, int y, int z, Block block, int meta) {
        TileEntity te = world.getTileEntity(x,y,z);
        if(te instanceof TileEntityAspectCrop) {
            TileEntityAspectCrop teCrop = (TileEntityAspectCrop) te;
            if(teCrop.mainAspect != null && teCrop.aspectAmount > 0) {
                aspectCoordsData.put(new WorldCoordinates(x,y,z, world.provider.dimensionId), new AspectList().add(teCrop.mainAspect, teCrop.aspectAmount));
            }
        }
        super.breakBlock(world, x, y, z, block, meta);
    }

    @Override
    public int getLightValue(IBlockAccess world, int x, int y, int z) {
        switch (world.getBlockMetadata(x, y, z)) {
            case 1: return 2;
            case 2: return 4;
            case 3: return 6;
            default: return 0;
        }
    }

    @Override
    public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune) {
        ArrayList<ItemStack> itemToDrop = new ArrayList<>();

        ItemAspectSeed itemSeed = (ItemAspectSeed) this.func_149866_i();
        ItemStack stackSeed = new ItemStack(itemSeed);

        ItemCrystalEssence itemCrystal = (ItemCrystalEssence) this.func_149865_P();
        ItemStack stackCrystal = new ItemStack(itemCrystal);

        AspectList asps = new AspectList().add(Aspect.PLANT, 1);

        if(aspectCoordsData.get(new WorldCoordinates(x,y,z,world.provider.dimensionId)) != null) {
            asps.remove(Aspect.PLANT);
            asps = aspectCoordsData.get(new WorldCoordinates(x,y,z,world.provider.dimensionId));
        } else if(world.getTileEntity(x,y,z) instanceof TileEntityAspectCrop) {
            TileEntityAspectCrop cropTE = (TileEntityAspectCrop) world.getTileEntity(x, y, z);
            if (cropTE.getMainAspect() != null) {
                asps.remove(Aspect.PLANT);
                asps = new AspectList().add(cropTE.getMainAspect(), cropTE.getAspectAmount());
            }
        }

        if(asps != null) {
            itemSeed.setAspects(stackSeed, new AspectList().add(asps.getAspects()[0],2));
            itemToDrop.add(stackSeed);
            int amountAspectBonus = asps.size() - 1;
            if(metadata >= this.stages-1) {
                itemCrystal.setAspects(stackCrystal, asps);
                for (int i = 0; i < 4+(fortune+amountAspectBonus); ++i) {
                    if (world.rand.nextInt(15-fortune) <= metadata) {
                        stackCrystal.stackSize++;
                    }
                }
                itemToDrop.add(stackCrystal);
            }
        }
        aspectCoordsData.remove(new WorldCoordinates(x,y,z, world.provider.dimensionId));
        return itemToDrop;
    }

    //fertilize
    @Override
    public void func_149863_m(World world, int x, int y, int z) {
        Random rand = world.rand;
        if (!world.isRemote) {
            TileEntity te = world.getTileEntity(x, y, z);

            if (te instanceof TileEntityAspectCrop) {
                TileEntityAspectCrop crop = (TileEntityAspectCrop) te;
                crop.onGrow(world.getBlockMetadata(x, y, z), this.stages, x, y, z, world, rand);
            }
        }
    }

    @Override
    public void updateTick(World world, int x, int y, int z, Random rand) {
        TileEntity te = world.getTileEntity(x,y,z);
        if(te instanceof TileEntityAspectCrop) {
            TileEntityAspectCrop cropTE = (TileEntityAspectCrop) te;
            if (world.getBlockLightValue(x, y + 1, z) >= this.lightRequiredToGrow) {
                int meta = world.getBlockMetadata(x, y, z);
                this.applyIndirectGrow(world, x, y, z, cropTE, meta, 10f);
                aspectCoordsData.remove(new WorldCoordinates(x, y, z, world.provider.dimensionId));
            }
        }
    }

    public void applyIndirectGrow(World world, int x, int y, int z, TileEntityAspectCrop te, int meta, float ratio) {
        float f = this.getGrowthRate(world, x, y, z);

        int chance = world.rand.nextInt((int) (ratio / f) - 1);
        if(meta < this.stages-1) {
            if (chance == 0) {
                ++meta;
                world.setBlockMetadataWithNotify(x, y, z, meta, 3);
                te.markDirty();
                world.markBlockRangeForRenderUpdate(x,y,z,x,y,z);
            }
        } else {
            if (chance == 0) {
                te.addAspect(1);
            }
        }
    }
}
