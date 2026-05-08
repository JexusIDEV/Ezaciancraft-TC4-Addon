package com.gabid.ezaciancraft.common.items;

import com.gabid.ezaciancraft.common.blocks.tileentity.TileEntityWirelessEssentiaInterfaceBase;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlockWithMetadata;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class ItemBlockWirelessEssentiaInterface extends ItemBlockWithMetadata {
    public ItemBlockWirelessEssentiaInterface(Block blockDummy) {
        super(blockDummy, blockDummy);
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        return super.getUnlocalizedName() + "." + stack.getItemDamage();
    }

    @Override
    public boolean placeBlockAt(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ, int metadata) {
        boolean ret = super.placeBlockAt(stack, player, world, x, y, z, side, hitX, hitY, hitZ, metadata);
        TileEntity te = world.getTileEntity(x, y, z);
        if (te instanceof TileEntityWirelessEssentiaInterfaceBase) {
            TileEntityWirelessEssentiaInterfaceBase wei = (TileEntityWirelessEssentiaInterfaceBase) world.getTileEntity(x, y, z);
            wei.facing = ForgeDirection.getOrientation(side).getOpposite();
            wei.metaFacing = wei.facing.ordinal();
            wei.blockMetadata = stack.getItemDamage();
            wei.markDirty();
            world.markBlockForUpdate(x, y, z);
        }
        return ret;
    }
}
