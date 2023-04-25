package cz.cvut.fel.strobad1.XiangQi.Model;


import java.lang.reflect.Array;
import java.util.ArrayList;

public abstract class Piece {

    //location of figure
    private int row;
    private int col;
    private final String color;

    Board board = Main.getMatch().getGameBoard();

    public Piece(int row, int col, String color) {
        this.row = row;
        this.col = col;
        this.color = color;
        board.updateCell(row,col,this);
    }


    /**
     * returns an arraylist of all the piece's valid moves.
     */
    public abstract ArrayList<Cell> getValidMoves();


    /**
     * Checks if a given move is valid.
     * @param newRow the row of the move being checked
     * @param newCol the col of the move being checked
     * @return true if the move is valid, else false
     */
    public boolean isValidMove(int newRow, int newCol){

        Cell newCell = board.getCell(newRow,newCol);

        if(getValidMoves().contains(newCell)){
            return true;
        }
        return false;
    }
    public boolean move(int newRow,int newCol) {

    // A method that moves a piece to a new position if valid
        if(isValidMove(newRow,newCol)) {
            board.updateCell(this.row,this.col,null);
            this.row= newRow;
            this.col = newCol;
            board.updateCell(newRow,newCol,this);
            return true; // Move successful
//        ([former rank][former file])-[new rank][new file] Thus,
//        the most common opening in the game would be written as: cannon (32)–35 soldier (18)–37
//        this looks weird because the numbers dont have a line between them such as 3,2 - 3,5
        }
        else {
            return false; // Move invalid
        }
    }

    public String getColor() {
        return color;
    }
}
