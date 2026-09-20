package com.gabid.ezaciancraft.common.blocks.machine;

import com.gabid.ezaciancraft.common.blocks.tileentity.TileEntityAdvancedEssentiaStorage;
import com.gabid.ezaciancraft.common.blocks.tileentity.TileEntityAdvancedEssentiaStorageInterface;
import com.gabid.ezaciancraft.registry.EzacianCraftBlocks;
import com.gabid.ezaciancraft.registry.EzacianCraftItems;
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
import thaumcraft.common.config.ConfigItems;

import static com.gabid.ezaciancraft.api.EzacianCraftGeneralLang.UNLOCALE_ADVANCED_ESSENTIA_STORAGE;
import static com.gabid.ezaciancraft.registry.EzacianCraftTypeRenders.WIRELESS_ESSENTIA_INTERFACES_RENDER_ID;
import static thaumcraft.common.Thaumcraft.MODID;

public class BlockAdvancedEssentiaStorage extends BlockContainer {

    public IIcon bWEIIcon;

    static Block masterBlock = EzacianCraftBlocks.alchemyBlockExpert; //0
    static Block blockAlchemy = ConfigBlocks.blockMetalDevice; //16
    static Block blockGreatWood = ConfigBlocks.blockWoodenDevice; //6

    public static Block[][][] multiblockBlueprint = {
            {
                    {blockAlchemy, blockAlchemy, blockAlchemy},
                    {blockAlchemy, blockAlchemy, blockAlchemy},
                    {blockAlchemy, blockAlchemy, blockAlchemy}
            },
            {
                    {blockGreatWood, blockAlchemy, blockGreatWood},
                    {blockAlchemy, masterBlock, blockAlchemy},
                    {blockGreatWood, blockAlchemy, blockGreatWood}
            },
            {
                    {blockAlchemy, blockAlchemy, blockAlchemy},
                    {blockAlchemy, blockAlchemy, blockAlchemy},
                    {blockAlchemy, blockAlchemy, blockAlchemy}
            }
    };
    public static int[][][] multiblockMetaDatas = {
            {
                    {9, 9, 9},
                    {9, 3, 9},
                    {9, 9, 9}
            },
            {
                    {6, 3, 6},
                    {3, 0, 3},
                    {6, 3, 6}
            },
            {
                    {9, 9, 9},
                    {9, 3, 9},
                    {9, 9, 9}
            }
    };

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
        TileEntityAdvancedEssentiaStorage storageTE = findCenter(world, x, y, z);
        if (storageTE != null && !storageTE.getExtendedAspects().aspects.isEmpty() && !player.capabilities.isCreativeMode) {
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
            if (!player.capabilities.isCreativeMode) {
                TileEntity hitTE = world.getTileEntity(x,y,z);
                if(hitTE instanceof TileEntityAdvancedEssentiaStorage) {
                    this.dropBlockAsItem(world, x, y, z, new ItemStack(EzacianCraftBlocks.alchemyBlockExpert, 1, 0));
                } else if(hitTE instanceof TileEntityAdvancedEssentiaStorageInterface) {
                    this.dropBlockAsItem(world, x, y, z, new ItemStack(ConfigBlocks.blockMetalDevice, 1, 9));
                } else {
                    this.dropBlockAsItem(world, x, y, z, new ItemStack(this, 1, 0));
                }
            }
            super.onBlockHarvested(world, x, y, z, meta, player);
        }
    }

    public static TileEntityAdvancedEssentiaStorage findCenter(World w, int x, int y, int z) {
        for (int xo = -1; xo <= 1; xo++) {
            for (int yo = -1; yo <= 1; yo++) {
                for (int zo = -1; zo <= 1; zo++) {

                    TileEntity te2 = w.getTileEntity(x + xo, y + yo, z + zo);

                    if (te2 instanceof TileEntityAdvancedEssentiaStorage) {
                        return (TileEntityAdvancedEssentiaStorage) te2;
                    }
                }
            }
        }
        return null;
    }

    private static void regenMultiByCenter(World w, int x, int y, int z) {
        for (int xo = -1; xo <= 1; xo++) {
            for (int yo = -1; yo <= 1; yo++) {
                for (int zo = -1; zo <= 1; zo++) {
                    if (xo == 0 && yo == 0 && zo == 0) continue;

                    int newMeta = multiblockMetaDatas[yo + 1][zo + 1][xo + 1];

                    w.setBlock(x + xo, y + yo, z + zo, Block.getBlockFromItem(new ItemStack(multiblockBlueprint[yo+1][zo+1][xo+1], newMeta).getItem()), newMeta, 3);
                }
            }
        }
    }

    private static void regenMultiByEdges(World w, int x, int y, int z, TileEntityAdvancedEssentiaStorage storage) {
        for (int xOffset = -1; xOffset <= 1; xOffset++) {
            for (int yOffset = -1; yOffset <= 1; yOffset++) {
                for (int zOffset = -1; zOffset <= 1; zOffset++) {
                    if(xOffset == 0 && yOffset == 0 && zOffset == 0) continue;
                    if(storage.xCoord + xOffset == x && storage.yCoord + yOffset == y && storage.zCoord + zOffset == z) continue;

                    int newMeta = multiblockMetaDatas[yOffset + 1][zOffset + 1][xOffset + 1];
                    w.setBlock(storage.xCoord + xOffset, storage.yCoord + yOffset, storage.zCoord + zOffset, Block.getBlockFromItem(new ItemStack(multiblockBlueprint[yOffset+1][zOffset+1][xOffset+1], newMeta).getItem()), newMeta, 3);
                }
            }
        }
        w.setBlock(storage.xCoord, storage.yCoord, storage.zCoord, Block.getBlockFromItem(new ItemStack(EzacianCraftBlocks.alchemyBlockExpert).getItem()), 0, 3);
    }

    @Override
    public void breakBlock(World world, int x, int y, int z, Block block, int meta) {
        if (world.isRemote) return;

        TileEntityAdvancedEssentiaStorage centerTE = findCenter(world, x, y, z);

        if (centerTE != null) {
            if(meta == 0 && !centerTE.isBreaking) {
                centerTE.isBreaking = true;
                regenMultiByCenter(world, x, y, z);
                return;
            } else if(meta != 0 && !centerTE.isBreaking) {
                centerTE.isBreaking = true;
                regenMultiByEdges(world, x, y, z, centerTE);
                return;
            }
        }
        super.breakBlock(world, x, y, z, block, meta);
    }

    @Override
    protected void dropBlockAsItem(World world, int x, int y, int z, ItemStack stack) {
        super.dropBlockAsItem(world, x, y, z, stack);
    }
}
