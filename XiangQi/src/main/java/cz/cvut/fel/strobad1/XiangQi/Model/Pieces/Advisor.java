package cz.cvut.fel.strobad1.XiangQi.Model.Pieces;



import cz.cvut.fel.strobad1.XiangQi.Model.*;


import java.util.ArrayList;

// stuck in the castle, moves in an X shape

public class Advisor extends Piece {

    private final int value = 2;
    private int[][] offsets = {{+1,+1},{+1,-1},{-1,+1},{-1,-1}};

    @Override
    public int[][] getOffsets() {
        return offsets;
    }

    public Advisor(int row, int col, String color, Board board) {
        super(row, col, color, board);
    }

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




}