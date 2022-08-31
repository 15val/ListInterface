package interfaces;

public interface Map<K, V> extends MyAbstractMap {
    void put(K key, V value);

    V getByKey(K key);

    boolean removeByKey(K key);

    boolean containsValue(V value);
    boolean containsKey(K key);

    Object[] keySet();

    Object[] values();
}