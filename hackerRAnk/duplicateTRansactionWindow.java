// Challenge 3 — Duplicate Transactions Within a Window

// A payment system receives a sequence of transaction IDs. Return true if the same transaction ID appears at two different indices whose distance is at most k. Otherwise, return false.

// Formally, find whether there are indices i and j such that:

// i < j
// transactionIds[i] == transactionIds[j]
// j - i <= k

// Examples

// Transaction IDs	k	Output	Explanation
// [101, 202, 303, 101]	3	true	ID 101 repeats at indices 0 and 3
// [101, 202, 303, 101]	2	false	The repeated IDs are too far apart

import java.util.*;

class Result {

    /*
     * Returns true if the same transaction ID appears
     * at two different indices at most k positions apart.
     */
    public static boolean hasNearbyDuplicate(
            int[] transactionIds, int k) {
        HashMap<Integer, Integer> preDuplicate = new HashMap<>();
 
        for (int i = 0 ; i < transactionIds.length; i++ ) {
            if(preDuplicate.containsKey(transactionIds[i])) {
                if ( i - preDuplicate.get(transactionIds[i]) <= k ) {
                    return true;
                }
            } 
                 
            preDuplicate.put(transactionIds[i], i)
            
        }
        // Write your code here
        return false;
    }

    public static boolean hasNearbyDuplicate(
        int[] transactionIds, int k) {

    if (k == 0) return false;

    Set<Integer> window = new HashSet<>();

    for (int i = 0; i < transactionIds.length; i++) {
        // Keep only the previous k positions.
        if (i > k) {
            window.remove(transactionIds[i - k - 1]);
        }

        // add() returns false if the ID already exists.
        if (!window.add(transactionIds[i])) {
            return true;
        }
    }

    return false;
}
}

public class Main {
    public static void main(String[] args) {
        System.out.println(Result.hasNearbyDuplicate(
                new int[]{101, 202, 303, 101}, 3));
        // Expected: true

        System.out.println(Result.hasNearbyDuplicate(
                new int[]{101, 202, 303, 101}, 2));
        // Expected: false

        System.out.println(Result.hasNearbyDuplicate(
                new int[]{101, 202, 101, 101}, 1));
        // Expected: true

        System.out.println(Result.hasNearbyDuplicate(
                new int[]{101, 202, 303}, 2));
        // Expected: false

        System.out.println(Result.hasNearbyDuplicate(
                new int[]{101, 101}, 0));
        // Expected: false
    }
}