package util;

import java.util.ArrayList;

public class ArrayQueue<E> implements Queue<E> {
    private ArrayList<E> data;
    private int start = 0;
    private int end = -1;

    public ArrayQueue() {
        data = new ArrayList<>();
    }

    @Override
    public void enqueue(E item) {
        data.set(++end, item);
    }

    @Override
    public E dequeue() {
        if (isEmpty()) return null;

        E value = data.get(start++);
        if (isEmpty()) {
            data = new ArrayList<>();
            start = 0;
            end = -1;
        }
        return value;
    }

    @Override
    public E peak() {
        if (isEmpty()) return null;
        return data.get(start);
    }

    @Override
    public boolean isEmpty() {
        return start > end;
    }
}
