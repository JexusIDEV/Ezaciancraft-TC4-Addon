package com.gabid.ezaciancraft.registry;

import cpw.mods.fml.common.event.FMLInterModComms;
import net.minecraft.item.Item;

public class EzacianCraftRegistryIMC {
    public EzacianCraftRegistryIMC() {

    }

    public static void initAll() {
        registerClusterIMC();
    }

    public static void registerClusterIMC() {
        int oreCrystalyiumId = Item.getIdFromItem(Item.getItemFromBlock(EzacianCraftResources.crudeCrystalyiumResources.getResourceOreBlock()));
        int clusterCrystalyiumId = Item.getIdFromItem(EzacianCraftResources.crudeCrystalyiumResources.getResourceMetal());

        FMLInterModComms.sendMessage(
                "Thaumcraft",
                "nativeCluster",
                oreCrystalyiumId + ",0," + clusterCrystalyiumId + ",2,0.25"
        );

        //register Native Cluster Events IMC

        int oreShadowVoidId = Item.getIdFromItem(Item.getItemFromBlock(EzacianCraftResources.shadowVoidMetalResources.getResourceOreBlock()));
        int clusterShadowVoidId = Item.getIdFromItem(EzacianCraftResources.shadowVoidMetalResources.getResourceMetal());

        FMLInterModComms.sendMessage(
                "Thaumcraft",
                "nativeCluster",
                oreShadowVoidId + ",0," + clusterShadowVoidId + ",2,0.25"
        );
    }
}
