package exam_prep_1.p01_limited_memory.main;


import java.util.HashMap;
import java.util.Iterator;

public class LimitedMemoryCollection<K, V> implements ILimitedMemoryCollection<K, V> {
    public static final String NO_SUCH_KEY_EXISTS_MESSAGE = "No such key exists!";

    private int capacity;
    private int count;
    private PairWrapper head; // the most recent element, which has been operated on
    private PairWrapper tail; // the oldest element, which has been operated on
    private HashMap<K, PairWrapper> map;

    public LimitedMemoryCollection(int capacity) {
        this.capacity = capacity;
        this.map = new HashMap<>(capacity);
    }

    @Override
    public V get(K key) {
        if (!map.containsKey(key)) {
            throw new IllegalArgumentException(NO_SUCH_KEY_EXISTS_MESSAGE);
        }

        V value = (V) map.get(key).content.getValue();
        updateElement(key, value);
        return value;
    }

    @Override
    public void set(K key, V value) {
        if (count == 0) {
            PairWrapper firstElement = new PairWrapper<K, V>(new Pair<>(key, value), null, null);

            head = firstElement;
            tail = firstElement;
            map.put(key, firstElement);

            count++;
            return;
        }

        if (!map.containsKey(key)) {
            if (count >= capacity) { // capacity is reached, an element must go
                PairWrapper oldestOperatedElement = tail;
                map.remove(oldestOperatedElement.content.getKey());
                tail = tail.prev;
                tail.next = null;
            } else {
                count++;
            }

            PairWrapper newElement = new PairWrapper<K, V>(new Pair<>(key, value), null, head);
            head.prev = newElement;
            head = newElement;

            map.put(key, newElement);
        } else {
            updateElement(key, value);
        }
    }

    @Override
    public int getCapacity() {
        return capacity;
    }

    @Override
    public int getCount() {
        return count;
    }

    @Override
    public Iterator<Pair<K, V>> iterator() {
        return new LimitedMemoryIterator();
    }

    private void updateElement(K key, V value) {
        PairWrapper elementToBeUpdated = map.get(key);

        // if the element to be changed is the last operated on element, there is
        // no need to fix the references
        if (!elementToBeUpdated.equals(head)) {

            elementToBeUpdated.prev.next = elementToBeUpdated.next;

            if (elementToBeUpdated.next != null) {
                elementToBeUpdated.next.prev = elementToBeUpdated.prev;
            }

            elementToBeUpdated.next = head;
            elementToBeUpdated.prev = null;

            head.prev = elementToBeUpdated;
            head = elementToBeUpdated;
        }

        elementToBeUpdated.content.setValue(value);
    }

    private class PairWrapper<K, V> {
        private Pair<K, V> content;
        private PairWrapper prev;
        private PairWrapper next;

        public PairWrapper(Pair<K, V> content, PairWrapper prev, PairWrapper next) {
            this.content = content;
            this.prev = prev;
            this.next = next;
        }

        @Override
        public String toString() {
            return content.toString();
        }

        @Override
        public boolean equals(Object other) {
            if (other == null) {
                return false;
            }

            if (this == other) {
                return true;
            }

            if (!(other instanceof PairWrapper)) {
                return false;
            }

            PairWrapper<K, V> otherPairWrapper = (PairWrapper) other;

            return this.content.getKey().equals(otherPairWrapper.content.getKey()); // keys are unique
        }
    }

    private class LimitedMemoryIterator implements Iterator<Pair<K, V>> {
        PairWrapper<K, V> currentElement;

        public LimitedMemoryIterator() {
            currentElement = head;
        }

        @Override
        public boolean hasNext() {
            return currentElement != null;
        }

        @Override
        public Pair<K, V> next() {
            Pair<K, V> result = currentElement.content;
            currentElement = currentElement.next;
            return result;
        }
    }
}
