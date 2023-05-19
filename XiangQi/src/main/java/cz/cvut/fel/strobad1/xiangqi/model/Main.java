package cz.cvut.fel.strobad1.xiangqi.model;

import javafx.application.Application;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.stage.Stage;


import javafx.scene.Scene;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import static javafx.application.Application.launch;


public class Main extends Application {

    public void start(Stage stage) throws Exception {
        // Set the stage's title and minimum size.
        stage.setTitle("XiangQi Semestralni projekt");
        stage.setMinWidth(800);
        stage.setMinHeight(600);

        // Load the FXML file for the main menu scene.
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/scenes/MainMenu.fxml"));
        Parent root = loader.load();

        // Create a scene for the main menu scene.
        Scene scene = new Scene(root, 800, 600);

        // Set the stage's scene to the main menu scene.
        stage.setScene(scene);

        // Show the stage.
        stage.show();


        Logger logger = Logger.getLogger(Main.class.getName());

//        String fileName = "src/main/resources/logging.properties";
//        InputStream inputStream = new FileInputStream(fileName);
//        LogManager logManager = LogManager.getLogManager();
//        logManager.readConfiguration(inputStream);
        logger.info("Main Menu loaded.");


    }


    public static void main(String[] args) throws IOException {
        launch(args);
    }

}







