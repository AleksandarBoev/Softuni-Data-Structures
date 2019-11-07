package t04_middle_nodes;

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
        Map<Integer, Tree<Integer>> treeMap = new HashMap<>();
        BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));
        ReadTree.readTreeMapFromConsole(treeMap, consoleReader);
        consoleReader.close();

        List<String> sortedLeafValues = treeMap.values()
                .stream()
                .filter(tree -> !tree.getChildren().isEmpty() && tree.getParentTree() != null)
                .map(leaf -> leaf.getValue())
                .sorted((v1, v2) -> v1 - v2)
                .map(leafValue -> leafValue.toString())
                .collect(Collectors.toList());

        System.out.println("Middle nodes: " + String.join(" ", sortedLeafValues));
    }
}
