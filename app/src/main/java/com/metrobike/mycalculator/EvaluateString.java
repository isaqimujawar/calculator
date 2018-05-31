package com.metrobike.mycalculator;

import java.util.Stack;

public class EvaluateString {

    static Stack<Operand> operandStack;
    static Stack<Operator> operatorStack;

    static Result evalString(String expression) {
        char[] charArray;
        float result = 0;

        expression = "(" + expression + ")";
        charArray = expression.toCharArray();

        operandStack = new Stack<Operand>();
        operatorStack = new Stack<Operator>();

        int i = 0, j = 0;

        while (i < expression.length()) {
            if (charArray[i] == '(') {
                operatorStack.push(new Operator('('));
                i++;
            } else if (charArray[i] == ')') {
                Operator op;
                try {
                    while ((op = operatorStack.pop()).val != '(') {
                        Operand op2 = operandStack.pop();
                        Operand op1 = operandStack.pop();
                        if (op.val == '/' && op2.val == 0) {
                            return new Result("div by 0");
                        }
                        float res = performOp(op1, op, op2);
                        operandStack.push(new Operand(res));
                        result = res;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                i++;
            } else if (isDig(charArray[i])) {
                String num = "0";
                do {
                    num = num.concat("" + charArray[i]);
                    ++i;
                } while (isDig(charArray[i]) || charArray[i] == '.');
                float operand;
                operand = Float.parseFloat(num);
                operandStack.push(new Operand(operand));
            } else {
                if (operatorStack.empty() || operatorStack.peek().val == '(') {
                    operatorStack.push(new Operator(charArray[i]));
                    i++;
                } else {
                    if (operatorStack.peek().getPrior() < Operator.getPriority(charArray[i])) {
                        operatorStack.push(new Operator(charArray[i]));
                        i++;
                    } else {
                        Operator op;
                        try {

                            while ((op = operatorStack.peek()).getPrior() >= Operator.getPriority(charArray[i])) {
                                Operand op2 = operandStack.pop();
                                Operand op1 = operandStack.pop();
                                op = operatorStack.pop();
                                float res;
                                if (op.val == '/' && op2.val == 0) {
                                    return new Result("div by 0");
                                }
                                res = performOp(op1, op, op2);


                                operandStack.push(new Operand(res));


                                result = res;
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        operatorStack.push(new Operator(charArray[i]));
                        i++;
                    }
                }
            }
        }
        return new Result(result);
    }

    static float performOp(Operand op1, Operator op, Operand op2) {

        char oper = op.val;
        float oper1 = op1.val;
        float oper2 = op2.val;

        switch (oper) {

            case '*':
                return oper1 * oper2;
            case '/':
                return oper1 / oper2;
            case '+':
                return oper1 + oper2;
            case '-':
                return oper1 - oper2;
            case '^':
                return (float) Math.pow(oper1, oper2);
            case '%':
                return oper1 % oper2;
        }


        return 0;
    }

    static boolean isDig(char a) {

        if (a >= '0' && a <= '9') return true;

        else return false;

    }

    public static class Result {
        boolean isInvalid;
        String error = "";
        float value;

        public Result(float a) {
            value = a;
            isInvalid = false;
            error = "";
        }

        public Result(String errMsg) {
            isInvalid = true;
            error = errMsg;
            value = -1;
        }

        @Override
        public String toString() {
            if (error.length() != 0) {
                return error;
            } else
                return Float.toString(value);
        }
    }
}