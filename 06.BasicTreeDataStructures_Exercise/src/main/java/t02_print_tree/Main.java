package t02_print_tree;

import data_structures.Tree;
import tree_lib.ReadTree;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));
        Map<Integer, Tree<Integer>> treeMap = new HashMap<>();
        Tree<Integer> root = ReadTree.readTreeMapFromConsole(treeMap, consoleReader);
        consoleReader.close();
        System.out.println(root.print(0, new StringBuilder()));
    }
}
