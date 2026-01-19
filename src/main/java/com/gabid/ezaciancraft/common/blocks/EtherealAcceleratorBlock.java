package com.gabid.ezaciancraft.common.blocks;

import com.gabid.ezaciancraft.common.blocks.tileentity.EtherealAcceleratorTE;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import static com.gabid.ezaciancraft.api.EzacianCraftGeneralLang.UNLOCALE_ETHEREAL_ACCELERATOR;
import static com.gabid.ezaciancraft.registry.EzacianCraftTypeRenders.ETHEREAL_ACCELERATOR_RENDER_ID;

public class EtherealAcceleratorBlock extends BlockContainer {
    public EtherealAcceleratorBlock() {
        super(Material.air);
        this.setBlockName(UNLOCALE_ETHEREAL_ACCELERATOR);
    }

    @Override
    public boolean isBlockSolid(IBlockAccess p_149747_1_, int p_149747_2_, int p_149747_3_, int p_149747_4_, int p_149747_5_) {
        return false;
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }

    @Override
    public int getRenderType() {
        return ETHEREAL_ACCELERATOR_RENDER_ID;
    }

    @Override
    public boolean isReplaceable(IBlockAccess world, int x, int y, int z) {
        return true;
    }

    @Override
    public void onBlockAdded(World level, int x, int y, int z) {
        level.setTileEntity(x, y, z, this.createNewTileEntity(level, level.getBlockMetadata(x, y, z)));
    }

    @Override
    public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
        return new EtherealAcceleratorTE(16);
    }

    @Override
    public AxisAlignedBB getSelectedBoundingBoxFromPool(World p_149633_1_, int p_149633_2_, int p_149633_3_, int p_149633_4_) {
        this.setBlockBounds(0.25f, 0.25f, 0.25f, 0.75f,0.75f,0.75f);
        return AxisAlignedBB.getBoundingBox(0.25d, 0.25d, 0.25d, 0.75d, 0.75d, 0.75d);
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World p_149668_1_, int p_149668_2_, int p_149668_3_, int p_149668_4_) {
        return null;
    }
}
