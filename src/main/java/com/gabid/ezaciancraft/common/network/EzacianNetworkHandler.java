package com.gabid.ezaciancraft.common.network;

import com.gabid.ezaciancraft.common.network.client.PacketUpdateEzacianModeTool;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.entity.player.EntityPlayerMP;

import static com.gabid.ezaciancraft.CoreMod.MODID;

public class EzacianNetworkHandler {
    private static SimpleNetworkWrapper INSTANCE;
    private static int id = 0;

    public static void initNetwork() {
        INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(MODID+"_network");

        registerPacket(PacketUpdateEzacianModeTool.class, PacketUpdateEzacianModeTool.class, Side.SERVER);
    }

    private static void registerPacket(Class messageHandler, Class requestMessageType, Side theSide) {
        INSTANCE.registerMessage(messageHandler, requestMessageType, id, theSide);
        id++;
    }

    public static void sendToServer(IMessage message) {
        INSTANCE.sendToServer(message);
    }

    public static void sendToClient(IMessage message, EntityPlayerMP player) {
        INSTANCE.sendTo(message, player);
    }

    public static void sendToAllClients(IMessage message) {
        INSTANCE.sendToAll(message);
    }

    public static void sendToNearClients(IMessage message, NetworkRegistry.TargetPoint point) {
        INSTANCE.sendToAllAround(message, point);
    }

    public static void sendToDimension(IMessage message, int id) {
        INSTANCE.sendToDimension(message, id);
    }
}
