package cz.cvut.fel.strobad1.XiangQi.Model;

import cz.cvut.fel.strobad1.XiangQi.Model.Pieces.*;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class GameState {

    private static Player redPlayer;
    private static Player blackPlayer;

    private static final boolean blackWins = false;
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
     *
     * starts game
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
     * turn start operation happens every turn
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
     * getter for the red general
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
     * getter for the black general
     * @return red general
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
     *  loops through each of the pieces that can move to the red general's location and returns them as an arraylist
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
     * it checks if the king is checked, checks if the player can get rid of them or block their path,
     * else returns true, since the general is checkmated
     * @return true if player whose turn it is won
     */
    private boolean checkMateCheck() {
        General blackGeneral =(General) getRedGeneral();
        General redGeneral = (General)getBlackGeneral();
        ArrayList checkingPieces;
        if (redTurn){

            checkingPieces = getPiecesCheckingRedGeneral();

            //if no pieces checking or general can move
            if(checkingPieces.isEmpty() || redGeneral.getValidMoves().size()>0){
                return false;
            }

            //// TODO IS THIS A BAD THING? INTELLIJ FORCES OBJECT
            // if defending side can take checking piece
            for (Piece checkingPiece : checkingPieces) {

                for (Piece defendingPiece: gameBoard.getPieceList()) {


                    //TODO DESTROY THIS MONSTROSITY
                    defendingPiece.getValidMoves().contains(gameBoard.getFirstCellWithPiece(checkingPiece))
                }
                if (piece.getValidMoves().contains())
            }

        }
        else {
            getPiecesCheckingBlackGeneral();
        }

        //        if(general1 or general2 checkamted)
//            redWins or blackWins= true


        return true;

    }

}