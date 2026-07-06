package com.gabid.ezaciancraft.common.blocks;

import com.gabid.ezaciancraft.common.blocks.tileentity.TileEntityAdvancedEssentiaStorage;
import com.gabid.ezaciancraft.common.blocks.tileentity.TileEntityAdvancedEssentiaStorageInterface;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.common.config.ConfigBlocks;

import static com.gabid.ezaciancraft.api.EzacianCraftGeneralLang.UNLOCALE_ADVANCED_ESSENTIA_STORAGE;
import static com.gabid.ezaciancraft.registry.EzacianCraftTypeRenders.WIRELESS_ESSENTIA_INTERFACES_RENDER_ID;
import static thaumcraft.common.Thaumcraft.MODID;

public class BlockAdvancedEssentiaStorage extends BlockContainer {

    public IIcon bWEIIcon;

    public BlockAdvancedEssentiaStorage() {
        super(Material.piston);
        this.setHardness(3f);
        this.setResistance(.5f);
        this.setStepSound(Block.soundTypeMetal);
        this.setBlockName(UNLOCALE_ADVANCED_ESSENTIA_STORAGE);
        this.setHarvestLevel("pickaxe", 2);
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
        return WIRELESS_ESSENTIA_INTERFACES_RENDER_ID;
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta) {
        switch (meta) {
            case 0:
                return new TileEntityAdvancedEssentiaStorage();
            case 1:
                return new TileEntityAdvancedEssentiaStorageInterface();
            case 2:
                return new TileEntityAdvancedEssentiaStorageInterface();
            default:
                return null;
        }
    }

    @Override
    public void onBlockHarvested(World world, int x, int y, int z, int meta, EntityPlayer player) {
        TileEntity te = world.getTileEntity(x, y, z);
        if (te instanceof TileEntityAdvancedEssentiaStorage) {
            TileEntityAdvancedEssentiaStorage storageTE = (TileEntityAdvancedEssentiaStorage) te;
            if (!storageTE.getExtendedAspects().aspects.isEmpty() && !player.capabilities.isCreativeMode) {
                int sz = 0;
                for(AspectList ai : storageTE.getExtendedAspects().getAspectLists()) {
                    int danger = ai.getAmount(ai.getAspects()[0]) / 8;
                    sz+=danger;
                }
                int q = 0;
                if (sz > 0) {
                    world.createExplosion(null, (double) x + 0.5, (double) y + 0.5, (double) z + 0.5, 4.0F, false);

                    for (int a = 0; a < 50; ++a) {
                        int xx = x + world.rand.nextInt(16) - world.rand.nextInt(16);
                        int yy = y + world.rand.nextInt(16) - world.rand.nextInt(16);
                        int zz = z + world.rand.nextInt(16) - world.rand.nextInt(16);
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
                    this.dropBlockAsItem(world, x, y, z, new ItemStack(this, 1, 0));
                    super.onBlockHarvested(world, x, y, z, meta, player);
                }
            } else {
                if (!player.capabilities.isCreativeMode)
                    this.dropBlockAsItem(world, x, y, z, new ItemStack(this, 1, 0));
                super.onBlockHarvested(world, x, y, z, meta, player);
            }
        }
    }
}
