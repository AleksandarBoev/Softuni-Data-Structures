package lab;

import java.util.*;

public class HashTable<TKey, TValue> implements Iterable<KeyValue<TKey, TValue>> {
    private static final String DUPLICATE_KEY_ERROR_MESSAGE = "Key already exists!";
    private static final String ELEMENT_NOT_FOUND_MESSAGE = "Element not found!";

    private static final int DEFAULT_CAPACITY = 16;
    private static final float FILL_FACTOR = 0.75f; // data structure is most effective when full up to 75%. After that it gets slow.

    private int size;
    private int capacity;
//    Array of linked lists. In the event of a collision, the element will be added to the linked list structure.
    private LinkedList<KeyValue<TKey, TValue>>[] data;
    private int maxAllowedOccupiedCapacity;

    public HashTable() {
        this(DEFAULT_CAPACITY);
    }

    public HashTable(int capacity) {
        data = new LinkedList[capacity];
        this.capacity = capacity;
        maxAllowedOccupiedCapacity = calculateMaxAllowedOccupiedCapacity();
    }

    public void add(TKey key, TValue value) {
        add(key, value, data);
    }

    public int size() {
        return this.size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int capacity() {
        return this.capacity;
    }

    public boolean addOrReplace(TKey key, TValue value) {
        KeyValue<TKey, TValue> newKvp = new KeyValue<>(key, value);
        int index = getIndex(newKvp);

        if (data[index] == null) {
            data[index] = new LinkedList<>();
        }

        for (KeyValue<TKey, TValue> kvp : data[index]) {
            if (kvp.getKey().equals(newKvp.getKey())) {
                kvp.setValue(newKvp.getValue());
                return false; // do not increment size, since an element was updated, not added
            }
        }

        if (size + 1 > maxAllowedOccupiedCapacity) {
            getResized();
        }

        data[index].add(newKvp);
        size++;
        return true;
    }

    public TValue get(TKey key) {
        KeyValue<TKey, TValue> kvpFound = find(key);

        if (kvpFound == null) {
            throw new IllegalArgumentException(ELEMENT_NOT_FOUND_MESSAGE);
        }

        return kvpFound.getValue();
    }

    public boolean tryGetValue(TKey key, TValue value) {
        KeyValue<TKey, TValue> kvpFound = find(key);

        if (kvpFound == null) {
            return false;
        }

        return value.equals(kvpFound.getValue());
    }

    public KeyValue<TKey, TValue> find(TKey key) {
        int index = getIndex(key);

        if (data[index] == null) {
            return null;
        }

        for (KeyValue<TKey, TValue> kvp : data[index]) {
            if (key.equals(kvp.getKey())) {
                return kvp;
            }
        }

        //Solution 2
        /*
        for (int i = 0; i < data.length; i++) {
            if (data[i] == null) {
                continue;
            }

            for (var kvp : data[i]) {
                if (key.equals(kvp.getKey())) {
                    return kvp;
                }
            }
        }
         */

        return null;
    }

    public boolean containsKey(TKey key) {
        return find(key) != null;
    }

    public boolean remove(TKey key) {
        int index = getIndex(key);

        if (data[index] == null) {
            return false;
        }

        for (KeyValue<TKey, TValue> kvp : data[index]) {
            if (key.equals(kvp.getKey())) {
                data[index].remove(kvp);

                if (data[index].isEmpty()) {
                    data[index] = null;
                }

                size--;
                return true;
            }
        }

        //Solution 2
        /*
        for (int i = 0; i < data.length; i++) {
            if (data[i] == null) {
                continue;
            }

            for (var kvp : data[i]) {
                if (key.equals(kvp.getKey())) {
                    data[i].remove(kvp);

                    if (data[i].isEmpty()) {
                        data[i] = null;
                    }

                    size--;
                    return true;
                }
            }
        }
         */

        return false;
    }

    public void clear() {
        size = 0;
        capacity = DEFAULT_CAPACITY;
        maxAllowedOccupiedCapacity = calculateMaxAllowedOccupiedCapacity();
        data = new LinkedList[capacity];
    }

    public Iterable<TKey> keys() {
        List<TKey> result = new ArrayList<>(size);

        for (int i = 0; i < data.length; i++) {
            if (data[i] == null) {
                continue;
            }

            for (var kvp : data[i]) {
                result.add(kvp.getKey());
            }
        }

        return result;
    }

    public Iterable<TValue> values() {
        List<TValue> result = new ArrayList<>(size);

        for (int i = 0; i < data.length; i++) {
            if (data[i] == null) {
                continue;
            }

            for (var kvp : data[i]) {
                result.add(kvp.getValue());
            }
        }

        return result;
    }

    @Override
    public Iterator<KeyValue<TKey, TValue>> iterator() {
        List<KeyValue<TKey, TValue>> resultStructure = new ArrayList<>(size);

        for (int i = 0; i < data.length; i++) {
            if (data[i] == null) {
                continue;
            }

            resultStructure.addAll(data[i]);
        }

        return resultStructure.iterator();
    }

    private void add(TKey key, TValue value, LinkedList<KeyValue<TKey, TValue>>[] data) {
        KeyValue<TKey, TValue> newKvp = new KeyValue<>(key, value);
        int index = getIndex(newKvp);

        if (data[index] == null) {
            data[index] = new LinkedList<>();
        } else {
            for (KeyValue<TKey, TValue> kvp : data[index]) {
                if (kvp.getKey().equals(newKvp.getKey())) {
                    throw new IllegalArgumentException(DUPLICATE_KEY_ERROR_MESSAGE + " " + kvp.getKey());
                }
            }
        }

        if (size + 1 > maxAllowedOccupiedCapacity) {
            this.data = getResized();
            data = this.data;
            index = getIndex(newKvp); // getIndex calculates via capacity, so after changing the data with resize, it needs recalculation

            if (data[index] == null) {
                data[index] = new LinkedList<>();
            }
        }

        data[index].add(newKvp);
        size++;
    }

    private LinkedList<KeyValue<TKey, TValue>>[] getResized() {
        capacity *= 2;
        size = 0;
        maxAllowedOccupiedCapacity = calculateMaxAllowedOccupiedCapacity();
        LinkedList<KeyValue<TKey, TValue>>[] newData = new LinkedList[capacity];

        for (int i = 0; i < data.length; i++) {
            if (data[i] == null) {
                continue;
            }

            for (KeyValue<TKey, TValue> kvp : data[i]) {
                add(kvp.getKey(), kvp.getValue(), newData);
            }
        }

        return newData;
    }

    private int calculateMaxAllowedOccupiedCapacity() {
        return (int)(capacity * FILL_FACTOR);
    }

    private int getIndex(KeyValue<TKey, TValue> kvp) {
        return Math.abs(kvp.hashCode()) % capacity;
    }

    private int getIndex(TKey key) {
        return getIndex(new KeyValue<TKey, TValue>(key, null)); // changed the skeleton hashcode to depend only on key
    }
}
