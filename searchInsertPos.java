class Solution {
    public int searchInsert(int[] nums, int target) {
        return searchBinaPos(0, nums.length-1, target, nums);
        
    }

    public int searchBinaPos(int start, int fin, int target, int[]nums) {

        if( start >= fin ) {
            if(fin==-1) return 0 ;
            if (nums[fin] < target) return fin + 1;
            return start; 
        }
    
        int mid = (start+fin)/2;
        System.out.println(start + ";" + fin +";"+mid);
        if (nums[mid] == target) return mid;
        if ( nums[mid] > target ) return searchBinaPos(start, mid-1, target, nums);
        return searchBinaPos(mid+1, fin, target, nums);
    }
}

class Solution{
    public int searchInsert(int[] nums, int target){
        int low = 0;
        int high = nums.length - 1;

        while (low <= high){
            int mid = low + (high - low) / 2;

            if (target == nums[mid]){
                return mid;
            } 
            else if (target > nums[mid]){
                low = mid + 1;
            } 
            else{
                high = mid - 1;
            }
        }
        return low;
    }
}