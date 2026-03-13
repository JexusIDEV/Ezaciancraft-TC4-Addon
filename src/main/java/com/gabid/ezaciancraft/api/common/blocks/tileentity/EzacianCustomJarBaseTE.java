package com.gabid.ezaciancraft.api.common.blocks.tileentity;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.ForgeDirection;
import thaumcraft.api.TileThaumcraft;
import thaumcraft.common.tiles.TileJarFillable;

import java.util.Random;

public class EzacianCustomJarBaseTE extends TileThaumcraft {
    ResourceLocation texture = new ResourceLocation("thaumcraft", "textures/models/jar.png");

    public EzacianCustomJarBaseTE() {
    }

    @SideOnly(Side.CLIENT)
    public AxisAlignedBB getRenderBoundingBox() {
        return AxisAlignedBB.getBoundingBox(this.xCoord, this.yCoord, this.zCoord, (this.xCoord + 1), (this.yCoord + 1), (double) (this.zCoord + 1));
    }

    @Override
    public void updateEntity() {
        super.updateEntity();
    }

    public ResourceLocation getTexture() {
        return this.texture;
    }
}
