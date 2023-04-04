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

    public String getSideColor() {
        return sideColor;
    }

    public Boolean getPalace() {
        return isPalace;
    }

    public Piece getPieceOnCell() {
        return pieceOnCell;
    }

    public void setPieceOnCell(Piece pieceOnCell) {
        this.pieceOnCell = pieceOnCell;
    }



}
