package com.gabid.ezaciancraft.common.event;

import com.gabid.ezaciancraft.common.blocks.tileentity.TileEntityAdvancedEssentiaStorage;
import com.gabid.ezaciancraft.common.blocks.tileentity.TileEntityAdvancedEssentiaStorageInterface;
import com.gabid.ezaciancraft.registry.EzacianCraftBlocks;
import cpw.mods.fml.common.network.NetworkRegistry;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.wands.IWandTriggerManager;
import thaumcraft.common.config.ConfigBlocks;
import thaumcraft.common.items.wands.ItemWandCasting;
import thaumcraft.common.lib.network.PacketHandler;
import thaumcraft.common.lib.network.fx.PacketFXBlockSparkle;
import thaumcraft.common.lib.research.ResearchManager;

import static com.gabid.ezaciancraft.lib.EzacianArrayLibHelper.all2DArrayTrue;
import static com.gabid.ezaciancraft.lib.EzacianArrayLibHelper.all3DArrayTrue;

public class EzacianCraftWandMultiblockEvent implements IWandTriggerManager {

    public EzacianCraftWandMultiblockEvent() {
    }

    private static boolean createAdvancedArcaneWorkbench(ItemStack wand, EntityPlayer player, World world, int x, int y, int z) {
        Block masterBlock = EzacianCraftBlocks.ezacianStoneDecorativeBlocks;
        Block arcaneDeco = ConfigBlocks.blockCosmeticSolid;
        Block[][] multiblockBlueprint = {{arcaneDeco, arcaneDeco, arcaneDeco}, {arcaneDeco, masterBlock, arcaneDeco}, {arcaneDeco, arcaneDeco, arcaneDeco}}; //3x3x1
        int[][] multiblockMetaDatas = {{7, 6, 7}, {6, 0, 6}, {7, 6, 7}};

        Block hitBlock = world.getBlock(x, y, z);
        int hitMeta = world.getBlockMetadata(x, y, z);

        boolean[][] blueprintValidation = {{false, false, false}, {false, false, false}, {false, false, false}};

        for (int xOffset = -1; xOffset <= 1; xOffset++) {
            for (int zOffset = -1; zOffset <= 1; zOffset++) {
                Block currentBlock = world.getBlock(x + xOffset, y, z + zOffset);
                int currentBlockMeta = world.getBlockMetadata(x + xOffset, y, z + zOffset);

                int arrayXOffset = xOffset + 1;
                int arrayZOffset = zOffset + 1;

                if (hitBlock == masterBlock && hitMeta == 0) {
                    if (multiblockBlueprint[arrayXOffset][arrayZOffset] == currentBlock && multiblockMetaDatas[arrayXOffset][arrayZOffset] == currentBlockMeta) {
                        blueprintValidation[arrayXOffset][arrayZOffset] = true;
                    }
                }
            }
        }

        ItemWandCasting wandItem = (ItemWandCasting) wand.getItem();
        boolean hasEnoughVisToPerform = wandItem.consumeAllVisCrafting(wand, player, new AspectList()
                        .add(Aspect.FIRE, 75)
                        .add(Aspect.ORDER, 75)
                        .add(Aspect.WATER, 75)
                        .add(Aspect.AIR, 75)
                        .add(Aspect.EARTH, 75)
                        .add(Aspect.ENTROPY, 75)
                , true);
        boolean blueprintValidated = all2DArrayTrue(blueprintValidation);

        if (blueprintValidated && hasEnoughVisToPerform) {
            if (world.isRemote) {
                PacketHandler.INSTANCE.sendToAllAround(new PacketFXBlockSparkle(x, y, z, -9999), new NetworkRegistry.TargetPoint(world.provider.dimensionId, x, y, z, 32.0));
            } else {
                return replaceAdvancedArcaneWorkbench(world, x, y, z);
            }
        }
        return false;
    }

