package com.gabid.ezaciancraft.client.renderer.tiles;

import com.gabid.ezaciancraft.common.blocks.tileentity.TileEntityExtendedArcaneWorkbench;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import org.lwjgl.opengl.GL11;
import thaumcraft.common.items.wands.ItemWandCasting;

public class ExtendedArcaneWorkbenchTileEntityRenderer extends TileEntitySpecialRenderer {


    public void renderTEAndWand(TileEntityExtendedArcaneWorkbench arcaneWorkbenchTE, double x, double y, double z, float ticks) {
        if (arcaneWorkbenchTE.func_70301_a(10) != null && arcaneWorkbenchTE.func_70301_a(10).getItem() instanceof ItemWandCasting) {
            GL11.glPushMatrix();

            ItemStack copyWand = arcaneWorkbenchTE.func_70301_a(10).copy();
            copyWand.stackSize = 1;
            ItemWandCasting wandItem = (ItemWandCasting) copyWand.getItem();
            EntityItem entItem = new EntityItem(arcaneWorkbenchTE.getWorldObj(), 0, 0, 0, copyWand);
            if (wandItem.isStaff(copyWand)) {
                GL11.glTranslatef((float) x + 0.125F, (float) y + 1.05F, (float) z + 0.125F);
            } else {
                GL11.glTranslatef((float) x + 0.3F, (float) y + 1.05F, (float) z + 0.3F);
            }
            GL11.glRotatef(90.0F, 0.0F, 0.0F, -1F);
            GL11.glRotatef(45.0F, 1.0F, 0.0F, 0F);

            entItem.hoverStart = 0.0F;
            RenderItem.renderInFrame = true;

            RenderManager.instance.renderEntityWithPosYaw(entItem, 0d, 0d, 0d, 0f, 0f);

            RenderItem.renderInFrame = false;
            GL11.glPopMatrix();
        }
    }

    @Override
    public void renderTileEntityAt(TileEntity te, double a, double b, double c, float d) {
        renderTEAndWand((TileEntityExtendedArcaneWorkbench) te, a, b, c, d);
    }
}
