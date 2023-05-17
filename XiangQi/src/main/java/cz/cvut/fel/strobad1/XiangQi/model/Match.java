package cz.cvut.fel.strobad1.XiangQi.model;

import cz.cvut.fel.strobad1.XiangQi.model.Pieces.General;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Logger;

public class Match {


    public String getAiColor() {
        return aiColor;
    }

    private String aiColor = "black";
    private boolean playingAgainstAI = false;

    private static boolean redWins = false;
    private static boolean blackWins = false;
    private static boolean gameDraw= false;

    int viewingBoardIndex;

    Logger logger = Logger.getLogger(Match.class.getName());


    public static boolean isGameDraw() {
        return gameDraw;
    }

    private static Player redPlayer;
    private static Player blackPlayer;
    private static int turnCounter;
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
        gameBoard = new Board();
        gameBoard.setUpPieces();


        Board newBoard = gameBoard.clone();
        moveHistory.add(newBoard);
        viewingBoard = newBoard;
        gameBoard=newBoard;
    }


    public void setGameBoard(Board gameBoard) {
        this.gameBoard = gameBoard;
    }


    /**
     * Turn start operation that happens every turn.
     */

    public void startTurn() throws IOException, CloneNotSupportedException {




        //if it was black's turn, it's red's turn now.
        if (!gameBoard.isRedTurn()) {
            //Turn number only changes on red's turn
            turnCounter++;
            //it's red's turn;

        }

        Board newBoard = gameBoard.clone();
        moveHistory.add(newBoard);
        viewingBoard = newBoard;
        gameBoard=newBoard;


        gameClock.resumeCountdown();
        // saves last turn's board (starting at 0 pieces moved)


        if(turnCounter!=0){
            gameClock.switchTurn();
        }


        //check if you have any legal moves ELSE game ends via stalemate
        checkMateCheck();



        // the person whose turn it is when generals are revealed wins.
        if(gameBoard.flyingGeneralCheck()==true){
            if(gameBoard.isRedTurn()){
                redWins=true;
            }
            else{
                blackWins=true;
            }
            gameOver();
        }



        if(repetitionCheck()){

            if(gameBoard.isRedTurn()){
                redWins=true;
            }
            if(!gameBoard.isRedTurn()){
                blackWins=true;
            }
            gameOver();

        }




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

                drawGame();

            }

        }
    }

    private boolean lackOfPiecesCheck() {
        if(gameBoard.getPieceList().size()==2){

            drawGame();
            return true;
        }
        return false;
    }


    public void drawGame(){
        logger.info("Draw!");
        gameOver();
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


        logger.info("REPETITION CHECK! CHECKER LOSES!!");
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





    public Board getGameBoard() {
        return gameBoard;
    }







    public void checkMateCheck() {
        General blackGeneral = (General) gameBoard.getBlackGeneral();
        General redGeneral = (General) gameBoard.getRedGeneral();

        ArrayList<Piece> checkingPieces;


        //check at the start of the turn of the player being attacked
        if (gameBoard.isRedTurn()) {

            checkingPieces = gameBoard.getPiecesCheckingRedGeneral();

            //if no pieces checking or general can move
            if (checkingPieces.isEmpty() || redGeneral.getValidMoves().size() > 0) {
                return;
            }

            ArrayList<Cell> totalPossibleMoves = new ArrayList<>();
            for (Piece allyPiece : gameBoard.getPieceList()) {
                if (allyPiece.color.equals("red")) {
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

            ArrayList<Cell> totalPossibleMoves = new ArrayList<>();
            for (Piece allyPiece : gameBoard.getPieceList()) {
                if (allyPiece.color.equals("red")) {
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

            logger.info("Red wins.");
            System.out.println( "RED PLAYER WINS!!!");
        }
        else{

            logger.info("Black wins.");
            System.out.println( "BLACK PLAYER WINS!!!");
        }


        gameOver();

    }

    public void stopViewingPast() {
        viewingPast=false;
        logger.info("viewing present now");
    }
    public void startViewingPast() {
        viewingPast = true;
        logger.info("viewing past now");
    }

    public int getViewingBoardIndex() {
        return viewingBoardIndex;
    }

    public Board getViewingBoard1TurnBack() {

        viewingBoardIndex = moveHistory.indexOf(viewingBoard);

        if(viewingBoardIndex== moveHistory.size()-1 && !viewingPast){
            startViewingPast();
        }
        if(viewingBoardIndex ==0){
            logger.info("already on first turn");
            return viewingBoard;
        }

        viewingBoardIndex --;
        viewingBoard = moveHistory.get(viewingBoardIndex);

        return viewingBoard;

    }

    public Board getViewingBoard1TurnAhead() {

        viewingBoardIndex = moveHistory.indexOf(viewingBoard);

        if(viewingBoardIndex == moveHistory.size()-1){
            logger.info("already on last turn");
            return viewingBoard;
        }

        if(viewingBoardIndex == moveHistory.size()-2) {

            stopViewingPast();
        }
        viewingBoardIndex ++;
        viewingBoard = moveHistory.get(viewingBoardIndex);

        return viewingBoard;
    }



    public void gameOver(){

        logger.fine("Game over.");
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


    public void randomAIMove() throws IOException, CloneNotSupportedException {

        if((!gameBoard.isRedTurn() && aiColor.equals("red"))||
                gameBoard.isRedTurn() && aiColor.equals("black"))
        {
            return;
        }
        Random random = new Random();


        ArrayList<Piece> pieceList = gameBoard.getPieceList();
        Piece randomPiece;


        ArrayList<Piece> redPieces= new ArrayList<>();
        ArrayList<Piece> blackPieces= new ArrayList<>();

        for (Piece pieceInList: pieceList) {

            if(pieceInList.color.equals("red")){
                redPieces.add(pieceInList);
            }
            else{
                blackPieces.add(pieceInList);
            }

        }


        ArrayList<Piece>playingSidePieces;

        if(gameBoard.isRedTurn()){
            playingSidePieces=redPieces;
        }else {
            playingSidePieces=blackPieces;
        }

        while (true){

            int pieceIndexToTry = random.nextInt(playingSidePieces.size());

            randomPiece = playingSidePieces.get(pieceIndexToTry);

            if(randomPiece.getValidMoves().size()==0){
                continue;
            }
            break;
        }


        ArrayList<Cell> validMoves = randomPiece.getValidMoves();
        int randomValidMoveIndex = random.nextInt(validMoves.size());

        Cell randomValidCell = validMoves.get(randomValidMoveIndex);

        Cell[][] cellList = gameBoard.getCellList();



        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 9; j++) {



                if(randomValidCell==(cellList[i][j])){

                    if(randomPiece.moveIfValid(i, j)){
                        logger.info("AI is moving "  + randomPiece.color + " "+ randomPiece.getClass().getSimpleName() + " to " + i + " " + j +".");
                        startTurn();
                        return;
                    }


                }



            }
        }

        logger.severe("AI couldn't find its move!");
        throw new NullPointerException();
    }

    public void setGameClock(ChessClock importedClock) {
        this.gameClock = importedClock;
    }
}