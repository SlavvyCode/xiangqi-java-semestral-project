package cz.cvut.fel.strobad1.xiangqi.model.pieces;
//like a knight in chess except:
//      A_A       if O has target, cannot move.
//       O
//       X     knight=horse=X

import cz.cvut.fel.strobad1.xiangqi.model.*;
import java.util.ArrayList;


public class Horse extends Piece {

    private final int[][] offsets = {{-2, -1}, {-1, -2}, {1, -2}, {2, -1}, {2, 1}, {1, 2}, {-1, 2}, {-2, 1}};
    public Horse(int row, int col, String color,Board board) {

        super(row, col, color,board);
    }





    public ArrayList<Cell> getMoveList(){


        ArrayList<Cell> moveList = new ArrayList<Cell>();

        //return valid moves for horse

        for(int[] offset : offsets) {

            //each offset is for example {1,2}
            int destRow = row + offset[0];
            int destCol = col + offset[1];

            // Check if destination is within the board
            if (destRow < 0 || destRow >= 10 || destCol < 0 || destCol >= 9) {
                continue;
            }
            //Takes CURRENT JUMP OFFSET and discards the decimal point leftover (0.5)
            int blockingRow = row + offset[0] / 2;
            int blockingCol = col + offset[1] / 2;


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
    public ArrayList<Cell> getValidMoves() {
        ArrayList<Cell> moveList = new ArrayList<Cell>();

        //return valid moves for horse

        for(int[] offset : offsets) {

            //each offset is for example {1,2}
            int destRow = row + offset[0];
            int destCol = col + offset[1];

            // Check if destination is within the board
            if (destRow < 0 || destRow >= 10 || destCol < 0 || destCol >= 9) {
                continue;
            }
            //Takes CURRENT JUMP OFFSET and discards the decimal point leftover (0.5)
            int blockingRow = row + offset[0] / 2;
            int blockingCol = col + offset[1] / 2;


            Piece blockingPiece = board.getCell(blockingRow, blockingCol).getPieceOnCell();


            if (blockingPiece != null) {
                continue;
            }


            if (checkValidityAndAddMove(destRow, destCol)) {
                moveList.add(board.getCell(destRow, destCol));
            }
        }
        return moveList;
    }

    @Override
    public int[][] getOffsets() {
        return offsets;
    }

    @Override
    protected boolean checkValidityAndAddMove(int destRow, int destCol) {
        return super.checkValidityAndAddMove(destRow, destCol);
    }


    @Override
    public Piece clone(Board newBoard) {
        // create a new Piece object with the same fields as this
        Horse newPiece = new Horse(this.row, this.col, this.color, newBoard);

        // return the new Piece object
        return newPiece;
    }
}