public class GameBoard {

    private char[][] board;
    private int size = 3;

    public GameBoard() {
        board = new char[size][size];
        resetBoard();
    }

    public void setSize(int size) {
        this.size = size;
    }
    public int getSize() {
        return size;
    }

    public void printBoard(){

        for (int col = 0; col < board.length; col++) {
            System.out.print("   "+(col+1));
        }
        System.out.println();
        for(int row = 0; row <board.length; row++){
            System.out.print((row+1) + " ");
            for(int col = 0; col <board[row].length; col++){
                System.out.print(" " + board[row][col] + "  ");
            }
            System.out.println();
        }
    }

    private void resetBoard(){
        for(int row=0; row<board.length; row++){
            for(int col = 0; col <board[row].length; col++){
                board[row][col] = ' ';
            }
            System.out.println();
        }
    }

    public boolean placeMove(int row, int col, char symbol) {
        if (board[row][col] == ' ') {
            board[row][col] = symbol;
            return true;
        }
        return false;
    }

    public boolean drawCheck(){
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                if (board[row][col] == ' ') {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean checkForWin(char symbol) {
        for (int i = 0; i < size; i++) {
            if (checkRowsAndCols(i, symbol, true) || checkRowsAndCols(i, symbol, false)) {
                return true;
            }
        }
        return checkDiagonals(symbol);
    }

    private boolean checkRowsAndCols(int index, char symbol, boolean checkRow) {
        for (int i = 0; i < size; i++) {
            char current = checkRow ? board[index][i] : board[i][index];
            if (current != symbol) {
                return false;
            }
        }
        return true;
    }

    private boolean checkDiagonals(char symbol) {

        boolean leftDiagonal = true, rightDiagonal = true;

        for (int i = 0, j=size-1; i < size; i++, j--) {
            if (board[i][i] != symbol) {
                leftDiagonal = false;
            }
            if (board[i][j] != symbol) {
                rightDiagonal = false;
            }
            if (!leftDiagonal && !rightDiagonal) {
                break;
            }
        }
        return leftDiagonal || rightDiagonal;
    }

    public int[] closeWinCheck(char symbol) {

        for (int i = 0; i < size; i++) {

            int [] rowMove = winningMove(i, symbol, true);
            int [] colMove = winningMove(i, symbol,false);

            if (rowMove[0] > -1 && rowMove[1] > -1) {
                return rowMove;
            }
            else if (colMove[0] > -1 && colMove[1] > -1) {
                return colMove;
            }
        }

        int[] diagonalMove = winningDiagonalMove(symbol);
        if (diagonalMove[0] > -1 && diagonalMove[1] > -1) {
            return diagonalMove;
        }

        return new int[]{-1, -1};
    }

    private int[] winningMove(int index, char symbol, boolean checkRow) {

        int countSymbol = 0;
        int emptySpot = -1;

        for (int i = 0; i < size; i++) {
            char current = checkRow ? board[index][i] : board[i][index];
            if (current == symbol) {
                countSymbol++;
            }
            if (current == ' ' ) {
                emptySpot = i;
            }
        }
        if (countSymbol == size-1 && emptySpot != -1) {
            if (checkRow){
                return new int[] {index, emptySpot};
            }
            else{
                return new int[] {emptySpot, index};
            }
        }
        return new int[]{-1, -1};
    }

    private int[] winningDiagonalMove(char symbol) {

        int count = 0;
        int emptySpot = -1;
        for(int i = 0; i<size; i++){
            if (board[i][i] == symbol) {
                count++;
            }
            if (board[i][i] == ' '){
                emptySpot = i;
            }
        }
        if (count == size-1 && emptySpot != -1){
            return new int[]{emptySpot, emptySpot};
        }

        count = 0;
        emptySpot = -1;
        for(int i = 0, j=size-1; i<size; i++, j--){
            if (board[i][j] == symbol) {
                count++;
            }
            if (board[i][j] == ' '){
                emptySpot = i;
            }
        }
        if (count == size - 1 && emptySpot != -1){
            return new int[]{emptySpot, size-1-emptySpot};
        }
        return new int[]{-1, -1};
    }
}
