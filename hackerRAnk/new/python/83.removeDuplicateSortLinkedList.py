# Definition for singly-linked list.
# class ListNode:
#     def __init__(self, val=0, next=None):
#         self.val = val
#         self.next = next
# Definition for singly-linked list.
# class ListNode:
#     def __init__(self, val=0, next=None):
#         self.val = val
#         self.next = next
class Solution:
    def deleteDuplicates(self, head: ListNode | None) -> ListNode | None:
        cur = head 

        while cur and cur.next != None:
            if cur.val == cur.next.val:
                run = cur.next
                while run and run.next and run.next.val == cur.val:
                    run = run.next
                cur.next = run.next
            cur = cur.next
        return head

   