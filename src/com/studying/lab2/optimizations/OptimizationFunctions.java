package com.studying.lab2.optimizations;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.studying.lab1.Validators.isAsymmetric;
import static com.studying.util.KOptimizationUtils.K_TYPES;
import static com.studying.util.KOptimizationUtils.RELATION_TYPE_PREDICATES;

public class OptimizationFunctions {
    public static final Function<Integer[][], String> asymmetricRelationBlockingOptimizationFunction = relation -> {
        if (!isAsymmetric.test(relation)) {
            return "{}";
        }
        String result = IntStream.range(0, relation.length)
                .boxed()
                .filter(element -> getUpperSection(element, relation).isEmpty())
                .map(el -> el += 1)
                .map(Object::toString)
                .collect(Collectors.joining(", "));

        return "{" + result + "}";
    };

    public static final Function<Integer[][], String> asymmetricRelationDominationOptimizationFunction = relation -> {
        if (!isAsymmetric.test(relation)) {
            return "{}";
        }
        String result = IntStream.range(0, relation.length)
                .boxed()
                .filter(element -> getLowerSection(element, relation)
                        .containsAll(collectNotEqualsElems(getRelationSet(relation.length), element)))
                .map(el -> el += 1)
                .map(Object::toString)
                .collect(Collectors.joining(", "));

        return "{" + result + "}";
    };

    public static final Function<Integer[][], String> relationBlockingOptimizationFunction = relation -> {
        if (isAsymmetric.test(relation)) {
            return "{}";
        }
        String result = IntStream.range(0, relation.length)
                .boxed()
                .filter(element -> getLowerSection(element, relation)
                        .containsAll(getUpperSection(element, relation)))
                .map(el -> el += 1)
                .map(Object::toString)
                .collect(Collectors.joining(", "));

        return "{" + result + "}";
    };

    public static final Function<Integer[][], String> relationDominationOptimizationFunction = relation -> {
        if (isAsymmetric.test(relation)) {
            return "{}";
        }
        String result = IntStream.range(0, relation.length)
                .boxed()
                .filter(element -> getLowerSection(element, relation)
                        .containsAll(getRelationSet(relation.length)))
                .map(el -> el += 1)
                .map(Object::toString)
                .collect(Collectors.joining(", "));

        return "{" + result + "}";
    };

    public static final Function<Integer[][], String> strictRelationBlockingOptimizationFunction = relation -> {
        if (isAsymmetric.test(relation)) {
            return "{}";
        }
        String result = IntStream.range(0, relation.length)
                .boxed()
                .filter(element -> getUpperSection(element, relation)
                        .stream()
                        .allMatch(element::equals))
                .map(el -> el += 1)
                .map(Object::toString)
                .collect(Collectors.joining(", "));

        return "{" + result + "}";
    };

    public static final Function<Integer[][], String> strictRelationDominationOptimizationFunction = relation -> {
        if (isAsymmetric.test(relation)) {
            return "{}";
        }
        String result = IntStream.range(0, relation.length)
                .boxed()
                .filter(element -> getLowerSection(element, relation).containsAll(getRelationSet(relation.length))
                        && getUpperSection(element, relation).contains(element)
                        && getUpperSection(element, relation).size() == 1)
                .map(el -> el += 1)
                .map(Object::toString)
                .collect(Collectors.joining(", "));

        return "{" + result + "}";
    };

    public static final Function<Integer[][], String> KOptimizationFunction = (relation) -> {
        final List<List<String>> relationMatrix = applyRelationTypes(relation);

        return K_TYPES.entrySet().stream()
                .sorted(Comparator.comparingInt(Map.Entry::getKey))
                .map(entry -> solveForK(relationMatrix, entry.getValue(), relation, entry.getKey()))
                .collect(Collectors.joining("\n"));
    };

    public static final Function<Integer[][], String> NMOptimizationFunction = (relation) -> {
        return buildResultString(getResult(getSCollectionIncrements(getInitialSCollection(relation), relation), relation), relation);
    };

    private static List<Integer> getLowerSection(final int element, final Integer[][] matrix) {

        return IntStream.range(0, matrix.length)
                .filter(index -> matrix[element][index] == 1)
                .boxed()
                .collect(Collectors.toList());
    }

    private static List<Integer> getUpperSection(final int element, final Integer[][] matrix) {

        return IntStream.range(0, matrix.length)
                .filter(index -> matrix[index][element] == 1)
                .boxed()
                .collect(Collectors.toList());
    }

    private static Set<Integer> getRelationSet(final int length) {

        return IntStream.range(0, length)
                .boxed()
                .collect(Collectors.toSet());

    }

    private static Set<Integer> collectNotEqualsElems(final Set<Integer> set, final int element) {

        return set.stream()
                .filter(member -> !member.equals(element))
                .collect(Collectors.toSet());
    }

    private static List<List<String>> applyRelationTypes(final Integer[][] relation) {

        return IntStream.range(0, relation.length)
                .mapToObj(rowIndex -> IntStream.range(0, relation.length)
                        .mapToObj(columnIndex -> getRelationType(rowIndex, columnIndex, relation))
                        .collect(Collectors.toList()))
                .collect(Collectors.toList());
    }

    private static String getRelationType(final int rowIndex, final int columnIndex, final Integer[][] relation) {

        return RELATION_TYPE_PREDICATES.entrySet().stream()
                .filter(entry -> entry.getKey().test(relation[rowIndex][columnIndex], relation[columnIndex][rowIndex]))
                .map(Map.Entry::getValue)
                .findFirst()
                .orElse("0");
    }

