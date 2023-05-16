package cz.cvut.fel.strobad1.XiangQi.controller;

import cz.cvut.fel.strobad1.XiangQi.model.*;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.DoubleBinding;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.time.Clock;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;


public class GameController {

    private static Match match;
    public GameController(Match match) {
        // Set the match field with the constructor parameter
        this.match = match;
    }


    // Define the initial position of the piece
    double startX = 0;
    double startY = 0;
    private double lastX;
    private double lastY;
    @FXML
    private GridPane boardGrid;
    @FXML
    private BorderPane gameScene;
    private Stage stage;
    private Scene scene;
    private Parent root;
    private Board board;
    ChessClock gameTime;



    @FXML
    public void initialize() throws IOException, CloneNotSupportedException {


        //RED STARTS

        //start game method, switches sides of players
        //set default positions


//        NEEDED TO START GAME:


        match.startGame();

        board = match.getGameBoard();

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

        DoubleBinding ratioSize = minSize.multiply(1189 / 1154);

        boardGrid.maxWidthProperty().bind(ratioSize);
        boardGrid.maxHeightProperty().bind(minSize);
        boardGrid.minWidthProperty().bind(ratioSize.multiply(0.3));
        boardGrid.minHeightProperty().bind(minSize.multiply(0.3));


        gameScene.setCenter(stackpane);
        //#endregion


        updateBoard();


        gameTime = match.getGameClock();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("h:mm:ss");


        Text clockLabel = (Text) gameScene.lookup("#ClockDisplay");

        // Create a Timeline that updates the time every second
        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            // Get the current time and format it as hh:mm:ss

            String redTime=null;
            String blackTime = null;

            if(gameTime.getBlackRemainingTime()>0 && gameTime.getRedRemainingTime() >0){
                redTime = LocalTime.ofSecondOfDay(gameTime.getRedRemainingTime() / 1000)
                        .format(formatter);
                blackTime = LocalTime.ofSecondOfDay(gameTime.getBlackRemainingTime() / 1000)
                        .format(formatter);

            }
            else {
                match.setGameDraw(true);
            }

            // Set the text of the label to the formatted time
            clockLabel.setText("\n\n\n red time is: " + redTime + "\n and black time is: " + blackTime + "\n");

        }), new KeyFrame(Duration.seconds(1)));
// Set the cycle count to indefinite, so the animation repeats forever
        clock.setCycleCount(Animation.INDEFINITE);
// Start the animation
        clock.play();

