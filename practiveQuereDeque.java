// #### Bài 1: Valid Parentheses — 30 phút
// Dùng `Deque<Character>` thay vì `Stack`.
// ```
// Input: "()[]{}"
// Output: true

// Input: "([)]"
// Output: false
// ```

// Anh phải giải thích được:

// - Vì sao dùng stack.
// - Time complexity: `O(n)`.
// - Space complexity: `O(n)`.

// #### Bài 2: Group Anagrams — 45 phút

// ```
// Input: ["eat", "tea", "tan", "ate", "nat", "bat"]

// Output:
// [
//   ["eat", "tea", "ate"],
//   ["tan", "nat"],
//   ["bat"]
// ]
// ```

public class Main {
    public static void main(String[]args){

        String[] input1 = "()[]{}";

        System.out.println(isValidParenthese(input1));

        String[] arr = ["eat", "tea", "tan", "ate", "nat", "bat"]

        List <String> input = Arrays.asList(arr);

        System.out.println(groupAnagram(input));
        
    }

    public boolean isValidParenthese(String input) {
        // dung Stack vi la first in Last out, Ngoac mo sau can dong truoc do do logic la FILO
        Deque<Character> stack = new ArrayDeque<>() ;
        Map<Character, Character> paren = new HashMap<>();
        paren.put('(',')');
        paren.put('{','}');
        paren.put('[',']');

        // String[] arr = input.split("")
        for (char a : input.toCharArray()) {
            if ( paren.containsKey(a) ) {
                stack.push(paren.get(a));
                continue;
            } 

            if (a==')'|| a == ']' || a=='}') {
                if (stack.isEmpty() || a!= stack.pop()) {
                    return false;
                }
            }
        }
        return stack.isEmpty()
    }

    public ArrayList<ArrayList<String>> groupAnagram(List<String> input ) {
        HashMap<String, ArrayList<String>> groups = new HashMap<>();
        for (String word : input ) {
            char[] chars = word.toCharArray() ;
            Arrays.sort(chars);

            // "eat", "tea", "ate" đều thành key "aet"
            String key = new String(chars);

            groups.
                computeIfAbsent(key, k -> new ArrayList<String>()).add(word)

        }

        return new ArrayList<>(groups.values());

    }
}