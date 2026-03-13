package com.gabid.ezaciancraft.registry;

import com.gabid.ezaciancraft.api.common.blocks.tileentity.EzacianCustomJarFillableTE;
import com.gabid.ezaciancraft.common.blocks.tileentity.AlchemicalMixerTileEntity;
import com.gabid.ezaciancraft.common.blocks.tileentity.EtherealAcceleratorTE;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.tileentity.TileEntity;

import static com.gabid.ezaciancraft.api.EzacianCraftGeneralLang.*;

public class EzacianCraftTileEntities {

    public static void setupTileEntities() {
        registerTileEntity(AlchemicalMixerTileEntity.class, UNLOCALE_ALCHEMICAL_MIXER);
        registerTileEntity(EtherealAcceleratorTE.class, UNLOCALE_ETHEREAL_ACCELERATOR);
        registerTileEntity(EzacianCustomJarFillableTE.class, UNLOCALE_CUSTOM_BASE_JAR);
    }

    private static <T extends TileEntity> void registerTileEntity(Class<T> classTE, String unlocaleBlockName) {
        GameRegistry.registerTileEntity(classTE, "te_"+unlocaleBlockName);
    }
}
