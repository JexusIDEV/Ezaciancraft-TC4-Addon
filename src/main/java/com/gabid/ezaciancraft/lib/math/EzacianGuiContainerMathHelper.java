package com.gabid.ezaciancraft.lib.math;

import net.minecraft.util.MathHelper;

public class EzacianGuiContainerMathHelper {

    public static int getProgressScalar(int minValue, int maxValue, int scalarPixel) {
        return maxValue != 0 && minValue != 0 ? minValue * scalarPixel / maxValue : 0;
    }

    public static int getScaledValue(int minValue, int maxValue, int scalarPixel) {
        return maxValue != 0 && minValue != 0 ? minValue * scalarPixel / maxValue : 0;
    }

    public static int getPercentageContainerDataValue(int minValue, int maxValue) {
        return maxValue != 0 && minValue != 0 ? MathHelper.clamp_int(minValue / maxValue, 0, 1) : 0;
    }

    public static int getPercentageValue(int min, int max) {
        return max != 0 && min != 0 ? MathHelper.clamp_int((min / max), 0, 1) : 0;
    }
}
