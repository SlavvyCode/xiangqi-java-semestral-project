package cz.cvut.fel.strobad1.XiangQi.Model;

public class Cell {

    private Piece pieceOnCell = null;

    private int row;
    private int col;
    private String sideColor;
    private Boolean isPalace;
    public Cell(int row,int col, String sideColor,boolean isPalace) {

        if(row>10 || col>9){
            throw new ArrayIndexOutOfBoundsException("10x9");
        }
        this.row=row;
        this.col=col;
        this.sideColor = sideColor;
        this.isPalace = isPalace;
    }
}
