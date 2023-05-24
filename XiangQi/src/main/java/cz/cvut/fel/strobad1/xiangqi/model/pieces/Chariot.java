package cz.cvut.fel.strobad1.xiangqi.model.pieces;

import cz.cvut.fel.strobad1.xiangqi.model.*;

import java.util.ArrayList;

// == rook
public class Chariot extends Piece {

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


    public Chariot(int row, int col, colorEnum colorEnum, Board board) {
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

            boolean pieceInPath = false;
            boolean hasJumpedOverObstacle = false;


            //loops through each cell between the current location and the destination cell,
            // checking if there are any pieces on those cells.



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

//
//    @Override
//    public ArrayList<Cell> getValidMoves() {
//        ArrayList<Cell> moveList = new ArrayList<Cell>();
//
//        // Define the directions for the chariot to move
//        int[][] directions = {{-1,0},{0,-1},{0,1},{1,0}};
//
//        // Loop through each direction
//        for (int[] direction : directions) {
//
//            // Initialize the destination row and column as the current row and column
//            int destRow = row;
//            int destCol = col;
//
//            // Loop until the destination is out of the board
//            while (true) {
//
//                // Update the destination by adding the direction offset
//                destRow += direction[0];
//                destCol += direction[1];
//
//                // Check if destination is within the board
//                if (destRow < 0 || destRow >= numberOfRows || destCol < 0 || destCol >= numberOfCols) {
//                    break;
//                }
//
//                // Get the piece on the destination cell
//                Piece piece = board.getCell(destRow, destCol).getPieceOnCell();
//
//                // If there is no piece on the destination cell
//                if (piece == null) {
//                    // The chariot can move there
//                    moveList.add(board.getCell(destRow, destCol));
//                }
//                // If there is a piece on the destination cell
//                else {
//                    // If the piece is of the opposite color, the chariot can capture it
//                    if (piece.getColor() != this.color) {
//                        moveList.add(board.getCell(destRow, destCol));
//                    }
//                    // The chariot cannot move further in this direction
//                    break;
//                }
//            }
//        }
//
//        return moveList;
//    }

    @Override
    public ArrayList<Cell> getValidMoves() {
        ArrayList<Cell> moveList = new ArrayList<>();

        // Define the directions for the chariot to move
        int[][] directions = {{-1, 0}, {0, -1}, {0, 1}, {1, 0}};

        // Loop through each direction
        for (int[] direction : directions) {
            // Get the valid moves in the specified direction
            getValidMovesInDirection(direction, moveList);
        }

        return moveList;
    }

    private void getValidMovesInDirection(int[] direction, ArrayList<Cell> moveList) {
        // Initialize the destination row and column as the current row and column
        int destRow = row;
        int destCol = col;

        boolean pieceInPath=false;

        // Loop until the destination is out of the board
        while (true) {
            // Update the destination by adding the direction offset
            destRow += direction[0];
            destCol += direction[1];


            // Check if destination is within the board
            if (destRow < 0 || destRow >= Board.numberOfRows || destCol < 0 || destCol >= Board.numberOfCols) {
                break;
            }

            Cell destCell = board.getCell(destRow,destCol);

            if(destCell.getPieceOnCell()!=null){
                if (checkValidityAndAddMove(destRow, destCol)) {
                    moveList.add(board.getCell(destRow, destCol));
                }
                break;
            }

            // Check the validity of the move and add it to the moveList
            if (checkValidityAndAddMove(destRow, destCol)) {
                moveList.add(board.getCell(destRow, destCol));
            }
        }
    }

    @Override
    public int[][] getOffsets() {
        return offsets;
    }



    @Override
    public Piece clone(Board newBoard) {
        // create a new Piece object with the same fields as this
        Chariot newPiece = new Chariot(this.row, this.col, this.color, newBoard);

        // return the new Piece object
        return newPiece;
    }
}
