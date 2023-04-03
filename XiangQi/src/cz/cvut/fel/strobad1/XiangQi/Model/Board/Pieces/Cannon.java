package cz.cvut.fel.strobad1.XiangQi.Model.Board.Pieces;

import cz.cvut.fel.strobad1.XiangQi.Model.Board.Board;
import cz.cvut.fel.strobad1.XiangQi.Model.Board.Piece;

// moves like a chariot but captures only after jumping over another piece.
// the cannon can ONLY jump over pieces WHEN capturing, otherwise it moves like a chariot.
public class Cannon extends Piece {

    private final float value = 4.5f;
    public Cannon(int row, int col, String color) {
        super(row, col, color);
    }

    @Override
    public boolean isValidMove(Board board, int newRow, int newCol) {
        return false;
    }
}
