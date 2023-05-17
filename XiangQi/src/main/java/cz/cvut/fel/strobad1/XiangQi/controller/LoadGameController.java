package cz.cvut.fel.strobad1.XiangQi.controller;

import cz.cvut.fel.strobad1.XiangQi.model.SaveManager;
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

public class LoadGameController {
    private Stage stage;
    private Scene scene;
    private Parent root;
    private boolean againstAI = false;

    @FXML
    public void switchToMainMenu(ActionEvent event) throws IOException {



        root = FXMLLoader.load(getClass().getResource("/scenes/MainMenu.fxml"));


        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void loadGameAndSwitchToGameScreen(ActionEvent event) throws IOException {


        SaveManager saveManager = new SaveManager();

        saveManager.getMatchFromSaveFile();
        root = FXMLLoader.load(getClass().getResource("/scenes/XiangQiBoard.fxml"));




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

    public void initialize(URL url, ResourceBundle resourceBundle) {


    }

    public void selectSaveGame(ActionEvent actionEvent) {
    }

    public void setIfGameAgainstAI(ActionEvent actionEvent) {
        againstAI = !againstAI;
    }

}
