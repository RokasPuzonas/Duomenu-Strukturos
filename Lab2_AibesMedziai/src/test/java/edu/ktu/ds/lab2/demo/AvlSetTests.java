package edu.ktu.ds.lab2.demo;

import edu.ktu.ds.lab2.utils.AvlSet;
import edu.ktu.ds.lab2.utils.Set;
import org.junit.Assert;
import org.junit.Test;

import java.io.Console;
import java.util.List;
import java.util.Random;

public class AvlSetTests extends BstSetTests {
    public Set<Integer> newSet() {
        return new AvlSet<>();
    }

    @Test
    public void checkAddRightTurn() {
        AvlSet<Integer> avl = (AvlSet<Integer>) set;
        avl.add(3);
        avl.add(2);
        avl.add(1);
        Assert.assertEquals(avl.serialize(), "(1,2,3)");
    }

    @Test
    public void checkAddDoubleRightTurn() {
        AvlSet<Integer> avl = (AvlSet<Integer>) set;
        avl.add(3);
        avl.add(1);
        avl.add(2);
        Assert.assertEquals(avl.serialize(), "(1,2,3)");
    }

    @Test
    public void checkAddLeftTurn() {
        AvlSet<Integer> avl = (AvlSet<Integer>) set;
        avl.add(1);
        avl.add(2);
        avl.add(3);
        Assert.assertEquals(avl.serialize(), "(1,2,3)");
    }

    @Test
    public void checkAddDoubleLeftTurn() {
        AvlSet<Integer> avl = (AvlSet<Integer>) set;
        avl.add(2);
        avl.add(1);
        avl.add(3);
        Assert.assertEquals(avl.serialize(), "(1,2,3)");
    }

    @Test
    public void checkRemoveRightTurn() {
        AvlSet<Integer> avl = (AvlSet<Integer>) set;
        avl.add(1);
        avl.add(2);
        avl.add(3);
        avl.add(0);
        avl.remove(3);
        Assert.assertEquals(avl.serialize(), "(0,1,2)");
    }

    @Test
    public void checkRemoveDoubleRightTurn() {
        AvlSet<Integer> avl = (AvlSet<Integer>) set;
        avl.add(1);
        avl.add(3);
        avl.add(4);
        avl.add(2);
        avl.remove(4);
        Assert.assertEquals(avl.serialize(), "(1,2,3)");
    }

    @Test
    public void checkRemoveLeftTurn() {
        AvlSet<Integer> avl = (AvlSet<Integer>) set;
        avl.add(2);
        avl.add(1);
        avl.add(3);
        avl.add(4);
        avl.remove(1);
        Assert.assertEquals(avl.serialize(), "(2,3,4)");
    }

    @Test
    public void checkRemoveDoubleLeftTurn() {
        AvlSet<Integer> avl = (AvlSet<Integer>) set;
        avl.add(1);
        avl.add(2);
        avl.add(4);
        avl.add(3);
        avl.remove(1);
        Assert.assertEquals(avl.serialize(), "(2,3,4)");
    }

    @Test
    public void checkMassRemoving() {
        Random r = new Random();
        for (int i = 0; i < 50; i++) {
            add(r.nextInt(1000));
        }
        assertSetsMatch(set, javaSet);
        while (set.size() > 0) {
            int index = r.nextInt(set.size());
            remove((Integer) set.toArray()[index]);
            assertSetsMatch(set, javaSet);
        }

    }
}
