package cz.cvut.fel.strobad1.XiangQi.model;

import cz.cvut.fel.strobad1.XiangQi.model.Pieces.*;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
        // Create a board and a match
        Board board = new Board(match);
        Match match = new Match();

        // Create an Advisor piece at position (3, 3) with color 'red'
        Advisor advisor = new Advisor(3, 3, "red", board);

        // Get the valid moves for the Advisor piece
        ArrayList<Cell> validMoves = advisor.getValidMoves();

        // Assert the expected valid moves
        assertEquals(4, validMoves.size());
        assertTrue(validMoves.contains(board.getCell(2, 2)));
        assertTrue(validMoves.contains(board.getCell(2, 4)));
        assertTrue(validMoves.contains(board.getCell(4, 2)));
        assertTrue(validMoves.contains(board.getCell(4, 4)));
    }

    @Test
    public void testGeneralValidMoves() {
        // Create a General piece at position (1, 4) with color 'red'
        General general = new General(1, 4, "red", board);
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
        Soldier soldier = new Soldier(4, 6, "black", board);
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
        Chariot chariot = new Chariot(0, 0, "red", board);
        ArrayList<Cell> validMoves = chariot.getValidMoves();
        // Assert the expected valid moves for the Chariot piece
        assertEquals(2, validMoves.size());
        assertTrue(validMoves.contains(board.getCell(1,0)));
        assertTrue(validMoves.contains(board.getCell(2,0)));
    }

    @Test
    public void testHorseValidMoves() {
        // Create a Horse piece at position (5, 5) with color 'black'
        Horse horse = new Horse(5, 5, "black", board);
        ArrayList<Cell> validMoves = horse.getValidMoves();
        // Assert the expected valid moves for the Horse piece
        assertEquals(8, validMoves.size());
        assertTrue(validMoves.contains(board.getCell(3, 4)));
        assertTrue(validMoves.contains(board.getCell(3, 6)));
    }

    @Test
    public void testElephantValidMoves() {
        // Create an Elephant piece at position (2, 2) with color 'red'

        Elephant elephant = new Elephant(2, 2, "red", board);
        ArrayList<Cell> validMoves = elephant.getValidMoves();
        // Assert the expected valid moves for the Elephant piece
        assertEquals(2, validMoves.size());
        assertTrue(validMoves.contains(board.getCell(4, 4)));
        assertTrue(validMoves.contains(board.getCell(4, 0)));
    }

    @Test
    public void testCannonValidMoves() {
        // Create a Cannon piece at position (2, 7) with color 'black'
        Cannon cannon = new Cannon(2, 7, "black", board);
        ArrayList<Cell> validMoves = cannon.getValidMoves();
        // Assert the expected valid moves for the Cannon piece
        assertEquals(7, validMoves.size());
        assertTrue(validMoves.contains(board.getCell(2, 6)));
        assertTrue(validMoves.contains(board.getCell(2, 5)));
    }



}
