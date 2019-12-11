package com.vivek.simplecalculator.expression;

import com.vivek.simplecalculator.tokenizer.Token;

import java.util.concurrent.atomic.AtomicReference;

public class Expression {

    private final String expr;
    private final AtomicReference<Double> value = new AtomicReference<>();

    public Expression(String expr) {
        this.expr = expr;
    }

    public double getValue() {
        value.compareAndSet(null, evaluate());
        return value.get();
    }

    private double evaluate() {
        Token[] tokens = ExpressionTranslator.convertInfixToPostfix(expr);
        BinaryExpressionTree expTree = BinaryExpressionTree.from(tokens);
        return expTree.evaluate();
    }
}
