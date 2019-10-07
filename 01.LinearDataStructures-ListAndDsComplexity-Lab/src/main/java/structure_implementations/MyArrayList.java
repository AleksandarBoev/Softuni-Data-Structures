package structure_implementations;

import structure_contracts.MyList;

public class MyArrayList<T> implements MyList<T> {
    public static final String OUT_OF_BOUNDS_MESSAGE = "Invalid index";
    public static final int INITIAL_CAPACITY = 2;
    private static final int CAPACITY_MULTIPLIER = 2;

    private int count;
    private T[] data;

    public MyArrayList() {
        this(INITIAL_CAPACITY);
    }

    public MyArrayList(int initialCapacity) {
        count = 0;
        data = (T[])new Object[initialCapacity];
    }

    @Override
    public int getCount() {
        return count;
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        return data[index];
    }

    @Override
    public void add(T element) {
        count++;
        checkAndResize();
        data[count - 1] = element;
    }

    @Override
    public T removeAt(int index) {
        checkIndex(index);

        T removedValue = data[index];
        for (int i = index + 1; i < count; i++) {
            data[i - 1] = data[i];
        }
        count--;

        return removedValue;
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= count) {
            throw new IndexOutOfBoundsException(OUT_OF_BOUNDS_MESSAGE);
        }
    }

    private void checkAndResize() {
        if (count > data.length) {
            T[] extendedData = (T[]) new Object[data.length * CAPACITY_MULTIPLIER];

            for (int i = 0; i < data.length; i++) {
                extendedData[i] = data[i];
            }

            data = extendedData;
        }
    }
}
