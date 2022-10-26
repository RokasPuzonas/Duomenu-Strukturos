package edu.ktu.ds.lab2.utils;

import java.util.Comparator;
import java.util.Iterator;
import java.util.Stack;

/**
 * Rikiuojamos objektų kolekcijos - aibės realizacija dvejetainiu paieškos
 * medžiu.
 *
 * @param <E> Aibės elemento tipas. Turi tenkinti interfeisą Comparable<E>, arba
 *            per klasės konstruktorių turi būti paduodamas Comparator<E> interfeisą
 *            tenkinantis objektas.
 * @author darius.matulis@ktu.lt
 * @užduotis Peržiūrėkite ir išsiaiškinkite pateiktus metodus.
 */
public class BstSet<E extends Comparable<E>> implements SortedSet<E>, Cloneable {

    // Medžio šaknies mazgas
    protected BstNode<E> root = null;
    // Medžio dydis
    protected int size = 0;
    // Rodyklė į komparatorių
    protected Comparator<? super E> c;

    /**
     * Sukuriamas aibės objektas DP-medžio raktams naudojant Comparable<E>
     */
    public BstSet() {
        this.c = Comparator.naturalOrder();
    }

    /**
     * Sukuriamas aibės objektas DP-medžio raktams naudojant Comparator<E>
     *
     * @param c Komparatorius
     */
    public BstSet(Comparator<? super E> c) {
        this.c = c;
    }

    /**
     * Patikrinama ar aibė tuščia.
     *
     * @return Grąžinama true, jei aibė tuščia.
     */
    @Override
    public boolean isEmpty() {
        return root == null;
    }

    /**
     * @return Grąžinamas aibėje esančių elementų kiekis.
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Išvaloma aibė.
     */
    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    /**
     * Patikrinama ar aibėje egzistuoja elementas.
     *
     * @param element - Aibės elementas.
     * @return Grąžinama true, jei aibėje egzistuoja elementas.
     */
    @Override
    public boolean contains(E element) {
        if (element == null) {
            throw new IllegalArgumentException("Element is null in contains(E element)");
        }

        return get(element) != null;
    }

