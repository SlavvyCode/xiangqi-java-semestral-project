package cz.cvut.fel.strobad1.xiangqi.model;

import cz.cvut.fel.strobad1.xiangqi.model.pieces.*;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class PieceTest {


    private Match match;
    private Board board;

    @Before
    public void setUp() throws IOException, CloneNotSupportedException {
        match = new Match();
        match.startGame();
        board = match.getGameBoard();

    }



    @Test
    public void testAdvisorValidMoves() {
        // Create an Advisor piece at position (3, 3) with color 'red'
        Advisor advisor = new Advisor(1, 4, colorEnum.RED, board);

        // Get the valid moves for the Advisor piece
        ArrayList<Cell> validMoves = advisor.getValidMoves();


//        System.out.println(board);


        // Assert the expected valid moves
        assertEquals(2, validMoves.size());

        assertTrue(validMoves.contains(board.getCell(2, 3)));
        assertTrue(validMoves.contains(board.getCell(2, 5)));
        assertFalse(validMoves.contains(board.getCell(0, 3)));
        assertFalse(validMoves.contains(board.getCell(0, 5)));
    }

    @Test
    public void testGeneralValidMoves() {
        // Create a General piece at position (1, 4) with color 'red'
        General general = new General(1, 4, colorEnum.RED, board);
        ArrayList<Cell> validMoves = general.getValidMoves();
        // Assert the expected valid moves for the General piece


        assertEquals(3, validMoves.size());
        assertTrue(validMoves.contains(board.getCell(1, 3)));
        assertTrue(validMoves.contains(board.getCell(1, 5)));
        assertTrue(validMoves.contains(board.getCell(2, 4)));
    }

    @Test
    public void testSoldierValidMoves() {
        // Create a Soldier piece at position (4, 6) with color 'black'
        Soldier soldier = new Soldier(4, 6, colorEnum.BLACK, board);
        ArrayList<Cell> validMoves = soldier.getValidMoves();

        // Assert the expected valid moves for the Soldier piece
        assertEquals(3, validMoves.size());
        assertTrue(validMoves.contains(board.getCell(3, 6)));
        assertTrue(validMoves.contains(board.getCell(4, 5)));
        assertTrue(validMoves.contains(board.getCell(4, 7)));
    }

    @Test
    public void testChariotValidMoves() {
        // Create a Chariot piece at position (0, 0) with color 'red'
        Chariot chariot = new Chariot(0, 0, colorEnum.RED, board);
        ArrayList<Cell> validMoves = chariot.getValidMoves();
        // Assert the expected valid moves for the Chariot piece
        assertEquals(2, validMoves.size());
        assertTrue(validMoves.contains(board.getCell(1,0)));
        assertTrue(validMoves.contains(board.getCell(2,0)));
    }

    @Test
    public void testHorseValidMoves() {
        // Create a Horse piece at position (5, 5) with color 'black'
        Horse horse = new Horse(5, 5, colorEnum.BLACK, board);
        ArrayList<Cell> validMoves = horse.getValidMoves();
        // Assert the expected valid moves for the Horse piece
        assertEquals(8, validMoves.size());
        assertTrue(validMoves.contains(board.getCell(3, 4)));
        assertTrue(validMoves.contains(board.getCell(3, 6)));
    }

    @Test
    public void testElephantValidMoves() {
        // Create an Elephant piece at position (2, 2) with color 'red'

        Elephant elephant = new Elephant(2, 2, colorEnum.RED, board);
        ArrayList<Cell> validMoves = elephant.getValidMoves();
        // Assert the expected valid moves for the Elephant piece
        assertEquals(2, validMoves.size());
        assertTrue(validMoves.contains(board.getCell(4, 4)));
        assertTrue(validMoves.contains(board.getCell(4, 0)));
    }

    @Test
    public void testCannonValidMoves() {
        // Create a Cannon piece at position (2, 7) with color 'black'
        System.out.println(board);
        Cannon cannon = new Cannon(2, 7, colorEnum.BLACK, board);
        ArrayList<Cell> validMoves = cannon.getValidMoves();
        System.out.println(board);
        // Assert the expected valid moves for the Cannon piece
        assertEquals(11, validMoves.size());
        assertTrue(validMoves.contains(board.getCell(2, 6)));
        assertTrue(validMoves.contains(board.getCell(2, 5)));
    }




    @Test
    public void testAdvisorLeavingPalace() {
        // Create a game board
        Board board = new Board();
        board.setUpPieces();

        // Set up the initial position with an Advisor in the palace
        Piece advisor = new Advisor(2, 3, colorEnum.RED, board);
        board.updateCell(2, 3, advisor);

        // Attempt to move the Advisor out of the palace
        boolean moveResult = advisor.moveIfValid(1, 3);

        // Verify that the move was not valid (Advisor cannot leave the palace)
        assertFalse(moveResult, "Advisor should not be able to leave the palace");

        // Verify that the Advisor is still in its original position
        assertTrue(board.getCell(2, 3).getPieceOnCell().equals(advisor), "Advisor should still be in the original position");

        // Attempt to move the Advisor out of the palace in different directions
        boolean moveResult1 = advisor.moveIfValid(3, 2);
        boolean moveResult2 = advisor.moveIfValid(1, 2);
        boolean moveResult3 = advisor.moveIfValid(3, 4);



        assertFalse(moveResult1, "Advisor should not be able to leave the palace ");
        assertFalse(moveResult2, "Advisor should not be able to leave the palace ");
        assertFalse(moveResult3, "Advisor should not be able to leave the palace ");

    }


}
