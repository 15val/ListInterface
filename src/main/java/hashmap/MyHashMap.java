package hashmap;

import interfaces.Map;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class MyHashMap<K, V> implements Map<K, V>, Iterable<MyHashMap.Entry<K, V>> {
    private int size = 0;
    private Entry<K,V>[] table;
    private int capacity= 4;
    private double loadFactor = 0.75;

    public static class Entry<K, V> {
        K key;
        V value;
        Entry<K,V> next;

        public K getKey() {
            return key;
        }

        public V getValue() {
            return value;
        }

        public Entry<K, V> getNext() {
            return next;
        }

        public Entry(K key, V value, Entry<K,V> next){
            this.key = key;
            this.value = value;
            this.next = next;
        }
    }


    public MyHashMap(){
        table = new Entry[capacity];
    }

    private int hash(K key){
        return Math.abs(key.hashCode()) % capacity;
    }

    public int ensureCapacity() {
        int newSize = table.length * 2;
        table = Arrays.copyOf(table, newSize);
        return table.length;
    }


    @Override
    public void put(K newKey, V value) {
        if(newKey==null) {
            return;
        }
        if(size >= table.length * loadFactor){
            ensureCapacity();
        }
        int hash=hash(newKey);

        Entry<K,V> newEntry = new Entry<>(newKey, value, null);
        size++;

        if(table[hash] == null){
            table[hash] = newEntry;
        }
        else{
            Entry<K,V> previous = null;
            Entry<K,V> current = table[hash];

            while(current != null){ //we have reached last entry
                if(current.key.equals(newKey)){
                    newEntry.next=current.next;
                    if(previous==null){
                        table[hash]=newEntry;
                    }
                    else{
                        previous.next=newEntry;
                    }
                    return;
                }
                previous=current;
                current = current.next;
            }
            previous.next = newEntry;
        }
    }


    @Override
    public V getByKey(K key) {
        int hash = hash(key);

        if (table[hash] != null) {
            Entry<K, V> temp = table[hash];
            while (temp != null) {
                if (temp.key.equals(key))
                    return temp.value; //returns value of given key.
                temp = temp.next;
            }
        }
        return null;
    }

    @Override
    public boolean removeByKey(K deleteKey) {
        int hash = hash(deleteKey);

        if (table[hash] != null) {
            Entry<K, V> previous = null;
            Entry<K, V> current = table[hash];

            while (current != null) { //we have reached last entry
                if (current.key.equals(deleteKey)) {
                    if (previous == null) {
                        table[hash] = table[hash].next;
                    } else {
                        previous.next = current.next;
                    }
                    size--;
                    return true;
                }
                previous = current;
                current = current.next;
            }
        }
        return false;
    }

    @Override
    public boolean containsValue(V value) {
        for(int i = 0; i < table.length; i++){
            if (table[i] != null) {
                Entry<K, V> current = table[i];
                while (current != null) {
                    if (current.value.equals(value))
                        return true;
                    current = current.next;
                }
            }
        }
        return false;
    }

    @Override
    public boolean containsKey(K key) {
        int hash = hash(key);
        if (table[hash] != null) {
            Entry<K, V> current = table[hash];
            while (current != null) {
                if (current.key.equals(key))
                    return true;
                current = current.next;
            }
        }
        return false;
    }

    @Override
    public Object[] keySet(){
        int count = 0;

        for(int i = 0; i < table.length; i++) {
            if (table[i] != null) {
                Entry<K, V> current = table[i];
                while (current != null) {
                    count++;
                    current = current.next;
                }
            }
        }
        Object[] keys = new Object[count];
        count = 0;
        for(int i = 0; i < table.length; i++) {
            if (table[i] != null) {
                Entry<K, V> current = table[i];
                while (current != null) {
                    keys[count] = current.key;
                    count++;
                    current = current.next;
                }
            }
        }
        return keys;
    }

    @Override
    public Object[] values(){
        int count = 0;
        for(int i = 0; i < table.length; i++) {
            if (table[i] != null) {
                Entry<K, V> current = table[i];
                while (current != null) {
                    count++;
                    current = current.next;
                }
            }
        }
        Object[] values = new Object[count];
        count = 0;
        for(int i = 0; i < table.length; i++) {
            if (table[i] != null) {
                Entry<K, V> current = table[i];
                while (current != null) {
                    values[count] = current.value;
                    count++;
                    current = current.next;
                }
            }
        }
        return values;
    }

    @Override
    public int size() {
        return size;
    }

    public Entry<K,V> getFirstEntry() {
        for (int i = 0; i < table.length; i++) {
            if (table[i] != null) {
                return table[i];
            }
        }
        return null;
    }

    @Override
    public Iterator<Entry<K, V>> iterator() {
        return new Iterator<Entry<K, V>>() {
            Entry<K, V> current = getFirstEntry();

            @Override
            public boolean hasNext() {
                if(current == null) {
                    return false;
                }
                int count = 0;
                for (int i = 0; i < table.length; i++) {
                    if (table[i] == current) {
                        count++;
                        if (current.next != null) {
                            return true;
                        }
                        continue;
                    }
                    if (count > 0 && table[i] != null) {
                        return true;
                    }
                }
                return false;
            }

            @Override
            public Entry<K, V> next() {
                int count = 0;
                for (int i = 0; i < table.length; i++) {
                    if (table[i] == current) {
                        count++;
                        if (current.next != null) {
                            current = current.next;
                            return current;
                        }
                        continue;
                    }
                    if (count > 0 && table[i] != null) {
                        current = table[i];
                        return current;
                    }
                }
                throw new NoSuchElementException("no such element");
            }
        };
    }
}

