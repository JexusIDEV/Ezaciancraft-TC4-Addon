package com.gabid.ezaciancraft.common.event;

import com.gabid.ezaciancraft.registry.EzacianCraftBlocks;
import cpw.mods.fml.common.network.NetworkRegistry;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
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

public class ThaumcraftMultiblockEvent implements IWandTriggerManager {

    public ThaumcraftMultiblockEvent() {
    }

    public static boolean createAdvancedArcaneWorkbench(ItemStack wand, EntityPlayer player, World world, int x, int y, int z) {
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
                    if (multiblockBlueprint[arrayXOffset][arrayZOffset].equals(currentBlock) && multiblockMetaDatas[arrayXOffset][arrayZOffset] == currentBlockMeta) {
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

    public static boolean replaceAdvancedArcaneWorkbench(World world, int x, int y, int z) {
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

    @Override
    public boolean performTrigger(World world, ItemStack wand, EntityPlayer player, int x, int y, int z, int side, int event) {
        switch (event) {
            case 0:
                if (ResearchManager.isResearchComplete(player.getDisplayName(), "ADVANCED_ARCANE_WORKBENCH")) {
                    return createAdvancedArcaneWorkbench(wand, player, world, x, y, z);
                }
        }
        return false;
    }
}
