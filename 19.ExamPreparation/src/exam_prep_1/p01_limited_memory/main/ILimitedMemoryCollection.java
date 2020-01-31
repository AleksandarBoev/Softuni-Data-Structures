package exam_prep_1.p01_limited_memory.main;

public interface ILimitedMemoryCollection<K, V> extends Iterable<Pair<K, V>> {

    V get(K key);

    void set(K key, V value);

    int getCapacity();

    int getCount();
}
