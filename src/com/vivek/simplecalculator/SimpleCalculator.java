package com.vivek.simplecalculator;

import com.vivek.simplecalculator.expression.Expression;

import java.io.InputStreamReader;
import java.util.Scanner;

public class SimpleCalculator {


    private static final String HELP_MSSG =
            "Supported operations: add(+), subtract(-), " +
            "divide(/), multiply(*), modulus(%), power(^)\n" +
            "Supported functions: sine(sin), cosine(cos), tan, log\n";

    public static void main(String[] args) {

        System.out.println("Type expression to evaluate. Type \"help\" for help.  Type \"quit\" to exit");
        String input;
        Scanner scanner = new Scanner(new InputStreamReader(System.in));
        do{
            System.out.print(">");
            input = scanner.nextLine();
            if(input.equalsIgnoreCase("quit")){
                System.exit(0);
            }else if(input.equalsIgnoreCase("help")){
                System.out.println(HELP_MSSG);
                continue;
            }
            try{
                double result = new SimpleCalculator().calculate(input);
                System.out.println(result);
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }

        }while (true);
    }

    private double calculate(String input){
        Expression exp = new Expression(input);
        return exp.evaluate();
    }
}

