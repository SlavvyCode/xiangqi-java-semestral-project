package cz.cvut.fel.strobad1.XiangQi.Model.Board;

import cz.cvut.fel.strobad1.XiangQi.Model.Board.Board;
import cz.cvut.fel.strobad1.XiangQi.Model.Board.Piece;

public class Cell {


    private Piece pieceOnCell = null;
    private final int row;
    private final int col;
    private final String sideColor;
    private final Boolean isPalace;
    public Cell(int row,int col, String sideColor,boolean isPalace) {

        if(row>10 || col>9){
            throw new ArrayIndexOutOfBoundsException("10x9");
        }
        this.row=row;
        this.col=col;
        this.sideColor = sideColor;
        this.isPalace = isPalace;
        //cells start out empty
        this.pieceOnCell = null;
    }

    public Piece getPieceOnCell() {
        return pieceOnCell;
    }

    public void setPieceOnCell(Piece pieceOnCell) {
        this.pieceOnCell = pieceOnCell;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }
}
