package com.gabid.ezaciancraft.client.event;

import com.gabid.ezaciancraft.common.items.tools.VoidStaffOfPrimalReconstructorItem;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import org.lwjgl.opengl.GL11;

import static com.gabid.ezaciancraft.api.EzacianCraftNBTConstants.PRIMAL_TOOL_AOE;
import static com.gabid.ezaciancraft.api.EzacianCraftNBTConstants.PRIMAL_TOOL_SUB_MODE;
import static com.gabid.ezaciancraft.api.EzacianCraftTranslations.*;

public class GUIClientEvents {

    public GUIClientEvents() {
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void onRenderOverlay(RenderGameOverlayEvent.Pre event) {
        if (event.type == RenderGameOverlayEvent.ElementType.TEXT) {
            Minecraft mc = Minecraft.getMinecraft();
            EntityPlayer player = mc.thePlayer;
            ItemStack stack = player.getHeldItem();
            if (stack != null && player.getHeldItem().getItem() instanceof VoidStaffOfPrimalReconstructorItem && mc.currentScreen == null) {

                VoidStaffOfPrimalReconstructorItem primalTool = (VoidStaffOfPrimalReconstructorItem) stack.getItem();
                int currentBehaviour = primalTool.getBehaviour(stack);
                int currentSubMode = primalTool.getSubMode(stack);

                GL11.glPushMatrix();
                GL11.glScalef(0.725f, 0.725f, 0.725f);
                String infoBehaviour = "";
                switch (currentBehaviour) {
                    case 0:
                        infoBehaviour = StatCollector.translateToLocal(ezacianPrimalToolBehaviourSeekerTranslation);
                        break;
                    case 1:
                        infoBehaviour = StatCollector.translateToLocal(ezacianPrimalToolBehaviourMagnetTranslation);
                        break;
                    case 2:
                        infoBehaviour = StatCollector.translateToLocal(ezacianPrimalToolBehaviourPlacerTranslation);
                        break;
                    case 3:
                        infoBehaviour = StatCollector.translateToLocal(ezacianPrimalToolBehaviourGrowerTranslation);
                        break;
                    case 4:
                        infoBehaviour = StatCollector.translateToLocal(ezacianPrimalToolBehaviourZephyrTranslation);
                        break;
                }
                mc.fontRenderer.drawStringWithShadow(StatCollector.translateToLocal(ezacianPrimalToolBehaviourTranslation) + infoBehaviour, 10, 10, 0xffffffff);

                String infoSubMode = "";
                switch (currentSubMode) {
                    case 0:
                        infoSubMode = StatCollector.translateToLocal(ezacianPrimalToolSubModeAreaTranslation);
                        break;
                    case 1:
                        infoSubMode = StatCollector.translateToLocal(ezacianPrimalToolSubModeVeinTranslation);
                        break;
                }
                mc.fontRenderer.drawStringWithShadow(StatCollector.translateToLocal(ezacianPrimalToolSubModeTranslation) + infoSubMode, 10, 20, 0xffffffff);

                if (stack.stackTagCompound.getInteger(PRIMAL_TOOL_SUB_MODE) == 0) {
                    int currentAOE = 2 * stack.stackTagCompound.getInteger(PRIMAL_TOOL_AOE) + 1;
                    mc.fontRenderer.drawStringWithShadow(StatCollector.translateToLocal(ezacianPrimalToolAOETranslation) + currentAOE + "x" + currentAOE + "x" + 1, 10, 30, 0xffffffff);
                }
                GL11.glPopMatrix();
            }
        }
    }
}
