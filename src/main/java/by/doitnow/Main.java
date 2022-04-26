package by.doitnow;

import by.doitnow.ownexception.*;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        System.out.print("Input: ");
        String input = scan.nextLine();
        calc(input);
    }

    public static void calc(String s) {
        s = s.replaceAll("\\s+", "");
        char sign;

        try {
            sign = opSign(s);
        } catch (NonSutableOpFormat nonSutableOpFormat) {
            System.out.println("throws Exception //т.к. формат математической операции не удовлетворяет заданию - два операнда и один оператор (+, -, /, *)");
            return;
        }

        String[] expr;
        if (sign == '+' || sign == '*') {
            String tmp = "\\";
            expr = s.split(tmp.concat(String.valueOf(sign)));
        } else {
            expr = s.split(String.valueOf(sign));
        }

        String arg1 = expr[0];
        String arg2 = expr[1];

        if (isRome(arg1) && isRome(arg2)) {
            try {
                RomeNum res = calcRome(arg1, arg2, sign);
                System.out.println("Output: " + res);
            } catch (UnsuitableNumber unsuitableNumber) {
                System.out.println("Non correct Number Format. Enter 2 numbers and operation(+,-,*,/) and  a>=I,a<=X,b>=I,b<=X");

            } catch (NegativeRomeResult negativeRomeResult) {
                System.out.println("Rome Number can not be Negative");
            }
            return;
        }
        if (isRome(arg1) && isArabic(arg2) || isArabic(arg1) && isRome(arg2)) {
            try {
                throw new UsedTwoDifferentSystem();
            } catch (UsedTwoDifferentSystem ex) {
                System.out.println("throws Exception //т.к. используются одновременно разные системы счисления");
                return;
            }
        } else {
            int result;
            try {
                result = calcArabic(arg1, arg2, sign);
                System.out.println("Output: " + result);
            } catch (UnsuitableNumber ex) {
                System.out.println("Non correct Number Format. Enter 2 numbers and operation(+,-,*,/) and  a>=1,a<=10,b>=1,b<=10");
                return;
            }
        }
    }

    private static boolean isArabic(String arg) {
        try {
            Integer.parseInt(arg);
        } catch (IllegalArgumentException e) {
            return false;
        }
        return true;
    }

    private static boolean isRome(String arg1) {
        try {
            if (RomeNum.valueOf(arg1) instanceof RomeNum) {
            }
        } catch (IllegalArgumentException e) {
            return false;
        }
        return true;

    }

    private static int calcArabic(String arg1, String arg2, char sign) throws UnsuitableNumber {
        int a, b;
        try {
            a = Integer.parseInt(arg1);
            b = Integer.parseInt(arg2);
        } catch (NumberFormatException e) {
            throw new UnsuitableNumber();
        }
        if (a > 10 || b > 10 || a < 1 || b < 1) {
            throw new UnsuitableNumber();
        }
        return calc(a, b, sign);
    }

    private static RomeNum calcRome(String arg1, String arg2, char sign) throws UnsuitableNumber, NegativeRomeResult {
        int a = RomeNum.valueOf(arg1).ordinal() + 1;
        int b = RomeNum.valueOf(arg2).ordinal() + 1;

        if (a > 10 || b > 10 || a < 1 || b < 1) {
            throw new UnsuitableNumber();
        }
        int result = calc(a, b, sign);
        if (result <= 0) {
            throw new NegativeRomeResult();
        }
        return (RomeNum.values()[result - 1]);
    }

    private static int calc(int a, int b, char sign) {
        int result = -100;
        switch (sign) {
            case '+':
                break;
            case '-':
                result = a - b;
                break;
            case '*':
                result = a * b;
                break;
            case '/':
                result = a / b;
                break;
        }
        return result;
    }


    private static char opSign(String s) throws NonSutableOpFormat {
        String sign = "+-*/";
        char result = 'e';//code of error
        int count = 0;
        char tmp;
        for (int i = 0; i < s.length(); i++) {
            tmp = s.charAt(i);
            if (sign.contains(String.valueOf(tmp))) {
                count++;
                result = tmp;
            }
        }
        if (count > 1 || result == 'e') {
            throw new NonSutableOpFormat();
        }
        return result;
    }
}