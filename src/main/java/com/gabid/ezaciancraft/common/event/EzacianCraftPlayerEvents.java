package com.gabid.ezaciancraft.common.event;

import com.gabid.ezaciancraft.registry.EzacianCraftItems;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.living.LivingEvent;

public class EzacianCraftPlayerEvents {

    public EzacianCraftPlayerEvents() {}

    @SubscribeEvent
    public void onPlayerJumpEvent(LivingEvent.LivingJumpEvent playerJumpEvent) {
        if (playerJumpEvent.entity instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) playerJumpEvent.entity;
            if (player.getCurrentArmor(0) != null && player.getCurrentArmor(0).getItem() == EzacianCraftItems.magicAlloyTravellerBoots) {
                player.motionY += 0.5D;
            }
        }
    }
}
