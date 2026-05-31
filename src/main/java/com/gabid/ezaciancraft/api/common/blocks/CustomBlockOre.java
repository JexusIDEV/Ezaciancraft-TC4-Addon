package com.gabid.ezaciancraft.api.common.blocks;

import net.minecraft.block.BlockOre;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;

import java.util.Random;

public class CustomBlockOre extends BlockOre {

    protected float experience;

    public CustomBlockOre(float _experience) {
        super();
        this.experience = _experience;
    }

    private final Random rand = new Random();

    @Override
    public int getExpDrop(IBlockAccess access, int meta, int fortune) {
        return MathHelper.getRandomIntegerInRange(this.rand, 1, (int) this.experience);
    }
}
