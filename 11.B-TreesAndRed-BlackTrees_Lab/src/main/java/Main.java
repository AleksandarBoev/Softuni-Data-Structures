public class Main {

    public static void main(String[] args) {
        RedBlackTree<Integer> redBlackTree = new RedBlackTree<>();
        redBlackTree.insert(5);
        redBlackTree.insert(12);
        redBlackTree.insert(18);
        redBlackTree.insert(37);
        redBlackTree.insert(48);
        redBlackTree.insert(60);
        redBlackTree.insert(80);
        redBlackTree.insert(4);
        redBlackTree.insert(3);
    }

    //37 - 7
    // 12, 60 - 3
    // 5, 18, 48, 80 - 1
}
