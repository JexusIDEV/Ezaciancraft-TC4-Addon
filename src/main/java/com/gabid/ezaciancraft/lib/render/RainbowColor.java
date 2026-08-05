package com.gabid.ezaciancraft.lib.render;

import net.royawesome.jlibnoise.MathHelper;

import static thaumcraft.common.lib.utils.Utils.clamp_float;

//from our furry protegen dragon
//from hammerlib - Zeith
//https://github.com/dragon-forge/HammerLib/blob/1.12.2/src/main/java/com/zeitheron/hammercore/utils/color/Rainbow.java
public class RainbowColor {
    public static int generateRainbowColor(long offset, float saturarionStrength, float mixingSaturationUmbral, long fullSycleInMilis) {
        float r = 1;
        float g = 1;
        float b = 1;

        long time = Math.abs(System.currentTimeMillis() + offset) % fullSycleInMilis;
        long msPerSector = fullSycleInMilis / 3L;
        int currentSector = MathHelper.floor((double) time / (double) msPerSector) + 1;
        float sectorProgression = (time % msPerSector) / (float) msPerSector;
        float sectorDepth = sectorProgression * 2F;
        if(sectorDepth > 1)
            sectorDepth = 2 - sectorDepth;

        if(currentSector == 1)
        {
            b = 1 - sectorProgression;
            g = sectorProgression;
        } else if(currentSector == 2)
        {
            r = 1 - sectorProgression;
            b = sectorProgression;
        } else if(currentSector == 3)
        {
            g = 1 - sectorProgression;
            r = sectorProgression;
        }

        // --- PASTEL EFFECT ---
        float gray = (r + g + b) / 3f;

        r = gray + (r - gray) * saturarionStrength;
        g = gray + (g - gray) * saturarionStrength;
        b = gray + (b - gray) * saturarionStrength;

        mixingSaturationUmbral = clamp_float(mixingSaturationUmbral,0, 1);

        r = r * (1 - mixingSaturationUmbral) + mixingSaturationUmbral;
        g = g * (1 - mixingSaturationUmbral) + mixingSaturationUmbral;
        b = b * (1 - mixingSaturationUmbral) + mixingSaturationUmbral;

        return (((int) (r * 255F)) << 16) | (((int) (g * 255F)) << 8) | ((int) (b * 255F));
    }
}
