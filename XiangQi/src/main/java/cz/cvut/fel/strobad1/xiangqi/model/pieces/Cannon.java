package cz.cvut.fel.strobad1.xiangqi.model.pieces;


import cz.cvut.fel.strobad1.xiangqi.model.*;

import java.util.ArrayList;

// moves like a chariot but captures only after jumping over another piece.
// the cannon can ONLY jump over pieces WHEN capturing, otherwise it moves like a chariot.
public class Cannon extends Piece {

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

    public Cannon(int row, int col, colorEnum colorEnum, Board board) {
        super(row, col, colorEnum, board);
    }


    @Override
    public ArrayList<Cell> getMoveList() {
        ArrayList<Cell> moveList = new ArrayList<Cell>();


        for(int[] offset : offsets) {

            //each offset is for example {1,2}
            int destRow = row + offset[0];
            int destCol = col + offset[1];

            // Check if destination is within the board
            if (destRow < 0 || destRow >= Board.numberOfRows || destCol < 0 || destCol >= Board.numberOfCols) {
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

        // Define the directions for the cannon to move
        int[][] directions = {{-1,0},{0,-1},{0,1},{1,0}};

        // Loop through each direction
        for (int[] direction : directions) {

            // Initialize the destination row and column as the current row and column
            int destRow = this.getRow();
            int destCol = this.getCol();

            // Initialize a flag to indicate if the cannon has jumped over an obstacle
            boolean hasJumped = false;

            // Loop until the destination is out of the board
            while (true) {

                // Update the destination by adding the direction offset
                destRow += direction[0];
                destCol += direction[1];

                // Check if the destination is within the board
                if (destRow < 0 || destRow >= Board.numberOfRows || destCol < 0 || destCol >= Board.numberOfCols) {
                    break;
                }

                // Get the piece on the destination cell
                Piece pieceOnDestination = board.getCell(destRow, destCol).getPieceOnCell();

                // If there is no piece on the destination cell
                if (pieceOnDestination == null) {
                    // If the cannon hasnot jumped over an obstacle, it can move further
                    if (hasJumped) {
                        continue;
                    }
                }
                // If there is a piece on the destination cell
                else {
                    // If the cannon has not jumped over an obstacle, it can jump over this piece
                    if (!hasJumped) {
                        hasJumped = true;
                        continue;
                    }
                    // If the cannon has jumped over an obstacle and the piece is the same team it goes no further
                    else if (pieceOnDestination.getColor() == this.color) {
                        break;
                    }
                    // Otherwise, it can move just this far and no further in this direction
                    else {

                        if(checkValidityAndAddMove(destRow, destCol)) ;
                        moveList.add(board.getCell(destRow, destCol));
                        break;
                    }
                }

                if(checkValidityAndAddMove(destRow, destCol));
                moveList.add(board.getCell(destRow, destCol));
            }
        }

        return moveList;
    }

    @Override
    public boolean checkValidityAndAddMove(int destRow, int destCol) {
        Cell currentCell = board.getCell(this.getRow(),this.getCol());
        Cell destCell = board.getCell(destRow, destCol);

        int currentRow = this.getRow();
        int currentCol = this.getCol();

        // gets placed back if checkmates arise.
        Piece destCellOriginalPiece = destCell.getPieceOnCell();

        if(destCellOriginalPiece != null && destCellOriginalPiece.getColor().equals(this.color)){
            return false;
        }

        move(destRow, destCol);

        int amountOfCheckingPieces;

        if(this.color.equals(colorEnum.RED)){
            amountOfCheckingPieces = board.getPiecesCheckingRedGeneral().size();
        }
        else
        {
            amountOfCheckingPieces = board.getPiecesCheckingBlackGeneral().size();
        }

        // uses general.getcol()
        if(board.flyingGeneralCheck() == true){

            move(currentRow,currentCol);
            if(destCellOriginalPiece !=null) {
                destCellOriginalPiece.move(destRow, destCol);
            }
            return false;
        }

        move(currentRow,currentCol);

        if(destCellOriginalPiece !=null){
            destCellOriginalPiece.move(destRow, destCol);
        }

        if(amountOfCheckingPieces>0){
            return false;
        }

        return true;
    }




    @Override
    public Piece clone(Board newBoard) {
        // create a new Piece object with the same fields as this
        Cannon newPiece = new Cannon(this.row, this.col, this.color, newBoard);

        // return the new Piece object
        return newPiece;
    }

}
