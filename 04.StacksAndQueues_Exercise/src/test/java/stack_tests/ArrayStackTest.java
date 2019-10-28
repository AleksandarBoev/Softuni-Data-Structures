package stack_tests;

import org.junit.Before;
import org.junit.Test;
import t03_array_stack.ArrayStack;

public class ArrayStackTest {
    private ArrayStack<Integer> arrayStack;

    @Before
    public void init() {
        arrayStack = new ArrayStack<>();
    }

    @Test
    public void size_whenAddingElements_sizeIsIncreasedAccordingly() {
        StackTest.sizeIncreaseTest(arrayStack);
    }

    @Test
    public void size_whenRemovingElements_sizeIsDecreasedAccordingly() {
        StackTest.sizeDecreaseTest(arrayStack);
    }

    @Test
    public void push_whenAddingElements_correctlyOrdered() {
        StackTest.pushValuesCorrectlyOrderedTest(arrayStack);
    }

    @Test
    public void pop_whenRemovingElements_correctlyPoppedElements() {
        StackTest.popValuesCorrectlyOrderedTest(arrayStack);
    }
}
