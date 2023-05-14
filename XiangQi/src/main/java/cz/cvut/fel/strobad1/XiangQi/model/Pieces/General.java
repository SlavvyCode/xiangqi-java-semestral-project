package cz.cvut.fel.strobad1.XiangQi.model.Pieces;
// moves in a plus shape, contained to the palace. CANNOT have Line of sight with the enemy king

import cz.cvut.fel.strobad1.XiangQi.model.*;


import java.util.ArrayList;

public class General extends Piece {

    private float value = 0;
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



            if ((destRow < 0 || destRow > 2) || (destRow < 7 || destRow > 9) || destCol < 3 || destCol > 5) {
                // destination is outside of the general's palace
                continue;
            }
            Cell destCell = board.getCell(destRow, destCol);

            moveList.add(destCell);

        }
        return moveList;
    }




    @Override
    public int[][] getOffsets() {
        return offsets;
    }
}