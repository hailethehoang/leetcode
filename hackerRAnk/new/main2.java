// There is an integer array nums sorted in ascending order with distinct values.
// Prior to being passed to your function, nums is possibly rotated at 
// an unknown pivot index k (1 <= k < nums.length) 
// such that the resulting array is [nums[k], nums[k+1], ..., nums[n-1], nums[0], nums[1], ..., nums[k-1]].
// Example: [0,1,2,4,5,6,7] rotated at pivot index 3 becomes [4,5,6,7,0,1,2].
 
// Given the array nums after the possible rotation and an integer target, 
// return the index of target if it is in nums, or -1 if it is not in nums.
 
// Examples
// Input: nums = [4,5,6,7,0,1,2], target = 0 | Output: 4
// Input: nums = [4,5,6,7,0,1,2], target = 3 | Output: -1 
// Input: nums = [1], target = 0 | Output: -1
 
// Constraints
// 1 <= nums.length <= 5000 
// -10^4 <= nums[i] <= 10^4
// -10^4 <= target <= 10^4
// All values of nums are unique. 
// nums is an ascending array that is possibly rotated.
// [0,1,2,4,5,6,7]
public class Main2 {
    public int findIndex( int[] num, int target) {

        // rotation start at Y index => find 
        Have Y 
        [4,5,7 0,1,2 , 3]  => 6

        n + log(n)

        find binary in two sub array  => index 

        res + Y - 


        // loop from that index -> early exit

        

        for(int i= 0 ; i < num.length; i++) {
            if (num[i]== target) return i;
            // if (target> num[i]) return -1;
        }
        return -1;
    }

    // [0,1,2,4,5,6,7]
    // 7 

    public int binarySearch(int[] num, int target) {

        return search(0, target.length, target, num);

    }

// [0,1,2,4,5,6,7]
// mid 3 [ 4-7]
// search (4, 6, int target, int[] num)
// mid= 5 

    public int search (int l, int r, int target, int[] num) {

        if(r<=l) return -1; 
        int mid = (l+r)/2; // 3

        if (num[mid] == target ) return mid;
        if (num[mid-1] == target ) return mid-1;
        if(num[mid+1]== target) return mid+1; // => return 6

        if (num[mid] > target ) return search(l, mid-1, target, num);
        else return search(mid+1, r, target, num); 
        //search(7, 7, 7, num);

    }
    
}