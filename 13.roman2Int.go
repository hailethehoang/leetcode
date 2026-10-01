func romanToInt(s string) int {
	var res int
	length := len(s)
    for i, c := range s {

		curVal, _ := romanCharToInt(c)

		if i < length - 1 {
			nextVal, _ := romanCharToInt(rune(s[i+1]))
			if curVal < nextVal {
				res = res - curVal
				continue
			}
		}
		res = res + curVal
	}
	return res
}

func romanCharToInt(c rune) (int, error ){

	switch(c) {
	case 'I': return 1, nil
	case 'X': return 10, nil
	case 'V': return 5, nil
	case 'L': return 50, nil
	case 'C': return 100, nil
	case 'D': return 500, nil
	case 'M': return 1000, nil
	default: 
	return 0, errors.New("Invalid Roman Character")
	}
}