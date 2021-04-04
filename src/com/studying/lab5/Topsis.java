package com.studying.lab5;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Topsis {
    public List<List<Double>> normalizeScores(final List<List<Integer>> matrix) {
        List<Double> averageGeometrics = IntStream.range(0, matrix.get(0).size())
                .mapToDouble(i -> getAverageGeometric(matrix.stream()
                        .mapToInt(integers -> integers.get(i))
                        .boxed()
                        .collect(Collectors.toList())))
                .boxed()
                .collect(Collectors.toList());

        return matrix.stream()
                .map(integers -> IntStream.range(0, integers.size())
                        .mapToDouble(j -> integers.get(j) / averageGeometrics.get(j))
                        .boxed()
                        .collect(Collectors.toList()))
                .collect(Collectors.toList());
    }

    public List<List<Double>> normalizeScoresWithCriteriaPolarity(final List<List<Integer>> matrix,
                                                                  final  List<Boolean> polarities) {
        List<Integer> columnMaxes = IntStream.range(0, matrix.get(0).size())
                .map(i -> getMax(matrix.stream()
                        .mapToInt(integers -> integers.get(i))
                        .boxed()
                        .collect(Collectors.toList())))
                .boxed()
                .collect(Collectors.toList());

        List<Integer> columnMins = IntStream.range(0, matrix.get(0).size())
                .map(i -> getMin(matrix.stream()
                        .mapToInt(integers -> integers.get(i))
                        .boxed()
                        .collect(Collectors.toList())))
                .boxed()
                .collect(Collectors.toList());

        return matrix.stream()
                .map(integers -> IntStream.range(0, integers.size())
                        .mapToDouble(j -> {
                            if(polarities.get(j)){
                                return (double) (integers.get(j) - columnMins.get(j)) / (double) (columnMaxes.get(j) - columnMins.get(j));
                            } else {
                                return (double) (columnMins.get(j) - integers.get(j)) / (double) (columnMins.get(j) - columnMaxes.get(j));
                            }
                        })
                        .boxed()
                        .collect(Collectors.toList()))
                .collect(Collectors.toList());
    }

    public List<List<Double>> weightScores(final List<List<Double>> matrix,
                                           final List<Double> weights) {
        return matrix.stream()
                .map(doubles -> IntStream.range(0, doubles.size())
                        .mapToDouble(j -> doubles.get(j) * weights.get(j))
                        .boxed()
                        .collect(Collectors.toList()))
                .collect(Collectors.toList());
    }

    public List<Double> getPisNis(final List<List<Double>> matrix,
                                  final List<Boolean> weightsMax) {
        return IntStream.range(0, matrix.get(0).size())
                .mapToDouble(i -> getBest(matrix.stream()
                        .mapToDouble(doubles -> doubles.get(i))
                        .boxed()
                        .collect(Collectors.toList()), weightsMax.get(i)))
                .boxed()
                .collect(Collectors.toList());

    }

    public double calculateDistance(final List<Double> point1,
                                    final List<Double> point2) {
        return Math.sqrt(IntStream.range(0, point1.size())
                .mapToDouble(i -> point1.get(i) - point2.get(i))
                .map(i -> i * i)
                .sum());

    }

    public List<Double> getCloseness(final List<Double> negative,
                                     final List<Double> positive) {
        return IntStream.range(0, negative.size())
                .mapToDouble(i -> negative.get(i) / (negative.get(i) + positive.get(i)))
                .boxed()
                .collect(Collectors.toList());
    }

    public List<String> getOrder(final List<Double> closeness) {
        return IntStream.range(0, closeness.size())
                .mapToObj(i -> Map.entry("A" + (i+1), closeness.get(i)))
                .sorted((Map.Entry<String, Double> alt1, Map.Entry<String, Double> alt2) -> Double.compare(alt2.getValue(), alt1.getValue()))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    public List<Double> normalizeWeights(final List<Double> weights){
        double sum = weights.stream()
                .mapToDouble(Double::doubleValue)
                .sum();
        return weights.stream()
                .map(i -> (double) i/ (double) sum)
                .collect(Collectors.toList());
    }

    private Double getBest(final List<Double> list, final Boolean isMax) {
        return isMax
                ? list.stream()
                .mapToDouble(Double::doubleValue)
                .max().getAsDouble()
                : list.stream()
                .mapToDouble(Double::doubleValue)
                .min().getAsDouble();
    }

    private Double getAverageGeometric(final List<Integer> list) {
        return Math.sqrt(list.stream()
                .mapToInt(Integer::intValue)
                .map(x -> x * x)
                .sum());
    }
    private Integer getMax(final List<Integer> list) {
        return list.stream()
                .mapToInt(Integer::intValue)
                .max().getAsInt();
    }
    private Integer getMin(final List<Integer> list) {
        return list.stream()
                .mapToInt(Integer::intValue)
                .min().getAsInt();
    }
}
