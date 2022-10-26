package util;

public class LinkedListQueue<E extends Comparable<E>> implements Queue<E> {
    private LinkedList<E> data;

    public LinkedListQueue() {
        data = new LinkedList<>();
    }

    @Override
    public void enqueue(E item) {
        data.add(item);
    }

    @Override
    public E dequeue() {
        return data.remove(0);
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
