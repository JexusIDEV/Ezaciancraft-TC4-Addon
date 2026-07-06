package com.gabid.ezaciancraft.api.common.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.world.IBlockAccess;

public class BaseGenericLightBlock extends BaseGenericBlock {
    protected int lightPotency;

    public BaseGenericLightBlock(Material material, String _unlocName, float blockHardness, float explosionResistance, SoundType sound, CreativeTabs tab,int _lightPotency) {
        super(material, _unlocName, blockHardness, explosionResistance, sound, tab);
        this.lightPotency = _lightPotency;
    }

    @Override
    public int getLightValue(IBlockAccess world, int x, int y, int z) {
        return this.lightPotency;
    }
}
