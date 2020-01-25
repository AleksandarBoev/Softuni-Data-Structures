package exercise.tasks;

import lab.HashTable;
import lab.KeyValue;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class P02PhoneBook {
    public static void main(String[] args) throws IOException {
        HashTable<String, String> namesContacts = new HashTable<>();

        BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));
        String input;
        while (!(input = consoleReader.readLine()).equals("search")) {
            String[] inputTokens = input.split("-");

            namesContacts.addOrReplace(inputTokens[0], inputTokens[1]);
        }

        while (!(input = consoleReader.readLine()).equals("")) {
            KeyValue<String, String> contactFound = namesContacts.find(input);

            if (contactFound == null) {
                System.out.printf("Contact %s does not exist.%n", input);
            } else {
                System.out.println(contactFound);
            }
        }
        consoleReader.close();
    }
}
