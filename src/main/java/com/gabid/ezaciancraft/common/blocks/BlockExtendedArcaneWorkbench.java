package com.gabid.ezaciancraft.common.blocks;

import com.gabid.ezaciancraft.CoreMod;
import com.gabid.ezaciancraft.common.blocks.tileentity.TileEntityExtendedArcaneWorkbench;
import com.gabid.ezaciancraft.registry.EzacianCraftBlocks;
import com.gabid.ezaciancraft.registry.EzacianCraftGUIContainerEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import thaumcraft.common.config.ConfigBlocks;

import java.util.ArrayList;
import java.util.List;

import static com.gabid.ezaciancraft.CoreMod.MODID;
import static com.gabid.ezaciancraft.api.EzacianCraftGeneralLang.UNLOCALE_EXTENDED_ARCANE_WORKBENCH;
import static com.gabid.ezaciancraft.lib.world.ContainerInventoryHelperUtils.dropInventoryItemsWithExclusion;

public class BlockExtendedArcaneWorkbench extends BlockContainer {

    public static Block masterBlock = EzacianCraftBlocks.ezacianStoneDecorativeBlocks;
    public static Block arcaneDeco = ConfigBlocks.blockCosmeticSolid;
    public static Block[] multiblockBlueprint = {arcaneDeco, arcaneDeco, arcaneDeco, arcaneDeco, masterBlock, arcaneDeco, arcaneDeco, arcaneDeco, arcaneDeco}; //9
    public static int[] multiblockMetaDatas = {7, 6, 7, 6, 0, 6, 7, 6, 7};
    public IIcon baseTex;
    public IIcon[] multiblockIcons = new IIcon[10];
    private int currentRender = 0;

    public BlockExtendedArcaneWorkbench() {
        super(Material.rock);
        this.setBlockName(UNLOCALE_EXTENDED_ARCANE_WORKBENCH);
        this.setHardness(3f);
        this.setResistance(3f);
        this.setHarvestLevel("pickaxe", 0);
    }

    @Override
    public void getSubBlocks(Item p_149666_1_, CreativeTabs p_149666_2_, List p_149666_3_) {
    }

    @Override
    public int getRenderType() {
        return this.currentRender;
    }

    @Override
    public boolean isOpaqueCube() {
        return this.getRenderType() == 0;
    }

    @Override
    public boolean renderAsNormalBlock() {
        return this.getRenderType() == 0;
    }

