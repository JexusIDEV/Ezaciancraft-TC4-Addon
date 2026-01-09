package com.gabid.ezaciancraft.registry;

import com.gabid.ezaciancraft.common.blocks.tileentity.AlchemicalMixerTileEntity;
import com.gabid.ezaciancraft.common.blocks.tileentity.EtherealAcceleratorTE;
import cpw.mods.fml.common.registry.GameRegistry;

import static com.gabid.ezaciancraft.api.EzacianCraftGeneralLang.UNLOCALE_ALCHEMICAL_MIXER;
import static com.gabid.ezaciancraft.api.EzacianCraftGeneralLang.UNLOCALE_ETHEREAL_ACCELERATOR;

public class EzacianCraftTileEntities {

    public static void setupTileEntities() {
        GameRegistry.registerTileEntity(AlchemicalMixerTileEntity.class, "te_"+UNLOCALE_ALCHEMICAL_MIXER);
        GameRegistry.registerTileEntity(EtherealAcceleratorTE.class, "te_"+UNLOCALE_ETHEREAL_ACCELERATOR);
    }

}
