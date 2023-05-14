package cz.cvut.fel.strobad1.XiangQi.model.Pieces;
// moves in an X shape, two fields away. it cannot jump over units

import cz.cvut.fel.strobad1.XiangQi.model.*;

import java.util.ArrayList;

public class Elephant extends Piece {
    private float value = 2;

    private int[][] offsets = {{+2,+2},{+2,-2},{-2,+2},{-2,-2}};
    public Elephant(int row, int col, String color, Board board) {
        super(row, col, color, board);
    }

    @Override
    public ArrayList<Cell> getMoveList() {


        ArrayList<Cell> moveList = new ArrayList<Cell>();
        for(int[] offset : offsets) {

            //each offset is for example {1,2}
            int destRow = row + offset[0];
            int destCol = col + offset[1];



            if (this.color =="red"){

            }
            // Check if destination is within the player's side
            if (destRow < 0 || destRow >= 5 || destCol < 0 || destCol >= 9) {
                continue;
            }
            else if (destRow < 5 || destRow >= 10 || destCol < 0 || destCol >= 9) {
                continue;
            }


            int blockingRow = row + offset[0] / -2;
            int blockingCol = col + offset[1] / -2;


            Piece blockingPiece = board.getCell(blockingRow, blockingCol).getPieceOnCell();


            if (blockingPiece != null) {
                continue;
            }

            // add valid move to list



            moveList.add(board.getCell(destRow,destCol));
        }
        return moveList;
    }

    @Override
    public int[][] getOffsets() {
        return offsets;
    }



}