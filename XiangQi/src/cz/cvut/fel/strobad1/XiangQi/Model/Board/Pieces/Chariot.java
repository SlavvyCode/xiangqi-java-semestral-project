package cz.cvut.fel.strobad1.XiangQi.Model.Board.Pieces;

import cz.cvut.fel.strobad1.XiangQi.Model.Board.Board;
import cz.cvut.fel.strobad1.XiangQi.Model.Board.Piece;

// == rook
public class Chariot extends Piece {

    private float value = 9;
    public Chariot(int row, int col, String color) {
        super(row, col, color);
        this.value = value;
    }

    @Override
    public boolean isValidMove(Board board, int newRow, int newCol) {
        return false;
    }
}
