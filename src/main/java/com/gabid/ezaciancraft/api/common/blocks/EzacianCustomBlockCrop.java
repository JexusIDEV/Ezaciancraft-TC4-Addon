package com.gabid.ezaciancraft.api.common.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.IGrowable;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
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
import java.util.List;
import java.util.Random;

import static com.gabid.ezaciancraft.CoreMod.MODID;

public class EzacianCustomBlockCrop extends BlockCrops {
    public final String cropName;
    public int stages;
    public IIcon[] plantStagesIcons;
    protected final Item cropSeedItem;
    protected final Item cropResult;

    public EzacianCustomBlockCrop(String _cropName, int _stages, Item _cropSeedItem, Item _cropResult, CreativeTabs tabToRegister) {
        super();
        this.setStepSound(EzacianCustomBlockCrop.soundTypeGrass);
        this.cropName = _cropName;
        if(_stages <= 0) {
            _stages = 1;
        }
        this.stages = _stages;
        this.setBlockName(_cropName);
        this.cropSeedItem = _cropSeedItem;
        this.cropResult = _cropResult;
    }

    @Override
    public boolean getTickRandomly() {
        return super.getTickRandomly();
    }

    @Override
    public void updateTick(World p_149674_1_, int p_149674_2_, int p_149674_3_, int p_149674_4_, Random p_149674_5_) {
        super.updateTick(p_149674_1_, p_149674_2_, p_149674_3_, p_149674_4_, p_149674_5_);

        if (p_149674_1_.getBlockLightValue(p_149674_2_, p_149674_3_ + 1, p_149674_4_) >= 9)
        {
            int l = p_149674_1_.getBlockMetadata(p_149674_2_, p_149674_3_, p_149674_4_);

            if (l < this.stages-1)
            {
                float f = this.func_149864_n(p_149674_1_, p_149674_2_, p_149674_3_, p_149674_4_);

                if (p_149674_5_.nextInt((int)(25.0F / f) + 1) == 0)
                {
                    ++l;
                    p_149674_1_.setBlockMetadataWithNotify(p_149674_2_, p_149674_3_, p_149674_4_, l, 2);
                }
            }
        }
    }

    @Override
    public void func_149863_m(World p_149863_1_, int p_149863_2_, int p_149863_3_, int p_149863_4_) {
        int l = p_149863_1_.getBlockMetadata(p_149863_2_, p_149863_3_, p_149863_4_) + MathHelper.getRandomIntegerInRange(p_149863_1_.rand, 2, 5);

        if (l > this.stages-1)
        {
            l = this.stages-1;
        }

        p_149863_1_.setBlockMetadataWithNotify(p_149863_2_, p_149863_3_, p_149863_4_, l, 2);
    }

    private float func_149864_n(World p_149864_1_, int p_149864_2_, int p_149864_3_, int p_149864_4_)
    {
        float f = 1.0F;
        Block block = p_149864_1_.getBlock(p_149864_2_, p_149864_3_, p_149864_4_ - 1);
        Block block1 = p_149864_1_.getBlock(p_149864_2_, p_149864_3_, p_149864_4_ + 1);
        Block block2 = p_149864_1_.getBlock(p_149864_2_ - 1, p_149864_3_, p_149864_4_);
        Block block3 = p_149864_1_.getBlock(p_149864_2_ + 1, p_149864_3_, p_149864_4_);
        Block block4 = p_149864_1_.getBlock(p_149864_2_ - 1, p_149864_3_, p_149864_4_ - 1);
        Block block5 = p_149864_1_.getBlock(p_149864_2_ + 1, p_149864_3_, p_149864_4_ - 1);
        Block block6 = p_149864_1_.getBlock(p_149864_2_ + 1, p_149864_3_, p_149864_4_ + 1);
        Block block7 = p_149864_1_.getBlock(p_149864_2_ - 1, p_149864_3_, p_149864_4_ + 1);
        boolean flag = block2 == this || block3 == this;
        boolean flag1 = block == this || block1 == this;
        boolean flag2 = block4 == this || block5 == this || block6 == this || block7 == this;

        for (int l = p_149864_2_ - 1; l <= p_149864_2_ + 1; ++l)
        {
            for (int i1 = p_149864_4_ - 1; i1 <= p_149864_4_ + 1; ++i1)
            {
                float f1 = 0.0F;

                if (p_149864_1_.getBlock(l, p_149864_3_ - 1, i1).canSustainPlant(p_149864_1_, l, p_149864_3_ - 1, i1, ForgeDirection.UP, this))
                {
                    f1 = 1.0F;

                    if (p_149864_1_.getBlock(l, p_149864_3_ - 1, i1).isFertile(p_149864_1_, l, p_149864_3_ - 1, i1))
                    {
                        f1 = 3.0F;
                    }
                }

                if (l != p_149864_2_ || i1 != p_149864_4_)
                {
                    f1 /= 4.0F;
                }

                f += f1;
            }
        }

        if (flag2 || flag && flag1)
        {
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
        return meta == (this.stages-1) ? this.func_149865_P() : this.func_149866_i();
    }

    @Override
    public Item getItem(World p_149694_1_, int p_149694_2_, int p_149694_3_, int p_149694_4_) {
        return this.func_149866_i();
    }

    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta) {
        if (meta < 0 || meta > this.stages-1) {
            meta = this.stages-1;
        }
        return this.plantStagesIcons[meta];
    }

    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister register) {
        this.plantStagesIcons = new IIcon[this.stages];

        for (int i = 0; i < this.plantStagesIcons.length; ++i)
        {
            this.plantStagesIcons[i] = register.registerIcon(new ResourceLocation(MODID,this.cropName + "_stage_" + i).toString());
        }
    }

    @Override
    public EnumPlantType getPlantType(IBlockAccess world, int x, int y, int z) {
        return EnumPlantType.Crop;
    }

    @Override
    public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune) {
        ArrayList<ItemStack> ret = super.getDrops(world, x, y, z, metadata, fortune);

        if (metadata >= this.stages-1) {
            for (int i = 0; i < 3 + fortune; ++i) {
                if (world.rand.nextInt(15) <= metadata) {
                    ret.add(new ItemStack(this.func_149866_i(), 1, 0));
                }
            }
        }
        return ret;
    }
}
