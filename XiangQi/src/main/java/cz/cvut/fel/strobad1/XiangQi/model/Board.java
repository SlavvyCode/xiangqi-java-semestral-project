package cz.cvut.fel.strobad1.XiangQi.model;


import cz.cvut.fel.strobad1.XiangQi.model.Pieces.*;

import java.util.ArrayList;
import java.util.Arrays;

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

    private Match match;

    private String[] movesPerformedThisTurn = new String[2];

    private Cell[][] cellList = new Cell[10][9];


    public Board(Match match) {

        this.match = match;

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
        Board newBoard = new Board(this.match);


//        // clone the cellList using a loop
//        newBoard.cellList = new Cell[10][9];
//
//        for (int i = 0; i < 10; i++) {
//            for (int j = 0; j < 9; j++) {
//
//
//                if (i < 5) {
//                    //finding palace on RedSide
//                    if (i < 3 && (j > 2 && j < 6)) {
//                        newBoard.cellList[i][j] = new Cell("red", null, true);
//                    } else {
//                        newBoard.cellList[i][j] = new Cell("red", null, false);
//                    }
//                } else {
//                    //check for palace on BLACKSide
//
//                    if (i > 6 && (j > 2 && j < 6)) {
//                        newBoard.cellList[i][j] = new Cell("black", null, true);
//                    } else {
//                        newBoard.cellList[i][j] = new Cell("black", null, false);
//                    }
//
//                }
//
//
//            }
//        }

//        Board tempBoard = match.getGameBoard();

        System.out.println(this);
        // clone the pieceList using a loop
        ArrayList<Piece> newPieceList = newBoard.getPieceList();
        newPieceList = new ArrayList<Piece>();
        for (Piece piece : this.pieceList) {
            Piece newPiece = (Piece) piece.clone();
            newPiece.setBoard(newBoard);

            newPieceList.add(newPiece);



            int pieceRow = piece.getRow();
            int pieceCol = piece.getCol();


//            newBoard.getCell(pieceRow, pieceCol).setPieceOnCell(newPiece);

            newBoard.updateCell(pieceRow,pieceCol,newPiece);

        }
        newBoard.setPieceList(newPieceList);

//        match.setGameBoard(tempBoard);
//        tempBoard.setPieceList(pieceList);


        // KRITICKY KOD????
        //General se prepise druhym. proc???? clone() je snad dobre udelan?
        //nic neodkazuje na soucasny piecelist???

        // newBoard se NEnastavuje na match board - blbost. - lze postupne projit debuggerem.
//        System.out.println(match.getGameBoard().getFirstCellWithPiece(match.getBlackGeneral()));
//        System.out.println(match.getGameBoard().getFirstCellWithPiece(match.getRedGeneral()));


        System.out.println(this);
//        for (int i = 0; i < 10; i++) {
//            for (int j = 0; j < 9; j++) {
//                if (getCell(i, j).getPieceOnCell() !=null) {
////                    if (getCell(i, j).getPieceOnCell().equals(pieceToFind)) {
//                    System.out.println(getCell(i, j).getPieceOnCell().getClass());
//                }
//            }
//        }




        // clone the movesPerformedThisTurn array using Arrays.copyOf
        newBoard.movesPerformedThisTurn = Arrays.copyOf(this.movesPerformedThisTurn, this.movesPerformedThisTurn.length);
        // return the new Board object
        return newBoard;
    }

    /**
     * Sets up piecs on the board.
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

    public void setMovesPerformedThisTurn(String movesPerformedThisTurn, int i) {

        this.movesPerformedThisTurn[i] = movesPerformedThisTurn;
    }

    public String[] getMovesPerformedThisTurn() {
        return movesPerformedThisTurn;
    }

    /**
     * Returns the cell which has a certain piece - for example, the red general, otherwise, throws a nullPointerException.
     *
     * @param pieceToFind
     * @return the cell of the piece we have.
     */
    public Cell getFirstCellWithPiece(Piece pieceToFind) {

//        int pieceRow = pieceToFind.getRow();
//        int pieceCol = pieceToFind.getCol();
//
//        return getCell(pieceRow,pieceCol);

        System.out.println(this);
        System.out.println("\n\n\n\n");


//
//        System.err.println("come back to getFirstCellWithPiece");
//        return getCell(pieceRow,pieceCol);
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 9; j++) {
                if (getCell(i, j).getPieceOnCell() == pieceToFind) {
//                    if (getCell(i, j).getPieceOnCell().equals(pieceToFind)) {
                    return getCell(i, j);
                }
            }
        }
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
        Cell selectedCell = cellList[row][col];
        selectedCell.setPieceOnCell(piece);
    }

    public ArrayList<Piece> getPieceList() {
        return pieceList;
    }


    public ArrayList<Piece> getPiecesCheckingRedGeneral() {

        General redGeneral = (General) match.getRedGeneral();

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
        Cell generalLocation = this.getFirstCellWithPiece(match.getBlackGeneral());

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

    public Match getMatch() {
        return match;
    }


//    @Override
//    public String toString() {
//
//        String output = "";
//        for (int i = 0; i < 10; i++) {
//            for (int j = 0; j < 9; j++) {
//
//                Piece piece = getCell(i, j).getPieceOnCell();
//
//                if (piece == null) {
//                    output += " ";
//                } else {
//
//                    output += piece.getClass().getSimpleName().toString().charAt(0);
//
//                }
//
//
//            }
//            output += "\n";
//
//        }
//
//        return output;
//    }




}



