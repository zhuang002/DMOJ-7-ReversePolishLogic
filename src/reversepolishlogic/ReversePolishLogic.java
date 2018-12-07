/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reversepolishlogic;

import static java.lang.Math.round;
import java.util.Scanner;
import java.util.Stack;

/**
 *
 * @author zhuan
 */
public class ReversePolishLogic {

    /**
     * @param args the command line arguments
     */
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        // TODO code application logic here
        for (int i = 0; i < 5; i++) {
            String line = sc.nextLine();
            String exp = translateRPL(line);
            //if (i==1) System.out.println(line);
            //else 
                System.out.println(exp);
        }
    }

    private static String translateRPL(String line) {
        Stack<Double> data = new Stack();
        Stack<String> exps = new Stack();
        Stack<Character> ops = new Stack();
        String tocken = "";
        String exp = "";
        for (int i = 0; i < line.length(); i++) {
            Character c = line.charAt(i);
            if (Character.isDigit(c) || c == '.') {
                tocken += c;
            } else {
                if (!tocken.isEmpty()) {
                    double n = Double.parseDouble(tocken);
                    data.push(n);
                    exps.push(tocken);
                    ops.push('M');
                    tocken = "";
                }
                if (c != 'E') {
                    
                    String exp1 = exps.pop();
                    String exp2 = exps.pop();
                    Character op1 = 'M';
                    if (!ops.isEmpty()) {
                        op1 = ops.pop();
                    }
                    Character op2 = 'M';
                    if (!ops.isEmpty()) {
                        op2 = ops.pop();
                    }
                    if (priority(c) > priority(op1)) {
                        exp1 = "(" + exp1 + ")";
                    }
                    if (priority(c) > priority(op2)) {
                        if (!(c=='/' && op2=='*' || c=='-' && op2=='+'))
                            exp2 = "(" + exp2 + ")";
                    }
                    exp = exp2 + " " + c + " " + exp1;
                    exps.push(exp);
                    ops.push(c);

                    double data1 = data.pop();
                    double data2 = data.pop();
                    double n = calculate(c, data2, data1);
                    data.push(n);
                }
            }
        }
        exp = exps.pop() + " = " + myRound(data.pop());
        return exp;
    }

    private static int priority(Character c) {
        switch (c) {
            case '+':
                return 1;
            case '-':
                return 2;
            case '*':
                return 3;
            case '/':
                return 4;
            case '!':
                return 5;
        }
        return Integer.MAX_VALUE;
    }

    private static double calculate(Character c, double d1, double d2) {
        switch (c) {
            case '+':
                return d1 + d2;
            case '-':
                return d1 - d2;
            case '*':
                return d1 * d2;
            case '/':
                return d1 / d2;
            case '!':
                return Math.pow(d1,d2);
        }
        return -1;
    }

    private static String myRound(Double d) {
        Double pd = Math.abs(d);
        String sd = pd.toString();
        int length = sd.length();
        int dot = sd.lastIndexOf(".");
        Double compensate = Math.pow(10, -(length - dot-1));
        Character c = sd.charAt(length - 1);

        if (c == '9' || c == '1') {
            Double tmp = 0.0;
            if (sd.charAt(length - 1) == '9') {
                tmp = pd + compensate;

            } else if (sd.charAt(length - 1) == '1') {
                tmp = pd - compensate;

            }
            sd = tmp.toString();
            if (length - sd.length() > 5) {
                pd = tmp;
            }
        }
        
            
        if (d < 0) {
            pd = -pd;
        }
        sd=pd.toString();
        if (sd.lastIndexOf('.')==sd.length()-2 && sd.charAt(sd.length()-1)=='0')
            sd=sd.substring(0,sd.length()-2);
        return sd;
    }

}
