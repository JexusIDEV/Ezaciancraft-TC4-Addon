package com.gabid.ezaciancraft.lib;

import java.util.Arrays;

public class EzacianArrayLibHelper {

    //by gpt but usefull
    public static boolean all2DArrayTrue(boolean[][] matrix) {
        return Arrays.stream(matrix)
                .allMatch(file -> {
                    for (boolean v : file) {
                        if (!v) return false;
                    }
                    return true;
                });
    }

    public static boolean hasValueToCompare(int valueToCheck, int[] values) {
        for (int s : values) {
            if (valueToCheck == s) {
                return true;
            }
        }
        return false;
    }
}
