package com.vivek.simplecalculator.operation;

import java.util.function.DoubleBinaryOperator;

public enum ArithmeticOperation implements Operation {

    ADD('+', 1, true, 2, Double::sum),
    SUBTRACT('-', 1, true, 2, (a, b)-> a - b),
    MULTIPLY('*', 2, true, 2, (a, b)-> a * b),
    DIVIDE('/',2,true, 2, (a, b)-> a / b),
    MODULUS('%',2, true, 2, (a, b)-> a % b),
    POWER('^',3,false, 2, Math::pow);

    private final DoubleBinaryOperator operator;
    private final char symbol;
    private final int precedence;
    private final boolean leftAssociative;
    private final int numOperands;

    ArithmeticOperation(char symbol, int precedence, boolean leftAssociative, int numOperands, DoubleBinaryOperator operator) {
        this.operator = operator;
        this.symbol = symbol;
        this.precedence = precedence;
        this.leftAssociative = leftAssociative;
        this.numOperands = numOperands;
    }

    public char getSymbol() {
        return symbol;
    }

    public int getPrecedence() {
        return precedence;
    }

    public boolean isLeftAssociative() {
        return leftAssociative;
    }

    @Override
    public int getNumOperands() {
        return numOperands;
    }

    public static boolean isSupported(char symbol){
        ArithmeticOperation[] operations = ArithmeticOperation.values();
        for (ArithmeticOperation operation : operations) {
            if (symbol == operation.symbol) {
                return true;
            }
        }
        return false;
    }

    public static ArithmeticOperation getForSymbol(char symbol){
        ArithmeticOperation[] operations = ArithmeticOperation.values();
        for (ArithmeticOperation operation : operations) {
            if (symbol == operation.symbol) {
                return operation;
            }
        }
        throw new UnsupportedOperationException("no operator found for symbol: "+symbol);
    }

    @Override
    public double apply(double... operands) {
        return operator.applyAsDouble(operands[0], operands[1]);
    }
}