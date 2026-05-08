package com.gabid.ezaciancraft.client.renderer.tiles;

import com.gabid.ezaciancraft.common.blocks.tileentity.TileEntityWirelessEssentiaInterfaceBase;
import com.gabid.ezaciancraft.common.blocks.tileentity.TileEntityWirelessEssentiaInterfaceInput;
import com.gabid.ezaciancraft.common.blocks.tileentity.TileEntityWirelessEssentiaInterfaceOutput;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;
import org.lwjgl.opengl.GL11;

import static com.gabid.ezaciancraft.CoreMod.MODID;

@SideOnly(Side.CLIENT)
public class WirelessEssentiaInterfaceTileEntityRenderer extends TileEntitySpecialRenderer {

    private final ResourceLocation texturePath = new ResourceLocation(MODID, "textures/models/blocks/base_tex.png");
    public IModelCustom outputModel = AdvancedModelLoader.loadModel(new ResourceLocation(MODID, "models/blocks/outputWirelessEssentiaInterface.obj"));
    public IModelCustom inputModel = AdvancedModelLoader.loadModel(new ResourceLocation(MODID, "models/blocks/inputWirelessEssentiaInterface.obj"));

    public WirelessEssentiaInterfaceTileEntityRenderer() {
    }

    private void renderWirelessEssentiaInterfaceTE(TileEntityWirelessEssentiaInterfaceBase te, double x, double y, double z, float ticks) {
        GL11.glPushMatrix();
        int teDirection = te.metaFacing;
        this.translateFromOrientation(x, y, z, teDirection);
        this.bindTexture(this.texturePath);

        if (te instanceof TileEntityWirelessEssentiaInterfaceOutput) {
            this.outputModel.renderAll();
        } else if (te instanceof TileEntityWirelessEssentiaInterfaceInput) {
            this.inputModel.renderAll();
        }
        GL11.glPopMatrix();
    }

    private void translateFromOrientation(double x, double y, double z, int orientation) {
        GL11.glTranslated(x + 0.5, y + 0.5, z + 0.5);
        if (orientation == 0) {
            GL11.glRotatef(-90.0F, 1.0F, 0.0F, 0.0F);
        } else if (orientation == 1) {
            GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F);
        } else if (orientation != 2) {
            if (orientation == 3) {
                GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);
            } else if (orientation == 4) {
                GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
            } else if (orientation == 5) {
                GL11.glRotatef(-90.0F, 0.0F, 1.0F, 0.0F);
            }
        }
    }

    @Override
    public void renderTileEntityAt(TileEntity te, double x, double y, double z, float ticks) {
        this.renderWirelessEssentiaInterfaceTE((TileEntityWirelessEssentiaInterfaceBase) te, x, y, z, ticks);
    }
}
