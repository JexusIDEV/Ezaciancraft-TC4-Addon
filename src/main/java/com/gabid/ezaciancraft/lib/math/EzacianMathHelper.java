package com.gabid.ezaciancraft.lib.math;

public class EzacianMathHelper {
    public static boolean isPowOfTwo(int value) {
        return value > 0 && (value & (value - 1)) == 0;
    }
}
