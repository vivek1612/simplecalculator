package com.vivek.simplecalculator.expression;

import com.vivek.simplecalculator.operation.ArithmeticOperation;
import com.vivek.simplecalculator.operation.FunctionOperation;
import com.vivek.simplecalculator.operation.Operation;
import com.vivek.simplecalculator.tokenizer.Token;

import java.util.Arrays;
import java.util.Deque;
import java.util.NoSuchElementException;
import java.util.concurrent.LinkedBlockingDeque;

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
        Deque<BinaryExpressionTree> stack = new LinkedBlockingDeque<>();
        Arrays.stream(tokens).forEach(token -> {
            switch (token.getType()){
                case NUMBER:
                    BinaryExpressionTree numberTree = new BinaryExpressionTree(Double.parseDouble(token.getValue()), null, null, null);
                    stack.push(numberTree);
                    break;
                case OPERATOR:
                    ArithmeticOperation operation = ArithmeticOperation.getForSymbol(token.getValue().charAt(0));
                    BinaryExpressionTree opTree;
                    if (operation.getNumOperands() == 1) {
                        opTree = new BinaryExpressionTree(0, operation, stack.pop(), null);
                    } else {
                        BinaryExpressionTree right;
                        BinaryExpressionTree left;
                        try {
                            right = stack.pop();
                            left = stack.pop();
                        } catch (NoSuchElementException e) {
                            throw new RuntimeException("invalid expression", e);
                        }
                        opTree = new BinaryExpressionTree(0, operation, left, right);
                    }
                    stack.push(opTree);
                    break;
                case FUNCTION:
                    FunctionOperation func = FunctionOperation.getForName(token.getValue());
                    BinaryExpressionTree funcTree = new BinaryExpressionTree(0, func, stack.pop(), null);
                    stack.push(funcTree);
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + token.getType());
            }
        });
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
