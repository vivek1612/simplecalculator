package com.vivek.simplecalculator.expression;

import com.vivek.simplecalculator.tokenizer.Token;

public class Expression {

    private final String expression;
    private BinaryExpressionTree expTree;
    private Double value;

    public Expression(String expression) {
        this.expression = expression;
    }

    public double evaluate() {
        if (value == null) {
            synchronized (Expression.class) {
                if (value == null) {
                    Token[] tokens = ExpressionTranslator.convertInfixToPostfix(expression);
                    expTree = BinaryExpressionTree.from(tokens);
                    value = expTree.evaluate();
                }
            }
        }
        return value;
    }
}
