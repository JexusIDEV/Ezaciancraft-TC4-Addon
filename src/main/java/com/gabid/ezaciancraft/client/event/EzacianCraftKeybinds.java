package com.gabid.ezaciancraft.client.event;

import cpw.mods.fml.common.FMLCommonHandler;

public class EzacianCraftKeybinds {
    public static void registerKeybinds() {
        FMLCommonHandler.instance().bus().register(new KeyHandlerInput());
    }
}
