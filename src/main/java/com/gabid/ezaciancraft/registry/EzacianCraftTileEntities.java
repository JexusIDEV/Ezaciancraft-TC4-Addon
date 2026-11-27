package com.gabid.ezaciancraft.registry;

import static com.gabid.ezaciancraft.api.EzacianCraftGeneralLang.UNLOCALE_ADVANCED_ESSENTIA_STORAGE;
import static com.gabid.ezaciancraft.api.EzacianCraftGeneralLang.UNLOCALE_ALCHEMICAL_MIXER;
import static com.gabid.ezaciancraft.api.EzacianCraftGeneralLang.UNLOCALE_ASPECT_CROP_BASENAME;
import static com.gabid.ezaciancraft.api.EzacianCraftGeneralLang.UNLOCALE_CUSTOM_BASE_JAR;
import static com.gabid.ezaciancraft.api.EzacianCraftGeneralLang.UNLOCALE_ETHEREAL_ACCELERATOR;
import static com.gabid.ezaciancraft.api.EzacianCraftGeneralLang.UNLOCALE_EXTENDED_ARCANE_WORKBENCH;
import static com.gabid.ezaciancraft.api.EzacianCraftGeneralLang.UNLOCALE_SHADOW_ALCHEMY_FURNACE;
import static com.gabid.ezaciancraft.api.EzacianCraftGeneralLang.UNLOCALE_UPGRADED_BUFFER_TUBE;
import static com.gabid.ezaciancraft.api.EzacianCraftGeneralLang.UNLOCALE_WIRELESS_ESSENTIA_INTERFACE;

import net.minecraft.tileentity.TileEntity;

import com.gabid.ezaciancraft.api.common.blocks.tileentity.EzacianCustomJarFillableTE;
import com.gabid.ezaciancraft.common.blocks.tileentity.TileEntityAdvancedEssentiaStorage;
import com.gabid.ezaciancraft.common.blocks.tileentity.TileEntityAdvancedEssentiaStorageInterface;
import com.gabid.ezaciancraft.common.blocks.tileentity.TileEntityAlchemicalMixer;
import com.gabid.ezaciancraft.common.blocks.tileentity.TileEntityEtherealAccelerator;
import com.gabid.ezaciancraft.common.blocks.tileentity.TileEntityExtendedArcaneWorkbench;
import com.gabid.ezaciancraft.common.blocks.tileentity.TileEntityShadowAlchemyFurnace;
import com.gabid.ezaciancraft.common.blocks.tileentity.TileEntityUpgradedBufferTube;
import com.gabid.ezaciancraft.common.blocks.tileentity.TileEntityWirelessEssentiaInterfaceInput;
import com.gabid.ezaciancraft.common.blocks.tileentity.TileEntityWirelessEssentiaInterfaceOutput;
import com.gabid.ezaciancraft.common.blocks.vegetal.TileEntityAspectCrop;

import cpw.mods.fml.common.registry.GameRegistry;

/// The tile entities of the mod
public class EzacianCraftTileEntities {

    public static void setupTileEntities() {
        registerTileEntity(TileEntityAlchemicalMixer.class, UNLOCALE_ALCHEMICAL_MIXER);
        registerTileEntity(TileEntityShadowAlchemyFurnace.class, UNLOCALE_SHADOW_ALCHEMY_FURNACE);
        registerTileEntity(TileEntityExtendedArcaneWorkbench.class, UNLOCALE_EXTENDED_ARCANE_WORKBENCH);
        registerTileEntity(
            TileEntityWirelessEssentiaInterfaceOutput.class,
            UNLOCALE_WIRELESS_ESSENTIA_INTERFACE + "Output");
        registerTileEntity(
            TileEntityWirelessEssentiaInterfaceInput.class,
            UNLOCALE_WIRELESS_ESSENTIA_INTERFACE + "Input");
        registerTileEntity(TileEntityAdvancedEssentiaStorage.class, UNLOCALE_ADVANCED_ESSENTIA_STORAGE);
        registerTileEntity(
            TileEntityAdvancedEssentiaStorageInterface.class,
            UNLOCALE_ADVANCED_ESSENTIA_STORAGE + "Interface");
        registerTileEntity(TileEntityUpgradedBufferTube.class, UNLOCALE_UPGRADED_BUFFER_TUBE);
        registerTileEntity(TileEntityEtherealAccelerator.class, UNLOCALE_ETHEREAL_ACCELERATOR);
        registerTileEntity(EzacianCustomJarFillableTE.class, UNLOCALE_CUSTOM_BASE_JAR);

        registerTileEntity(TileEntityAspectCrop.class, UNLOCALE_ASPECT_CROP_BASENAME);
    }

    private static <T extends TileEntity> void registerTileEntity(Class<T> classTE, String unlocaleBlockName) {
        GameRegistry.registerTileEntity(classTE, "te_" + unlocaleBlockName);
    }
}
