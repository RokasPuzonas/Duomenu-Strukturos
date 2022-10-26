package com.ktu;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.stream.Collectors;

public class Optional2 {
    static int[] slidingMaximum(int[] nums, int m)
    {
        int[] ans = new int[nums.length - m + 1];
        Deque<Integer> d = new ArrayDeque<>();

        for (int i = 0; i < nums.length; i++) {
            int num = nums[i];
            while (!d.isEmpty() && nums[d.getLast()] < num) {
                d.removeLast();
            }
            d.addLast(i);
            if (d.getFirst() == i - m) {
                d.removeFirst();
            }
            if (i >= m-1) {
                ans[i - m + 1] = nums[d.getFirst()];
            }
        }

        return ans;
    }

    public static void main(String args[])
    {
        int m = 3;
        int[] numbers = { 8, 7, 5, 4, 3, 1 };

        int[] ans = slidingMaximum(numbers, m);
        System.out.println(Arrays.stream(ans).mapToObj(String::valueOf).collect(Collectors.joining(", ")));
    }
}
