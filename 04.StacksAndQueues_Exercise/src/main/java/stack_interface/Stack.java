package stack_interface;

public interface Stack<T> {
    int size();

    void push(T element);

    T pop();

    T[] toArray();
}
