import java.util.ArrayDeque;
import java.util.Deque;
import java.util.function.Consumer;

public class BinarySearchTree<T extends Comparable<T>> {
    private Node root;

    public BinarySearchTree() {
    }

    private BinarySearchTree(Node root) {
        this.root = new Node(root.value);
    }

    public Node getRoot() {
        return this.root;
    }

    public void insert(T value) {
        if (root == null) {
            root = new Node(value);
            return;
        }

        Node current = root;
        Node parentNode = null;

        while (current != null) {
            parentNode = current;

            if (value.compareTo(current.value) < 0) {
                current = current.left;
            } else if (value.compareTo(current.value) > 0) {
                current = current.right;
            } else {
                return; // if value is already present in data structure, do not add it
            }
        }

        if (value.compareTo(parentNode.value) < 0) {
            parentNode.left = new Node(value);
        } else {
            parentNode.right = new Node(value);
        }
    }

    public boolean contains(T value) {
        return getNode(value) != null;
    }

    public BinarySearchTree<T> search(T item) {
        Node nodeFound = getNode(item);
        if (nodeFound == null) {
            return null;
        }

        BinarySearchTree<T> result = new BinarySearchTree<>();
        result.copy(nodeFound);
        return result;
    }


    /**
     * Does action onto tree following Inorder traversal (Left -> Root -> Right). This means
     * the action will be executed on sorted, ascending elements.
     *
     * @param consumer
     */
    public void eachInOrder(Consumer<T> consumer) {
        inOrder(root, consumer);
    }

    public Iterable<T> range(T from, T to) {
        Deque<T> queue = new ArrayDeque<>();

        range(from, to, queue, root);

        return queue;
    }

    private Node getNode(T value) {
        Node current = root;
        while (current != null) {
            if (value.compareTo(current.value) < 0) {
                current = current.left;
            } else if (value.compareTo(current.value) > 0) {
                current = current.right;
            } else {
                return current;
            }
        }

        return null;
    }

    private void copy(Node node) {
        if (node == null) {
            return;
        }

        this.insert(node.value);
        copy(node.left);
        copy(node.right);
    }

    private void range(T from, T to, Deque<T> queue, Node node) {
        if (node == null) {
            return;
        }

        int lowerRangeComparisonResult = from.compareTo(node.value); // node.value should be >= from --> result should be <= 0
        int upperRangeComparisonResult = to.compareTo(node.value); // node.value should be <= to --> result should be >= 0

        if (lowerRangeComparisonResult <= 0) {
            range(from, to, queue, node.left);
        }

        if (lowerRangeComparisonResult <= 0 && upperRangeComparisonResult >= 0) {
            queue.addLast(node.value);
        }

        if (upperRangeComparisonResult >= 0) {
            range(from, to, queue, node.right);
        }
    }

    public void deleteMin() {
        if (root == null) {
            return;
        }

        if (root.left == null) {
            root = root.right;
        }

        Node currentNode = root;
        Node parentNode = null;
        while (currentNode.left != null) {
            parentNode = currentNode;
            currentNode = currentNode.left;
        }

        parentNode.left = currentNode.right;
    }

    private void inOrder(Node node, Consumer<T> consumer) {
        if (node == null) {
            return;
        }

        inOrder(node.left, consumer);
        consumer.accept(node.value);
        inOrder(node.right, consumer);
    }

    class Node {
        private T value;
        private Node left;
        private Node right;

        public Node(T value) {
            this.value = value;
        }

        public T getValue() {
            return this.value;
        }

        public void setValue(T value) {
            this.value = value;
        }

        public Node getLeft() {
            return this.left;
        }

        public void setLeft(Node left) {
            this.left = left;
        }

        public Node getRight() {
            return this.right;
        }

        public void setRight(Node right) {
            this.right = right;
        }
    }
}

