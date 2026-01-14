package com.gabid.ezaciancraft.client.renderer.items.blocks;

import com.gabid.ezaciancraft.common.blocks.tileentity.AlchemicalMixerTileEntity;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.ItemStack;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.client.IItemRenderer;
import org.lwjgl.opengl.GL11;

import static com.gabid.ezaciancraft.registry.EzacianCraftTypeRenders.ALCHEMICAL_MIXER_RENDER_ID;
import static thaumcraft.client.renderers.block.BlockRenderer.W14;
import static thaumcraft.client.renderers.block.BlockRenderer.W2;

//this is really for itemBlock...
public class AlchemicalMixerBlockRender implements IItemRenderer {

    public AlchemicalMixerBlockRender() {

    }

    @Override
    public boolean handleRenderType(ItemStack item, ItemRenderType type) {
        return true;
    }

    @Override
    public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
        return true;
    }

    @Override
    public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
        GL11.glPushMatrix();
        if(type == ItemRenderType.ENTITY) {
            GL11.glScaled(1.5, 1.5, 1.5);
            GL11.glTranslated(-0.5, 0.15, -0.5);
        }

        AlchemicalMixerTileEntity te = new AlchemicalMixerTileEntity();

        GL11.glScaled(1.25, 1.25, 1.25);
        GL11.glTranslated(0, -0.125, 0);
        TileEntityRendererDispatcher.instance.renderTileEntityAt(te,0f,0f,0f,0f);
        GL11.glPopMatrix();
    }
}
