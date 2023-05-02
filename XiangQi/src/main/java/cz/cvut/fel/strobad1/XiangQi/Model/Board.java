package cz.cvut.fel.strobad1.XiangQi.Model;


import cz.cvut.fel.strobad1.XiangQi.Model.Pieces.*;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Board implements Cloneable{


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
    private ArrayList<Piece> pieceList;

    private Cell[][] cellList = new Cell[10][9];


    public Cell[][] getCellList() {
        return cellList;
    }

    public Cell getCell(int row, int col) {
        return cellList[row][col];
    }
    public Board() {

        pieceList = new ArrayList<Piece>();


        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 9; j++) {
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
        this.pieceList = pieceList;
        this.cellList = cellList;
    }

    /**
     * Sets up piecs on the board.
     */
    public void setUpPieces() {

        pieceList.removeAll(pieceList);
        pieceList = new ArrayList<Piece>();



        //DummyFigures Below:

        Soldier soldierRed1 = new Soldier(0,0, "red",this);
        pieceList.add(soldierRed1);



        General redGeneral = new General(0,2, "red", this);
        pieceList.add(redGeneral);


        General blackGeneral = new General(8,8, "black", this);
        pieceList.add(blackGeneral);


        //5 pawns soldiers
        //2 cannons
        //2 horses
        //2 elephants
        //2 advisors
        //2 chariots rooks

    }

    /**
     * Returns the cell which has a certain piece - for example, the red general, otherwise, throws a nullPointerException.
     * @param pieceToFind
     * @return the cell of the piece we have.
     */
    public Cell getFirstCellWithPiece(Piece pieceToFind){
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 9; j++) {
                // TODO MAKE BETTER if needed? This only finds the FIRST cell with a piece instead of all of them. but i dont need it?????
                if(getCell(i,j).getPieceOnCell()==pieceToFind)
                {
                    return getCell(i,j);
                }
            }
        }
        throw new NullPointerException();
    }

    /**
     * Removes or sets a piece on a cell that we decide by its coordinates.
     * @param row the board's row
     * @param col the board's col
     * @param piece the piece or lack thereof that we want to put or remove from the cell.
     */
    public void updateCell(int row, int col, Piece piece) {
        Cell selectedCell = cellList[row][col];
        selectedCell.setPieceOnCell(piece);
    }
    public ArrayList<Piece> getPieceList() {
        return pieceList;
    }


    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }




    public ArrayList<Piece> getPiecesCheckingRedGeneral(){
        General redGeneral = (General) Main.getMatch().getRedGeneral();

        ArrayList<Piece> checkingPieces = new ArrayList<>();
        Cell generalLocation = this.getFirstCellWithPiece(redGeneral);

        for (Piece enemyPiece: this.getPieceList()) {
            if (enemyPiece.getColor()=="black" && enemyPiece.getMoveList().contains(generalLocation)){
                checkingPieces.add(enemyPiece);
            }
        }
        return checkingPieces;
    }
    public ArrayList<Piece> getPiecesCheckingBlackGeneral(){
            ArrayList<Piece> checkingPieces = new ArrayList<>();
            Cell generalLocation = this.getFirstCellWithPiece(Main.getMatch().getBlackGeneral());

            for (Piece enemyPiece: this.getPieceList()) {
                if (enemyPiece.getColor()=="red" && enemyPiece.getValidMoves().contains(generalLocation)){
                    checkingPieces.add(enemyPiece);
                }
            }
            return checkingPieces;

    }

}



