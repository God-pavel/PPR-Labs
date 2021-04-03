package com.studying.lab5;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Vikor {
    public List<List<Double>> normalizeScores(final List<List<Integer>> matrix) {
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
                        .mapToDouble(j -> (double) (columnMaxes.get(j) - integers.get(j)) / (double) (columnMaxes.get(j) - columnMins.get(j)))
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

    public List<Double> getS(final List<List<Double>> matrix){
        return matrix.stream()
                .map(list -> list.stream().mapToDouble(Double::doubleValue).sum())
                .collect(Collectors.toList());
    }
    public List<Map.Entry<String, Double>> order(final List<Double> list){
        return IntStream.range(0, list.size())
                .mapToObj(i -> Map.entry("A" + (i+1), list.get(i)))
                .sorted((Map.Entry<String, Double> alt1, Map.Entry<String, Double> alt2) -> Double.compare(alt2.getValue(), alt1.getValue()))
                .collect(Collectors.toList());
    }

    public String getBest(final List<Map.Entry<String, Double>> qlist){
        Collections.reverse(qlist);
        String result = qlist.get(0).getKey();
        int i = 0;

        while (-(qlist.get(0).getValue() - qlist.get(++i).getValue()) < (double) 1/(qlist.size()-1)){
            result += ", " + qlist.get(i).getKey();
        }
        return result;
    }

    public List<Double> getR(final List<List<Double>> matrix){
        return matrix.stream()
                .map(list -> list.stream().mapToDouble(Double::doubleValue).max().getAsDouble())
                .collect(Collectors.toList());
    }

    public List<Double> getQ(final List<Double> s,
                             final List<Double> r,
                             final Double v){
        return IntStream.range(0, s.size())
                .mapToDouble(i -> (v*(s.get(i) - getMinDouble(s))/(getMaxDouble(s) - getMinDouble(s))) +
                        ((1-v) * (r.get(i)- getMinDouble(r)) / (getMaxDouble(r) - getMinDouble(r))))
                .boxed()
                .collect(Collectors.toList());
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
    private Double getMaxDouble(final List<Double> list) {
        return list.stream()
                .mapToDouble(Double::doubleValue)
                .max().getAsDouble();
    }
    private Double getMinDouble(final List<Double> list) {
        return list.stream()
                .mapToDouble(Double::doubleValue)
                .min().getAsDouble();
    }
}
