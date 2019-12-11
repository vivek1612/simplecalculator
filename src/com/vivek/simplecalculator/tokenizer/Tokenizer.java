package com.vivek.simplecalculator.tokenizer;

import com.vivek.simplecalculator.operation.ArithmeticOperation;
import com.vivek.simplecalculator.operation.FunctionOperation;

public class Tokenizer {

    private final char[] expression;
    private final int expressionLength;
    private int pos = 0;
    private Token last;


    public Tokenizer(String expression) {
        this.expression = expression.trim().toCharArray();
        this.expressionLength = this.expression.length;
    }

    public boolean hasNext() {
        return this.expression.length > pos;
    }

    public Token next() {
        char ch = expression[pos];
        while (Character.isWhitespace(ch)) {
            ch = expression[++pos];
        }
        if (Character.isDigit(ch) || ch == '.') {
            if (last != null) {
                if (last.getType() == TokenType.NUMBER) {
                    throw new IllegalArgumentException("invalid expression. pos:  "+pos);
                }
            }
            return parseNumber(ch);
        } else if (ch == '(' || ch == ')') {
            return parseParentheses(ch);
        } else if (ArithmeticOperation.isSupported(ch)) {
            pos++;
            last = new Token(TokenType.OPERATOR, Character.toString(ch));
            return last;
        } else if (Character.isLetter(ch)) {
            return parseFunction();
        }
        throw new IllegalArgumentException("invalid expression. pos:  "+pos);
    }

    private Token parseParentheses(final char ch) {
        if (ch == '(') {
            this.last = new Token(TokenType.LEFT_BRACKET, "(");
        } else {
            this.last = new Token(TokenType.RIGHT_BRACKET, ")");
        }
        this.pos++;
        return last;
    }

    private Token parseFunction() {
        final int offset = this.pos;
        int testPos;
        int lastValidLen = 1;
        Token lastValidToken = null;
        int len = 1;
        if (isEndOfExpression(offset)) {
            this.pos++;
        }
        testPos = offset + len - 1;
        while (!isEndOfExpression(testPos) &&
                Character.isLetter(expression[testPos])) {
            String name = new String(expression, offset, len);
            if (FunctionOperation.isSupported(name)) {
                lastValidLen = len;
                lastValidToken = new Token(TokenType.FUNCTION, name);
            }
            len++;
            testPos = offset + len - 1;
        }
        if (lastValidToken == null) {
            throw new RuntimeException("invalid expression. " + "pos: " + pos);
        }
        pos += lastValidLen;
        last = lastValidToken;
        return last;
    }

    private Token parseNumber(final char firstChar) {
        final int offset = this.pos;
        int len = 1;
        this.pos++;
        if (isEndOfExpression(offset + len)) {
            last = new Token(TokenType.NUMBER, (String.valueOf(firstChar)));
            return last;
        }
        while (!isEndOfExpression(offset + len) &&
                (Character.isDigit(expression[offset + len]) || expression[offset + len] == '.')) {
            len++;
            this.pos++;
        }

        String value = new String(expression, offset, len);
        try {
            Double.parseDouble(value);
        } catch (NumberFormatException e) {
            throw new RuntimeException("invalid expression. pos: " + offset, e);
        }
        last = new Token(TokenType.NUMBER, value);
        return last;
    }

    private boolean isEndOfExpression(int offset) {
        return this.expressionLength <= offset;
    }
}
