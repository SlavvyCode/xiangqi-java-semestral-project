package main.java.cz.cvut.fel.strobad1.XiangQi.Model.Pieces;
// moves in an X shape, two fields away. it cannot jump over units

import main.java.cz.cvut.fel.strobad1.XiangQi.Model.*;

import java.util.ArrayList;

public class Elephant extends Piece {
    private float value = 2;
    public Elephant(int row, int col, String color) {
        super(row, col, color);
    }

    @Override
    public ArrayList<Cell> getValidMoves() {
        return null;
    }

    @Override
    public boolean isValidMove(Board board, int newRow, int newCol) {
        return false;
    }
}