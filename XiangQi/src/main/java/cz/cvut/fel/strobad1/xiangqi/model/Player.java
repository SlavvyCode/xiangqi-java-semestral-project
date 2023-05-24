package cz.cvut.fel.strobad1.xiangqi.model;

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


}
