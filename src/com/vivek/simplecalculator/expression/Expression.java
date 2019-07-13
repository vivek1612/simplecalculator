package com.vivek.simplecalculator.expression;

import com.vivek.simplecalculator.tokenizer.Token;

public class Expression {

    private final String expression;
    private ExpressionTree expTree;
    private Double value;

    public Expression(String expression) {
        this.expression = expression;
    }

    public double evaluate(){
        Token[] tokens = ExpressionTranslator.convertInfixToPostfix(expression);
        expTree = ExpressionTree.from(tokens);
        return expTree.evaluate();
    }
}
