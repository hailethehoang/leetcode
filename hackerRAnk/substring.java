// Given two strings, str1, and str2, where str1 contains exactly one character more than str2, find the indices of the characters in str1 that can be removed to make str1 equal to str2. Return the array of indices in increasing order. If it is not possible, return the array [-1]. 
// Note: Use 0-based indexing.
// Example
// str1 = "abdgggda" str2 = "abdggda"
// Any "g" character at positions 3, 4, or 5 can be deleted to obtain str2. Return [3, 4, 5].
// Input Format
// Function Description
// Complete the function getRemovableIndices in the editor below.
// getRemovableIndices has the following parameters:
// string str1: the string to modify
// string str2: the target string
// Constraints
// Constraints
// 2 ≤ |str1| ≤ 2 * 10^5
// 1 ≤ |str2| ≤ 2 * 10^5
// |str1| = |str2| + 1 
// str1 and str2 only contain lowercase English letters.
// Output Format
// Output Format
//      int[]: the indices of characters that can be removed from str1 in ascending order, or [-1] if it is not possible to match str2
// Sample Input 0
// aabbb
// aabb
// Sample Output 0
// 2
// 3
// 4
// Explanation 0
// From str1, a character at indices 2, 3, or 4 can be removed to make it equal to str2.

// https://www.hackerrank.com/contests/mock-interviews-software-engineer-coding/challenges/string-difference-1-4/problem?isFullScreen=true

class Result {

    /*
     * Complete the 'getRemovableIndices' function below.
     *
     * The function is expected to return an INTEGER_ARRAY.
     * The function accepts following parameters:
     *  1. STRING str1
     *  2. STRING str2
     */

    public static List<Integer> getRemovableIndices(String str1, String str2) {
        // Write your code here
        
    int l1 = str1.length();
        int l2 = str2.length();

        if( l1 != l2 +1) {
            return List.of(-1);
        }

        List<Integer> res = new LinkedList<>();

        for (int i= 0 ; i < l1; i++) {
            String contruct = 
                str1.substring(0,i) + str1.substring(i+1,l1);
            
            if(contruct.equals(str2)) {
                res.add(i);
            }
        }

        return res.isEmpty() ? List.of(-1) : res;

    }



}

public static List<Integer> getRemovableIndices(String str1, String str2) {
    List<Integer> result = new ArrayList<>();

    for (int i = 0; i < str1.length(); i++) {
        StringBuilder sb = new StringBuilder(str1);
        sb.deleteCharAt(i);

        if (sb.toString().equals(str2)) {
            result.add(i);
        }
    }

    return result.isEmpty() ? List.of(-1) : result;
}

public static List<Integer> getRemovableIndices(String str1, String str2) {
    List<Integer> result = new ArrayList<>();

    if (str1.length() != str2.length() + 1) {
        return List.of(-1);
    }

    // Find first position where they differ
    int firstMismatch = 0;

    while (firstMismatch < str2.length()
            && str1.charAt(firstMismatch) == str2.charAt(firstMismatch)) {
        firstMismatch++;
    }

    // The character we remove must be str1[firstMismatch].
    char target = str1.charAt(firstMismatch);

    // Try consecutive occurrences of that character
    for (int i = firstMismatch;
         i < str1.length() && str1.charAt(i) == target;
         i++) {

        boolean valid = true;

        // Compare str1 and str2 while skipping str1[i]
        for (int j = 0; j < str2.length(); j++) {
            int str1Index = j < i ? j : j + 1;

            if (str1.charAt(str1Index) != str2.charAt(j)) {
                valid = false;
                break;
            }
        }

        if (valid) {
            result.add(i);
        }
    }

    return result.isEmpty() ? List.of(-1) : result;
}