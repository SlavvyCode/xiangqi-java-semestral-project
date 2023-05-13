package cz.cvut.fel.strobad1.XiangQi.Model;

import cz.cvut.fel.strobad1.XiangQi.controller.MainMenuController;
import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.DoubleBinding;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.stage.Stage;


import javafx.scene.Scene;

import javafx.scene.layout.*;

import java.io.IOException;

import static javafx.application.Application.launch;


public class Main extends Application {

    private static Match match;

    public static Match getMatch() {
        return match;
    }








    public void start(Stage stage) throws Exception {
        // Set the stage's title and minimum size.
        stage.setTitle("FXML Welcome");
        stage.setMinWidth(800);
        stage.setMinHeight(600);

        // Load the FXML file for the main menu scene.
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/XiangQiBoard.fxml"));
        Parent root = loader.load();

        // Create a scene for the main menu scene.
        Scene scene = new Scene(root, 800, 600);

        // Set the stage's scene to the main menu scene.
        stage.setScene(scene);

        // Show the stage.
        stage.show();
    }


    public static void main(String[] args) throws IOException {
        launch(args);




        //RED STARTS

        //start game method, switches sides of players
        //set default positions



//        NEEDED TO START GAME:

        match = new Match();
        match.startGame();
    }

}







