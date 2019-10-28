package t05_linked_queue;

public class LinkedQueue<E> {
    private static final String EMPTY_DATA_STRUCTURE_MESSAGE = "Data structure is empty!";

    private int size;
    private QueueNode<E> head;
    private QueueNode<E> tail;

    public LinkedQueue() {
        size = 0;
        head = null;
    }

    public int size() {
        return this.size;
    }

    private void setSize(int size) {
        this.size = size;
    }

    public void enqueue(E element) {
        if (size == 0) {
            head = new QueueNode<>();
            head.setNextNode(null);
            head.setPrevNode(null);
            head.setValue(element);
            tail = head;
        } else {
            QueueNode<E> newNode = new QueueNode<>();

            newNode.setValue(element);
            newNode.setPrevNode(tail);
            newNode.setNextNode(null);

            tail.setNextNode(newNode);
            tail = newNode;
        }

        size++;
    }

    public E dequeue() {
        if (size == 0) {
            throw new IllegalArgumentException(EMPTY_DATA_STRUCTURE_MESSAGE);
        }

        size--;

        E polledElement = head.getValue();

        head = head.getNextNode();
        if (head != null) {
            head.setPrevNode(null);
        }

        return polledElement;
    }

    public E[] toArray() {
        E[] result = (E[]) new Object[size];

        int counter = 0;
        QueueNode<E> currentNode = head;
        while (currentNode != null) {
            result[counter++] = currentNode.getValue();
            currentNode = currentNode.getNextNode();
        }

        return result;
    }

    private class QueueNode<E> {
        private E value;

        private QueueNode<E> nextNode;
        private QueueNode<E> prevNode;

        public E getValue() {
            return this.value;
        }

        private void setValue(E value) {
            this.value = value;
        }

        public QueueNode<E> getNextNode() {
            return this.nextNode;
        }

        public void setNextNode(QueueNode<E> nextNode) {
            this.nextNode = nextNode;
        }

        public QueueNode<E> getPrevNode() {
            return this.prevNode;
        }

        public void setPrevNode(QueueNode<E> prevNode) {
            this.prevNode = prevNode;
        }
    }
}