func isValidPathesis(s string) bool {
	expectedClose := map[rune]rune {
		'(':')',
		'[':']',
		'{','}',
	}

	stack = []rune{}

	fir _, c := range s {
		if closing, isOpening := expectedClose[c] ; isOpening {
			stack = append(stack, closing)
		} else {
			if len(stack)==0 || c != stack[len(stack)-1] {
				return false
			}
			stack = stack[:len(stack-1)]
		}
	}
	return len(stack)==0

}

func groupAnagram(input []string) [][]string {
	gcategory := make(map[string][]string)

	for _, word := range input {
		
		wordPresentative := make([]int, 26)

		for _, c := range word {
			wordPresentative[c-'a']++
		}

		key := fmt.Sprint(wordPresentative)

		gcategory[key] = append(gcategory[key], word)

	}

	group := [][]string{}
	for _, v := range gcategory {
		group = append( group, v)
	}
	return group
}