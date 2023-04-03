package cz.cvut.fel.strobad1.XiangQi.Model;

public abstract class Piece {
    private int value;
    private final String color;
    private int[][] possibleMoveList;
    private PieceType type;


    public Piece(PieceType type, String color) {
        this.type = type;
        this.color = color;
    }

    public enum PieceType {
        GENERAL(0),
        ADVISOR(2),
        ELEPHANT(4),
        HORSE(6),
        CHARIOT(8),
        CANNON(10),
        SOLDIER(12);

        private final int value;

        PieceType(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }
    public enum Offset {
        HORSE(new int[][]{{-2, -1}, {-2, 1}, {-1, -2}, {-1, 2}, {1, -2}, {1, 2}, {2, -1}, {2, 1}}),
        GENERAL(new int[][] {{+1,0}});
        // add other piece types here
        // ...

        private final int[][] offsets;

        Offset(int[][] offsets) {
            this.offsets = offsets;
        }

        public int[][] getOffsets() {
            return offsets;
        }

        }

    public abstract int[][] getPossibleMoveList();
    public abstract boolean getValidMoves(int newRow, int newCol);
    public boolean move(int newRow,int newCol) {

    // A method that moves a piece to a new position if valid
        if {

//        ([former rank][former file])-[new rank][new file] Thus,
//        the most common opening in the game would be written as: cannon (32)–35 soldier (18)–37
//                                                                make it 3,2 - 3,5 instead
            return true;
        }

        return false;
    }
    public String getColor() {
        return color;
    }
}
