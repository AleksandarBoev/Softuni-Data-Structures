package t07_paths_with_given_sum;

import data_structures.Tree;
import tree_lib.ReadTree;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));

        Map<Integer, Tree<Integer>> treeMap = new HashMap<>();

        Tree<Integer> root = ReadTree.readTreeMapFromConsole(treeMap, consoleReader);
        Integer targetSum = Integer.parseInt(consoleReader.readLine());

        consoleReader.close();

        System.out.println("Paths of sum " + targetSum + ":");
        System.out.println(getPathsOfSum(root, targetSum));
    }

    private static String getPathsOfSum(Tree<Integer> root, Integer targetSum) {
        StringBuilder result = new StringBuilder();
        List<Tree<Integer>> treeList = new ArrayList<>();

        dfsPathsOfSum(root, treeList, targetSum);

        for (int i = 0; i < treeList.size(); i++) {
            result.append(getTreeValuesToRoot(treeList.get(i))).append(System.lineSeparator());
        }

        return result.toString().trim();
    }

    private static void dfsPathsOfSum(Tree<Integer> currentTree, List<Tree<Integer>> treeList, Integer targetSum) {
        for (Tree<Integer> childTree : currentTree.getChildren()) {
            dfsPathsOfSum(childTree, treeList, targetSum);
        }

        if (currentTree.getChildren().isEmpty()) {
            if (getValuesSumUpToRoot(currentTree) == targetSum) {
                treeList.add(currentTree);
            }
        }
    }

    private static int getValuesSumUpToRoot(Tree<Integer> tree) {
        int result = 0;
        while (tree != null) {
            result += tree.getValue();
            tree = tree.getParentTree();
        }

        return result;
    }

    private static String getTreeValuesToRoot(Tree<Integer> tree) {
        Deque<String> stack = new ArrayDeque<>();

        while (tree != null) {
            stack.push(tree.getValue().toString());
            tree = tree.getParentTree();
        }

        return String.join(" ", stack);
    }
}
