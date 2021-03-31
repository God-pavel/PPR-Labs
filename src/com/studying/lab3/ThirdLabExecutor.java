package com.studying.lab3;

import com.studying.Executor;

import java.util.List;

import static com.studying.lab2.SecondLabExecutor.optimizationsFunctions;
import static com.studying.lab3.RelationAdvantagesFunctions.*;
import static com.studying.util.ThirdLabAlternativesAndCriteria.ALTERNATIVES_AND_CRITERIA;

public class ThirdLabExecutor implements Executor {

    @Override
    public void execute() {
        final List<List<Integer>> pareto = PARETO.apply(ALTERNATIVES_AND_CRITERIA);
        printRelation(pareto);
        printOptimizedResult(pareto);
        final List<List<Integer>> majority = MAJORITY.apply(ALTERNATIVES_AND_CRITERIA);
        printRelation(majority);
        printOptimizedResult(majority);
        final List<List<Integer>> lexicographic = LEXICOGRAPHIC
                .apply(ALTERNATIVES_AND_CRITERIA, List.of(3, 12, 5, 8, 7, 2, 1, 10, 9, 6, 4, 11));
        printRelation(lexicographic);
        printOptimizedResult(lexicographic);
        final List<List<Integer>> berezovskoiy = BEREZOVSKIY.apply(ALTERNATIVES_AND_CRITERIA, List.of(List.of(2, 6, 8, 10), List.of(5, 12), List.of(1, 3, 4, 7, 9, 11)));
        printRelation(berezovskoiy);
        printOptimizedResult(berezovskoiy);
        final List<List<Integer>> poodinovskiy = POODINOVSKIY.apply(ALTERNATIVES_AND_CRITERIA);
        printRelation(poodinovskiy);
        printOptimizedResult(poodinovskiy);
    }

    private void printRelation(List<List<Integer>> relation) {
        relation.forEach(alternative -> System.out.println(alternative.toString()));
    }

    private void printOptimizedResult(List<List<Integer>> relation) {
        optimizationsFunctions.entrySet().stream()
                .map(entry -> entry.getKey() + " = " + entry.getValue().apply(listToArray(relation)))
                .forEach(System.out::println);
    }

    private Integer[][] listToArray(List<List<Integer>> list){
          return list.stream()
                .map(l -> l.toArray(Integer[]::new))
                .toArray(Integer[][]::new);
    }
}