    /**
     * Patikrinama ar visi abės set elementai egzistuoja aibėje
     *
     * @param set aibė
     * @return
     */
    @Override
    public boolean containsAll(Set<E> set) {
        for (E e : set) {
            if (!contains(e)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Aibė papildoma nauju elementu.
     *
     * @param element - Aibės elementas.
     */
    @Override
    public void add(E element) {
        if (element == null) {
            throw new IllegalArgumentException("Element is null in add(E element)");
        }

        root = addRecursive(element, root);
    }

    /**
     * Abės set elementai pridedami į esamą aibę, jeigu abi aibės turi tą patį elementą, jis nėra dedamas.
     *
     * @param set pridedamoji aibė
     */
    @Override
    public void addAll(Set<E> set) {
        for (E element : set) {
            add(element);
        }
    }

    private BstNode<E> addRecursive(E element, BstNode<E> node) {
        if (node == null) {
            size++;
            return new BstNode<>(element);
        }

        int cmp = c.compare(element, node.element);

        if (cmp < 0) {
            node.left = addRecursive(element, node.left);
        } else if (cmp > 0) {
            node.right = addRecursive(element, node.right);
        }

        return node;
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

        root = removeRecursive(element, root);
    }

    /**
     * Aibėje lieka tik tie elementai, kurie yra aibėje set.
     *
     * @param set aibė
     */
    @Override
    public void retainAll(Set<E> set) {
        for (E e : this) {
            if (!set.contains(e)) {
                remove(e);
            }
        }
    }

    private BstNode<E> removeRecursive(E element, BstNode<E> node) {
        if (node == null) return null;

        int cmp = c.compare(element, node.element);

        if (cmp < 0) {
            node.left = removeRecursive(element, node.left);
        } else if (cmp > 0) {
            node.right = removeRecursive(element, node.right);
        } else {
            size--;
            if (node.left == null && node.right == null) {
                return null;
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
                return node.left;
            } else { // if (node.right != null)
                return node.right;
            }
        }

        return node;
    }

    private E get(E element) {
        if (element == null) {
            throw new IllegalArgumentException("Element is null in get(E element)");
        }

        BstNode<E> node = root;
        while (node != null) {
            int cmp = c.compare(element, node.element);

            if (cmp < 0) {
                node = node.left;
            } else if (cmp > 0) {
                node = node.right;
            } else {
                return node.element;
            }
        }

        return null;
    }

    /**
     * Grąžinamas aibės elementų masyvas.
     *
     * @return Grąžinamas aibės elementų masyvas.
     */
    @Override
    public Object[] toArray() {
        int i = 0;
        Object[] array = new Object[size];
        for (Object o : this) {
            array[i++] = o;
        }
        return array;
    }

    /**
     * Aibės elementų išvedimas į String eilutę Inorder (Vidine) tvarka. Aibės
     * elementai išvedami surikiuoti didėjimo tvarka pagal raktą.
     *
     * @return elementų eilutė
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        boolean isFirst = true;
        for (E element : this) {
            if (!isFirst) {
                sb.append(", ");
            }
            sb.append(element.toString());
            isFirst = false;
        }
        sb.append("]");
        return sb.toString();
    }

    /**
     * Medžio vaizdavimas simboliais, žiūr.: unicode.org/charts/PDF/U2500.pdf
     * Tai 4 galimi terminaliniai simboliai medžio šakos gale
     */
    private static final String[] term = {"\u2500", "\u2534", "\u252C", "\u253C"};
    private static final String rightEdge = "\u250C";
    private static final String leftEdge = "\u2514";
    private static final String endEdge = "\u25CF";
    private static final String vertical = "\u2502  ";
    private String horizontal;

    /* Papildomas metodas, išvedantis aibės elementus į vieną String eilutę.
     * String eilutė formuojama atliekant elementų postūmį nuo krašto,
     * priklausomai nuo elemento lygio medyje. Galima panaudoti spausdinimui į
     * ekraną ar failą tyrinėjant medžio algoritmų veikimą.
     *
     * @author E. Karčiauskas
     */
    @Override
    public String toVisualizedString() {
        horizontal = term[0] + term[0];
        return root == null ? ">" + horizontal : toTreeDraw(root, ">", "");
    }

    private String toTreeDraw(BstNode<E> node, String edge, String indent) {
        if (node == null) { return ""; }
        String step = (edge.equals(leftEdge)) ? vertical : "   ";
        StringBuilder sb = new StringBuilder();
        sb.append(toTreeDraw(node.right, rightEdge, indent + step));

        int t = (node.right != null ? 1 : 0) + (node.left != null ? 2 : 0);
        sb.append(indent).append(edge).append(horizontal);
        sb.append(term[t]).append(endEdge).append(node.element.toString());
        sb.append(System.lineSeparator());
        step = (edge.equals(rightEdge)) ? vertical : "   ";
        sb.append(toTreeDraw(node.left, leftEdge, indent + step));
        return sb.toString();
    }

    /**
     * Sukuria ir grąžina aibės kopiją.
     *
     * @return Aibės kopija.
     * @throws java.lang.CloneNotSupportedException
     */
    @Override
    public Object clone() throws CloneNotSupportedException {
        BstSet<E> cl = (BstSet<E>) super.clone();
        if (root == null) {
            return cl;
        }
        cl.root = cloneRecursive(root);
        cl.size = this.size;
        return cl;
    }

    private BstNode<E> cloneRecursive(BstNode<E> node) {
        if (node == null) {
            return null;
        }

        BstNode<E> clone = new BstNode<>(node.element);
        clone.left = cloneRecursive(node.left);
        clone.right = cloneRecursive(node.right);
        return clone;
    }

    /**
     * Grąžinamas aibės poaibis iki elemento.
     *
     * @param element - Aibės elementas.
     * @return Grąžinamas aibės poaibis iki elemento.
     */
    @Override
    public Set<E> headSet(E element) {
        Set<E> headSet = new BstSet<>();
        for (E e : this) {
            headSet.add(e);
            if (c.compare(e, element) == 0) break;
        }
        return headSet;
    }

    /**
     * Grąžinamas aibės poaibis nuo elemento element1 iki element2.
     *
     * @param element1 - pradinis aibės poaibio elementas.
     * @param element2 - galinis aibės poaibio elementas.
     * @return Grąžinamas aibės poaibis nuo elemento element1 iki element2.
     */
    @Override
    public Set<E> subSet(E element1, E element2) {
        Set<E> subSet = new BstSet<>();
        boolean seen1 = false;
        boolean seen2 = false;
        for (E e : this) {
            if (!seen1 && c.compare(e, element1) == 0) seen1 = true;

            if (seen1 && !seen2) {
                subSet.add(e);
            }

            if (!seen2 && c.compare(e, element2) == 0) seen2 = true;
        }
        return subSet;
    }

    /**
     * Grąžinamas aibės poaibis nuo elemento.
     *
     * @param element - Aibės elementas.
     * @return Grąžinamas aibės poaibis nuo elemento.
     */
    @Override
    public Set<E> tailSet(E element) {
        Set<E> tailSet = new BstSet<>();
        boolean seen = false;
        for (E e : this) {
            if (!seen && c.compare(e, element) == 0) seen = true;

            if (seen) {
                tailSet.add(e);
            }
        }
        return tailSet;
    }

    public BstNode<E> getRoot() {
        return root;
    }

    /**
     * Grąžinamas tiesioginis iteratorius.
     *
     * @return Grąžinamas tiesioginis iteratorius.
     */
    @Override
    public Iterator<E> iterator() {
        return new IteratorBst(true);
    }

    /**
     * Grąžinamas atvirkštinis iteratorius.
     *
     * @return Grąžinamas atvirkštinis iteratorius.
     */
    @Override
    public Iterator<E> descendingIterator() {
        return new IteratorBst(false);
    }

    /**
     * Vidinė objektų kolekcijos iteratoriaus klasė. Iteratoriai: didėjantis ir
     * mažėjantis. Kolekcija iteruojama kiekvieną elementą aplankant vieną kartą
     * vidine (angl. inorder) tvarka. Visi aplankyti elementai saugomi steke.
     * Stekas panaudotas iš java.util paketo, bet galima susikurti nuosavą.
     */
    private class IteratorBst implements Iterator<E> {

        private final Stack<BstNode<E>> stack = new Stack<>();
        // Nurodo iteravimo kolekcija kryptį, true - didėjimo tvarka, false - mažėjimo
        private final boolean ascending;
        // Reikalinga remove() metodui.
        private BstNode<E> lastInStack;
        private BstNode<E> last;

        IteratorBst(boolean ascendingOrder) {
            this.ascending = ascendingOrder;
            this.toStack(root);
        }

        @Override
        public boolean hasNext() {
            return !stack.empty();
        }

        @Override
        public E next() {// Jei stekas tuščias
            if (stack.empty()) {
                lastInStack = root;
                last = null;
                return null;
            } else {
                // Grąžinamas paskutinis į steką patalpintas elementas
                BstNode<E> n = stack.pop();
                // Atsimenamas paskutinis grąžintas elementas, o taip pat paskutinis steke esantis elementas.
                // Reikia remove() metodui
                lastInStack = stack.isEmpty() ? root : stack.peek();
                last = n;
                BstNode<E> node = (ascending) ? n.right : n.left;
                // Dešiniajame n pomedyje ieškoma minimalaus elemento,
                // o visi paieškos kelyje esantys elementai talpinami į steką
                toStack(node);
                return n.element;
            }
        }

        @Override
        public void remove() {
            root = removeRecursive(lastInStack.element, root);
        }

        private void toStack(BstNode<E> n) {
            while (n != null) {
                stack.push(n);
                n = (ascending) ? n.left : n.right;
            }
        }
    }

    /**
     * Vidinė kolekcijos mazgo klasė
     *
     * @param <N> mazgo elemento duomenų tipas
     */
    public static class BstNode<N> {

        // Elementas
        public N element;
        // Rodyklė į kairįjį pomedį
        public BstNode<N> left;
        // Rodyklė į dešinįjį pomedį
        public BstNode<N> right;

        protected BstNode() {
        }

        protected BstNode(N element) {
            this.element = element;
            this.left = null;
            this.right = null;
        }
    }
}
