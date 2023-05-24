package cz.cvut.fel.strobad1.xiangqi.controller;

import cz.cvut.fel.strobad1.xiangqi.model.Match;
import cz.cvut.fel.strobad1.xiangqi.model.SaveManager;
import cz.cvut.fel.strobad1.xiangqi.model.colorEnum;
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
import java.util.ArrayList;
import java.util.ResourceBundle;

public class LoadGameController implements Initializable {
    SaveManager saveManager;
    private Stage stage;
    private Scene scene;
    private Parent root;
    private boolean againstAI = false;
    @FXML
    private ChoiceBox opponentSelection;
    @FXML
    private ChoiceBox aiColorSelection;


    private int opponentSelectionChoiceIndex;
    private String aiColorSelectionString;

    @FXML
    public void switchToMainMenu(ActionEvent event) throws IOException {


        root = FXMLLoader.load(getClass().getResource("/scenes/MainMenu.fxml"));


        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void loadGameAndSwitchToGameScreen(ActionEvent event) throws IOException, CloneNotSupportedException {


        Match match = new Match();

        if (opponentSelectionChoiceIndex == 0) {
            match.setPlayingAgainstAI(true);
        } else {
            match.setPlayingAgainstAI(false);
        }


        if (aiColorSelectionString.equals("Red")) {
            match.setAiColor(colorEnum.RED);
        } else if (aiColorSelectionString.equals("Black")) {
            match.setAiColor(colorEnum.BLACK);
        }





        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/scenes/XiangQiBoard.fxml"));
        // Set the controller factory to create and inject the GameController object
        loader.setControllerFactory(c ->
                new GameController(
                        match,
                        saveManager.getRedTimeToLoad(),
                        saveManager.getBlackTimeToLoad(),
                        saveManager.getMovesToLoad()
                )
        );
        // Load the root node from the FXML file
        root = loader.load();
        // Switch to the new scene
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


    public void initialize(URL url, ResourceBundle resourceBundle) {

        saveManager = new SaveManager();


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


        aiColorSelection.getSelectionModel().select("Red");
        aiColorSelectionString = (String) aiColorSelection.getSelectionModel().getSelectedItem();
    }

    public void selectSaveGame(ActionEvent actionEvent) {
        saveManager.prepareMatchForLoading();
    }

    public void setIfGameAgainstAI(ActionEvent actionEvent) {
        againstAI = !againstAI;
    }

}
