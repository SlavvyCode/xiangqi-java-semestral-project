package cz.cvut.fel.strobad1.XiangQi.controller;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class NewGameController{


    private Stage stage;
    private Scene scene;
    private Parent root;


    @FXML
    public void switchToGameBoardView(ActionEvent event) throws IOException {

        GameController gameController = new GameController();

        root = FXMLLoader.load(getClass().getResource("/scenes/XiangQiBoard.fxml"));


        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void switchToMainMenu(ActionEvent event) throws IOException {

        GameController gameController = new GameController();

        root = FXMLLoader.load(getClass().getResource("/scenes/MainMenu.fxml"));


        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public void initialize(URL url, ResourceBundle resourceBundle) {


    }
}
