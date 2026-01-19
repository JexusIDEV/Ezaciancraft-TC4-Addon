package com.gabid.ezaciancraft.api.aspects;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.wands.IWandRodOnUpdate;
import thaumcraft.common.items.wands.ItemWandCasting;

import java.util.ArrayList;

public class CustomWandRodPrimalUpdate implements IWandRodOnUpdate {

    public float maxAspectsPercentage;
    public int ticksToUpdateOneAspect;
    public int ticksToUpdateAspects;

    //primals for a full wand aspects, aspect is for single
    protected Aspect aspect;
    protected ArrayList<Aspect> primals;

    public CustomWandRodPrimalUpdate(Aspect aspect, int _ticksToUpdateOneAspect) {
        this.ticksToUpdateOneAspect = _ticksToUpdateOneAspect;
        this.aspect = aspect;
    }

    public CustomWandRodPrimalUpdate(float _maxAspectsPercentage, int _ticksToUpdateAspects) {
        this.maxAspectsPercentage = _maxAspectsPercentage;
        this.ticksToUpdateAspects =_ticksToUpdateAspects;
        this.aspect = null;
        this.primals = Aspect.getPrimalAspects();
    }

    public void onUpdate(ItemStack itemstack, EntityPlayer player) {
        if (this.aspect != null) {
            if (player.ticksExisted % this.ticksToUpdateOneAspect == 0 && ((ItemWandCasting)itemstack.getItem()).getVis(itemstack, this.aspect) < ((ItemWandCasting)itemstack.getItem()).getMaxVis(itemstack) * this.maxAspectsPercentage) {
                ((ItemWandCasting)itemstack.getItem()).addVis(itemstack, this.aspect, 1, true);
            }
        } else if (player.ticksExisted % this.ticksToUpdateAspects == 0) {
            ArrayList<Aspect> aspects = new ArrayList<>();

            for (Aspect as : this.primals) {
                if (((ItemWandCasting) itemstack.getItem()).getVis(itemstack, as) < ((ItemWandCasting) itemstack.getItem()).getMaxVis(itemstack) * this.maxAspectsPercentage) {
                    aspects.add(as);
                }
            }

            if (!aspects.isEmpty()) {
                ((ItemWandCasting)itemstack.getItem()).addVis(itemstack, aspects.get(player.worldObj.rand.nextInt(aspects.size())), 1, true);
            }
        }

    }
}
