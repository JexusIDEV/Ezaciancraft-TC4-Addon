package com.gabid.ezaciancraft.common.blocks;

import com.gabid.ezaciancraft.CoreMod;
import com.gabid.ezaciancraft.common.blocks.tileentity.TileEntityShadowAlchemyFurnace;
import com.gabid.ezaciancraft.registry.EzacianCraftGUIContainerEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import thaumcraft.common.config.ConfigBlocks;
import thaumcraft.common.lib.utils.InventoryUtils;
import thaumcraft.common.tiles.TileAlchemyFurnace;

import java.util.Random;

import static com.gabid.ezaciancraft.CoreMod.MODID;
import static com.gabid.ezaciancraft.api.EzacianCraftGeneralLang.UNLOCALE_SHADOW_ALCHEMY_FURNACE;

public class BlockShadowAlchemyFurnace extends BlockContainer {

    public IIcon[] shadowAlchemyFurnaceIcons = new IIcon[6];

    public BlockShadowAlchemyFurnace() {
        super(Material.iron);
        this.setHardness(5f);
        this.setResistance(7.5f);
        this.setStepSound(Block.soundTypeMetal);
        this.setBlockName(UNLOCALE_SHADOW_ALCHEMY_FURNACE);
        this.setHarvestLevel("pickaxe", 3);
    }

    @Override
    public void registerBlockIcons(IIconRegister register) {
        this.shadowAlchemyFurnaceIcons[0] = register.registerIcon(new ResourceLocation(MODID, UNLOCALE_SHADOW_ALCHEMY_FURNACE + "_front_off").toString());
        this.shadowAlchemyFurnaceIcons[1] = register.registerIcon(new ResourceLocation(MODID, UNLOCALE_SHADOW_ALCHEMY_FURNACE + "_front_on").toString());
        this.shadowAlchemyFurnaceIcons[2] = register.registerIcon(new ResourceLocation(MODID, UNLOCALE_SHADOW_ALCHEMY_FURNACE + "_side").toString());
        this.shadowAlchemyFurnaceIcons[3] = register.registerIcon(new ResourceLocation(MODID, UNLOCALE_SHADOW_ALCHEMY_FURNACE + "_bottom").toString());
        this.shadowAlchemyFurnaceIcons[4] = register.registerIcon(new ResourceLocation(MODID, UNLOCALE_SHADOW_ALCHEMY_FURNACE + "_top").toString());
        this.shadowAlchemyFurnaceIcons[5] = register.registerIcon(new ResourceLocation(MODID, UNLOCALE_SHADOW_ALCHEMY_FURNACE + "_top_filled").toString());
    }

    @Override
    public IIcon getIcon(int side, int meta) {
        return side == 0 ? this.shadowAlchemyFurnaceIcons[3] : side == 1 ? this.shadowAlchemyFurnaceIcons[4] : side == 3 ? this.shadowAlchemyFurnaceIcons[0] : this.shadowAlchemyFurnaceIcons[2];
    }

    @Override
    public IIcon getIcon(IBlockAccess access, int x, int y, int z, int side) {
        int meta = access.getBlockMetadata(x, y, z);
        TileEntity te = access.getTileEntity(x, y, z);
        if (te instanceof TileEntityShadowAlchemyFurnace) {
            TileEntityShadowAlchemyFurnace furnaceTE = (TileEntityShadowAlchemyFurnace) te;
            if (side == 0) {
                return this.shadowAlchemyFurnaceIcons[3];
            } else if (side == 1) {
                if (furnaceTE.getEssentiaAmountVis() > 0) {
                    return this.shadowAlchemyFurnaceIcons[5];
                } else {
                    return this.shadowAlchemyFurnaceIcons[4];
                }
            } else if (meta >= 2 && meta <= 5 && side == meta) {
                if (furnaceTE.getFurnaceBurnTime() > 0) {
                    return this.shadowAlchemyFurnaceIcons[1];
                } else {
                    return this.shadowAlchemyFurnaceIcons[0];
                }
            } else {
                return this.shadowAlchemyFurnaceIcons[2];
            }
        }
        return super.getIcon(access, x, y, z, side);
    }

