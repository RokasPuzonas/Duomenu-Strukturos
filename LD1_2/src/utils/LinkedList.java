package utils;

import java.util.Iterator;

/*
Realizuokite visus interfeiso metodus susietojo sarašo pagrindu.
Nesinaudokite java klase LinkedList<E>, visa logika meginkite parasyti patys.
Jeigu reikia, galima kurti papildomus metodus ir kintamuosius.
*/
public class LinkedList<T> implements List<T> {

    private class Node {
        public T value;
        public Node next;

        public Node(T value, Node next) {
            this.value = value;
            this.next = next;
        }

        public Node(T value) {
            this.value = value;
        }
    }

    Node head, tail;

    @Override
    public void add(T item) {
        if (head == null) {
            this.head = new Node(item);
            this.tail = this.head;
        } else {
            Node d = new Node(item);
            this.tail.next = d;
            this.tail = d;
        }
    }

    @Override
    public T get(int index) {
        Node current = this.head;
        for (int i = 0; i < index && current != null; i++) {
            current = current.next;
        }
        return current != null ? current.value : null;
    }

    @Override
    public boolean remove(T item) {
        Node prev = null;
        Node current = this.head;
        while (current != null) {
            if (current.value.equals(item)) {
                if (current.next == null) {
                    this.tail = prev;
                    prev.next = null;
                } else if (prev == null) {
                    this.head = current.next;
                } else {
                    prev.next = current.next;
                }
                return true;
            }
            prev = current;
            current = current.next;
        }
        return false;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            public Node current = head;

            @Override
            public boolean hasNext() {
                return current != null;
            }

            @Override
            public T next() {
                T value = current.value;
                current = current.next;
                return value;
            }
        };
    }
}
