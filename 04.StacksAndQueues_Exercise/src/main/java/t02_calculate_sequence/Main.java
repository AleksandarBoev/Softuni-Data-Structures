package t02_calculate_sequence;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;

public class Main {
    public static void main(String[] args) throws IOException {
        ArrayDeque<Integer> queueCalculations = new ArrayDeque<>();

        BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));
        Integer inputNumber = Integer.parseInt(consoleReader.readLine());
        consoleReader.close();

        StringBuilder result = new StringBuilder();
        queueCalculations.addLast(inputNumber);

        int i;
        for (i = 0; i < 50 && queueCalculations.size() < 50; i++) {
            Integer polledNumber = queueCalculations.poll();
            result.append(polledNumber).append(", ");
            queueCalculations.addLast(polledNumber + 1);
            queueCalculations.addLast(2 * polledNumber + 1);
            queueCalculations.addLast(polledNumber + 2);
        }

        for (; i < 50; i++) {
            result.append(queueCalculations.poll()).append(", ");
        }

        System.out.println(result.toString().substring(0, result.lastIndexOf(", ")));
    }
}
