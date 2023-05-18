package cz.cvut.fel.strobad1.xiangqi.controller;

import cz.cvut.fel.strobad1.xiangqi.model.Match;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class NewGameController implements Initializable {


    private static Match match;
    private Stage stage;
    private Scene scene;
    private Parent root;


    @FXML
    private ChoiceBox opponentSelection;
    @FXML
    private ChoiceBox aiColorSelection;

    @FXML
    private ChoiceBox timeSelection;

    int timeSettingMin;

    private int opponentSelectionChoiceIndex;
    private String aiColorSelectionString;


    @FXML
    public void switchToGameBoardView(ActionEvent event) throws IOException {


        match = new Match();
        GameController gameController = new GameController(match,timeSettingMin);




        if (opponentSelectionChoiceIndex == 0) {
            match.setPlayingAgainstAI(true);
        } else {
            match.setPlayingAgainstAI(false);
        }


        if(aiColorSelectionString.equals("Red")){
            match.setAiColor("red");
        }
        else if(aiColorSelectionString.equals("Black")){
            match.setAiColor("black");
        }

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/scenes/XiangQiBoard.fxml"));
        // Set the controller factory to create and inject the GameController object
        loader.setControllerFactory(c -> new GameController(match, timeSettingMin));
        // Load the root node from the FXML file
        root = loader.load();
        // Switch to the new scene
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void switchToMainMenu(ActionEvent event) throws IOException {

        root = FXMLLoader.load(getClass().getResource("/scenes/MainMenu.fxml"));


        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


    public void initialize(URL url, ResourceBundle resourceBundle) {


        opponentSelection.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            //Set the opponent selection choice index based on the selected option
            if (newVal.equals("Computer")) {
                opponentSelectionChoiceIndex = 0;
            } else if (newVal.equals("User")) {
                opponentSelectionChoiceIndex = 1;
            }
        });

        opponentSelection.getSelectionModel().select(0);



        aiColorSelection.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            //Set the opponent selection choice index based on the selected option
            if (newVal.equals("Red")) {
                aiColorSelectionString = "Red";
            } else if (newVal.equals("Black")) {
                aiColorSelectionString = "Black";
            }
        });

        aiColorSelection.getSelectionModel().select(0);


        timeSelection.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            //Set the opponent selection choice index based on the selected option
            if (newVal.equals("60 min each")) {
                timeSettingMin = 60;
            } else if (newVal.equals("10 min each")) {
                timeSettingMin = 10;
            } else if (newVal.equals("1 min each")) {
                timeSettingMin = 1;
            }
        });

        timeSelection.getSelectionModel().select(0);
    }


}
