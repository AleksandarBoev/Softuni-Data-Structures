package t06_reversed_list;

import java.util.Iterator;

/**
 * Elements are kept in normal order in array. Only the get, removeAt and iterator methods are changed.
 * This is done so that there is no need to reposition every other element when adding a new element.
 * @param <T>
 */
public class ReversedList<T> implements Iterable<T> {
    public static final String OUT_OF_BOUNDS_MESSAGE = "Invalid index";
    public static final int INITIAL_CAPACITY = 2;
    private static final int CAPACITY_MULTIPLIER = 2;

    private int count;
    private T[] data;

    public ReversedList() {
        this(INITIAL_CAPACITY);
    }

    public ReversedList(int initialCapacity) {
        count = 0;
        data = (T[])new Object[initialCapacity];
    }

    public int getCount() {
        return count;
    }

    public T get(int index) {
        checkIndex(index);
        return data[count - 1 - index];
    }

    public void add(T element) {
        count++;
        checkAndResize();
        data[count - 1] = element;
    }

    public T removeAt(int index) {
        checkIndex(index);

        int indexToRemove = count - 1 - index;
        T removedValue = data[indexToRemove];
        for (int i = indexToRemove; i < count - 1; i++) {
            data[i] = data[i + 1];
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

    @Override
    public Iterator<T> iterator() {
        return new ReversedListIterator();
    }

    private class ReversedListIterator implements Iterator<T> {
        private int counter;

        public ReversedListIterator() {
            counter = count - 1;
        }

        @Override
        public boolean hasNext() {
            return counter>= 0;
        }

        @Override
        public T next() {
            return data[counter--];
        }
    }
}
