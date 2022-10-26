package edu.ktu.ds.lab2.optional2;

import edu.ktu.ds.lab2.utils.BstSet;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Stack;

public class Main {
    public static void main(String[] args) {
        BstSet<Integer> set = new BstSet<>();
        set.add(15);
        set.add(23);
        set.add(7);
        set.add(4);
        set.add(12);
        set.add(19);
        set.add(26);
        set.add(1);
        set.add(5);
        set.add(9);
        set.add(14);
        set.add(16);
        set.add(21);
        set.add(25);
        set.add(30);
        System.out.println(set.toVisualizedString());
        System.out.println(getInnerValues(set));
    }

    public static <E extends Comparable<E>> ArrayList<E> getInnerValues(BstSet<E> set) {
        HashSet<E> excludedValues = new HashSet<>();
        excludedValues.addAll(getLeftEdge(set));
        excludedValues.addAll(getRightEdge(set));
        excludedValues.addAll(getBottomEdge(set));

        ArrayList<E> innerValues = new ArrayList<>();
        for (E value : set) {
            if (!excludedValues.contains(value)) {
                innerValues.add(value);
            }
        }
        return innerValues;
    }

    public static <E extends Comparable<E>> ArrayList<E> getLeftEdge(BstSet<E> set) {
        ArrayList<E> leftEdge = new ArrayList<>();
        BstSet.BstNode<E> node = set.getRoot();
        while (node != null) {
            leftEdge.add(node.element);
            node = node.left;
        }
        return leftEdge;
    }

    public static <E extends Comparable<E>> ArrayList<E> getRightEdge(BstSet<E> set) {
        ArrayList<E> rightEdge = new ArrayList<>();
        BstSet.BstNode<E> node = set.getRoot();
        while (node != null) {
            rightEdge.add(node.element);
            node = node.right;
        }
        return rightEdge;
    }

    public static <E extends Comparable<E>> ArrayList<E> getBottomEdge(BstSet<E> set) {
        ArrayList<E> bottomEdge = new ArrayList<>();
        Stack<BstSet.BstNode<E>> stack = new Stack<>();
        stack.add(set.getRoot());
        while (!stack.isEmpty()) {
            BstSet.BstNode<E> node = stack.pop();
            if (node.left == null && node.right == null) {
                bottomEdge.add(node.element);
            } else {
                if (node.left != null) {
                    stack.push(node.left);
                }
                if (node.right != null) {
                    stack.push(node.right);
                }
            }
        }
        return bottomEdge;
    }

}
