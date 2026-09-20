package com.gabid.ezaciancraft.lib;

import java.util.Arrays;

///api simplistic lib what helps about some multidimensional arrays
public class EzacianArrayLibHelper {

    public static boolean all2DArrayTrue(boolean[][] matrix) {
        return Arrays.stream(matrix)
                .allMatch(file -> {
                    for (boolean v : file) {
                        if (!v) return false;
                    }
                    return true;
                });
    }

    public static boolean all3DArrayTrue(boolean[][][] tensor) {
        return Arrays.stream(tensor)
                .allMatch(plane ->
                        Arrays.stream(plane)
                                .allMatch(row -> {
                                    for (boolean v : row) {
                                        if (!v) return false;
                                    }
                                    return true;
                                })
                );
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
