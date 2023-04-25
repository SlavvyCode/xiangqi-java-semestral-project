package main.java.cz.cvut.fel.strobad1.XiangQi.Model.Pieces;



import main.java.cz.cvut.fel.strobad1.XiangQi.Model.*;


import java.util.ArrayList;

// stuck in the castle, moves in an X shape

public class Advisor extends Piece {

    private final int value = 2;
    public Advisor(int row, int col, String color) {
        super(row, col, color);
    }

    @Override
    public ArrayList<Cell> getValidMoves() {
        return new ArrayList<Cell>();
    }

    public boolean isValidMove(Board board, int newRow, int newCol) {
        return false;
    }
}