package com.gabid.ezaciancraft.client.renderer.blocks;

import com.gabid.ezaciancraft.common.blocks.tileentity.AlchemicalMixerTileEntity;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.world.IBlockAccess;
import org.lwjgl.opengl.GL11;

//this is really for itemBlock...
public class AlchemicalMixerBlockRender implements ISimpleBlockRenderingHandler {

    public AlchemicalMixerBlockRender() {

    }

    @Override
    public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer) {
        try {
            GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
            TileEntityRendererDispatcher.instance.renderTileEntityAt(new AlchemicalMixerTileEntity(), 0.0, 0.0, 0.0, 0.0F);
            GL11.glEnable(32826);
        } catch (IllegalStateException e) {}
    }

    @Override
    public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {
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
        return -1;
    }
}
