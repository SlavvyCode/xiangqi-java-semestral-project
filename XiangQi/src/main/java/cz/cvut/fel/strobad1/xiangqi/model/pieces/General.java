package cz.cvut.fel.strobad1.xiangqi.model.pieces;
// moves in a plus shape, contained to the palace. CANNOT have Line of sight with the enemy king

import cz.cvut.fel.strobad1.xiangqi.model.*;


import java.util.ArrayList;

public class General extends Piece {

    public General(int row, int col, String color,Board board) {
        super(row, col, color,board);
    }



    private int[][] offsets = {{+1, 0},{-1,0},{0,+1},{0,-1}};

    @Override
    public ArrayList<Cell> getMoveList() {

        ArrayList<Cell> moveList = new ArrayList<Cell>();
        for (int[] offset : offsets) {
            int destRow = row + offset[0];
            int destCol = col + offset[1];


            if (destRow < 0 || destRow >= 10 || destCol < 0 || destCol >= 9) {
                continue;
            }
            Cell destCell = board.getCell(destRow, destCol);


            if(!destCell.getIsPalace()){
                continue;
            }

            moveList.add(destCell);

        }
        return moveList;
    }




    @Override
    public int[][] getOffsets() {
        return offsets;
    }



    @Override
    public Piece clone(Board newBoard) {
        // create a new Piece object with the same fields as this
        General newPiece = new General(this.row, this.col, this.color, newBoard);

        // return the new Piece object
        return newPiece;
    }

}