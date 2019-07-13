package com.vivek.simplecalculator.operation;

public interface Operation {

    int getNumOperands();

    double apply(double ... operands);
}