    private static String solveForK(final List<List<String>> matrix, final Set<String> usedTypes, final Integer[][] relation, final int k) {

        final List<List<Integer>> subsetsByK = matrix.stream()
                .map(row -> IntStream.range(0, matrix.size())
                        .filter(columnIndex -> usedTypes.contains(row.get(columnIndex)))
                        .boxed()
                        .collect(Collectors.toList()))
                .collect(Collectors.toList());

        return k + "-max = " + concatenateValues(getMaxForK(subsetsByK)) + "\n"
                + k + "-opt = " + concatenateValues(getOptimalForK(subsetsByK, relation));
    }

    private static List<Integer> getMaxForK(final List<List<Integer>> subsetsByK) {

        return IntStream.range(0, subsetsByK.size())
                .filter(index -> subsetsByK.stream()
                        .allMatch(subset -> subsetsByK.get(index)
                                .containsAll(subset)))
                .boxed()
                .collect(Collectors.toList());
    }

    private static List<Integer> getOptimalForK(final List<List<Integer>> subsetsByK, final Integer[][] relation) {

        return IntStream.range(0, subsetsByK.size())
                .filter(index -> subsetsByK.get(index)
                        .containsAll(getRelationSet(relation.length)))
                .boxed()
                .collect(Collectors.toList());
    }

    private static String concatenateValues(final Collection<Integer> values) {

        return "{" + values.stream()
                .map(el -> el += 1)
                .map(Object::toString)
                .collect(Collectors.joining(", ")) + "}";
    }

    private static Set<Integer> getInitialSCollection(final Integer[][] relation) {

        return getRelationSet(relation.length).stream()
                .filter(element -> getUpperSection(element, relation).isEmpty())
                .collect(Collectors.toSet());
    }

    private static List<Set<Integer>> getSCollectionIncrements(final Set<Integer> initialCollection, final Integer[][] relation) {

        final List<Set<Integer>> collectionIncrements = new ArrayList<>();
        final Set<Integer> sCollection = new HashSet<>(initialCollection);

        collectionIncrements.add(initialCollection);

        while (!doesCollectionIncrementsContainAllSet(collectionIncrements, relation)) {

            final Set<Integer> incrementToAdd = getRemainingElements(sCollection, relation).stream()
                    .filter(element -> sCollection.containsAll(getUpperSection(element, relation)))
                    .collect(Collectors.toSet());

            collectionIncrements.add(incrementToAdd);
            sCollection.addAll(incrementToAdd);
        }

        return collectionIncrements;
    }

    private static boolean doesCollectionIncrementsContainAllSet(final List<Set<Integer>> collectionIncrements, final Integer[][] relation) {

        return collectionIncrements.stream()
                .flatMap(Collection::stream)
                .collect(Collectors.toSet())
                .containsAll(getRelationSet(relation.length));
    }

    private static Set<Integer> getRemainingElements(Set<Integer> usedElements, final Integer[][] relation) {

        return getRelationSet(relation.length).stream()
                .filter(Predicate.not(usedElements::contains))
                .collect(Collectors.toSet());
    }

    private static Set<Integer> getFirstIncrement(final List<Set<Integer>> collectionIncrements) {

        return collectionIncrements.get(0);
    }

    private static Set<Integer> getResult(final List<Set<Integer>> sCollectionIncrement, final Integer[][] relation) {

        final Set<Integer> result = new LinkedHashSet<>(getFirstIncrement(sCollectionIncrement));

        sCollectionIncrement.stream()
                .skip(1)
                .forEach(increment -> result.addAll(getSuitableElementsFromIncrement(increment, result, relation)));

        return result;
    }

    private static Set<Integer> getSuitableElementsFromIncrement(final Set<Integer> increment, final Set<Integer> result, final Integer[][] relation) {

        return increment.stream()
                .filter(element -> intersectCollections(getUpperSection(element, relation), result).isEmpty())
                .collect(Collectors.toSet());
    }

    private static Set<Integer> intersectCollections(final Collection<Integer> set1, final Collection<Integer> set2) {

        return set1.stream()
                .filter(set2::contains)
                .collect(Collectors.toSet());
    }

    private static boolean performCheckOfResult(final Set<Integer> result, final Integer[][] relation) {

        return checkInternalStabilityOfResult(result, relation) && checkExternalStabilityOfResult(result, relation);
    }

    private static boolean checkInternalStabilityOfResult(final Set<Integer> result, final Integer[][] relation) {

        return result.stream()
                .allMatch(element -> result.stream()
                        .filter(innerElement -> !innerElement.equals(element))
                        .allMatch(innerElement -> checkInternalStabilityCondition(element, innerElement, relation)));
    }

    private static boolean checkInternalStabilityCondition(final int firstElement, final int secondElement, final Integer[][] relation) {

        return relation[firstElement][secondElement] == 0 && relation[secondElement][firstElement] == 0;
    }

    private static boolean checkExternalStabilityOfResult(final Set<Integer> result, final Integer[][] relation) {

        return getRelationSet(relation.length).stream()
                .filter(Predicate.not(result::contains))
                .allMatch(element -> result.stream()
                        .anyMatch(elementFromResult -> relation[elementFromResult][element] == 1));
    }

    private static String buildResultString(final Set<Integer> result, final Integer[][] relation) {

        return "X = {" + result.stream().map(el -> el += 1).map(Object::toString).collect(Collectors.joining(", ")) + "}\n"
                + "Are stability checks passed: " + performCheckOfResult(result, relation);
    }
}
