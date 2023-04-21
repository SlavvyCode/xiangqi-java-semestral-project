package cz.cvut.fel.strobad1.XiangQi.Model;

public class Main {
    private static Match match;

    public static Match getMatch() {
        return match;
    }

    public static void main(String[] args) {

        //RED STARTS

        //start game method, switches sides of players
        //set default positions




        match = new Match();
        match.startGame();

//        while()

//            checkMateCheck();

        System.out.println("x wins!");


    }
}
