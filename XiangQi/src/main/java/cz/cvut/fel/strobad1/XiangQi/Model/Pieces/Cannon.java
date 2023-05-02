package cz.cvut.fel.strobad1.XiangQi.Model.Pieces;


import cz.cvut.fel.strobad1.XiangQi.Model.*;

import javax.security.auth.login.AccountExpiredException;
import java.util.ArrayList;

// moves like a chariot but captures only after jumping over another piece.
// the cannon can ONLY jump over pieces WHEN capturing, otherwise it moves like a chariot.
public class Cannon extends Piece {

    private final float value = 4.5f;

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

    public Cannon(int row, int col, String color, Board board) {
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

            boolean hasObstacle = false;
            boolean hasJumpedOverObstacle = false;


            //loops through each cell between the current location and the destination cell,
            // checking if there are any pieces on those cells.


            for (int r = Math.min(destRow, row) + 1; r < Math.max(destRow, row); r++) {
                for (int c = Math.min(destCol, col) + 1; c < Math.max(destCol, col); c++) {
                    if (board.getCell(r, c).getPieceOnCell() != null) {
                        // If there is a piece and hasObstacle is false, the code sets hasObstacle to true.
                        if (!hasObstacle) {
                            hasObstacle = true;
                        // If there is a piece and hasObstacle is already true, the code sets hasJumpedOverObstacle to true and breaks out of the loop.
                        } else {
                            hasJumpedOverObstacle = true;
                            break;
                        }
                    }
                }

                // if cannon already jumped over one obstacle, break
                if (hasJumpedOverObstacle) {
                    break;
                }
            }

            // Check if the move is valid
            if (board.getCell(destRow, destCol).getPieceOnCell() == null) {
                if (!hasObstacle) {
                    moveList.add(board.getCell(destRow, destCol));
                }
            } else {
                if (board.getCell(destRow, destCol).getPieceOnCell().getColor() != this.color && !hasJumpedOverObstacle) {
                    moveList.add(board.getCell(destRow, destCol));
                }
            }

        }

        return moveList;
    }

    @Override
    public int[][] getOffsets() {
        return offsets;
    }
    public ArrayList<Cell> getValidMoves() {
        ArrayList<Cell> moveList = new ArrayList<Cell>();


        for(int[] offset : offsets) {

            //each offset is for example {1,2}

            int destRow = this.getRow() + offset[0];
            int destCol = this.getCol() + offset[1];

            // Check if destination is within the board, else move on to the next move
            if (destRow < 0 || destRow >= 10 || destCol < 0 || destCol >= 9) {
                continue;
            }

//            if the piece can move to this new spot and not cause any checkmates to own king, it's in the valid move list

            Cell currentCell = board.getCell(this.getRow(),this.getCol());
            Cell destCell = board.getCell(destRow,destCol);

//            gets placed back if checkmates arise.
            Piece destCellOriginalPiece = destCell.getPieceOnCell();

            currentCell.setPieceOnCell(null);
            destCell.setPieceOnCell(this);


            int amountOfCheckingPieces;

            if(this.color=="red"){

                amountOfCheckingPieces = board.getPiecesCheckingRedGeneral().size();
            }
            else
            {
                amountOfCheckingPieces = board.getPiecesCheckingBlackGeneral().size();
            }

            //return original position

            currentCell.setPieceOnCell(this);
            destCell.setPieceOnCell(destCellOriginalPiece);

            if(amountOfCheckingPieces>0){
                continue;
            }

            moveList.add(board.getCell(destRow,destCol));

        }
        return moveList;

    }


}
