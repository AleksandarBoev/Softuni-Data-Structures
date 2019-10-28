package t01_LinkedList;

import java.util.Iterator;

public class LinkedList<E> implements Iterable<E> {
    private Node head;
    private Node tail;
    private int size;

    public int size() { //should be a getter, but this is how the homework skeleton was given
        return this.size;
    }

    private void setSize(int size) {
        this.size = size;
    }

    public void addFirst(E item) {
        Node newNode;

        if (size == 0) {
            newNode = new Node(null, null, item);
            head = newNode;
            tail = newNode;
        } else {
            newNode = new Node(null, head, item);
            head.prev = newNode;
            head = newNode;
        }

        size++;
    }

    public void addLast(E item) {
        Node newNode;

        if (size == 0) {
            newNode = new Node(null, null, item);
            head = newNode;
            tail = newNode;
        } else {
            newNode = new Node(tail, null, item);
            tail.next = newNode;
            tail = newNode;
        }

        size++;
    }

    public E removeFirst() {
        checkSizeWhenRemoving();

        E removedValue = head.value;

        head = head.next;
        if (size > 1) {
            head.prev = null;
        }

        size--;

        return removedValue;
    }

    public E removeLast() {
        checkSizeWhenRemoving();

        E removedValue = tail.value;

        tail = tail.prev;
        if (size > 1) {
            tail.next = null;
        }

        size--;

        return removedValue;
    }

    private void checkSizeWhenRemoving() {
        if (size == 0) {
            throw new IllegalArgumentException();
        }
    }

    private class Node {
        private Node prev;
        private Node next;
        private E value;

        public Node(Node prev, Node next, E value) {
            this.prev = prev;
            this.next = next;
            this.value = value;
        }
    }

    @Override
    public Iterator<E> iterator() {
        return new ListIterator(this);
    }

    private final class ListIterator implements Iterator<E> {
        Node current;

        public ListIterator(LinkedList<E> list) {
            current = list.head;
        }

        public boolean hasNext() {
            return current != null;
        }

        public E next() {
            E data = current.value;
            current = current.next;
            return data;
        }
    }
}
