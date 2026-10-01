class Solution:
    def climbStairs(self, n: int) -> int:
        if n == 1: 
            return 1
        elif n == 2:
            return 2
        return self.climbStairs(n-1) + self.climbStairs(n-2)

    # Solution 2: Dynamic Programming
    # to take to n from n-2 step there are 2 ways, to take 1 step or 2 steps
    # to take to n from n-1 step there is only 1 way, to take 1 step
    # there is one way to step from n-2 to n-1, we need to eliminate these duplicates.
    # so formula: ways(n) = ways(n-1) * 2 + ( ways(n-2) ) * 1 - ways(n-1)
    # final formula: ways(n) = ways(n-1) + ways(n-2)
    def climbStairs2(self, n: int) -> int:
        if n <= 2:
            return n
        int_array =[0] * (n+1)
        int_array[1] = 1
        int_array[2] = 2
        for i in range(3, n+1):
            int_array[i] = int_array[i-1] + int_array[i-2]
        return int_array[n]