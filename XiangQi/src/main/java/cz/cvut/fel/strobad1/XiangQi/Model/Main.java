package cz.cvut.fel.strobad1.XiangQi.Model;

import javafx.application.Application;
import javafx.fxml.*;
import javafx.scene.*;
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



public class Main extends Application {
    private static Match match;

    public static Match getMatch() {
        return match;
    }

//    public static void main(String[] args) {

//        //RED STARTS
//
//        //start game method, switches sides of players
//        //set default positions
//
//`
//
//
//        match = new Match();
//        match.startGame();
//
////        while()
//
////            checkMateCheck();
//
//        System.out.println("x wins!");
//




    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/cz/cvut/fel/strobad1/XiangQi/Model/LoadMenu.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}






