package util;

import java.util.ArrayList;

public class ArrayStack<E> implements Stack<E> {
    private ArrayList<E> data;

    public ArrayStack() {
        data = new ArrayList<>();
    }

    @Override
    public E pop() {
        int size = data.size();
        if (size == 0) return null;
        return data.remove(size-1);
    }

    @Override
    public void push(E item) {
        data.add(item);
    }

    @Override
    public E peak() {
        int size = data.size();
        if (size == 0) return null;
        return data.get(size-1);
    }

    @Override
    public boolean isEmpty() {
        return data.size() == 0;
    }
}
