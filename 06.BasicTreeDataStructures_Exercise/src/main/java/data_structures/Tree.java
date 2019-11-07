package data_structures;

import java.util.*;
import java.util.function.Consumer;

public class Tree<T> {
    private T value;
    private List<Tree<T>> children;
    private Tree<T> parentTree;

    public Tree(T value, Tree<T>... children) {
        this.value = value;

        if (children.length == 0) {
            this.children = new ArrayList<>();
        } else {
            this.children = new ArrayList<Tree<T>>(Arrays.asList(children));
        }
    }

    public T getValue() {
        return value;
    }

    public List<Tree<T>> getChildren() {
        return children;
    }

    public Tree<T> getParentTree() {
        return parentTree;
    }

    public void setParentTree(Tree<T> parentTree) {
        this.parentTree = parentTree;
    }

    // append output to builder
    public String print(int indent, StringBuilder builder) {
        for (int i = 0; i < indent; i++) {
            builder.append("  ");
        }

        builder.append(this.value).append("\n");

        for (Tree<T> childTree : this.children) {
            childTree.print(indent + 1, builder);
        }
        return builder.toString();
    }

    public void each(Consumer<T> consumer) {
        consumer.accept(this.value);

        for (Tree<T> childTree: this.children) {
            childTree.each(consumer);
        }
    }

    public Iterable<T> orderDFS() {
        List<T> orderedValues = new ArrayList<>();

        dfs(this, orderedValues);

        return orderedValues;
    }

    public Iterable<T> orderBFS() {
        List<T> orderedValues = new ArrayList<>();
        Deque<Tree<T>> treeQueue = new ArrayDeque<>();

        treeQueue.addLast(this);

        while (!treeQueue.isEmpty()) {
            Tree<T> currentTree = treeQueue.pollFirst();

            orderedValues.add(currentTree.value);

            for (Tree<T> childTree: currentTree.children) {
                treeQueue.addLast(childTree);
            }
        }
        return orderedValues;
    }

    /**
     * Similar to DFS, but it is root -> left -> right
     * @return
     */
    public Iterable<T> preOrderValues() {
        List<T> orderedValues = new ArrayList<>();

        preOrder(this, orderedValues);

        return orderedValues;
    }

    public void addTree(Tree<T> tree) {
        this.children.add(tree);
    }


    private void dfs(Tree<T> currentTree, List<T> orderedValues) {
        for (Tree<T> childTree: currentTree.children) {
            dfs(childTree, orderedValues);
        }

        orderedValues.add(currentTree.value);
    }

    private void preOrder(Tree<T> currentTree, List<T> orderedValues) {
        orderedValues.add(currentTree.value);

        for (Tree<T> childTree: currentTree.children) {
            preOrder(childTree, orderedValues);
        }
    }
}