package com.gabid.ezaciancraft.config.gui;

import com.gabid.ezaciancraft.config.EzacianCraftConfiguration;
import cpw.mods.fml.client.config.GuiConfig;
import cpw.mods.fml.client.config.IConfigElement;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;

import java.util.ArrayList;
import java.util.List;

import static com.gabid.ezaciancraft.CoreMod.MODID;

public class EzacianCraftConfigGUI extends GuiConfig {
    public EzacianCraftConfigGUI(GuiScreen parentScreen) {
        super(
                parentScreen,
                getConfigElements(),
                MODID,
                false,
                false,
                "EzacianCraft General Configuration");
    }

    private static List<IConfigElement> getConfigElements() {
        List<IConfigElement> list = new ArrayList<IConfigElement>();

        for (String category : EzacianCraftConfiguration.config.getCategoryNames()) {
            list.add(new ConfigElement(
                    EzacianCraftConfiguration.config.getCategory(category)
            ));
        }

        return list;
    }
}
