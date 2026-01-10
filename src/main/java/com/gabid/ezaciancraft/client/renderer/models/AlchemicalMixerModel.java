package com.gabid.ezaciancraft.client.renderer.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import org.lwjgl.opengl.GL11;

public class AlchemicalMixerModel extends ModelBase {

    private final float scaleModelRender = 0.0625f;

    //fields
    public ModelRenderer Container;
    public ModelRenderer PipeOutput;
    public ModelRenderer PipeOutputConnected;
    public ModelRenderer PipeInputA;
    public ModelRenderer PipeInputB;
    public ModelRenderer Deco1;
    public ModelRenderer Deco2;
    public ModelRenderer Deco3;
    public ModelRenderer PipeDecoInputA;
    public ModelRenderer PipeDecoInputB;
    public ModelRenderer PipeDecoOutput;
    public ModelRenderer WhiskerBase;
    public ModelRenderer WhiskerRod;
    public ModelRenderer WhiskerHorizontal;
    public ModelRenderer WhiskerVertical;

    public AlchemicalMixerModel()
    {
        this.textureWidth = 128;
        this.textureHeight = 64;
        Container = new ModelRenderer(this, 0, 0);
        Container.addBox(0F, 0F, 0F, 8, 8, 8, 0F);
        Container.setRotationPoint(-4F, 12F, -4F);
        this.setModelRotationAngles(this.Container, 0f,0f,0f);
        Container.mirror = false;
        PipeOutput = new ModelRenderer(this, 48, 0);
        PipeOutput.addBox(0F, 0F, 0F, 4, 2, 4, 0F);
        PipeOutput.setRotationPoint(-2F, 8F, -2F);
        this.setModelRotationAngles(this.PipeOutput, 0f,0f,0f);
        PipeOutputConnected = new ModelRenderer(this, 48, 7);
        PipeOutputConnected.addBox(0F, 0F, 0F, 2, 6, 2, 0F);
        PipeOutputConnected.setRotationPoint(-1F, 2F, -1F);
        this.setModelRotationAngles(this.PipeOutputConnected, 0f,0f,0f);
        PipeOutputConnected.mirror = false;
        PipeInputA = new ModelRenderer(this, 33, 9);
        PipeInputA.addBox(0F, 0F, 0F, 3, 4, 4, 0F);
        PipeInputA.setRotationPoint(5F, 14F, -2F);
        this.setModelRotationAngles(this.PipeInputA, 0f,0f,0f);
        PipeInputA.mirror = false;
        PipeInputB = new ModelRenderer(this, 33, 0);
        PipeInputB.addBox(0F, 0F, 0F, 3, 4, 4, 0F);
        PipeInputB.setRotationPoint(-8F, 14F, -2F);
        this.setModelRotationAngles(this.PipeInputB, 0f,0f,0f);
        PipeInputB.mirror = false;
        PipeDecoInputA = new ModelRenderer(this, 17, 17);
        PipeDecoInputA.addBox(0F, 0F, 0F, 2, 6, 6, 0F);
        PipeDecoInputA.setRotationPoint(3F, 13F, -3F);
        this.setModelRotationAngles(this.PipeDecoInputA, 0f,0f,0f);
        PipeDecoInputA.mirror = false;
        PipeDecoInputB = new ModelRenderer(this, 0, 17);
        PipeDecoInputB.addBox(0F, 0F, 0F, 2, 6, 6, 0F);
        PipeDecoInputB.setRotationPoint(-5F, 13F, -3F);
        this.setModelRotationAngles(this.PipeDecoInputB, 0f,0f,0f);
        PipeDecoInputB.mirror = false;
        PipeDecoOutput = new ModelRenderer(this, 34, 20);
        PipeDecoOutput.addBox(0F, 0F, 0F, 6, 3, 6, 0F);
        PipeDecoOutput.setRotationPoint(-3F, 10F, -3F);
        this.setModelRotationAngles(this.PipeDecoOutput, 0f,0f,0f);
        PipeDecoOutput.mirror = false;
        WhiskerBase = new ModelRenderer(this, 41, 30);
        WhiskerBase.addBox(-1.5F, -1.5F, -0.5F, 3, 3, 1, 0F);
        WhiskerBase.setRotationPoint(0F, 16F, 4F);
        this.setModelRotationAngles(this.WhiskerBase, 0f,0f,0f);
        WhiskerBase.mirror = false;
        Deco1 = new ModelRenderer(this, 0, 30);
        Deco1.addBox(0F, 0F, 0F, 10, 1, 10, 0F);
        Deco1.setRotationPoint(-5F, 11F, -5F);
        this.setModelRotationAngles(Deco1, 0f,0f,0f);
        Deco1.mirror = false;
        Deco2 = new ModelRenderer(this, 0, 42);
        Deco2.addBox(0F, 0F, 0F, 8, 1, 8, 0F);
        Deco2.setRotationPoint(-4F, 21F, -4F);
        this.setModelRotationAngles(Deco2, 0f,0f,0f);
        Deco2.mirror = false;
        Deco3 = new ModelRenderer(this, 0, 30);
        Deco3.addBox(0F, 0F, 0F, 10, 1, 10, 0F);
        Deco3.setRotationPoint(-5F, 20F, -5F);
        this.setModelRotationAngles(Deco3, 0f,0f,0f);
        Deco3.mirror = false;
        WhiskerRod = new ModelRenderer(this, 41, 35);
        WhiskerRod.addBox(-0.5F, -0.5F, -1.5F, 1, 1, 5, 0F);
        WhiskerRod.setRotationPoint(0F, 16F, -1F);
        WhiskerRod.setRotationPoint(0F, 16F, 0F);
        WhiskerRod.mirror = false;
        WhiskerHorizontal = new ModelRenderer(this, 41, 42);
        WhiskerHorizontal.addBox(-2.5F, -0.5F, -0.5F, 5, 1, 1, 0F);
        WhiskerHorizontal.setRotationPoint(0F, 16F, 0F);
        this.setModelRotationAngles(WhiskerHorizontal, 0f,0f,0f);
        WhiskerHorizontal.mirror = false;
        WhiskerVertical = new ModelRenderer(this, 50, 30);
        WhiskerVertical.addBox(-0.5F, -2.5F, -0.5F, 1, 5, 1, 0F);
        WhiskerVertical.setRotationPoint(0F, 16F, 0F);
        this.setModelRotationAngles(WhiskerVertical, 0f,0f,0f);
        WhiskerVertical.mirror = false;
    }

    public void setModelRotationAngles(ModelRenderer model, float x, float y, float z) {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }

    public void renderPipes() {
        this.PipeInputA.render(scaleModelRender);
        this.PipeDecoInputA.render(scaleModelRender);
        this.PipeInputB.render(scaleModelRender);
        this.PipeDecoInputB.render(scaleModelRender);
        this.PipeOutput.render(scaleModelRender);
        this.PipeDecoOutput.render(scaleModelRender);
    }

    public void renderWhiskerMixer() {
        this.WhiskerBase.render(scaleModelRender);
        this.WhiskerRod.render(scaleModelRender);
        this.WhiskerHorizontal.render(scaleModelRender);
        this.WhiskerVertical.render(scaleModelRender);
    }

    public void renderContainer() {
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.3F);
        this.Container.render(scaleModelRender);
        GL11.glDisable(GL11.GL_BLEND);
    }

    public void renderDeco() {
        this.Deco1.render(scaleModelRender);
        this.Deco2.render(scaleModelRender);
        this.Deco3.render(scaleModelRender);
    }

    public void renderConnected() {
        this.PipeOutputConnected.render(scaleModelRender);
    }
}
