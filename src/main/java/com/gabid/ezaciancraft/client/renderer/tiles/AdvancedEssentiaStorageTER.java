package com.gabid.ezaciancraft.client.renderer.tiles;

import com.gabid.ezaciancraft.common.blocks.tileentity.TileEntityAdvancedEssentiaStorage;
import com.gabid.ezaciancraft.common.blocks.tileentity.TileEntityAlchemicalMixer;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;
import org.lwjgl.opengl.GL11;

import static com.gabid.ezaciancraft.CoreMod.MODID;
import static com.gabid.ezaciancraft.api.EzacianCraftGeneralLang.UNLOCALE_ALCHEMICAL_MIXER;

@SideOnly(Side.CLIENT)
public class AdvancedEssentiaStorageTER extends TileEntitySpecialRenderer {

    private final ResourceLocation texturePath = new ResourceLocation(MODID, "textures/models/blocks/base_tex.png");
    public IModelCustom model = AdvancedModelLoader.loadModel(new ResourceLocation(MODID, "models/blocks/essentiaStorage.obj"));

    public AdvancedEssentiaStorageTER() {
    }

    private void renderAlchemicalMixerTE(TileEntityAdvancedEssentiaStorage te, double x, double y, double z, float ticks) {
        GL11.glPushMatrix();
        GL11.glTranslated(x+.5, y-.5, z+.5);
        bindTexture(texturePath);
        this.model.renderAll();
        GL11.glPopMatrix();
    }

    @Override
    public void renderTileEntityAt(TileEntity te, double x, double y, double z, float ticks) {
        this.renderAlchemicalMixerTE((TileEntityAdvancedEssentiaStorage) te, x, y, z, ticks);
    }


}
