package edu.ktu.ds.lab2.demo;

import edu.ktu.ds.lab2.utils.BstSetIterative;
import edu.ktu.ds.lab2.utils.Set;

import java.util.Random;

public class BstSetIterativeTests extends BstSetTests {
    @Override
    public Set<Integer> newSet() {
        return new BstSetIterative<>();
    }
}
