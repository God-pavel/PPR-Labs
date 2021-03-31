package com.studying.util;

import java.util.List;

public class FirstLabRelations {


    private static final Integer[][] r1 = {
            {1, 0, 1, 0, 1, 0},
            {1, 1, 1, 0, 1, 0},
            {1, 0, 1, 0, 1, 0},
            {1, 1, 1, 1, 1, 1},
            {1, 0, 1, 0, 1, 0},
            {1, 1, 1, 1, 1, 1},
    };

    private static final Integer[][] r2 = {
            {1, 1, 1, 0, 1, 1},
            {0, 0, 0, 1, 1, 0},
            {1, 0, 0, 0, 1, 0},
            {1, 1, 0, 0, 0, 1},
            {1, 0, 0, 1, 1, 1},
            {0, 0, 1, 1, 1, 0},
    };

    private static final Integer[][] r3 = {
            {1, 0, 0, 0, 0, 1},
            {1, 1, 1, 1, 1, 1},
            {1, 0, 1, 0, 0, 1},
            {1, 0, 1, 1, 1, 1},
            {1, 0, 1, 0, 1, 1},
            {0, 0, 0, 0, 0, 1},
    };

    private static final Integer[][] r4 = {
            {1, 0, 1, 1, 0, 0},
            {0, 1, 0, 0, 1, 0},
            {1, 0, 1, 1, 0, 0},
            {1, 0, 1, 1, 0, 0},
            {0, 1, 0, 0, 1, 0},
            {0, 0, 0, 0, 0, 1},
    };

    private static final Integer[][] r5 = {
            {1, 0, 0, 1, 0, 0},
            {0, 1, 0, 0, 1, 1},
            {0, 0, 1, 0, 0, 0},
            {1, 0, 0, 1, 0, 0},
            {0, 1, 0, 0, 1, 1},
            {0, 1, 0, 0, 1, 1},
    };

    private static final Integer[][] r6 = {
            {0, 1, 1, 1, 1, 0},
            {0, 0, 0, 0, 0, 0},
            {0, 1, 0, 0, 0, 0},
            {0, 1, 1, 0, 1, 0},
            {0, 1, 1, 0, 0, 0},
            {1, 1, 1, 1, 1, 0},
    };

    private static final Integer[][] r7 = {
            {1, 0, 0, 0, 0, 1},
            {1, 0, 0, 0, 0, 0},
            {1, 0, 1, 0, 0, 1},
            {0, 1, 1, 0, 1, 0},
            {0, 1, 1, 1, 0, 1},
            {0, 0, 1, 0, 1, 0},
    };

    private static final Integer[][] r8 = {
            {0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0},
            {1, 1, 0, 0, 0, 1},
            {1, 1, 0, 0, 0, 1},
            {1, 1, 1, 1, 0, 1},
            {0, 0, 0, 0, 0, 0},
    };

    public static final List<Integer[][]> FIRST_LAB_RELATIONS = List.of(r1, r2, r3, r4, r5, r6, r7, r8);
}
