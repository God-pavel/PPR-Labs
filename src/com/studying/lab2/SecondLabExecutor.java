package com.studying.lab2;

import com.studying.Executor;
import com.studying.util.Propety;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static com.studying.lab1.FirstLabExecutor.defineMainClass;
import static com.studying.lab1.FirstLabExecutor.getRelationPropeties;
import static com.studying.lab1.Validators.isAcyclic;
import static com.studying.lab2.optimizations.OptimizationFunctions.*;
import static com.studying.util.FirstLabRelations.FIRST_LAB_RELATIONS;
import static com.studying.util.SecondLabRelations.SECOND_LAB_RELATIONS;

public class SecondLabExecutor implements Executor {

    private static final Map<String, Function<int[][], String>> optimizationsFunctions =
            Map.ofEntries(
                    Map.entry("X0R", relationBlockingOptimizationFunction),
                    Map.entry("X00R", strictRelationBlockingOptimizationFunction),
                    Map.entry("X0P", asymmetricRelationBlockingOptimizationFunction),
                    Map.entry("X*R", relationDominationOptimizationFunction),
                    Map.entry("X**R", strictRelationDominationOptimizationFunction),
                    Map.entry("X*P", asymmetricRelationDominationOptimizationFunction)
            );

    @Override
    public void execute() {
        System.out.println("-------FIRST PART-----------");
        int i = 1;
        for (int[][] relation : FIRST_LAB_RELATIONS) {
            System.out.println("Relation: " + i++);
            List<Propety> properties = getRelationPropeties(relation);
            System.out.println("Relation class: " + defineMainClass(properties));
            optimizationsFunctions.entrySet().stream()
                    .map(entry -> entry.getKey() + " = " + entry.getValue().apply(relation))
                    .forEach(System.out::println);
            System.out.println("--------------------------------");
        }

        System.out.println("-------SECOND PART-----------");
        i = 1;
        for (int[][] relation : SECOND_LAB_RELATIONS) {
            System.out.println("Relation: " + i++);
            if (isAcyclic.test(relation)) {
                System.out.println("NM Optimiztion:\n");
                System.out.println(NMOptimizationFunction.apply(relation) + "\n");
            } else {
                System.out.println("K Optimiztion:\n");
                System.out.println(KOptimizationFunction.apply(relation) + "\n");
            }
        }
    }
}
