package com.studying;

import com.studying.lab1.FirstLabExecutor;
import com.studying.lab2.SecondLabExecutor;

public class Main {
    public static void main(String[] args) {
//        Executor firstLabExecutor = new FirstLabExecutor();
//        firstLabExecutor.execute();

        Executor secondLabExecutor = new SecondLabExecutor();
        secondLabExecutor.execute();
    }
}