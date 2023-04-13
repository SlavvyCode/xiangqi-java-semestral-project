package cz.cvut.fel.strobad1.XiangQi.Model;

import cz.cvut.fel.strobad1.XiangQi.Model.Pieces.*;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class GameState {

    private static Player redPlayer;
    private static Player blackPlayer;

    private static boolean blackWins = false;
    private static final boolean redWins = false;

    private Board gameBoard;
    private ArrayList<Board> moveHistory;
    private static int turnCounter;

    private static boolean redTurn=false;


    /**
     * Constructor starts game
     *
     */
    public GameState() {
        startGame();
    }


    /**
     * Does all important checks before ending the turn to make sure that the move was legal
     *
     */
    private void endTurnChecks() {
// CHECK IF GENERAL WILL BE IN LOS WITH OTHER GENERAL OR GO INTO CHECK BY MAKING A BAD MOVE.**************
        ///TODO
    }


    /**
     * Starts the instance of the game
     *
     */
    public void startGame() {
        turnCounter= -1;
        // possibly add name selection, low importance
        redPlayer = new Player("red");
        blackPlayer = new Player("black");

        gameBoard.setUpPieces();

        while (!blackWins && !redWins){
            startTurn();
        }
    }


    /**
     * Turn start operation that happens every turn.
     */

    public void startTurn(){
        //if it was black's turn, it's red's turn now.
        turnCounter++;
        if(!redTurn){
            redTurn=true;
            //it's red's turn;
        }
        else
        {
            //it's black's turn
            redTurn=false;

        }

        //check if you have any legal moves ELSE game ends via stalemate


    }


    /**
     * Get red general by looping through the pieceList of the board else throw NullPointerException
     * @return red general
     */
    private Piece getRedGeneral() {

        General redGeneral = null;

        for (Piece piece : gameBoard.getPieceList()) {
            if (piece instanceof General && piece.getColor() == "red") {
                redGeneral = (General) piece;
            }
        }
        if(redGeneral==null){
            throw new NullPointerException();
        }
        return redGeneral;
    }

    /**
     * Get black general by looping through the pieceList of the board else throw NullPointerException
     * @return black general
     */
    private Piece getBlackGeneral() {

        General blackGeneral = null;

        for (Piece piece : gameBoard.getPieceList()) {
            if (piece instanceof General && piece.getColor() == "black") {
                blackGeneral = (General) piece;
            }
        }
        if(blackGeneral==null){
            throw new NullPointerException();
        }
        return blackGeneral;
    }

    public Board getGameBoard() {
        return gameBoard;
    }

    //true if general in check

    /**
     * loops through each of the pieces that can move to the black general's location and returns them as an arraylist
     * @return all pieces checking the black general
     */
    private ArrayList<Piece> getPiecesCheckingBlackGeneral() {

        ArrayList<Piece> checkingPieces = new ArrayList<>();
        Cell generalLocation = getGameBoard().getFirstCellWithPiece(getBlackGeneral());

        for (Piece enemyPiece: gameBoard.getPieceList()) {
            if (enemyPiece.getColor()=="red" && enemyPiece.getValidMoves().contains(generalLocation)){
                checkingPieces.add(enemyPiece);
            }
        }
        return checkingPieces;
    }

    /**
     * Loops through each of the pieces that can move to the red general's location and returns them as an arraylist.
     * @return all pieces checking the red general
     */
    private ArrayList<Piece> getPiecesCheckingRedGeneral() {

        ArrayList<Piece> checkingPieces = new ArrayList<>();
        Cell generalLocation = getGameBoard().getFirstCellWithPiece(getRedGeneral());

        for (Piece enemyPiece: gameBoard.getPieceList()) {
            if (enemyPiece.getColor()=="black" && enemyPiece.getValidMoves().contains(generalLocation)){
                checkingPieces.add(enemyPiece);
            }
        }
        return checkingPieces;
    }


    /**
     * Checks if the king is checked, then if the checked player can get rid of the checking pieces or block their path.
     * if yes, returns false.
     * else returns true, since the general is checkmated
     * @return true if player whose turn it is won
     */
    private boolean checkMateCheck() {
        General blackGeneral =(General) getRedGeneral();
        General redGeneral = (General)getBlackGeneral();
        ArrayList<Piece> checkingPieces;
        if (redTurn){

            checkingPieces = getPiecesCheckingRedGeneral();

            //if no pieces checking or general can move
            if(checkingPieces.isEmpty() || redGeneral.getValidMoves().size()>0){
                return false;
            }

            //TODO add a check for if player can block a piece's movement --> go make a new board and check things there

            // if defending side can take checking piece
            for (Piece checkingPiece : checkingPieces) {
                for (Piece defendingPiece: gameBoard.getPieceList()) {
                    //TODO DESTROY THIS MONSTROSITY
                    //a defending move can take checking piece
                    if(defendingPiece.getValidMoves().contains(gameBoard.getFirstCellWithPiece(checkingPiece))){
                        continue;
                    }
                    else{
                        blackWins=true;
                        return true;
                    }
                }
            }
        }
        else {
            getPiecesCheckingBlackGeneral();
        }


        return true;

    }

}