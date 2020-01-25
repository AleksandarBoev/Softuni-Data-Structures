package exercise.tasks;

import lab.HashTable;
import lab.KeyValue;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class P01CountSymbols {
    public static void main(String[] args) throws IOException {
        BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));
        String input = consoleReader.readLine();
        consoleReader.close();

        HashTable<String, Integer> symbolsCount = new HashTable<>(input.length());
        for (int i = 0; i < input.length(); i++) {
            KeyValue<String, Integer> symbolCountFound = symbolsCount.find("" + input.charAt(i));

            if (symbolCountFound == null) {
                symbolsCount.add("" + input.charAt(i), 1);
            } else {
                symbolCountFound.setValue(symbolCountFound.getValue() + 1);
            }
        }

        StringBuilder output = new StringBuilder();
        for (KeyValue<String, Integer> currentSymbolCount : symbolsCount) {
            output.append(currentSymbolCount.getKey()) //"<symbol>: <count> time/s"
                    .append(": ")
                    .append(currentSymbolCount.getValue())
                    .append(" time/s")
                    .append(System.lineSeparator());
        }

        System.out.println(output.toString());
    }
}
