package tree_lib;

import data_structures.Tree;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Map;

public class ReadTree {
    /**
     * Reads from the console and saves all trees into a provided map. Returns root tree.
     * @param resultMap all trees are saved here
     * @return root tree
     * @throws IOException
     */
    public static Tree<Integer> readTreeMapFromConsole(Map<Integer, Tree<Integer>> resultMap,
                                                       BufferedReader consoleReader) throws IOException {
        Tree<Integer> root = null;

        int numberOfNodes = Integer.parseInt(consoleReader.readLine());
        for (int i = 0; i < numberOfNodes - 1; i++) {
            Integer[] treeNodeValuesPair = Arrays.stream(consoleReader.readLine().split("\\s+"))
                    .map(Integer::parseInt)
                    .toArray(n -> new Integer[n]);

            Tree<Integer> parentTree = getTree(treeNodeValuesPair[0], resultMap);
            Tree<Integer> childTree = getTree(treeNodeValuesPair[1], resultMap);

            parentTree.addTree(childTree);
            childTree.setParentTree(parentTree);

            if (root == null) {
                root = parentTree;
            }
        }

        return root;
    }

    private static Tree<Integer> getTree(Integer treeValue, Map<Integer, Tree<Integer>> treeMap) {
        if (!treeMap.containsKey(treeValue)) {
            treeMap.put(treeValue, new Tree(treeValue));
        }

        return treeMap.get(treeValue);
    }
}
