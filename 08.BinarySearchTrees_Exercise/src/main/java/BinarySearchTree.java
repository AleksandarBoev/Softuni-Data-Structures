import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

public class BinarySearchTree<T extends Comparable<T>> {
    private Node root;
    private int nodesCount;

    public BinarySearchTree() {
    }

    private BinarySearchTree(Node root) {
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
        return nodesCount;
    }

    //TODO found a bug in the provided by the lecturers code. parent.childrenCount++ executes even when value.compareTo(current.value) == 0
    public void insert(T value) {
        this.nodesCount++;

        if (this.root == null) {
            this.root = new Node(value);
            return;
        }

        Node parent = null;
        Node current = this.root;
        while (current != null) {
            parent = current;
            parent.childrenCount++;

            if (value.compareTo(current.value) < 0) {
                current = current.left;
            } else if (value.compareTo(current.value) > 0) {
                current = current.right;
            } else {
                return;
            }
        }

        Node newNode = new Node(value);
        if (value.compareTo(parent.value) < 0) {
            parent.left = newNode;
        } else {
            parent.right = newNode;
        }
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

    public BinarySearchTree<T> search(T item) {
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

        return new BinarySearchTree<>(current);
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

        if (parent == null) {
            this.root = this.root.right;
        } else {
            parent.left = min.right;
        }

        this.nodesCount--;
    }

    public void deleteMax() {
        if (root == null) {
            throw new IllegalArgumentException();
        }

        nodesCount--;
        Node current = root;
        Node parent = null;

        while (current.right != null) {
            current.childrenCount--;
            parent = current;
            current = current.right;
        }

        if (parent == null) {
            root = root.left;
        } else {
            parent.right = current.left;
        }
    }

    public T ceil(T element) {
        throw new UnsupportedOperationException();
    }

    public T floor(T element) {
        throw new UnsupportedOperationException();
    }

    public void delete(T key) {
        throw new UnsupportedOperationException();
    }

    public int rank(T item) {
        return rank(item, root, 0);
    }

    private int rank(T item, Node node, int counter) {
        if (node == null) {
            return counter;
        }

        int comparisonResult = item.compareTo(node.value);

        if (comparisonResult < 0) { // item < node.value
            return rank(item, node.left, counter);
        } else if (comparisonResult > 0) { // item > node.value
            counter++;
            if (node.left != null) {
                counter += node.left.childrenCount;
            }

            return rank(item, node.right, counter);
        } else {
            if (node.left != null) {
                counter += node.left.childrenCount;
            }

            return counter;
        }
    }

    /*
        Using rank is not the optimal solution here. Iterating through the items in preOrder
        means values will be sorted in ascending. So the element, which will have "n" smaller elements will
        just be the "n + 1" element in a collection of these elements.
     */
    public T select(int n) {
        List<T> arrayList = new ArrayList<>();
        preOrderItems(root, n + 1, arrayList);
        return arrayList.get(arrayList.size() - 1);
    }

    private void preOrderItems(Node node, int numberOfItems, List<T> list) {
        if (node == null) {
            return;
        }

        preOrderItems(node.left, numberOfItems, list);

        if (list.size() == numberOfItems) {
            return;
        }
        list.add(node.value);

        preOrderItems(node.right, numberOfItems, list);
    }


    class Node {
        private T value;
        private Node left;
        private Node right;

        private int childrenCount;

        public Node(T value) {
            this.value = value;
            this.childrenCount = 1;
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
        public String toString() {
            return this.value + "";
        }
    }
}

