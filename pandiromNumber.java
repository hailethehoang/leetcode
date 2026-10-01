class Solution {
    public boolean isPalindrome(int x) {
        if (x < 0 ) {
            return false;
        }

        String xStr = String.valueOf(x);
        String[] xStrArr = xStr.split("");

        for (int i = 0; i < xStr.length()/2; i++) {
            if (!xStrArr[i].equals(xStrArr[xStr.length()-1-i]) ) {
                return false;
            }
        }
        return true;
    }

    public boolean isPalindrome2(int x) {
        if (x<0) {
            return false;
        }

        ArrayList<Integer> a = new ArrayList<>();

        while(x > 0) {
            a.add(x%10);
            x /= 10;
        } 
    
        for (int i=0; i < a.size()/2; i++) {
            if (a.get(i) != a.get(a.size()-1-i)) {
                return false;
            }
        } 
        return true;

    }
}