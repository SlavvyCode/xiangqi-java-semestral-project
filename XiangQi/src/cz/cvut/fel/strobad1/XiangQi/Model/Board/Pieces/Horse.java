package cz.cvut.fel.strobad1.XiangQi.Model.Board.Pieces;
//like a knight in chess except:
//      A_A       if O has target, cannot move.
//       O
//       X     knight=horse=X

import cz.cvut.fel.strobad1.XiangQi.Model.Board.Board;
import cz.cvut.fel.strobad1.XiangQi.Model.Piece;

public class Horse extends Piece {
    public Horse(int row, int col, String color) {
        super(row, col, color);
    }

    @Override
    public boolean isValidMove(Board board, int newRow, int newCol) {
        return false;
    }
}