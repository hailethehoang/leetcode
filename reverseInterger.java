class Solution {
    public int reverse(int x) {
        int res = 0 ; 
        int xc = Math.abs(x);
        while ( xc > 0 ) {
            int remainder = xc%10;
            if ( res > (Integer.MAX_VALUE- remainder) /10 ){
                return 0;
            }
            res = res * 10 + remainder;
            xc/=10 ;
        }
        return (x>=0) ? (res) : (-res);
    }
}