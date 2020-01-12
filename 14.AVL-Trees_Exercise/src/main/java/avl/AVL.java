package avl;

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

    public void delete(T item) {
        root = delete(root, item);
    }

    public void deleteMin() {
        root = deleteMin(root);
    }

    // BONUS
    public void deleteMax() {
        root = deleteMax(root);
    }

    public void eachInOrder(Consumer<T> consumer) {
        this.eachInOrder(this.root, consumer);
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

        node = balanceTree(node);

        updateHeight(node);
        return node;
    }

    private Node<T> balanceTree(Node<T> node) {
        if (node == null) {
            return null;
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

        return node;
    }

    private int getBalanceFactor(Node<T> node) {
        return getHeight(node.left) - getHeight(node.right);
    }

    private int getHeight(Node<T> node) {
        if (node == null) {
            return 0;
        } else {
            return node.height;
        }
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

    private void updateHeight(Node<T> node) {
        node.height = Math.max(getHeight(node.left), getHeight(node.right)) + 1;
    }

    private Node<T> delete(Node<T> node, T item) {
        if (node == null) {
            return null;
        }

        int comparisonResult = node.value.compareTo(item);
        if (comparisonResult > 0) {
            node.left = delete(node.left, item);
        } else if (comparisonResult < 0) {
            node.right = delete(node.right, item);
        } else { //node to delete is found
            if (node.right == null) {
                return node.left;
            } else if (node.left == null) {
                return node.right;
            } else {
                Object[] minAndParentNodes = getMinNodeAndParent(node.right);
                Node<T> minNode = (Node<T>) minAndParentNodes[0];
                Node<T> parentNode = (Node<T>) minAndParentNodes[1];

                if (parentNode != null) {
                    parentNode.left = minNode.right;
                }

                if (minNode.value != node.right.value) {
                    minNode.right = node.right;
                }

                minNode.left = node.left;
                minNode.right = balanceTree(minNode.right); // after moving the smallest node from the right, it might need to be rebalanced
                if (minNode.right != null) {
                    updateHeight(minNode.right);
                }
                node = minNode;
            }
        }

        node = balanceTree(node);
        updateHeight(node);

        return node;
    }

    /**
     * Min node is at index 0 and its parent at index 1.
     */
    private Object[] getMinNodeAndParent(Node<T> node) {
        Node<T> parentNode = null;

        while (node.left != null) {
            parentNode = node;
            node = node.left;
        }

        return new Object[]{node, parentNode};
    }

    private Node<T> deleteMin(Node<T> node) {
        if (node == null) {
            return null;
        }

        if (node.left == null) { //smallest element found
            return node.right;
        }

        node.left = deleteMin(node.left);
        node = balanceTree(node);
        updateHeight(node);
        return node;
    }

    private Node<T> deleteMax(Node<T> node) {
        if (node == null) {
            return null;
        }

        if (node.right == null) { //smallest element found
            return node.left;
        }

        node.right = deleteMax(node.right);
        node = balanceTree(node);
        updateHeight(node);
        return node;
    }

    private void eachInOrder(Node<T> node, Consumer<T> action) {
        if (node == null) {
            return;
        }

        this.eachInOrder(node.left, action);
        action.accept(node.value);
        this.eachInOrder(node.right, action);
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
