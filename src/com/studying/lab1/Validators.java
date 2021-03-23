package com.studying.lab1;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class Validators {

    public static final Predicate<int[][]> isReflexive = (relation) -> {
        for (int i = 0; i < relation.length; i++) {
            if (relation[i][i] == 0) {
                return false;
            }
        }
        return true;
    };

    public static final Predicate<int[][]> isIrreflexive = (relation) -> {
        for (int i = 0; i < relation.length; i++) {
            if (relation[i][i] == 1) {
                return false;
            }
        }
        return true;
    };

    public static final Predicate<int[][]> isSymmetric = (relation) -> {
        for (int i = 0; i < relation.length; i++) {
            for (int g = i; g < relation.length; g++) {
                if (relation[i][g] != relation[g][i]) {
                    return false;
                }
            }
        }
        return true;
    };

    public static final Predicate<int[][]> isAsymmetric = (relation) -> {
        for (int i = 0; i < relation.length; i++) {
            for (int g = i; g < relation.length; g++) {
                if (relation[i][g] == 1 && relation[g][i] == 1) {
                    return false;
                }
            }
        }
        return true;
    };

    public static final Predicate<int[][]> isAntisymmetric = (relation) -> {
        for (int i = 0; i < relation.length; i++) {
            for (int g = i + 1; g < relation[i].length; g++) {
                if (relation[i][g] == 1 && relation[g][i] == 1) {
                    return false;
                }
            }
        }
        return true;
    };

    public static final Predicate<int[][]> isTransitive = (relation) -> {
        for (int i = 0; i < relation.length; i++) {
            for (int g = 0; g < relation.length; g++) {
                if (relation[i][g] == 1) {
                    for (int j = 0; j < relation.length; j++) {
                        if (relation[g][j] == 1 && relation[i][j] != 1) {
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    };

    public static final Predicate<int[][]> isNegativelyTransitive = (relation) -> {
        for (int i = 0; i < relation.length; i++) {
            for (int g = 0; g < relation.length; g++) {
                if (relation[i][g] == 0) {
                    for (int j = 0; j < relation.length; j++) {
                        if (relation[g][j] == 0 && relation[i][j] != 0) {
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    };

    public static final Predicate<int[][]> isLinked = (relation) -> {
        for (int i = 0; i < relation.length; i++) {
            for (int g = i; g < relation.length; g++) {
                if (!(relation[i][g] == 1 || relation[g][i] == 1)) {
                    return false;
                }
            }
        }
        return true;
    };

    public static final Predicate<int[][]> isWeaklyLinked = (relation) -> {
        for (int i = 0; i < relation.length; i++) {
            for (int g = i + 1; g < relation.length; g++) {
                if (!(relation[i][g] == 1 || relation[g][i] == 1)) {
                    return false;
                }
            }
        }
        return true;
    };

    public static boolean isCycle(int[][] relation, int x, int y, List<Integer> row) {
        for (int i = 0; i < relation.length; i++) {
            if (relation[x][i] == 1 && !row.contains(i)) {
                row.add(i);
                if (relation[i][y] == 1 || isCycle(relation, i, y, row)) {
                    return true;
                }
            }
        }
        return false;
    }
    public static final Predicate<int[][]> isAcyclic = (relation) -> {
        if (!isIrreflexive.test(relation) || isSymmetric.test(relation)) {
            return false;
        }
        for (int i = 0; i < relation.length; i++) {
            if (isCycle(relation, i, i, new ArrayList<>())) {
                return false;
            }
        }
        return true;
    };
}
