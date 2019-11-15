import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

public class Main {
    public static void main(String[] args) throws IOException {
//        BinarySearchTree<Integer> bst = new BinarySearchTree<>();
//
//        for (Integer value : new Integer[]{50, 48, 70, 30, 20, 32, 15, 25, 31, 35, 65, 90, 67, 66, 69, 98, 94, 99}) {
//            bst.insert(value);
//        }
//
//        System.out.println(bst.select(4));

        BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(consoleReader.readLine());
        consoleReader.close();

        int wat = 5;
        Predicate<Integer> checkIfBigger = numberToBeChecked -> {
            return numberToBeChecked > wat;
        };

        System.out.println(checkIfBigger.test(10));
    }
}
