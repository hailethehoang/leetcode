func twoSum(nums []int, target int) []int {
	indexMap := make(map[int]int)
    for index, value := range nums {

		if needIndx, existed := indexMap[target - value]; existed {
			return []int { needIndx, index }
		}

		indexMap[value] = index

	}
	return nil
}