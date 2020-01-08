package tasks;

import java.util.*;
import java.util.stream.Collectors;

public class Hierarchy<T> implements IHierarchy<T> {
    public static final String PARENT_NOT_FOUND_MESSAGE = "Parent element not found!";
    public static final String DUPLICATE_CHILD_MESSAGE = "Child element already exists!";
    public static final String NO_SUCH_ELEMENT_MESSAGE = "No such element!";
    public static final String CANNOT_REMOVE_ROOT_ELEMENT_MESSAGE = "Can't remove root element!";

    private Map<T, Node<T>> valueNode;
    private int elementsCounter;
    private Node<T> root;

    public Hierarchy(T element){
        Node<T> root = new Node<>(element, null);
        this.root = root;

        valueNode = new LinkedHashMap<>();
        valueNode.put(element, root);

        elementsCounter = 1;
    }

    public void add(T parent, T child){
        if (!valueNode.containsKey(parent)) {
            throw new IllegalArgumentException(PARENT_NOT_FOUND_MESSAGE);
        }

        Node<T> parentNode = valueNode.get(parent);

        if (valueNode.containsKey(child)) {
            throw new IllegalArgumentException(DUPLICATE_CHILD_MESSAGE);
        }

        Node<T> newNode = new Node<>(child, parentNode);
        valueNode.put(child, newNode);
        parentNode.valueChild.put(child, newNode);

        elementsCounter++;
    }

    public int getCount() {
        return elementsCounter;
    }

    public void remove(T element){
        if (!valueNode.containsKey(element)) {
            throw new IllegalArgumentException(NO_SUCH_ELEMENT_MESSAGE);
        }

        if (root.value.equals(element)) {
            throw new IllegalStateException(CANNOT_REMOVE_ROOT_ELEMENT_MESSAGE);
        }

        Node<T> elementNode = valueNode.get(element);
        valueNode.remove(element);
        Node<T> parentNode = valueNode.get(elementNode.parent.value);

        parentNode.valueChild.remove(element);
        parentNode.valueChild.putAll(elementNode.valueChild);
        elementNode.valueChild.values().forEach(childNode -> childNode.parent = parentNode);

        elementsCounter--;
    }

    public boolean contains(T element){
        return valueNode.containsKey(element);
    }

    public T getParent(T element){
        if (!valueNode.containsKey(element)) {
            throw new IllegalArgumentException(NO_SUCH_ELEMENT_MESSAGE);
        }

        Node<T> parent = valueNode.get(element).parent;
        if (parent == null) {
            return null;
        } else {
            return parent.value;
        }
    }

    public Iterable<T> getChildren(T element){
        if (!valueNode.containsKey(element)) {
            throw new IllegalArgumentException(NO_SUCH_ELEMENT_MESSAGE);
        }

        return valueNode.get(element).valueChild.keySet();
    }

    public Iterable<T> getCommonElements(IHierarchy<T> other){
        return valueNode.keySet().stream()
                .filter(currentValue -> other.contains(currentValue))
                .collect(Collectors.toList());
    }

    @Override
    public Iterator<T> iterator() {
        return new HierarchyIterator<>();
    }

    private class Node<T> {
        private T value;
        private Node<T> parent;
        private LinkedHashMap<T, Node<T>> valueChild;

        private Node(T value, Node<T> parent) {
            this.value = value;
            this.parent = parent;
            valueChild = new LinkedHashMap<>();
        }
    }

    private class HierarchyIterator<T> implements Iterator<T> {
        private List<T> elementsBfs;
        private int counter;

        private HierarchyIterator() {
            counter = 0;

            elementsBfs = new ArrayList<>(elementsCounter);
            Deque<Node<T>> elementsQueue = new ArrayDeque<>(elementsCounter);
            elementsQueue.addLast((Node<T>)root);

            while (!elementsQueue.isEmpty()) {
                Node<T> currentNode = elementsQueue.pollFirst();
                elementsBfs.add(currentNode.value);

                elementsQueue.addAll(currentNode.valueChild.values());
            }
        }

        @Override
        public boolean hasNext() {
            return counter < elementsCounter;
        }

        @Override
        public T next() {
            return elementsBfs.get(counter++);
        }
    }
}
