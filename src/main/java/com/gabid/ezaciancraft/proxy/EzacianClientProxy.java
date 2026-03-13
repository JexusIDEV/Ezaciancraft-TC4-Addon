package com.gabid.ezaciancraft.proxy;

import com.gabid.ezaciancraft.api.common.blocks.tileentity.EzacianCustomJarFillableTE;
import com.gabid.ezaciancraft.client.event.EzacianCraftKeybinds;
import com.gabid.ezaciancraft.client.renderer.items.blocks.AlchemicalMixerBlockRender;
import com.gabid.ezaciancraft.client.renderer.items.blocks.EzacianCustomItemJarFilledRenderer;
import com.gabid.ezaciancraft.client.renderer.tiles.AlchemicalMixerTileEntityRenderer;
import com.gabid.ezaciancraft.client.renderer.tiles.EzacianCustomJarFillableTER;
import com.gabid.ezaciancraft.common.blocks.tileentity.AlchemicalMixerTileEntity;
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

public class EzacianClientProxy extends EzacianCommonProxy {

    @Override
    public void preInit(FMLPreInitializationEvent event) {
    }

    @Override
    public void init(FMLInitializationEvent event) {
        EzacianCraftKeybinds.registerKeybinds();
    }

    @Override
    public void postInit(FMLPostInitializationEvent event) {

    }

    @Override
    public EntityPlayer getClientPlayer(){
        return Minecraft.getMinecraft().thePlayer;
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
        MinecraftForgeClient.registerItemRenderer(EzacianCraftItems.itemFilledJarCrystalyium, new EzacianCustomItemJarFilledRenderer());
        MinecraftForgeClient.registerItemRenderer(EzacianCraftItems.itemFilledJarShadowVoidMetal, new EzacianCustomItemJarFilledRenderer());

    }

    private void registerCustomEntityRenders() {

    }

    private void registerBlockRenderer() {
    }

    private void registerTileEntitiesRenders() {
        ClientRegistry.bindTileEntitySpecialRenderer(AlchemicalMixerTileEntity.class, new AlchemicalMixerTileEntityRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(EzacianCustomJarFillableTE.class, new EzacianCustomJarFillableTER());
    }
}
