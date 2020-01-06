import java.util.Deque;
import java.util.LinkedList;
import java.util.function.Consumer;

public class RedBlackTree<T extends Comparable<T>> {
    /*
    Red-black tree rules:
    1) All leaves are black
    2) The root is black
    3) No node has two red links connected to it
    4) Every path from a given node to its descendant leaf nodes contains the same number of black nodes
    5) Red links lean left (only left nodes can be red. Right nodes can't)
     */

    private static final boolean RED = true;
    private static final boolean BLACK = false;

    private Node root;
    private int nodesCount;

    public RedBlackTree() {
    }

    private RedBlackTree(Node root) {
        this.preOrderCopy(root);
    }

    private void preOrderCopy(Node node) {
        if (node == null) {
            return;
        }

        this.insert(node.value);
        this.preOrderCopy(node.left);
        this.preOrderCopy(node.right);
    }

    public Node getRoot() {
        return this.root;
    }

    public int getNodesCount() {
        return this.nodesCount;
    }

    public void insert(T value) {
        nodesCount++;
        root = insert(value, root);
        root.color = BLACK; // root must always remain black, rule 2)
    }

    private Node insert(T value, Node currentNode) {
        if (currentNode == null) {
            Node newNode = new Node(value);
            return newNode;
        }

        int valueCurrentNodeValueComparisonResult = value.compareTo(currentNode.value);

        if (valueCurrentNodeValueComparisonResult > 0) { // value is bigger --> it will be a right child of current node
            currentNode.setRight(insert(value, currentNode.getRight()));
        }

        if (rightChildIsRedAndleftChildIsBlack(currentNode)) { // rule 5)
            currentNode = leftRotation(currentNode);
        }

        if (bothChildrenAreRed(currentNode)) {
            flipColors(currentNode);
        }

        return currentNode;
    }

    private Node leftRotation(Node currentNode) {
        Node subTree = currentNode.getRight(); // is now right node
        currentNode.setRight(subTree.getLeft());
        subTree.setLeft(currentNode);

        subTree.color = BLACK;
        currentNode.color = RED;

        return subTree;
    }

    private boolean rightChildIsRedAndleftChildIsBlack(Node currentNode) {
        return isRed(currentNode.getRight()) && !isRed(currentNode.getLeft());
    }

    private boolean bothChildrenAreRed(Node currentNode) {
        return isRed(currentNode.getRight()) && isRed(currentNode.getLeft());
    }

