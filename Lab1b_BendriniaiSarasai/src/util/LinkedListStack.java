package util;

public class LinkedListStack<E extends Comparable<E>> implements Stack<E> {
    private LinkedList<E> data;

    public LinkedListStack() {
        data = new LinkedList();
    }

    @Override
    public E pop() {
        return data.remove(0);
    }

    @Override
    public void push(E item) {
        data.add(0, item);
    }

    @Override
    public E peak() {
        return data.get(0);
    }

    @Override
    public boolean isEmpty() {
        return data.isEmpty();
    }
}
