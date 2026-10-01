You are given an array of integers arr and a number k, 
return the number of subarrays with exatcly k odd numbers.
 
A subarray is a contiguous non-empty sequence of elements within an array.
 
Examples:
 
Input : arr = [2, 5, 6, 9],  k = 2 
Output: 2
Explanation: There are 2 subarrays with 2 odds: [2, 5, 6, 9] and [5, 6, 9].

public class Main {


    // 1 0 0 1. -> 1
    // 0 1 0 0 1 -> 2
    // 0 0 1 0 0 1. -> 3.  ;   0 0 0 1 0 0 1. -> 4
    // 0 0 1 0 0 1 0 -> 6.     0 0 0 1 0 0 1 0 -> 8
    // 0 0 1 0 0 1 0 0 -> 9
    // 0 0 1 0 0 1 0 0 0 -> 12
    // 0 0 1 0 0 1 0 0 0 1 -> 12 + 3
    // L.  R.    P
    // 0 0 1 0 0 0 1 0 0 0 1 -> 12 + 4
    //     L.      R.      Pointer

    // 0 0 1 0 0 0 1 0 0 0 1 1 ->16 + 3

    // -- -- --- --- --- -
    // 0 0 1.  0 0 0 3. 0 0  9.  0 0 0 0 3.
    // 0             6          
    //               3  6. 9 

    // index = odd / even
    //     odd: res = 1 + num Of event 
    //     even: res += (1 + num Of event ) + even af odds

    // MINE is wrong
    public int subArrayNumber( int[]arr, int k) {
        int right = 0
        int left = 0;
        int curOdd = 0; // maintain the current ODD item (nearest)
        int countOdd = 0;
        int subArray =0;

        int adding = 0; // adding x subarray when + an even element
        for (int i = 0 ; i < arr.length; i++) {
            // find next Odd
            if (arr[i] % 2 != 0) {
                left = right;
                right= curOdd; 
                curOdd = i; 
                countOdd++;
            } 

            if (countOdd >= k ) {
                subArray += (right - left +1) ;
            }

        }

        return res;
    }


    public long subArrayNumber(int[] arr, int k) {
        // Assumes k >= 1.
        int left = 0;
        int countOdd = 0;
        int adding = 0;
        long result = 0;

        for (int right = 0; right < arr.length; right++) {
            if (arr[right] % 2 != 0) {
                countOdd++;
                adding = 0; // Recalculate valid starts for the new odd.
            }

            while (countOdd == k) {
                adding++;

                if (arr[left] % 2 != 0) {
                    countOdd--;
                }

                left++;
            }

            result += adding;
        }

        return result;
    }


}