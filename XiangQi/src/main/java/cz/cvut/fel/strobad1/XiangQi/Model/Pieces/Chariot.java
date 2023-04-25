package main.java.cz.cvut.fel.strobad1.XiangQi.Model.Pieces;

import main.java.cz.cvut.fel.strobad1.XiangQi.Model.*;

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

        return null;
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