    /**
     * @return if "null" or "BLACK" then return false. If not - return true
     */
    private boolean isRed(Node node) {
        if (node == null || node.color == BLACK) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * Makes red colors black and black colors - red. Applies this change to "currentNode" and its direct children.
     */
    private void flipColors(Node currentNode) {
        currentNode.color = !currentNode.color;
        currentNode.getRight().color = !currentNode.getRight().color;
        currentNode.getLeft().color = !currentNode.getLeft().color;
    }

    public boolean contains(T value) {
        Node current = this.root;
        while (current != null) {
            if (value.compareTo(current.value) < 0) {
                current = current.left;
            } else if (value.compareTo(current.value) > 0) {
                current = current.right;
            } else {
                break;
            }
        }

        return current != null;
    }

    public RedBlackTree<T> search(T item) {
        Node current = this.root;
        while (current != null) {
            if (item.compareTo(current.value) < 0) {
                current = current.left;
            } else if (item.compareTo(current.value) > 0) {
                current = current.right;
            } else {
                break;
            }
        }

        return new RedBlackTree<>(current);
    }

    public void eachInOrder(Consumer<T> consumer) {
        this.eachInOrder(this.root, consumer);
    }

    private void eachInOrder(Node node, Consumer<T> consumer) {
        if (node == null) {
            return;
        }

        this.eachInOrder(node.left, consumer);
        consumer.accept(node.value);
        this.eachInOrder(node.right, consumer);
    }

    public Iterable<T> range(T from, T to) {
        Deque<T> queue = new LinkedList<>();
        this.range(this.root, queue, from, to);
        return queue;
    }

    private void range(Node node, Deque<T> queue, T startRange, T endRange) {
        if (node == null) {
            return;
        }

        int compareStart = startRange.compareTo(node.value);
        int compareEnd = endRange.compareTo(node.value);
        if (compareStart < 0) {
            this.range(node.left, queue, startRange, endRange);
        }
        if (compareStart <= 0 && compareEnd >= 0) {
            queue.addLast(node.value);
        }
        if (compareEnd > 0) {
            this.range(node.right, queue, startRange, endRange);
        }
    }

    private T minValue(Node root) {
        T minv = root.value;
        while (root.left != null) {
            minv = root.left.value;
            root = root.left;
        }

        return minv;
    }

    public void deleteMin() {
        if (this.root == null) {
            throw new IllegalArgumentException("Tree is empty!");
        }

        Node min = this.root;
        Node parent = null;

        while (min.left != null) {
            parent = min;
            parent.childrenCount--;
            min = min.left;
        }

        if (parent == null) { // if there is only one element - root element
            this.root = this.root.right; // like deleting the root and the right becomes new root
        } else {
            parent.left = min.right;
        }

        this.nodesCount--;
    }

    public void deleteMax() {
        if (this.root == null) {
            throw new IllegalArgumentException("Tree is empty!");
        }

        Node max = this.root;
        Node parent = null;

        while (max.right != null) {
            parent = max;
            max = max.right;
        }

        if (parent == null) {
            this.root = this.root.left;
        } else {
            parent.right = max.left;
        }
    }

    public T ceil(T element) {
        return this.select(this.rank(element) + 1);
    }

    public T floor(T element) {
        return this.select(this.rank(element) - 1);
    }

    public void delete(T key) {
        this.root = deleteRecursive(this.root, key);
    }

    private Node deleteRecursive(Node root, T key) {
        if (root == null) {
            return root;
        }

        if (key.compareTo(root.value) < 0) {
            root.left = deleteRecursive(root.left, key);
        }
        else if (key.compareTo(root.value) > 0) {
            root.right = deleteRecursive(root.right, key);
        } else {
            if (root.left == null) {
                return root.right;
            } else if (root.right == null) {
                return root.left;
            }

            root.value = minValue(root.right);

            root.right = deleteRecursive(root.right, root.value);
        }

        return root;
    }

    public int rank(T element) { // the lower the rank, the smaller the element. Higher = bigger
        return this.rank(element, this.root);
    }

    private int rank(T element, Node node) {
        if (node == null) {
            return 0;
        }

        int compare = element.compareTo(node.value);

        if (compare < 0) {
            return this.rank(element, node.left);
        }

        if (compare > 0) {
            return 1 + this.size(node.left) + this.rank(element, node.right);
        }

        return this.size(node.left);
    }

    public T select(int rank) {
        Node node = this.select(rank, this.root);
        if (node == null) {
            throw new IllegalArgumentException("ERROR");
        }

        return node.value;
    }

    private Node select(int rank, Node node) {
        if (node == null) {
            return null;
        }

        int leftCount = this.size(node.left);
        if (leftCount == rank) {
            return node;
        }

        if (leftCount > rank) {
            return this.select(rank, node.left);
        } else {
            return this.select(rank - (leftCount + 1), node.right);
        }
    }

    private int size(Node node) {
        if (node == null) {
            return 0;
        }

        return node.childrenCount;
    }

    class Node {
        private T value;
        private Node left;
        private Node right;

        private int childrenCount;
        private boolean color;

        public Node(T value) {
            this.value = value;
            childrenCount = 1; // counting itself
            color = RED;
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

        @Override
        public String toString() { //helps with debugging
            return this.value + "";
        }
    }
}


