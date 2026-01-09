package com.gabid.ezaciancraft.common.blocks;

import com.gabid.ezaciancraft.common.blocks.tileentity.AlchemicalMixerTileEntity;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import thaumcraft.api.aspects.Aspect;

import java.util.Objects;

import static com.gabid.ezaciancraft.api.EzacianCraftGeneralLang.UNLOCALE_ALCHEMICAL_MIXER;
import static thaumcraft.common.Thaumcraft.MODID;

public class AlchemicalMixerBlock extends BlockContainer {

    public IIcon alchemicalMixerIcon = null;

    public AlchemicalMixerBlock() {
        super(Material.iron);
        this.setBlockName(UNLOCALE_ALCHEMICAL_MIXER);
        this.setHardness(4f);
        this.setResistance(.5f);
        this.setStepSound(Block.soundTypeMetal);
        this.setHarvestLevel("pickaxe", 2);
    }

    @Override
    public void registerBlockIcons(IIconRegister register) {
        this.alchemicalMixerIcon = register.registerIcon(new ResourceLocation(MODID, "pipe_1").toString());
    }

    @Override
    public IIcon getIcon(int side, int meta) {
        return this.alchemicalMixerIcon;
    }

    @Override
    public int getRenderType() {
        return -1;
    }

    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public boolean shouldSideBeRendered(IBlockAccess p_149646_1_, int p_149646_2_, int p_149646_3_, int p_149646_4_, int p_149646_5_) {
        return true;
    }

    @Override
    public int damageDropped(int meta) {
        return meta;
    }

    /*@Override
    public boolean hasComparatorInputOverride() {
        return true;
    }*/

    @Override
    public void onBlockAdded(World level, int x, int y, int z) {
        level.setTileEntity(x, y, z, this.createNewTileEntity(level, level.getBlockMetadata(x, y, z)));
    }

    @Override
    public TileEntity createNewTileEntity(World level, int meta) {
        return new AlchemicalMixerTileEntity();
    }

    @Override
    public void onBlockPlacedBy(World level, int x, int y, int z, EntityLivingBase player, ItemStack stack) {
        int l = MathHelper.floor_double((double)(player.rotationYaw * 4.0F / 360.0F) + 0.5) & 3;
        TileEntity te = level.getTileEntity(x, y, z);

        if(te instanceof AlchemicalMixerTileEntity) {
            if (l == 0) {
                ((AlchemicalMixerTileEntity) te).metaFacing = 2;
            }
            if (l == 1) {
                ((AlchemicalMixerTileEntity) te).metaFacing = 5;
            }
            if (l == 2) {
                ((AlchemicalMixerTileEntity) te).metaFacing = 3;
            }
            if(l == 3) {
                ((AlchemicalMixerTileEntity) te).metaFacing = 4;
            }
        }
    }
}
