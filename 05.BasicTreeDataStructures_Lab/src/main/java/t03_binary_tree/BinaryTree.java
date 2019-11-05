package t03_binary_tree;

import java.util.function.Consumer;

public class BinaryTree<T> {
    private T value;
    private BinaryTree<T> leftChild;
    private BinaryTree<T> rightChild;

    public BinaryTree(T value) {
        this(value, null, null);
    }

    public BinaryTree(T value, BinaryTree<T> child) { //TODO rename to just left child?
        this(value, child, null);
    }

    public BinaryTree(T value, BinaryTree<T> leftChild, BinaryTree<T> rightChild) {
        this.value = value;
        this.leftChild = leftChild;
        this.rightChild = rightChild;
    }

    /**
     * Returns in pre-order (root -> left -> right)
     *
     * @param indent
     * @param builder
     * @return
     */
    public String printIndentedPreOrder(int indent, StringBuilder builder) {
        for (int i = 0; i < indent; i++) {
            builder.append("  ");
        }

        builder.append(this.value).append("\n");

        if (this.leftChild != null) {
            this.leftChild.printIndentedPreOrder(indent + 1, builder);
        }

        if (this.rightChild != null) {
            this.rightChild.printIndentedPreOrder(indent + 1, builder);
        }

        return builder.toString();
    }

    /**
     * Applies lambda expression in left-root-right order
     * @param consumer
     */
    public void eachInOrder(Consumer<T> consumer) {
        if (this.leftChild != null) {
            this.leftChild.eachInOrder(consumer);
        }

        consumer.accept(this.value);

        if (this.rightChild != null) {
            this.rightChild.eachInOrder(consumer);
        }
    }

    /**
     * Applies lambda expression in left-right-root order
     * @param consumer
     */
    public void eachPostOrder(Consumer<T> consumer) {
        if (this.leftChild != null) {
            this.leftChild.eachPostOrder(consumer);
        }

        if (this.rightChild != null) {
            this.rightChild.eachPostOrder(consumer);
        }

        consumer.accept(this.value);
    }
}
