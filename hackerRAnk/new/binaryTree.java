public class Solution {

    // Nums is a accending sorted array. Find the position to insert the target or return the index of
    // Target if existed in the array number
    // Do not use recursive

    // [0,1,3,5,7,9].  10    6
    // [0 3 4 8 9 10 12]. 9
     // [ 3 4 8 9 10 12].  0
    public int binarySearchPosition( int target, int[] nums ) {
        // just in case
        Arrays.sort(nums);

        int r = nums.length;
        int l = 0;

        while ( l < r ) {
            mid = (l+r) / 2;
            if ( nums[mid]== target ) return mid;
            else if(nums[mid] > target) {
                r = mid - 1;
            } else {
                l = mid + 1;
            }
        }

        return -1;

    }

    // [0,1,3,5,7,9].  10    6
    // [0 3 4 8 9 10 12]. 9
    // [ 3 4 8 10 11 12].  0.  9
    public int findInsertPosition( int target, int[] nums ) {
        int r = nums.length;
        int l = 0;

        if (target < nums[l]) return 0;
        if (target > nums[r-1]) return r+1;

        int id = 0;
        while ( l < r ) {
            int mid = (l+r) / 2;
            if ( nums[mid]== target ) return mid;
            else if(nums[mid] > target) {
                r = mid - 1;
                id = l;
            } else {
                l = mid + 1;
                id = r;
            }
        }

        return id + 1;
    }

}