package cz.cvut.fel.strobad1.xiangqi.model;

import cz.cvut.fel.strobad1.xiangqi.model.pieces.General;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Logger;

public class Match {



    private Player redPlayer;

    public Player getRedPlayer() {
        return redPlayer;
    }

    public void setRedPlayer(Player redPlayer) {
        this.redPlayer = redPlayer;
    }

    public Player getBlackPlayer() {
        return blackPlayer;
    }

    public void setBlackPlayer(Player blackPlayer) {
        this.blackPlayer = blackPlayer;
    }

    private Player blackPlayer;

    int viewingBoardIndex;
    Logger logger = SingletonLogger.getInstance().getLogger();
    Board viewingBoard;
    ChessClock gameClock;
    private colorEnum aiColor = colorEnum.BLACK;
    private boolean playingAgainstAI = false;
    private boolean redWins = false;
    private boolean blackWins = false;
    private boolean gameDraw = false;
    private Board gameBoard;
    private ArrayList<Board> moveHistory;
    private boolean viewingPast = false;
    private boolean aiOpponent;
    private SaveManager saveManager;

    public colorEnum getAiColor() {
        return aiColor;
    }

    public void setAiColor(colorEnum aiColor) {
        this.aiColor = aiColor;
    }

    public boolean isGameDraw() {
        return gameDraw;
    }

    public void setGameDraw(boolean gameDraw) {
        this.gameDraw = gameDraw;
    }

    public SaveManager getSaveManager() {
        return saveManager;
    }

    public ChessClock getGameClock() {
        return gameClock;
    }

    public void setGameClock(ChessClock importedClock) {
        this.gameClock = importedClock;
    }

    /**
     * Starts the instance of the game
     */
    public void startGame() throws IOException, CloneNotSupportedException {
        // possibly add name selection, low importance

        saveManager = new SaveManager();


//        gameClock = new ChessClock();


        //set up move history
        moveHistory = new ArrayList<Board>();

        //set up board
        gameBoard = new Board();
        gameBoard.setUpPieces();
        gameBoard.setRedTurn(true);


        Board newBoard = gameBoard.clone();
        moveHistory.add(newBoard);
        viewingBoard = newBoard;
        gameBoard = newBoard;

    }

    /**
     * Turn start operation that happens every turn.
     * Consists of checking for checkmates and other niche rules of the game,
     * switching the countdown timer to the other player
     * and copying the board and adding it to the move history.
     */
    public void startTurn() throws IOException, CloneNotSupportedException {


        Board newBoard = gameBoard.clone();
        moveHistory.add(newBoard);
        viewingBoard = newBoard;
        gameBoard = newBoard;


        gameClock.resumeCountdown();
        // saves last turn's board (starting at 0 pieces moved)


        gameClock.switchTurn();


        //check if you have any legal moves ELSE game ends via stalemate
        checkMateCheck();


        // the person whose turn it is when generals are revealed wins.
        if (gameBoard.flyingGeneralCheck() == true) {
            if (gameBoard.isRedTurn()) {
                redWins = true;
            } else {
                blackWins = true;
            }
            gameOver();
        }


        if (repetitionCheck()) {

            if (gameBoard.isRedTurn()) {
                redWins = true;
            }
            if (!gameBoard.isRedTurn()) {
                blackWins = true;
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

        if (moveHistory.size() < 50) {
            return;
        }
        for (int i = 0; i < 50; i++) {

            ArrayList<Piece> tempPieceList = moveHistory.get(moveHistory.size() - i - 1).getPieceList();

            if (gameBoard.getPieceList() != tempPieceList) {
                return;
            }
            if (i >= 50) {

                drawGame();

            }

        }
    }

    private boolean lackOfPiecesCheck() {
        if (gameBoard.getPieceList().size() == 2) {

            drawGame();
            return true;
        }
        return false;
    }

    public void drawGame() {
        logger.info("Draw!");
        gameOver();
    }

    public ArrayList<Board> getMoveHistory() {
        return moveHistory;
    }

    private boolean repetitionCheck() {

        // If you check your opponent three times in a row in a way that repeats their positions, you lose.

        if (moveHistory.size() <= 2) {
            return false;
        }

        if (gameBoard.getPiecesCheckingRedGeneral().size() == 0 || gameBoard.getPiecesCheckingBlackGeneral().size() == 0) {
            return false;
        }

        Board lastTurnBoard = moveHistory.get(moveHistory.size() - 2);

        Board twoTurnsAgoBoard = moveHistory.get(moveHistory.size() - 3);


        for (int i = 0; i < gameBoard.numberOfRows; i++) {
            for (int j = 0; j < gameBoard.numberOfCols; j++) {
                //if the pieces aren't in the same place == have not been repeated
                if (lastTurnBoard.getCell(i, j).getPieceOnCell() != gameBoard.getCell(i, j).getPieceOnCell()) {
                    return false;
                }
            }
        }
        for (int i = 0; i < gameBoard.numberOfRows; i++) {
            for (int j = 0; j < gameBoard.numberOfCols; j++) {
                //if the pieces aren't in the same place == have not been repeated
                if (twoTurnsAgoBoard.getCell(i, j).getPieceOnCell() != gameBoard.getCell(i, j).getPieceOnCell()) {
                    return false;
                }
            }
        }


        logger.info("REPETITION CHECK! CHECKER LOSES!!");
        return true;


    }

    public colorEnum getVictor() {
        if (redWins) {
            return colorEnum.RED;
        }
        if (blackWins) {
            return colorEnum.BLACK;
        }
        return null;
    }

    public void setRedWins(boolean redWins) {
        this.redWins = redWins;
    }

    public void setBlackWins(boolean blackWins) {
        this.blackWins = blackWins;
    }

    public Board getGameBoard() {
        return gameBoard;
    }

    public void setGameBoard(Board gameBoard) {
        this.gameBoard = gameBoard;
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
                if (allyPiece.color.equals(colorEnum.RED)) {
                    //Get Valid moves checks for being able to move next turn.
                    totalPossibleMoves.addAll(allyPiece.getValidMoves());
                    if (totalPossibleMoves.size() > 0) {
                        return;
                    }
                }
            }
            blackWins = true;
        } else {
            checkingPieces = gameBoard.getPiecesCheckingBlackGeneral();

            //if no pieces checking or general can move
            if (checkingPieces.isEmpty() || blackGeneral.getValidMoves().size() > 0) {
                return;
            }

            ArrayList<Cell> totalPossibleMoves = new ArrayList<>();
            for (Piece allyPiece : gameBoard.getPieceList()) {
                if (allyPiece.color.equals(colorEnum.RED)) {
                    //Get Valid moves checks for being able to move next turn.
                    totalPossibleMoves.addAll(allyPiece.getValidMoves());
                    if (totalPossibleMoves.size() > 0) {
                        return;
                    }
                }
            }

            redWins = true;

        }


        if (redWins) {

            logger.info("Red wins.");
        } else {

            logger.info("Black wins.");
        }


        gameOver();

    }


