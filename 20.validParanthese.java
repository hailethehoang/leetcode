class Solution {
    public boolean isValid(String s) {

        Deque<Character> stack = new ArrayDeque<Character>();
        Map<Character, Character> closeBracket = new HashMap<>();
        closeBracket.put('(',')');
        closeBracket.put('{','}');
        closeBracket.put('[',']');
    
        for (char c : s.toCharArray() ) {
            if ( closeBracket.containsKey(c) ) {
                stack.push(closeBracket.get(c));
            } else if (stack.isEmpty() || stack.pop() != c )  {
                return false;
            }
        }
        return true;
    }
}