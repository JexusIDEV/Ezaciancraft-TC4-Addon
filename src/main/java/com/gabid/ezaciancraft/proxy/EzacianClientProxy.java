package com.gabid.ezaciancraft.proxy;

import com.gabid.ezaciancraft.api.common.blocks.tileentity.EzacianCustomJarFillableTE;
import com.gabid.ezaciancraft.client.event.EzacianCraftKeybinds;
import com.gabid.ezaciancraft.client.event.GUIClientEvents;
import com.gabid.ezaciancraft.client.renderer.items.VoidStaffOfPrimalReconstructorRenderer;
import com.gabid.ezaciancraft.client.renderer.items.blocks.AlchemicalMixerBlockRender;
import com.gabid.ezaciancraft.client.renderer.items.blocks.EzacianCustomItemJarFilledRenderer;
import com.gabid.ezaciancraft.client.renderer.items.blocks.WirelessEssentiaInterfaceBlockRender;
import com.gabid.ezaciancraft.client.renderer.tiles.AlchemicalMixerTileEntityRenderer;
import com.gabid.ezaciancraft.client.renderer.tiles.ExtendedArcaneWorkbenchTileEntityRenderer;
import com.gabid.ezaciancraft.client.renderer.tiles.EzacianCustomJarFillableTER;
import com.gabid.ezaciancraft.client.renderer.tiles.WirelessEssentiaInterfaceTileEntityRenderer;
import com.gabid.ezaciancraft.common.blocks.tileentity.TileEntityAlchemicalMixer;
import com.gabid.ezaciancraft.common.blocks.tileentity.TileEntityExtendedArcaneWorkbench;
import com.gabid.ezaciancraft.common.blocks.tileentity.TileEntityWirelessEssentiaInterfaceInput;
import com.gabid.ezaciancraft.registry.EzacianCraftBlocks;
import com.gabid.ezaciancraft.registry.EzacianCraftItems;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.common.MinecraftForge;

public class EzacianClientProxy extends EzacianCommonProxy {

    @Override
    public void preInit(FMLPreInitializationEvent event) {
    }

    @Override
    public void init(FMLInitializationEvent event) {
        EzacianCraftKeybinds.registerKeybinds();
        GUIClientEvents guiClientEvents = new GUIClientEvents();
        MinecraftForge.EVENT_BUS.register(guiClientEvents);
    }

    @Override
    public void postInit(FMLPostInitializationEvent event) {

    }

    @Override
    public EntityPlayer getClientPlayer() {
        return Minecraft.getMinecraft().thePlayer;
    }

    @Override
    public Minecraft getMinecraft() {
        return Minecraft.getMinecraft();
    }

    @Override
    public WorldClient getClientLevel() {
        return Minecraft.getMinecraft().theWorld;
    }

    public void registerDisplayClientStuff() {
        this.registerCustomItemRenders();
        this.registerCustomEntityRenders();
        this.registerBlockRenderer();
        this.registerTileEntitiesRenders();
    }

    private void registerCustomItemRenders() {
        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(EzacianCraftBlocks.alchemicalMixer), new AlchemicalMixerBlockRender());
        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(EzacianCraftBlocks.wirelessEssentiaInterface), new WirelessEssentiaInterfaceBlockRender());

        MinecraftForgeClient.registerItemRenderer(EzacianCraftItems.itemFilledJarCrystalyium, new EzacianCustomItemJarFilledRenderer());
        MinecraftForgeClient.registerItemRenderer(EzacianCraftItems.itemFilledJarShadowVoidMetal, new EzacianCustomItemJarFilledRenderer());

        MinecraftForgeClient.registerItemRenderer(EzacianCraftItems.voidStaffOfPrimalReconstructor, new VoidStaffOfPrimalReconstructorRenderer());
    }

    private void registerCustomEntityRenders() {

    }

    private void registerBlockRenderer() {
    }

    private void registerTileEntitiesRenders() {
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityAlchemicalMixer.class, new AlchemicalMixerTileEntityRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityExtendedArcaneWorkbench.class, new ExtendedArcaneWorkbenchTileEntityRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityWirelessEssentiaInterfaceInput.class, new WirelessEssentiaInterfaceTileEntityRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(EzacianCustomJarFillableTE.class, new EzacianCustomJarFillableTER());
    }
}
