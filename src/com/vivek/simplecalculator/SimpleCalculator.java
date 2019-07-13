package com.vivek.simplecalculator;

import com.vivek.simplecalculator.expression.ExpressionTranslator;
import com.vivek.simplecalculator.expression.ExpressionTree;
import com.vivek.simplecalculator.tokenizer.Token;
import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

public class SimpleCalculator {


    public static void main(String[] args) {
        String e = "1.5^0-2*cos90";
//        Tokenizer tokenizer = new Tokenizer(e);
//        while(tokenizer.hasNext()){
//            System.out.println(tokenizer.nextToken().getValue());
//        }
//
//        System.out.println("-------------");
//
//        Tokenizer tokenizer1 = new Tokenizer(e,null,null,null,false);
//        while(tokenizer1.hasNext()){
//            System.out.println(tokenizer1.nextToken().toString());
//        }

        Token[] tokens = ExpressionTranslator.convertInfixToPostfix(e);
        for(Token t:tokens){
            System.out.println(t.getValue());
        }

        System.out.println("-------------");
        net.objecthunter.exp4j.tokenizer.Token[] tokens1 = net.objecthunter.exp4j.shuntingyard.ShuntingYard.convertToRPN(e, null, null, null, false);
        for(net.objecthunter.exp4j.tokenizer.Token t:tokens1){
            System.out.println(t.toString());
        }

        ExpressionTree tree = ExpressionTree.from(tokens);
        System.out.println(tree.evaluate());
        System.out.println("-------------");

        Expression expression = new ExpressionBuilder(e).build();
        System.out.println(expression.evaluate());

    }
}

