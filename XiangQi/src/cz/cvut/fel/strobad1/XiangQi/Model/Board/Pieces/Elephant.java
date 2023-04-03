package cz.cvut.fel.strobad1.XiangQi.Model.Board.Pieces;
// moves in an X shape, two fields away. it cannot jump over units

import cz.cvut.fel.strobad1.XiangQi.Model.Board.Board;
import cz.cvut.fel.strobad1.XiangQi.Model.Piece;

public class Elephant extends Piece {
    private float value = 2;
    public Elephant(int row, int col, String color) {
        super(row, col, color);
    }

    @Override
    public boolean isValidMove(Board board, int newRow, int newCol) {
        return false;
    }
}