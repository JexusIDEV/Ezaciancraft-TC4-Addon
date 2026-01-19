package com.gabid.ezaciancraft.api.common.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class EzacianCustomItemBlockPlant extends ItemBlock {
    protected final EzacianCustomBlockPlant plant;

    public EzacianCustomItemBlockPlant(Block _plant) {
        super(_plant);
        this.plant = (EzacianCustomBlockPlant) _plant;
        this.setMaxDamage(0);
        this.setHasSubtypes(true);
    }

    @Override
    public IIcon getIcon(ItemStack stack, int pass) {
        return this.plant.plantIcons[stack.getItemDamage()];
    }

    @SideOnly(Side.CLIENT)
    @Override
    public IIcon getIconFromDamage(int meta) {
        return this.plant.plantIcons[meta];
    }

    @Override
    public int getMetadata(int meta)
    {
        return meta;
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        return super.getUnlocalizedName() + "." + stack.getItemDamage();
    }

    @Override
    public boolean placeBlockAt(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ, int metadata) {
        boolean placed = super.placeBlockAt(stack, player, world, x, y, z, side, hitX, hitY, hitZ, metadata);
        if (placed) {
            try {
                world.setBlockMetadataWithNotify(x, y, z, this.getDamage(stack), side);
            } catch (Exception var14) {
            }
        }
        return placed;
    }
}
