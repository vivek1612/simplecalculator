package com.vivek.simplecalculator.operation;

import java.util.function.DoubleUnaryOperator;

public enum FunctionOperation implements Operation {

    SINE("sin", Math::sin),
    COSINE("cos", Math::cos),
    TAN("tan", Math::tan),
    LOG("log", Math::log);

    private final DoubleUnaryOperator operator;
    private final String name;


    FunctionOperation(String name, DoubleUnaryOperator operator) {
        this.operator = operator;
        this.name = name;
    }

    public static boolean isSupported(String name) {
        try {
            FunctionOperation.valueOf(name);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    @Override
    public int getNumOperands() {
        return 1;
    }

    @Override
    public double apply(double... operands) {
        return operator.applyAsDouble(operands[0]);
    }
}