// hallenge 5 — Minimum Transactions to Reach a Target

// Given an array of positive transaction amounts and a positive target, return the length of the shortest contiguous subarray whose sum is greater than or equal to target.

// Return 0 if no such subarray exists.

// Example

// amounts = [2, 3, 1, 2, 4, 3]
// target = 7

// Output: 2

// The subarray [4, 3] has sum 7 and length 2. No single element reaches the target.

// Additional examples

// Amounts	Target	Output
// [1, 4, 4]	4	1
// [1, 1, 1, 1]	6	0
// [2, 3, 5]	10	3
// [5, 1, 3, 5, 10, 7]	15	2
// [8]	7	1

// Constraints

// 1 <= amounts.length <= 200,000
// 1 <= amounts[i] <= 1,000,000,000
// 1 <= target <= 10^14
// Elements must be consecutive; you cannot reorder or skip them.
class Result {

    /*
     * Returns the length of the shortest contiguous
     * subarray with sum >= target, or 0 if none exists.
     */
    public static int minTransactionCount(int[] amounts, long target) {
        // Write your code here
        HashMap<Integer, Integer> isSeen = new HashMap<>() ;
        int sum= 0 ;
        int res = 0 ;
        for( int i = 0; i < amounts.length ; i++) {
            if (amount[i]== target) return 1;

            sum += amounts[i];

            if ( isSeen.contains( target - sum) ) {
                res = Math.min( res, i - isSeen.get(target - sum) + 1);
            }
            isSeen.put(sum, i);

        }
        return res;
    }
}

public class Main {
    public static void main(String[] args) {
        System.out.println(Result.minTransactionCount(
                new int[]{2, 3, 1, 2, 4, 3}, 7L));
        // Expected: 2

        System.out.println(Result.minTransactionCount(
                new int[]{1, 4, 4}, 4L));
        // Expected: 1

        System.out.println(Result.minTransactionCount(
                new int[]{1, 1, 1, 1}, 6L));
        // Expected: 0

        System.out.println(Result.minTransactionCount(
                new int[]{2, 3, 5}, 10L));
        // Expected: 3

        System.out.println(Result.minTransactionCount(
                new int[]{5, 1, 3, 5, 10, 7}, 15L));
        // Expected: 2

        System.out.println(Result.minTransactionCount(
                new int[]{8}, 7L));
        // Expected: 1
    }
}