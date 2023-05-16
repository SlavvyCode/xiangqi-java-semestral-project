package cz.cvut.fel.strobad1.XiangQi.model.Pieces;



import cz.cvut.fel.strobad1.XiangQi.model.*;


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


            if (destRow < 0 || destRow >= 10 || destCol < 0 || destCol >= 9) {
                continue;
            }

            Cell destCell = board.getCell(destRow, destCol);

            if(!destCell.getIsPalace()){
                continue;
            }

            moveList.add(destCell);

        }
        return moveList;
    }


    @Override
    public Piece clone() {
        // create a new Piece object with the same fields as this
        Advisor newPiece = new Advisor(this.row, this.col, this.color, this.board);

        // return the new Piece object
        return newPiece;
    }





}