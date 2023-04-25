package cz.cvut.fel.strobad1.XiangQi.Model.Pieces;
// moves in a plus shape, contained to the palace. CANNOT have Line of sight with the enemy king

import cz.cvut.fel.strobad1.XiangQi.Model.*;

import java.util.ArrayList;

public class General extends Piece {

    private float value = 0;
    public General(int row, int col, String color) {
        super(row, col, color);
    }

    @Override
    public ArrayList<Cell> getValidMoves() {
        return null;
    }


    @Override
    public boolean isValidMove(int newRow, int newCol) {
        return false;
    }
}