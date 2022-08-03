package arraylist;

import interfaces.List;

import java.util.*;
import java.util.function.Consumer;


public class MyArrayList<E> implements List<E>, Iterable<E> {

    private int size = 0;

    //Default capacity of list
    private static final int DEFAULT_CAPACITY = 10;

    //All elements added to list
    private Object[] elements;


    public MyArrayList() {
        elements = new Object[DEFAULT_CAPACITY];
    }


    @Override
    public int size() {
        return size;
    }

    public int ensureCapacity() {
        int newSize = elements.length * 2;
        elements = Arrays.copyOf(elements, newSize);
        return elements.length;
    }

    @Override
    public void addAtPosition(E element, int index) {
        if (size == elements.length) {
            ensureCapacity();
        }
        size++;
        try {
            Object[] tempArray = elements;
            System.arraycopy(tempArray, index, elements, index+1, elements.length - index - 1);
            elements[index] = element;
        }
        catch (IndexOutOfBoundsException e){
            throw new IndexOutOfBoundsException("out of bounds");
        }
    }

    @Override
    public void add(E element) {
        if (size == elements.length) {
            ensureCapacity();
        }
        elements[size++] = element;
    }


    @Override
    public E getByIndex(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size " + size);
        }
        return (E) elements[index];
    }

    @Override
    public E removeByIndex(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size " + size);
        }
        Object element = elements[index];
        int numOfElements = elements.length - ( index + 1 ) ;
        System.arraycopy( elements, index + 1, elements, index, numOfElements ) ;
        size--;
        return (E) element;
    }

    @Override
    public boolean contains(E element) {
        for (int i = 0; i < elements.length - 1; i++){
            if(elements[i] == element){
                return true;
            }
        }
        return false;
    }


    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>(){
            private int currentIndex = 0;
            @Override
            public boolean hasNext() {
                return currentIndex < size;
            }

            @Override
            public E next() {
                if(currentIndex < size) {
                    return (E) elements[currentIndex++];
                }
                else {
                    throw new NoSuchElementException("no such element");
                }
            }
        };
    }



    @Override
    public void forEach(Consumer action) {
        Iterable.super.forEach(action);
    }

    @Override
    public Spliterator<E> spliterator() {
        return Iterable.super.spliterator();
    }
}
