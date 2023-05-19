package cz.cvut.fel.strobad1.xiangqi.controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainMenuController{


    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    public void switchToNewGameMenu(ActionEvent event) throws IOException {


        root = FXMLLoader.load(getClass().getResource("/scenes/NewGameMenu.fxml"));


        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void switchToLoadMenu(ActionEvent event) throws IOException {


        root = FXMLLoader.load(getClass().getResource("/scenes/LoadMenu.fxml"));


        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }



    @FXML
    public void exit(ActionEvent event) throws IOException {
        System.exit(0);

    }


    public void initialize(URL url, ResourceBundle resourceBundle) {


    }
}
