package cz.cvut.fel.strobad1.XiangQi.Model.Pieces;
//like a knight in chess except:
//      A_A       if O has target, cannot move.
//       O
//       X     knight=horse=X

import cz.cvut.fel.strobad1.XiangQi.Model.Board;
import cz.cvut.fel.strobad1.XiangQi.Model.GameState;
import cz.cvut.fel.strobad1.XiangQi.Model.Main;
import cz.cvut.fel.strobad1.XiangQi.Model.Piece;

import java.util.ArrayList;

import static cz.cvut.fel.strobad1.XiangQi.Model.GameState.gameBoard;
import static cz.cvut.fel.strobad1.XiangQi.Model.GameState.getGameBoard;

public class Horse extends Piece {
    private int row;
    private int col;
    private final float value;
    Board gameBoard = Main.getMatch().getGameBoard();
    public Horse(int row, int col, String color) {

        super(row, col, color);
    }



    @Override
    public ArrayList<String> getValidMoves(){


        ArrayList<String> validMoveList = new ArrayList<String>();

        //return valid moves for horse
        int[][] offsets = {{-2, -1}, {-1, -2}, {1, -2}, {2, -1}, {2, 1}, {1, 2}, {-1, 2}, {-2, 1}};

        for(int[] offset : offsets) {

            //each offset is for example {1,2}
            int destRow = row + offset[0];
            int destCol = col + offset[1];

            // Check if destination is within the board
            if (destRow < 0 || destRow >= 10 || destCol < 0 || destCol >= 9) {
                continue;
            }


            //Takes CURRENT JUMP OFFSET and discards the decimal point leftover (0.5)
            int blockingRow = row + offset[0] / 2;
            int blockingCol = col + offset[1] / 2;




            Piece blockingPiece = gameBoard.getCell(blockingRow, blockingCol).getPieceOnCell();


            if (blockingPiece != null) {
                continue;
            }

            // add valid move to list



            validMoveList.add(destRow + "," + destCol);
        }
        return validMoveList;
    }
}