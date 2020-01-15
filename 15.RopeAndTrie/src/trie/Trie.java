package trie;

import java.util.*;

public class Trie<Value> {
    private Node root;

    public Trie() {
        this.root = new Node(); // isTerminal by default is false and value is null
    }

    public Value getValue(String key) { // returns the value of the terminal node of the string key
        Node result = this.getNode(this.root, key, 0);

        if (result == null || !result.isTerminal()) {
            throw new IllegalArgumentException();
        }

        return result.getValue();
    }

    public boolean contains(String key) {
        Node node = this.getNode(this.root, key, 0);
        return node != null && node.isTerminal();
    }

    public void insert(String key, Value value) { // like inserting a kvp into a map
        /*this.root = */
        this.insert(this.root, key, value, 0); //"this.root = ..." is provided in skeleton but is kind of useless imo
    }

    public Iterable<String> getByPrefix(String prefix) { // like autocomplete. Returns all words which start with given prefix
        Deque<String> result = new LinkedList<>();
        Node node = this.getNode(this.root, prefix, 0);

        this.collect(node, prefix, result);
        return reverseCollection(result);
    }

    public void remove(String key) {
        remove(root, key, 0, new BooleanWrapper());
    }

    private void collect(Node node, String prefix, Deque<String> result) {
        if (node == null) {
            return;
        }

        if (node.getValue() != null && node.isTerminal()) {
            result.addFirst(prefix);
        }

        for (Character c : node.getNext().keySet()) {
            this.collect(node.getNext().get(c), prefix + c, result);
        }
    }

    private Node insert(Node node, String key, Value value, int v) { // returns Node provided by skeleton, but not needed imo
        if (key.length() == v) {
            node.value = value;
            node.setTerminal(true);
            return node;
        }

        Character character = key.charAt(v);

        Node nodeCharacterFound = node.getNext().get(character);

        if (nodeCharacterFound == null) { // character not found
            nodeCharacterFound = new Node();
            node.getNext().put(character, nodeCharacterFound);
        }

        return insert(nodeCharacterFound, key, value, v + 1);
    }

    private Node getNode(Node node, String key, int v) { // returns terminal node of searched word (or null)
        if (node == null) {
            return null;
        }

        if (v == key.length()) {
            return node;
        }

        char c = key.charAt(v);
        return this.getNode(node.getNext().get(c), key, v + 1); //map.get(c) --> returns null if no such key is found
    }

    private Iterable<String> reverseCollection(Deque<String> collection) {
        ArrayList<String> result = new ArrayList<>();

        Iterator<String> iterator = collection.descendingIterator();
        while (iterator.hasNext()) {
            result.add(iterator.next());
        }

        return result;
    }

    /*
   To delete a key, every character along the way needs to be deleted. And since the nodes are stored
   in a Map<Character, Node> then they need to be deleted from those maps.

   A node should NOT be deleted in these cases:
   1) It it has children characters. That means another key is using the current character and all its parent characters.
   Example 1: "Pesho", "Peshoslav". "Pesho" is to be deleted. But all its letters are included in another key. Only thing to
   do is make the "o" non-terminal.
   Example 2: "Pesho", "Petar". "Pesho" is to be deleted. "o", "h", "s" are deleted, but "e" has more than 1 child character,
   which means it is included in another key. So the deletion should stop.
   2) In the process of deletion of all the characters, a terminal node is found. It should NOT be deleted.
   Example: "Pesho", "Pe". "Pesho" is to be deleted. "o", "h", "s" should be removed, but "e" should stay, because it is
   part of a different key.
    */
    private void remove(Node node, String key, int v, BooleanWrapper delete) {
        if (v == key.length() && node.isTerminal) { // last symbol of key to be deleted is found
            node.setTerminal(false);

            if (node.getNext().isEmpty()) {
                delete.setValue(true);
            }

            return;
        }

        char c = key.charAt(v);
        remove(node.getNext().get(c), key, v + 1, delete);

        if (delete.getValue() == true) {
            if (node.getNext().size() == 1 && !node.getNext().get(c).isTerminal()) { //if there is only one next element and it is not terminal
                node.getNext().remove(c);
            } else if (!node.getNext().get(c).isTerminal()) { //current letter has more than one children --> delete the specified child and stop deletion process
                node.getNext().remove(c);
                delete.setValue(false);
            } else {
                delete.setValue(false); // stop recursive deletion process
            }

            return;
        }
    }

    private class Node {
        private Value value;
        private boolean isTerminal;
        private Map<Character, Node> next;

        public Node() {
            this.next = new LinkedHashMap<>();
        }

        public Value getValue() {
            return this.value;
        }

        public void setValue(Value value) {
            this.value = value;
        }

        public boolean isTerminal() {
            return isTerminal;
        }

        public void setTerminal(boolean terminal) {
            this.isTerminal = terminal;
        }

        public Map<Character, Node> getNext() {
            return this.next;
        }
    }

    /**
     * Only purpose is to pass boolean by reference in the recursive calls in "delete" method.
     */
    private class BooleanWrapper {
        Boolean[] booleanObject;

        public BooleanWrapper() {
            booleanObject = new Boolean[1];
            booleanObject[0] = false;
        }

        public void setValue(Boolean value) {
            booleanObject[0] = value;
        }

        public Boolean getValue() {
            return booleanObject[0];
        }

        @Override
        public String toString() {
            return "" + getValue();
        }
    }
}
