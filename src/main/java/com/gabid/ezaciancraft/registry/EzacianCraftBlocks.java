package com.gabid.ezaciancraft.registry;

import com.gabid.ezaciancraft.common.blocks.AlchemicalMixerBlock;
import com.gabid.ezaciancraft.common.blocks.EtherealAcceleratorBlock;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockCompressed;
import net.minecraft.block.BlockOre;
import net.minecraft.block.material.MapColor;
import net.minecraft.util.ResourceLocation;

import static com.gabid.ezaciancraft.CoreMod.MODID;
import static com.gabid.ezaciancraft.api.EzacianCraftGeneralLang.*;
import static com.gabid.ezaciancraft.registry.EzacianCraftCreativeTab.*;

public class EzacianCraftBlocks {

    //functional block TE's
    public static Block alchemicalMixer;
    public static Block etherealAccelerator;

    //extra void resources
    public static Block voidSeedOre;
    public static Block voidMetalBlock;

    public static void setupBlocksRegistry() {
        //TE stuff
        alchemicalMixer = new AlchemicalMixerBlock();
        alchemicalMixer.setCreativeTab(EZACIANCRAFT_TAB);
        GameRegistry.registerBlock(alchemicalMixer, alchemicalMixer.getUnlocalizedName());

        etherealAccelerator = new EtherealAcceleratorBlock();
        etherealAccelerator.setCreativeTab(EZACIANCRAFT_TAB);
        GameRegistry.registerBlock(etherealAccelerator, etherealAccelerator.getUnlocalizedName());

        //extra void resources
        voidSeedOre = new BlockOre();
        voidSeedOre.setBlockName(UNLOCALE_VOID_SEED_ORE);
        voidSeedOre.setBlockTextureName(new ResourceLocation(MODID, UNLOCALE_VOID_SEED_ORE).toString());
        voidSeedOre.setCreativeTab(EZACIANCRAFT_RESOURCES_TAB);
        GameRegistry.registerBlock(voidSeedOre, voidSeedOre.getUnlocalizedName());

        voidMetalBlock = new BlockCompressed(MapColor.purpleColor);
        voidMetalBlock.setBlockName(UNLOCALE_VOID_METAL_BLOCK);
        voidMetalBlock.setBlockTextureName(new ResourceLocation(MODID, UNLOCALE_VOID_METAL_BLOCK).toString());
        voidMetalBlock.setStepSound(Block.soundTypeMetal);
        voidMetalBlock.setCreativeTab(EZACIANCRAFT_RESOURCES_TAB);
        GameRegistry.registerBlock(voidMetalBlock, voidMetalBlock.getUnlocalizedName());
    }
}
