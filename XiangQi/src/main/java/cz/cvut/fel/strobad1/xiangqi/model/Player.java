package cz.cvut.fel.strobad1.xiangqi.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Logger;

public class Player {


    public Player(Match match, boolean isRed, boolean isAI) {
        this.match = match;
        this.isRed = isRed;
        this.isAI = isAI;
    }

    private Match match;
    private boolean isRed;

    public boolean isRed() {
        return isRed;
    }

    public void setRed(boolean red) {
        isRed = red;
    }

    public boolean isAI() {
        return isAI;
    }

    public void setAI(boolean AI) {
        isAI = AI;
    }

    private boolean isAI;


//    CAN BE IMPLEMENTED TO EXPAND THE PROJECT
//    enum aiDifficulty{
//        Low,
//        Medium,
//        High
//    };

    public void randomAIMove() throws IOException, CloneNotSupportedException {

        Logger logger = Logger.getLogger(Player.class.getName());

        Random random = new Random();

        Piece randomPiece;



        ArrayList<Piece> playingSidePieces;

        if (match.getGameBoard().isRedTurn()) {
            playingSidePieces = match.getRedPieces();
        } else {
            playingSidePieces = match.getBlackPieces();
        }

        while (true) {
            int pieceIndexToTry = random.nextInt(playingSidePieces.size());

            randomPiece = playingSidePieces.get(pieceIndexToTry);

            if (randomPiece.getValidMoves().size() == 0) {
                continue;
            }
            break;
        }


        ArrayList<Cell> validMoves = randomPiece.getValidMoves();
        int randomValidMoveIndex = random.nextInt(validMoves.size());

        Cell randomValidCell = validMoves.get(randomValidMoveIndex);

        Cell[][] cellList = match.getGameBoard().getCellList();


        int[] cellCoords = match.getGameBoard().findCellCoords(randomValidCell);


        if (randomPiece.moveIfValid(cellCoords[0], cellCoords[1])) {
            logger.info("AI is moving " + randomPiece.color + " "
                    + randomPiece.getClass().getSimpleName() +
                    " to " + cellCoords[0] + " " + cellCoords[1] + ".");
            match.startTurn();
            return;
        }


        logger.severe("AI couldn't find its move!");
        throw new NullPointerException();
    }

}
