package com.gabid.ezaciancraft.lib.nbt;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

import java.util.Arrays;

///api Helper used for writing NBT to TE's or ItemStacks in the game directly
public class NBTHelper {

    public static boolean containerNBTIsNotNull(ItemStack container) {
        return container.getTagCompound() != null && container.hasTagCompound();
    }

    public static ItemStack setDefaultContainerNBT(ItemStack container, String keyName, String data) {
        if (!container.hasTagCompound() || container.getTagCompound() != null) {
            container.setTagCompound(new NBTTagCompound());
            container.getTagCompound().setString(keyName, data);
        }
        return container;
    }

    public static ItemStack setDefaultContainerNBT(ItemStack container, String keyName, int data) {
        if (!container.hasTagCompound() || container.getTagCompound() != null) {
            container.setTagCompound(new NBTTagCompound());
            container.getTagCompound().setInteger(keyName, data);
        }
        return container;
    }

    public static ItemStack setDefaultContainerNBT(ItemStack container, String keyName, boolean data) {
        if (!container.hasTagCompound() || container.getTagCompound() != null) {
            container.setTagCompound(new NBTTagCompound());
            container.getTagCompound().setBoolean(keyName, data);
        }
        return container;
    }

    public static ItemStack setDefaultContainerNBT(ItemStack container, String keyName, float data) {
        if (!container.hasTagCompound() || container.getTagCompound() != null) {
            container.setTagCompound(new NBTTagCompound());
            container.getTagCompound().setFloat(keyName, data);
        }
        return container;
    }

    public static ItemStack setDefaultContainerNBT(ItemStack container, String keyName, long data) {
        if (!container.hasTagCompound() || container.getTagCompound() != null) {
            container.setTagCompound(new NBTTagCompound());
            container.getTagCompound().setLong(keyName, data);
        }
        return container;
    }

    public static void writeNBTItemStackInventory(NBTTagCompound tag, ItemStack[] stackToWrite) {
        NBTTagList itemStackNBT = new NBTTagList();

        for (int i = 0; i < stackToWrite.length; i++) {
            ItemStack stack = stackToWrite[i];

            if (stack != null) {
                NBTTagCompound t = new NBTTagCompound();
                t.setByte("Index", (byte) i);
                stack.writeToNBT(t);
                itemStackNBT.appendTag(t);
            }
        }
        tag.setTag("Items", itemStackNBT);
    }

    public static ItemStack[] readNBTItemStackInventory(NBTTagCompound tag, int size) {
        ItemStack[] inventory = new ItemStack[size];
        NBTTagList list = tag.getTagList("Items", 10);

        for (int i = 0; i < list.tagCount(); i++) {
            NBTTagCompound t = list.getCompoundTagAt(i);
            int index = t.getByte("Index") & 255;

            if (index >= 0 && index < inventory.length) {
                inventory[index] = ItemStack.loadItemStackFromNBT(t);
            }
        }
        return inventory;
    }
}
