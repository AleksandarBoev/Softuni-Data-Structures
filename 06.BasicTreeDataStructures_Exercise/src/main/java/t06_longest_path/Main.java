package t06_longest_path;

import data_structures.Tree;
import tree_lib.ReadTree;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));
        Tree<Integer> root = ReadTree.readTreeMapFromConsole(new HashMap<>(), consoleReader);
        consoleReader.close();

        System.out.println("Longest path: " + getLongestPath(root));
    }

    private static String getLongestPath(Tree<Integer> root) {
        Tree<Integer> deepestNode = getDeepestNode(root);

        return String.join(" ", getTreeValuesToRoot(deepestNode));
    }

    private static String getTreeValuesToRoot(Tree<Integer> tree) {
        Deque<String> stack = new ArrayDeque<>();

        while (tree != null) {
            stack.push(tree.getValue().toString());
            tree = tree.getParentTree();
        }

        return String.join(" ", stack);
    }

    private static Tree<Integer> getDeepestNode(Tree<Integer> root) {
        Object[] levelTree = new Object[]{0, root}; //first element is max level, second is tree on that level

        processDeepestNode(levelTree, 0, root);

        return (Tree<Integer>)levelTree[1];
    }

    private static void processDeepestNode(Object[] moreStuff, int currentLevel, Tree<Integer> currentTree) {
        for (Tree<Integer> childTree : currentTree.getChildren()) {
            processDeepestNode(moreStuff, currentLevel + 1, childTree);
        }

        // checking AFTER iteration, because the first Trees to get to this code will be the leafs
        if (currentLevel > (Integer)moreStuff[0]) {
            moreStuff[0] = currentLevel;
            moreStuff[1] = currentTree;
        }
    }
}
