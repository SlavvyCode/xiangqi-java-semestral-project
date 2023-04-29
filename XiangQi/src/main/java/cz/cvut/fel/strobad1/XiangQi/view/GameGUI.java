package cz.cvut.fel.strobad1.XiangQi.view;


import javafx.scene.layout.GridPane;
import cz.cvut.fel.strobad1.XiangQi.Model.*;

public class GameGUI extends GridPane {


    /**
     * displays error log
     * @param message
     */
    public void displayErrorMessage(String message){

    }


    /**
     *
     * @return piece which mouse is on
     */

    public Piece getSelectedPiece(){
        return null;
    }

    /**
     * display game over screen
     * @param Winner
     */
    public void displayGameOver(Player Winner){}

    /**
     * display pause menu screen
     */
    public void displayPauseMenu(){

    }

    /**
     * display save window
     */
    public void displaySaveWindwo(){

    }

    /**
     * get selected cell which mouse is on
     * @return cell
     */
    public Cell getSelectedCell(){

        return null;
    }

    /**
     * update the visual side of the game.
     */
    public void updateBoardView(){

    }

}
