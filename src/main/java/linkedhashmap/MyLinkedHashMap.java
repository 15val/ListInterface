package linkedhashmap;

import hashmap.MyHashMap;
import interfaces.Map;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class MyLinkedHashMap<K, V> implements Map<K,V>, Iterable<MyLinkedHashMap.Entry<K,V>> {
    private int size = 0;
    private Entry<K,V>[] table;
    private Entry<K,V> header;
    private Entry<K,V> last;

    private int capacity= 4;
    private double loadFactor = 0.75;

    public static class Entry<K, V> {
        K key;
        V value;
        Entry<K,V> next;
        Entry<K,V> before;
        Entry<K,V> after ;

        public Entry(K key, V value, Entry<K,V> next){
            this.key = key;
            this.value = value;
            this.next = next;
        }

        public K getKey() {
            return key;
        }

        public V getValue() {
            return value;
        }

        public Entry<K, V> getNext() {
            return next;
        }
    }

    public MyLinkedHashMap(){
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
    public Iterator<Entry<K,V>> iterator() {
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

    @Override
    public void put(K newKey, V value) {
        if(newKey == null) {
            return;
        }
        if(size >= table.length * loadFactor){
            ensureCapacity();
        }
        int hash=hash(newKey);

        Entry<K,V> newEntry = new Entry<>(newKey, value, null);
        size++;

        maintainOrderAfterInsert(newEntry);
        if(table[hash] == null){
            table[hash] = newEntry;
        }else{
            Entry<K,V> previous = null;
            Entry<K,V> current = table[hash];
            while(current != null){ //we have reached last entry of bucket.
                if(current.key.equals(newKey)){
                    newEntry.next = current.next;
                    if(previous == null){  //node has to be inserted on first place of bucket.
                        table[hash] = newEntry;
                    }
                    else{
                        previous.next=newEntry;
                    }
                    return;
                }
                previous = current;
                current = current.next;
            }
            previous.next = newEntry;
        }
    }

   private void maintainOrderAfterInsert(Entry<K, V> newEntry) {

        if(header == null){
            header = newEntry;
            last = newEntry;
            return;
        }

        if(header.key.equals(newEntry.key)){
            removeFirst();
            putFirst(newEntry);
            return;
        }

        if(last.key.equals(newEntry.key)){
            removeLast();
            putLast(newEntry);
            return;
        }

        Entry<K, V> beforeDeleteEntry = removeSpecificEntry(newEntry);
        if(beforeDeleteEntry == null){
            putLast(newEntry);
        }
        else{
            putAfter(beforeDeleteEntry, newEntry);
        }


    }

    private void putAfter(Entry<K, V> beforeEntry, Entry<K, V> newEntry) {
        Entry<K, V> current = header;
        while(current != beforeEntry){
            current = current.after;  //move to next node.
        }

        newEntry.after = beforeEntry.after;
        beforeEntry.after.before = newEntry;
        newEntry.before = beforeEntry;
        beforeEntry.after = newEntry;

    }

    private void putFirst(Entry<K, V> newEntry){

        if(header == null){ //no entry found
            header = newEntry;
            last = newEntry;
            return;
        }

        newEntry.after = header;
        header.before = newEntry;
        header = newEntry;

    }


    private void putLast(Entry<K, V> newEntry){

        if(header == null){
            header = newEntry;
            last = newEntry;
            return;
        }
        last.after = newEntry;
        newEntry.before = last;
        last = newEntry;

    }

    private void removeLast(){

        if(header == last){
            header = last = null;
            return;
        }

        last = last.before;
        last.after = null;

    }

    private void removeFirst(){

        if(header == last){ //only one entry found.
            header = last = null;
            return;
        }
        header = header.after;
        header.before = null;

    }


    private Entry<K, V> removeSpecificEntry(Entry<K, V> newEntry){

        Entry<K, V> current = header;
        while(!current.key.equals(newEntry.key)){
            if(current.after == null){   //entry not found
                return null;
            }
            current = current.after;  //move to next node.
        }

        Entry<K, V> beforeEntry = current.before;
        current.before.after = current.after;
        current.after.before = current.before;  //entry deleted
        return beforeEntry;
    }

    private void maintainOrderAfterDeletion(Entry<K, V> deleteEntry) {

        if(header.key.equals(deleteEntry.key)){
            removeFirst();
            return;
        }

        if(last.key.equals(deleteEntry.key)){
            removeLast();
            return;
        }

        removeSpecificEntry(deleteEntry);

    }
    @Override
    public V getByKey(K key) {
        int hash = hash(key);

        if (table[hash] != null) {
            Entry<K, V> temp = table[hash];
            while (temp != null) {
                if (temp.key.equals(key))
                    return temp.value;
                temp = temp.next; //return value corresponding to key.
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

            while (current != null) { //we have reached last entry node of bucket.
                if (current.key.equals(deleteKey)) {
                    maintainOrderAfterDeletion(current);
                    if (previous == null) {  //delete first entry node.
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
    public Object[] keySet() {
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
    public Object[] values() {
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
}
