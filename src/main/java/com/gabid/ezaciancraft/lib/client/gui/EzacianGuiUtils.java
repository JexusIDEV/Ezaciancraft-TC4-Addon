package com.gabid.ezaciancraft.lib.client.gui;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import org.lwjgl.opengl.GL11;

import java.util.Iterator;
import java.util.List;

//sorry brandon...
public class EzacianGuiUtils {
    public static final double PXL128 = 0.0078125;
    public static final double PXL256 = 0.00390625;

    public static boolean isInRect(int x, int y, int xSize, int ySize, int mouseX, int mouseY) {
        return mouseX >= x && mouseX <= x + xSize && mouseY >= y && mouseY <= y + ySize;
    }

    public static void drawTexturedRect(int x, int y, int u, int v, int width, int height) {
        drawTexturedRect(x, y, width, height, u, v, width, height, 0.0, 0.00390625);
    }

    public static void drawTexturedRect(double x, double y, double width, double height, int u, int v, int uSize, int vSize, double zLevel, double pxl) {
        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        tessellator.addVertexWithUV(x, y + height, zLevel, (double) u * pxl, (double) (v + vSize) * pxl);
        tessellator.addVertexWithUV(x + width, y + height, zLevel, (double) (u + uSize) * pxl, (double) (v + vSize) * pxl);
        tessellator.addVertexWithUV(x + width, y, zLevel, (double) (u + uSize) * pxl, (double) v * pxl);
        tessellator.addVertexWithUV(x, y, zLevel, (double) u * pxl, (double) v * pxl);
        tessellator.draw();
    }

    public static void drawGradientRect(int x1, int y1, int x2, int y2, int colour1, int colour2, float fade, double scale) {
        float f = (float) (colour1 >> 24 & 255) / 255.0F * fade;
        float f1 = (float) (colour1 >> 16 & 255) / 255.0F;
        float f2 = (float) (colour1 >> 8 & 255) / 255.0F;
        float f3 = (float) (colour1 & 255) / 255.0F;
        float f4 = (float) (colour2 >> 24 & 255) / 255.0F * fade;
        float f5 = (float) (colour2 >> 16 & 255) / 255.0F;
        float f6 = (float) (colour2 >> 8 & 255) / 255.0F;
        float f7 = (float) (colour2 & 255) / 255.0F;
        GL11.glDisable(3553);
        GL11.glEnable(3042);
        GL11.glDisable(3008);
        OpenGlHelper.glBlendFunc(770, 771, 1, 0);
        GL11.glShadeModel(7425);
        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        tessellator.setColorRGBA_F(f1, f2, f3, f);
        tessellator.addVertex(x2, y1, 300.0);
        tessellator.addVertex(x1, y1, 300.0);
        tessellator.setColorRGBA_F(f5, f6, f7, f4);
        tessellator.addVertex(x1, y2, 300.0);
        tessellator.addVertex(x2, y2, 300.0);
        tessellator.draw();
        GL11.glShadeModel(7424);
        GL11.glDisable(3042);
        GL11.glEnable(3008);
        GL11.glEnable(3553);
    }

    public static void drawHoveringText(List list, int x, int y, FontRenderer font, float fade, double scale, int guiWidth, int guiHeight) {
        if (!list.isEmpty()) {
            GL11.glPushMatrix();
            GL11.glDisable(32826);
            GL11.glDisable(2896);
            GL11.glDisable(2929);
            GL11.glScaled(scale, scale, 1.0);
            x = (int) ((double) x / scale);
            y = (int) ((double) y / scale);
            int k = 0;
            Iterator iterator = list.iterator();

            int adjY;
            while (iterator.hasNext()) {
                String s = (String) iterator.next();
                adjY = font.getStringWidth(s);
                if (adjY > k) {
                    k = adjY;
                }
            }

            int adjX = x + 12;
            adjY = y - 12;
            int i1 = 8;
            if (list.size() > 1) {
                i1 += 2 + (list.size() - 1) * 10;
            }

            if (adjX + k > (int) ((double) guiWidth / scale)) {
                adjX -= 28 + k;
            }

            if (adjY + i1 + 6 > (int) ((double) guiHeight / scale)) {
                adjY = (int) ((double) guiHeight / scale) - i1 - 6;
            }

            int j1 = -267386864;
            drawGradientRect(adjX - 3, adjY - 4, adjX + k + 3, adjY - 3, j1, j1, fade, scale);
            drawGradientRect(adjX - 3, adjY + i1 + 3, adjX + k + 3, adjY + i1 + 4, j1, j1, fade, scale);
            drawGradientRect(adjX - 3, adjY - 3, adjX + k + 3, adjY + i1 + 3, j1, j1, fade, scale);
            drawGradientRect(adjX - 4, adjY - 3, adjX - 3, adjY + i1 + 3, j1, j1, fade, scale);
            drawGradientRect(adjX + k + 3, adjY - 3, adjX + k + 4, adjY + i1 + 3, j1, j1, fade, scale);
            int k1 = 1347420415;
            int l1 = (k1 & 16711422) >> 1 | k1 & -16777216;
            drawGradientRect(adjX - 3, adjY - 3 + 1, adjX - 3 + 1, adjY + i1 + 3 - 1, k1, l1, fade, scale);
            drawGradientRect(adjX + k + 2, adjY - 3 + 1, adjX + k + 3, adjY + i1 + 3 - 1, k1, l1, fade, scale);
            drawGradientRect(adjX - 3, adjY - 3, adjX + k + 3, adjY - 3 + 1, k1, k1, fade, scale);
            drawGradientRect(adjX - 3, adjY + i1 + 2, adjX + k + 3, adjY + i1 + 3, l1, l1, fade, scale);

            for (Object o : list) {
                String s1 = (String) o;
                GL11.glEnable(3042);
                GL11.glDisable(3008);
                OpenGlHelper.glBlendFunc(770, 771, 1, 0);
                font.drawStringWithShadow(s1, adjX, adjY, (int) (fade * 240.0F) + 16 << 24 | 16777215);
                adjY += 10;
            }

            GL11.glEnable(2896);
            GL11.glEnable(2929);
            GL11.glEnable(32826);
            GL11.glPopMatrix();
        }

    }
}
