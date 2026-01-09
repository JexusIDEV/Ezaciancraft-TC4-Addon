package com.gabid.ezaciancraft;

import com.gabid.ezaciancraft.common.network.EzacianNetworkHandler;
import com.gabid.ezaciancraft.proxy.EzacianCommonProxy;
import com.gabid.ezaciancraft.registry.EzacianCraftBlocks;
import com.gabid.ezaciancraft.registry.EzacianCraftItems;
import com.gabid.ezaciancraft.registry.EzacianCraftResources;
import com.gabid.ezaciancraft.registry.EzacianCraftTileEntities;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.registry.GameData;
import net.minecraft.client.main.Main;
import tv.twitch.Core;

@Mod(modid = CoreMod.MODID, version = CoreMod.VERSION, dependencies = CoreMod.DEPENDENCIES)
public class CoreMod
{
    public static final String MODID = "ezaciancraft";
    public static final String VERSION = "1.0";
    public static final String DEPENDENCIES = "required-after:Thaumcraft@[4.2.3.5,);after:Baubles@[1.0.1.10,);";

    @Mod.Instance(MODID)
    public static CoreMod instance;
    @SidedProxy(modId = MODID, clientSide = "com.gabid.ezaciancraft.proxy.EzacianClientProxy", serverSide = "com.gabid.ezaciancraft.proxy.EzacianServerProxy")
    public static EzacianCommonProxy proxy;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        EzacianCraftItems.setupItemsRegistry();
        EzacianCraftBlocks.setupBlocksRegistry();
        EzacianCraftTileEntities.setupTileEntities();
        EzacianCraftResources.setupResources();
        proxy.preInit(event);
    }

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        EzacianNetworkHandler.initNetwork();
        proxy.init(event);
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
        proxy.postInit(event);
    }
}
