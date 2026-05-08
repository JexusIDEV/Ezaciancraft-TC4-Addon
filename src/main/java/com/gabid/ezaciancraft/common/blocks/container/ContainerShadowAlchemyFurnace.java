package com.gabid.ezaciancraft.common.blocks.container;

import com.gabid.ezaciancraft.api.common.blocks.container.FuelSlot;
import com.gabid.ezaciancraft.common.blocks.tileentity.TileEntityShadowAlchemyFurnace;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.common.container.SlotLimitedHasAspects;
import thaumcraft.common.lib.crafting.ThaumcraftCraftingManager;
import thaumcraft.common.tiles.TileAlchemyFurnace;

public class ContainerShadowAlchemyFurnace extends Container {
    private final TileEntityShadowAlchemyFurnace furnaceTE;

    protected int lastEssentiaAmountVis;
    protected int lastFurnaceBurnTime;
    protected int lastMaxFurnaceBurnTime;
    protected int lastFurnaceCookTime;
    protected int lastMaxFurnaceCookTime;

    public ContainerShadowAlchemyFurnace(InventoryPlayer inventoryPlayer, TileEntityShadowAlchemyFurnace _furnaceTE) {
        this.furnaceTE = _furnaceTE;
        this.addSlotToContainer(new SlotLimitedHasAspects(_furnaceTE, 0, 84, 19));
        this.addSlotToContainer(new FuelSlot(_furnaceTE, 1, 84, 74));

        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 9; ++j) {
                this.addSlotToContainer(new Slot(inventoryPlayer, j + i * 9 + 9, 8 + j * 18, 105 + i * 18));
            }
        }

        for (int i = 0; i < 9; ++i) {
            this.addSlotToContainer(new Slot(inventoryPlayer, i, 8 + i * 18, 163));
        }
    }

    @Override
    public boolean canInteractWith(EntityPlayer player) {
        return this.furnaceTE.isUseableByPlayer(player);
    }

    @Override
    public void addCraftingToCrafters(ICrafting data) {
        super.addCraftingToCrafters(data);
        data.sendProgressBarUpdate(this, 0, this.furnaceTE.getFurnaceCookTime());
        data.sendProgressBarUpdate(this, 1, this.furnaceTE.getMaxFurnaceCookTime());
        data.sendProgressBarUpdate(this, 2, this.furnaceTE.getFurnaceCookTime());
        data.sendProgressBarUpdate(this, 3, this.furnaceTE.getMaxFurnaceCookTime());
        data.sendProgressBarUpdate(this, 4, this.furnaceTE.getEssentiaAmountVis());
    }

    @Override
    public void detectAndSendChanges() {
        super.detectAndSendChanges();
        for (Object crafter : this.crafters) {
            ICrafting data = (ICrafting) crafter;

            if (this.lastFurnaceCookTime != this.furnaceTE.getFurnaceCookTime()) {
                data.sendProgressBarUpdate(this, 0, this.furnaceTE.getFurnaceCookTime());
            }
            if (this.lastMaxFurnaceCookTime != this.furnaceTE.getMaxFurnaceCookTime()) {
                data.sendProgressBarUpdate(this, 1, this.furnaceTE.getMaxFurnaceCookTime());
            }
            if (this.lastFurnaceBurnTime != this.furnaceTE.getFurnaceBurnTime()) {
                data.sendProgressBarUpdate(this, 2, this.furnaceTE.getFurnaceBurnTime());
            }
            if (this.lastMaxFurnaceBurnTime != this.furnaceTE.getMaxFurnaceBurnTime()) {
                data.sendProgressBarUpdate(this, 3, this.furnaceTE.getMaxFurnaceBurnTime());
            }
            if (this.lastEssentiaAmountVis != this.furnaceTE.getEssentiaAmountVis()) {
                data.sendProgressBarUpdate(this, 4, this.furnaceTE.getEssentiaAmountVis());
            }
        }
        this.lastFurnaceCookTime = this.furnaceTE.getFurnaceCookTime();
        this.lastMaxFurnaceCookTime = this.furnaceTE.getMaxFurnaceCookTime();
        this.lastFurnaceBurnTime = this.furnaceTE.getFurnaceBurnTime();
        this.lastMaxFurnaceBurnTime = this.furnaceTE.getMaxFurnaceBurnTime();
        this.lastEssentiaAmountVis = this.furnaceTE.getEssentiaAmountVis();
    }

    @Override
    public void updateProgressBar(int slot, int value) {
        switch (slot) {
            case 0:
                this.furnaceTE.setFurnaceCookTime(value);
                break;
            case 1:
                this.furnaceTE.setMaxFurnaceCookTime(value);
                break;
            case 2:
                this.furnaceTE.setFurnaceBurnTime(value);
                break;
            case 3:
                this.furnaceTE.setMaxFurnaceBurnTime(value);
                break;
            case 4:
                this.furnaceTE.setEssentiaAmountVis(value);
                break;
        }
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int slotId) {
        ItemStack itemstack = null;
        Slot slot = (Slot) this.inventorySlots.get(slotId);
        if (slot != null && slot.getHasStack()) {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();
            if (slotId != 1 && slotId != 0) {
                AspectList al = ThaumcraftCraftingManager.getObjectTags(itemstack1);
                al = ThaumcraftCraftingManager.getBonusTags(itemstack1, al);
                if (TileAlchemyFurnace.isItemFuel(itemstack1)) {
                    if (!this.mergeItemStack(itemstack1, 1, 2, false) && !this.mergeItemStack(itemstack1, 0, 1, false)) {
                        return null;
                    }
                } else if (al.size() > 0) {
                    if (!this.mergeItemStack(itemstack1, 0, 1, false)) {
                        return null;
                    }
                } else if (slotId < 29) {
                    if (!this.mergeItemStack(itemstack1, 29, 38, false)) {
                        return null;
                    }
                } else if (slotId < 38 && !this.mergeItemStack(itemstack1, 2, 29, false)) {
                    return null;
                }
            } else if (!this.mergeItemStack(itemstack1, 2, 38, false)) {
                return null;
            }

            if (itemstack1.stackSize == 0) {
                slot.putStack(null);
            } else {
                slot.onSlotChanged();
            }

            if (itemstack1.stackSize == itemstack.stackSize) {
                return null;
            }
            slot.onPickupFromSlot(player, itemstack1);
        }
        return itemstack;
    }
}
