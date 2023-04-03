package cz.cvut.fel.strobad1.XiangQi.Model.Board;

import cz.cvut.fel.strobad1.XiangQi.Model.Piece;

import java.util.Map;
import java.util.LinkedHashMap;

public class Board {


//         10 +===X===X===X===X===X===X===X===+ BLACK SIDE
//            |   |   |   | \ | / |   |   |   |
//          9 +---+---+---+---X---+---+---+---+
//            |   |   |   | / | \ |   |   |   |
//          8 +---+---+---+---+---+---+---+---+
//            |   |   |   |   |   |   |   |   |
//          7 +---+---+---+---+---+---+---+---+
//            |   |   |   |   |   |   |   |   |
//          6 +---+---+---+---+---+---+---+---+
//            +===+===+===+===+===+===+===+===+ RIVER
//          5 +---+---+---+---+---+---+---+---+
//            |   |   |   |   |   |   |   |   |
//          4 +---+---+---+---+---+---+---+---+
//            |   |   |   |   |   |   |   |   |
//          3 +---+---+---+---+---+---+---+---+
//            |   |   |   | \ | / |   |   |   |
//          2 +---+---+---+---X---+---+---+---+
//            |   |   |   | / | \ |   |   |   |
//          1 +===X===X===X===X===X===X===X===+ RED SIDE
//            1   2   3   4   5   6   7   8   9
    //


    // RED STARTS


    ///****** SHOULD THE BOARD REMEMBER THE PIECES' PLACES OR SHOULD THE PIECES THEMSELVES

    private Map<String, Piece> pieceMap; // Map representing the current state of the game
    private Map<String, String> moveHistory; // LinkedHashMap representing the game history



    public Board() {
        pieceMap = new LinkedHashMap<>();
        moveHistory = new LinkedHashMap<>();

    }

    public Board(Map<String, Piece> pieceMap, Map<String, String> moveHistory) {
        this.pieceMap = pieceMap;
        this.moveHistory = moveHistory;

        //TODO: SETUP PIECES

    }

    public void resetBoard() {


        ///each side has:
        //5 pawns soldiers
        //2 cannons
        //2 horses
        //2 elephants
        //2 advisors
        //2 chariots rooks

    }


    public Board saveBoard(){
        return this;
    }

}



