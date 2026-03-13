package com.gabid.ezaciancraft.client.renderer.items.blocks;

import com.gabid.ezaciancraft.api.common.blocks.tileentity.EzacianCustomJarFillableTE;
import com.gabid.ezaciancraft.api.common.blocks.tileentity.EzacianCustomJarFillableVoidTE;
import com.gabid.ezaciancraft.api.common.items.EzacianCustomItemJarFilled;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;
import org.lwjgl.opengl.GL11;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;

public class EzacianCustomItemJarFilledRenderer implements IItemRenderer {
    RenderBlocks renderBlocks = new RenderBlocks();

    @Override
    public boolean handleRenderType(ItemStack item, ItemRenderType type) {
        return item != null && item.getItem() instanceof EzacianCustomItemJarFilled;
    }

    @Override
    public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
        return helper != ItemRendererHelper.EQUIPPED_BLOCK;
    }

    @Override
    public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
        if (item.getItem() instanceof EzacianCustomItemJarFilled) {
            EzacianCustomItemJarFilled jarItem = (EzacianCustomItemJarFilled)item.getItem();
            if (type == ItemRenderType.ENTITY) {
                GL11.glScalef(0.5f, 0.5f, 0.5f);
                GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
            } else if (type == ItemRenderType.EQUIPPED && data[1] instanceof EntityPlayer) {
                GL11.glRotatef(25.0f, 0f, 0f, 1f);
                GL11.glRotatef(130.0f, 0f, 1f, 0f);
                GL11.glScalef(0.75f, 0.75f, 0.75f);
                GL11.glTranslatef(-1.325F, -0.725F, 0.375F);

            } else if(type == ItemRenderType.INVENTORY) {
                GL11.glTranslatef(0.5F, 0.325F, 0.5F);
            }

            EzacianCustomJarFillableTE jarTE = new EzacianCustomJarFillableTE();
            if (item.isItemDamaged()) {
                if (item.getItemDamage() == 3) {
                    jarTE = new EzacianCustomJarFillableVoidTE();
                }
            }

            jarTE.setMaxAmount(jarItem.getBlockJar().getNewEssentiaMaxAmount());
            AspectList aspects = jarItem.getAspects(item);
            if (aspects != null && aspects.size() == 1) {
                jarTE.amount = aspects.getAmount(aspects.getAspects()[0]);
                jarTE.aspect = aspects.getAspects()[0];
            }

            if(item.stackTagCompound != null) {
                String tf = item.getTagCompound().getString("AspectFilter");
                if (tf != null) {
                    jarTE.aspectFilter = Aspect.getAspect(tf);
                }
            }

            jarTE.facing = 5;
            jarTE.blockType = jarItem.getBlockJar();
            jarTE.blockMetadata = 0;
            TileEntityRendererDispatcher.instance.renderTileEntityAt(jarTE, 0.0, 0.0, 0.0, 0.0F);
            GL11.glPushMatrix();
            GL11.glTranslatef(0.5F, 0.5F, 0.5F);
            GL11.glEnable(3042);
            GL11.glBlendFunc(770, 771);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            Minecraft.getMinecraft().renderEngine.bindTexture(TextureMap.locationBlocksTexture);
            this.renderBlocks.useInventoryTint = true;
            this.renderBlocks.renderBlockAsItem(jarItem.getBlockJar(), item.getItemDamage(), 1.0F);
            GL11.glPopMatrix();
            GL11.glEnable(32826);
        }
    }
}
