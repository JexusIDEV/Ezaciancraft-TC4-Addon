package com.gabid.ezaciancraft.lib.nbt;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class NBTHelper {

    public static boolean containerNBTIsNotNull(ItemStack container) {
        return container.getTagCompound() != null && container.hasTagCompound();
    }

    public static ItemStack setDefaultContainerNBT(ItemStack container, String keyName, String data) {
        if(!container.hasTagCompound() || container.getTagCompound() != null) {
            container.setTagCompound(new NBTTagCompound());
            container.getTagCompound().setString(keyName, data);
        }
        return container;
    }

    public static ItemStack setDefaultContainerNBT(ItemStack container, String keyName, int data) {
        if(!container.hasTagCompound() || container.getTagCompound() != null) {
            container.setTagCompound(new NBTTagCompound());
            container.getTagCompound().setInteger(keyName, data);
        }
        return container;
    }

    public static ItemStack setDefaultContainerNBT(ItemStack container, String keyName, boolean data) {
        if(!container.hasTagCompound() || container.getTagCompound() != null) {
            container.setTagCompound(new NBTTagCompound());
            container.getTagCompound().setBoolean(keyName, data);
        }
        return container;
    }

    public static ItemStack setDefaultContainerNBT(ItemStack container, String keyName, float data) {
        if(!container.hasTagCompound() || container.getTagCompound() != null) {
            container.setTagCompound(new NBTTagCompound());
            container.getTagCompound().setFloat(keyName, data);
        }
        return container;
    }

    public static ItemStack setDefaultContainerNBT(ItemStack container, String keyName, long data) {
        if(!container.hasTagCompound() || container.getTagCompound() != null) {
            container.setTagCompound(new NBTTagCompound());
            container.getTagCompound().setLong(keyName, data);
        }
        return container;
    }
}
