package com.studying.lab5;

import com.studying.Executor;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.studying.util.FifthLabAlternativesAndCriteria.ALTERNATIVES_AND_CRITERIA;
import static com.studying.util.FifthLabAlternativesAndCriteria.WEIGHTS;

public class FifthLabExecutor implements Executor {

    public static final List<Boolean> subtask1WeightsPis = List.of(true, true, true, true, true, true, true, true, true, true, true, true);
    public static final List<Boolean> subtask1WeightsNis = List.of(false, false, false, false, false, false, false, false, false, false, false, false);

    public static final List<Boolean> subtask2WeightsPis = List.of(true, true, true, true, true, true, true, false, false, false, false, false);
    public static final List<Boolean> subtask2WeightsNis = List.of(false, false, false, false, false, false, false, true, true, true, true, true);

    public static final List<List<Integer>> T = List.of(
            List.of(5, 8, 4),
            List.of(7, 6, 8),
            List.of(8, 8, 6),
            List.of(7, 4, 6)
    );
    public static final List<Double> W = List.of(0.3, 0.4, 0.3);

    @Override
    public void execute() {
        System.out.println("---------------------TOPSIS SUBTASK1---------------------");
        Topsis topsis = new Topsis();
        List<Double> normalizedWeights = topsis.normalizeWeights(WEIGHTS);
        printList("Normilized weights: ", normalizedWeights);

        List<List<Double>> normalized = topsis.normalizeScores(ALTERNATIVES_AND_CRITERIA);
        printRelation(normalized);
        List<List<Double>> weighted = topsis.weightScores(normalized, normalizedWeights);
        printRelation(weighted);
        List<Double> pis = topsis.getPisNis(weighted, subtask1WeightsPis);
        printList("PIS : ", pis);
        List<Double> nis = topsis.getPisNis(weighted, subtask1WeightsNis);
        printList("NIS : ", nis);
        List<Double> nisDistances = weighted.stream()
                .map(alternative -> topsis.calculateDistance(nis, alternative))
                .collect(Collectors.toList());
        List<Double> pisDistances = weighted.stream()
                .map(alternative -> topsis.calculateDistance(pis, alternative))
                .collect(Collectors.toList());
        printList("S+ : ", pisDistances);
        printList("S- : ", nisDistances);
        List<Double> closeness = topsis.getCloseness(nisDistances, pisDistances);
        printList("Closeness : ", closeness);
        List<String> order = topsis.getOrder(closeness);
        System.out.println("Alternatives : ");
        printOrder(order);
        System.out.println("---------------------TOPSIS SUBTASK2---------------------");

        List<List<Double>> normalized2 = topsis.normalizeScoresWithCriteriaPolarity(ALTERNATIVES_AND_CRITERIA, subtask2WeightsPis);
        printRelation(normalized2);
        List<List<Double>> weighted2 = topsis.weightScores(normalized2, normalizedWeights);
        printRelation(weighted2);
        List<Double> pis2 = topsis.getPisNis(weighted2, subtask2WeightsPis);
        printList("PIS : ", pis2);
        List<Double> nis2 = topsis.getPisNis(weighted2, subtask2WeightsNis);
        printList("NIS : ", nis2);
        List<Double> nisDistances2 = weighted2.stream()
                .map(alternative -> topsis.calculateDistance(nis2, alternative))
                .collect(Collectors.toList());
        List<Double> pisDistances2 = weighted2.stream()
                .map(alternative -> topsis.calculateDistance(pis2, alternative))
                .collect(Collectors.toList());
        printList("S+ : ", pisDistances2);
        printList("S- : ", nisDistances2);
        List<Double> closeness2 = topsis.getCloseness(nisDistances2, pisDistances2);
        printList("Closeness : ", closeness2);
        List<String> order2 = topsis.getOrder(closeness2);
        System.out.println("Alternatives : ");
        printOrder(order2);

        System.out.println("---------------------VIKOR---------------------");

        final Vikor vikor = new Vikor();
        List<List<Double>> vikor_normalized = vikor.normalizeScores(ALTERNATIVES_AND_CRITERIA);
        printRelation(vikor_normalized);
        List<List<Double>> vikor_weighted = vikor.weightScores(vikor_normalized, normalizedWeights);
        printRelation(vikor_weighted);
        double v = 0.5;
        List<Double> s = vikor.getS(vikor_weighted);
        List<Double> r = vikor.getR(vikor_weighted);
        List<Double> q = vikor.getQ(s, r, v);
        System.out.println("Strategy weight: " + v);
        printList("S : ", s);
        printList("R : ", r);
        printList("Q : ", q);
        List<Map.Entry<String, Double>> sortedS = vikor.order(s);
        System.out.println("Sorted S:\n" + sortedS.get(0).toString() + " " + sortedS.get(1).toString() + " " + sortedS.get(2).toString() + " " + sortedS.get(3).toString() + " " + sortedS.get(4).toString() + "\n" + sortedS.get(5).toString() + " " + sortedS.get(6).toString() + " " + sortedS.get(7).toString() + " " + sortedS.get(8).toString() + " " + sortedS.get(9).toString() + "\n" + sortedS.get(10).toString() + " " + sortedS.get(11).toString() + sortedS.get(12).toString() + " " + sortedS.get(13).toString() + " " + sortedS.get(14).toString());
        List<Map.Entry<String, Double>> sortedR = vikor.order(r);
        System.out.println("Sorted R:\n" + sortedR.get(0).toString() + " " + sortedR.get(1).toString() + " " + sortedR.get(2).toString() + " " + sortedR.get(3).toString() + " " + sortedR.get(4).toString() + "\n" + sortedR.get(5).toString() + " " + sortedR.get(6).toString() + " " + sortedR.get(7).toString() + " " + sortedR.get(8).toString() + " " + sortedR.get(9).toString() + "\n" + sortedR.get(10).toString() + " " + sortedR.get(11).toString()+ sortedR.get(12).toString() + " " + sortedR.get(13).toString() + " " + sortedR.get(14).toString());
        List<Map.Entry<String, Double>> sortedQ = vikor.order(q);
        System.out.println("Sorted Q:\n" + sortedQ.get(0).toString() + " " + sortedQ.get(1).toString() + " " + sortedQ.get(2).toString() + " " + sortedQ.get(3).toString() + " " + sortedQ.get(4).toString() + "\n" + sortedQ.get(5).toString() + " " + sortedQ.get(6).toString() + " " + sortedQ.get(7).toString() + " " + sortedQ.get(8).toString() + " " + sortedQ.get(9).toString() + "\n" + sortedQ.get(10).toString() + " " + sortedQ.get(11).toString()+ sortedQ.get(12).toString() + " " + sortedQ.get(13).toString() + " " + sortedQ.get(14).toString());
        System.out.println("Result: " + vikor.getBest(sortedQ));
    }

    private void printRelation(List<List<Double>> relation) {
        System.out.println();
        relation.stream()
                .map(list -> list.stream().map(a -> Math.round(a * 100) / 100D).collect(Collectors.toList()))
                .forEach(alternative -> System.out.println(alternative.toString()));
    }

    private void printList(String message, List<Double> list) {
        System.out.println(message + list.stream().map(a -> Math.round(a * 10000) / 10000D).collect(Collectors.toList()).toString());
    }

    private void printOrder(List<String> list) {
        list.forEach(el -> {
            if (list.get(0).equals(el)) {
                System.out.print(el);
            } else {
                System.out.print(" > " + el);
            }
        });
        System.out.println();
    }
}
