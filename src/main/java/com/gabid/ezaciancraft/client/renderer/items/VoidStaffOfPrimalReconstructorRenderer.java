package com.gabid.ezaciancraft.client.renderer.items;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;
import org.lwjgl.opengl.GL11;

import static com.gabid.ezaciancraft.CoreMod.MODID;

public class VoidStaffOfPrimalReconstructorRenderer implements IItemRenderer {

    private static final IModelCustom staffModel = AdvancedModelLoader.loadModel(new ResourceLocation(MODID, "models/items/staff.obj"));
    private static final ResourceLocation staffTexture = new ResourceLocation(MODID, "textures/models/staff_atlas.png");

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
        switch (type) {
            case EQUIPPED:
                GL11.glPushMatrix();
                Minecraft.getMinecraft().renderEngine.bindTexture(staffTexture);
                GL11.glRotatef(135, -0.3f, -1, 0);
                GL11.glScalef(1.75f, 1.75f, 1.75f);
                GL11.glTranslatef(0.15f, 0.125f, -0.375f);
                staffModel.renderAll();
                GL11.glPopMatrix();
                break;
            case EQUIPPED_FIRST_PERSON: {
                GL11.glPushMatrix();
                Minecraft.getMinecraft().renderEngine.bindTexture(staffTexture);
                GL11.glTranslatef(1.5f, 0.15f, .75f);
                GL11.glScalef(1.5f, 1.5f, 1.5f);
                GL11.glRotatef(80, -1f, 0.15f, 0f);
                staffModel.renderAll();
                GL11.glPopMatrix();
                break;
            }
            case INVENTORY:
                GL11.glPushMatrix();
                Minecraft.getMinecraft().renderEngine.bindTexture(staffTexture);
                GL11.glTranslatef(-0.1f, 0f, 0f);
                GL11.glScalef(.625f, .625f, .625f);
                GL11.glRotatef(202.5f, 1f, 0f, 0f);
                staffModel.renderAll();
                GL11.glPopMatrix();
                break;
            case ENTITY:
                GL11.glPushMatrix();
                Minecraft.getMinecraft().renderEngine.bindTexture(staffTexture);
                GL11.glScalef(.45f, .45f, .45f);
                GL11.glTranslatef(0f, -.275f, 0f);
                staffModel.renderAll();
                GL11.glPopMatrix();
                break;
            default:
                GL11.glPushMatrix();
                Minecraft.getMinecraft().renderEngine.bindTexture(staffTexture);
                staffModel.renderAll();
                GL11.glPopMatrix();
                break;
        }
    }
}
