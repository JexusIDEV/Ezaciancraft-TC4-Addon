package com.gabid.ezaciancraft.lib.network;

import com.gabid.ezaciancraft.CoreMod;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;

public abstract class EzacianBasePacket<REQ extends IMessage> implements IMessage, IMessageHandler<REQ, REQ> {

    @Override
    public REQ onMessage(REQ message, MessageContext ctx) {
        if(ctx.side == Side.SERVER) {
            handleServerSidePacket(message, ctx.getServerHandler().playerEntity);
        } else {
            handleClientSidePacket(message, CoreMod.proxy.getClientPlayer());
        }
        return null;
    }

    public abstract void handleClientSidePacket(REQ message, EntityPlayer player);

    public abstract void handleServerSidePacket(REQ message, EntityPlayer player);

}
