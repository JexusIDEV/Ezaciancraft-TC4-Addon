package com.gabid.ezaciancraft.client.event;

import com.gabid.ezaciancraft.api.aspects.ExtendedAspectList;
import com.gabid.ezaciancraft.api.aspects.IExtendedAspectContainer;
import com.gabid.ezaciancraft.common.blocks.tileentity.TileEntityAdvancedEssentiaStorage;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MovingObjectPosition;
import net.minecraftforge.client.event.DrawBlockHighlightEvent;
import net.minecraftforge.common.util.ForgeDirection;
import org.lwjgl.opengl.GL11;
import thaumcraft.api.IGoggles;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.aspects.IAspectContainer;
import thaumcraft.client.lib.RenderEventHandler;
import thaumcraft.client.lib.UtilsFX;
import thaumcraft.common.Thaumcraft;

import java.awt.*;

import static thaumcraft.client.lib.RenderEventHandler.tagscale;

public class RenderEvents {

    public RenderEvents() {}

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void blockHighlight(DrawBlockHighlightEvent event) {
        int ticks = event.player.ticksExisted;
        MovingObjectPosition target = event.target;

        if (event.player.getCurrentArmor(3) != null && event.player.getCurrentArmor(3).getItem() instanceof IGoggles && ((IGoggles) event.player.getCurrentArmor(3).getItem()).showIngamePopups(event.player.getCurrentArmor(3), event.player)) {
            boolean spaceAbove = event.player.worldObj.isAirBlock(target.blockX, target.blockY + 1, target.blockZ);
            TileEntity te = event.player.worldObj.getTileEntity(target.blockX, target.blockY, target.blockZ);

            if(te != null) {
                if(te instanceof IExtendedAspectContainer) {
                    if(te instanceof TileEntityAdvancedEssentiaStorage) {
                        TileEntityAdvancedEssentiaStorage storage = (TileEntityAdvancedEssentiaStorage) te;
                        if(storage.getExtendedAspects() != null && !storage.getExtendedAspects().aspects.isEmpty()) {
                            float shift = 0;

                            if (tagscale < 0.3F) {
                                tagscale += 0.031F - tagscale / 10.0F;
                            }

                            this.drawTagsOnContainer(target.blockX, ((float)target.blockY + (spaceAbove ? 0.4F : 0.0F) + shift), target.blockZ, storage.getExtendedAspects(), 220, spaceAbove ? ForgeDirection.UP : ForgeDirection.getOrientation(event.target.sideHit), event.partialTicks);
                        }
                    }
                }
            }
        }
    }

    public void drawTagsOnContainer(double x, double y, double z, ExtendedAspectList extendedAspectList, int bright, ForgeDirection dir, float partialTicks) {
        if (Minecraft.getMinecraft().thePlayer != null && extendedAspectList != null && !extendedAspectList.aspects.isEmpty()) {
            EntityPlayer player = Minecraft.getMinecraft().thePlayer;
            double iPX = player.prevPosX + (player.posX - player.prevPosX) * (double)partialTicks;
            double iPY = player.prevPosY + (player.posY - player.prevPosY) * (double)partialTicks;
            double iPZ = player.prevPosZ + (player.posZ - player.prevPosZ) * (double)partialTicks;
            int rowsize = 5;
            int current = 0;
            float shifty = 0.0F;
            int left = extendedAspectList.aspects.size();
            AspectList[] aspectLists = extendedAspectList.getAspectLists();

            for(AspectList aspectList : aspectLists) {
                Aspect tag = aspectList.getAspects()[0];
                int div = Math.min(left, rowsize);
                if (current >= rowsize) {
                    current = 0;
                    shifty -= tagscale * 1.05F;
                    left -= rowsize;
                    if (left < rowsize) {
                        div = left % rowsize;
                    }
                }

                float shift = ((float)current - (float)div / 2.0F + 0.5F) * tagscale * 4.0F;
                shift *= tagscale;
                Color color = new Color(tag.getColor());
                GL11.glPushMatrix();
                GL11.glDisable(2929);
                GL11.glTranslated(-iPX + x + 0.5 + (double)(tagscale * 2.0F * (float)dir.offsetX), -iPY + y - (double)shifty + 0.5 + (double)(tagscale * 2.0F * (float)dir.offsetY), -iPZ + z + 0.5 + (double)(tagscale * 2.0F * (float)dir.offsetZ));
                float xd = (float)(iPX - (x + 0.5));
                float zd = (float)(iPZ - (z + 0.5));
                float rotYaw = (float)(Math.atan2((double)xd, (double)zd) * 180.0 / Math.PI);
                GL11.glRotatef(rotYaw + 180.0F, 0.0F, 1.0F, 0.0F);
                GL11.glTranslated((double)shift, 0.0, 0.0);
                GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
                GL11.glScalef(tagscale, tagscale, tagscale);
                if (!Thaumcraft.proxy.playerKnowledge.hasDiscoveredAspect(player.getDisplayName(), tag)) {
                    UtilsFX.renderQuadCenteredFromTexture("textures/aspects/_unknown.png", 1.0F, (float)color.getRed() / 255.0F, (float)color.getGreen() / 255.0F, (float)color.getBlue() / 255.0F, bright, 771, 0.75F);
                    new Color(11184810);
                } else {
                    UtilsFX.renderQuadCenteredFromTexture(tag.getImage(), 1.0F, (float)color.getRed() / 255.0F, (float)color.getGreen() / 255.0F, (float)color.getBlue() / 255.0F, bright, 771, 0.75F);
                }

                if (aspectList.getAmount(tag) >= 0) {
                    String am = "" + aspectList.getAmount(tag);
                    GL11.glScalef(0.04F, 0.04F, 0.04F);
                    GL11.glTranslated(0.0, 6.0, -0.1);
                    int sw = Minecraft.getMinecraft().fontRenderer.getStringWidth(am);
                    GL11.glEnable(3042);
                    Minecraft.getMinecraft().fontRenderer.drawString(am, 14 - sw, 1, 1118481);
                    GL11.glTranslated(0.0, 0.0, -0.1);
                    Minecraft.getMinecraft().fontRenderer.drawString(am, 13 - sw, 0, 16777215);
                }

                GL11.glEnable(2929);
                GL11.glPopMatrix();
                ++current;
            }
        }

    }
}
