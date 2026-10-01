// Bài 3 — Two Sum
// Cho mảng số và target, trả về index của hai số có tổng bằng target.
// nums = [2, 7, 11, 15]
// target = 9
// // [0, 1]
// Yêu cầu:
// Không dùng nested loop O(n²).
// Dùng HashMap<Integer, Integer>.
// Key là số đã đi qua, value là index.
// Với mỗi nums[i], tìm target - nums[i].

// Bài 4 — Frequency Counter
// Đếm tần suất mỗi từ, không phân biệt hoa thường.
// ["Java", "Go", "java", "Python", "GO", "java"]
// Kết quả mong muốn:
// {
//   "java": 3,
//   "go": 2,
//   "python": 1
// }
// Yêu cầu:
// public static Map<String, Integer> countFrequency(List<String> words)
// Nâng cấp:
// Trả về từ xuất hiện nhiều nhất.
// Nếu bằng nhau, chọn từ alphabetically nhỏ hơn.
// In kết quả theo thứ tự frequency giảm dần.

// Bài 5 — Top K Frequent Elements
// nums = [1, 1, 1, 2, 2, 3]
// k = 2
// // [1, 2]
// Yêu cầu:
// Dùng HashMap<Integer, Integer> đếm frequency.
// Dùng PriorityQueue để lấy top K.
// Giải thích min-heap và vì sao chỉ giữ heap size k.
// Signature:
// public static int[] topKFrequent(int[] nums, int k)

class Solution {
    public static int[] twoSum( int[] nums, int target ) {
        Map<Integer, Integer> noIndex = new HashMap<>();
        for (int i = 0; i < nums.length(); i++) {
            num = nums[i];
            if ( noIndex.containsKey(target-num) ) {
                return []int{noIndex.get(target-num), i};
            } else {
                noIndex.put(num, i);
            }
        }
        return []int{};
    }

    public static Map<String, Integer> countFrequency(List<String> words) {
        Collections.sort(words);
    
        Map<String, Integer> res = new LinkedHashMap<>();

        if (words.isEmpty()) return res;

        String curWords = words.get(0);
        int count = 1;
        for (int i = 1; i < words.length(); i++) {
            curWords = words.get(i); 
            if ( curWords.equals(words.get(i-1))  ) {
                res.put(words.get(i-1), count);
                count = 1;
            } else {
                count++;
            }
        }
        res.put(curWords, count);
        return res;
    }

    // use record way
    record Element(int value, int times){}
    public static int[] topKFrequent(int[] nums, int k) {
        Map<Interger, Interger> appear = new HashMap<>();
        for (int num : nums ){
            appear.put(num, appear.getOrDefault(num, 0)+1);
        }

        // PriorityQueue<Element> pq = new PriorityQueue<>((e1, e2)->e1.times()-e2.times());
        PriorityQueue<Element> pq = new PriorityQueue<>(
            Comparator.comparingInt(Element::times)
        );

        for (Map.Entry<Integer, Integer> entry : appear.entrySet()) {
            pq.offer(new Element(entry.getKey(), entry.getValue()));

            if (pq.size() > k ) {
                pq.poll();
            }
        }

        int[] res = new int[k];
        for (int i = k-1; i>=0; i-- ) {
            res[i] = pq.poll().value();
        }
    }
}