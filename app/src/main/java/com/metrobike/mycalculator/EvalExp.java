package com.metrobike.mycalculator;

class Operator {

    char val;

    Operator(char a) {
        val = a;
    }

    static int getPriority(char val) {
        int prior = -1;
        if (val == '-') prior = 7;
        else if (val == '+') prior = 7;
        else if (val == '*') prior = 8;
        else if (val == '/') prior = 8;
        else if (val == '%') prior = 8;
        else if (val == '^') prior = 9;
        return prior;
    }

    int getPrior() {
        int prior = -1;
        if (val == '-') prior = 7;
        else if (val == '+') prior = 7;
        else if (val == '*') prior = 8;
        else if (val == '/') prior = 8;
        else if (val == '%') prior = 8;
        else if (val == '^') prior = 9;
        return prior;
    }

    public String toString() {
        return "" + val;
    }
}

class Operand {
    float val;

    Operand(float a) {
        val = a;
    }

    public String toString() {
        return Float.toString(val);
    }
}
