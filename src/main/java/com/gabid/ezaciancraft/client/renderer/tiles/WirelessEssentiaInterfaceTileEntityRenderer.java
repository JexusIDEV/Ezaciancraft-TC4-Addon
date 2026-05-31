package com.gabid.ezaciancraft.client.renderer.tiles;

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
import thaumcraft.api.TileThaumcraft;

import static com.gabid.ezaciancraft.CoreMod.MODID;

@SideOnly(Side.CLIENT)
public class WirelessEssentiaInterfaceTileEntityRenderer extends TileEntitySpecialRenderer {

    private final ResourceLocation WEISTexPath = new ResourceLocation(MODID, "textures/models/blocks/wirelessEssentiaInterfacesTex.png");
    public IModelCustom outputModel = AdvancedModelLoader.loadModel(new ResourceLocation(MODID, "models/blocks/outputWirelessEssentiaInterface.obj"));
    public IModelCustom inputModel = AdvancedModelLoader.loadModel(new ResourceLocation(MODID, "models/blocks/inputWirelessEssentiaInterface.obj"));

    public WirelessEssentiaInterfaceTileEntityRenderer() {
    }

    private void renderWirelessEssentiaInterfaceTE(TileThaumcraft te, double x, double y, double z, float ticks) {
        GL11.glPushMatrix();
        GL11.glTranslated(x + 0.5, y + 0.5, z + 0.5);
        this.bindTexture(this.WEISTexPath);

        if (te instanceof TileEntityWirelessEssentiaInterfaceOutput) {
            TileEntityWirelessEssentiaInterfaceOutput wire = (TileEntityWirelessEssentiaInterfaceOutput) te;
            this.translateFromOrientation(wire.metaFacing);
            this.outputModel.renderAll();
        } else if (te instanceof TileEntityWirelessEssentiaInterfaceInput) {
            TileEntityWirelessEssentiaInterfaceInput wire = (TileEntityWirelessEssentiaInterfaceInput) te;
            this.translateFromOrientation(wire.metaFacing);
            this.inputModel.renderAll();
        }
        GL11.glPopMatrix();
    }

    private void translateFromOrientation(int orientation) {
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
        this.renderWirelessEssentiaInterfaceTE((TileThaumcraft) te, x, y, z, ticks);
    }
}
