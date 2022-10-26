package edu.ktu.ds.lab2.demo;

import edu.ktu.ds.lab2.utils.Set;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

public abstract class SetTests<E extends Comparable<E>> {
    protected Set<E> set;
    protected java.util.Set<E> javaSet;

    @Before
    public void setup() {
        javaSet = new TreeSet<>();
        set = newSet();
    }

    public abstract Set<E> newSet();

    public abstract E newElement();

    public List<E> newTestList(int count) {
        List<E> list = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            list.add(newElement());
        }
        return list;
    }

    public void add(E element) {
        set.add(element);
        javaSet.add(element);
    }

    private Set<E> collectionToSet(Collection<E> elements) {
        Set<E> set = newSet();
        for (E e : elements) {
            set.add(e);
        }
        return set;
    }

    public void addAll(Collection<E> elements) {
        set.addAll(collectionToSet(elements));
        javaSet.addAll(elements);
    }

    public void remove(E element) {
        set.remove(element);
        javaSet.remove(element);
    }

    public void fill(Iterable<E> elements) {
        for (E e : elements) {
            add(e);
        }
    }

    public void retainAll(Collection<E> elements) {
        set.retainAll(collectionToSet(elements));
        javaSet.retainAll(elements);
    }

    public ArrayList<E> iterableToArray(Iterable<E> iterable) {
        ArrayList<E> array = new ArrayList<>();
        for (E e : iterable) array.add(e);
        return array;
    }

    public void assertSetsMatch(Iterable<E> actual, Iterable<E> expected) {
        Assert.assertEquals(iterableToArray(actual), iterableToArray(expected));
    }

    public void assertContainsAllMatches(Collection<E> elements) {
        Assert.assertEquals(set.containsAll(collectionToSet(elements)), javaSet.containsAll(elements));
    }

    @Test
    public void checkAddition() {
        add(newElement());
        add(newElement());
        add(newElement());
        assertSetsMatch(set, javaSet);
    }

    @Test
    public void checkAdditionWithDuplicates() {
        E element = newElement();
        add(newElement());
        add(newElement());
        add(newElement());
        add(element);
        add(element);
        assertSetsMatch(set, javaSet);
    }

    @Test
    public void checkSize() {
        E e1 = newElement();
        E e2 = newElement();
        E e3 = newElement();
        Assert.assertEquals(set.size(), 0);
        set.add(e1);
        set.add(e2);
        set.add(e3);
        Assert.assertEquals(set.size(), 3);
        set.remove(e2);
        Assert.assertEquals(set.size(), 2);
        set.remove(e1);
        set.remove(e3);
        Assert.assertEquals(set.size(), 0);
    }

    @Test
    public void checkRemoving() {
        List<E> values = newTestList(5);
        fill(values);
        assertSetsMatch(set, javaSet);

        remove(values.get(2));
        assertSetsMatch(set, javaSet);

        remove(values.get(0));
        remove(values.get(4));
        assertSetsMatch(set, javaSet);

        remove(values.get(1));
        remove(values.get(3));
        assertSetsMatch(set, javaSet);
    }

    @Test
    public void checkAddingAll() {
        List<E> values = newTestList(5);
        add(values.get(0));
        add(values.get(1));
        assertSetsMatch(set, javaSet);

        addAll(values);
        assertSetsMatch(set, javaSet);
    }

    @Test
    public void checkContainsAll() {
        List<E> values = newTestList(5);
        add(values.get(0));
        add(values.get(1));
        assertContainsAllMatches(values);

        fill(values);
        assertContainsAllMatches(values);
    }

    @Test
    public void checkRetainsAll() {
        List<E> values = newTestList(5);
        List<E> retained = Arrays.asList(values.get(0), values.get(4), values.get(2));
        fill(values);
        retainAll(retained);
        assertSetsMatch(set, javaSet);
    }
}
