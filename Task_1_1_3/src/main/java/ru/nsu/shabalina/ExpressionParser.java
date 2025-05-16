package ru.nsu.shabalina;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class ExpressionParser {
    private ExpressionParser() {}

    public static int getPrecedence(char op) {
        return switch (op) {
            case '*', '/' -> 2;
            case '+', '-' -> 1;
            default -> 0;
        };
    }

    public static List<String> convertToRPN(String input) {
        Deque<Character> operators = new ArrayDeque<>();
        List<String> output = new ArrayList<>();
        input = input.replaceAll(" ", "");

        for (int i = 0; i < input.length(); i++) {
            char current = input.charAt(i);

            if (current == '(') {
                operators.push(current);
            }
            else if (Character.isDigit(current)) {
                StringBuilder number = new StringBuilder();
                while (i < input.length() && Character.isDigit(input.charAt(i))) {
                    number.append(input.charAt(i++));
                }
                i--;
                output.add(number.toString());
            }
            else if (current == ')') {
                while (!operators.isEmpty() && operators.peek() != '(') {
                    output.add(Character.toString(operators.pop()));
                }
                operators.pop();
            }
            else if (isOperator(current)) {
                while (!operators.isEmpty() &&
                        getPrecedence(operators.peek()) >= getPrecedence(current)) {
                    output.add(Character.toString(operators.pop()));
                }
                operators.push(current);
            }
            else if (Character.isLetter(current)) {
                StringBuilder variable = new StringBuilder();
                while (i < input.length() && Character.isLetter(input.charAt(i))) {
                    variable.append(input.charAt(i++));
                }
                i--;
                output.add(variable.toString());
            }
        }

        while (!operators.isEmpty()) {
            output.add(Character.toString(operators.pop()));
        }

        return output;
    }

    public static MathExpression parseExpression(String input) {
        List<String> rpn = convertToRPN(input);
        Deque<MathExpression> stack = new ArrayDeque<>();

        for (String token : rpn) {
            if (Character.isDigit(token.charAt(0))) {
                stack.push(new Constant(Integer.parseInt(token)));
            }
            else if (isOperator(token.charAt(0))) {
                MathExpression right = stack.pop();
                MathExpression left = stack.pop();
                stack.push(createExpression(token.charAt(0), left, right));
            }
            else {
                stack.push(new VariableExpression(token));
            }
        }

        return stack.pop();
    }

    public static Map<String, Integer> parseVariables(String input) {
        Map<String, Integer> variables = new HashMap<>();
        input = input.replaceAll(" ", "");

        try {
            String[] assignments = input.split(";");
            for (String assignment : assignments) {
                String[] parts = assignment.split("=");
                if (parts.length == 2) {
                    variables.put(parts[0], Integer.parseInt(parts[1]));
                }
            }
        } catch (NumberFormatException e) {
            System.err.println("Invalid variable format");
            return null;
        }

        return variables;
    }

    private static boolean isOperator(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/';
    }

    private static MathExpression createExpression(char op, MathExpression left, MathExpression right) {
        return switch (op) {
            case '+' -> new Sum(left, right);
            case '-' -> new Difference(left, right);
            case '*' -> new Product(left, right);
            case '/' -> new Quotient(left, right);
            default -> throw new IllegalArgumentException("Unknown operator: " + op);
        };
    }
}

