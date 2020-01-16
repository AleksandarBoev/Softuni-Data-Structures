import text_editor.TextEditor;
import text_editor.TextEditorImpl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws IOException {
//        TextEditor textEditor = new TextEditorImpl();
//
//        BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));
//
//        String input;
//        while (!"end".equals(input = consoleReader.readLine())) {
//            String[] tokens = input.split(" ");
//            int index1 = input.indexOf('"') + 1;
//            int index2 = input.lastIndexOf('"');
//            String string = "";
//            if (index2 != -1) {
//                string = input.substring(index1, index2);
//            }
//
//            if (tokens[0].equals("login")) {
//                textEditor.login(tokens[1]);
//            } else if (tokens[0].equals("logout")) {
//                textEditor.logout(tokens[1]);
//            } else if (tokens[0].equals("users")) {
//                if (tokens.length == 2) {
//                    printIterable(textEditor.users(tokens[1]));
//                } else {
//                    printIterable(textEditor.users(""));
//                }
//            } else {
//                String username = tokens[0];
//                String action = tokens[1];
//
//                switch (action) {
//                    //pesho substring 0 4
//                    case "substring":
//                        textEditor.substring(username, Integer.parseInt(tokens[2]), Integer.parseInt(tokens[3]));
//                        break;
//
//                    case "length":
//                        System.out.println(textEditor.length(username));
//                        break;
//
//                    case "print":
//                        System.out.println(textEditor.print(username));
//                        break;
//
//                    case "undo":
//                        textEditor.undo(username);
//                        break;
//
//                    case "insert":
//                        textEditor.insert(username, Integer.parseInt(tokens[2]), string);
//                        break;
//
//                    case "prepend":
//                        textEditor.prepend(username, string);
//                        break;
//
//                    case "clear":
//                        textEditor.clear(username);
//                        break;
//
//                    case "delete":
//                        textEditor.delete(username, Integer.parseInt(tokens[2]), Integer.parseInt(tokens[3]));
//                        break;
//                }
//            }
//        }
//
//        consoleReader.close();
    }

    public static void printIterable(Iterable<String> iterable) {
        iterable.forEach(System.out::println);
    }
}
