package text_editor;

import trie.Trie;

import java.util.ArrayDeque;
import java.util.Deque;

public class TextEditorImpl implements TextEditor {
    public final static String INVALID_USERNAME_MESSAGE = "Username can't be an empty string or be null!";

    private Trie<Deque<String>> userStringVersions;

    public TextEditorImpl() {
        userStringVersions = new Trie<>();
    }

    @Override
    public void login(String username) {
        if ("".equals(username) || username == null) {
            throw new IllegalArgumentException(INVALID_USERNAME_MESSAGE);
        }

        Deque<String> stringVersions = new ArrayDeque<>();
        stringVersions.addFirst(""); //TODO is this needed?
        userStringVersions.insert(username, stringVersions);
    }

    /*
    Judging by the description of the task, logout should delete the user entirely.
     */
    @Override
    public void logout(String username) {
        if (!userStringVersions.contains(username)) {
            return;
        }

        userStringVersions.remove(username);
    }

    /*
    Inserts at beginning of string
     */
    @Override
    public void prepend(String username, String string) {
        Deque<String> stringVersionsFound = userStringVersions.getValue(username);

        if (stringVersionsFound == null) {
            return;
        }

        String newString = stringVersionsFound.peek() + string;
        stringVersionsFound.addFirst(newString);
    }

    @Override
    public void insert(String username, int index, String string) {
        Deque<String> stringVersionsFound = userStringVersions.getValue(username);

        if (stringVersionsFound == null) {
            return;
        }

        String currentString = stringVersionsFound.peek();
        stringVersionsFound.addFirst(currentString.substring(0, index) + string + currentString.substring(index));
    }

    /*
    Changes the string of the given user to a part of itself.
     */
    @Override
    public void substring(String username, int startIndex, int length) {
        Deque<String> stringVersionsFound = userStringVersions.getValue(username);

        if (stringVersionsFound == null) {
            return;
        }

        stringVersionsFound.addFirst(stringVersionsFound.peek().substring(startIndex, startIndex + length));
    }

    @Override
    public void delete(String username, int startIndex, int length) {
        Deque<String> stringVersionsFound = userStringVersions.getValue(username);

        if (stringVersionsFound == null) {
            return;
        }

        String currentString = stringVersionsFound.peek();
        stringVersionsFound.addFirst(currentString.substring(0, startIndex) + currentString.substring(startIndex + length));
    }

    @Override
    public void clear(String username) {
        Deque<String> stringVersionsFound = userStringVersions.getValue(username);

        if (stringVersionsFound == null) {
            return;
        }

        stringVersionsFound.addFirst("");
    }

    @Override
    public int length(String username) {
        return print(username).length();
    }

    @Override
    public String print(String username) {
        Deque<String> stringVersions = userStringVersions.getValue(username);

        if (stringVersions == null) {
            return "";
        } else {
            return stringVersions.peekFirst();
        }
    }

    /*
    Sets the user string to previous state
     */
    @Override
    public void undo(String username) {
        userStringVersions.getValue(username).removeFirst();
    }

    @Override
    public Iterable<String> users(String prefix) {
        if (prefix == null) {
            return null;
        }

        return userStringVersions.getByPrefix(prefix);
    }
}
