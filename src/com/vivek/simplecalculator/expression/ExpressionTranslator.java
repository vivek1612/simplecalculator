package com.vivek.simplecalculator.expression;

import com.vivek.simplecalculator.operation.ArithmeticOperation;
import com.vivek.simplecalculator.tokenizer.Token;
import com.vivek.simplecalculator.tokenizer.TokenType;
import com.vivek.simplecalculator.tokenizer.Tokenizer;

import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.concurrent.LinkedBlockingDeque;

class ExpressionTranslator {


    static Token[] convertInfixToPostfix(final String expression) {
        final Deque<Token> stack = new LinkedBlockingDeque<>();
        final List<Token> output = new ArrayList<>();

        final Tokenizer tokenizer = new Tokenizer(expression);
        while (tokenizer.hasNext()) {
            Token token = tokenizer.next();
            switch (token.getType()) {
                case NUMBER:
                    output.add(token);
                    break;
                case FUNCTION:
                    stack.add(token);
                    break;
                case OPERATOR:
                    while (!stack.isEmpty() && stack.peek().getType() == TokenType.OPERATOR) {
                        Token nextToken = stack.peek();
                        ArithmeticOperation operation1 = ArithmeticOperation.getForSymbol(token.getValue().charAt(0));
                        ArithmeticOperation operation2 = ArithmeticOperation.getForSymbol(nextToken.getValue().charAt(0));
                        if (operation1.getNumOperands() == 1 && operation2.getNumOperands() == 2) {
                            break;
                        } else if ((operation1.isLeftAssociative() && operation1.getPrecedence() <= operation2.getPrecedence())
                                || (operation1.getPrecedence() < operation2.getPrecedence())) {
                            output.add(stack.pop());
                        } else {
                            break;
                        }
                    }
                    stack.push(token);
                    break;
                case LEFT_BRACKET:
                    stack.push(token);
                    break;
                case RIGHT_BRACKET:
                    while (!stack.isEmpty() && stack.peek().getType() != TokenType.LEFT_BRACKET) {
                        output.add(stack.pop());
                    }
                    if (stack.isEmpty()) {
                        throw new IllegalArgumentException("Mismatch in brackets");
                    }
                    stack.pop();
                    if (!stack.isEmpty() && stack.peek().getType() == TokenType.FUNCTION) {
                        output.add(stack.pop());
                    }
                    break;
                default:
                    throw new IllegalArgumentException("Unrecognized token: " + token.getValue());
            }
        }

        while (!stack.isEmpty()) {
            Token t = stack.pop();
            if (t.getType() == TokenType.LEFT_BRACKET || t.getType() == TokenType.RIGHT_BRACKET) {
                throw new IllegalArgumentException("Mismatch in brackets");
            } else {
                output.add(t);
            }
        }
        return output.toArray(new Token[0]);
    }
}
