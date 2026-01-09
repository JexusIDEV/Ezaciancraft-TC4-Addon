package com.gabid.ezaciancraft.proxy;

import com.gabid.ezaciancraft.client.event.EzacianCraftKeybinds;
import com.gabid.ezaciancraft.client.renderer.blocks.AlchemicalMixerBlockRender;
import com.gabid.ezaciancraft.client.renderer.tiles.AlchemicalMixerTileEntityRenderer;
import com.gabid.ezaciancraft.common.blocks.tileentity.AlchemicalMixerTileEntity;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.entity.player.EntityPlayer;

import static com.gabid.ezaciancraft.api.EzacianCraftGeneralLang.UNLOCALE_ALCHEMICAL_MIXER;

public class EzacianClientProxy extends EzacianCommonProxy {

    @Override
    public void preInit(FMLPreInitializationEvent event) {
    }

    @Override
    public void init(FMLInitializationEvent event) {
        GameRegistry.registerTileEntity(AlchemicalMixerTileEntity.class, "tile."+UNLOCALE_ALCHEMICAL_MIXER);
        ClientRegistry.bindTileEntitySpecialRenderer(AlchemicalMixerTileEntity.class, new AlchemicalMixerTileEntityRenderer());
        this.registerBlockRenderer(new AlchemicalMixerBlockRender());
        EzacianCraftKeybinds.registerKeybinds();
    }

    @Override
    public void postInit(FMLPostInitializationEvent event) {
    }

    public void registerBlockRenderer(ISimpleBlockRenderingHandler renderer) {
        RenderingRegistry.registerBlockHandler(renderer);
    }

    @Override
    public EntityPlayer getClientPlayer(){
        return Minecraft.getMinecraft().thePlayer;
    }

    @Override
    public WorldClient getClientLevel() {
        return Minecraft.getMinecraft().theWorld;
    }
}
