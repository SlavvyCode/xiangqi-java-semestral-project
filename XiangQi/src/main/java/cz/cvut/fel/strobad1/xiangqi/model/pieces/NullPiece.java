package cz.cvut.fel.strobad1.xiangqi.model.pieces;

import cz.cvut.fel.strobad1.xiangqi.model.Board;
import cz.cvut.fel.strobad1.xiangqi.model.Cell;
import cz.cvut.fel.strobad1.xiangqi.model.Piece;
import cz.cvut.fel.strobad1.xiangqi.model.colorEnum;

import java.util.ArrayList;

public class NullPiece extends Piece {
    public NullPiece(int row, int col, colorEnum colorEnum, Board board) {
        super(row, col, colorEnum, board);
    }
    @Override
    public int[][] getOffsets() {
        return new int[0][];
    }

    @Override
    public ArrayList<Cell> getMoveList() {
        return new ArrayList<>();
    }

    @Override
    public Piece clone(Board board) {
        return new NullPiece(this.row,this.col,this.color,this.board);
    }
}
