package com.gabid.ezaciancraft.proxy;

import com.gabid.ezaciancraft.common.blocks.tileentity.AlchemicalMixerTileEntity;
import com.gabid.ezaciancraft.common.blocks.tileentity.EtherealAcceleratorTE;
import com.gabid.ezaciancraft.common.network.EzacianNetworkHandler;
import com.gabid.ezaciancraft.registry.EzacianCraftBlocks;
import com.gabid.ezaciancraft.registry.EzacianCraftItems;
import com.raz.howlingmoon.TileEntityBlockCharm;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.entity.player.EntityPlayer;

import static com.gabid.ezaciancraft.api.EzacianCraftGeneralLang.*;

public abstract class EzacianCommonProxy {

    public abstract void preInit(FMLPreInitializationEvent event);

    public abstract void init(FMLInitializationEvent event);

    public abstract void postInit(FMLPostInitializationEvent event);

    public abstract EntityPlayer getClientPlayer();

    public abstract WorldClient getClientLevel();


}
