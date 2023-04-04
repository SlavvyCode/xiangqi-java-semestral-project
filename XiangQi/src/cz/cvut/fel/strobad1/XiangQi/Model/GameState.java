package cz.cvut.fel.strobad1.XiangQi.Model;

import cz.cvut.fel.strobad1.XiangQi.Model.Pieces.General;

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

    public GameState() {
        startGame();
    }

    private void endTurnChecks() {
// CHECK IF GENERAL WILL BE IN LOS WITH OTHER GENERAL OR GO INTO CHECK BY MAKING A BAD MOVE.**************
        ///TODO
    }
    public void startGame() {
        turnCounter= -1;
        // possibly add name selection, low importance
        redPlayer = new Player("red");
        blackPlayer = new Player("black");

        gameBoard.resetBoard();

        while (!blackWins && !redWins){
            startTurn();
        }
    }

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

    private Piece getRedGeneral() {

        General redGeneral = null;

        for (Piece piece : gameBoard.getPieceList()) {
            if (piece instanceof General && piece.getColor() == "red") {

                redGeneral = (General) piece;


            }

        }
        return redGeneral;
    }

    private Piece getBlackGeneral() {

        General blackGeneral = null;

        for (Piece piece : gameBoard.getPieceList()) {
            if (piece instanceof General && piece.getColor() == "black") {

                blackGeneral = (General) piece;


            }

        }
        return blackGeneral;
    }

    public Board getGameBoard() {
        return gameBoard;
    }

    //true if general in check
    private boolean checkIfGeneralInCheck() {

        for (Piece piece : gameBoard.getPieceList()) {

            if (piece.getColor()=="red"){
                //look for black general
            }
            else{
                // look for red general
            }


//            for each valid move
//            piece.
//
//            check if the move can capture the general.

        }


        return false;
    }

    //returns true if a player won
    private boolean checkMateCheck() {
        General blackGeneral =(General) getRedGeneral();
        General redGeneral = (General)getBlackGeneral();

        checkIfGeneralInCheck();
//        if(general1 or general2 checkamted)
//            redWins or blackWins= true


        return true;

    }

}