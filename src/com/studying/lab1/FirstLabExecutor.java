package com.studying.lab1;

import com.studying.Executor;
import com.studying.util.Classes;
import com.studying.util.Propety;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.studying.lab1.ValidatorsPropertiesMap.PROPERTY_VALIDATOR;
import static com.studying.util.FirstLabRelations.*;

public class FirstLabExecutor implements Executor {

    @Override
    public void execute() {
        for (int[][] relation : FIRST_LAB_RELATIONS) {
            List<Propety> properties = getRelationPropeties(relation);
            System.out.print("Properties: " + properties + ". Main class: ");
            System.out.println(defineMainClass(properties));
            System.out.println();
        }
    }

    public static List<Propety> getRelationPropeties(int[][] relation) {
        return PROPERTY_VALIDATOR.entrySet().stream()
                .filter(entry -> entry.getValue().test(relation))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    public static Classes defineMainClass(List<Propety> properties) {
        return Stream.of(Classes.values())
                .filter(relationClass -> properties.containsAll(relationClass.getProperties()))
                .findFirst().get();
    }
}
