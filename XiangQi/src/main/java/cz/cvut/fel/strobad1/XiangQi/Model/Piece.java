package cz.cvut.fel.strobad1.XiangQi.Model;


import java.lang.reflect.Array;
import java.util.ArrayList;

public abstract class Piece {

    protected final String color;
    //location of figure
    protected int row;
    protected int col;
    protected Board board;
    Match match = Main.getMatch();


    public Piece(int row, int col, String color, Board board) {
        this.row = row;
        this.col = col;
        this.color = color;
        this.board = board;
        board.updateCell(row, col, this);

    }

    public abstract int[][] getOffsets();

    /**
     * returns an arraylist of all the piece's valid moves.
     */
    public ArrayList<Cell> getValidMoves() {

        int[][] offsets = getOffsets();


        Board board = Main.getMatch().getGameBoard();

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

            Cell currentCell = board.getCell(this.getRow(), this.getCol());
            Cell destCell = board.getCell(destRow, destCol);

//            gets placed back if checkmates arise.
            Piece destCellOriginalPiece = destCell.getPieceOnCell();

            currentCell.setPieceOnCell(null);
            destCell.setPieceOnCell(this);


            int amountOfCheckingPieces;

            if (this.color == "red") {
                amountOfCheckingPieces = board.getPiecesCheckingRedGeneral().size();
            } else {
                amountOfCheckingPieces = board.getPiecesCheckingBlackGeneral().size();
            }




            if(match.flyingGeneralCheck() == true){
                continue;
            }



            currentCell.setPieceOnCell(this);
            destCell.setPieceOnCell(destCellOriginalPiece);



            if (amountOfCheckingPieces > 0) {
                continue;
            }





            moveList.add(board.getCell(destRow, destCol));

        }
        return moveList;
    }

    ;


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


    public boolean move(int newRow, int newCol) {
        // A method that moves a piece to a new position if valid
        if (isValidMove(newRow, newCol)) {
            board.updateCell(this.row, this.col, null);

            int oldRow=this.getRow();
            int oldCol=this.getCol();


            this.row = newRow;
            this.col = newCol;

            Cell destCell = board.getCell(newRow,newCol);


            //remove piece from play.
            if(destCell.getPieceOnCell()!= null){

                board.getPieceList().remove(destCell.getPieceOnCell());

            }

            board.updateCell(newRow, newCol, this);




            // a-i rows
            // 0-9 cols

            int oldColToTranslate = oldCol; // the number to convert
            char oldColLetter = (char) ('a' + oldColToTranslate); // convert the number to a letter

            int newColToTranslate = newCol; // the number to convert
            char newColLetter = (char) ('a' + newColToTranslate); // convert the number to a letter



            int i;
            if(this.color=="red"){
                i=0;
            }
            else{
                i=1;
            }

//            String movePerformed = (oldColLetter + oldRow + " " + newColLetter + newRow);


//            if(piece of same kind in same col)

            board.setMovesPerformedThisTurn(movePerformed,i);
            //example chariot going from a0 to a2 should be a0a2
            System.out.println(oldColLetter + oldRow + " " + newColLetter + newRow);


            return true; // Move successful
//        ([former rank][former file])-[new rank][new file] Thus,
//        the most common opening in the game would be written as: cannon (32)–35 soldier (18)–37
//        this looks weird because the numbers dont have a line between them such as 3,2 - 3,5
        }

        else

        {
            return false; // Move invalid
        }
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

    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }


}
