package t04_remove_odd_occurences;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;

public class RemoveOddOccurrences {
    public static void main(String[] args) throws IOException {
        BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));
        List<Integer> inputNumbers = Arrays.stream(consoleReader.readLine().split("\\s+"))
                .map(Integer::parseInt)
                .collect(Collectors.toCollection(LinkedList::new));
        consoleReader.close();

        removeOddOccurrences(inputNumbers);
        System.out.println(inputNumbers
                .stream()
                .map(Object::toString)
                .collect(Collectors.joining(" ")));
    }

    public static void removeOddOccurrences(List<Integer> numbers) {
        Map<Integer, Integer> numberOccurrences = new HashMap<>();
        for (Integer number: numbers) {
            if (!numberOccurrences.containsKey(number))
                numberOccurrences.put(number, 0);

            numberOccurrences.put(number, numberOccurrences.get(number) + 1);
        }

        numberOccurrences.forEach((number, occurences) -> {
            if (occurences % 2 == 1) {
                while (numbers.contains(number)) {
                    numbers.remove(number);
                }
            }
        });
    }
}
