package com.gabid.ezaciancraft.api.common.blocks.container;

import com.gabid.ezaciancraft.common.blocks.tileentity.TileEntityExtendedArcaneWorkbench;
import cpw.mods.fml.common.FMLCommonHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.SlotCrafting;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerDestroyItemEvent;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.common.items.wands.ItemWandCasting;
import thaumcraft.common.lib.crafting.ThaumcraftCraftingManager;

public class ExtendedArcaneCraftingSlot extends SlotCrafting {
    private final TileEntityExtendedArcaneWorkbench te;
    private final IInventory craftMatrix;

    public ExtendedArcaneCraftingSlot(EntityPlayer player, IInventory matrix, IInventory result, int index, int x, int y, TileEntityExtendedArcaneWorkbench te) {
        super(player, matrix, result, index, x, y);
        this.craftMatrix = matrix;
        this.te = te;
    }

    @Override
    public void onPickupFromSlot(EntityPlayer player, ItemStack stack) {
        FMLCommonHandler.instance().firePlayerCraftingEvent(player, stack, this.craftMatrix);
        this.onCrafting(stack);

        AspectList aspects = ThaumcraftCraftingManager.findMatchingArcaneRecipeAspects(this.te, player);

        if (aspects != null && aspects.size() > 0 && this.te.func_70301_a(10) != null) {
            ItemWandCasting wand = (ItemWandCasting) this.te.func_70301_a(10).getItem();
            wand.consumeAllVisCrafting(
                    this.te.func_70301_a(10),
                    player,
                    aspects,
                    true
            );
        }

        for (int i = 0; i < 9; i++) {
            ItemStack stackInSlot = this.te.func_70301_a(i);

            if (stackInSlot != null) {
                this.te.func_70298_a(i, 1);

                if (stackInSlot.getItem().hasContainerItem(stackInSlot)) {
                    ItemStack container = stackInSlot.getItem().getContainerItem(stackInSlot);

                    if (container != null && container.isItemStackDamageable() && container.getItemDamage() > container.getMaxDamage()) {
                        MinecraftForge.EVENT_BUS.post(new PlayerDestroyItemEvent(player, container));
                    } else if (!stackInSlot.getItem().doesContainerItemLeaveCraftingGrid(stackInSlot) || !player.inventory.addItemStackToInventory(container)) {
                        if (this.te.func_70301_a(i) == null) {
                            this.te.func_70299_a(i, container);
                        } else {
                            player.dropPlayerItemWithRandomChoice(container, false);
                        }
                    }
                }
            }
        }
        this.te.markDirty();
    }
}
