class Solution {
    public void solveSudoku(char[][] board) {}

        // there are two action -> move one cell head or current cell
        // - novalue -> fill to try
        // - try a value -> 0 - 9 
        //     - check valid. 
        //         - if not valid-> back to try 1-9 (next val)
        //         - else: continue to check next cell
        //         - if no value left to try -> ROLL back previous depth value - previous cell

        // tryNextCellVal(0,0,'1',board);

    public void solveSudoku(char[][] board) {        
        tryCellVal(0, board ); 
    }

    public boolean tryCellVal(int indexCell, char[][] board) { 
        //index 0->81
         int row = indexCell/9;
         int col = indexCell%9 ;
         System.out.print(indexCell);

         if ( board[row][col] == '.' && tryCellWithVal( row, col, indexCell, '1', board ) ) { // only change .
            return true ;
         } else if (indexCell<80){ //hard number
            return tryCellVal(indexCell+1, board);
        }
        return false;
    }

    public boolean tryCellWithVal( int row, int col, int i, char val, char[][] board ) {
        board[row][col] = val ;
        if (checkCellValid(row, col , board)  && tryCellVal(i+1, board)) {
             return true;
        } else if (val < '9'){
            return tryCellWithVal (row, col, i, (char)(val+1), board);
        } 
        board[row][col] = '.';
        return false;
    }

    public boolean checkCellValid(int r, int c, char[][] board ){
        HashSet<Character> row = new HashSet<>() ;
        HashSet<Character> col = new HashSet<>() ;
        HashSet<Character> box = new HashSet<>() ;

        int val = board[r][c];
        for (int i = 0; i < 9; i++) {
            if(i != r) {
                if (row.contains(val) == false ){
                    row.add(board[i][c]);
                } else {
                    return false ;
                }
            }

            if ( i!=c ) {
                if (col.contains(val) == false ){
                    col.add(board[r][i]);
                } else {
                    return false ;
                }
            }
        }

        for (int i = r/3; i< r/3+3; i++) {
            for(int j = c/ 3; j < c/3+3; j++) {
                if (i != r && j != c) {
                    if (box.contains(val) == false ){
                        box.add(board[i][j]);
                    } else {
                        return false ;
                    }
                }
            }
        }
        return true; 
    }
}


class Solution {

    public void solveSudoku(char[][] board) {
        tryCellVal(0, board);
    }

    public boolean tryCellVal(int indexCell, char[][] board) {

        // Successfully passed all 81 cells
        if (indexCell == 81) {
            return true;
        }

        int row = indexCell / 9;
        int col = indexCell % 9;

        // Empty cell: MUST find a valid number
        if (board[row][col] == '.') {
            return tryCellWithVal(row, col, indexCell, '1', board);
        }

        // Fixed number: move to next cell
        return tryCellVal(indexCell + 1, board);
    }


    public boolean tryCellWithVal(
            int row,
            int col,
            int indexCell,
            char val,
            char[][] board) {

        board[row][col] = val;

        if (checkCellValid(row, col, board)
                && tryCellVal(indexCell + 1, board)) {
            return true;
        }

        // Try next number
        if (val < '9') {
            return tryCellWithVal(
                    row,
                    col,
                    indexCell,
                    (char) (val + 1),
                    board
            );
        }

        // None of 1-9 worked → backtrack
        board[row][col] = '.';
        return false;
    }


    public boolean checkCellValid(int r, int c, char[][] board) {

        char val = board[r][c];

        // Check row and column
        for (int i = 0; i < 9; i++) {

            // column
            if (i != r && board[i][c] == val) {
                return false;
            }

            // row
            if (i != c && board[r][i] == val) {
                return false;
            }
        }

        // Check 3x3 box
        int startRow = (r / 3) * 3;
        int startCol = (c / 3) * 3;

        for (int i = startRow; i < startRow + 3; i++) {
            for (int j = startCol; j < startCol + 3; j++) {

                if (!(i == r && j == c)
                        && board[i][j] == val) {
                    return false;
                }
            }
        }

        return true;
    }
}


class Solution {

    public void solveSudoku(char[][] board) {
        solve(board);
    }

    private boolean solve(char[][] board) {

        // Find an empty cell
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {

                if (board[row][col] == '.') {

                    // Try 1 -> 9
                    for (char num = '1'; num <= '9'; num++) {

                        if (isValid(board, row, col, num)) {
                            board[row][col] = num;

                            if (solve(board)) {
                                return true;
                            }

                            // Backtrack
                            board[row][col] = '.';
                        }
                    }

                    // No number works here
                    return false;
                }
            }
        }

        // No empty cells => solved
        return true;
    }

    private boolean isValid(char[][] board, int row, int col, char num) {

        // Row + column
        for (int i = 0; i < 9; i++) {
            if (board[row][i] == num) {
                return false;
            }

            if (board[i][col] == num) {
                return false;
            }
        }

        // 3x3 box
        int startRow = (row / 3) * 3;
        int startCol = (col / 3) * 3;

        for (int r = startRow; r < startRow + 3; r++) {
            for (int c = startCol; c < startCol + 3; c++) {
                if (board[r][c] == num) {
                    return false;
                }
            }
        }

        return true;
    }
}