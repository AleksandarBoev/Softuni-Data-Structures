package exercise.data_structures;

public interface OrderedSet <T extends Comparable<T>> extends Iterable<T> {
    /*
    •	Add(T element) - adds the element to the set
•	Contains(T element) - determines whether the element is present in the set
•	Remove(T element) - removes the element from the set. Its place should be taken by the bigger child node.
•	Count - property that returns the number of unique elements in the set
•	The set should be foreach-able (just like arrays, lists and other data structures). Implement the IEnumerable<T> interface to achieve this. The set should yield all elements, sorted, in ascending order.
Tip: Use in-order traversal.

     */
    void add(T element);

    boolean contains(T element);

    boolean remove(T element);

    int count();
}
