package com.studying.lab4;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Electre {

    public List<List<Double>> getAgreementIndexMatrix(final List<List<Integer>> alternativesCriteria,
                                                      final List<Double> weights) {
        return IntStream.range(0, alternativesCriteria.size())
                .mapToObj(i -> IntStream.range(0, alternativesCriteria.size())
                        .mapToDouble(j -> i!=j ? calculateAgreementIndex(weights, alternativesCriteria.get(i), alternativesCriteria.get(j)) : 0)
                        .boxed()
                        .collect(Collectors.toList()))
                .collect(Collectors.toList());
    }

    public List<List<Double>> getNotAgreementIndexMatrix(final List<List<Integer>> alternativesCriteria,
                                                         final List<Double> weights) {
        return IntStream.range(0, alternativesCriteria.size())
                .mapToObj(i -> IntStream.range(0, alternativesCriteria.size())
                        .mapToDouble(j -> calculateNotAgreementIndex(alternativesCriteria, weights, i, j))
                        .boxed()
                        .collect(Collectors.toList()))
                .collect(Collectors.toList());
    }

    public Integer[][] getResultMatrix(final List<List<Double>> agreementMatrix,
                                               final List<List<Double>> notAgreementMatrix,
                                               final Double c,
                                               final Double d) {
        List<Integer[]> relation = IntStream.range(0, agreementMatrix.size())
                .mapToObj(i -> IntStream.range(0, agreementMatrix.size())
                        .map(j -> agreementMatrix.get(i).get(j) < c || notAgreementMatrix.get(i).get(j) > d || i == j ? 0 : 1)
                        .boxed()
                        .collect(Collectors.toList()))
                .map(list -> list.toArray(Integer[]::new))
                .collect(Collectors.toList());
        return relation.toArray(Integer[][]::new);
    }

    private Double calculateNotAgreementIndex(final List<List<Integer>> alternativesCriteria,
                                              final List<Double> weights,
                                              final Integer row,
                                              final Integer column) {
        if (row.equals(column)) {
            return 1d;
        }
        final Double dividend = IntStream.range(0, weights.size())
                .mapToDouble(i -> alternativesCriteria.get(row).get(i) < alternativesCriteria.get(column).get(i) ? (alternativesCriteria.get(column).get(i) - alternativesCriteria.get(row).get(i)) * weights.get(i) : 0)
                .max().getAsDouble();
        final Double divider = IntStream.range(0, weights.size())
                .mapToDouble(i -> alternativesCriteria.get(row).get(i) < alternativesCriteria.get(column).get(i) ? (getMaxAtColumn(alternativesCriteria, i) - getMinAtColumn(alternativesCriteria, i)) * weights.get(i) : 0)
                .max().getAsDouble();
        return divider != 0 ? dividend / divider : 0;
    }

    private Integer getMaxAtColumn(final List<List<Integer>> alternativesCriteria,
                                   final Integer column) {
        return alternativesCriteria.stream()
                .mapToInt(list -> list.get(column))
                .max().getAsInt();
    }

    private Integer getMinAtColumn(final List<List<Integer>> alternativesCriteria,
                                   final Integer column) {
        return alternativesCriteria.stream()
                .mapToInt(list -> list.get(column))
                .min().getAsInt();
    }

    private Double calculateAgreementIndex(final List<Double> weights,
                                           final List<Integer> list1,
                                           final List<Integer> list2) {
        return IntStream.range(0, list1.size())
                .mapToDouble(i -> list1.get(i) >= list2.get(i) ? weights.get(i) : 0d)
                .map(i -> i / weights.stream().mapToDouble(Double::doubleValue).sum())
                .sum();
    }
}
