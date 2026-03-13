package com.gabid.ezaciancraft.lib;

public class EzacianMathHelper {
    public static boolean isPowOfTwo(int value) {
        return value > 0 && (value & (value - 1)) == 0;
    }
}
