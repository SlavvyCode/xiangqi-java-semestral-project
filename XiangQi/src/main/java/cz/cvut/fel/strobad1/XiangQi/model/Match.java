package cz.cvut.fel.strobad1.XiangQi.model;

import cz.cvut.fel.strobad1.XiangQi.model.Pieces.General;

import java.io.IOException;
import java.util.ArrayList;

public class Match {


    private String aiColor;
    private boolean playingAgainstAI = false;

    private static boolean redWins = false;
    private static boolean blackWins = false;
    private static boolean gameDraw= false;

    public static boolean isGameDraw() {
        return gameDraw;
    }

    private static Player redPlayer;
    private static Player blackPlayer;
    private static int turnCounter;
    private static boolean redTurn = false;
    Board viewingBoard;
    private Board gameBoard;
    private ArrayList<Board> moveHistory;
    private boolean viewingPast = false;

    private boolean aiOpponent;

    public SaveManager getSaveManager() {
        return saveManager;
    }

    private SaveManager saveManager;
    ChessClock gameClock;



    public ChessClock getGameClock() {
        return gameClock;
    }

    public static void setGameDraw(boolean gameDraw) {
        Match.gameDraw = gameDraw;
    }

    /**
     * Starts the instance of the game
     */
    public void startGame() throws IOException, CloneNotSupportedException {
        turnCounter = -1;
        // possibly add name selection, low importance

        saveManager = new SaveManager();
        //set up players
        redPlayer = new Player("red");
        blackPlayer = new Player("black");

        //set up move history
        moveHistory = new ArrayList<Board>();

        //set up board
        gameBoard = new Board(this);
        gameBoard.setUpPieces();



        if(aiOpponent==true){


            //TODO
        }




//        viewingBoard = gameBoard;



        //set up clock
        gameClock = new ChessClock();
        gameClock.pauseCountdown();

        //start the game

        //TODO move start
        startTurn();
    }


    public static boolean isRedTurn() {
        return redTurn;
    }

    /**
     * Turn start operation that happens every turn.
     */

    public void startTurn() throws IOException, CloneNotSupportedException {

        gameClock.resumeCountdown();
        // saves last turn's board (starting at 0 pieces moved)


        if(turnCounter!=0){
            gameClock.switchTurn();
        }


        turnCounter++;






        //if it was black's turn, it's red's turn now.
        if (!redTurn) {
            redTurn = true;
            //Turn number only changes on red's turn
            turnCounter++;
            //it's red's turn;



            //TODO RESOLVE ERROR


//
//            Board newBoard = gameBoard.clone();
//
//
//            moveHistory.add(newBoard);
//
            viewingBoard = gameBoard;

        } else {
            //it's black's turn
            redTurn = false;
        }



        //check if you have any legal moves ELSE game ends via stalemate
        checkMateCheck();



        // the person whose turn it is when generals are revealed wins.
        if(flyingGeneralCheck()==true){
            if(redTurn){
                redWins=true;
            }
            else{
                blackWins=true;
            }
        }



        repetitionCheck();
        lackOfPiecesCheck();
        fiftyMoveNoCaptureCheck();

    }

    public Board getViewingBoard() {
        return viewingBoard;
    }



    private void fiftyMoveNoCaptureCheck() {

        if(moveHistory.size()<50){
            return;
        }
        for (int i = 0; i < 50; i++) {

            ArrayList<Piece> tempPieceList = moveHistory.get(moveHistory.size()-i-1).getPieceList();

            if(gameBoard.getPieceList()!=tempPieceList){
                return;
            }
            if(i>=50){
                System.out.println("DRAW!!!");

            }

        }
    }

    private boolean lackOfPiecesCheck() {
        if(gameBoard.getPieceList().size()==2){
            System.out.println("DRAW!!!");
            return true;
        }
        return false;
    }

    public ArrayList<Board> getMoveHistory() {
        return moveHistory;
    }

    private boolean repetitionCheck() {

        // If you check your opponent three times in a row in a way that repeats their positions, you lose.

        if(moveHistory.size()<=2){
            return false;
        }

        if(gameBoard.getPiecesCheckingRedGeneral().size()==0 ||gameBoard.getPiecesCheckingBlackGeneral().size()==0){
            return false;
        }

        Board lastTurnBoard = moveHistory.get(moveHistory.size()-2);

        Board twoTurnsAgoBoard = moveHistory.get(moveHistory.size()-3);


        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 9; j++) {
                //if the pieces aren't in the same place == have not been repeated
                if(lastTurnBoard.getCell(i,j).getPieceOnCell()!=gameBoard.getCell(i,j).getPieceOnCell()){
                    return false;
                }
            }
        }
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 9; j++) {
                //if the pieces aren't in the same place == have not been repeated
                if(twoTurnsAgoBoard.getCell(i,j).getPieceOnCell()!=gameBoard.getCell(i,j).getPieceOnCell()){
                    return false;
                }
            }
        }


        System.out.println("REPETITION CHECK! CHECKER LOSES!!");
        return true;





    }


    public static String getVictor(){
        if(redWins){
            return "red";
        }
        if(blackWins){
            return  "black";
        }
        return null;
    }

    public static void setRedWins(boolean redWins) {
        Match.redWins = redWins;
    }

    public static void setBlackWins(boolean blackWins) {
        Match.blackWins = blackWins;
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
        for (Piece piece : gameBoard.getPieceList()) {
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
            if (piece instanceof General && piece.getColor().equals("black")) {
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

        gameOver();

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

        int viewingBoardIndex = moveHistory.indexOf(viewingBoard);

        if(viewingBoardIndex== moveHistory.size()-1){
            startOrStopViewingPast();
        }

        if (viewingBoardIndex > 0) {
            viewingBoardIndex -= 1;
            viewingBoard = moveHistory.get(viewingBoardIndex);
        } else {
            System.out.println("already on first turn");
        }
        return viewingBoard;
    }

    public Board getViewingBoard1TurnAhead() {

        int ViewingBoardIndex = moveHistory.indexOf(viewingBoard);

        //there's
        if (ViewingBoardIndex < moveHistory.size()-1) {
            ViewingBoardIndex += 1;
            viewingBoard = moveHistory.get(ViewingBoardIndex);
        } else {
            System.out.println("already on current turn");
            if(viewingPast){
                startOrStopViewingPast();

            }
        }

        return viewingBoard;
    }



    public void gameOver(){
        gameClock.pauseCountdown();
    }

    public boolean getViewingPast() {
        return viewingPast;

    }


    public boolean isPlayingAgainstAI() {
        return playingAgainstAI;
    }


    public void setPlayingAgainstAI(boolean playingAgainstAI) {
        this.playingAgainstAI = playingAgainstAI;
    }

    public void setAiColor(String aiColor) {
        this.aiColor = aiColor;
    }

}