    @Override
    public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
        return new TileEntityShadowAlchemyFurnace();
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void updateTick(World world, int x, int y, int z, Random rand) {
        TileEntity te = world.getTileEntity(x, y, z);
        if (te instanceof TileAlchemyFurnace && ((TileAlchemyFurnace) te).isBurning()) {
            float f = (float) x + 0.5F;
            float f1 = (float) y + 0.2F + rand.nextFloat() * 5.0F / 16.0F;
            float f2 = (float) z + 0.5F;
            float f3 = 0.52F;
            float f4 = rand.nextFloat() * 0.5F - 0.25F;
            world.spawnParticle("smoke", (f - f3), f1, (f2 + f4), 0.0, 0.0, 0.0);
            world.spawnParticle("flame", (f - f3), f1, (f2 + f4), 0.0, 0.0, 0.0);
            world.spawnParticle("smoke", (f + f3), f1, (f2 + f4), 0.0, 0.0, 0.0);
            world.spawnParticle("flame", (f + f3), f1, (f2 + f4), 0.0, 0.0, 0.0);
            world.spawnParticle("smoke", (f + f4), f1, (f2 - f3), 0.0, 0.0, 0.0);
            world.spawnParticle("flame", (f + f4), f1, (f2 - f3), 0.0, 0.0, 0.0);
            world.spawnParticle("smoke", (f + f4), f1, (f2 + f3), 0.0, 0.0, 0.0);
            world.spawnParticle("flame", (f + f4), f1, (f2 + f3), 0.0, 0.0, 0.0);
        }
    }

    @Override
    public int getLightValue(IBlockAccess access, int x, int y, int z) {
        TileEntity te = access.getTileEntity(x, y, z);
        if (te instanceof TileEntityShadowAlchemyFurnace) {
            TileEntityShadowAlchemyFurnace furnaceTE = (TileEntityShadowAlchemyFurnace) te;
            if (furnaceTE.getFurnaceBurnTime() > 0) {
                return 12;
            } else {
                return 0;
            }
        }
        return 0;
    }

    @Override
    public boolean hasComparatorInputOverride() {
        return true;
    }

    @Override
    public int getComparatorInputOverride(World world, int x, int y, int z, int a) {
        TileEntity te = world.getTileEntity(x, y, z);
        return te instanceof TileEntityShadowAlchemyFurnace ? Container.calcRedstoneFromInventory((IInventory) te) : 0;
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float a, float b, float c) {
        TileEntity te = world.getTileEntity(x, y, z);
        if (te instanceof TileEntityShadowAlchemyFurnace) {
            if (!player.isSneaking()) {
                player.openGui(CoreMod.instance, EzacianCraftGUIContainerEvent.GuiID.SHADOW_ALCHEMY_FURNACE.ordinal(), world, x, y, z);
                return true;
            }
        }
        return false;
    }

    @Override
    public void onBlockDestroyedByPlayer(World p_149664_1_, int p_149664_2_, int p_149664_3_, int p_149664_4_, int p_149664_5_) {
        super.onBlockDestroyedByPlayer(p_149664_1_, p_149664_2_, p_149664_3_, p_149664_4_, p_149664_5_);
    }

