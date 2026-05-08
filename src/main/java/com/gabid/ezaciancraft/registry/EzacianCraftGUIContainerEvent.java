package com.gabid.ezaciancraft.registry;

import com.gabid.ezaciancraft.client.gui.ScreenExtendedArcaneWorkbench;
import com.gabid.ezaciancraft.client.gui.ScreenShadowAlchemyFurnace;
import com.gabid.ezaciancraft.common.blocks.container.ContainerExtendedArcaneWorkbench;
import com.gabid.ezaciancraft.common.blocks.container.ContainerShadowAlchemyFurnace;
import com.gabid.ezaciancraft.common.blocks.tileentity.TileEntityExtendedArcaneWorkbench;
import com.gabid.ezaciancraft.common.blocks.tileentity.TileEntityShadowAlchemyFurnace;
import cpw.mods.fml.common.network.IGuiHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class EzacianCraftGUIContainerEvent implements IGuiHandler {

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        switch (GuiID.values()[ID]) {
            case SHADOW_ALCHEMY_FURNACE:
                return new ContainerShadowAlchemyFurnace(player.inventory, (TileEntityShadowAlchemyFurnace) world.getTileEntity(x, y, z));
            case EXTENDED_ARCANE_WORKBENCH:
                return new ContainerExtendedArcaneWorkbench(player.inventory, (TileEntityExtendedArcaneWorkbench) world.getTileEntity(x, y, z));
        }
        throw new NullPointerException("Error, not found the Container class or corresponded ID...");
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        switch (GuiID.values()[ID]) {
            case SHADOW_ALCHEMY_FURNACE:
                return new ScreenShadowAlchemyFurnace(player.inventory, (TileEntityShadowAlchemyFurnace) world.getTileEntity(x, y, z));
            case EXTENDED_ARCANE_WORKBENCH:
                return new ScreenExtendedArcaneWorkbench(player.inventory, (TileEntityExtendedArcaneWorkbench) world.getTileEntity(x, y, z));
        }
        throw new NullPointerException("Error, not found the Screen class or corresponded ID...");
    }

    public enum GuiID {
        SHADOW_ALCHEMY_FURNACE(),
        EXTENDED_ARCANE_WORKBENCH()
    }
}