    private static boolean replaceAdvancedArcaneWorkbench(World world, int x, int y, int z) {
        int metaIterator = 0;
        for (int xOffset = -1; xOffset <= 1; xOffset++) {
            for (int zOffset = -1; zOffset <= 1; zOffset++) {
                world.setBlock(x + xOffset, y, z + zOffset, EzacianCraftBlocks.extendedArcaneWorkbench, metaIterator, 3);
                world.markBlockForUpdate(x + xOffset, y, z + zOffset);
                metaIterator++;
            }
        }
        world.playSoundEffect((double) x + 0.5, (double) y + 0.5, (double) z + 0.5, "thaumcraft:wand", 1.0F, 1.0F);
        return true;
    }

    public static boolean createAdvancedEssentiaStorage(ItemStack wand, EntityPlayer player, World world, int hitX, int hitY, int hitZ) {
        Block masterBlock = EzacianCraftBlocks.alchemyBlockExpert; //0
        Block blockAlchemy = ConfigBlocks.blockMetalDevice; //16
        Block blockGreatWood = ConfigBlocks.blockWoodenDevice; //6

        Block[][][] multiblockBlueprint = {
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
        int[][][] multiblockMetaDatas = {
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

        boolean[][][] blueprintValidation = {
                {
                        {false, false, false},
                        {false, false, false},
                        {false, false, false}
                },
                {
                        {false, false, false},
                        {false, false, false},
                        {false, false, false}
                },
                {
                        {false, false, false},
                        {false, false, false},
                        {false, false, false}
                }
        };

        int masterX = 0;
        int masterY = 0;
        int masterZ = 0;

        //search the center
        for(int searchX=-1; searchX <= 1; searchX++) {
            for(int searchY=-1; searchY <= 1; searchY++) {
                for(int searchZ=-1; searchZ <= 1; searchZ++) {
                    Block searchMasterBlock = world.getBlock(hitX + searchX, hitY + searchY, hitZ + searchZ);
                    if(searchMasterBlock != null && searchMasterBlock == masterBlock) {
                        masterX = hitX + searchX;
                        masterY = hitY + searchY;
                        masterZ = hitZ + searchZ;
                    }
                }
            }
        }

        //do the real check
        for(int searchX=-1; searchX <= 1; searchX++) {
            for(int searchY=-1; searchY <= 1; searchY++) {
                for(int searchZ=-1; searchZ <= 1; searchZ++) {
                    int mSearchX = masterX + searchX;
                    int mSearchY = masterY + searchY;
                    int mSearchZ = masterZ + searchZ;

                    int positiveSearchX = searchX + 1;
                    int positiveSearchY = searchY + 1;
                    int positiveSearchZ = searchZ + 1;

                    Block searchedBlock = world.getBlock(mSearchX, mSearchY, mSearchZ);
                    int currentMetaBlock = world.getBlockMetadata(mSearchX, mSearchY, mSearchZ);
                    if(multiblockBlueprint[positiveSearchY][positiveSearchZ][positiveSearchX] == searchedBlock && multiblockMetaDatas[positiveSearchY][positiveSearchZ][positiveSearchX] == currentMetaBlock) {
                        blueprintValidation[positiveSearchY][positiveSearchZ][positiveSearchX] = true;
                    }
                }
            }
        }

        ItemWandCasting wandItem = (ItemWandCasting) wand.getItem();
        boolean hasEnoughVisToPerform = wandItem.consumeAllVisCrafting(wand, player, new AspectList()
                        .add(Aspect.FIRE, 125)
                        .add(Aspect.ORDER, 125)
                        .add(Aspect.WATER, 125)
                        .add(Aspect.AIR, 125)
                        .add(Aspect.EARTH, 125)
                        .add(Aspect.ENTROPY, 125)
                , true);
        boolean blueprintValidated = all3DArrayTrue(blueprintValidation);

        if (blueprintValidated && hasEnoughVisToPerform) {
            if (!world.isRemote) {
                return replaceAdvancedEssentiaStorage(world, masterX, masterY, masterZ);
            }
        }
        return false;
    }

    private static boolean replaceAdvancedEssentiaStorage(World world, int x, int y, int z) {
        Block realAdvancedEssentiaStorage = EzacianCraftBlocks.advancedEssentiaStorage;
        Block[][][] multiblockBlueprintFormed = {
                {
                        {realAdvancedEssentiaStorage, realAdvancedEssentiaStorage, realAdvancedEssentiaStorage},
                        {realAdvancedEssentiaStorage, null, realAdvancedEssentiaStorage},
                        {realAdvancedEssentiaStorage, realAdvancedEssentiaStorage, realAdvancedEssentiaStorage}
                },
                {
                        {null, null, null},
                        {null, realAdvancedEssentiaStorage, null},
                        {null, null, null}
                },
                {
                        {realAdvancedEssentiaStorage, realAdvancedEssentiaStorage, realAdvancedEssentiaStorage},
                        {realAdvancedEssentiaStorage, null, realAdvancedEssentiaStorage},
                        {realAdvancedEssentiaStorage, realAdvancedEssentiaStorage, realAdvancedEssentiaStorage}
                }
        };
        int[][][] multiblockMetaDatas = {
                {
                        {1, 1, 1},
                        {1, 0, 1},
                        {1, 1, 1}
                },
                {
                        {0, 0, 0},
                        {0, 0, 0},
                        {0, 0, 0}
                },
                {
                        {2, 2, 2},
                        {2, 0, 2},
                        {2, 2, 2}
                }
        };

        for(int searchX = -1; searchX <= 1; searchX++) {
            for(int searchY=-1; searchY <= 1; searchY++) {
                for(int searchZ=-1; searchZ <= 1; searchZ++) {
                    int mSearchX = x + searchX;
                    int mSearchY = y + searchY;
                    int mSearchZ = z + searchZ;

                    int positiveSearchX = searchX + 1;
                    int positiveSearchY = searchY + 1;
                    int positiveSearchZ = searchZ + 1;

                    if(multiblockBlueprintFormed[positiveSearchY][positiveSearchZ][positiveSearchX] != null) {
                        world.setBlock(mSearchX, mSearchY, mSearchZ, multiblockBlueprintFormed[positiveSearchY][positiveSearchZ][positiveSearchX], multiblockMetaDatas[positiveSearchY][positiveSearchZ][positiveSearchX], 3);
                    } else {
                        world.setBlock(mSearchX, mSearchY, mSearchZ, Blocks.air, 0, 3);
                    }
                }
            }
        }

        TileEntity realMasterTE = world.getTileEntity(x, y, z);
        TileEntityAdvancedEssentiaStorage storageTE;
        if(realMasterTE instanceof TileEntityAdvancedEssentiaStorage)
            storageTE = (TileEntityAdvancedEssentiaStorage) realMasterTE;
        else
            return false;

        for(int searchX = -1; searchX <= 1; searchX++) {
            for (int searchY = -1; searchY <= 1; searchY++) {
                for (int searchZ = -1; searchZ <= 1; searchZ++) {
                    int mSearchX = x + searchX;
                    int mSearchY = y + searchY;
                    int mSearchZ = z + searchZ;

                    TileEntity currentTE = world.getTileEntity(mSearchX, mSearchY, mSearchZ);
                    if(currentTE instanceof TileEntityAdvancedEssentiaStorageInterface) {
                        TileEntityAdvancedEssentiaStorageInterface interfaceTE = (TileEntityAdvancedEssentiaStorageInterface) currentTE;
                        interfaceTE.setStorageReference(storageTE);
                    }
                }
            }
        }

        return true;
    }

    @Override
    public boolean performTrigger(World world, ItemStack wand, EntityPlayer player, int x, int y, int z, int side, int event) {
        switch (event) {
            case 0:
                if (ResearchManager.isResearchComplete(player.getDisplayName(), "ADVANCED_ARCANE_WORKBENCH")) {
                    return createAdvancedArcaneWorkbench(wand, player, world, x, y, z);
                }
            case 1:
                if (ResearchManager.isResearchComplete(player.getDisplayName(), "ADVANCED_ESSENTIA_STORAGE")) {
                    return createAdvancedEssentiaStorage(wand, player, world, x, y, z);
                }
        }
        return false;
    }
}