// Add the label to the scene
        gameScene.getChildren().add(clockLabel);


        System.err.println("user is playing against AI == " + match.isPlayingAgainstAI());
    }

    public void updateBoard() {

        Cell[][] cellList = match.getViewingBoard().getCellList();


        boardGrid.getChildren().clear();

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 9; j++) {


                StackPane cellPane = new StackPane();

                boardGrid.add(cellPane, j, i);

                Piece pieceOnCell = cellList[i][j].getPieceOnCell();


                pieceAdding(pieceOnCell, cellPane);

            }
        }
    }

    private void pieceAdding(Piece pieceOnCell, StackPane stackPane) {
        if (pieceOnCell != null) {
            // Create an ImageView for the piece
            ImageView pieceImageView = new ImageView();

            // Set the image source according to the piece type and color
            String color = pieceOnCell.getColor().equals("red") ? "Red" : "Black";
            String pieceType = pieceOnCell.getClass().getSimpleName();
            String imagePath = "/images/Pieces/Western/" + color + "-Western_Xiangqi_" + pieceType + "_(Trad).png";
            pieceImageView.setImage((new Image(getClass().getResource(imagePath).toExternalForm())));

            // Bind the StackPane's size to the Scene's size
            pieceImageView.fitWidthProperty().bind(boardGrid.widthProperty().multiply(0.1));
            pieceImageView.fitHeightProperty().bind(boardGrid.heightProperty().multiply(0.091));

            // Add the ImageView to the StackPane
            stackPane.setAlignment(Pos.CENTER);
            stackPane.getChildren().add(pieceImageView);


            double originalX = pieceImageView.getTranslateX();
            double originalY = pieceImageView.getTranslateY();

            // Add an event handler for mouse press on the image
            pieceImageView.setOnMousePressed(event -> {


                if(gameTime.isPaused() || match.getViewingPast() || (match.getVictor()!=null)){
                    return;
                }
                // Get the initial mouse coordinates
                startX = event.getSceneX();
                startY = event.getSceneY();
                // Bring the piece to the front
                pieceImageView.toFront();
            });

            // Add an event handler for mouse drag on the image
            pieceImageView.setOnMouseDragged(event -> {

                if(gameTime.isPaused() || match.getViewingPast() || (match.getVictor()!=null)){
                    return;
                }
                // Get the current mouse coordinates
                double mouseX = event.getSceneX();
                double mouseY = event.getSceneY();
                // Calculate the new position of the piece
                double newTranslateX = pieceImageView.getTranslateX() + mouseX - startX;
                double newTranslateY = pieceImageView.getTranslateY() + mouseY - startY;
                // Move the piece to the new position
                pieceImageView.setTranslateX(newTranslateX);
                pieceImageView.setTranslateY(newTranslateY);
                // Update the initial mouse coordinates
                startX = mouseX;
                startY = mouseY;
            });

            // Add an event handler for mouse release on the image
            pieceImageView.setOnMouseReleased(event -> {
                // Get the current mouse coordinates

                if(gameTime.isPaused() || match.getViewingPast() || (match.getVictor()!=null)){
                    return;
                }

                double mouseX = event.getSceneX();
                double mouseY = event.getSceneY();
                // Loop through the children nodes of the GridPane
                double closestDistance = Double.MAX_VALUE;
                Node closestNode = null;
                for (Node node : boardGrid.getChildren()) {
                    // Check if the node contains the mouse coordinates
                    if (node.contains(node.sceneToLocal(mouseX, mouseY))) {
                        double distance = Math.sqrt(Math.pow(node.getBoundsInParent().getMinX() - mouseX, 2) + Math.pow(node.getBoundsInParent().getMinY() - mouseY, 2));
                        if (distance < closestDistance) {
                            closestDistance = distance;
                            closestNode = node;
                        }
                    }
                }

                if (closestNode != null) {
                    System.out.println("Piece placed at row " + GridPane.getRowIndex(closestNode) + " and column " + GridPane.getColumnIndex(closestNode));

                    // Get the row and column indices of the closest node
                    int row = GridPane.getRowIndex(closestNode);
                    int col = GridPane.getColumnIndex(closestNode);

                    // Check if the cell is empty
                    // Move the piece to the empty cell

                    if(pieceOnCell.moveIfValid(row, col)){
                        // Remove the piece from the old cell
                        updateBoard();
                        try {
                            match.startTurn();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        } catch (CloneNotSupportedException e) {
                            throw new RuntimeException(e);
                        }

                        stackPane.getChildren().clear();
                    }
                    else{
                        pieceImageView.setTranslateX(originalX);
                        pieceImageView.setTranslateY(originalY);
                    }



                }


            });


        } else {
            stackPane.getChildren().clear();
        }
    }

    public void viewOlderBoard(){

        match.getViewingBoard1TurnBack();
        updateBoard();
    }
    public void viewNewerBoard(){
        match.getViewingBoard1TurnAhead();
        updateBoard();
    }


    public void pauseClock(){

        if(match.getVictor()!=null){
            return;
        }

        if(!gameTime.isPaused()){
            gameTime.pauseCountdown();
            return;
        }
        gameTime.resumeCountdown();
    }

    public void resign(ActionEvent actionEvent) {

        match.gameOver();

        if(match.isRedTurn()){
            match.setBlackWins(true);
        }
        else
        {
            match.setRedWins(true);
        }
    }



    public void updateInfoDisplay(){

        Text infoDisplay = (Text) gameScene.lookup("#infoDisplay");

        if(match.getVictor().equals("red")){
            infoDisplay.setText("Game over!\n Red wins!");
        }
        else if(match.getVictor().equals("black")){

            infoDisplay.setText("Game over!\n Black wins!");
        }
        else if (gameTime.isPaused()) {
            infoDisplay.setText("P A U S E D");
        } else if (match.isRedTurn()) {
            infoDisplay.setText("Red's turn to play");
        } else {
            infoDisplay.setText("Black's turn to play");
        }
    }



    @FXML
    public void exitAndSwitchToMainMenu(ActionEvent event) throws IOException {

        root = FXMLLoader.load(getClass().getResource("/scenes/MainMenu.fxml"));




        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


    public void saveOngoingMatch() throws IOException {

        SaveManager saveManager = match.getSaveManager();
        saveManager.saveGame(match);
    }


    public String initMatchHistoryTracking() {

        Text moveHistoryDisplay = (Text) gameScene.lookup("#moveHistoryDisplay");


        // Create a StringBuilder object
        StringBuilder matchHistoryString = new StringBuilder();
        ArrayList<Board> moveHistory = match.getMoveHistory();
        for (int i = 0; i < moveHistory.size(); i++) {
            String[] movesPerformed = moveHistory.get(i).getMovesPerformedThisTurn();
            if (movesPerformed[1] == null) {
                String orderedMovesPerformed = (i + 1) + "." + movesPerformed[0] + " " + movesPerformed[1] + " ";
                // Append the orderedMovesPerformed string to the StringBuilder object
                matchHistoryString.append(orderedMovesPerformed);
                // Write a new line to the StringBuilder object
                matchHistoryString.append(System.lineSeparator());
            } else {
                // Handle the case when movesPerformed[1] is not null
            }
        }
        // Return the string representation of the StringBuilder object
        return matchHistoryString.toString();
    }

}
