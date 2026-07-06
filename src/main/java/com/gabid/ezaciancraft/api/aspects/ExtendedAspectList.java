package com.gabid.ezaciancraft.api.aspects;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

///@api An API Layer used for creating lists of lists as a AspectList of a single type of aspect each one;
 /// also used get near "infinite" amount of essentia-aspect without troubles with byte overflow in one aspectlist for other aspects
public class ExtendedAspectList {
    public Map<Aspect, AspectList> aspects;

    public ExtendedAspectList() {
        this.aspects = new HashMap<>();
    }

    public ExtendedAspectList(Aspect asp, int amount) {
        this.aspects = new HashMap<>();
        this.aspects.put(asp, new AspectList().add(asp, amount));
    }

    public void addAspect(Aspect asp, int amount) {
        AspectList list = this.aspects.get(asp);

        if (list != null) {
            list.add(asp, amount);
        } else {
            this.aspects.put(asp, new AspectList().add(asp, amount));
        }
    }

    public AspectList getAspectList(Aspect asp) {
        return this.aspects.get(asp);
    }

    public AspectList[] getAspectLists() {
        return this.aspects.values().toArray(new AspectList[0]);
    }

    public Aspect[] getAspects(Aspect asp) {
        AspectList list = this.aspects.get(asp);
        return list != null ? list.getAspects() : null;
    }

    public boolean hasAspect(Aspect asp) {
        return this.aspects.containsKey(asp);
    }

    public int getAmount(Aspect asp) {
        AspectList list = this.aspects.get(asp);
        return list != null ? list.getAmount(asp) : 0;
    }

    public void merge(ExtendedAspectList other) {
        if (other == null || other.aspects.isEmpty()) return;

        for (Map.Entry<Aspect, AspectList> entry : other.aspects.entrySet()) {
            Aspect asp = entry.getKey();
            int amount = entry.getValue().getAmount(asp);

            this.addAspect(asp, amount);
        }
    }

    public void removeAspect(Aspect asp, int amount) {
        AspectList list = this.aspects.get(asp);

        if (list == null) return;

        int current = list.getAmount(asp);
        int newAmount = current - amount;

        if (newAmount > 0) {
            list.aspects.put(asp, newAmount);
        } else {
            this.aspects.remove(asp);
        }
    }

    public void remove(ExtendedAspectList other) {
        if (other == null || other.aspects.isEmpty()) return;

        for (Map.Entry<Aspect, AspectList> entry : other.aspects.entrySet()) {
            Aspect asp = entry.getKey();
            int amount = entry.getValue().getAmount(asp);

            this.removeAspect(asp, amount);
        }
    }

    public void clear() {
        this.aspects.clear();
    }

    public Set<Aspect> getStoredAspects() {
        return this.aspects.keySet();
    }

    public void writeToNBT(NBTTagCompound mainTag) {
        NBTTagList listTag = new NBTTagList();

        for(AspectList ai : this.aspects.values()) {
            if(ai != null && ai.size() > 0) {
                Aspect asp = ai.getAspects()[0];
                int amount = ai.getAmount(asp);

                if(asp != null  && amount > 0) {
                    NBTTagCompound aspectKey = new NBTTagCompound();
                    aspectKey.setString("key", asp.getTag());
                    aspectKey.setInteger("amount", ai.getAmount(asp));
                    listTag.appendTag(aspectKey);
                }
            }
        }

        mainTag.setTag("AspectLists", listTag);
    }

    public void readFromNBT(NBTTagCompound tag) {
        if(tag.hasKey("AspectLists")) {
            this.aspects.clear();

            NBTTagList listTag = tag.getTagList("AspectLists", 10);

            for (int i = 0; i < listTag.tagCount(); i++) {
                NBTTagCompound listBaseTag = listTag.getCompoundTagAt(i);
                if (listBaseTag.hasKey("key") && listBaseTag.hasKey("amount"))
                    this.addAspect(Aspect.getAspect(listBaseTag.getString("key")), listBaseTag.getInteger("amount"));
            }
        }
    }
}