    @Override
    public void onBlockHarvested(World world, int x, int y, int z, int meta, EntityPlayer player) {
        TileEntity te = world.getTileEntity(x, y, z);
        if (te instanceof TileEntityShadowAlchemyFurnace) {
            TileEntityShadowAlchemyFurnace furnaceTE = (TileEntityShadowAlchemyFurnace) te;
            if (furnaceTE.getEssentiaAmountVis() > 0 && !player.capabilities.isCreativeMode) {
                int sz = furnaceTE.visAspects.visSize() / 16;
                int q = 0;
                if (sz > 0) {
                    world.createExplosion(null, (double) x + 0.5, (double) y + 0.5, (double) z + 0.5, 4.0F, false);

                    for (int a = 0; a < 50; ++a) {
                        int xx = x + world.rand.nextInt(5) - world.rand.nextInt(5);
                        int yy = y + world.rand.nextInt(5) - world.rand.nextInt(5);
                        int zz = z + world.rand.nextInt(5) - world.rand.nextInt(5);
                        if (world.isAirBlock(xx, yy, zz)) {
                            if (yy < y) {
                                world.setBlock(xx, yy, zz, ConfigBlocks.blockFluxGoo, 8, 3);
                            } else {
                                world.setBlock(xx, yy, zz, ConfigBlocks.blockFluxGas, 8, 3);
                            }

                            if (q++ >= sz) {
                                break;
                            }
                        }
                    }
                } else {
                    InventoryUtils.dropItems(world, x, y, z);
                    this.dropBlockAsItem(world, x, y, z, new ItemStack(this, 1, 0));
                    super.onBlockHarvested(world, x, y, z, meta, player);
                }
            } else {
                InventoryUtils.dropItems(world, x, y, z);
                if (!player.capabilities.isCreativeMode)
                    this.dropBlockAsItem(world, x, y, z, new ItemStack(this, 1, 0));
                super.onBlockHarvested(world, x, y, z, meta, player);
            }
        }
    }

    @Override
    public void onNeighborBlockChange(World world, int x, int y, int z, Block block) {
        TileEntity te = world.getTileEntity(x, y, z);
        if (te instanceof TileEntityShadowAlchemyFurnace) {
            TileEntityShadowAlchemyFurnace furnaceTE = (TileEntityShadowAlchemyFurnace) te;
            furnaceTE.getBellows();
        }
    }

    @Override
    public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_) {
        return null;
    }

    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase player, ItemStack stack) {
        int l = MathHelper.floor_double((double) (player.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
        switch (l) {
            case 1:
                world.setBlockMetadataWithNotify(x, y, z, 5, 2);
                break;
            case 2:
                world.setBlockMetadataWithNotify(x, y, z, 3, 2);
                break;
            case 3:
                world.setBlockMetadataWithNotify(x, y, z, 4, 2);
                break;
            default:
                world.setBlockMetadataWithNotify(x, y, z, 2, 2);
                break;
        }
    }

    private void setMetaFacingBlock(World p_149930_1_, int p_149930_2_, int p_149930_3_, int p_149930_4_) {
        if (!p_149930_1_.isRemote) {
            Block block = p_149930_1_.getBlock(p_149930_2_, p_149930_3_, p_149930_4_ - 1);
            Block block1 = p_149930_1_.getBlock(p_149930_2_, p_149930_3_, p_149930_4_ + 1);
            Block block2 = p_149930_1_.getBlock(p_149930_2_ - 1, p_149930_3_, p_149930_4_);
            Block block3 = p_149930_1_.getBlock(p_149930_2_ + 1, p_149930_3_, p_149930_4_);
            byte b0 = 3;

            if (block1.func_149730_j() && !block.func_149730_j()) {
                b0 = 2;
            }

            if (block2.func_149730_j() && !block3.func_149730_j()) {
                b0 = 5;
            }

            if (block3.func_149730_j() && !block2.func_149730_j()) {
                b0 = 4;
            }

            p_149930_1_.setBlockMetadataWithNotify(p_149930_2_, p_149930_3_, p_149930_4_, b0, 2);
        }
    }

    public void onBlockAdded(World p_149726_1_, int p_149726_2_, int p_149726_3_, int p_149726_4_) {
        super.onBlockAdded(p_149726_1_, p_149726_2_, p_149726_3_, p_149726_4_);
        this.setMetaFacingBlock(p_149726_1_, p_149726_2_, p_149726_3_, p_149726_4_);
    }
}
