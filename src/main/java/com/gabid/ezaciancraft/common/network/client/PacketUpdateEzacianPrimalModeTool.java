package com.gabid.ezaciancraft.common.network.client;

import com.gabid.ezaciancraft.api.common.items.IEzacianPrimalTool;
import com.gabid.ezaciancraft.lib.nbt.NBTHelper;
import com.gabid.ezaciancraft.lib.network.EzacianBasePacket;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import static com.gabid.ezaciancraft.api.EzacianCraftNBTConstants.*;

public class PacketUpdateEzacianPrimalModeTool extends EzacianBasePacket<PacketUpdateEzacianPrimalModeTool> {

    private int toolBehaviour;
    private int toolSubMode;
    private int toolAOE;

    public PacketUpdateEzacianPrimalModeTool() {
    }

    public PacketUpdateEzacianPrimalModeTool(int _toolBehaviour, int _toolSubMode, int _toolAOE) {
        this.toolBehaviour = _toolBehaviour;
        this.toolSubMode = _toolSubMode;
        this.toolAOE = _toolAOE;
    }

    @Override
    public void handleClientSidePacket(PacketUpdateEzacianPrimalModeTool message, EntityPlayer player) {

    }

    @Override
    public void handleServerSidePacket(PacketUpdateEzacianPrimalModeTool message, EntityPlayer player) {
        ItemStack ezacianToolStack = player.getHeldItem();
        if (ezacianToolStack != null && ezacianToolStack.getItem() instanceof IEzacianPrimalTool) {
            if (!NBTHelper.containerNBTIsNotNull(ezacianToolStack)) {
                NBTHelper.setDefaultContainerNBT(ezacianToolStack, PRIMAL_TOOL_BEHAVIOUR, message.toolBehaviour);
                NBTHelper.setDefaultContainerNBT(ezacianToolStack, PRIMAL_TOOL_SUB_MODE, message.toolSubMode);
                NBTHelper.setDefaultContainerNBT(ezacianToolStack, PRIMAL_TOOL_AOE, message.toolAOE);
            }
            ezacianToolStack.getTagCompound().setInteger(PRIMAL_TOOL_BEHAVIOUR, message.toolBehaviour);
            ezacianToolStack.getTagCompound().setInteger(PRIMAL_TOOL_SUB_MODE, message.toolSubMode);
            ezacianToolStack.getTagCompound().setInteger(PRIMAL_TOOL_AOE, message.toolAOE);
            player.worldObj.playSoundAtEntity(player, "thaumcraft:cameraticks", 0.3f, 1f);
        }
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.toolBehaviour = buf.readInt();
        this.toolSubMode = buf.readInt();
        this.toolAOE = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(this.toolBehaviour);
        buf.writeInt(this.toolSubMode);
        buf.writeInt(this.toolAOE);
    }
}
