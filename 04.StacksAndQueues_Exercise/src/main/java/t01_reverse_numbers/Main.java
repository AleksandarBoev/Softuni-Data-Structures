package t01_reverse_numbers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;

public class Main {
    public static void main(String[] args) throws IOException {
        ArrayDeque<String> stack = new ArrayDeque<>();

        BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));
        for (String token : consoleReader.readLine().split("\\s+")) {
            stack.addFirst(token);
        }
        consoleReader.close();

        StringBuilder result = new StringBuilder();
        while (!stack.isEmpty()) {
            result.append(stack.pop()).append(" ");
        }

        System.out.println(result.toString().trim());
    }
}
