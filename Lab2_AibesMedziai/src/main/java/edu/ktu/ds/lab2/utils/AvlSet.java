package edu.ktu.ds.lab2.utils;

import java.util.Comparator;

/**
 * Rikiuojamos objektų kolekcijos - aibės realizacija AVL-medžiu.
 *
 * @param <E> Aibės elemento tipas. Turi tenkinti interfeisą Comparable<E>, arba
 *            per klasės konstruktorių turi būti paduodamas Comparator<E>
 *            interfeisą tenkinantis objektas.
 * @author darius.matulis@ktu.lt
 * @užduotis Peržiūrėkite ir išsiaiškinkite pateiktus metodus.
 */
public class AvlSet<E extends Comparable<E>> extends BstSet<E> implements SortedSet<E> {

    public AvlSet() {
    }

    public AvlSet(Comparator<? super E> c) {
        super(c);
    }

    /**
     * Aibė papildoma nauju elementu.
     *
     * @param element
     */
    @Override
    public void add(E element) {
        if (element == null) {
            throw new IllegalArgumentException("Element is null in add(E element)");
        }
        root = addRecursive(element, (AVLNode<E>) root);
    }

    /**
     * Privatus rekursinis metodas naudojamas add metode;
     *
     * @param element
     * @param node
     * @return
     */
    private AVLNode<E> addRecursive(E element, AVLNode<E> node) {
        if (node == null) {
            size++;
            return new AVLNode<>(element);
        }
        int cmp = c.compare(element, node.element);

        if (cmp < 0) {
            node.left = addRecursive(element, (AVLNode<E>) node.left);
        } else if (cmp > 0) {
            node.right = addRecursive(element, (AVLNode<E>) node.right);
        }

        if (cmp != 0) {
            int balance = getBalance(node);
            if (balance >= 2) {
                int cmp2 = c.compare(element, node.left.element);
                node = (cmp2 < 0) ? rightRotation(node) : doubleRightRotation(node);
            } else if (balance <= -2) {
                int cmp2 = c.compare(node.right.element, element);
                node = (cmp2 < 0) ? leftRotation(node) : doubleLeftRotation(node);
            }
        }

        node.updateHeight();
        return node;
    }

    /**
     * Pašalinamas elementas iš aibės.
     *
     * @param element
     */
    @Override
    public void remove(E element) {
        if (element == null) {
            throw new IllegalArgumentException("Element is null in remove(E element)");
        }
        root = removeRecursive(element, (AVLNode<E>) root);
    }

    private int getBalance(AVLNode<E> node) {
        if (node == null) return 0;
        return height((AVLNode<E>) node.left) - height((AVLNode<E>) node.right);
    }

    private AVLNode<E> removeRecursive(E element, AVLNode<E> node) {
        if (node == null) return null;

        int cmp = c.compare(element, node.element);

        if (cmp < 0) {
            node.left = removeRecursive(element, (AVLNode<E>) node.left);
        } else if (cmp > 0) {
            node.right = removeRecursive(element, (AVLNode<E>) node.right);
        } else {
            size--;
            if (node.left == null && node.right == null) {
                node = null;
            } else if (node.left != null && node.right != null) {
                if (node.right.left != null) {
                    BstNode<E> minimumParent = node;
                    BstNode<E> minimumNode = node.right;

                    while (minimumNode.left != null) {
                        minimumParent = minimumNode;
                        minimumNode = minimumNode.left;
                    }

                    minimumParent.left = minimumNode.right;
                    node.element = minimumNode.element;
                } else {
                    node.element = node.right.element;
                    node.right = node.right.right;
                }
            } else if (node.left != null) {
                node = (AVLNode<E>) node.left;
            } else { // if (node.right != null)
                node = (AVLNode<E>) node.right;
            }
        }

        if (node != null) {
            node.updateHeight();
            if (cmp != 0) {
                int balance = getBalance(node);
                if (balance >= 2) {
                    int hl = node.left.left != null ? ((AVLNode<E>) node.left.left).height : -1;
                    int hr = node.left.right != null ? ((AVLNode<E>) node.left.right).height : -1;
                    node = (hl >= hr) ? rightRotation(node) : doubleRightRotation(node);
                } else if (balance <= -2) {
                    int hl = node.right.left != null ? ((AVLNode<E>) node.right.left).height : -1;
                    int hr = node.right.right != null ? ((AVLNode<E>) node.right.right).height : -1;
                    node = (hr >= hl) ? leftRotation(node) : doubleLeftRotation(node);
                }
            }
        }

        return node;
    }

    // Papildomi privatūs metodai, naudojami operacijų su aibe realizacijai
    // AVL-medžiu;

    //           n2
    //          /                n1
    //         n1      ==>      /  \
    //        /                n3  n2
    //       n3

    private AVLNode<E> rightRotation(AVLNode<E> n2) {
        AVLNode<E> n1 = (AVLNode<E>) n2.left;
        n2.left = n1.right;
        n1.right = n2;
        n2.updateHeight();
        n1.updateHeight();
        return n1;
    }

    private AVLNode<E> leftRotation(AVLNode<E> n1) {
        AVLNode<E> n2 = (AVLNode<E>) n1.right;
        n1.right = n2.left;
        n2.left = n1;
        n1.updateHeight();
        n2.updateHeight();
        return n2;
    }

    //            n3               n3
    //           /                /                n2
    //          n1      ==>      n2      ==>      /  \
    //           \              /                n1  n3
    //            n2           n1
    //
    private AVLNode<E> doubleRightRotation(AVLNode<E> n3) {
        n3.left = leftRotation((AVLNode<E>) n3.left);
        return rightRotation(n3);
    }

    private AVLNode<E> doubleLeftRotation(AVLNode<E> n1) {
        n1.right = rightRotation((AVLNode<E>) n1.right);
        return leftRotation(n1);
    }

    public String serialize() {
        return serialize((AVLNode<E>) root);
    }

    private String serialize(AVLNode<E> node) {
        StringBuilder builder = new StringBuilder();
        if (node.left == null && node.right == null) {
            builder.append(node.element);
        } else {
            builder.append("(");
            if (node.left != null) {
                builder.append(serialize((AVLNode<E>) node.left));
            } else {
                builder.append("null");
            }
            builder.append(",");
            builder.append(node.element);
            builder.append(",");
            if (node.right != null) {
                builder.append(serialize((AVLNode<E>) node.right));
            } else {
                builder.append("null");
            }
            builder.append(")");
        }
        return builder.toString();
    }

    private int height(AVLNode<E> n) {
        return (n == null) ? -1 : n.height;
    }

    /**
     * Vidinė kolekcijos mazgo klasė
     *
     * @param <N> mazgo elemento duomenų tipas
     */
    protected class AVLNode<N> extends BstNode<N> {
        protected int height;

        protected AVLNode(N element) {
            super(element);
            this.height = 0;
        }

        public void updateHeight() {
            height = Math.max(height((AVLNode<E>) this.left), height((AVLNode<E>) this.right)) + 1;
        }
    }
}
