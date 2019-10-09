package t03_longest_subsequence;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class LongestSubsequence {
    public static void main(String[] args) throws IOException {
        BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));
        List<Integer> inputNumbers = Arrays.stream(consoleReader.readLine().split("\\s+"))
                .map(Integer::parseInt)
                .collect(Collectors.toList());
        consoleReader.close();

        String result = longestSubSequence(inputNumbers)
                .stream()
                .map(Object::toString)
                .collect(Collectors.joining(" "));

        System.out.println(result);
    }

    public static List<Integer> longestSubSequence(List<Integer> numbers) {
        List<Integer> result = new ArrayList<>();
        if (numbers.isEmpty())
            return result;

        int biggestSubsequenceCount = 1;
        int biggestSubsequenceNumber = numbers.get(0);
        int currentSubsequenceCount = 1;

        for (int i = 1; i < numbers.size(); i++) {
            if (numbers.get(i).equals(numbers.get(i - 1)))
                currentSubsequenceCount++;
             else
                currentSubsequenceCount = 1;

            if (biggestSubsequenceCount < currentSubsequenceCount) {
                biggestSubsequenceCount = currentSubsequenceCount;
                biggestSubsequenceNumber = numbers.get(i - 1);
            }
        }

        for (int i = 0; i < biggestSubsequenceCount; i++) {
            result.add(biggestSubsequenceNumber);
        }

        return result;
    }
}




