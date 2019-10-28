package t03_array_stack;

import stack_interface.Stack;

import java.util.Arrays;

public class ArrayStack<T> implements Stack<T> {
    private static final int INITIAL_CAPACITY = 16;
    private static final String EMPTY_DATA_STRUCTURE_MESSAGE = "Data structure is empty!";

    private T[] elements;
    private int size;

    public ArrayStack() {
        this(INITIAL_CAPACITY);
    }

    public ArrayStack(int capacity) {
        elements = (T[])new Object[capacity];
    }

    public int size() {
        return this.size;
    }

    private void setSize(int size) {
        this.size = size;
    }

    public void push(T element) {
        if ((size + 1) == elements.length) {
            grow();
        }

        elements[size++] = element;
    }

    public T pop() {
        if (size == 0) {
            throw new IllegalArgumentException(EMPTY_DATA_STRUCTURE_MESSAGE);
        }

        return elements[--size];
    }

    public T[] toArray() {
        T[] result = (T[])new Object[size];

        int resultIterator = 0;
        for (int elementsIterator = size - 1; elementsIterator >= 0; elementsIterator--) {
            result[resultIterator++] = elements[elementsIterator];
        }
        return result;
    }

    private void grow() {
        elements = Arrays.copyOf(elements, elements.length * 2);
    }
}