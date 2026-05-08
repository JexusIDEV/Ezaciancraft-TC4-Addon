package com.gabid.ezaciancraft.common.blocks;

import com.gabid.ezaciancraft.common.blocks.tileentity.TileEntityWirelessEssentiaInterfaceInput;
import com.gabid.ezaciancraft.common.blocks.tileentity.TileEntityWirelessEssentiaInterfaceOutput;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.List;

import static com.gabid.ezaciancraft.api.EzacianCraftGeneralLang.UNLOCALE_WIRELESS_ESSENTIA_INTERFACE;
import static thaumcraft.common.Thaumcraft.MODID;

public class BlockWirelessEssentiaInterface extends BlockContainer {

    public IIcon bWEIIcon;

    public BlockWirelessEssentiaInterface() {
        super(Material.iron);
        this.setStepSound(Block.soundTypeMetal);
        this.setBlockName(UNLOCALE_WIRELESS_ESSENTIA_INTERFACE);
        this.setHarvestLevel("pickaxe", 2);
    }

    @Override
    public void getSubBlocks(Item item, CreativeTabs tabs, List list) {
        list.add(new ItemStack(item, 1, 0));
        list.add(new ItemStack(item, 1, 1));
    }

    @Override
    public void registerBlockIcons(IIconRegister register) {
        this.bWEIIcon = register.registerIcon(new ResourceLocation(MODID, "pipe_1").toString());
    }

    @Override
    public IIcon getIcon(int side, int meta) {
        return this.bWEIIcon;
    }

    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }

    @Override
    public int getRenderType() {
        return -1;
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta) {
        if (meta == 0) {
            return new TileEntityWirelessEssentiaInterfaceOutput();
        } else if (meta == 1) {
            return new TileEntityWirelessEssentiaInterfaceInput();
        }
        return null;
    }

    @Override
    public AxisAlignedBB getSelectedBoundingBoxFromPool(World world, int x, int y, int z) {
        TileEntity te = world.getTileEntity(x, y, z);
        if (te instanceof TileEntityWirelessEssentiaInterfaceInput) {
            TileEntityWirelessEssentiaInterfaceInput wei = (TileEntityWirelessEssentiaInterfaceInput) te;
            ForgeDirection facing = wei.facing;
            switch (facing) {
                case NORTH:
                    this.setBlockBounds(0f, 0f, 0f, 1f, 1f, .5f);
                    break;
                case SOUTH:
                    this.setBlockBounds(0f, 0f, 0.5f, 1f, 1f, 1f);
                    break;
                case EAST:
                    this.setBlockBounds(0.5f, 0f, 0f, 1f, 1f, 1f);
                    break;
                case WEST:
                    this.setBlockBounds(0f, 0f, 0f, .5f, 1f, 1f);
                    break;
                case UP:
                    this.setBlockBounds(0f, 0.5f, 0f, 1f, 1f, 1f);
                    break;
                case DOWN:
                    this.setBlockBounds(0f, 0f, 0f, 1f, 0.5f, 1f);
                    break;
            }
        }
        return super.getSelectedBoundingBoxFromPool(world, x, y, z);
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z) {
        TileEntity te = world.getTileEntity(x, y, z);
        if (te instanceof TileEntityWirelessEssentiaInterfaceInput) {
            TileEntityWirelessEssentiaInterfaceInput wei = (TileEntityWirelessEssentiaInterfaceInput) te;
            ForgeDirection facing = wei.facing;
            switch (facing) {
                case NORTH:
                    this.setBlockBounds(0f, 0f, 0f, 1f, 1f, .5f);
                    break;
                case SOUTH:
                    this.setBlockBounds(0f, 0f, 0.5f, 1f, 1f, 1f);
                    break;
                case EAST:
                    this.setBlockBounds(0.5f, 0f, 0f, 1f, 1f, 1f);
                    break;
                case WEST:
                    this.setBlockBounds(0f, 0f, 0f, .5f, 1f, 1f);
                    break;
                case UP:
                    this.setBlockBounds(0f, 0.5f, 0f, 1f, 1f, 1f);
                    break;
                case DOWN:
                    this.setBlockBounds(0f, 0f, 0f, 1f, 0.5f, 1f);
                    break;
            }
        }
        return super.getCollisionBoundingBoxFromPool(world, x, y, z);
    }
}
