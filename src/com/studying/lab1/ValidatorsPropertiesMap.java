package com.studying.lab1;

import com.studying.util.Propety;

import java.util.Map;
import java.util.function.Predicate;

import static com.studying.lab1.Validators.*;
import static com.studying.util.Propety.*;

public class ValidatorsPropertiesMap {
    public static final Map<Propety, Predicate<Integer[][]>> PROPERTY_VALIDATOR = Map.of(
            REFLEXIVE, isReflexive,
            IRREFLEXIVE, isIrreflexive,
            SYMMETRIC, isSymmetric,
            ASYMMETRIC, isAsymmetric,
            ANTISYMMETRIC, isAntisymmetric,
            TRANSITIVE, isTransitive,
            NEGATIVELY_TRANSITIVE, isNegativelyTransitive,
            LINKED, isLinked,
            WEAKLY_LINKED, isWeaklyLinked,
            ACYCLIC, isAcyclic
    );
}
