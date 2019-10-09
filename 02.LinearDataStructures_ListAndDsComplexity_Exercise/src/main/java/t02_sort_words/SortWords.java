package t02_sort_words;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class SortWords {
    public static void main(String[] args) throws IOException {
        BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));
        List<String> inputStrings = Arrays.stream(consoleReader.readLine()
                .split("\\s+"))
                .sorted(String::compareTo)
                .collect(Collectors.toList());
        consoleReader.close();

        System.out.println(String.join(" ", inputStrings));
    }
}
