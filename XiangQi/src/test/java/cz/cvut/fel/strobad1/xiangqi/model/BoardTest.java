package cz.cvut.fel.strobad1.xiangqi.model;

import cz.cvut.fel.strobad1.xiangqi.model.pieces.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;


public class BoardTest {

    private Match match;
    private Board board;

    @Before
    public void setUp() throws IOException, CloneNotSupportedException {
        match = new Match();
        match.startGame();
        board = match.getGameBoard();

    }




    @Test
    public void testConstructor(){

        assertNotNull(board.getCellList());



        // Verify expected number of pieces
        assertEquals(32, board.getPieceList().size());

        // Verify expected number of pieces

        for (int i = 0; i < Board.numberOfRows; i++) {

            assertEquals(9, board.getCellList()[i].length);
        }

    }



    @Test
    public void testInitialBoardSetup() {
        // Verify that the game board is set up correctly initially

        board.setUpPieces();

        // Verify that the pieces are placed on the correct cells on the board
        // For example, you can check the position of the red general
        Cell redGeneralCell = board.getFirstCellWithPiece(board.getRedGeneral());
        assertNotNull(redGeneralCell);

        assertEquals(colorEnum.RED, redGeneralCell.getSideColor());
        assertTrue(redGeneralCell.getIsPalace());



        ArrayList<Piece> pieceList = board.getPieceList();

        Advisor redAdvisor = null;
        Cannon redCannon = null;
        Elephant redElephant = null;
        General redGeneral = null;
        Horse redHorse = null;
        Soldier redSoldier = null;

        Advisor blackAdvisor = null;
        Cannon blackCannon = null;
        Elephant blackElephant = null;
        General blackGeneral = null;
        Horse blackHorse = null;
        Soldier blackSoldier = null;

        for (Piece piece: pieceList) {

            if (piece.getColor().equals(colorEnum.RED)){

                if (piece.getClass() == Advisor.class){

                    redAdvisor = (Advisor) piece;
                }
                if (piece.getClass() == Cannon.class){

                    redCannon = (Cannon) piece;
                }
                if (piece.getClass() == Elephant.class){

                    redElephant = (Elephant) piece;
                }
                if (piece.getClass() == General.class){

                    redGeneral = (General) piece;
                }
                if (piece.getClass() == Horse.class){

                    redHorse = (Horse) piece;
                }
                if (piece.getClass() == Soldier.class){

                    redSoldier = (Soldier) piece;
                }
            }
            else
            {

                if (piece.getClass() == Advisor.class) {
                    blackAdvisor = (Advisor) piece;
                } else if (piece.getClass() == Cannon.class) {
                    blackCannon = (Cannon) piece;
                } else if (piece.getClass() == Elephant.class) {
                    blackElephant = (Elephant) piece;
                } else if (piece.getClass() == General.class) {
                    blackGeneral = (General) piece;
                } else if (piece.getClass() == Horse.class) {
                    blackHorse = (Horse) piece;
                } else if (piece.getClass() == Soldier.class) {
                    blackSoldier = (Soldier) piece;
                }

            }


        }

        Assertions.assertNotNull(board.getFirstCellWithPiece(redAdvisor));
        Assertions.assertNotNull(board.getFirstCellWithPiece(redCannon));
        Assertions.assertNotNull(board.getFirstCellWithPiece(redElephant));
        Assertions.assertNotNull(board.getFirstCellWithPiece(redGeneral));
        Assertions.assertNotNull(board.getFirstCellWithPiece(redHorse));
        Assertions.assertNotNull(board.getFirstCellWithPiece(redSoldier));

        Assertions.assertNotNull(board.getFirstCellWithPiece(blackAdvisor));
        Assertions.assertNotNull(board.getFirstCellWithPiece(blackCannon));
        Assertions.assertNotNull(board.getFirstCellWithPiece(blackElephant));
        Assertions.assertNotNull(board.getFirstCellWithPiece(blackGeneral));
        Assertions.assertNotNull(board.getFirstCellWithPiece(blackHorse));
        Assertions.assertNotNull(board.getFirstCellWithPiece(blackSoldier));



    }



}


