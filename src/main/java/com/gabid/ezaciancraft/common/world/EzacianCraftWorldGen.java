package com.gabid.ezaciancraft.common.world;

import com.gabid.ezaciancraft.registry.EzacianCraftBlocks;
import com.gabid.ezaciancraft.registry.EzacianCraftResources;
import cpw.mods.fml.common.IWorldGenerator;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenMinable;
import thaumcraft.common.lib.world.ThaumcraftWorldGenerator;

import java.util.Random;

public class EzacianCraftWorldGen implements IWorldGenerator {

    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {
        int x = chunkX * 16;
        int z = chunkZ * 16;

        switch (world.provider.dimensionId) {
            case -1:
                this.generateInNether(world, x, z, random);
                break;
            case 1:
                this.generateInEnd(world, x, z, random);
                break;
            default:
                this.generateInOverworld(world, x, z, random);
                break;
        }
    }

    private void generateInOverworld(World world, int chunkX, int chunkZ, Random random) {
        //void seed ore gen
        if (world.provider.getBiomeGenForCoords(chunkX, chunkZ) == ThaumcraftWorldGenerator.biomeTaint) {
            this.addOre(EzacianCraftBlocks.voidSeedOre, world, random, chunkX, chunkZ, 1, 6, 8, 6, 16);
            this.addOre(EzacianCraftResources.shadowVoidMetalResources.getResourceOreBlock(), world, random, chunkX, chunkZ, 1, 8, 4, 1, 8);
        }

        //crystalyium gen
        this.addOre(EzacianCraftResources.crudeCrystalyiumResources.getResourceOreBlock(), world, random, chunkX, chunkZ, 1, 4, 4, 8, 12);
    }

    private void generateInNether(World world, int chunkX, int chunkZ, Random random) {

    }

    private void generateInEnd(World world, int chunkX, int chunkZ, Random random) {

    }

    public void addOre(Block block, World world, Random random, int chunkXPos, int chunkZPos, int minVeinSize, int maxVeinSize, int chancesToSpawn, int minY, int maxY) {
        for (int i = 0; i < chancesToSpawn; ++i) {
            int posX = chunkXPos + random.nextInt(16);
            int posY = minY + random.nextInt(maxY - minY);
            int posZ = chunkZPos + random.nextInt(16);
            new WorldGenMinable(block, 0, minVeinSize + random.nextInt(maxVeinSize - minVeinSize), Blocks.stone).generate(world, random, posX, posY, posZ);
        }
    }
}
