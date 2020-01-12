package first_last;

import java.util.*;

/*
    "Product" class in the tests does not implement ".equals()" method, which the underlying structures use.
    A wrapper class could be used, but it complicates the code in an unnecessary way.
 */
public class FirstLastList<T extends Comparable<T>> implements IFirstLastList<T> {
    public static final String INVALID_COUNT_MESSAGE = "Count out of bounds!";

    private ArrayList<T> elements;
    private PriorityQueue<T> ascendingOrder;
    private PriorityQueue<T> descendingOrder;

    public FirstLastList() {
        elements = new ArrayList<T>();
        ascendingOrder = new PriorityQueue<>();

        Comparator<T> flippedComparator = new Comparator<T>() {
            @Override
            public int compare(T o1, T o2) {
                return o2.compareTo(o1);
            }
        };
        descendingOrder = new PriorityQueue<T>(flippedComparator);
    }

    @Override
    public void add(T element) {
        elements.add(element);
        ascendingOrder.add(element);
        descendingOrder.add(element);
    }

    @Override
    public int getCount() {
        return elements.size();
    }

    @Override
    public Iterable<T> first(int count) {
        checkCountParameter(count);

        return elements.subList(0, count);
    }

    @Override
    public Iterable<T> last(int count) {
        checkCountParameter(count);

        ArrayList<T> result = new ArrayList<>(count);
        for (int i = elements.size() - 1; i >= elements.size() - count; i--) {
            result.add(elements.get(i));
        }

        return result;
    }

    @Override
    public Iterable<T> min(int count) {
        checkCountParameter(count);

        ArrayList<T> result = new ArrayList<>(count);

        PriorityQueue<T> ascendingOrderCopy = new PriorityQueue<>(ascendingOrder);
        while (result.size() != count) {
            result.add(ascendingOrderCopy.poll());
        }

        return result;
    }

    @Override
    public Iterable<T> max(int count) {
        checkCountParameter(count);

        ArrayList<T> result = new ArrayList<>(count);

        PriorityQueue<T> descendingOrderCopy = new PriorityQueue<>(descendingOrder);
        while (result.size() != count) {
            result.add(descendingOrderCopy.poll());
        }

        return result;
    }

    @Override
    public void clear() {
        elements.clear();
        ascendingOrder.clear();
        descendingOrder.clear();
    }

    @Override
    public int removeAll(T element) {
        int counter = 0;
        while (elements.contains(element)) {
            ascendingOrder.remove(element);
            descendingOrder.remove(element);
            elements.remove(element);
            counter++;
        }

        return counter;
    }

    private void checkCountParameter(int count) {
        if (count < 0 || count > getCount()) {
            throw new IllegalArgumentException(INVALID_COUNT_MESSAGE);
        }
    }
}