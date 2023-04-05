package cz.cvut.fel.strobad1.XiangQi.Model;

public class Cell {

    private Piece pieceOnCell = null;

    private String sideColor;
    private Boolean isPalace;
    public Cell(String sideColor,Piece pieceOnCell,boolean isPalace) {
        this.pieceOnCell = pieceOnCell;
        this.sideColor = sideColor;
        this.isPalace = isPalace;
    }


    /**
     * gets the color of the player who owns the piece
     * @return color of the player who owns the piece
     */
    public String getSideColor() {
        return sideColor;
    }


    /**
     * returns information about whether this cell is in the palace or not
     * @return true if cell is in palace, false if not
     */
    public Boolean getPalace() {
        return isPalace;
    }


    /**
     * returns the piece standing on the cell
     * @return  piece standing on the cell
     */

    public Piece getPieceOnCell() {
        return pieceOnCell;
    }

    /**
     * place a piece on this cell, this constitutes as removing a piece from play also.
     * @param pieceOnCell the piece we want to set on this cell
     */
    public void setPieceOnCell(Piece pieceOnCell) {
        this.pieceOnCell = pieceOnCell;
    }



}
