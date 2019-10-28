package stack_tests;

import org.junit.Before;
import org.junit.Test;
import t04_linked_stack.LinkedStack;

public class LinkedStackTest {
    private LinkedStack<Integer> linkedStack;

    @Before
    public void init() {
        linkedStack = new LinkedStack<>();
    }

    @Test
    public void size_whenAddingElements_sizeIsIncreasedAccordingly() {
        StackTest.sizeIncreaseTest(linkedStack);
    }

    @Test
    public void size_whenRemovingElements_sizeIsDecreasedAccordingly() {
        StackTest.sizeDecreaseTest(linkedStack);
    }

    @Test
    public void push_whenAddingElements_correctlyOrdered() {
        StackTest.pushValuesCorrectlyOrderedTest(linkedStack);
    }

    @Test
    public void pop_whenRemovingElements_correctlyPoppedElements() {
        StackTest.popValuesCorrectlyOrderedTest(linkedStack);
    }
}
