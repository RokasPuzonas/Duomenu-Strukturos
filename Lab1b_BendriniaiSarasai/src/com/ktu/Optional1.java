package com.ktu;

import util.ArrayStack;

import java.io.BufferedReader;
import java.io.Console;
import java.io.IOException;
import java.io.InputStreamReader;

public class Optional1 {
    public static void main(String... args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            String line = reader.readLine();
            boolean result = check(line);
            System.out.println(result);
        }
    }

    public static boolean check(String line) {
        ArrayStack<Character> stack = new ArrayStack<>();

        for (char c : line.toCharArray()) {
            if (c == '(' || c == '[' || c == '{') {
                stack.push(c);
            } else if (c == ')' || c == ']' || c == '}') {
                char opening_c = stack.pop();
                if (!((opening_c == '(' && c == ')') ||
                        (opening_c == '[' && c == ']') ||
                        (opening_c == '{' && c == '}'))) {
                    return false;
                }
            }
        }

        return stack.isEmpty();
    }
}
