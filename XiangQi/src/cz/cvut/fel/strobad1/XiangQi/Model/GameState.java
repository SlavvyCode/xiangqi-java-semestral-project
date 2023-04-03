package cz.cvut.fel.strobad1.XiangQi.Model;

import cz.cvut.fel.strobad1.XiangQi.Model.Board.Board;
import cz.cvut.fel.strobad1.XiangQi.Model.Board.Piece;
import cz.cvut.fel.strobad1.XiangQi.Model.Board.Pieces.General;

public class GameState {

    private Player redPlayer;
    private Player blackPlayer;
    private final boolean blackWins = false;
    private final boolean redWins = false;
    private Board gameBoard;
    private int turnCounter=-1;
    private boolean redTurn=false;

    public GameState() {

        startGame();

        newTurn();

    }

    private void newTurn() {

        startTurn();


        endTurnChecks();

    }

    private void endTurnChecks() {
// CHECK IF GENERAL WILL BE IN LOS WITH OTHER GENERAL OR GO INTO CHECK BY MAKING A BAD MOVE.
    }



    public void startGame() {
        turnCounter= -1;
        // possibly add name selection, low importance
        redPlayer = new Player("red");
        blackPlayer = new Player("black");

        gameBoard.resetBoard();




    }

    public void startTurn(){
        //if it was black's turn, it's red's turn now.
        turnCounter++;
        //it's red's turn;
        //it's black's turn
        redTurn= !redTurn;

        //check if you have any legal moves ELSE game ends via stalemate


    }

    private Piece getRedGeneral() {

        General redGeneral = null;

        return redGeneral;
    }

    private Piece getBlackGeneral() {

        General blackGeneral = null;

        return blackGeneral;
    }

    public Board getGameBoard() {
        return gameBoard;
    }

    //true if general in check
    private boolean checkIfGeneralInCheck() {



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