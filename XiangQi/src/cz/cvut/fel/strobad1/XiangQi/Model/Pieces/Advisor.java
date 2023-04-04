package cz.cvut.fel.strobad1.XiangQi.Model.Pieces;
import cz.cvut.fel.strobad1.XiangQi.Model.Board;
import cz.cvut.fel.strobad1.XiangQi.Model.Piece;

// stuck in the castle, moves in an X shape

public class Advisor extends Piece {

    private final int value = 2;
    public Advisor(int row, int col, String color) {
        super(row, col, color);
    }

    @Override
    public boolean isValidMove(Board board, int newRow, int newCol) {
        return false;
    }
}