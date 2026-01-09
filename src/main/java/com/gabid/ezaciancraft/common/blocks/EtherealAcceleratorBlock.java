package com.gabid.ezaciancraft.common.blocks;

import com.gabid.ezaciancraft.common.blocks.tileentity.EtherealAcceleratorTE;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import static com.gabid.ezaciancraft.api.EzacianCraftGeneralLang.UNLOCALE_ETHEREAL_ACCELERATOR;

public class EtherealAcceleratorBlock extends BlockContainer {
    public EtherealAcceleratorBlock() {
        super(Material.air);
        this.setBlockName(UNLOCALE_ETHEREAL_ACCELERATOR);
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
    public boolean isAir(IBlockAccess world, int x, int y, int z) {
        return true;
    }

    @Override
    public boolean isLeaves(IBlockAccess world, int x, int y, int z) {
        return true;
    }

    @Override
    public boolean isReplaceable(IBlockAccess world, int x, int y, int z) {
        return false;
    }

    @Override
    public void onBlockAdded(World level, int x, int y, int z) {
        level.setTileEntity(x, y, z, this.createNewTileEntity(level, level.getBlockMetadata(x, y, z)));
    }

    @Override
    public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
        return new EtherealAcceleratorTE(16);
    }
}
