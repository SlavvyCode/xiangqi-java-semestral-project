package cz.cvut.fel.strobad1.XiangQi.Model.Pieces;


import cz.cvut.fel.strobad1.XiangQi.Model.*;

import java.util.ArrayList;

public class Soldier extends Piece{
    public Soldier(int row, int col, String color) {
        super(row, col, color);
    }

    @Override
    public ArrayList<Cell> getValidMoves() {
        return null;
    }

    @Override
    public boolean isValidMove(int newRow, int newCol) {

        //like pawn but only forward, after river also side.
        if(this.getColor() =="red"&& this.getRow()>5){
            //moves up and L,R

        }

        if(this.getColor() =="black"&& this.getRow()<5){
//            moves down and LR

        }

        return false;
    }
}


