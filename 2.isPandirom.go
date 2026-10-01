func isPalindrome(x int) bool {
    
	if x < 0 {
		return false
	}

	var digits []int

	for {
		digits = append(digits, x%10)
		x= x / 10

		if (x <= 0 ) {
			break
		}
	}

	length := len(digits)
	for i, v := range digits {

		if i >= length/ 2  {
			break
		}

		if v != digits[length-1-i] {
			return false
		}

	} 
	return true
}