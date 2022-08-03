package interfaces;

public interface List<E> extends MyAbstractList {
    void addAtPosition(E element, int index);

    void add(E element);

    E getByIndex(int index);

    E removeByIndex(int index);

    boolean contains(E element);

}