package com.gabid.ezaciancraft.api.aspects;

import com.gabid.ezaciancraft.lib.render.RainbowColor;
import thaumcraft.api.aspects.Aspect;

import java.util.function.Function;

import static com.gabid.ezaciancraft.CoreMod.MODID;

public class ChangeableColoredAspect extends EzacianAspect {
    public static final Function<EzacianAspect, Integer> SOFT_RAINBOW = a -> RainbowColor.generateRainbowColor(a.hashCode(), 0.8f, 0.4f,7500L);

    public final Function<EzacianAspect, Integer> getColor;

    public ChangeableColoredAspect(String tag, int baseColor, Aspect[] components, int blend, String modid, Function<EzacianAspect, Integer> _getColor) {
        super(tag, baseColor, components, blend, modid);
        this.getColor = _getColor;
    }

    public ChangeableColoredAspect(String tag, int baseColor, Aspect[] components, int blend, Function<EzacianAspect, Integer> _getColor) {
        super(tag, baseColor, components, blend, MODID);
        this.getColor = _getColor;
    }

    @Override
    public int getColor() {
        return this.getColor.apply(this);
    }
}
