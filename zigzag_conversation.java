class Solution {
    public String convert(String s, int numRows) {

        if (numRows == 1) return s;
        int trend = 1 ;

        StringBuilder[] sa = new StringBuilder[numRows];

        int row = 0 ;
        for ( char c : s.toCharArray() ) {

            if(sa[row] == null) {
                sa[row] = new StringBuilder();
            }
            sa[row].append(c);

            row += trend;
            if (row == 0) {
                trend = 1;
            } else if (row >= numRows-1) {
                trend = -1 ;
            }
        }

        StringBuilder res = new StringBuilder();

        for ( StringBuilder b : sa ) {
            if ( b!= null) {
                res.append(b);
            }
        }

        return res.toString();
    }
}


class Solution {
    public String convert(String s, int numRows) {
        if(numRows==1 || numRows>=s.length()) {
            return s;
        }
        StringBuilder[] rows = new StringBuilder[numRows];
        for(int i=0; i<numRows; i++) {
            rows[i] = new StringBuilder();
        }

        int currentRow=0;
        boolean  goingDown = false;

        for(char c : s.toCharArray()) {
            rows[currentRow].append(c);
            if(currentRow==0 || currentRow==numRows-1){
                goingDown = !goingDown;
            }
            currentRow += goingDown ? 1 : -1;
        }
        StringBuilder res = new StringBuilder();
        for(StringBuilder row : rows) {
            res.append(row);
        }
        return res.toString();
    }
}