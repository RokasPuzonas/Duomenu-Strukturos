package edu.ktu.ds.lab2.optional1;


import edu.ktu.ds.lab2.utils.BstSet;

public class Main {
    public static void main(String[] args) {
        int k = 2;
        BstSet<Integer> set = new BstSet<>();
        BstSet.BstNode<Integer> a = set.getRoot();
        set.add(15);
        set.add(23);
        set.add(7);
        set.add(4);
        set.add(1);
        set.add(9);
        System.out.println(set.toVisualizedString());

        System.out.println(IsBalanced(set.getRoot(), k) != -1);
    }

    public static <E> int IsBalanced(BstSet.BstNode<E> node, int maxBalance) {
        if (node.left == null && node.right == null) return 0;

        int leftHeight = -1;
        int rightHeight = -1;
        if (node.left != null) {
            leftHeight = IsBalanced(node.left, maxBalance);
            if (leftHeight == -1) return -1;
        }
        if (node.right != null) {
            rightHeight = IsBalanced(node.right, maxBalance);
            if (rightHeight == -1) return -1;
        }

        if (Math.abs(leftHeight - rightHeight) >= maxBalance) return -1;

        return Math.max(leftHeight, rightHeight) + 1;
    }

}
