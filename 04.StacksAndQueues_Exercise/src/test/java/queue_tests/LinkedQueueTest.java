package queue_tests;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import t05_linked_queue.LinkedQueue;

public class LinkedQueueTest {
    private LinkedQueue<Integer> linkedQueue;

    @Before
    public void init() {
        linkedQueue = new LinkedQueue<>();
    }

    @Test
    public void whenAddingElements_sizeChangesAccordingly() {
        for (int i = 0; i < 10; i++) {
            linkedQueue.enqueue(0);
            Assert.assertEquals(i + 1, linkedQueue.size());
        }
    }

    @Test
    public void whenRemovingElements_sizeChangesAccordingly() {
        for (int i = 0; i < 10; i++) {
            linkedQueue.enqueue(i);
        }

        for (int i = 9; i >= 0; i--) {
            linkedQueue.dequeue();
            Assert.assertEquals(i, linkedQueue.size());
        }
    }

    @Test
    public void enqueue_whenAddingElements_correctlyOrdered() {
        for (int i = 0; i < 10; i++) {
            linkedQueue.enqueue(i);
        }

        Integer[] expectedArray = new Integer[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
        Object[] actualArray = linkedQueue.toArray();

        Assert.assertArrayEquals(expectedArray, actualArray);
    }

    @Test
    public void dequeue_whenRemovingElements_correctlyPolled() {
        for (int i = 0; i < 10; i++) {
            linkedQueue.enqueue(i);
        }

        Integer[] expectedArray = new Integer[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9};

        int counter = 0;
        while (linkedQueue.size() > 0) {
            Assert.assertEquals((Object)counter, linkedQueue.dequeue());
            counter++;
        }
    }
}
