package com.gabid.ezaciancraft.event;

import java.util.Collections;
import java.util.Set;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;

import com.gabid.ezaciancraft.config.gui.EzacianCraftConfigGUI;

import cpw.mods.fml.client.IModGuiFactory;

public class EzacianCraftGameModGuiFactory implements IModGuiFactory {

    @Override
    public void initialize(Minecraft minecraftInstance) {

    }

    @Override
    public Class<? extends GuiScreen> mainConfigGuiClass() {
        return EzacianCraftConfigGUI.class;
    }

    @Override
    public Set<RuntimeOptionCategoryElement> runtimeGuiCategories() {
        return Collections.emptySet();
    }

    @Override
    public RuntimeOptionGuiHandler getHandlerFor(RuntimeOptionCategoryElement element) {
        return null;
    }
}
