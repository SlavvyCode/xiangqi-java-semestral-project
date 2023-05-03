package cz.cvut.fel.strobad1.XiangQi.Model;

import javafx.application.Application;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.ListView;
import javafx.scene.text.Text;
import javafx.stage.Stage;




import javafx.application.Application;

import javafx.scene.Group;

import javafx.scene.Scene;

import javafx.scene.control.Accordion;

import javafx.scene.control.Button;

import javafx.scene.control.TitledPane;

import javafx.scene.layout.*;

import javafx.scene.paint.Color;

import javafx.scene.transform.Scale;

import javafx.stage.Stage;

import java.io.IOException;


public class Main {
    private static Match match;

    public static Match getMatch() {
        return match;
    }





    public static void main(String[] args) throws IOException {

        //RED STARTS

        //start game method, switches sides of players
        //set default positions



        //NEEDED TO START GAME:

        match = new Match();
        match.startGame();





//
//    @Override
//    public void start(Stage stage) throws Exception {
//
//
////        Parent root = FXMLLoader.load(getClass().getResource("/LoadMenu.fxml"));
//        Parent root = FXMLLoader.load(getClass().getResource("/XiangQiBoard.fxml"));
//
//
//
//
//
//        Scene scene = new Scene(root, 800, 600);
//
//        stage.setMinWidth(800);
//        stage.setMinHeight(600);
//
//
//
//        stage.setTitle("FXML Welcome");
//        stage.setScene(scene);
//        stage.show();
//    }
//
//    public static void main(String[] args) {
//        launch(args);
//
//    }

}
}






