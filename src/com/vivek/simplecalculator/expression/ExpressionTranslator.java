/*
 * Copyright 2014 Frank Asseg
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.vivek.simplecalculator.expression;

import com.vivek.simplecalculator.operation.ArithmeticOperation;
import com.vivek.simplecalculator.tokenizer.Token;
import com.vivek.simplecalculator.tokenizer.TokenType;
import com.vivek.simplecalculator.tokenizer.Tokenizer;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

class ExpressionTranslator {


    static Token[] convertInfixToPostfix(final String expression) {
        final Stack<Token> stack = new Stack<>();
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
                    while (!stack.empty() && stack.peek().getType() == TokenType.OPERATOR) {
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

        while (!stack.empty()) {
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
