package com.gabid.ezaciancraft.registry;

import com.gabid.ezaciancraft.api.common.blocks.tileentity.EzacianCustomJarFillableTE;
import com.gabid.ezaciancraft.common.blocks.tileentity.*;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.tileentity.TileEntity;

import static com.gabid.ezaciancraft.api.EzacianCraftGeneralLang.*;

public class EzacianCraftTileEntities {

    public static void setupTileEntities() {
        registerTileEntity(TileEntityAlchemicalMixer.class, UNLOCALE_ALCHEMICAL_MIXER);
        registerTileEntity(TileEntityShadowAlchemyFurnace.class, UNLOCALE_SHADOW_ALCHEMY_FURNACE);
        registerTileEntity(TileEntityExtendedArcaneWorkbench.class, UNLOCALE_EXTENDED_ARCANE_WORKBENCH);
        registerTileEntity(TileEntityWirelessEssentiaInterfaceBase.class, UNLOCALE_WIRELESS_ESSENTIA_INTERFACE);
        registerTileEntity(TileEntityEtherealAccelerator.class, UNLOCALE_ETHEREAL_ACCELERATOR);
        registerTileEntity(EzacianCustomJarFillableTE.class, UNLOCALE_CUSTOM_BASE_JAR);
    }

    private static <T extends TileEntity> void registerTileEntity(Class<T> classTE, String unlocaleBlockName) {
        GameRegistry.registerTileEntity(classTE, "te_" + unlocaleBlockName);
    }
}
