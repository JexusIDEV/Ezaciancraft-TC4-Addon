package com.gabid.ezaciancraft.common.blocks.container;

import com.gabid.ezaciancraft.api.common.blocks.container.SlotAnyWand;
import com.gabid.ezaciancraft.common.blocks.tileentity.TileEntityExtendedArcaneWorkbench;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import thaumcraft.common.container.ContainerDummy;
import thaumcraft.common.container.SlotCraftingArcaneWorkbench;
import thaumcraft.common.items.wands.ItemWandCasting;
import thaumcraft.common.lib.crafting.ThaumcraftCraftingManager;

import java.util.List;

public class ContainerExtendedArcaneWorkbench extends Container {

    protected TileEntityExtendedArcaneWorkbench arcaneWorkTE;
    protected InventoryPlayer playerInv;

    public ContainerExtendedArcaneWorkbench(InventoryPlayer inventoryPlayer, TileEntityExtendedArcaneWorkbench _arcaneWorkTE) {
        this.arcaneWorkTE = _arcaneWorkTE;
        this.arcaneWorkTE.eventHandler = this;
        this.playerInv = inventoryPlayer;
        this.addSlotToContainer(new SlotCraftingArcaneWorkbench(inventoryPlayer.player, this.arcaneWorkTE, this.arcaneWorkTE, 9, 160, 64));
        this.addSlotToContainer(new SlotAnyWand(this.arcaneWorkTE, 10, 160, 24));

        int var6;
        int var7;
        for (var6 = 0; var6 < 3; ++var6) {
            for (var7 = 0; var7 < 3; ++var7) {
                this.addSlotToContainer(new Slot(this.arcaneWorkTE, var7 + var6 * 3, 40 + var7 * 24, 40 + var6 * 24));
            }
        }

        for (var6 = 0; var6 < 3; ++var6) {
            for (var7 = 0; var7 < 9; ++var7) {
                this.addSlotToContainer(new Slot(inventoryPlayer, var7 + var6 * 9 + 9, 16 + var7 * 18, 151 + var6 * 18));
            }
        }

        for (var6 = 0; var6 < 9; ++var6) {
            this.addSlotToContainer(new Slot(inventoryPlayer, var6, 16 + var6 * 18, 209));
        }

        this.onCraftMatrixChanged(this.arcaneWorkTE);
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int slotId) {
        ItemStack result = null;
        Slot slot = (Slot) this.inventorySlots.get(slotId);

        if (slot != null && slot.getHasStack()) {
            ItemStack stackInSlot = slot.getStack();
            result = stackInSlot.copy();

            if (slotId == 0) {
                if (!this.mergeItemStack(stackInSlot, 11, 47, true)) {
                    return null;
                }
                slot.onSlotChange(stackInSlot, result);
            } else if (slotId >= 11 && slotId < 38) {
                if (stackInSlot.getItem() instanceof ItemWandCasting) {
                    if (!this.mergeItemStack(stackInSlot, 1, 2, false)) {
                        return null;
                    }
                    slot.onSlotChange(stackInSlot, result);
                } else if (!this.mergeItemStack(stackInSlot, 38, 47, false)) {
                    return null;
                }
            } else if (slotId >= 38 && slotId < 47) {
                if (stackInSlot.getItem() instanceof ItemWandCasting) {
                    if (!this.mergeItemStack(stackInSlot, 1, 2, false)) {
                        return null;
                    }
                    slot.onSlotChange(stackInSlot, result);
                } else if (!this.mergeItemStack(stackInSlot, 11, 38, false)) {
                    return null;
                }
            } else if (!this.mergeItemStack(stackInSlot, 11, 47, false)) {
                return null;
            }

            if (stackInSlot.stackSize == 0) {
                slot.putStack(null);
            } else {
                slot.onSlotChanged();
            }

            if (stackInSlot.stackSize == result.stackSize) {
                return null;
            }
            slot.onPickupFromSlot(player, stackInSlot);
        }
        return result;
    }

    @Override
    public boolean canInteractWith(EntityPlayer p_75145_1_) {
        return true;
    }

    @Override
    public void onCraftMatrixChanged(IInventory p_75130_1_) {
        super.onCraftMatrixChanged(p_75130_1_);
        InventoryCrafting ic = new InventoryCrafting(new ContainerDummy(), 3, 3);

        for (int a = 0; a < 9; ++a) {
            ic.setInventorySlotContents(a, this.arcaneWorkTE.func_70301_a(a));
        }

        this.arcaneWorkTE.setInventorySlotContentsSoftly(9, CraftingManager.getInstance().findMatchingRecipe(ic, this.arcaneWorkTE.getWorldObj()));
        if (this.arcaneWorkTE.func_70301_a(9) == null && this.arcaneWorkTE.func_70301_a(10) != null && this.arcaneWorkTE.func_70301_a(10).getItem() instanceof ItemWandCasting) {
            ItemWandCasting wand = (ItemWandCasting) this.arcaneWorkTE.func_70301_a(10).getItem();
            if (wand.consumeAllVisCrafting(this.arcaneWorkTE.func_70301_a(10), this.playerInv.player, ThaumcraftCraftingManager.findMatchingArcaneRecipeAspects(this.arcaneWorkTE, this.playerInv.player), false)) {
                this.arcaneWorkTE.setInventorySlotContentsSoftly(9, ThaumcraftCraftingManager.findMatchingArcaneRecipe(this.arcaneWorkTE, this.playerInv.player));
            }
        }
    }

    @Override
    public void onContainerClosed(EntityPlayer p_75134_1_) {
        super.onContainerClosed(p_75134_1_);
        if (!this.arcaneWorkTE.getWorldObj().isRemote) {
            this.arcaneWorkTE.eventHandler = null;
        }
    }

    @Override
    public ItemStack slotClick(int par1, int par2, int par3, EntityPlayer par4EntityPlayer) {
        if (par3 == 4) {
            int _par2 = 1;
            return super.slotClick(par1, _par2, par3, par4EntityPlayer);
        } else {
            if ((par1 == 0 || par1 == 1) && par2 > 0) {
                par2 = 0;
            }

            return super.slotClick(par1, par2, par3, par4EntityPlayer);
        }
    }

    @Override
    public boolean func_94530_a(ItemStack par1ItemStack, Slot par2Slot) {
        return par2Slot.inventory != this.arcaneWorkTE && super.func_94530_a(par1ItemStack, par2Slot);
    }
}
