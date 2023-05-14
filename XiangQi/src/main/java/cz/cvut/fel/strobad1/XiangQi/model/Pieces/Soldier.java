package cz.cvut.fel.strobad1.XiangQi.model.Pieces;


import cz.cvut.fel.strobad1.XiangQi.model.*;

import java.util.ArrayList;

public class Soldier extends Piece{




    public Soldier(int row, int col, String color, Board board) {
        super(row, col, color, board);
    }


    @Override
    public ArrayList<Cell> getMoveList() {

        ArrayList<Cell> moveList = new ArrayList<>();



        int[][] offsets = this.getOffsets();

        for (int[] offset : offsets) {
            int destRow = row + offset[0];
            int destCol = col + offset[1];

            // Check if destination is within the board
            if (destRow < 0 || destRow >= 10 || destCol < 0 || destCol >= 9) {
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
        int[][] forwardOffsets;
        if (color.equals("red")) {
            if (row >= 5) {
                // Soldier has crossed the river, can move horizontally as well
                forwardOffsets = new int[][]{{0, 1}, {-1, 0},{1,0}};
            } else {
                // Soldier is still behind the river, can only move vertically
                forwardOffsets = new int[][]{{0, 1}};
            }
        } else {
            //black soldier
            if (row <= 4) {
                // Soldier has crossed the river, can move horizontally as well
                forwardOffsets = new int[][]{{0, -1}, {1, 0}, {-1, 0}};
            } else {
                // Soldier is still behind the river, can only move vertically
                forwardOffsets = new int[][]{{-1, 0}};
            }
        }
        return forwardOffsets;
    }

}


