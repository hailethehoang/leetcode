class Solution {
    public boolean isValidSudoku(char[][] board) {
        return checkRow(board) && checkCol(board) && CheckBoxs(board);
    }

    public boolean checkRow(char[][] board) {
        for ( char[] cs : board) {
            HashSet<Character> set = new HashSet<Character>() ;
            for (char c : cs ) {
                if (c == '.') continue ;
                if (set.contains(c) == false ) {
                    set.add(c);
                } else {
                    // System.out.println(" ROW FALSE " + c) ;
                    return false ; 
                }
            }   
        }
        return true ;
    }

    public boolean checkCol(char[][] board) {
        for (int i = 0; i < 9 ; i++ ) { // col
            HashSet<Character> set = new HashSet<Character>() ;
            for (int j = 0; j <9; j++) { // row
                char c = board[j][i]; 
                if (c == '.') continue ;
                if ( set.contains(c) == false ) {
                    set.add (c) ; 
                } else {
                    // System.out.println(" COL FALSE " + i + j) ;
                    return false ;
                }
            }
        }
        return true ;
    }

    public boolean CheckBoxs (char[][] board) {
        for (int c = 0; c < 9 ; c += 3 ) { // col
            
            for (int r = 0; r <9; r +=3) { // row
                if (checkBox(r, c, board) ==false) {
                    return false ;
                }
            }
        }
        return true;
    }

        public boolean checkBox( int row, int col, char[][] board ) {
        HashSet<Character> set = new HashSet<Character>() ;
        for (int i =  row ; i< row + 3; i++){
            for (int j= col; j < col +3; j++ ){
                char c = board[i][j];
                if (c == '.') continue ;
                if ( set.contains(c) == false ) {
                    set.add(c) ; 
                } else {
                    // System.out.println(i +" " + j + " " + c+ " " + row + col);
                    return false ;
                }
            }
        }
        return true ;
    }
}

// [
// [".",".","4",".",".",".","6","3","."],
// [".",".",".",".",".",".",".",".","."],
// ["5",".",".",".",".",".",".","9","."],
// [".",".",".","5","6",".",".",".","."],
// ["4",".","3",".",".",".",".",".","1"],
// [".",".",".","7",".",".",".",".","."],
// [".",".",".","5",".",".",".",".","."],
// [".",".",".",".",".",".",".",".","."],
// [".",".",".",".",".",".",".",".","."]]