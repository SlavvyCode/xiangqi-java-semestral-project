package cz.cvut.fel.strobad1.XiangQi.Model;

import cz.cvut.fel.strobad1.XiangQi.Model.Pieces.General;

import java.util.ArrayList;

public class Match {

    private static boolean redWins = false;
    private static Player redPlayer;
    private static Player blackPlayer;
    private static boolean blackWins = false;
    private static int turnCounter;
    private static boolean redTurn = false;
    Board viewingBoard;
    private Board gameBoard;
    private ArrayList<Board> moveHistory;
    private boolean viewingPast = false;

    Clock gameClock;

    /**
     * Does all important checks before ending the turn to make sure that the move was legal
     */
    private void endTurnChecks() {
// CHECK IF GENERAL WILL BE IN LOS WITH OTHER GENERAL OR GO INTO CHECK BY MAKING A BAD MOVE.**************
        ///TODO end turn checks
    }


    /**
     * Starts the instance of the game
     */
    public void startGame() {
        turnCounter = -1;
        // possibly add name selection, low importance


        //set up players
        redPlayer = new Player("red");
        blackPlayer = new Player("black");

        //set up move history
        moveHistory = new ArrayList<Board>();

        //set up board
        gameBoard = new Board();
        gameBoard.setUpPieces();

        viewingBoard = gameBoard;



        //set up clock
        gameClock = new Clock();

        //start the game
        startTurn();
    }


    /**
     * Turn start operation that happens every turn.
     */

    public void startTurn() {

        // saves last turn's board (starting at 0 pieces moved)
        moveHistory.add(gameBoard);

        getBlackGeneral().getValidMoves();

        if(turnCounter!=0){
            gameClock.switchTurn();
        }


        turnCounter++;




        //if it was black's turn, it's red's turn now.
        if (!redTurn) {
            redTurn = true;
            //it's red's turn;
        } else {
            //it's black's turn
            redTurn = false;
        }

        //check if you have any legal moves ELSE game ends via stalemate
        checkMateCheck();


    }


    /**
     * Get red general by looping through the pieceList of the board else throw NullPointerException
     *
     * @return red general
     */
    public Piece getRedGeneral() {

        General redGeneral = null;

        for (Piece piece : gameBoard.getPieceList()) {
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

        for (Piece piece : gameBoard.getPieceList()) {
            if (piece instanceof General && piece.getColor() == "black") {
                blackGeneral = (General) piece;
            }
        }
        if (blackGeneral == null) {
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
     *
     * @return all pieces checking the black general
     */





    public void checkMateCheck() {
        General blackGeneral = (General) getBlackGeneral();
        General redGeneral = (General) getRedGeneral();

        ArrayList<Piece> checkingPieces;


        //check at the start of the turn of the player being attacked
        if (redTurn) {

            checkingPieces = gameBoard.getPiecesCheckingRedGeneral();

            //if no pieces checking or general can move
            if (checkingPieces.isEmpty() || redGeneral.getValidMoves().size() > 0) {
                return;
            }
            //TODO find out if this works
            ArrayList<Cell> totalPossibleMoves = new ArrayList<>();
            for (Piece allyPiece : gameBoard.getPieceList()) {
                if (allyPiece.color == "red") {
                    //Get Valid moves checks for being able to move next turn.
                    totalPossibleMoves.addAll(allyPiece.getValidMoves());
                    if (totalPossibleMoves.size() > 0) {
                        return;
                    }
                }
            }
            blackWins=true;



            //TODO DELETE IF IRRELEVANT, I think this code is useless but not sure
//
//            // if defending side can take checking piece
//            for (Piece checkingPiece : checkingPieces) {
//                for (Piece defendingPiece : gameBoard.getPieceList()) {
//
//                    //a defending move can take checking piece
//                    if (defendingPiece.getValidMoves().contains(gameBoard.getFirstCellWithPiece(checkingPiece))) {
//                        continue;
//                    } else {
//                        blackWins = true;
//                        return true;
//                    }
//                }
//            }

        } else {
            checkingPieces =gameBoard.getPiecesCheckingBlackGeneral();

            //if no pieces checking or general can move
            if (checkingPieces.isEmpty() || blackGeneral.getValidMoves().size() > 0) {
                return;
            }
            //TODO find out if this works
            ArrayList<Cell> totalPossibleMoves = new ArrayList<>();
            for (Piece allyPiece : gameBoard.getPieceList()) {
                if (allyPiece.color == "red") {
                    //Get Valid moves checks for being able to move next turn.
                    totalPossibleMoves.addAll(allyPiece.getValidMoves());
                    if (totalPossibleMoves.size() > 0) {
                        return;
                    }
                }
            }

            redWins=true;

        }


        if(redWins){
            System.out.println( "RED PLAYER WINS!!!");

        }
        else{
            System.out.println( "BLACK PLAYER WINS!!!");

        }

    }

    public void startOrStopViewingPast() {
        if (viewingPast) {
            viewingPast = false;
            viewingBoard = gameBoard;
            System.out.println("viewing present now");
            return;

        }
        viewingPast = true;
        System.out.println("viewing the past now");
    }

    public Board getViewingBoard1TurnBack() {

        int tempViewingBoardIndex = moveHistory.indexOf(viewingBoard);

        if (tempViewingBoardIndex > 0) {
            tempViewingBoardIndex -= 1;
            viewingBoard = moveHistory.get(tempViewingBoardIndex);
        } else {
            System.out.println("already on first turn");
        }
        return viewingBoard;
    }

    public Board getViewingBoard1TurnAhead() {

        int tempViewingBoardIndex = moveHistory.indexOf(viewingBoard);

        if (tempViewingBoardIndex < moveHistory.size() + 1) {
            tempViewingBoardIndex += 1;
            viewingBoard = moveHistory.get(tempViewingBoardIndex);
        } else {
            System.out.println("already on current turn");
        }

        return viewingBoard;
    }
}