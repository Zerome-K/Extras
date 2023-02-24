package games.fourte;

import java.util.ArrayList;
import java.util.Stack;

public class Calculator {
    private Double calculator(String expression) {
        Stack<Double> numbers = new Stack<>();
        Stack<Character> operators = new Stack<>();
        for (int i = 0; i < expression.length(); i++) {
            char ascii = expression.charAt(i);
            if (ascii == ' ') continue;
            if (ascii == '(') operators.push(ascii);
            else if (ascii == '+' || ascii == '-' || ascii == '*' || ascii == '/' || ascii == '^') {
                if (operators.isEmpty() || operators.peek() == '(') operators.push(ascii);
                else {
                    while (!operators.isEmpty() && precedence(operators.peek()) >= precedence(ascii)) {
                        numbers.push(eval(numbers.pop(), numbers.pop(), operators.pop()));
                    }
                    operators.push(ascii);
                }
            } else if (ascii == ')') {
                while (!operators.isEmpty() && operators.peek() != '(') {
                    numbers.push(eval(numbers.pop(), numbers.pop(), operators.pop()));
                }
                operators.pop();
            } else numbers.push((double) (ascii - '0'));
        }

        while (!operators.isEmpty()) {
            numbers.push(eval(numbers.pop(), numbers.pop(), operators.pop()));
        }
        return numbers.pop();
    }

    private Double eval(Double a, Double b, Character operator) {
        switch (operator) {
            case '+' -> {
                return (double) b + a;
            }
            case '-' -> {
                return (double) b - a;
            }
            case '*' -> {
                return (double) b * a;
            }
            case '^' -> {
                return (double) b * b;
            }
            default -> {
                if (a == 0) return 0.0;
                return (double) b / a;
            }
        }
    }

    private int precedence(Character operator) {
        switch (operator) {
            case '^' -> {
                return 4;
            }
            case '*', '/', '%' -> {
                return 3;
            }
            case '+', '-' -> {
                return 2;
            }
            default -> {
                return 1;
            }
        }
    }

    public String makeExpress(int[] numbers, ArrayList<Character> operators, int target) {
        int size = operators.size();
        if (size == 3) {
            return exp3(numbers, operators, target);
        } else if (size == 2) {
            return exp2(numbers, operators, target);
        } else if (size == 1) return exp1(numbers, operators, target);
        return numbers[0] == target ? numbers[0] + "" : "";
    }

    private String exp1(int[] numbers, ArrayList<Character> operators, int target) {
        double exp = eval((double) numbers[0], (double) numbers[1], operators.get(0));
        if (exp == target) return numbers[0] + "" + operators.get(0) + "" + numbers[1];
        return "";
    }

    private String exp2(int[] numbers, ArrayList<Character> operators, int target) {
        double exp1 = eval(eval((double) numbers[2], (double) numbers[1], operators.get(1)), (double) numbers[0], operators.get(0));
        if (exp1 == target)
            return numbers[0] + "" + operators.get(0) + "(" + numbers[1] + "" + operators.get(1) + "" + numbers[2] + ")";
        exp1 = eval((double) numbers[2], eval((double) numbers[1], (double) numbers[0], operators.get(0)), operators.get(1));
        if (exp1 == target)
            return "(" + numbers[0] + "" + operators.get(0) + numbers[1] + ")" + operators.get(1) + numbers[2];
        return "";
    }

    private String exp3(int[] numbers, ArrayList<Character> operators, int target) {
        String expression = numbers[0] + "" + operators.get(0) + "" + numbers[1] + "" + operators.get(1) + "" + numbers[2] + "" + operators.get(2) + "" + numbers[3];
        if (calculator(expression) == target) return expression;
        expression = "(" + numbers[0] + operators.get(0) + numbers[1] + ")" + operators.get(1) + "(" + numbers[2] + operators.get(2) + numbers[3] + ")";
        if (calculator(expression) == target) return expression;
        expression = "(" + "(" + numbers[0] + operators.get(0) + numbers[1] + ")" + operators.get(1) + numbers[2] + ")" + operators.get(2) + numbers[3];
        if (calculator(expression) == target) return expression;
        expression = numbers[0] + "" + operators.get(0) + "(" + numbers[1] + operators.get(1) + "(" + numbers[2] + operators.get(2) + numbers[3] + ")" + ")";
        if (calculator(expression) == target) return expression;
        expression = numbers[0] + "" + operators.get(0) + "(" + numbers[1] + operators.get(1) + numbers[2] + ")" + operators.get(2) + numbers[3];
        if (calculator(expression) == target) return expression;
        expression = "(" + numbers[0] + operators.get(0) + numbers[1] + operators.get(1) + numbers[2] + ")" + operators.get(2) + numbers[3];
        if (calculator(expression) == target) return expression;
        expression = numbers[0] + "" + operators.get(0) + "(" + numbers[1] + operators.get(1) + numbers[2] + operators.get(2) + numbers[3] + ")";
        if (calculator(expression) == target) return expression;
        return "";
    }
}
