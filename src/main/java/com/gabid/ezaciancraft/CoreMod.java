package com.gabid.ezaciancraft;

import com.gabid.ezaciancraft.common.event.EzacianCraftPlayerEvents;
import com.gabid.ezaciancraft.common.event.EzacianCraftWandMultiblockEvent;
import com.gabid.ezaciancraft.common.network.EzacianNetworkHandler;
import com.gabid.ezaciancraft.common.world.EzacianCraftWorldGen;
import com.gabid.ezaciancraft.config.EzacianCraftConfiguration;
import com.gabid.ezaciancraft.proxy.EzacianCommonProxy;
import com.gabid.ezaciancraft.registry.*;
import cpw.mods.fml.client.event.ConfigChangedEvent;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.ModContainer;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLInterModComms;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameData;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.ItemStack;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import thaumcraft.common.config.Config;

@Mod(modid = CoreMod.MODID, version = CoreMod.VERSION, dependencies = CoreMod.DEPENDENCIES, guiFactory = "com.gabid.ezaciancraft.event.EzacianCraftGameModGuiFactory")
public class CoreMod {
    public static final String MODID = "ezaciancraft";
    public static final String VERSION = "alpha-1.2.0";
    public static final String DEPENDENCIES = "required-after:Thaumcraft@[4.2.3.5,);after:Baubles@[1.0.1.10,);";

    public static final Logger LOG = LogManager.getLogger("EZACIANCRAFT");
    @Mod.Instance(MODID)
    public static CoreMod instance;
    @SidedProxy(modId = MODID, clientSide = "com.gabid.ezaciancraft.proxy.EzacianClientProxy", serverSide = "com.gabid.ezaciancraft.proxy.EzacianServerProxy")
    public static EzacianCommonProxy proxy;
    public static EzacianCraftWandMultiblockEvent thaumcraftMultiblockEvent;
    public static EzacianCraftPlayerEvents playerEvents = new EzacianCraftPlayerEvents();

    //https://github.com/mekanism/Mekanism/blob/1.7.10/src/main/java/mekanism/common/util/MekanismUtils.java#L1496
    public static String getModIdFromItemStack(ItemStack stack) {
        try {
            ModContainer mod = GameData.findModOwner(GameData.getItemRegistry().getNameForObject(stack.getItem()));
            return mod == null ? "Minecraft" : mod.getName();
        } catch (Exception e) {
            return "null";
        }
    }

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        try {
            EzacianCraftConfiguration.initConfig(event.getSuggestedConfigurationFile());
        } catch (Exception var8) {
            LOG.error("Ezaciancraft has a problem loading it's configuration");
        } finally {
            if (Config.config != null) {
                EzacianCraftConfiguration.save();
            }
        }

        EzacianCraftBlocks.setupBlocksRegistry();
        EzacianCraftItems.setupItemsRegistry();
        EzacianCraftResources.setupResources();
        EzacianCraftTileEntities.setupTileEntities();
        EzacianCraftAspects.initAspects();
        GameRegistry.registerWorldGenerator(new EzacianCraftWorldGen(), 0);

        thaumcraftMultiblockEvent = new EzacianCraftWandMultiblockEvent();
        playerEvents = new EzacianCraftPlayerEvents();

        NetworkRegistry.INSTANCE.registerGuiHandler(instance, new EzacianCraftGUIContainerEvent());

        EzacianCraftConfiguration.save();
        FMLCommonHandler.instance().bus().register(instance);

        proxy.preInit(event);
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.registerDisplayClientStuff();
        EzacianNetworkHandler.initNetwork();
        EzacianCraftRecipes.setupRecipes();
        EzacianCraftRegistryIMC.initAll();

        proxy.init(event);
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        EzacianCraftResearches.registerAllResearches();
        EzacianCraftAspectTagRegistry.initObjectAspects();

        proxy.postInit(event);
    }

    @EventHandler
    public void processIMC(FMLInterModComms iMCEvents) {

    }

    @SubscribeEvent
    public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
        if (event.modID.equals(MODID)) {
            EzacianCraftConfiguration.syncConfigurations();
        }
    }
}
