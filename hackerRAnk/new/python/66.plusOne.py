class Solution:
    def plusOne(self, digits: list[int]) -> list[int]:
        add = 1 
        for index, value in reversed(list(enumerate(digits))):
            sum = add + value
            value = sum % 10
            add = sum // 10
            digits[index]=value
            if sum < 10:
                break
        if add != 0 :
            digits.insert(0, add)
        return digits
        