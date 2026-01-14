package com.gabid.ezaciancraft.client.renderer.tiles;

import com.gabid.ezaciancraft.client.renderer.models.AlchemicalMixerModel;
import com.gabid.ezaciancraft.common.blocks.tileentity.AlchemicalMixerTileEntity;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.ForgeDirection;
import org.lwjgl.opengl.GL11;
import thaumcraft.api.ThaumcraftApiHelper;
import thaumcraft.api.aspects.IEssentiaTransport;
import thaumcraft.common.blocks.BlockJar;
import thaumcraft.common.config.ConfigBlocks;

import static com.gabid.ezaciancraft.CoreMod.MODID;
import static com.gabid.ezaciancraft.api.EzacianCraftGeneralLang.UNLOCALE_ALCHEMICAL_MIXER;
import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_LIGHTING;

@SideOnly(Side.CLIENT)
public class AlchemicalMixerTileEntityRenderer extends TileEntitySpecialRenderer {

    private final ResourceLocation texturePath = new ResourceLocation(MODID, "textures/blocks/"+UNLOCALE_ALCHEMICAL_MIXER+"Atlas.png");
    public AlchemicalMixerModel model = new AlchemicalMixerModel();

    public AlchemicalMixerTileEntityRenderer() {
    }

    private void renderAlchemicalMixerTE(AlchemicalMixerTileEntity te, double x, double y, double z, float ticks) {
        int teDirection = te.metaFacing;

        GL11.glPushMatrix();
        GL11.glTranslated(x+0.5, y-0.5, z+0.5);
        GL11.glRotatef(0f, 0f, 1f, 0f);
        this.rotateBlockByOrientation(teDirection);
        bindTexture(texturePath);
        this.model.renderPipes();
        this.renderIsDownConnected(te);
        GL11.glPopMatrix();

        GL11.glPushMatrix();
        GL11.glTranslated(x+0.5, y+-.5, z+0.5);
        this.rotateBlockByOrientation(teDirection);
        GL11.glTranslated(0, 1, 0);
        GL11.glRotatef(te.whiskerRotation, 0f, 0f, 1f);
        GL11.glTranslated(0, -1, 0);
        this.model.renderWhiskerMixer();
        GL11.glPopMatrix();

        GL11.glPushMatrix();
        GL11.glTranslated(x+0.5, y-0.5, z+0.5);
        GL11.glRotatef(0f, 0f, 1f, 0f);
        this.renderStoredEssentia(te);
        bindTexture(texturePath);
        this.model.renderDeco();
        this.model.renderContainer();
        GL11.glPopMatrix();
    }

    private void rotateBlockByOrientation(int metaFacing) {
        switch (metaFacing) {
            case 2:
                GL11.glRotatef(0.0F, 0.0F, 1.0F, 0.0F);
                break;
            case 3:
                GL11.glRotatef(180.0F, 0.0F, 1F, 0.0F);
                break;
            case 4:
                GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
                break;
            case 5:
                GL11.glRotatef(270.0F, 0.0F, 1.0F, 0.0F);
                break;
        }
    }

    @Override
    public void renderTileEntityAt(TileEntity te, double x, double y, double z, float ticks) {
        renderAlchemicalMixerTE((AlchemicalMixerTileEntity) te, x, y, z, ticks);
    }

    private void renderStoredEssentia(AlchemicalMixerTileEntity te) {
        if(this.field_147501_a.field_147553_e != null && te.currentAspectForColor != null && te.getAspects().visSize() != 0) {
            GL11.glPushMatrix();
            GL11.glEnable(GL_BLEND);
            GL11.glBlendFunc(770, 771);
            RenderBlocks renderBlocks = new RenderBlocks();
            GL11.glDisable(GL_LIGHTING);
            int currentStored = te.getAspects().visSize();
            float levelStored = (float) currentStored / te.maxAspectCapacity;
            Tessellator tessellator = Tessellator.instance;
            renderBlocks.setRenderBounds(-.225, .275, -.225, .225, (.175 + .55 * levelStored), .225);
            tessellator.startDrawingQuads();
            tessellator.setColorRGBA_F(te.colors.get(2).x, te.colors.get(2).y, te.colors.get(2).z, 0.9F);
            tessellator.setBrightness(200);
            IIcon icon = ((BlockJar) ConfigBlocks.blockJar).iconLiquid;
            this.field_147501_a.field_147553_e.bindTexture(TextureMap.locationBlocksTexture);
            renderBlocks.renderFaceYNeg(ConfigBlocks.blockEssentiaReservoir, 0.0, 0.5, 0.0, icon);
            renderBlocks.renderFaceYPos(ConfigBlocks.blockEssentiaReservoir, 0.0, 0.5, 0.0, icon);
            renderBlocks.renderFaceZNeg(ConfigBlocks.blockEssentiaReservoir, 0.0, 0.5, 0.0, icon);
            renderBlocks.renderFaceZPos(ConfigBlocks.blockEssentiaReservoir, 0.0, 0.5, 0.0, icon);
            renderBlocks.renderFaceXNeg(ConfigBlocks.blockEssentiaReservoir, 0.0, 0.5, 0.0, icon);
            renderBlocks.renderFaceXPos(ConfigBlocks.blockEssentiaReservoir, 0.0, 0.5, 0.0, icon);
            tessellator.draw();
            GL11.glEnable(GL_LIGHTING);
            GL11.glDisable(GL_BLEND);
            GL11.glPopMatrix();
        }
    }

    private void renderIsDownConnected(AlchemicalMixerTileEntity te) {
        if(te.getWorldObj() != null) {
            TileEntity down = ThaumcraftApiHelper.getConnectableTile(te.getWorldObj(), te.xCoord, te.yCoord, te.zCoord, ForgeDirection.DOWN);
            if(down instanceof IEssentiaTransport) {
                if (down.getBlockType() instanceof BlockJar) {
                    this.model.renderConnected();
                }
            }
        }
    }
}
