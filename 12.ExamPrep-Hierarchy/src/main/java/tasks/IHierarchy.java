package tasks;

/*
    For judge to accept the solution:
    1) Have an empty Main class in the zip
    2) Comment out the "package tasks;" from interface, implementation and Main class
    3) All interface methods should be UpperCamelCase like in C#, except the "getCount" method
 */
public interface IHierarchy<T> extends Iterable<T> {

    int getCount();

    void add(T element, T child);

    void remove(T element);

    Iterable<T> getChildren(T element);
    
    T getParent(T element);

    boolean contains(T element);

    Iterable<T> getCommonElements(IHierarchy<T> other);
}
