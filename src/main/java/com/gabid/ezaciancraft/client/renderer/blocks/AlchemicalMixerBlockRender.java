package com.gabid.ezaciancraft.client.renderer.blocks;

import com.gabid.ezaciancraft.common.blocks.tileentity.AlchemicalMixerTileEntity;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.world.IBlockAccess;
import org.lwjgl.opengl.GL11;

import static com.gabid.ezaciancraft.registry.EzacianCraftTypeRenders.ALCHEMICAL_MIXER_RENDER_ID;
import static thaumcraft.client.renderers.block.BlockRenderer.W14;
import static thaumcraft.client.renderers.block.BlockRenderer.W2;

//this is really for itemBlock...
public class AlchemicalMixerBlockRender implements ISimpleBlockRenderingHandler {

    public AlchemicalMixerBlockRender() {

    }

    @Override
    public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer) {
        GL11.glPushMatrix();
        GL11.glScalef(1.25F, 1.25F, 1.25F);
        GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
        TileEntityRendererDispatcher.instance.renderTileEntityAt(new AlchemicalMixerTileEntity(), 0.0F, 0.0F, 0.0F, 0.0F);
        GL11.glEnable(32826);
        GL11.glPopMatrix();
    }

    @Override
    public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {
        block.setBlockBounds(W2, W2, W2, W14, W14, W14);
        renderer.setRenderBoundsFromBlock(block);
        renderer.renderStandardBlock(block, x, y, z);
        renderer.clearOverrideBlockTexture();
        block.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
        renderer.setRenderBoundsFromBlock(block);
        return true;
    }

    @Override
    public boolean shouldRender3DInInventory(int modelId) {
        return true;
    }

    @Override
    public int getRenderId() {
        return ALCHEMICAL_MIXER_RENDER_ID;
    }
}
