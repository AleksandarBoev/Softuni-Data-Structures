package t08_subtrees_with_given_sum;

import data_structures.Tree;
import tree_lib.ReadTree;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));

        Tree<Integer> root = ReadTree.readTreeMapFromConsole(new HashMap<>(), consoleReader);
        Integer targetedSubtreeSum = Integer.parseInt(consoleReader.readLine());

        consoleReader.close();

        Iterable<Tree<Integer>> treesInPreorder = getTreesInPreorder(root);
        System.out.println("Subtrees of sum " + targetedSubtreeSum + ":");
        for (Tree<Integer> currentTree : treesInPreorder) {
            Iterable<Integer> preorderValues = currentTree.preOrderValues();
            if (getSum(preorderValues) == targetedSubtreeSum) {
                System.out.println(join(" ", preorderValues));
            }
        }
    }

    private static Iterable<Tree<Integer>> getTreesInPreorder(Tree<Integer> tree) {
        List<Tree<Integer>> result = new ArrayList<>();

        preorder(result, tree);

        return result;
    }

    private static void preorder(List<Tree<Integer>> treeList, Tree<Integer> currentTree) {
        treeList.add(currentTree);

        for (Tree<Integer> childTree : currentTree.getChildren()) {
            preorder(treeList, childTree);
        }
    }


    private static int getSum(Iterable<Integer> values) {
        int result = 0;

        for (Integer value : values) {
            result += value;
        }

        return result;
    }

    private static String join(String delimiter, Iterable<Integer> iterable) {
        StringBuilder sb = new StringBuilder();
        for (Integer element: iterable) {
            sb.append(element).append(delimiter);
        }

        return sb.toString().substring(0, sb.lastIndexOf(delimiter));
    }
}
