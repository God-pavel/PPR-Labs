package com.studying.util;

import java.util.List;

public class FirstLabRelations {


    private static final int[][] r1 = {
            {1, 0, 1, 0, 1, 0},
            {1, 1, 1, 0, 1, 0},
            {1, 0, 1, 0, 1, 0},
            {1, 1, 1, 1, 1, 1},
            {1, 0, 1, 0, 1, 0},
            {1, 1, 1, 1, 1, 1},
    };

    private static final int[][] r2 = {
            {1, 1, 1, 0, 1, 1},
            {0, 0, 0, 1, 1, 0},
            {1, 0, 0, 0, 1, 0},
            {1, 1, 0, 0, 0, 1},
            {1, 0, 0, 1, 1, 1},
            {0, 0, 1, 1, 1, 0},
    };

    private static final int[][] r3 = {
            {1, 0, 0, 0, 0, 1},
            {1, 1, 1, 1, 1, 1},
            {1, 0, 1, 0, 0, 1},
            {1, 0, 1, 1, 1, 1},
            {1, 0, 1, 0, 1, 1},
            {0, 0, 0, 0, 0, 1},
    };

    private static final int[][] r4 = {
            {1, 0, 1, 1, 0, 0},
            {0, 1, 0, 0, 1, 0},
            {1, 0, 1, 1, 0, 0},
            {1, 0, 1, 1, 0, 0},
            {0, 1, 0, 0, 1, 0},
            {0, 0, 0, 0, 0, 1},
    };

    private static final int[][] r5 = {
            {1, 0, 0, 1, 0, 0},
            {0, 1, 0, 0, 1, 1},
            {0, 0, 1, 0, 0, 0},
            {1, 0, 0, 1, 0, 0},
            {0, 1, 0, 0, 1, 1},
            {0, 1, 0, 0, 1, 1},
    };

    private static final int[][] r6 = {
            {0, 1, 1, 1, 1, 0},
            {0, 0, 0, 0, 0, 0},
            {0, 1, 0, 0, 0, 0},
            {0, 1, 1, 0, 1, 0},
            {0, 1, 1, 0, 0, 0},
            {1, 1, 1, 1, 1, 0},
    };

    private static final int[][] r7 = {
            {1, 0, 0, 0, 0, 1},
            {1, 0, 0, 0, 0, 0},
            {1, 0, 1, 0, 0, 1},
            {0, 1, 1, 0, 1, 0},
            {0, 1, 1, 1, 0, 1},
            {0, 0, 1, 0, 1, 0},
    };

    private static final int[][] r8 = {
            {0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0},
            {1, 1, 0, 0, 0, 1},
            {1, 1, 0, 0, 0, 1},
            {1, 1, 1, 1, 0, 1},
            {0, 0, 0, 0, 0, 0},
    };

    public static final List<int[][]> FIRST_LAB_RELATIONS = List.of(r1, r2, r3, r4, r5, r6, r7, r8);
}