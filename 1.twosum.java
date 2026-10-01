// https://leetcode.com/problems/two-sum/
class Solution {
    public int[] twoSum(int[] nums, int target) {
        Map<Integer, Integer> numMap = new HashMap<>();

        for (int i = 0; i < nums.length; i++) {
            int need = target - nums[i];

            if (numMap.containsKey(need) ) {
                return new int[]{numMap.get(need), i};
            }

            numMap.put(nums[i], i);
        }

        throw new IllegalArgumentException("No two sum provided");
    }

    // challenge 2: use java streamAPI()
    public int[] twoSum(int[] nums, int target) {
        nums.stream().
    }
}

