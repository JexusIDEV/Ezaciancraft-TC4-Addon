package com.gabid.ezaciancraft.lib.render;

public class ColorHelper {

    public static int getFullDecimalToHex(int decimalColor) {
        String hexCode = String.format("%06X", decimalColor & 0xFFFFFF);
        return Integer.parseInt(hexCode);
    }

    public static int getRedDecimalToHexInt(int decimalColor) {
        String hexCode = String.format("%06X", decimalColor & 0xFFFFFF);
        return Integer.parseInt(hexCode.substring(0, 2), 16);
    }

    public static int getGreenDecimalToHexInt(int decimalColor) {
        String hexCode = String.format("%06X", decimalColor & 0xFFFFFF);
        return Integer.parseInt(hexCode.substring(2, 4), 16);
    }

    public static int getBlueDecimalToHexInt(int decimalColor) {
        String hexCode = String.format("%06X", decimalColor & 0xFFFFFF);
        return Integer.parseInt(hexCode.substring(4, 6), 16);
    }

    public static byte getRedDecimalToHexByte(int decimalColor) {
        String hexCode = String.format("%06X", decimalColor & 0xFFFFFF);
        return Byte.parseByte(hexCode.substring(0, 2), 16);
    }

    public static byte getGreenDecimalToHexByte(int decimalColor) {
        String hexCode = String.format("%06X", decimalColor & 0xFFFFFF);
        return Byte.parseByte(hexCode.substring(2, 4), 16);
    }

    public static byte getBlueDecimalToHexByte(int decimalColor) {
        String hexCode = String.format("%06X", decimalColor & 0xFFFFFF);
        return Byte.parseByte(hexCode.substring(4, 6), 16);
    }

    public static float getRedDecimalToHexFloat(int decimalColor) {
        String hexCode = String.format("%06X", decimalColor & 0xFFFFFF);
        return Integer.parseInt(hexCode.substring(0, 2), 16) / 255f;
    }

    public static float getGreenDecimalToHexFloat(int decimalColor) {
        String hexCode = String.format("%06X", decimalColor & 0xFFFFFF);
        return Integer.parseInt(hexCode.substring(2, 4), 16) / 255f;
    }

    public static float getBlueDecimalToHexFloat(int decimalColor) {
        String hexCode = String.format("%06X", decimalColor & 0xFFFFFF);
        return Integer.parseInt(hexCode.substring(4, 6), 16) / 255f;
    }
}
