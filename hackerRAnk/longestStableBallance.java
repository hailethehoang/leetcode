// Challenge 4 — Longest Stable Balance Period

// Given an array changes representing daily account balance changes, find the length of the longest contiguous subarray whose sum equals 0.

// Return 0 if no such subarray exists.

// Example

// changes = [3, 4, -7, 5, -5, 2]

// Output: 5

// The subarray [3, 4, -7, 5, -5] sums to zero and has length 5.

// Additional examples

// Input	Output
// [1, -1, 3, -3]	4
// [1, 2, 3]	0
// [0, 0, 0]	3
// [2, 1, -1, 3]	2
// [5, -2, -3, 4]	3

import java.util.*;

class Result {

    /*
     * Complete 'longestZeroSumPeriod'.
     *
     * Returns:
     *   int: length of the longest contiguous subarray
     *        whose sum is zero.
     */
    public static int longestZeroSumPeriod(int[] changes) {
    int res = 0;
    long[] subSumsArray = new long[changes.length];

    for (int i = 0; i < changes.length; i++) {
        for (int j = 0; j <= i; j++) {
            subSumsArray[j] += changes[i];

            if (subSumsArray[j] == 0) {
                res = Math.max(res, i - j + 1);
            }
        }
    }

    return res;
}


// better O(n)
public static int longestZeroSumPeriod(int[] changes) {
    Map<Long, Integer> firstSeen = new HashMap<>();
    firstSeen.put(0L, -1);

    long sum = 0;
    int res = 0;

    for (int i = 0; i < changes.length; i++) {
        sum += changes[i];

        if (firstSeen.containsKey(sum)) {
            res = Math.max(res, i - firstSeen.get(sum));
        } else {
            firstSeen.put(sum, i);
        }
    }

    return res;
}

public class Main {
    public static void main(String[] args) {
        System.out.println(Result.longestZeroSumPeriod(
                new int[]{3, 4, -7, 5, -5, 2}));
        // Expected: 5

        System.out.println(Result.longestZeroSumPeriod(
                new int[]{1, -1, 3, -3}));
        // Expected: 4

        System.out.println(Result.longestZeroSumPeriod(
                new int[]{1, 2, 3}));
        // Expected: 0

        System.out.println(Result.longestZeroSumPeriod(
                new int[]{0, 0, 0}));
        // Expected: 3

        System.out.println(Result.longestZeroSumPeriod(
                new int[]{2, 1, -1, 3}));
        // Expected: 2

        System.out.println(Result.longestZeroSumPeriod(
                new int[]{5, -2, -3, 4}));
        // Expected: 3
    }
}