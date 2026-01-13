package com.gabid.ezaciancraft.proxy;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.entity.player.EntityPlayer;

public class EzacianServerProxy extends EzacianCommonProxy {
    @Override
    public void preInit(FMLPreInitializationEvent event) {
    }

    @Override
    public void init(FMLInitializationEvent event) {
    }

    @Override
    public void postInit(FMLPostInitializationEvent event) {
    }

    @Override
    public EntityPlayer getClientPlayer() {
        return null;
    }

    @Override
    public WorldClient getClientLevel() {
        return null;
    }

    @Override
    public void registerDisplayClientStuff() {

    }
}
