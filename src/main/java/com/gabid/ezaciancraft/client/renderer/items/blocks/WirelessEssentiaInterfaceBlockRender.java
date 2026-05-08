package com.gabid.ezaciancraft.client.renderer.items.blocks;

import com.gabid.ezaciancraft.common.blocks.tileentity.TileEntityWirelessEssentiaInterfaceBase;
import com.gabid.ezaciancraft.common.blocks.tileentity.TileEntityWirelessEssentiaInterfaceInput;
import com.gabid.ezaciancraft.common.blocks.tileentity.TileEntityWirelessEssentiaInterfaceOutput;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;
import org.lwjgl.opengl.GL11;

//this is really for itemBlock...
public class WirelessEssentiaInterfaceBlockRender implements IItemRenderer {

    public WirelessEssentiaInterfaceBlockRender() {
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

        int meta = item.getItemDamage();

        if (type == ItemRenderType.ENTITY) {
            GL11.glScaled(1.25f, 1.25f, 1.25f);
            GL11.glTranslated(0.5f, 0.15f, -1f);
        }
        if (type == ItemRenderType.INVENTORY) {
            GL11.glRotatef(180f, 0f, 1f, -0.065f);
            if (meta == 0) {
                GL11.glTranslated(0.125f, -1.175f, -.125f);
            } else if (meta == 1) {
                GL11.glTranslated(0.125f, -1.125f, 0f);
            }

            GL11.glScaled(1.25f, 1.25f, 1.25f);
        }

        TileEntityWirelessEssentiaInterfaceBase weiTE = null;
        if (meta == 0) {
            weiTE = new TileEntityWirelessEssentiaInterfaceOutput();
        } else if (meta == 1) {
            weiTE = new TileEntityWirelessEssentiaInterfaceInput();
        }

        TileEntityRendererDispatcher.instance.renderTileEntityAt(weiTE, 0f, 0f, 0f, 0f);
        GL11.glPopMatrix();
    }
}
