package cz.cvut.fel.strobad1.XiangQi.Model;

public abstract class Piece {

    //location of figure
    private int row;
    private int col;
    private int value;
    private final String color;


    public Piece(int row, int col, String color) {
        this.row = row;
        this.col = col;
        this.color = color;
        this.value = value;
        GameState.getGameBoard().updateCell(row,col,this);
    }

    public abstract int[][] getValidMoves();
    public abstract boolean isValidMove(int newRow, int newCol);
    public boolean move(int newRow,int newCol) {

    // A method that moves a piece to a new position if valid
        if(isValidMove(newRow,newCol)) {
            GameState.getGameBoard().updateCell(this.row,this.col,null);
            this.row= newRow;
            this.col = newCol;
            GameState.getGameBoard().updateCell(newRow,newCol,this);
            return true; // Move successful
//        ([former rank][former file])-[new rank][new file] Thus,
//        the most common opening in the game would be written as: cannon (32)–35 soldier (18)–37
//                                                                          this looks weird because the numbers dont have a line between them such as 3,2 - 3,5
        }
        else {
            return false; // Move invalid
        }
    }
    public String getColor() {
        return color;
    }
}
