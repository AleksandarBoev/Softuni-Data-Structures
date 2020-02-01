import com.sun.source.tree.Tree;

import java.util.SortedMap;
import java.util.TreeMap;

public class Main {
    public static void main(String[] args) {
        TreeMap<Integer, TreeMap<Integer, String>> treeMap = new TreeMap<>();
        treeMap.put(-1, new TreeMap<>());
        treeMap.put(-2, new TreeMap<>());
        treeMap.put(-3, new TreeMap<>());
        treeMap.put(-4, new TreeMap<>());
        treeMap.put(0, new TreeMap<>());
        treeMap.put(9, new TreeMap<>());
        treeMap.put(5, new TreeMap<>());
        SortedMap<Integer, TreeMap<Integer, String>> wat;
        wat = treeMap.subMap(0, 10);


    }
}
