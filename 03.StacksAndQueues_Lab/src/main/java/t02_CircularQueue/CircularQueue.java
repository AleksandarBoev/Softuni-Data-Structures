package t02_CircularQueue;

import java.util.Arrays;

public class CircularQueue<E> {
    private static final int INITIAL_CAPACITY = 16;
    private static final String INVALID_INITIAL_CAPACITY_MESSAGE = "Initial capacity should be > 0";
    private static final String EMPTY_DATA_DEQUEUE_MESSAGE = "There are no elements left.";

    private int size;
    private int startIndex;
    private int endIndex;
    private E[] data;

    public E[] getData() {
        return data;
    }

    public CircularQueue() {
        this(INITIAL_CAPACITY);
    }

    public CircularQueue(int initialCapacity) {
        if (initialCapacity <= 0) {
            throw new IllegalArgumentException(INVALID_INITIAL_CAPACITY_MESSAGE);
        }

        data = (E[])new Object[initialCapacity];
        startIndex = 0;
        endIndex = 0;
    }

    public int size() {
        return this.size;
    }

    private  void setSize(int size) {
        this.size = size;
    }

    public void enqueue(E element) {
        if (size == data.length) {
            data = Arrays.copyOf(data, data.length * 2);
            startIndex = 0;
            endIndex = size;
        }

        int newElementIndex = endIndex % data.length;
        data[newElementIndex] = element;

        endIndex = newElementIndex + 1;
        size++;
    }

    public E dequeue() {
        if (size <= 0) {
            throw new IllegalArgumentException(EMPTY_DATA_DEQUEUE_MESSAGE); //not the best exception class
        }

        int dequeueIndex = startIndex;
        startIndex = (startIndex + 1) % data.length;
        size--;
        return data[dequeueIndex];
    }

    public E[] toArray() {
        E[] result = (E[])new Object[size];

        int counter = 0;
        for (int i = 0; i < size; i++) {
            int dataIndex = (startIndex + i) % data.length;
            result[i] = data[dataIndex];
        }

        return result;
    }

}
