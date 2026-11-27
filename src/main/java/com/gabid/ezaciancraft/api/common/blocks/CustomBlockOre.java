package com.gabid.ezaciancraft.api.common.blocks;

import java.util.Random;

import net.minecraft.block.BlockOre;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;

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