    /**
     * changes the value of the boolean viewingPast to false and prints a logging message
     */
    public void stopViewingPast() {
        viewingPast = false;
        logger.info("viewing present now");
    }

    /**
     * changes the value of the boolean viewingPast to true and prints a logging message
     *
     */
    public void startViewingPast() {
        viewingPast = true;
        logger.info("viewing past now");
    }

    public int getViewingBoardIndex() {
        return viewingBoardIndex;
    }


    /**
     * returns a copy of the board that was active last turn.
     * Used for viewing past states of the game.
     * If current viewing board at first turn already, only prints a logging message.
     * @return copy of the board from one turn ago.
     */
    public Board getViewingBoard1TurnBack() {

        viewingBoardIndex = moveHistory.indexOf(viewingBoard);

        if (viewingBoardIndex == moveHistory.size() - 1 && !viewingPast) {
            startViewingPast();
        }
        if (viewingBoardIndex == 0) {
            logger.info("already on first turn");
            return viewingBoard;
        }

        viewingBoardIndex--;
        viewingBoard = moveHistory.get(viewingBoardIndex);

        return viewingBoard;

    }


    /**
     * returns a copy of the board that was active last turn.
     * Used for viewing past states of the game.
     * If current viewing board at last turn already, only prints a logging message.
     * @return copy of the board from one turn after the one we're on.
     */
    public Board getViewingBoard1TurnAhead() {

        viewingBoardIndex = moveHistory.indexOf(viewingBoard);

        if (viewingBoardIndex == moveHistory.size() - 1) {
            logger.info("already on last turn");
            return viewingBoard;
        }

        if (viewingBoardIndex == moveHistory.size() - 2) {

            stopViewingPast();
        }
        viewingBoardIndex++;
        viewingBoard = moveHistory.get(viewingBoardIndex);

        return viewingBoard;
    }


    /**
     * makes the game unplayable by pausing the clock, logs a message.
     */
    public void gameOver() {

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

    /**
     * Gets all the red pieces on the board.
     * @return red pieces
     */
    public ArrayList<Piece> getRedPieces() {
        ArrayList<Piece> redPieces = new ArrayList<Piece>();
        ArrayList<Piece> blackPieces = new ArrayList<Piece>();

        for (Piece pieceInList : gameBoard.getPieceList()) {

            if (pieceInList.color.equals(colorEnum.RED)) {
                redPieces.add(pieceInList);
            } else {
                blackPieces.add(pieceInList);
            }

        }
        return redPieces;
    }

    /**
     * Gets all the black pieces on the board.
     * @return black pieces
     */
    public ArrayList<Piece> getBlackPieces() {
        ArrayList<Piece> redPieces = new ArrayList<Piece>();
        ArrayList<Piece> blackPieces = new ArrayList<Piece>();

        for (Piece pieceInList : gameBoard.getPieceList()) {

            if (pieceInList.color.equals(colorEnum.RED)) {
                redPieces.add(pieceInList);
            } else {
                blackPieces.add(pieceInList);
            }

        }
        return blackPieces;
    }

}