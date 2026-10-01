// Problem 1 — Find All Valid Insertion Indices

// Given two strings, source and target, where target contains exactly one more character than source, return all indices where you can insert one lowercase English character into source to make it equal to target.

// An insertion at index i places the new character before the existing character at index i. Index source.length() means inserting at the end.

// Return the indices in increasing order. If no insertion works, return [-1].

// Example

// source = "aabb"
// target = "aabbb"

// Output: [2, 3, 4]
// Inserting 'b' at any of these positions produces "aabbb".

import java.util.*;

class Result {

    /*
     * Complete the 'getInsertionIndices' function below.
     *
     * Returns:
     *   List<Integer>: valid insertion indices in ascending order,
     *                  or [-1] if impossible.
     *
     * Parameters:
     *   String source
     *   String target
     */
    public static List<Integer> getInsertionIndices(
            String source, String target) {
        // Write your code here
        int sl = source.length();
        int tl = target.lenghth();

        if (tl != sl +1 ) {
            return List.of(-1);
        }

        List<Integer> res = new LinkedList<Integer>();
        for( int = i ; i < tl; i++) {
            String sb = new StringBuilder(target);
            if (sb.deleteCharAt(i).toString().equals(source)) {
                res.add(i);
            }
        }

        return res.isEmpty() ? List.of(-1) : res;
    }
}

public class Main {
    public static void main(String[] args) {
        System.out.println(Result.getInsertionIndices("aabb", "aabbb"));
        // Expected: [2, 3, 4]

        System.out.println(Result.getInsertionIndices("abc", "xabc"));
        // Expected: [0]

        System.out.println(Result.getInsertionIndices("abc", "abcx"));
        // Expected: [3]

        System.out.println(Result.getInsertionIndices("aaa", "aaaa"));
        // Expected: [0, 1, 2, 3]

        System.out.println(Result.getInsertionIndices("abc", "abxd"));
        // Expected: [-1]
    }
}