package cz.cvut.fel.strobad1.XiangQi.model;


import cz.cvut.fel.strobad1.XiangQi.model.Pieces.Advisor;
import cz.cvut.fel.strobad1.XiangQi.model.Pieces.General;

import java.util.ArrayList;

public abstract class Piece implements Cloneable {

    protected final String color;
    //location of figure
    protected int row;
    protected int col;
    protected Board board;
    protected Match match;


    public Piece(int row, int col, String color, Board board) {
        this.row = row;
        this.col = col;
        this.color = color;
        this.board = board;
        this.board.updateCell(row, col, this);

        this.match = this.board.getMatch();

    }

    public abstract int[][] getOffsets();

    public void setRow(int row) {
        this.row = row;
    }

    public void setCol(int col) {
        this.col = col;
    }

    /**
     * returns an arraylist of all the piece's valid moves.
     */
    public ArrayList<Cell> getValidMoves() {

        int[][] offsets = getOffsets();



        ArrayList<Cell> moveList = new ArrayList<Cell>();

        //example offsets for king
        //int[][] offsets = {{+1, 0},{-1,0},{0,+1},{0,-1}};

        for (int[] offset : offsets) {

            //each offset is for example {1,2}

            int destRow = this.getRow() + offset[0];
            int destCol = this.getCol() + offset[1];

            // Check if destination is within the board, else move on to the next move
            if (destRow < 0 || destRow >= 10 || destCol < 0 || destCol >= 9) {
                continue;
            }


//            if the piece can move to this new spot and not let a checkmate happen, it's in the valid move list


            int currentRow = this.getRow();
            int currentCol = this.getCol();

            Cell destCell = board.getCell(destRow, destCol);


//            gets placed back if checkmates arise.
            Piece destCellOriginalPiece = destCell.getPieceOnCell();


            if(destCellOriginalPiece != null && destCellOriginalPiece.getColor().equals(this.color)){
                continue;
            }


            move(destRow,destCol);


            int amountOfCheckingPieces;
            if (this.color == "red") {
                amountOfCheckingPieces = board.getPiecesCheckingRedGeneral().size();
            } else {
                amountOfCheckingPieces = board.getPiecesCheckingBlackGeneral().size();
            }


            if(match.flyingGeneralCheck() == true){

                //return to former position
                move(currentRow,currentCol);
                //return original destination cell piece to its cell
                if(destCellOriginalPiece !=null) {
                    destCellOriginalPiece.move(destRow, destCol);
                }
                continue;
            }

            move(currentRow,currentCol);

            if(destCellOriginalPiece !=null){
                destCellOriginalPiece.move(destRow,destCol);
            }

            if (amountOfCheckingPieces > 0) {
                continue;
            }

            moveList.add(board.getCell(destRow, destCol));

        }
        return moveList;
    }


    /**
     * Checks if a given move is valid.
     *
     * @param newRow the row of the move being checked
     * @param newCol the col of the move being checked
     * @return true if the move is valid, else false
     */
    public boolean isValidMove(int newRow, int newCol) {

        Cell newCell = board.getCell(newRow, newCol);

        System.out.println(newCell);

        if(this instanceof General || this instanceof Advisor){
            if(!newCell.getIsPalace()){
                return false;
            }
        }


        if (getValidMoves().contains(newCell)) {
            return true;
        }
        return false;
    }


    /**
     * gets the moves that are theoretically available while disregarding leaving your general unguarded
     *
     * @return
     */
    public abstract ArrayList<Cell> getMoveList();

    public void move(int newRow, int newCol) {
        // A method that moves a piece to a new position if valid
        board.updateCell(this.row, this.col, null);


        Piece pieceOnDestCell = board.getCell(newRow, newCol).getPieceOnCell();
        board.getPieceList().remove(pieceOnDestCell);


        board.updateCell(newRow, newCol, this);



        this.row = newRow;
        this.col = newCol;
    }
    public String getColor() {
        return color;
    }
    public int getRow() {
        return row;
    }
    public int getCol() {
        return col;
    }


    @Override
    public abstract Piece clone() ;


    public boolean moveIfValid(int newRow, int newCol){


        //if it's red's turn

        if((match.isRedTurn() && this.color.equals("red")) || (!match.isRedTurn() && this.color.equals("black"))){

            if (isValidMove(newRow, newCol)) {


                move(newRow,newCol);


                saveMoveToHistory(newRow, newCol);


                return true; // Move successful
//        ([former rank][former file])-[new rank][new file] Thus,
//        the most common opening in the game would be written as: cannon (32)–35 soldier (18)–37
//        this looks weird because the numbers dont have a line between them such as 3,2 - 3,5
            }

            else {
                return false; // Move invalid
            }

        }

        return false; // Move invalid


    }

    private void saveMoveToHistory(int newRow, int newCol) {
        int oldRow=this.getRow();
        int oldCol=this.getCol();


        this.row = newRow;
        this.col = newCol;

        board.updateCell(newRow, newCol, this);

        // a-i rows
        // 0-9 cols
        int oldColToTranslate = oldCol; // the number to convert
        String oldColLetter = String.format("%c", 'a' + oldColToTranslate); // convert the number to a letter

        int newColToTranslate = newCol; // the number to convert
        String newColLetter = String.format("%c", 'a' + newColToTranslate); // convert the number to a letter

        int i;
        if(this.color.equals("red")){
            i=0;
        }
        else{
            i=1;
        }

        String movePerformed = (oldColLetter + oldRow + newColLetter + newRow);

        board.setMovesPerformedThisTurn(movePerformed,i);
    }

    protected boolean checkValidityAndAddMove(int destRow, int destCol) {
        Cell currentCell = board.getCell(this.getRow(),this.getCol());
        Cell destCell = board.getCell(destRow, destCol);

        int currentRow = this.getRow();
        int currentCol = this.getCol();

        // gets placed back if checkmates arise.
        Piece destCellOriginalPiece = destCell.getPieceOnCell();

        if(destCellOriginalPiece != null && destCellOriginalPiece.getColor().equals(this.color)){
            return false;
        }

        move(destRow, destCol);

        int amountOfCheckingPieces;

        if(this.color.equals("red")){
            amountOfCheckingPieces = board.getPiecesCheckingRedGeneral().size();
        }
        else
        {
            amountOfCheckingPieces = board.getPiecesCheckingBlackGeneral().size();
        }

        // uses general.getcol()
        if(match.flyingGeneralCheck() == true){

            move(currentRow,currentCol);
            if(destCellOriginalPiece !=null) {
                destCellOriginalPiece.move(destRow, destCol);
            }
            return false;
        }

        move(currentRow,currentCol);

        if(destCellOriginalPiece !=null){
            destCellOriginalPiece.move(destRow, destCol);
        }

        if(amountOfCheckingPieces>0){
            return false;
        }

        return true;
    }


    public void setBoard(Board board) {
        this.board = board;
    }

}