from math import e


class Solution:
# Time Limit Exceeded
# 6 / 1019 testcases passed

# Last Executed Input
# Use Testcase
# x = 5
    def mySqrt(self, x: int) -> int:
        if x == 0:
            return 0
        if x < 4:
            return 1
        if x == 4:
            return 2
        mid = x // 2
        h = mid
        l = 0

        while l < h:
            mid = (h + l) // 2
            if mid * mid == x:
                return mid
            elif mid * mid < x:
                l = mid + 1
            else:
                h = mid - 1
        
                
        return mid -1
    
# ACCEPTED
class Solution:
    def mySqrt(self, x: int) -> int:
        if x == 0:
            return 0
        if x < 4:
            return 1
        if x == 4:
            return 2
        mid = x // 2
        h = mid
        l = 2

        while mid > l :
            if mid*mid == x:
                return mid
            elif mid*mid > x:
                h= mid
            else: 
                l = mid
            mid = l+(h-l)//2
        
        return mid

    def mySqrt3(self, x: int) -> int:
        if x == 0:
            return 0
        
        return findMidNumber(2, x, x)

    def findMidNumber(self, l, h, x: int) -> int:
        mid = l + (h - l) // 2
        if mid * mid == x:
            return mid
        elif mid * mid > x:
            return self.findMidNumber(l, mid, x)
        else:
            if (mid + 1) * (mid + 1) > x:
                return mid
            else:
                return self.findMidNumber(mid + 1, h, x)

    def findMidNumber(self, l, h, x: int) -> int:
        mid = l + (h-l)//2
        if mid == l or mid * mid == x:
            return mid
        elif mid * mid > x:
            return self.findMidNumber(l, mid, x)
        else:
            return self.findMidNumber(mid, h, x)