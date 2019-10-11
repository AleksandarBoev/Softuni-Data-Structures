package t08_doubly_linked_list;

import java.util.Iterator;
import java.util.function.Consumer;

public class DoublyLinkedList<T> implements Iterable<T> {
    private int size;
    private Node head; //first element
    private Node tail; //last element

    public int size() {
        return this.size;
    }

    private void setSize(int size) {
        this.size = size;
    }

    public void addFirst(T element) {
        if (size == 0) {
            setSize(size + 1);
            Node onlyElement = new Node(null, null, element);
            head = onlyElement;
            tail = onlyElement;
        } else {
            setSize(size + 1);
            Node newHead = new Node(null, head, element);
            head.previous = newHead;
            head = newHead;
        }
    }

    public void addLast(T element) {
        if (size == 0) {
            setSize(size + 1);
            Node onlyElement = new Node(null, null, element);
            head = onlyElement;
            tail = onlyElement;
        } else {
            setSize(size + 1);
            Node newTail = new Node(tail, null, element);
            tail.next = newTail;
            tail = newTail;
        }
    }

    public T removeFirst() {
        checkForEmptyStructure();

        T removedElement = head.value;
        if (size == 1) {
            setSize(size - 1);
            tail = null;
            head = null;
            return removedElement;
        }

        setSize(size - 1);
        head = head.next;
        head.previous = null;
        return removedElement;
    }

    public T removeLast() {
        checkForEmptyStructure();

        T removedElement = tail.value;
        if (size == 1) {
            setSize(size - 1);
            tail = null;
            head = null;
            return removedElement;
        }

        setSize(size - 1);
        tail = tail.previous;
        tail.next = null;
        return removedElement;
    }

    public T[] toArray() {
        T[] result = (T[]) new Object[size];
        int counter = 0;
        for (T element : this) {
            result[counter++] = element;
        }

        return result;
    }

    @Override
    public Iterator<T> iterator() {
        return new DoublyLinkedListIterator();
    }

    private class DoublyLinkedListIterator implements Iterator<T> {
        Node currentNode;

        DoublyLinkedListIterator() {
            currentNode = head;
        }

        @Override
        public boolean hasNext() {
            return currentNode != null;
        }

        @Override
        public T next() {
            T value = currentNode.value;
            currentNode = currentNode.next;
            return value;
        }
    }

    @Override
    public void forEach(Consumer<? super T> action) {
        Node currentNode = head;
        while (currentNode != null) {
            action.accept(currentNode.value);
            currentNode = currentNode.next;
        }
    }

    private class Node {
        Node previous;
        Node next;
        T value;

        Node(Node previous, Node next, T value) {
            this.previous = previous;
            this.next = next;
            this.value = value;
        }
    }

    private void checkForEmptyStructure() {
        if (size == 0) {
            throw new IllegalArgumentException();
        }
    }

    private T singleElementCase() {
        if (size == 1) {
            T result = tail.value;
            tail = null;
            head = null;
            return result;
        }
        return null;
    }
}
