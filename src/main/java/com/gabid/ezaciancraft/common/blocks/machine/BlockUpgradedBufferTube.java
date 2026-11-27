package com.gabid.ezaciancraft.common.blocks.machine;

import static com.gabid.ezaciancraft.CoreMod.MODID;
import static com.gabid.ezaciancraft.api.EzacianCraftGeneralLang.UNLOCALE_UPGRADED_BUFFER_TUBE;

import java.util.LinkedList;
import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.client.event.DrawBlockHighlightEvent;

import com.gabid.ezaciancraft.common.blocks.tileentity.TileEntityUpgradedBufferTube;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import thaumcraft.codechicken.lib.raytracer.IndexedCuboid6;
import thaumcraft.codechicken.lib.raytracer.RayTracer;
import thaumcraft.codechicken.lib.vec.BlockCoord;
import thaumcraft.codechicken.lib.vec.Vector3;
import thaumcraft.common.blocks.BlockTube;
import thaumcraft.common.items.relics.ItemResonator;
import thaumcraft.common.items.wands.ItemWandCasting;
import thaumcraft.common.tiles.TileTube;
import thaumcraft.common.tiles.TileTubeBuffer;

public class BlockUpgradedBufferTube extends BlockTube {

    public IIcon[] icon = new IIcon[2];
    private final RayTracer rayTracer = new RayTracer();

    public BlockUpgradedBufferTube() {
        this.setBlockName(UNLOCALE_UPGRADED_BUFFER_TUBE);
    }

    @Override
    public void getSubBlocks(Item item, CreativeTabs tabs, List list) {
        list.add(new ItemStack(item, 1, 5));
    }

    @Override
    public void registerBlockIcons(IIconRegister register) {
        this.icon[0] = register.registerIcon(new ResourceLocation("thaumcraft", "pipe_1").toString());
        this.icon[1] = register.registerIcon(new ResourceLocation(MODID, "upgraded_pipe_buffer").toString());
    }

    @Override
    public IIcon getIcon(int side, int meta) {
        return this.icon[1];
    }

    @Override
    public TileEntity createTileEntity(World world, int metadata) {
        return new TileEntityUpgradedBufferTube();
    }

    @Override
    public boolean hasComparatorInputOverride() {
        return true;
    }

    @Override
    public int getComparatorInputOverride(World world, int x, int y, int z, int meta) {
        TileEntity te = world.getTileEntity(x, y, z);
        if (te instanceof TileTubeBuffer buffTE) {
            float aspectsSize = (float) buffTE.aspects.visSize();
            float r = aspectsSize / 8.0F;
            return MathHelper.floor_float(r * 14.0F) + (buffTE.aspects.visSize() > 0 ? 1 : 0);
        } else {
            return 0;
        }
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void onBlockHighlight(DrawBlockHighlightEvent event) {
        if (event.target.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK
            && event.player.worldObj.getBlock(event.target.blockX, event.target.blockY, event.target.blockZ) == this
            && event.player.worldObj.getBlockMetadata(event.target.blockX, event.target.blockY, event.target.blockZ)
                != 7
            && event.player.getCurrentEquippedItem() != null
            && (event.player.getHeldItem()
                .getItem() instanceof ItemWandCasting
                || event.player.getHeldItem()
                    .getItem() instanceof ItemResonator)) {
            RayTracer.retraceBlock(
                event.player.worldObj,
                event.player,
                event.target.blockX,
                event.target.blockY,
                event.target.blockZ);
        }
    }

    @Override
    public MovingObjectPosition collisionRayTrace(World world, int x, int y, int z, Vec3 start, Vec3 end) {
        TileEntity tile = world.getTileEntity(x, y, z);
        if ((tile instanceof TileTube || tile instanceof TileTubeBuffer)) {
            List<IndexedCuboid6> cuboids = new LinkedList<>();
            if (tile instanceof TileTubeBuffer) {
                ((TileTubeBuffer) tile).addTraceableCuboids(cuboids);
            }
            return this.rayTracer
                .rayTraceCuboids(new Vector3(start), new Vector3(end), cuboids, new BlockCoord(x, y, z), this);
        } else {
            return super.collisionRayTrace(world, x, y, z, start, end);
        }
    }

    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase plyr, ItemStack stack) {
        TileEntity tile = world.getTileEntity(x, y, z);
        if (tile instanceof TileEntityUpgradedBufferTube buff) {
            buff.setMaxAmount(32);
        }
        super.onBlockPlacedBy(world, x, y, z, plyr, stack);
    }
}
