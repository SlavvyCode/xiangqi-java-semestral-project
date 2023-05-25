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

public class Main extends Application {
    private static Main instance;



    public Main() throws Exception{
        if (instance!=null){
            throw new Exception("Main already exists");
        }
    }
    // public static method that returns the instance
    public static Main Instance() throws Exception {
        // use synchronization or double-checked locking for thread-safety
        if (instance == null) {
            synchronized (Main.class) {
                if (instance == null) {
                    instance = new Main();
                }
            }
        }
        return instance;
    }

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

        Logger logger = SingletonLogger.getInstance().getLogger();


        logger.info("Main Menu loaded.");

        // assign instance to this object
        instance = this;
    }

    public static void main(String[] args) throws IOException {
        launch(args);
    }

}
