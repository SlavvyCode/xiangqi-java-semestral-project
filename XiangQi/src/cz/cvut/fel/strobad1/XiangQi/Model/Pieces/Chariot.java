package cz.cvut.fel.strobad1.XiangQi.Model.Pieces;

import cz.cvut.fel.strobad1.XiangQi.Model.Board;
import cz.cvut.fel.strobad1.XiangQi.Model.Piece;

// == rook
public class Chariot extends Piece {

    private float value = 9;
    public Chariot(int row, int col, String color) {
        super(row, col, color);
        this.value = value;
    }

    @Override
    public int[][] getValidMoves() {
        return new int[0][];
    }

    @Override
    public boolean isValidMove(int newRow, int newCol) {
        return false;
    }

    @Override
    public boolean isValidMove(Board board, int newRow, int newCol) {
        return false;
    }
}
