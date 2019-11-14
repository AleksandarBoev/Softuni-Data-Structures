public class Main {
    public static void main(String[] args) {
        BinarySearchTree<Integer> bst = new BinarySearchTree<>();

        for (Integer value : new Integer[]{50, 48, 70, 30, 20, 32, 15, 25, 31, 35, 65, 90, 67, 66, 69, 98, 94, 99}) {
            bst.insert(value);
        }

        System.out.println(bst.select(4));

    }
}
