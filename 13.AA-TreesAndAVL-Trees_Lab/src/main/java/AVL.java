import java.util.function.Consumer;

public class AVL<T extends Comparable<T>> {

    private Node<T> root;

    public Node<T> getRoot() {
        return this.root;
    }

    public boolean contains(T item) {
        Node<T> node = this.search(this.root, item);
        return node != null;
    }

    public void insert(T item) {
        this.root = this.insert(this.root, item);
    }

    public void eachInOrder(Consumer<T> consumer) {
        this.eachInOrder(this.root, consumer);
    }

    private void eachInOrder(Node<T> node, Consumer<T> action) {
        if (node == null) {
            return;
        }

        this.eachInOrder(node.left, action);
        action.accept(node.value);
        this.eachInOrder(node.right, action);
    }

    private Node<T> insert(Node<T> node, T item) {
        if (node == null) {
            return new Node<>(item);
        }

        int comparisonResult = item.compareTo(node.value);
        if (comparisonResult < 0) {
            node.left = this.insert(node.left, item);
        } else if (comparisonResult > 0) {
            node.right = this.insert(node.right, item);
        }

        int balanceFactor = getBalanceFactor(node);
        if (balanceFactor < -1) {
            if (getBalanceFactor(node.right) > 0) {
                node = doubleLeftRotation(node);
            } else {
                node = leftRotation(node);
            }
        }

        if (balanceFactor > 1) {
            if (getBalanceFactor(node.left) < 0) {
                node = doubleRightRotation(node);
            } else {
                node = rightRotation(node);
            }
        }

        updateHeight(node);
        return node;
    }

    private int getHeight(Node<T> node) {
        if (node == null) {
            return 0;
        } else {
            return node.height;
        }
    }

    private void updateHeight(Node<T> node) {
        node.height = Math.max(getHeight(node.left), getHeight(node.right)) + 1;
    }

    private int getBalanceFactor(Node<T> node) {
        return getHeight(node.left) - getHeight(node.right);
    }

    private Node<T> leftRotation(Node<T> node) {
        Node<T> rightSubTree = node.right;
        node.right = rightSubTree.left;
        rightSubTree.left = node;

        updateHeight(node);
        updateHeight(rightSubTree);

        return rightSubTree;
    }

    private Node<T> rightRotation(Node<T> node) {
        Node<T> leftSubTree = node.left;
        node.left = leftSubTree.right;
        leftSubTree.right = node;

        updateHeight(node);
        updateHeight(leftSubTree);

        return leftSubTree;
    }

    private Node<T> doubleLeftRotation(Node<T> node) {
        node.right = rightRotation(node.right);
        return leftRotation(node);
    }

    private Node<T> doubleRightRotation(Node<T> node) {
        node.left = leftRotation(node.left);
        return rightRotation(node);
    }

    private Node<T> search(Node<T> node, T item) {
        if (node == null) {
            return null;
        }

        int comparisonResult = item.compareTo(node.value);
        if (comparisonResult < 0) {
            return search(node.left, item);
        } else if (comparisonResult > 0) {
            return search(node.right, item);
        }

        return node;
    }
}
