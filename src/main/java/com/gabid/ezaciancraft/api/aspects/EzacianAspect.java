package com.gabid.ezaciancraft.api.aspects;

import com.gabid.ezaciancraft.lib.render.RainbowColor;
import net.minecraft.util.ResourceLocation;
import thaumcraft.api.aspects.Aspect;

import java.util.function.Function;

import static com.gabid.ezaciancraft.CoreMod.MODID;

public class EzacianAspect extends Aspect {

    public EzacianAspect(String tag, int color, Aspect[] components, ResourceLocation image, int blend) {
        super(tag, color, components, image, blend);
    }

    public EzacianAspect(String tag, int color, Aspect[] components) {
        this(tag, color, components, new ResourceLocation(MODID, "textures/aspects/" + tag.toLowerCase() + ".png"), 1);
    }

    public EzacianAspect(String tag, int color, Aspect[] components, int blend) {
        this(tag, color, components, new ResourceLocation(MODID, "textures/aspects/" + tag.toLowerCase() + ".png"), blend);
    }

    public EzacianAspect(String tag, int color, Aspect[] components, String modid) {
        this(tag, color, components, new ResourceLocation(modid, "textures/aspects/" + tag.toLowerCase() + ".png"), 1);
    }

    public EzacianAspect(String tag, int color, Aspect[] components, int blend, String modid) {
        this(tag, color, components, new ResourceLocation(modid, "textures/aspects/" + tag.toLowerCase() + ".png"), blend);
    }

    public EzacianAspect(String tag, int color, String chatcolor, int blend) {
        this(tag, color, (Aspect[]) null, blend);
        this.setChatcolor(chatcolor);
    }


}