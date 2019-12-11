package com.vivek.simplecalculator;

import com.vivek.simplecalculator.expression.Expression;

import java.io.InputStreamReader;
import java.util.Scanner;

public class SimpleCalculator {


    private static final String CMD_PROMT = ">";
    private static final String CMD_QUIT = "quit";
    private static final String CMD_HELP = "help";
    private static final String HELP_MSSG =
            "Supported operations: add(+), subtract(-), " +
                    "divide(/), multiply(*), modulus(%), power(^)\n" +
                    "Supported functions: sine(sin), cosine(cos), tan, log\n";
    private static final String INIT_MSSG =
            "Type expression to getValue. Type \"help\" for help.  Type \"quit\" to exit";
    private static final SimpleCalculator calc = new SimpleCalculator();

    public static void main(String[] args) {

        System.out.println(INIT_MSSG);
        String input;
        Scanner scanner = new Scanner(new InputStreamReader(System.in));
        do {
            System.out.print(CMD_PROMT);
            input = scanner.nextLine();
            String expr = input.trim().toLowerCase();
            switch (expr){
                case CMD_QUIT:
                    scanner.close();
                    System.exit(0);
                case CMD_HELP:
                    System.out.println(HELP_MSSG);
                    break;
                default:
                    try {
                        double result = calc.calculate(input);
                        System.out.println(result);
                    } catch (Exception e) {
                        System.err.println(e.getMessage());
                    }
            }
        } while (true);
    }

    private double calculate(String input) {
        Expression exp = new Expression(input);
        return exp.getValue();
    }
}

