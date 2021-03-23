package com.studying.util;

import java.util.Map;
import java.util.Set;
import java.util.function.BiPredicate;

public class KOptimizationUtils {
    public static final Map<BiPredicate<Integer, Integer>, String> RELATION_TYPE_PREDICATES =
            Map.ofEntries(
                    Map.entry((first, second) -> first == 1 && second == 0, "P"),
                    Map.entry((first, second) -> first == 1 && second == 1, "I"),
                    Map.entry((first, second) -> first == 0 && second == 0, "N"),
                    Map.entry((first, second) -> first == 0 && second == 1, "0")
            );

    public static final Map<Integer, Set<String>> K_TYPES =
            Map.ofEntries(
                    Map.entry(1, Set.of("P", "I", "N")),
                    Map.entry(2, Set.of("P", "N")),
                    Map.entry(3, Set.of("P", "I")),
                    Map.entry(4, Set.of("P"))
            );
}
