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
        /*this.root = */this.insert(this.root, key, value, 0); //"this.root = ..." is provided in skeleton but is kind of useless imo
    }

    public Iterable<String> getByPrefix(String prefix) { // like autocomplete. Returns all words which start with given prefix
        Deque<String> result = new LinkedList<>();
        Node node = this.getNode(this.root, prefix, 0);

        this.collect(node, prefix, result);
        return reverseCollection(result);
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
}
