package structure_contracts;

public interface MyList<T> {
    int getCount();

    T get(int index);

    void add(T element);

    T removeAt(int index);
}
