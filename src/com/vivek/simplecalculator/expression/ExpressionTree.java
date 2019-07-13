package com.vivek.simplecalculator.expression;

import com.vivek.simplecalculator.operation.ArithmeticOperation;
import com.vivek.simplecalculator.operation.FunctionOperation;
import com.vivek.simplecalculator.operation.Operation;
import com.vivek.simplecalculator.tokenizer.Token;
import com.vivek.simplecalculator.tokenizer.TokenType;

import java.util.Stack;

public class  ExpressionTree {

    private final double operand;
    private final Operation operation;
    private final ExpressionTree left, right;

    private ExpressionTree(double operand, Operation operation,
                          ExpressionTree left, ExpressionTree right) {
        this.operand = operand;
        this.operation = operation;
        this.right = right;
        this.left = left;
    }

    public static ExpressionTree from(Token[] tokens) {
        Stack<ExpressionTree> stack = new Stack<>();
        for(Token token : tokens){
            if(token.getType()== TokenType.NUMBER){
                ExpressionTree numberTree = new ExpressionTree(Double.parseDouble(token.getValue()),null,null,null);
                stack.push(numberTree);
            }else if(token.getType()==TokenType.OPERATOR){
                ArithmeticOperation operation = ArithmeticOperation.getForSymbol(token.getValue().charAt(0));
                ExpressionTree opTree;
                if(operation.getNumOperands()==1){
                    opTree = new ExpressionTree(0, operation, stack.pop(),null);
                }else{
                    ExpressionTree right = stack.pop();
                    ExpressionTree left = stack.pop();
                    opTree = new ExpressionTree(0, operation, left, right);
                }
                stack.push(opTree);
            }else if(token.getType()==TokenType.FUNCTION){
                FunctionOperation func = FunctionOperation.getForName(token.getValue());
                ExpressionTree funcTree = new ExpressionTree(0,func,stack.pop(),null);
                stack.push(funcTree);
            }else{
                throw new RuntimeException("invalid token: "+token);
            }
        }
        return stack.pop();
    }

    public double evaluate(){
        if(this.operation==null){
            return this.operand;
        }
        int numOperands = operation.getNumOperands();
        if(numOperands==1){
            return operation.apply(left.evaluate());
        }else{
            return operation.apply(left.evaluate(), right.evaluate());
        }
    }
}
