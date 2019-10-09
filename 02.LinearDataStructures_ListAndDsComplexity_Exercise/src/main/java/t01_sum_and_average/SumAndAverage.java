package t01_sum_and_average;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class SumAndAverage {
    public static void main(String[] args) throws IOException {
        BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));
        final List<Integer> inputNumbers = Arrays.stream(consoleReader.readLine()
                .split("\\s+"))
                .filter(e -> !"".equals(e))
                .map(Integer::parseInt)
                .collect(Collectors.toList());
        consoleReader.close();

        System.out.printf("Sum=%d; Average=%.2f", getSum(inputNumbers), getAverage(inputNumbers));
    }

    private static double getAverage(Collection<Integer> numbers) {
        if (numbers.size() == 0)
            return 0;

        return getSum(numbers) / (double)numbers.size();
    }

    private static int getSum(Collection<Integer> numbers) {
        int sum = 0;
        for (Integer number : numbers) {
            sum += number;
        }

        return sum;
    }
}
