package com.gabid.ezaciancraft.client.renderer.tiles;

import com.gabid.ezaciancraft.client.renderer.models.AlchemicalMixerModel;
import com.gabid.ezaciancraft.common.blocks.tileentity.AlchemicalMixerTileEntity;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import static com.gabid.ezaciancraft.CoreMod.MODID;
import static com.gabid.ezaciancraft.api.EzacianCraftGeneralLang.UNLOCALE_ALCHEMICAL_MIXER;

@SideOnly(Side.CLIENT)
public class AlchemicalMixerTileEntityRenderer extends TileEntitySpecialRenderer {

    private final ResourceLocation texturePath = new ResourceLocation(MODID, "textures/blocks/"+UNLOCALE_ALCHEMICAL_MIXER+"Atlas.png");
    public static AlchemicalMixerModel model = new AlchemicalMixerModel();

    public AlchemicalMixerTileEntityRenderer() {
    }

    private void renderAlchemicalMixerTE(AlchemicalMixerTileEntity te, double x, double y, double z, float ticks) {
        //float rotation = te.rotation;
        int teDirection = te.metaFacing;

        GL11.glPushMatrix();
        GL11.glTranslated(x+0.5, y-0.5, z+0.5);
        GL11.glRotatef(0f, 0f, 1f, 0f);
        rotateBlockByOrientation(teDirection);
        bindTexture(texturePath);
        model.renderPipes();
        model.renderWhiskerMixer();
        this.renderStoredEssentia(te, ticks);
        model.renderDeco();
        model.renderContainer();
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

    private void renderStoredEssentia(AlchemicalMixerTileEntity te, float ticks) {
        int currentStored = 0;
    }
}
