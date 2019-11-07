package t01_root_node;

import data_structures.Tree;
import tree_lib.ReadTree;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws IOException {
        Map<Integer, Tree<Integer>> treeMap = new HashMap<>();
        BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));
        //Solution 1
//        System.out.println("Root node: " + ReadTree.readTreeMapFromConsole(treeMap, consoleReader).getValue());

        //Solution 2
        ReadTree.readTreeMapFromConsole(treeMap, consoleReader);
        Integer answer = treeMap.values().stream()
                .filter(tree -> tree.getParentTree() == null)
                .map(root -> root.getValue())
                .toArray(n -> new Integer[n])[0];

        System.out.println("Root node: " + answer);

        consoleReader.close();
    }
}
