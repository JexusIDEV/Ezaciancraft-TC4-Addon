package com.gabid.ezaciancraft.common.network.client;

import com.gabid.ezaciancraft.api.common.items.IEzacianTool;
import com.gabid.ezaciancraft.lib.nbt.NBTHelper;
import com.gabid.ezaciancraft.lib.network.EzacianBasePacket;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import static com.gabid.ezaciancraft.api.EzacianCraftNBTConstants.TOOL_MODE;

public class PacketUpdateEzacianModeTool extends EzacianBasePacket<PacketUpdateEzacianModeTool> {

    private int toolMode;

    public PacketUpdateEzacianModeTool() {
    }

    public PacketUpdateEzacianModeTool(int _toolMode) {
        this.toolMode = _toolMode;
    }

    @Override
    public void handleClientSidePacket(PacketUpdateEzacianModeTool message, EntityPlayer player) {

    }

    @Override
    public void handleServerSidePacket(PacketUpdateEzacianModeTool message, EntityPlayer player) {
        ItemStack ezacianToolStack = player.getHeldItem();
        if(ezacianToolStack != null && ezacianToolStack.getItem() instanceof IEzacianTool) {
            if(NBTHelper.containerNBTIsNotNull(ezacianToolStack)) {
                ezacianToolStack.getTagCompound().setInteger(TOOL_MODE, message.toolMode);
                player.worldObj.playSoundAtEntity(player, "thaumcraft:cameraticks", 0.3f, 1f);
            } else {
                NBTHelper.setDefaultContainerNBT(ezacianToolStack, TOOL_MODE, message.toolMode);
                ezacianToolStack.getTagCompound().setInteger(TOOL_MODE, message.toolMode);
                player.worldObj.playSoundAtEntity(player, "thaumcraft:cameraticks", 0.3f, 1f);
            }
        }
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.toolMode = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(this.toolMode);
    }
}
