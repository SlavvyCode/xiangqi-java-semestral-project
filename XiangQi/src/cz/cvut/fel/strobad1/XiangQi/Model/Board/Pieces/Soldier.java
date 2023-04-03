package cz.cvut.fel.strobad1.XiangQi.Model.Board.Pieces;


import cz.cvut.fel.strobad1.XiangQi.Model.Board.Board;
import cz.cvut.fel.strobad1.XiangQi.Model.Board.Piece;

public class Soldier extends Piece{
    public Soldier(int row, int col, String color) {
        super(row, col, color);
    }

    @Override
    public void Move() {

        //like pawn but only forward, after river also side.
        if(color=="red" && row>4){
            //moves up and L,R

        }

        if(color=="black"&& row<5){
//            moves down and LR

        }
    }

    @Override
    public boolean isValidMove(Board board, int newRow, int newCol) {
        return false;
    }
}


