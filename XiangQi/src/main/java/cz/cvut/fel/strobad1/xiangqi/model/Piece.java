package cz.cvut.fel.strobad1.xiangqi.model;


import java.util.ArrayList;
import java.util.logging.Logger;

public abstract class Piece implements Cloneable {

    protected final String color;
    //location of figure
    protected int row;
    protected int col;
    protected Board board;

    private Logger logger = Logger.getLogger(Piece.class.getName());

    public Piece(int row, int col, String color, Board board) {
        this.row = row;
        this.col = col;
        this.color = color;
        this.board = board;
        this.board.updateCell(row, col, this);




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

//        int[][] offsets = getOffsets();


        ArrayList<Cell> validMoveList = new ArrayList<Cell>();


        for (Cell move: getMoveList()) {

            int[] coords = board.findCellCoords(move);

            if(checkValidityAndAddMove(coords[0],coords[1])){

                validMoveList.add(move);
            }

        }



        return validMoveList;
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



    public abstract Piece clone(Board board) ;


    public boolean moveIfValid(int newRow, int newCol){

        if((board.isRedTurn() && this.color.equals("red")) || (!board.isRedTurn() && this.color.equals("black"))){

            if (isValidMove(newRow, newCol)) {
                saveMoveToHistory(newRow, newCol);
                move(newRow,newCol);

                board.setRedTurn(!board.isRedTurn());
                return true; // Move successful
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

        String movePerformed = (oldColLetter + oldRow + newColLetter + newRow);

        board.setMovePerformedThisTurn(movePerformed);
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
        if(board.flyingGeneralCheck() == true){

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
