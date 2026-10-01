class Solution {
    public int removeElement(int[] nums, int val) {

        Deque<Integer> q = new ArrayDeque<>();

        for (int num : nums) {
            if (num != val) {
                q.offer(num);
            }
        }

        int res = q.size();
        for (int i = 0 ; q.isEmpty()==false ; i++) {
            nums[i]=q.poll();
        }
        return res;
    }

    public int removeElement2(int[] nums, int val) {
        
       int j = 0 ;  

       for (int i = 0 ; i < nums.length; i++) {
            if (nums[i]==val) {
                
            }
       }
    }
        
}
