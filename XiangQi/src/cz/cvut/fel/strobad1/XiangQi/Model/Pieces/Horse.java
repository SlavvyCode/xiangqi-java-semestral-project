package cz.cvut.fel.strobad1.XiangQi.Model.Pieces;
//like a knight in chess except:
//      A_A       if O has target, cannot move.
//       O
//       X     knight=horse=X

import cz.cvut.fel.strobad1.XiangQi.Model.Board;
import cz.cvut.fel.strobad1.XiangQi.Model.GameState;
import cz.cvut.fel.strobad1.XiangQi.Model.Piece;

import static cz.cvut.fel.strobad1.XiangQi.Model.GameState.gameBoard;
import static cz.cvut.fel.strobad1.XiangQi.Model.GameState.getGameBoard;

public class Horse extends Piece {
    private int row;
    private int col;
    public Horse(int row, int col, String color) {
        super(row, col, color);
    }


    @Override
    public boolean isValidMove(int newRow, int newCol) {
        return false;
    }



    @Override
    public int[][] getValidMoves(){
                //return valid moves for horse
        int[][] offsets = {{-2, -1}, {-1, -2}, {1, -2}, {2, -1}, {2, 1}, {1, 2}, {-1, 2}, {-2, 1}};

        for(int[] offset : offsets) {

            //each offset is for example {1,2}
            int destRow = row + offset[0];
            int destCol = col + offset[1];

            // Check if destination is within the board
            if (destRow < 0 || destRow >= gameBoard.ROWS || destCol < 0 || destCol >= gameBoard.COLS) {
                continue;
            }


            //Takes CURRENT JUMP OFFSET and discards the decimal point leftover (0.5)
            int blockingRow = row + offset[0] / 2;
            int blockingCol = col + offset[1] / 2;


            for (cell:
                 GameState.getGameBoard()
                 ) {

            }
            Piece blockingPiece = gameBoard.getCell(blockingRow, blockingCol).getPieceOnCell();


            if (blockingPiece != null) {
                continue;
            }

            // add valid move to list
            possibleMoveList.add(destRow + "," + destCol);
        }
        return possibleMoveList
    }
}