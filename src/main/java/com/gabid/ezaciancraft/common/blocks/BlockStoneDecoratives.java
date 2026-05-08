package com.gabid.ezaciancraft.common.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import java.util.List;

import static com.gabid.ezaciancraft.CoreMod.MODID;
import static com.gabid.ezaciancraft.api.EzacianCraftGeneralLang.UNLOCALE_EZACIAN_STONE_DECORATIVE;

public class BlockStoneDecoratives extends Block {

    public IIcon ezacianChiseledSymbol;

    public BlockStoneDecoratives() {
        super(Material.rock);
        this.setBlockName(UNLOCALE_EZACIAN_STONE_DECORATIVE);
        this.setHarvestLevel("pickaxe", 1);
    }

    @Override
    public void getSubBlocks(Item item, CreativeTabs tab, List list) {
        list.add(new ItemStack(this, 1, 0));
    }

    @Override
    public void registerBlockIcons(IIconRegister register) {
        super.registerBlockIcons(register);
        this.ezacianChiseledSymbol = register.registerIcon(new ResourceLocation(MODID, "ezacianDecorartiveBlock_0").toString());
    }

    @Override
    public IIcon getIcon(int side, int meta) {
        if (meta == 0) {
            return this.ezacianChiseledSymbol;
        } else {
            return super.getIcon(side, meta);
        }
    }

    @Override
    public float getBlockHardness(World world, int x, int y, int z) {
        int meta = world.getBlockMetadata(x, y, z);
        switch (meta) {
            case 0:
                return 2f;
            default:
                return super.getBlockHardness(world, x, y, z);
        }
    }

    @Override
    public float getExplosionResistance(Entity par1Entity, World world, int x, int y, int z, double explosionX, double explosionY, double explosionZ) {
        int meta = world.getBlockMetadata(x, y, z);
        switch (meta) {
            case 0:
                return 1f;
            default:
                return super.getExplosionResistance(par1Entity, world, x, y, z, explosionX, explosionY, explosionZ);
        }
    }
}
