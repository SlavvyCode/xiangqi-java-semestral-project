package cz.cvut.fel.strobad1.xiangqi.model;

public class Cell {

    private Piece pieceOnCell;

    private colorEnum sideColor;
    private boolean isPalace;
    public Cell(colorEnum sideColor,Piece pieceOnCell,boolean isPalace) {
        this.pieceOnCell = pieceOnCell;
        this.sideColor = sideColor;
        this.isPalace = isPalace;
    }

    public colorEnum getSideColor() {
        return sideColor;
    }


    /**
     * Returns information about whether this cell is in the palace or not.
     * @return true if cell is in palace, false if not
     */
    public boolean getIsPalace() {
        return isPalace ;
    }


    /**
     * Returns the piece standing on this cell.
     * @return  piece standing on this cell
     */

    public Piece getPieceOnCell() {
        return pieceOnCell;
    }

    /**
     * Place a piece on this cell, this constitutes as removing a piece from play also.
     * @param pieceOnCell the piece we want to set on this cell
     */
    public void setPieceOnCell(Piece pieceOnCell) {
        this.pieceOnCell = pieceOnCell;
//        pieceOnCell.setCol();

    }


//    @Override
//    public boolean equals(Object cellToCompare) {
//        if(cellToCompare.getClass() != Cell.class){
//            return false;
//        }
//        Cell cell = (Cell) cellToCompare;
//
//        if(cell.isPalace)
//
//            this.pieceOnCell = pieceOnCell;
//        this.sideColor = sideColor;
//        this.isPalace = isPalace;
//    }
}
