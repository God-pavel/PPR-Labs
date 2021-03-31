package com.studying.lab3;

import java.util.List;

public class ParetoSystem {
    private List<List<Boolean>> matrixP;
    private List<List<Boolean>> matrixI;
    private List<List<Boolean>> matrixN;

    public ParetoSystem(List<List<Boolean>> matrixP, List<List<Boolean>> matrixI, List<List<Boolean>> matrixN) {
        this.matrixP = matrixP;
        this.matrixI = matrixI;
        this.matrixN = matrixN;
    }

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
}
