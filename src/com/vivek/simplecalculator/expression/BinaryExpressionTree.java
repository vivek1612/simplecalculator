package com.vivek.simplecalculator.expression;

import com.vivek.simplecalculator.operation.ArithmeticOperation;
import com.vivek.simplecalculator.operation.FunctionOperation;
import com.vivek.simplecalculator.operation.Operation;
import com.vivek.simplecalculator.tokenizer.Token;
import com.vivek.simplecalculator.tokenizer.TokenType;

import java.util.Stack;

class BinaryExpressionTree {

    private final double operand;
    private final Operation operation;
    private final BinaryExpressionTree left, right;

    private BinaryExpressionTree(double operand, Operation operation,
                                 BinaryExpressionTree left, BinaryExpressionTree right) {
        this.operand = operand;
        this.operation = operation;
        this.right = right;
        this.left = left;
    }

    static BinaryExpressionTree from(Token[] tokens) {
        Stack<BinaryExpressionTree> stack = new Stack<>();
        for (Token token : tokens) {
            if (token.getType() == TokenType.NUMBER) {
                BinaryExpressionTree numberTree = new BinaryExpressionTree(Double.parseDouble(token.getValue()), null, null, null);
                stack.push(numberTree);
            } else if (token.getType() == TokenType.OPERATOR) {
                ArithmeticOperation operation = ArithmeticOperation.getForSymbol(token.getValue().charAt(0));
                BinaryExpressionTree opTree;
                if (operation.getNumOperands() == 1) {
                    opTree = new BinaryExpressionTree(0, operation, stack.pop(), null);
                } else {
                    BinaryExpressionTree right = stack.pop();
                    BinaryExpressionTree left = stack.pop();
                    opTree = new BinaryExpressionTree(0, operation, left, right);
                }
                stack.push(opTree);
            } else if (token.getType() == TokenType.FUNCTION) {
                FunctionOperation func = FunctionOperation.getForName(token.getValue());
                BinaryExpressionTree funcTree = new BinaryExpressionTree(0, func, stack.pop(), null);
                stack.push(funcTree);
            } else {
                throw new RuntimeException("invalid token: " + token);
            }
        }
        return stack.pop();
    }

    double evaluate() {
        if (this.operation == null) {
            return this.operand;
        }
        int numOperands = operation.getNumOperands();
        if (numOperands == 1) {
            return operation.apply(left.evaluate());
        } else {
            return operation.apply(left.evaluate(), right.evaluate());
        }
    }
}