    @Override
    public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune) {
        ArrayList<ItemStack> drops = new ArrayList<>();

        drops.add(new ItemStack(multiblockBlueprint[metadata], 1, multiblockMetaDatas[metadata]));

        return drops;
    }

    @Override
    public void registerBlockIcons(IIconRegister register) {
        super.registerBlockIcons(register);
        this.baseTex = register.registerIcon(new ResourceLocation(MODID, "ezacianDecorartiveBlock_0").toString());

        this.multiblockIcons[0] = register.registerIcon(new ResourceLocation(MODID, "advancedArcaneWorkbench_horizontal_sides").toString());

        for (int i = 1; i < 10; i++) {
            int correctorIndex = i - 1;
            this.multiblockIcons[i] = register.registerIcon(new ResourceLocation(MODID, "advancedArcaneWorkbench_top_bottom_" + correctorIndex).toString());
        }
    }

    @Override
    public IIcon getIcon(int side, int meta) {
        return this.baseTex;
    }

    @Override
    public IIcon getIcon(IBlockAccess worldAccess, int x, int y, int z, int side) {
        return this.calculateTexture(worldAccess, x, y, z, side);
    }

    //roughly hardcoded, but this is how I can do it...
    @SideOnly(Side.CLIENT)
    public IIcon calculateTexture(IBlockAccess world, int x, int y, int z, int side) {
        int meta = world.getBlockMetadata(x, y, z);

        switch (side) {
            case 0: //DOWN
                if (meta == 0) {
                    return this.multiblockIcons[1];
                }
                if (meta == 1) {
                    return this.multiblockIcons[4];
                }
                if (meta == 2) {
                    return this.multiblockIcons[7];
                }
                if (meta == 3) {
                    return this.multiblockIcons[2];
                }
                if (meta == 4) {
                    return this.multiblockIcons[5];
                }
                if (meta == 5) {
                    return this.multiblockIcons[8];
                }
                if (meta == 6) {
                    return this.multiblockIcons[3];
                }
                if (meta == 7) {
                    return this.multiblockIcons[6];
                }
                if (meta == 8) {
                    return this.multiblockIcons[9];
                }
                break;
            case 1: //UP
                if (meta == 0) {
                    return this.multiblockIcons[1];
                }
                if (meta == 1) {
                    return this.multiblockIcons[4];
                }
                if (meta == 2) {
                    return this.multiblockIcons[7];
                }
                if (meta == 3) {
                    return this.multiblockIcons[2];
                }
                if (meta == 4) {
                    return this.multiblockIcons[5];
                }
                if (meta == 5) {
                    return this.multiblockIcons[8];
                }
                if (meta == 6) {
                    return this.multiblockIcons[3];
                }
                if (meta == 7) {
                    return this.multiblockIcons[6];
                }
                if (meta == 8) {
                    return this.multiblockIcons[9];
                }
                break;
            case 2: //NORTH
                if (meta == 0 || meta == 3 || meta == 6) {
                    return this.multiblockIcons[0];
                }
                break;
            case 3: //SOUTH
                if (meta == 2 || meta == 5 || meta == 8) {
                    return this.multiblockIcons[0];
                }
                break;
            case 4: //EAST
                if (meta == 0 || meta == 1 || meta == 2) {
                    return this.multiblockIcons[0];
                }
                break;
            case 5: //WEST
                if (meta == 6 || meta == 7 || meta == 8) {
                    return this.multiblockIcons[0];
                }
                break;
            default:
                return this.baseTex;
        }
        return this.baseTex;
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta) {
        if (meta == 4) {
            this.currentRender = -1;
            return new TileEntityExtendedArcaneWorkbench();
        } else {
            this.currentRender = 0;
            return null;
        }
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int p_149727_6_, float p_149727_7_, float p_149727_8_, float p_149727_9_) {
        if (!player.isSneaking()) {
            for (int xOffset = -1; xOffset <= 1; xOffset++) {
                for (int zOffset = -1; zOffset <= 1; zOffset++) {
                    TileEntity teSearch = world.getTileEntity(x + xOffset, y, z + zOffset);
                    if (teSearch instanceof TileEntityExtendedArcaneWorkbench) {
                        player.openGui(CoreMod.instance, EzacianCraftGUIContainerEvent.GuiID.EXTENDED_ARCANE_WORKBENCH.ordinal(), world, x + xOffset, y, z + zOffset);
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public void breakBlock(World world, int x, int y, int z, Block block, int meta) {
        if (!world.isRemote) {
            if (meta != 4) {
                for (int xOffset = -1; xOffset <= 1; xOffset++) {
                    for (int zOffset = -1; zOffset <= 1; zOffset++) {
                        if (world.getBlock(x + xOffset, y, z + zOffset) == this && world.getBlockMetadata(x + xOffset, y, z + zOffset) == 4) {
                            TileEntityExtendedArcaneWorkbench tile = (TileEntityExtendedArcaneWorkbench) world.getTileEntity(x + xOffset, y, z + zOffset);
                            if (tile != null) {
                                dropInventoryItemsWithExclusion(tile, world, new int[]{9}, x, y, z);
                                tile.destroyMultiblock();
                                return;
                            }
                        }
                    }
                }
            } else {
                TileEntityExtendedArcaneWorkbench arcaneTE = (TileEntityExtendedArcaneWorkbench) world.getTileEntity(x, y, z);
                if (arcaneTE != null) {
                    dropInventoryItemsWithExclusion(arcaneTE, world, new int[]{9}, x, y, z);
                }
                for (int xOffset = -1; xOffset <= 1; xOffset++) {
                    for (int zOffset = -1; zOffset <= 1; zOffset++) {
                        if ((xOffset != 0 || zOffset != 0) && world.getBlock(x + xOffset, y, z + zOffset) == this) {
                            int currentBlockMeta = world.getBlockMetadata(x + xOffset, y, z + zOffset);
                            world.setBlock(x + xOffset, y, z + zOffset, Block.getBlockFromItem(new ItemStack(multiblockBlueprint[currentBlockMeta]).getItem()), multiblockMetaDatas[currentBlockMeta], 3);
                        }
                    }
                }
                world.playSoundEffect(x + .5f, y + .5f, z + .5f, "thaumcraft:craftfail", 1f, 1f);
            }
        }
    }
}
