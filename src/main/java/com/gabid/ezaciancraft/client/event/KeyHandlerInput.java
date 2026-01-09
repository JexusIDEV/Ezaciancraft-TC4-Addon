package com.gabid.ezaciancraft.client.event;

import com.gabid.ezaciancraft.api.common.items.IEzacianTool;
import com.gabid.ezaciancraft.common.network.EzacianNetworkHandler;
import com.gabid.ezaciancraft.common.network.client.PacketUpdateEzacianModeTool;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class KeyHandlerInput {
    public KeyBinding kb_CETM = new KeyBinding("Change Ezacian Tool Mode", 36, "key.categories.misc");
    public boolean kb_CETM_IsPressed = false;
    public long kb_CETM_LastPressed = 0L;

    public KeyHandlerInput() {
        ClientRegistry.registerKeyBinding(this.kb_CETM);
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent event) {
        if(this.kb_CETM.isPressed()) {
            kb_CETM_IsPressed = true;
            EntityPlayer player = Minecraft.getMinecraft().thePlayer;
            World level = Minecraft.getMinecraft().theWorld;
            if(player != null) {
                ItemStack toolStack = player.getHeldItem();
                if(toolStack != null && toolStack.getItem() instanceof IEzacianTool) {
                    IEzacianTool toolItem = (IEzacianTool) toolStack.getItem();
                    toolItem.changeMode(toolStack);
                    EzacianNetworkHandler.sendToServer(new PacketUpdateEzacianModeTool(toolItem.getMode(toolStack)));
                }
            }
        } else {
            kb_CETM_IsPressed = false;
        }
    }
}
