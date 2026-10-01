/**
 * Definition for singly-linked list.
 * type ListNode struct {
 *     Val int
 *     Next *ListNode
 * }
 */
func addTwoNumbers(l1 *ListNode, l2 *ListNode) *ListNode {

	res := &ListNode{
		Val: 0,
		Next: nil,
	}

	return addTillEndNode(res, l1, l2)
}

func addTillEndNode(resNode *ListNode, l1 *ListNode, l2 *ListNode) *ListNode {

    var l1Next, l2Next *ListNode
	if l1 != nil {
		resNode.Val = resNode.Val + l1.Val
		l1Next = l1.Next
	}

	if l2 != nil {
		resNode.Val = resNode.Val + l2.Val
		l2Next = l2.Next
	}

	carry := resNode.Val / 10
	resNode.Val = resNode.Val % 10

	// Chi tao nextNode neu 
	if carry > 0 || l1Next != nil || l2Next != nil  {
		resNode.Next = &ListNode{
			Val: carry,
		}
	    resNode.Next = addTillEndNode(resNode.Next, l1Next, l2Next)
	}
		
	return resNode
}

// best
func addTwoNumbers(l1 *ListNode, l2 *ListNode) *ListNode {
	dummy := &ListNode{}
	current := dummy
	carry := 0

	for l1 != nil || l2 != nil || carry != 0 {
		sum := carry

		if l1 != nil {
			sum += l1.Val
			l1 = l1.Next
		}

		if l2 != nil {
			sum += l2.Val
			l2 = l2.Next
		}

		current.Next = &ListNode{
			Val: sum % 10,
		}

		carry = sum / 10
		current = current.Next
	}

	return dummy.Next
}