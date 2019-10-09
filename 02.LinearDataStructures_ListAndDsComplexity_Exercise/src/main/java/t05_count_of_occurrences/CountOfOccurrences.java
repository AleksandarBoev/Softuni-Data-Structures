package t05_count_of_occurrences;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;

public class CountOfOccurrences {
    public static void main(String[] args) throws IOException {
        BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));
        List<Integer> inputNumbers = Arrays.stream(consoleReader.readLine().split("\\s+"))
                .map(Integer::parseInt)
                .collect(Collectors.toCollection(ArrayList::new));
        consoleReader.close();

        Map<Integer, Integer> sortedNumbersAndOccurrences =
                getNumberOfOccurrencesSorted(inputNumbers);

        System.out.println(getFormattedResult(sortedNumbersAndOccurrences));
    }

    public static TreeMap<Integer, Integer> getNumberOfOccurrencesSorted(Collection<Integer> numbers) {
        TreeMap<Integer, Integer> result = new TreeMap<>();

        for (Integer number: numbers) {
            if (!result.containsKey(number))
                result.put(number, 0);

            result.put(number, result.get(number) + 1);
        }
        return result;
    }

    public static String getFormattedResult(Map<Integer, Integer> map) {
        StringBuilder sb = new StringBuilder();
        for (Integer number: map.keySet())
            sb.append(number).append(" -> ").append(map.get(number)).append(" times").append(System.lineSeparator());

        return sb.toString().trim();
    }
}
