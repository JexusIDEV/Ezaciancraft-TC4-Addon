package com.gabid.ezaciancraft.client.event;

import com.gabid.ezaciancraft.api.common.items.IEzacianPrimalTool;
import com.gabid.ezaciancraft.api.common.items.IEzacianTool;
import com.gabid.ezaciancraft.common.network.EzacianNetworkHandler;
import com.gabid.ezaciancraft.common.network.client.PacketUpdateEzacianModeTool;
import com.gabid.ezaciancraft.common.network.client.PacketUpdateEzacianPrimalModeTool;
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

    public KeyBinding kb_CPETB = new KeyBinding("Change Primal Ezacian Tool Behaviour", 37, "key.categories.misc");
    public boolean kb_CPETB_IsPressed = false;

    public KeyBinding kb_CPETSM = new KeyBinding("Change Primal Ezacian Tool Sub-Mode", 38, "key.categories.misc");
    public boolean kb_CPETSM_IsPressed = false;

    public KeyBinding kb_CPETAOE = new KeyBinding("Change Primal Ezacian Tool AoE", 39, "key.categories.misc");
    public boolean kb_CPETAOE_IsPressed = false;

    public KeyHandlerInput() {
        ClientRegistry.registerKeyBinding(this.kb_CETM);
        ClientRegistry.registerKeyBinding(this.kb_CPETB);
        ClientRegistry.registerKeyBinding(this.kb_CPETSM);
        ClientRegistry.registerKeyBinding(this.kb_CPETAOE);
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent event) {
        if (this.kb_CETM.isPressed()) {
            kb_CETM_IsPressed = true;
            EntityPlayer player = Minecraft.getMinecraft().thePlayer;
            World level = Minecraft.getMinecraft().theWorld;
            if (player != null) {
                ItemStack toolStack = player.getHeldItem();
                if (toolStack != null && toolStack.getItem() instanceof IEzacianTool) {
                    IEzacianTool toolItem = (IEzacianTool) toolStack.getItem();
                    toolItem.changeMode(toolStack);
                    EzacianNetworkHandler.sendToServer(new PacketUpdateEzacianModeTool(toolItem.getMode(toolStack)));
                }
            }
        } else {
            kb_CETM_IsPressed = false;
        }

        if (this.kb_CPETB.isPressed()) {
            this.kb_CPETB_IsPressed = true;
            EntityPlayer player = Minecraft.getMinecraft().thePlayer;
            if (player != null) {
                ItemStack toolStack = player.getHeldItem();
                if (toolStack != null && toolStack.getItem() instanceof IEzacianPrimalTool) {
                    IEzacianPrimalTool toolItem = (IEzacianPrimalTool) toolStack.getItem();
                    toolItem.changeBehaviour(toolStack);
                    EzacianNetworkHandler.sendToServer(new PacketUpdateEzacianPrimalModeTool(toolItem.getBehaviour(toolStack), toolItem.getSubMode(toolStack), toolItem.getAOE(toolStack)));
                }
            }
        } else {
            this.kb_CPETB_IsPressed = false;
        }

        if (this.kb_CPETSM.isPressed()) {
            this.kb_CPETSM_IsPressed = true;
            EntityPlayer player = Minecraft.getMinecraft().thePlayer;
            if (player != null) {
                ItemStack toolStack = player.getHeldItem();
                if (toolStack != null && toolStack.getItem() instanceof IEzacianPrimalTool) {
                    IEzacianPrimalTool toolItem = (IEzacianPrimalTool) toolStack.getItem();
                    toolItem.changeSubMode(toolStack);
                    EzacianNetworkHandler.sendToServer(new PacketUpdateEzacianPrimalModeTool(toolItem.getBehaviour(toolStack), toolItem.getSubMode(toolStack), toolItem.getAOE(toolStack)));
                }
            }
        } else {
            this.kb_CPETSM_IsPressed = false;
        }

        if (this.kb_CPETAOE.isPressed()) {
            this.kb_CPETAOE_IsPressed = true;
            EntityPlayer player = Minecraft.getMinecraft().thePlayer;
            if (player != null) {
                ItemStack toolStack = player.getHeldItem();
                if (toolStack != null && toolStack.getItem() instanceof IEzacianPrimalTool) {
                    IEzacianPrimalTool toolItem = (IEzacianPrimalTool) toolStack.getItem();
                    toolItem.changeAOE(toolStack);
                    EzacianNetworkHandler.sendToServer(new PacketUpdateEzacianPrimalModeTool(toolItem.getBehaviour(toolStack), toolItem.getSubMode(toolStack), toolItem.getAOE(toolStack)));
                }
            }
        } else {
            this.kb_CPETAOE_IsPressed = false;
        }
    }
}
