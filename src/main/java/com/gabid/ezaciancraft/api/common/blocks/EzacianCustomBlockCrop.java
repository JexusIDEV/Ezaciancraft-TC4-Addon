package com.gabid.ezaciancraft.api.common.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.ArrayList;
import java.util.Random;

import static com.gabid.ezaciancraft.CoreMod.MODID;

public class EzacianCustomBlockCrop extends BlockCrops {
    public final String cropName;
    protected final Item cropSeedItem;
    protected final Item cropResult;
    public int stages;
    public IIcon[] plantStagesIcons;
    public int lightRequiredToGrow;
    public int renderTypeId;

    public EzacianCustomBlockCrop(String _cropName, int _stages, Item _cropSeedItem, Item _cropResult, int _lightRequiredToGrow, int _renderTypeId) {
        super();
        this.setStepSound(EzacianCustomBlockCrop.soundTypeGrass);
        this.cropName = _cropName;
        if (_stages <= 0) {
            _stages = 1;
        }
        this.stages = _stages;
        this.setBlockName(_cropName);
        this.cropSeedItem = _cropSeedItem;
        this.cropResult = _cropResult;
        this.lightRequiredToGrow = _lightRequiredToGrow;
        this.renderTypeId = _renderTypeId;
    }

    @Override
    public int getRenderType() {
        return this.renderTypeId;
    }

    @Override
    public boolean getTickRandomly() {
        return super.getTickRandomly();
    }

    @Override
    public void updateTick(World world, int x, int y, int z, Random rand) {
        if (world.getBlockLightValue(x, y + 1, z) >= this.lightRequiredToGrow) {
            int l = world.getBlockMetadata(x, y, z);

            if (l < this.stages - 1) {
                float f = this.getGrowthRate(world, x, y, z);

                if (rand.nextInt((int) (25.0F / f) + 1) == 0) {
                    ++l;
                    world.setBlockMetadataWithNotify(x, y, z, l, 3);
                }
            }
        }
    }

    public float getGrowthRate(World world, int x, int y, int z) {
        float f = 1.0F;
        Block block = world.getBlock(x, y, z - 1);
        Block block1 = world.getBlock(x, y, z + 1);
        Block block2 = world.getBlock(x - 1, y, z);
        Block block3 = world.getBlock(x + 1, y, z);
        Block block4 = world.getBlock(x - 1, y, z - 1);
        Block block5 = world.getBlock(x + 1, y, z - 1);
        Block block6 = world.getBlock(x + 1, y, z + 1);
        Block block7 = world.getBlock(x - 1, y, z + 1);
        boolean flag = block2 == this || block3 == this;
        boolean flag1 = block == this || block1 == this;
        boolean flag2 = block4 == this || block5 == this || block6 == this || block7 == this;

        for (int l = x - 1; l <= x + 1; ++l) {
            for (int i1 = y - 1; i1 <= y + 1; ++i1) {
                float f1 = 0.0F;

                if (world.getBlock(l, y - 1, i1).canSustainPlant(world, l, y - 1, i1, ForgeDirection.UP, this)) {
                    f1 = 1.0F;

                    if (world.getBlock(l, y - 1, i1).isFertile(world, l, y - 1, i1)) {
                        f1 = 3.0F;
                    }
                }
                if (l != x || i1 != z) {
                    f1 /= 4.0F;
                }

                f += f1;
            }
        }
        if (flag2 || flag && flag1) {
            f /= 2.0F;
        }
        return f;
    }

    //seeds
    @Override
    protected Item func_149866_i() {
        return this.cropSeedItem;
    }

    //crop developed item
    @Override
    protected Item func_149865_P() {
        return this.cropResult;
    }

    @Override
    public Item getItemDropped(int meta, Random rand, int p_149650_3_) {
        return meta == (this.stages - 1) ? this.func_149865_P() : this.func_149866_i();
    }

    @Override
    public Item getItem(World p_149694_1_, int p_149694_2_, int p_149694_3_, int p_149694_4_) {
        return this.func_149866_i();
    }

    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta) {
        if (meta < 0 || meta > this.stages - 1) {
            meta = this.stages - 1;
        }
        return this.plantStagesIcons[meta];
    }

    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister register) {
        this.plantStagesIcons = new IIcon[this.stages];

        for (int i = 0; i < this.plantStagesIcons.length; ++i) {
            this.plantStagesIcons[i] = register.registerIcon(new ResourceLocation(MODID, this.cropName + "_stage_" + i).toString());
        }
    }

    @Override
    public EnumPlantType getPlantType(IBlockAccess world, int x, int y, int z) {
        return EnumPlantType.Crop;
    }

    @Override
    public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune) {
        ArrayList<ItemStack> ret = super.getDrops(world, x, y, z, metadata, fortune);

        if(this.cropResult != null || this.cropSeedItem != null) {
            if (metadata >= this.stages - 1) {
                for (int i = 0; i < 3 + fortune; ++i) {
                    if (world.rand.nextInt(15) <= metadata) {
                        ret.add(new ItemStack(this.func_149866_i(), 1, 0));
                    }
                }
            }
        }
        return ret;
    }

    //fertilize, the BlockCrops method
    @Override
    public void func_149863_m(World world, int x, int y, int z) {
        int l = world.getBlockMetadata(x, y, z) + MathHelper.getRandomIntegerInRange(world.rand, 2, 5);

        if (l > this.stages-1)
        {
            l = this.stages-1;
        }

        world.setBlockMetadataWithNotify(x, y, z, l, 3);
    }

    //can fertilize
    @Override
    public boolean func_149851_a(World world, int x, int y, int z, boolean flag) {
        int meta = world.getBlockMetadata(x, y, z);
        return meta < this.stages-1;
    }

    //should fertilize
    @Override
    public boolean func_149852_a(World world, Random rand, int x, int y, int z) {
        return super.func_149852_a(world, rand, x, y, z);
    }

    //fertilize, the IGrowable method
    @Override
    public void func_149853_b(World world, Random rand, int x, int y, int z) {
        super.func_149853_b(world, rand, x, y, z);
    }
}