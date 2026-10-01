class Solution {
     public int maxArea(int[] height) {

        int head = 0 ;
        int tail = height.length - 1 ;

        int maxArea = calArea(head, tail, height);
        
        while (head < tail) {
            if (height[head] > height[tail]) {
                tail--;
            } else {
                head++;
            }
            maxArea = Math.max(calArea(head, tail, height),maxArea);
        }
        
        return maxArea ; 
    }

    public int calArea(int x, int y, int[]height) {
        if (y<x) return 0;
        return Math.min(height[x], height[y] ) * (y-x) ; 
    }
}