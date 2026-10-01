class Solution:
    def merge(self, nums1: list[int], m: int, nums2: list[int], n: int) -> None:
        """
        Do not return anything, modify nums1 in-place instead.
        """
        nums = [0] * ( m + n)
        i, j, x = 0, 0, 0
        while i < m and j < n :
            if nums1[i] <= nums2[j]:
                nums[x] = nums1[i]
                i += 1
            else:
                nums[x] = nums2[j]
                j += 1
            x += 1

        while i < m:
            nums[x] = nums1[i]
            x += 1
            i += 1
        while j < n:
            nums[x] = nums2[j]
            j += 1
            x += 1

        for index, val in list(enumerate(nums)):
            nums1[index] = val

    def merge(self, nums1: List[int], m: int, nums2: List[int], n: int) -> None:
        a, b, write_index = m-1, n-1, m + n - 1
        while b >= 0:
            if a >= 0 and nums1[a] > nums2[b]:
                nums1[write_index] = nums1[a]
                a -= 1
            else:
                nums1[write_index] = nums2[b]
                b -= 1
            write_index -= 1