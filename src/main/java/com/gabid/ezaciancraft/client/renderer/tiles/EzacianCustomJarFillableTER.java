package com.gabid.ezaciancraft.client.renderer.tiles;

import com.gabid.ezaciancraft.api.common.blocks.tileentity.EzacianCustomJarFillableTE;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import org.lwjgl.opengl.GL11;
import thaumcraft.client.lib.UtilsFX;
import thaumcraft.common.blocks.BlockJar;
import thaumcraft.common.config.Config;
import thaumcraft.common.config.ConfigBlocks;

public class EzacianCustomJarFillableTER extends TileEntitySpecialRenderer {
    public void renderTileEntityAt(EzacianCustomJarFillableTE tile, double x, double y, double z, float f) {
        GL11.glPushMatrix();
        GL11.glDisable(2884);
        GL11.glTranslatef((float) x + 0.5F, (float) y + 0.01F, (float) z + 0.5F);
        GL11.glRotatef(180.0F, 1.0F, 0.0F, 0.0F);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        if ((tile).amount > 0) {
            this.renderLiquid(tile, x, y, z, f);
        }

        if ((tile).aspectFilter != null) {
            GL11.glPushMatrix();
            switch ((tile).facing) {
                case 3:
                    GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);
                    break;
                case 4:
                    GL11.glRotatef(270.0F, 0.0F, 1.0F, 0.0F);
                    break;
                case 5:
                    GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
            }

            float rot = (float) (((tile).aspectFilter.getTag().hashCode() + tile.xCoord + (tile).facing) % 4 - 2);
            GL11.glPushMatrix();
            GL11.glTranslatef(0.0F, -0.4F, 0.315F);
            if (Config.crooked) {
                GL11.glRotatef(rot, 0.0F, 0.0F, 1.0F);
            }

            UtilsFX.renderQuadCenteredFromTexture("textures/models/label.png", 0.5F, 1.0F, 1.0F, 1.0F, -99, 771, 1.0F);
            GL11.glPopMatrix();
            GL11.glPushMatrix();
            GL11.glTranslatef(0.0F, -0.4F, 0.316F);
            if (Config.crooked) {
                GL11.glRotatef(rot, 0.0F, 0.0F, 1.0F);
            }

            GL11.glScaled(0.021, 0.021, 0.021);
            GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);
            UtilsFX.drawTag(-8, -8, (tile).aspectFilter);
            GL11.glPopMatrix();
            GL11.glPopMatrix();
        }

        this.bindTexture(tile.getTexture());
        GL11.glEnable(2884);
        GL11.glPopMatrix();
    }

    public void renderLiquid(EzacianCustomJarFillableTE te, double x, double y, double z, float f) {
        if (this.field_147501_a.field_147553_e != null) {
            GL11.glPushMatrix();
            GL11.glRotatef(180.0F, 1.0F, 0.0F, 0.0F);
            RenderBlocks renderBlocks = new RenderBlocks();
            GL11.glDisable(2896);
            float level = (float) te.amount / (float) te.maxAmount * 0.625F;
            Tessellator t = Tessellator.instance;
            renderBlocks.setRenderBounds(0.25, 0.0625, 0.25, 0.75, 0.0625 + (double) level, 0.75);
            t.startDrawingQuads();

            if (te.aspect != null) {
                t.setColorOpaque_I(te.aspect.getColor());
            }

            int bright = 200;
            if (te.getWorldObj() != null) {
                bright = Math.max(200, ConfigBlocks.blockJar.getMixedBrightnessForBlock(te.getWorldObj(), te.xCoord, te.yCoord, te.zCoord));
            }

            t.setBrightness(bright);
            IIcon icon = ((BlockJar) ConfigBlocks.blockJar).iconLiquid;
            this.field_147501_a.field_147553_e.bindTexture(TextureMap.locationBlocksTexture);
            renderBlocks.renderFaceYNeg(ConfigBlocks.blockJar, -0.5, 0.0, -0.5, icon);
            renderBlocks.renderFaceYPos(ConfigBlocks.blockJar, -0.5, 0.0, -0.5, icon);
            renderBlocks.renderFaceZNeg(ConfigBlocks.blockJar, -0.5, 0.0, -0.5, icon);
            renderBlocks.renderFaceZPos(ConfigBlocks.blockJar, -0.5, 0.0, -0.5, icon);
            renderBlocks.renderFaceXNeg(ConfigBlocks.blockJar, -0.5, 0.0, -0.5, icon);
            renderBlocks.renderFaceXPos(ConfigBlocks.blockJar, -0.5, 0.0, -0.5, icon);

            t.draw();
            GL11.glEnable(2896);
            GL11.glPopMatrix();
            GL11.glColor3f(1.0F, 1.0F, 1.0F);
        }
    }

    @Override
    public void renderTileEntityAt(TileEntity te, double a, double b, double c, float d) {
        this.renderTileEntityAt((EzacianCustomJarFillableTE) te, a, b, c, d);
    }
}
