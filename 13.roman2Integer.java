class Solution {
    public int romanToInt(String s) {
        Map<String, Integer> converter = new HashMap<>();

        converter.put("I", 1);
        converter.put("V", 5);
        converter.put("X", 10);
        converter.put("L", 50);
        converter.put("C", 100);
        converter.put("D", 500);
        converter.put("M", 1000);

        int res = 0;
        String[] strArr = s.split("");

        for (int i = 0; i < strArr.length; ) {
            // System.out.println(strArr[i]);
            int first = converter.get(strArr[i]);
            int sec = (i+1>=strArr.length) ?  0 : converter.get(strArr[i+1]);
            if (first < sec) {
                res = res + sec - first;
                i+=2;
            } else {
                res = res + first;
                i++;
            }
        }
        return res;
    }
}