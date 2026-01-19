package com.gabid.ezaciancraft.common.blocks;

import com.gabid.ezaciancraft.common.blocks.tileentity.AlchemicalMixerTileEntity;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import static com.gabid.ezaciancraft.api.EzacianCraftGeneralLang.UNLOCALE_ALCHEMICAL_MIXER;
import static com.gabid.ezaciancraft.registry.EzacianCraftTypeRenders.ALCHEMICAL_MIXER_RENDER_ID;
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
        return ALCHEMICAL_MIXER_RENDER_ID;
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
    public boolean hasComparatorInputOverride() {
        return true;
    }

    @Override
    public int getComparatorInputOverride(World level, int x, int y, int z, int side) {
        TileEntity te = level.getTileEntity(x,y,z);
        if(te instanceof AlchemicalMixerTileEntity) {
            AlchemicalMixerTileEntity alchemicalTE = (AlchemicalMixerTileEntity) te;
            int stored = alchemicalTE.getAspects().size();
            if(stored > 0) {
                return stored * 5;
            } else {
                return 0;
            }
        } else {
            return 0;
        }
    }

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

    //magic numbers? nahhhhh

    @Override
    public AxisAlignedBB getSelectedBoundingBoxFromPool(World p_149633_1_, int p_149633_2_, int p_149633_3_, int p_149633_4_) {
        this.setBlockBounds(0.185f,0.125f,0.185f,0.815f,0.875f,0.815f);
        return super.getSelectedBoundingBoxFromPool(p_149633_1_, p_149633_2_, p_149633_3_, p_149633_4_);
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World p_149668_1_, int p_149668_2_, int p_149668_3_, int p_149668_4_) {
        this.setBlockBounds(0.185f,0.125f,0.185f,0.815f,0.875f,0.815f);
        return super.getCollisionBoundingBoxFromPool(p_149668_1_, p_149668_2_, p_149668_3_, p_149668_4_);
    }
}
