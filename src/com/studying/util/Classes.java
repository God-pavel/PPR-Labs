package com.studying.util;

import java.util.Set;

import static com.studying.util.Propety.*;

public enum Classes {

    EQUIVALENCE(Set.of(REFLEXIVE, SYMMETRIC, TRANSITIVE)),

    STRICT_ORDER(Set.of(NEGATIVELY_TRANSITIVE, ASYMMETRIC, TRANSITIVE, ACYCLIC)),

    NOT_STRICT_ORDER(Set.of(REFLEXIVE, ANTISYMMETRIC, TRANSITIVE)),

    QUASI_ORDER(Set.of(REFLEXIVE, TRANSITIVE)),

    WEAK_ORDERING(Set.of(ASYMMETRIC, TRANSITIVE, NEGATIVELY_TRANSITIVE)),

    TOLERANCE(Set.of(REFLEXIVE, SYMMETRIC)),

    NO_CLASS(Set.of());

    private final Set<Propety> requiredProperties;

    public Set<Propety> getProperties() {
        return this.requiredProperties;
    }

    Classes(final Set<Propety> requiredProperties) {

        this.requiredProperties = requiredProperties;
    }
}