package edu.ktu.ds.lab2.demo;

import edu.ktu.ds.lab2.utils.BstSet;
import edu.ktu.ds.lab2.utils.Set;
import edu.ktu.ds.lab2.utils.SortedSet;
import org.junit.Test;

import java.util.Comparator;
import java.util.List;

public abstract class SortedSetTests<E extends Comparable<E>> extends SetTests<E> {
    @Test
    public void checkHeadSet() {
        List<E> values = newTestList(5);
        fill(values);
        values.sort(Comparator.naturalOrder());
        Set<E> headSet = ((SortedSet<E>)set).headSet(values.get(3));
        assertSetsMatch(headSet, values.subList(0, 4));
    }

    @Test
    public void checkTailSet() {
        List<E> values = newTestList(5);
        fill(values);
        values.sort(Comparator.naturalOrder());
        Set<E> headSet = ((SortedSet<E>)set).tailSet(values.get(2));
        assertSetsMatch(headSet, values.subList(2, 5));
    }

    @Test
    public void checkSubSet() {
        List<E> values = newTestList(5);
        fill(values);
        values.sort(Comparator.naturalOrder());
        Set<E> headSet = ((SortedSet<E>)set).subSet(values.get(1), values.get(3));
        assertSetsMatch(headSet, values.subList(1, 4));
    }
}
