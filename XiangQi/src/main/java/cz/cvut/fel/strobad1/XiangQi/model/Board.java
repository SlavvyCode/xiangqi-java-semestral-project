package cz.cvut.fel.strobad1.XiangQi.model;


import cz.cvut.fel.strobad1.XiangQi.model.Pieces.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Logger;

public class Board implements Cloneable {


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


    public void setPieceList(ArrayList<Piece> pieceList) {
        this.pieceList = pieceList;
    }


    private ArrayList<Piece> pieceList;

    public void setRedTurn(boolean redTurn) {
        isRedTurn = redTurn;
    }

    public boolean isRedTurn() {
        return isRedTurn;
    }

    private boolean isRedTurn=true;
    Logger logger = Logger.getLogger(Board.class.getName());

    private String movePerformedThisTurn;

    private Cell[][] cellList = new Cell[10][9];


    public Board() {

        pieceList = new ArrayList<Piece>();


        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 9; j++) {

                if (i < 5) {
                    //finding palace on RedSide
                    if (i < 3 && (j > 2 && j < 6)) {
                        cellList[i][j] = new Cell("red", null, true);
                    } else {
                        cellList[i][j] = new Cell("red", null, false);
                    }
                } else {
                    //check for palace on BLACKSide

                    if (i > 6 && (j > 2 && j < 6)) {
                        cellList[i][j] = new Cell("black", null, true);
                    } else {
                        cellList[i][j] = new Cell("black", null, false);
                    }

                }


            }
        }
    }

    @Override
    public Board clone() throws CloneNotSupportedException {
        // create a new Board object
        Board newBoard = new Board();

        newBoard.setRedTurn(this.isRedTurn);
        newBoard.movePerformedThisTurn = this.movePerformedThisTurn;

        // clone the pieceList using a loop
        ArrayList<Piece> newPieceList = newBoard.getPieceList();

        for (Piece piece : this.pieceList) {

            //should work?
            Piece newPiece = (Piece) piece.clone(newBoard);

            newPieceList.add(newPiece);

            int pieceRow = piece.getRow();
            int pieceCol = piece.getCol();

            newBoard.updateCell(pieceRow,pieceCol,newPiece);

        }

        newBoard.setPieceList(newPieceList);


        return newBoard;
    }

    /**
     * Sets up pieces on the board in the default layout.
     */
    public void setUpPieces() {

        pieceList.removeAll(pieceList);
        pieceList = new ArrayList<Piece>();


        // Set up the red pieces
        // Red pieces
        pieceList.add(new Chariot(0, 0, "red", this));
        pieceList.add(new Elephant(0, 1, "red", this));
        pieceList.add(new Horse(0, 2, "red", this));
        pieceList.add(new Advisor(0, 3, "red", this));
        pieceList.add(new General(0, 4, "red", this));
        pieceList.add(new Advisor(0, 5, "red", this));
        pieceList.add(new Elephant(0, 6, "red", this));
        pieceList.add(new Horse(0, 7, "red", this));
        pieceList.add(new Chariot(0, 8, "red", this));
        pieceList.add(new Cannon(2, 1, "red", this));
        pieceList.add(new Cannon(2, 7, "red", this));
        pieceList.add(new Soldier(3, 0, "red", this));
        pieceList.add(new Soldier(3, 2, "red", this));
        pieceList.add(new Soldier(3, 4, "red", this));
        pieceList.add(new Soldier(3, 6, "red", this));
        pieceList.add(new Soldier(3, 8, "red", this));

        // Black pieces
        pieceList.add(new Chariot(9, 0, "black", this));
        pieceList.add(new Elephant(9, 1, "black", this));
        pieceList.add(new Horse(9, 2, "black", this));
        pieceList.add(new Advisor(9, 3, "black", this));
        pieceList.add(new General(9, 4, "black", this));
        pieceList.add(new Advisor(9, 5, "black", this));
        pieceList.add(new Elephant(9, 6, "black", this));
        pieceList.add(new Horse(9, 7, "black", this));
        pieceList.add(new Chariot(9, 8, "black", this));
        pieceList.add(new Cannon(7, 1, "black", this));
        pieceList.add(new Cannon(7, 7, "black", this));
        pieceList.add(new Soldier(6, 0, "black", this));
        pieceList.add(new Soldier(6, 2, "black", this));
        pieceList.add(new Soldier(6, 4, "black", this));
        pieceList.add(new Soldier(6, 6, "black", this));
        pieceList.add(new Soldier(6, 8, "black", this));
        //5 pawns soldiers
        //2 cannons
        //2 horses
        //2 elephants
        //2 advisors
        //2 chariots rooks

    }

    public void setMovePerformedThisTurn(String movePerformedThisTurn) {

        this.movePerformedThisTurn = movePerformedThisTurn;
    }

    public String getMovePerformedThisTurn() {
        return movePerformedThisTurn;
    }

    /**
     * Returns the cell which has a certain piece - for example, the red general, otherwise, throws a nullPointerException.
     *
     * @param pieceToFind
     * @return the cell of the piece we have.
     */
    public Cell getFirstCellWithPiece(Piece pieceToFind) {

//
//        Cell cellToReturn = getCell(pieceToFind.getRow(),pieceToFind.getCol());
//
//        if (cellToReturn == null){
//
//            logger.severe("An error occurred: Piece not found!");
//            throw new NullPointerException();
//        }
//
//        return cellToReturn;

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 9; j++) {
                if (getCell(i, j).getPieceOnCell() == pieceToFind) {
//                    if (getCell(i, j).getPieceOnCell().equals(pieceToFind)) {
                    return getCell(i, j);
                }
            }
        }
        logger.severe("An error occurred: Piece not found!");
        throw new NullPointerException();
    }

    /**
     * Removes or sets a piece on a cell that we decide by its coordinates.
     *
     * @param row   the board's row
     * @param col   the board's col
     * @param piece the piece or lack thereof that we want to put or remove from the cell.
     */
    public void updateCell(int row, int col, Piece piece) {
        Cell selectedCell = this.cellList[row][col];
        selectedCell.setPieceOnCell(piece);
    }

    public ArrayList<Piece> getPieceList() {
        return pieceList;
    }


    public ArrayList<Piece> getPiecesCheckingRedGeneral() {

        General redGeneral = (General) getRedGeneral();

        ArrayList<Piece> checkingPieces = new ArrayList<>();

        Cell generalLocation = getCell(redGeneral.getRow(), redGeneral.getCol());

        for (Piece enemyPiece : this.getPieceList()) {
            if (enemyPiece.getColor() == "black" && enemyPiece.getMoveList().contains(generalLocation)) {
                checkingPieces.add(enemyPiece);
            }
        }
        return checkingPieces;
    }

    public ArrayList<Piece> getPiecesCheckingBlackGeneral() {
        ArrayList<Piece> checkingPieces = new ArrayList<>();
        Cell generalLocation = this.getFirstCellWithPiece(getBlackGeneral());

        for (Piece enemyPiece : this.getPieceList()) {
            if (enemyPiece.getColor().equals("red") && enemyPiece.getMoveList().contains(generalLocation)) {
                checkingPieces.add(enemyPiece);
            }
        }
        return checkingPieces;

    }


    public Cell[][] getCellList() {
        return cellList;
    }

    public Cell getCell(int row, int col) {
        return cellList[row][col];
    }



    @Override
    public String toString() {

        String output = "";
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 9; j++) {

                Piece piece = getCell(i, j).getPieceOnCell();

                if (piece == null) {
                    output += " ";
                } else {

                    output += piece.getClass().getSimpleName().toString().charAt(0);

                }


            }
            output += "\n";

        }

        return output;
    }


    @Override
    public boolean equals(Object boardToCompare) {

        if(boardToCompare.getClass() != Board.class){
            return false;
        }
        Board board = (Board) boardToCompare;

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 9; j++) {


                 if(!board.cellList[i][j].equals(cellList[i][j])){
                     return false;
                 }

            }
        }



        return true;

    }
    /**
     *
     * @return true if the generals can "see" each other
     */


    public boolean flyingGeneralCheck() {
        General redGeneral = (General) getRedGeneral();
        General blackGeneral = (General) getBlackGeneral();

        // If the generals aren't in the same column, no need to check further
        if (redGeneral.getCol() != blackGeneral.getCol()) {
            return false;
        }

        // Loop through the pieces in the same column as the generals
        for (Piece piece : getPieceList()) {
            // Skip the generals themselves
            if (piece == redGeneral || piece == blackGeneral) {
                continue;
            }

            // Check if the piece is between the generals
            if (piece.getCol() == redGeneral.getCol()) {
                int minRow = Math.min(redGeneral.getRow(), blackGeneral.getRow());
                int maxRow = Math.max(redGeneral.getRow(), blackGeneral.getRow());

                // Check if the piece is in the same column and its row is between the generals' rows
                if (piece.getRow() > minRow && piece.getRow() < maxRow) {
                    return false;
                }
            }
        }

        // If no piece is between the generals, return true
        return true;
    }

    /**
     * Get red general by looping through the pieceList of the board else throw NullPointerException
     *
     * @return red general
     */
    public Piece getRedGeneral() {

        General redGeneral = null;

        for (Piece piece : getPieceList()) {
            if (piece instanceof General && piece.getColor() == "red") {
                redGeneral = (General) piece;
            }
        }
        if (redGeneral == null) {
            throw new NullPointerException();
        }
        return redGeneral;
    }

    /**
     * Get black general by looping through the pieceList of the board else throw NullPointerException
     *
     * @return black general
     */
    public Piece getBlackGeneral() {

        General blackGeneral = null;

        for (Piece piece : getPieceList()) {
            if (piece instanceof General && piece.getColor().equals("black")) {
                blackGeneral = (General) piece;
            }
        }
        if (blackGeneral == null) {
            throw new NullPointerException();
        }
        return blackGeneral;
    }


}





