package com.gabid.ezaciancraft.registry;

import com.gabid.ezaciancraft.api.common.blocks.EzacianCustomBlockJar;
import com.gabid.ezaciancraft.api.common.items.BasicNamedItemBlockWithMetadata;
import com.gabid.ezaciancraft.api.common.items.EzacianCustomItemBlockJar;
import com.gabid.ezaciancraft.common.blocks.*;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockCompressed;
import net.minecraft.block.BlockOre;
import net.minecraft.block.material.MapColor;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;

import static com.gabid.ezaciancraft.CoreMod.MODID;
import static com.gabid.ezaciancraft.api.EzacianCraftGeneralLang.UNLOCALE_VOID_METAL_BLOCK;
import static com.gabid.ezaciancraft.api.EzacianCraftGeneralLang.UNLOCALE_VOID_SEED_ORE;
import static com.gabid.ezaciancraft.registry.EzacianCraftCreativeTab.*;

public class EzacianCraftBlocks {

    //basic blocks
    public static Block ezacianStoneDecorativeBlocks;

    //functional block TE's
    public static Block alchemicalMixer;
    public static Block shadowAlchemyFurnace;
    public static Block wirelessEssentiaInterface;
    public static Block extendedArcaneWorkbench;
    public static Block etherealAccelerator;

    //extra void resources
    public static Block voidSeedOre;
    public static Block voidMetalBlock;

    public static EzacianCustomBlockJar crystalyiumJar;
    public static EzacianCustomBlockJar shadowVoidMetalJar;

    public static void setupBlocksRegistry() {
        //basicBlocks
        ezacianStoneDecorativeBlocks = new BlockStoneDecoratives();
        ezacianStoneDecorativeBlocks.setCreativeTab(EZACIANCRAFT_DECORATIVES_TAB);
        GameRegistry.registerBlock(ezacianStoneDecorativeBlocks, BasicNamedItemBlockWithMetadata.class, ezacianStoneDecorativeBlocks.getUnlocalizedName());

        //TE stuff
        alchemicalMixer = new BlockAlchemicalMixer();
        alchemicalMixer.setCreativeTab(EZACIANCRAFT_TAB);
        GameRegistry.registerBlock(alchemicalMixer, alchemicalMixer.getUnlocalizedName());

        shadowAlchemyFurnace = new BlockShadowAlchemyFurnace();
        shadowAlchemyFurnace.setCreativeTab(EZACIANCRAFT_TAB);
        GameRegistry.registerBlock(shadowAlchemyFurnace, ItemBlock.class, shadowAlchemyFurnace.getUnlocalizedName());

        //wirelessEssentiaInterface = new BlockWirelessEssentiaInterface();
        //wirelessEssentiaInterface.setCreativeTab(EZACIANCRAFT_TAB);
        //GameRegistry.registerBlock(wirelessEssentiaInterface, ItemBlockWirelessEssentiaInterface.class, wirelessEssentiaInterface.getUnlocalizedName());

        /*etherealAccelerator = new EtherealAcceleratorBlock();
        etherealAccelerator.setCreativeTab(EZACIANCRAFT_TAB);
        GameRegistry.registerBlock(etherealAccelerator, etherealAccelerator.getUnlocalizedName());*/

        extendedArcaneWorkbench = new BlockExtendedArcaneWorkbench();
        extendedArcaneWorkbench.setCreativeTab(EZACIANCRAFT_TAB);
        GameRegistry.registerBlock(extendedArcaneWorkbench, ItemBlock.class, extendedArcaneWorkbench.getUnlocalizedName());

        crystalyiumJar = new BlockCrystalyiumJar();
        GameRegistry.registerBlock(crystalyiumJar, EzacianCustomItemBlockJar.class, crystalyiumJar.getUnlocalizedName());
        shadowVoidMetalJar = new BlockShadowVoidMetalJar();
        GameRegistry.registerBlock(shadowVoidMetalJar, EzacianCustomItemBlockJar.class, shadowVoidMetalJar.getUnlocalizedName());

        //extra void resources
        voidSeedOre = new BlockOre();
        voidSeedOre.setBlockName(UNLOCALE_VOID_SEED_ORE);
        voidSeedOre.setBlockTextureName(new ResourceLocation(MODID, UNLOCALE_VOID_SEED_ORE).toString());
        voidSeedOre.setHardness(3.5f);
        voidSeedOre.setResistance(5f);
        voidSeedOre.setCreativeTab(EZACIANCRAFT_RESOURCES_TAB);
        GameRegistry.registerBlock(voidSeedOre, voidSeedOre.getUnlocalizedName());

        voidMetalBlock = new BlockCompressed(MapColor.purpleColor);
        voidMetalBlock.setBlockName(UNLOCALE_VOID_METAL_BLOCK);
        voidMetalBlock.setBlockTextureName(new ResourceLocation(MODID, UNLOCALE_VOID_METAL_BLOCK).toString());
        voidMetalBlock.setStepSound(Block.soundTypeMetal);
        voidMetalBlock.setHardness(5.25f);
        voidMetalBlock.setResistance(10.25f);
        voidMetalBlock.setCreativeTab(EZACIANCRAFT_RESOURCES_TAB);
        GameRegistry.registerBlock(voidMetalBlock, voidMetalBlock.getUnlocalizedName());
    }
}
