package com.studying.lab4;

import com.studying.Executor;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.studying.lab2.optimizations.OptimizationFunctions.NMOptimizationFunction;
import static com.studying.util.FifthLabAlternativesAndCriteria.ALTERNATIVES_AND_CRITERIA;
import static com.studying.util.FifthLabAlternativesAndCriteria.WEIGHTS;

public class FourthLabExecutor implements Executor {

    @Override
    public void execute() {
        final Electre electre = new Electre();
        final double c = 0.671;
        final double d = 0.300;
        List<List<Double>> agreementMatrix = electre.getAgreementIndexMatrix(ALTERNATIVES_AND_CRITERIA, WEIGHTS);
        printRelation(agreementMatrix, "C index matrix: ");
        List<List<Double>> notAgreementMatrix = electre.getNotAgreementIndexMatrix(ALTERNATIVES_AND_CRITERIA, WEIGHTS);
        printRelation(notAgreementMatrix, "D index matrix: ");
        System.out.println("C index, D index: ");
        System.out.println(String.format("%.3f", c) + ",   " + String.format("%.3f", d));
        System.out.println();
        Integer[][] finalMatrix = electre.getResultMatrix(agreementMatrix, notAgreementMatrix, c, d);
        printRelationInt(finalMatrix, "Final matrix: ");

        System.out.println("The core of binary relation:");
        System.out.println(NMOptimizationFunction.apply(finalMatrix));
    }

    private void printRelation(List<List<Double>> relation, String message) {
        System.out.println(message);
        relation.stream()
                .map(list -> list.stream().map(a -> Math.round(a * 1000) / 1000D).map(d -> String.format("%.3f", d)).collect(Collectors.toList()))
                .forEach(alternative -> System.out.println(alternative.toString()));
        System.out.println();
    }

    private void printRelationInt(Integer[][] relation, String message) {
        System.out.println(message);
        for (Integer[] row : relation) {
            System.out.println(Arrays.toString(row));
        }
        System.out.println();
    }
}
