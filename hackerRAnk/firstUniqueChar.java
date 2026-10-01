// Challenge 2 — First Unique Character

// Given a string s, return the index of its first non-repeating character. If every character repeats, return -1.

// Use 0-based indexing.

// Examples

// Input	Output	Explanation
// "leetcode"	0	'l' appears once
// "loveleetcode"	2	'v' is the first character that appears once
// "aabb"	-1	Every character repeats
// "z"	0	'z' appears once

// Constraints

// 1 ≤ s.length() ≤ 200,000
// s contains only lowercase English letters.

class Result {

    /*
     * Complete the 'firstUniqueChar' function below.
     *
     * Returns:
     *   int: index of the first non-repeating character,
     *        or -1 if none exists.
     *
     * Parameters:
     *   String s
     */
    public static int firstUniqueChar(String s) {
        // Write your code here
        char[] ar = s.toCharArray();
        boolean isFirst = true ;
        for (int i = 0 ; i < ar.length - 1; i++ ) {
            if (isFirst) {
                if (ar[i] != ar[i+1]) {
                    return i;
                } else {
                    isFirst = false;
                }
            } else {
                isFirst =true;
                continue;
            }
        }

        return isFirst ? s.length : -1;
    }

     public static int firstUniqueChar(String s) {
        // Write your code here
        char[] ar = s.toCharArray();
        HashMap<Character, Integer> m = new HashMap<>();

        for (int i = 0 ; i < ar.length; i++ ) {
            int times = m.getOrDefault(ar[i], 0);
            m.put(ar[i], times+1);
        }

        for (int i = 0 ; i < ar.length; i++ ) {
            if(m.get(ar[i])==1) return i;
        }

        return -1;
    }
}

public class Main {
    public static void main(String[] args) {
        System.out.println(Result.firstUniqueChar("leetcode"));
        // Expected: 0

        System.out.println(Result.firstUniqueChar("loveleetcode"));
        // Expected: 2

        System.out.println(Result.firstUniqueChar("aabb"));
        // Expected: -1

        System.out.println(Result.firstUniqueChar("z"));
        // Expected: 0
    }
}