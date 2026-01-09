package com.gabid.ezaciancraft.registry;

import com.gabid.ezaciancraft.common.blocks.AlchemicalMixerBlock;
import com.gabid.ezaciancraft.common.blocks.EtherealAcceleratorBlock;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockCompressed;
import net.minecraft.block.material.MapColor;
import net.minecraft.util.ResourceLocation;

import static com.gabid.ezaciancraft.CoreMod.MODID;
import static com.gabid.ezaciancraft.api.EzacianCraftGeneralLang.UNLOCALE_SHADOW_VOID_METAL_BLOCK;

public class EzacianCraftBlocks {

    //functional block TE's
    public static Block alchemicalMixer;
    public static Block etherealAccelerator;

    public static void setupBlocksRegistry() {
        //TE stuff
        alchemicalMixer = new AlchemicalMixerBlock();
        alchemicalMixer.setCreativeTab(EzacianCraftCreativeTab.EZACIANCRAFT_TAB);
        GameRegistry.registerBlock(alchemicalMixer, alchemicalMixer.getUnlocalizedName());

        etherealAccelerator = new EtherealAcceleratorBlock();
        etherealAccelerator.setCreativeTab(EzacianCraftCreativeTab.EZACIANCRAFT_TAB);
        GameRegistry.registerBlock(etherealAccelerator, etherealAccelerator.getUnlocalizedName());
    }
}
