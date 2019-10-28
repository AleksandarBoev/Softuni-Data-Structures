package stack_tests;

import org.junit.Assert;
import stack_interface.Stack;

public class StackTest {
    public static void sizeIncreaseTest(Stack<Integer> stack) {
        for (int i = 0; i < 18; i++) {
            stack.push(0);
            Assert.assertEquals(i + 1, stack.size());
        }
    }

    public static void sizeDecreaseTest(Stack<Integer> stack) {
        int maxSize = 18;
        for (int i = 0; i < maxSize; i++) {
            stack.push(0);
        }

        for (int i = maxSize - 1; i >= 0; i--) {
            stack.pop();
            Assert.assertEquals(i, stack.size());
        }
    }

    public static void pushValuesCorrectlyOrderedTest(Stack<Integer> stack) {
        for (int i = 0; i < 10; i++) {
            stack.push(i);
        }

        Object[] actualResult = stack.toArray();
        Integer[] expectedResult = new Integer[]{9, 8, 7, 6, 5, 4, 3, 2, 1, 0};

        Assert.assertArrayEquals(expectedResult, actualResult);
    }

    public static void popValuesCorrectlyOrderedTest(Stack<Integer> stack) {
        for (int i = 0; i < 10; i++) {
            stack.push(i);
        }
        Object[] arrayStackElements = stack.toArray();

        for (int i = 9; i >= 5; i--) {
            Assert.assertEquals((Object)i, stack.pop());
        }

        Integer[] expectedElementsLeft = new Integer[]{4, 3, 2, 1, 0};

        Assert.assertArrayEquals(expectedElementsLeft, stack.toArray());
    }
}
