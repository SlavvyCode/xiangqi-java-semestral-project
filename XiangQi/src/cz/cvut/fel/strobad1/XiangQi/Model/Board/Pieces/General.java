package cz.cvut.fel.strobad1.XiangQi.Model.Board.Pieces;
// moves in a plus shape, contained to the palace. CANNOT have Line of sight with the enemy king

import cz.cvut.fel.strobad1.XiangQi.Model.Board.Board;
import cz.cvut.fel.strobad1.XiangQi.Model.Piece;

public class General extends Piece {

    private float value = 0;
    public General(int row, int col, String color) {
        super(row, col, color);
    }

    @Override
    public boolean isValidMove(Board board, int newRow, int newCol) {
        return false;
    }
}