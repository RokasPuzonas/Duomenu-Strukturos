package edu.ktu.ds.lab2.utils;

import java.util.Comparator;

/**
 * Klasė paveldi klasę BstSet ir perdengia add metodą iteracine realizacija
 *
 * @param <E>
 * @author darius
 */
public class BstSetIterative<E extends Comparable<E>> extends BstSet<E> implements SortedSet<E> {

    public BstSetIterative() {
        super();
    }

    public BstSetIterative(Comparator<? super E> c) {
        super(c);
    }

    /**
     * Aibė papildoma nauju elementu. Papildymas atliekamas iteracijos į gylį
     *
     * @param element
     */
    @Override
    public void add(E element) {
        if (element == null) {
            throw new IllegalArgumentException("Element is null in add(E element)");
        }

        if (root == null) {
            root = new BstNode<E>(element);
        } else {
            BstNode<E> current = root;
            BstNode<E> parent = null;
            while (current != null) {
                parent = current;
                int cmp = c.compare(element, current.element);
                if (cmp < 0) {
                    current = current.left;
                } else if (cmp > 0) {
                    current = current.right;
                } else {
                    return;
                }
            }

            int cmp = c.compare(element, parent.element);
            if (cmp < 0) {
                parent.left = new BstNode<E>(element);
            } else if (cmp > 0) {
                parent.right = new BstNode<E>(element);
            }
        }
        size++;
    }

    /**
     * Pašalinamas elementas iš aibės.
     *
     * @param element - Aibės elementas.
     */
    @Override
    public void remove(E element) {
        if (element == null) {
            throw new IllegalArgumentException("Element is null in remove(E element)");
        }
        if (root == null) return;

        BstNode<E> current = root;
        BstNode<E> parent = null;

        while (current != null) {
            int cmp = c.compare(element, current.element);
            if (cmp == 0) break;

            parent = current;
            if (cmp < 0) {
                current = current.left;
            } else { //if (cmp > 0) {
                current = current.right;
            }
        }

        if (current == null) {
            return;
        } else if (current.right != null && current.left != null) {
            BstNode<E> rightMinimumParent = null;
            BstNode<E> rightMinimum = current.right;

            while (rightMinimum.left != null) {
                rightMinimumParent = rightMinimum;
                rightMinimum = rightMinimum.left;
            }

            if (rightMinimumParent != null) {
                rightMinimumParent.left = rightMinimum.right;
            } else {
                current.right = rightMinimum.right;
            }

            current.element = rightMinimum.element;
        } else {
            BstNode<E> newCurrent = current.left == null ? current.right : current.left;

            if (parent == null) {
                root = newCurrent;
            } else if (parent.left == current) {
                parent.left = newCurrent;
            } else {
                parent.right = newCurrent;
            }
        }

        size--;
    }
}
