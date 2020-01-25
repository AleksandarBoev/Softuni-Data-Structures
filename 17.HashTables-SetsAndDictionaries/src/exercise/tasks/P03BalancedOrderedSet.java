package exercise.tasks;

import exercise.data_structures.OrderedSet;
import exercise.data_structures.RedBlackTree;

public class P03BalancedOrderedSet {
    public static void main(String[] args) {
        OrderedSet<Integer> set = new RedBlackTree<>();
        set.add(17);
        set.add(9);
        set.add(12);
        set.add(19);
        set.add(6);
        set.add(25);

        for (var item : set) {
            System.out.println(item);
        }
    }
}
