package com.studying.lab3;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class RelationAdvantagesFunctions {
    public static final Function<List<List<Integer>>, List<List<Integer>>> PARETO = (alternativesCriteria) -> alternativesCriteria.stream()
            .map(alternative -> buildComparedList(alternative, alternativesCriteria, (list) -> list.stream().allMatch(i -> i >= 0)))
            .collect(Collectors.toList());

    public static final Function<List<List<Integer>>, List<List<Integer>>> MAJORITY = (alternativesCriteria) -> alternativesCriteria.stream()
            .map(alternative -> buildComparedList(alternative, alternativesCriteria, (list) -> list.stream().mapToInt(Integer::intValue).sum() > 0))
            .collect(Collectors.toList());
    public static final BiFunction<List<List<Integer>>, List<Integer>, List<List<Integer>>> LEXICOGRAPHIC = (alternativesCriteria, order) -> alternativesCriteria.stream()
            .map(alternative -> buildComparedList(alternative, alternativesCriteria, (list) -> {
                List<Integer> orderedList = order.stream()
                        .mapToInt(i -> list.get(i - 1))
                        .boxed()
                        .collect(Collectors.toList());
                for (Integer el : orderedList) {
                    if (el == 1) return true;
                    if (el == -1) return false;
                }
                return false;
            }))
            .collect(Collectors.toList());

    public static final BiFunction<List<List<Integer>>, List<List<Integer>>, List<List<Integer>>> BEREZOVSKIY = (alternativesCriteria, classes) -> {
        List<ParetoSystem> paretoSystems = classes.stream()
                .map(criteriaClass -> buildPINO(criteriaClass, alternativesCriteria))
                .map(pino -> List.of(buildFromPino(pino, "I"), buildFromPino(pino, "P"), buildFromPino(pino, "N")))
                .map(lists -> new ParetoSystem(lists.get(1), lists.get(0), lists.get(2)))
                .collect(Collectors.toList());
        BerezovskiySystem berezovskiySystem = new BerezovskiySystem();
        berezovskiySystem = berezovskiySystem.buildBerezovskiySystem(paretoSystems);
        List<List<Boolean>> result = berezovskiySystem.getMatrixP();
        return result.stream()
                .map(RelationAdvantagesFunctions::convertList)
                .collect(Collectors.toList());
    };

    public static final Function<List<List<Integer>>, List<List<Integer>>> POODINOVSKIY = (alternativesCriteria) -> {
        List<List<Integer>> lists = alternativesCriteria.stream().map(list -> {
            List<Integer> sortedList = new ArrayList<>(list);
            sortedList.sort((a, b) -> b - a);
            return sortedList;
        }).collect(Collectors.toList());
        return PARETO.apply(lists);
    };

    private static List<Integer> buildComparedList(List<Integer> comparedAlternative,
                                                   List<List<Integer>> alternativesCriteria,
                                                   Predicate<List<Integer>> predicate) {
        return alternativesCriteria.stream()
                .map(alternative -> compareTwoAlternatives(comparedAlternative, alternative, predicate))
                .map(bool -> bool ? 1 : 0)
                .collect(Collectors.toList());
    }

    private static boolean compareTwoAlternatives(List<Integer> alt1,
                                                  List<Integer> alt2,
                                                  Predicate<List<Integer>> predicate) {
        List<Integer> comparedList = IntStream.range(0, alt1.size())
                .map(i -> alt1.get(i) - alt2.get(i))
                .boxed()
                .map(i -> {
                    if (i > 0) return 1;
                    else if (i == 0) return 0;
                    else return -1;
                })
                .collect(Collectors.toList());
        return predicate.test(comparedList);
    }

    private static List<List<String>> buildPINO(List<Integer> criteriaClass,
                                                List<List<Integer>> alternativesCriteria) {
        return alternativesCriteria.stream()
                .map(alternative -> buildComparedPINO(alternative, alternativesCriteria, criteriaClass))
                .collect(Collectors.toList());
    }

    private static List<String> buildComparedPINO(List<Integer> comparedAlternative,
                                                  List<List<Integer>> alternativesCriteria,
                                                  List<Integer> criteriaClass) {
        return alternativesCriteria.stream()
                .map(alternative -> compareTwoPINOAlternatives(comparedAlternative, alternative, criteriaClass))
                .collect(Collectors.toList());
    }

    private static String compareTwoPINOAlternatives(List<Integer> alt1,
                                                     List<Integer> alt2,
                                                     List<Integer> criteriaClass) {
        if (criteriaClass.stream().allMatch(criteria ->
                alt1.get(criteria - 1).equals(alt2.get(criteria - 1)))) {
            return "I";
        }
        if (criteriaClass.stream().allMatch(criteria ->
                alt1.get(criteria - 1) >= (alt2.get(criteria - 1)))) {
            return "P";
        }
        if (criteriaClass.stream().allMatch(criteria ->
                alt1.get(criteria - 1) <= (alt2.get(criteria - 1)))) {
            return "0";
        }
        return "N";
    }

    private static List<List<Boolean>> buildFromPino(List<List<String>> pino, String val) {
        return pino.stream()
                .map(list->convertPINOList(list, val))
                .collect(Collectors.toList());
    }

    private static List<Boolean> convertPINOList(List<String> list, String val) {
        return list.stream()
                .map(el -> el.equals(val))
                .collect(Collectors.toList());
    }

    private static List<Integer> convertList(List<Boolean> list) {
        return list.stream()
                .map(el -> el ? 1 : 0)
                .collect(Collectors.toList());
    }
}
