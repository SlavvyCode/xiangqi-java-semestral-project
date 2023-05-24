package cz.cvut.fel.strobad1.xiangqi.model.pieces;
// moves in an X shape, two fields away. it cannot jump over units

import cz.cvut.fel.strobad1.xiangqi.model.*;

import java.util.ArrayList;

public class Elephant extends Piece {

    private int[][] offsets = {{+2,+2},{+2,-2},{-2,+2},{-2,-2}};
    public Elephant(int row, int col, colorEnum colorEnum, Board board) {
        super(row, col, colorEnum, board);
    }

    @Override
    public ArrayList<Cell> getMoveList() {
        ArrayList<Cell> moveList = new ArrayList<Cell>();
        for(int[] offset : offsets) {

            //each offset is for example {1,2}
            int destRow = row + offset[0];
            int destCol = col + offset[1];



            //elephant only moves up to the river and back.
            if (this.color.equals(colorEnum.RED) && (destRow < 0 || destRow >= 5)){
                continue;
            } else if (this.color.equals(colorEnum.BLACK) && (destRow < 5 || destRow >= Board.numberOfRows)) {
                continue;
            }


            //within board's columns
            if (destCol < 0 || destCol >= Board.numberOfCols) {
                continue;
            }


            int blockingRow = row + offset[0] / 2;
            int blockingCol = col + offset[1] / 2;


//            if (blockingRow < 0 || blockingRow >= numberOfRows || blockingCol < 0 || blockingCol >= numberOfCols) {
//                System.err.println("wait wtf how ");
//            }

            Piece blockingPiece = board.getCell(blockingRow, blockingCol).getPieceOnCell();


            if (blockingPiece != null) {
                continue;
            }
            else
            {
                moveList.add(board.getCell(destRow, destCol));
            }

        }
        return moveList;

        }



    @Override
    public ArrayList<Cell> getValidMoves() {

        ArrayList<Cell> moveList = new ArrayList<Cell>();
        for(int[] offset : offsets) {

            //each offset is for example {1,2}
            int destRow = row + offset[0];
            int destCol = col + offset[1];



            //elephant only moves up to the river and back.
            if (this.color.equals(colorEnum.RED) && (destRow < 0 || destRow >= 5)){
                continue;
            } else if (this.color.equals(colorEnum.BLACK) && (destRow < 5 || destRow >= Board.numberOfRows)) {
                continue;
            }


            //within board's columns
            if (destCol < 0 || destCol >= Board.numberOfCols) {
                continue;
            }


            int blockingRow = row + offset[0] / 2;
            int blockingCol = col + offset[1] / 2;

            Piece blockingPiece = board.getCell(blockingRow, blockingCol).getPieceOnCell();


            if (blockingPiece != null) {
                continue;
            }
            else
            {
                if (checkValidityAndAddMove(destRow, destCol)) {
                    moveList.add(board.getCell(destRow, destCol));
                }
            }

        }
        return moveList;

    }


    @Override
    protected boolean checkValidityAndAddMove(int destRow, int destCol) {
        return super.checkValidityAndAddMove(destRow, destCol);
    }

    @Override
    public int[][] getOffsets() {
        return offsets;
    }

    @Override
    public Piece clone(Board newBoard) {
        // create a new Piece object with the same fields as this
        Elephant newPiece = new Elephant(this.row, this.col, this.color, newBoard);

        // return the new Piece object
        return newPiece;
    }


}