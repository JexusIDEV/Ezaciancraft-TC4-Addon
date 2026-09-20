package com.gabid.ezaciancraft.client.renderer.tiles;

import com.gabid.ezaciancraft.common.blocks.vegetal.BlockAspectCrop;
import com.gabid.ezaciancraft.common.blocks.vegetal.TileEntityAspectCrop;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import org.lwjgl.opengl.GL11;

public class AspectedCropBlockTileEntityRenderer extends TileEntitySpecialRenderer {

    private void renderPlant(TileEntityAspectCrop te, double x, double y, double z, float t) {
        if(te.hasWorldObj()) {
            Tessellator tes = Tessellator.instance;
            BlockAspectCrop cropBlock = (BlockAspectCrop) te.blockType;
            int color;
            int currentMeta = te.blockMetadata;
            float scale = 0.5f;

            if (te.getMainAspect() != null) {
                color = te.getMainAspect().getColor();
            } else {
                color = 0xffffff;
            }

            float r = (color >> 16 & 255) / 255.0F;
            float g = (color >> 8 & 255) / 255.0F;
            float b = (color & 255) / 255.0F;

            GL11.glPushMatrix();
            GL11.glTranslated(x+0.5, y, z+0.5);

            bindTexture(TextureMap.locationBlocksTexture);

            IIcon currentStageCrop = cropBlock.getIcon(0, currentMeta);

            float minU = currentStageCrop.getMinU();
            float maxU = currentStageCrop.getMaxU();
            float minV = currentStageCrop.getMinV();
            float maxV = currentStageCrop.getMaxV();

            GL11.glDisable(GL11.GL_LIGHTING);
            GL11.glDisable(GL11.GL_CULL_FACE);
            tes.startDrawingQuads();
            tes.setBrightness(cropBlock.getMixedBrightnessForBlock(te.getWorldObj(), te.xCoord, te.yCoord, te.zCoord));
            tes.setColorOpaque_F(1F, 1F, 1F);

            this.renderCrossModel(tes, minU, maxU, minV, maxV, scale);
            tes.draw();

            if (currentMeta >= cropBlock.stages - 1 && te.getMainAspect() != null) {
                IIcon overlay = cropBlock.colouredParts;

                minU = overlay.getMinU();
                maxU = overlay.getMaxU();
                minV = overlay.getMinV();
                maxV = overlay.getMaxV();

                tes.startDrawingQuads();
                tes.setColorOpaque_F(r, g, b);

                this.renderCrossModel(tes, minU, maxU, minV, maxV, scale);

                tes.draw();
                tes.setColorOpaque_F(1F, 1F, 1F);
            }
            GL11.glEnable(GL11.GL_CULL_FACE);
            GL11.glEnable(GL11.GL_LIGHTING);
            GL11.glPopMatrix();
        }
    }

    @Override
    public void renderTileEntityAt(TileEntity te, double x, double y, double z, float t) {
        this.renderPlant((TileEntityAspectCrop) te, x, y, z, t);
    }

    private void renderCrossModel(Tessellator tes, float minU, float maxU, float minV, float maxV, float scale) {
        tes.addVertexWithUV(-scale, 0, -scale, minU, maxV);
        tes.addVertexWithUV(scale, 0, scale, maxU, maxV);
        tes.addVertexWithUV(scale, 1, scale, maxU, minV);
        tes.addVertexWithUV(-scale, 1, -scale, minU, minV);

        tes.addVertexWithUV(-scale, 0, scale, minU, maxV);
        tes.addVertexWithUV(scale, 0, -scale, maxU, maxV);
        tes.addVertexWithUV(scale, 1, -scale, maxU, minV);
        tes.addVertexWithUV(-scale, 1, scale, minU, minV);
    }
}
