package cz.cvut.fel.strobad1.XiangQi.controller;

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;

public class GameController {


    @FXML
    private GridPane boardGrid;
    @FXML
    private BorderPane gameScene;


    @FXML
    public void initialize() {


        //#region BIND BOARD SIZE TO CENTER
        // create a stackpane to wrap the gridpane
        StackPane stackpane = new StackPane(boardGrid);

        // bind the stackpane's size to the scene's size
        stackpane.prefWidthProperty().bind(gameScene.widthProperty());
        stackpane.prefHeightProperty().bind(gameScene.heightProperty());


        // get the center node as a StackPane
        StackPane center = stackpane;



        stackpane.maxHeightProperty().bind(center.widthProperty());

        DoubleBinding minSize = (DoubleBinding) Bindings.min(stackpane.widthProperty(), stackpane.heightProperty());

        DoubleBinding ratioSize = minSize.multiply(1189/1154);

        boardGrid.maxWidthProperty().bind(ratioSize);
        boardGrid.maxHeightProperty().bind(minSize);
        boardGrid.minWidthProperty().bind(ratioSize.multiply(0.3));
        boardGrid.minHeightProperty().bind(minSize.multiply(0.3));


        gameScene.setCenter(stackpane);
        //#endregion


        // add any event handlers or bindings for your UI elements here


    }



    // a method to switch to the game scene
    public void setGameScene() {
        // get the stage from the main class and set the scene
        MainMenuController.stage.setScene(new Scene(gameScene));
    }




}
