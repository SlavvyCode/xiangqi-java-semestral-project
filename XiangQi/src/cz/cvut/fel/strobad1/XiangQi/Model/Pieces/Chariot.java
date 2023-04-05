package cz.cvut.fel.strobad1.XiangQi.Model.Pieces;

import cz.cvut.fel.strobad1.XiangQi.Model.Board;
import cz.cvut.fel.strobad1.XiangQi.Model.Cell;
import cz.cvut.fel.strobad1.XiangQi.Model.Piece;

import java.util.ArrayList;

// == rook
public class Chariot extends Piece {

    private float value = 9;
    public Chariot(int row, int col, String color) {
        super(row, col, color);
        this.value = value;
    }

    @Override
    public ArrayList<Cell> getValidMoves() {

        return ;
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
