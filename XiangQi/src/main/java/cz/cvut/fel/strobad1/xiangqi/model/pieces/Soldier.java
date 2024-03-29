package cz.cvut.fel.strobad1.xiangqi.model.pieces;


import cz.cvut.fel.strobad1.xiangqi.model.*;

import java.util.ArrayList;

public class Soldier extends Piece{




    public Soldier(int row, int col, colorEnum colorEnum, Board board) {
        super(row, col, colorEnum, board);
    }


    @Override
    public ArrayList<Cell> getMoveList() {

        ArrayList<Cell> moveList = new ArrayList<>();



        int[][] offsets = this.getOffsets();

        for (int[] offset : offsets) {
            int destRow = row + offset[0];
            int destCol = col + offset[1];

            // Check if destination is within the board
            if (destRow < 0 || destRow >= Board.numberOfRows || destCol < 0 || destCol >= Board.numberOfCols) {
                continue;
            }

            Cell destCell = board.getCell(destRow, destCol);

            if (destCell.getPieceOnCell() == null || destCell.getPieceOnCell().getColor() != color) {
                moveList.add(destCell);
            }
        }

        return moveList;

    }

    @Override
    public int[][] getOffsets() {
        int[][] offsets;
        if (color.equals(colorEnum.RED)) {
            if (row >= 5) {
                // Soldier has crossed the river, can move horizontally as well
                offsets = new int[][]{{1, 0}, {0, 1},{0,-1}};
            } else {
                // Soldier is still behind the river, can only move vertically
                offsets = new int[][]{{1, 0}};
            }
        } else {
            //black soldier
            if (row <= 4) {
                // Soldier has crossed the river, can move horizontally as well
                offsets = new int[][]{{-1, 0}, {0, 1},{0,-1}};
            } else {
                // Soldier is still behind the river, can only move vertically
                offsets = new int[][]{{-1, 0}};
            }
        }
        return offsets;
    }


    @Override
    public Piece clone(Board newBoard) {
        // create a new Piece object with the same fields as this
        Soldier newPiece = new Soldier(this.row, this.col, this.color, newBoard);

        // return the new Piece object
        return newPiece;
    }
}


