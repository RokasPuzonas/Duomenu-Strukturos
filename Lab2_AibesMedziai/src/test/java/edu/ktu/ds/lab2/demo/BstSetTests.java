package edu.ktu.ds.lab2.demo;

import edu.ktu.ds.lab2.utils.BstSet;
import edu.ktu.ds.lab2.utils.Set;
import org.junit.Test;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class BstSetTests extends SortedSetTests<Integer> {

    @Override
    public Set<Integer> newSet() {
        return new BstSet<>();
    }

    @Override
    public Integer newElement() {
        return new Random().nextInt(1000);
    }
}
