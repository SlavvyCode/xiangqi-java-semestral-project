package cz.cvut.fel.strobad1.XiangQi.controller;

import cz.cvut.fel.strobad1.XiangQi.model.Match;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitMenuButton;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class NewGameController implements Initializable{


    private Stage stage;
    private Scene scene;
    private Parent root;
    private static Match match;

    @FXML
    private ChoiceBox opponentSelection;

    @FXML
    private MenuItem item1;

    @FXML
    private MenuItem item2;

    private int opponentSelectionChoiceIndex;


    @FXML
    public void switchToGameBoardView(ActionEvent event) throws IOException {

        GameController gameController = new GameController(match);

        match = new Match();



        if(opponentSelectionChoiceIndex==0) {
        match.setPlayingAgainstAI(true);
        }
        else {
            match.setPlayingAgainstAI(false);
        }

//        root = FXMLLoader.load(getClass().getResource("/scenes/XiangQiBoard.fxml"));
//
//
//        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
//        scene = new Scene(root);
//        stage.setScene(scene);
//        stage.show();


        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/scenes/XiangQiBoard.fxml"));
        // Set the controller factory to create and inject the GameController object
        loader.setControllerFactory(c -> new GameController(match));
        // Load the root node from the FXML file
        root = loader.load();
        // Switch to the new scene
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void switchToMainMenu(ActionEvent event) throws IOException {

        root = FXMLLoader.load(getClass().getResource("/scenes/MainMenu.fxml"));


        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


    public void initialize(URL url, ResourceBundle resourceBundle) {


        opponentSelection.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            //Set the opponent selection choice index based on the selected option
            if(newVal.equals("Computer")) {
                opponentSelectionChoiceIndex = 0;
            } else if(newVal.equals("User")) {
                opponentSelectionChoiceIndex = 1;
            }
        });

        opponentSelection.getSelectionModel().select(0);

    }


}
