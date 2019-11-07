package t03_leaf_nodes;

import data_structures.Tree;
import tree_lib.ReadTree;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));
        Map<Integer, Tree<Integer>> treeMap = new HashMap<>();
        ReadTree.readTreeMapFromConsole(treeMap, consoleReader);
        consoleReader.close();

        List<String> sortedLeafValues = treeMap.values()
                .stream()
                .filter(tree -> tree.getChildren().isEmpty())
                .map(leaf -> leaf.getValue())
                .sorted((v1, v2) -> v1 - v2)
                .map(leafValue -> leafValue.toString())
                .collect(Collectors.toList());

        System.out.println("Leaf nodes: " + String.join(" ", sortedLeafValues));
    }
}
