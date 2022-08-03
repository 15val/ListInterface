package linkedlist;

import interfaces.List;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Spliterator;
import java.util.function.Consumer;

public class MyLinkedList<E> implements List<E>, Iterable<E> {
    private Node<E> head;
    private int size = 0;
    public MyLinkedList(){

    }


    static class Node<E> {

        private E element;
        private Node<E> nextNode;

        public Node(E element){
            this.element = element;
        }

        public E getElement() {
            return element;
        }


        public Node<E> getNextNode() {
            return nextNode;
        }

        public void setNextNode(Node<E> nextNode) {
            this.nextNode = nextNode;
        }
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>(){
            Node<E> currentNode = head;
            @Override
            public boolean hasNext() {
                if (currentNode.getNextNode() != null){
                    return true;
                }
                return false;
            }

            @Override
            public E next() {
                if(currentNode.getNextNode() != null) {
                    currentNode = currentNode.getNextNode();
                    return currentNode.getElement();
                }
                else {
                    throw new NoSuchElementException("no such element");
                }
            }
        };
    }

    @Override
    public void forEach(Consumer<? super E> action) {
        Iterable.super.forEach(action);
    }

    @Override
    public Spliterator<E> spliterator() {
        return Iterable.super.spliterator();
    }


    @Override
    public int size() {
        return size;
    }

    @Override
    public void addAtPosition(E element, int index) {
        Node<E> newNode = new Node<>(element);
        if(this.head == null){
           throw new IndexOutOfBoundsException("out of bounds");
        }else {
            Node<E> currentNode = head;
            if (index == 0) {
                newNode.setNextNode(currentNode);
                head = newNode;
            } else {
                int count = 0;
                while (true) {
                    count++;
                    if (count == index) {
                        break;
                    }
                    if (currentNode.getNextNode() == null) {
                        throw new IndexOutOfBoundsException("out of bounds");
                    }
                    currentNode = currentNode.getNextNode();
                }
                newNode.setNextNode(currentNode.getNextNode());
                currentNode.setNextNode(newNode);
            }
        }
        size++;
    }


    @Override
    public void add(E element) {
        Node<E> newNode = new Node<>(element);
        if(this.head == null){
            head = newNode;
        }else {
            Node<E> currentNode = head;
            while(currentNode.getNextNode() != null){
                currentNode = currentNode.getNextNode();
            }
            currentNode.setNextNode(newNode);
        }
        size++;
    }

    @Override
    public E getByIndex(int index) {
        Node<E> node = head;
        for(int i = 0; i < index; i++){
            node = node.getNextNode();
        }
        return node.getElement();
    }

    @Override
    public E removeByIndex(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size " + size);
        }
        Node<E> node = head;
        Object element;

        if(index == 0){
            head = head.getNextNode();
            size--;
            element = node.getElement();
            return (E) element;
        }
        if(index == size - 1) {
            while (node.getNextNode().getNextNode() != null) {
                node = node.getNextNode();
            }
            element = node.getNextNode().getElement();
            node.setNextNode(null);
            size--;
            return (E) element;
        }
        for(int i = 0; i < index; i++){
            node = node.getNextNode();
        }
        element = node.getElement();
        node.setNextNode(node.getNextNode().getNextNode());
        return (E) element;
    }

    @Override
    public boolean contains(E element) {
        Node<E> node = head;
       while (node != null){
           if(node.getElement() == element){
               return true;
           }
           node = node.getNextNode();
       }
       return false;
    }
}
