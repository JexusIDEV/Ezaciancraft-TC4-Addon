package com.gabid.ezaciancraft.api.common.items;

import com.gabid.ezaciancraft.api.common.blocks.EzacianCustomBlockJar;
import com.gabid.ezaciancraft.api.common.blocks.tileentity.EzacianCustomJarFillableTE;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.common.blocks.BlockJar;
import thaumcraft.common.blocks.ItemJarFilled;

public class EzacianCustomItemJarFilled extends ItemJarFilled {
    private final EzacianCustomBlockJar blockJar;

    public EzacianCustomItemJarFilled(EzacianCustomBlockJar _blockJar) {
        this.blockJar = _blockJar;
        this.setUnlocalizedName(_blockJar.getUnlocalizedName());
    }

    @Override
    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float a, float b, float c) {
        Block block = world.getBlock(x, y, z);
        if (block == Blocks.snow_layer && (world.getBlockMetadata(x, y, z) & 7) < 1) {
            side = 1;
        } else if (block != Blocks.vine && block != Blocks.tallgrass && block != Blocks.lava && !block.isReplaceable(world, x, y, z)) {
            if (side == 0) {
                --y;
            }

            if (side == 1) {
                ++y;
            }

            if (side == 2) {
                --z;
            }

            if (side == 3) {
                ++z;
            }

            if (side == 4) {
                --x;
            }

            if (side == 5) {
                ++x;
            }
        }

        if (stack.stackSize == 0) {
            return false;
        } else if (!player.canPlayerEdit(x, y, z, side, stack)) {
            return false;
        } else if (y == 255 && this.blockJar.getMaterial().isSolid()) {
            return false;
        } else if (world.canPlaceEntityOnSide(this.blockJar, x, y, z, false, side, player, stack)) {
            Block metaBlock = this.blockJar;
            int meta = this.getMetadata(stack.getItemDamage());
            int data = this.blockJar.onBlockPlaced(world, x, y, z, side, a, b, c, meta);
            if (this.placeBlockAt(stack, player, world, x, y, z, side, a, b, c, data)) {
                TileEntity te = world.getTileEntity(x, y, z);
                if (te instanceof EzacianCustomJarFillableTE && stack.hasTagCompound()) {
                    EzacianCustomJarFillableTE jarTE = (EzacianCustomJarFillableTE)te;
                    AspectList aspects = this.getAspects(stack);
                    if (aspects != null && aspects.size() == 1) {
                        jarTE.amount = aspects.getAmount(aspects.getAspects()[0]);
                        jarTE.aspect = aspects.getAspects()[0];
                    }

                    String aspectFilter = stack.stackTagCompound.getString("AspectFilter");
                    if (aspectFilter != null) {
                        jarTE.aspectFilter = Aspect.getAspect(aspectFilter);
                    }
                }
                world.playSoundEffect(((float)x + 0.5F), ((float)y + 0.5F), ((float)z + 0.5F), metaBlock.stepSound.func_150496_b(), (metaBlock.stepSound.getVolume() + 1.0F) / 2.0F, metaBlock.stepSound.frequency * 0.8F);
                --stack.stackSize;
            }
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean placeBlockAt(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ, int metadata) {
        if (!world.setBlock(x, y, z, this.blockJar, metadata, 3) && this.blockJar != null) {
            return false;
        } else {
            if (world.getBlock(x, y, z) instanceof BlockJar && this.blockJar != null) {
                this.blockJar.onBlockPlacedBy(world, x, y, z, player, stack);
                this.blockJar.onPostBlockPlaced(world, x, y, z, metadata);
            }
            return true;
        }
    }

    public EzacianCustomBlockJar getBlockJar() {
        return this.blockJar;
    }
}