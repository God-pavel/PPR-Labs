package com.studying.lab3;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class BerezovskiySystem {
    private List<List<Boolean>> matrixP;
    private List<List<Boolean>> matrixI;
    private List<List<Boolean>> matrixN;

    public BerezovskiySystem(List<List<Boolean>> matrixP, List<List<Boolean>> matrixI, List<List<Boolean>> matrixN) {
        this.matrixP = matrixP;
        this.matrixI = matrixI;
        this.matrixN = matrixN;
    }

    public BerezovskiySystem(){}

    public List<List<Boolean>> getMatrixP() {
        return matrixP;
    }

    public void setMatrixP(List<List<Boolean>> matrixP) {
        this.matrixP = matrixP;
    }

    public List<List<Boolean>> getMatrixI() {
        return matrixI;
    }

    public void setMatrixI(List<List<Boolean>> matrixI) {
        this.matrixI = matrixI;
    }

    public List<List<Boolean>> getMatrixN() {
        return matrixN;
    }

    public void setMatrixN(List<List<Boolean>> matrixN) {
        this.matrixN = matrixN;
    }

    public BerezovskiySystem buildBerezovskiySystem(final List<ParetoSystem> paretoSystems) {

        final BerezovskiySystem result = new BerezovskiySystem(paretoSystems.get(0).getMatrixP(), paretoSystems.get(0).getMatrixI(), paretoSystems.get(0).getMatrixN());

        paretoSystems.stream()
                .skip(1)
                .forEach(paretoSystem -> modifyBerezovskiySystem(result, paretoSystem));

        return result;
    }

    private void modifyBerezovskiySystem(final BerezovskiySystem berezovskySystem, final ParetoSystem paretoSystem) {

        List<List<Boolean>> berezovskyPMatrix = getBerezovskyPMatrix(berezovskySystem, paretoSystem);
        List<List<Boolean>> berezovskyIMatrix = getBerezovskyIMatrix(berezovskySystem, paretoSystem);

        berezovskySystem.setMatrixP(berezovskyPMatrix);
        berezovskySystem.setMatrixI(berezovskyIMatrix);

        List<List<Boolean>> berezovskyNMatrix = getBerezovskyNMatrix(berezovskySystem, paretoSystem);

        berezovskySystem.setMatrixN(berezovskyNMatrix);
    }

    private List<List<Boolean>> getBerezovskyMatrix(final BerezovskiySystem berezovskySystem, final ParetoSystem paretoSystem,
                                                    QuadroPredicate<Integer, Integer, BerezovskiySystem, ParetoSystem> conditionPredicate) {

        return IntStream.range(0, paretoSystem.getMatrixP().size())
                .mapToObj(rowIndex -> IntStream.range(0, paretoSystem.getMatrixP().size())
                        .mapToObj(columnIndex -> conditionPredicate.test(rowIndex, columnIndex, berezovskySystem, paretoSystem))
                        .collect(Collectors.toList()))
                .collect(Collectors.toList());
    }

    private List<List<Boolean>> getBerezovskyPMatrix(final BerezovskiySystem berezovskySystem, final ParetoSystem paretoSystem) {

        return getBerezovskyMatrix(berezovskySystem, paretoSystem, this::checkBerezovskyPCondition);
    }

    private List<List<Boolean>> getBerezovskyIMatrix(final BerezovskiySystem berezovskySystem, final ParetoSystem paretoSystem) {

        return getBerezovskyMatrix(berezovskySystem, paretoSystem, this::checkBerezovskyICondition);
    }

    private List<List<Boolean>> getBerezovskyNMatrix(final BerezovskiySystem berezovskySystem, final ParetoSystem paretoSystem) {

        return getBerezovskyMatrix(berezovskySystem, paretoSystem, this::checkBerezovskyNCondition);
    }

    private boolean checkBerezovskyPCondition(final int rowIndex, final int columnIndex, final BerezovskiySystem berezovskySystem, final ParetoSystem paretoSystem) {

        return (paretoSystem.getMatrixP().get(rowIndex).get(columnIndex) && !berezovskySystem.getMatrixP().get(columnIndex).get(rowIndex))
                ||
                (paretoSystem.getMatrixI().get(rowIndex).get(columnIndex) && berezovskySystem.getMatrixP().get(rowIndex).get(columnIndex));
    }

    private boolean checkBerezovskyICondition(final int rowIndex, final int columnIndex, final BerezovskiySystem berezovskySystem, final ParetoSystem paretoSystem) {

        return paretoSystem.getMatrixI().get(rowIndex).get(columnIndex) && berezovskySystem.getMatrixI().get(rowIndex).get(columnIndex);
    }

    private boolean checkBerezovskyNCondition(final int rowIndex, final int columnIndex, final BerezovskiySystem berezovskySystem, final ParetoSystem paretoSystem) {

        return !(berezovskySystem.getMatrixP().get(rowIndex).get(columnIndex)
                || berezovskySystem.getMatrixP().get(columnIndex).get(rowIndex)
                || berezovskySystem.getMatrixI().get(rowIndex).get(columnIndex));
    }

    @FunctionalInterface
    public interface QuadroPredicate<T, U, R, V> {

        boolean test(T t, U u, R r, V v);
    }
}
