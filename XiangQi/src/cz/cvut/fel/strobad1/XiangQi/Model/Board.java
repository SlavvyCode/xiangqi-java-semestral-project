package cz.cvut.fel.strobad1.XiangQi.Model;

import cz.cvut.fel.strobad1.XiangQi.Model.Pieces.*;

import java.util.ArrayList;

public class Board {


//         10 +===X===X===X===X===X===X===X===+ BLACK SIDE
//            |   |   |   | \ | / |   |   |   |
//          9 +---+---+---+---X---+---+---+---+
//            |   |   |   | / | \ |   |   |   |
//          8 +---+---+---+---+---+---+---+---+
//            |   |   |   |   |   |   |   |   |
//          7 +---+---+---+---+---+---+---+---+
//            |   |   |   |   |   |   |   |   |
//          6 +---+---+---+---+---+---+---+---+
//            +===+===+===+===+===+===+===+===+ RIVER
//          5 +---+---+---+---+---+---+---+---+
//            |   |   |   |   |   |   |   |   |
//          4 +---+---+---+---+---+---+---+---+
//            |   |   |   |   |   |   |   |   |
//          3 +---+---+---+---+---+---+---+---+
//            |   |   |   | \ | / |   |   |   |
//          2 +---+---+---+---X---+---+---+---+
//            |   |   |   | / | \ |   |   |   |
//          1 +===X===X===X===X===X===X===X===+ RED SIDE
//            1   2   3   4   5   6   7   8   9
    //


    // RED STARTS


    ///****** SHOULD THE BOARD REMEMBER THE PIECES' PLACES OR SHOULD THE PIECES THEMSELVES
    private int[] rows = {10};
    private int[] cols = {9};
    private ArrayList<Piece> pieceList;

    private Cell[][] cellList = new Cell[rows.length][cols.length];

    public Board() {
        for (int i = 0; i < 10; i++) {
            rows[i] = i;
            for (int j = 0; j < 9; j++) {
                cols[j] = j;

                //// make sure indexing here is right *********************
                if (i < 5) {
                    //finding palace on RedSide
                    if (i < 1 && (j > 2 || j < 6)) {
                        cellList[i][j] = new Cell("red", null, true);
                    } else {
                        cellList[i][j] = new Cell("red", null, false);
                    }
                } else {
                    //check for palace on BLACKSide

                    if (i > 6 && (j > 2 || j < 6)) {
                        cellList[i][j] = new Cell("black", null, true);
                    } else {
                        cellList[i][j] = new Cell("black", null, false);
                    }

                }


            }
        }
    }

    public Board(int[] rows, int[] cols, ArrayList<Piece> pieceList, Cell[][] cellList) {
        this.rows = rows;
        this.cols = cols;
        this.pieceList = pieceList;
        this.cellList = cellList;
    }


    public void resetBoard() {
        pieceList.removeAll(pieceList);
        pieceList = new ArrayList<Piece>();
        Soldier soldierRed1 = new Soldier(0,0, "red");
        pieceList.add(soldierRed1);


        //5 pawns soldiers
        //2 cannons
        //2 horses
        //2 elephants
        //2 advisors
        //2 chariots rooks

    }

    public void updateCell(int row, int col, Piece piece) {
        Cell selectedCell = cellList[row][col];
        selectedCell.setPieceOnCell(piece);
    }

    public ArrayList<Piece> getPieceList() {
        return pieceList;
    }
}



