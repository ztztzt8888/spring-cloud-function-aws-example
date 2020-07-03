package com.techprimers.serverless;

import java.util.*;

public class RomanNumeralsCalculator {

    public static void main(String[] args) {
        RomanNumeralsCalculator calculator = new RomanNumeralsCalculator();
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Please type your equation:");
            double result = calculator.calculate(scanner.nextLine());
            if (result % 1 == 0) {
                System.out.println((int) result);
            } else {
                System.out.println(result);
            }
        }
    }

    public double calculate(String romanInput) {
        if (romanInput == null) {
            return 0;
        }
        String intInput = replaceRomanWithInt(romanInput);
        if (intInput.length() == 0) {
            return 0;
        }
        Deque<Character> queue = new ArrayDeque<>();
        boolean previousIsOperator = false;
        boolean isFirstCharacter = true;
        for (char c : intInput.toCharArray()) {
            if (c == ' ') {
                continue;
            }
            boolean currentIsOperator = "+-*/".contains(c + "");
            if (isFirstCharacter && "*/".contains(c + "")) {
                throw new IllegalArgumentException("Leading operator cannot be '*' or '/'.");
            } else if (previousIsOperator && currentIsOperator) {
                throw new IllegalArgumentException("Two operators should not be next to each other.");
            }
            isFirstCharacter = false;
            previousIsOperator = currentIsOperator;
            queue.offer(c);
        }
        queue.offer('+');
        return calculate(queue);
    }

    private double calculate(Deque<Character> queue) {
        char sign = '+';
        double num = 0;
        Deque<Double> stack = new ArrayDeque<>();
        while (!queue.isEmpty()) {
            char c = queue.poll();
            if (Character.isDigit(c)) {
                num = 10 * num + c - '0';
            } else if (c == '(') {
                num = calculate(queue);
            } else {
                if (sign == '+') {
                    stack.push(num);
                } else if (sign == '-') {
                    stack.push(-num);
                } else if (sign == '*') {
                    stack.push(stack.pop() * num);
                } else if (sign == '/') {
                    stack.push(stack.pop() / num);
                }
                num = 0;
                sign = c;
                if (c == ')') {
                    break;
                }
            }
        }
        double sum = 0;
        while (!stack.isEmpty()) {
            sum += stack.pop();
        }
        return sum;
    }

    private String replaceRomanWithInt(String rawInput) {
        StringBuilder sb = new StringBuilder();
        String spacedInput = rawInput
                .replace("+", " + ")
                .replace("-", " - ")
                .replace("*", " * ")
                .replace("/", " / ")
                .replace("(", " ( ")
                .replace(")", " ) ")
                .trim();
        String[] parts = spacedInput.split("\\s+");
        for (String part : parts) {
            if ("+-*/()".contains(part)) {
                sb.append(part);
            } else {
                int romanNum = romanToInt(part);
                if (romanNum >= 4_000) {
                    throw new IllegalArgumentException("Enter a valid Roman Numeral from 1 to 3999");
                }
                sb.append(romanNum);
            }
        }
        return sb.toString();
    }

    private int romanToInt(String s) {
        boolean seenI = false;
        boolean seenV = false;
        boolean seenX = false;
        boolean seenL = false;
        boolean seenC = false;
        boolean seenD = false;
        boolean seenM = false;

        int result = 0;
        char prev = '0';

        for (int i = 0; i < s.length(); i++) {
            char curr = s.charAt(i);

            switch (curr) {
                case 'I':
                    result += 1;
                    seenI = true;
                    break;

                case 'V':
                    result += 5;
                    if (prev == 'I') {
                        result -= 2;
                    }
                    seenV = true;
                    break;

                case 'X':
                    result += 10;
                    if (prev == 'I') {
                        result -= 2;
                    }
                    seenX = true;
                    break;

                case 'L':
                    if (!seenV && !seenI) {
                        result += 50;
                        if (prev == 'X') {
                            result -= 20;
                        }
                        seenL = true;
                        break;
                    }
                    throw new IllegalArgumentException("Invalid Roman Numeral Input");

                case 'C':
                    if (!seenL && !seenV && !seenI) {
                        result += 100;
                        if (prev == 'X') {
                            result -= 20;
                        }
                        seenC = true;
                        break;
                    }
                    throw new IllegalArgumentException("Invalid Roman Numeral Input");

                case 'D':
                    if (!seenL && !seenX && !seenV && !seenI) {
                        result += 500;
                        if (prev == 'C') {
                            result -= 200;
                        }
                        seenD = true;
                        break;
                    }
                    throw new IllegalArgumentException("Invalid Roman Numeral Input");

                case 'M':
                    if (!seenD && !seenL && !seenX && !seenV && !seenI) {
                        result += 1000;
                        if (prev == 'C') {
                            result -= 200;
                        }
                        seenM = true;
                        break;
                    }
                    throw new IllegalArgumentException("Invalid Roman Numeral Input");
            }

            prev = curr;
        }

        return result;
    }
}
