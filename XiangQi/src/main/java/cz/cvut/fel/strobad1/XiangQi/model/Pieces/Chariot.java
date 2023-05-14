package cz.cvut.fel.strobad1.XiangQi.model.Pieces;

import cz.cvut.fel.strobad1.XiangQi.model.*;

import java.util.ArrayList;

// == rook
public class Chariot extends Piece {

    private static final float value = 9;

    private final int[][] offsets = {
            {-1,0},{0,-1},{0,1},{1,0},
            {-2,0},{0,-2},{0,2},{2,0},
            {-3,0},{0,-3},{0,3},{3,0},
            {-4,0},{0,-4},{0,4},{4,0},
            {-5,0},{0,-5},{0,5},{5,0},
            {-6,0},{0,-6},{0,6},{6,0},
            {-7,0},{0,-7},{0,7},{7,0},
            {-8,0},{0,-8},{0,8},{8,0},
            {-9,0},{0,-9},{0,9},{9,0},
            {-10,0},{0,-10},{0,10},{10,0}
    };


    public Chariot(int row, int col, String color, Board board) {
        super(row, col, color, board);
    }



    @Override
    public ArrayList<Cell> getMoveList() {

        ArrayList<Cell> moveList = new ArrayList<Cell>();


        for(int[] offset : offsets) {

            //each offset is for example {1,2}
            int destRow = row + offset[0];
            int destCol = col + offset[1];

            // Check if destination is within the board
            if (destRow < 0 || destRow >= 10 || destCol < 0 || destCol >= 9) {
                continue;
            }

            boolean pieceInPath = false;
            boolean hasJumpedOverObstacle = false;


            //loops through each cell between the current location and the destination cell,
            // checking if there are any pieces on those cells.



            //TODO CHECK FUNCTIONALITY

            for (int r = Math.min(destRow, row) + 1; r < Math.max(destRow, row); r++) {
                for (int c = Math.min(destCol, col) + 1; c < Math.max(destCol, col); c++) {

                    //Piece EXISTS on desired cell
                    if (board.getCell(r, c).getPieceOnCell() != null) {
                        if (!pieceInPath) {
                            pieceInPath = true;
                            break;
                        }
                    }
                }

                // if chariot already reached the last possible cell in this row, break
                if (pieceInPath) {
                    break;
                }
            }

            // Check if the move is valid
            if (board.getCell(destRow, destCol).getPieceOnCell() == null) {
                moveList.add(board.getCell(destRow, destCol));
            }

        }

        return moveList;
    }
    @Override
    public int[][] getOffsets() {
        return offsets;
    }
}
