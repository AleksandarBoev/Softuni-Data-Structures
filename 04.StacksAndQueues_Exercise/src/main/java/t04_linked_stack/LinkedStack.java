package t04_linked_stack;

import stack_interface.Stack;

public class LinkedStack<E> implements Stack<E> {
    private static final String EMPTY_DATA_STRUCTURE_MESSAGE = "Data structure is empty!";

    private Node<E> firstNode;
    private int size;

    public int size() {
        return this.size;
    }

    private void setSize(int size) {
        this.size = size;
    }

    public void push(E element) {
        size++;

        if (firstNode == null) { //empty data structure
            firstNode = new Node<E>(element);
        } else {
            Node<E> newNode = new Node<E>(element, firstNode);
            firstNode = newNode;
        }
    }

    public E pop() {
        if (size == 0) {
            throw new IllegalArgumentException(EMPTY_DATA_STRUCTURE_MESSAGE);
        }

        size--;

        E elementToReturn = firstNode.value;
        firstNode = firstNode.nextNode;
        return elementToReturn;
    }

    public E[] toArray() {
        E[] result = (E[])new Object[size];

        Node<E> iterator = firstNode;

        for (int i = 0; i < result.length; i++) {
            result[i] = iterator.value;
            iterator = iterator.nextNode;
        }

        return result;
    }

    private class Node<E> {

        private E value;
        private Node<E> nextNode;

        public Node(E value) {
            this(value, null);
        }

        public Node(E value, Node<E> nextNode) {
            this.value = value;
            this.nextNode = nextNode;
        }

        public Node<E> getNextNode() {
            return this.nextNode;
        }

        public void setNextNode(Node<E> nextNode) {
            this.nextNode = nextNode;
        }
    }
}