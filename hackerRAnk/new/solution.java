public class Solution {

    // Problem: Given an array of ints, 
    // return the length of the smallest contiguous subarray with sum at least target. 
    // Describe your approach as you go.

    public int smallestLengthOfTarget( int [] nums, int target ) {
        int head = 0;
        int tail = 0;
        int sum = nums[0]; 
        int minLen = Integer.MAX_VALUE;

        while ( head < nums.length ) {
            // if sum < target -> move head ahead;
            if ( sum < target ) {
                head++;
                sum+= nums[head];
            }
            // Shrink window as much as possible
            while (sum >= target) {
                minLen = Math.min(minLen, right - left + 1);

                sum -= nums[left];
                left++;
            }
        }

        return minLen == Integer.MAX_VALUE ? 0 : minLen;
    }
