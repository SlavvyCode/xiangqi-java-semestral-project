package cz.cvut.fel.strobad1.xiangqi.controller;

import cz.cvut.fel.strobad1.xiangqi.model.*;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.DoubleBinding;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.LoadException;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.logging.Logger;

public class GameController {

    private static Match match;
    int realTurnNum;
    int timeSettingMin;
    Logger logger = Logger.getLogger(Match.class.getName());
    @FXML
    Text clockDisplay;
    @FXML
    Text infoDisplay;
    @FXML
    Text turnDisplay;
    // Define the initial position of the piece
    double startX = 0;
    double startY = 0;
    ChessClock gameTime;
    StringBuilder matchHistoryString;
    @FXML
    Text moveHistoryDisplay;
    // Declare a global variable to store the last written index
    int lastWrittenIndex = -1;
    private double lastX;
    private double lastY;
    @FXML
    private GridPane boardGrid;
    @FXML
    private BorderPane gameScene;
    private Stage stage;
    private Scene scene;
    private Parent root;
    private ArrayList<String> movesToLoad;


    private boolean startedViaLoad;
    private int redTimeToLoad;
    private int blackTimeToLoad;



    public GameController(Match match, int timeSettingMin) {
        // Set the match field with the constructor parameter
        this.match = match;
        this.timeSettingMin = timeSettingMin;
    }


    public GameController(Match match, int redTimeToLoad, int blackTimeToLoad, ArrayList<String> movesToLoad) {
        // Set the match field with the constructor parameter
        this.match = match;
        this.redTimeToLoad = redTimeToLoad;;
        this.blackTimeToLoad = blackTimeToLoad;
        this.movesToLoad = movesToLoad;
        startedViaLoad =true;
    }

    @FXML
    public void initialize() throws IOException, CloneNotSupportedException {


        //RED STARTS

        //start game method, switches sides of players
        //set default positions


//        NEEDED TO START GAME:


        match.startGame();
        // Get a logger for the current class
        Logger logger = Logger.getLogger(GameController.class.getName());

        // Log an info message


        Board board = match.getGameBoard();

        logger.info("Board created.");

        if(!startedViaLoad){
            logger.info(timeSettingMin + "is time in mins");
            gameTime = new ChessClock(timeSettingMin * 60 * 1000, match);

        }
        else{

            logger.info(redTimeToLoad + " is red's time");
            logger.info(blackTimeToLoad + " is black's time");
            gameTime = new ChessClock(redTimeToLoad,blackTimeToLoad,match);
        }


        //set up clock

        match.setGameClock(gameTime);

        gameTime.pauseCountdown();

        logger.info("Clock setUp");

        match.startTurn();

        logger.info("Match started");


        matchHistoryString = new StringBuilder();



        bindBoardSizeToCenter();



        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("mm:ss");


        // Create a Timeline that updates the time every second
        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> updateClock() ),
                new KeyFrame(Duration.millis(500)));


        // Set the cycle count to indefinite, so the animation repeats forever
        clock.setCycleCount(Animation.INDEFINITE);
        // Start the animation
        clock.play();


        // Create a Timeline that updates the time every second
        Timeline updater = new Timeline(new KeyFrame(Duration.ZERO, e ->
            updateEverything()
        ), new KeyFrame(Duration.millis(100)));
        // Set the cycle count to indefinite, so the animation repeats forever
        updater.setCycleCount(Animation.INDEFINITE);
        // Start the animation
        updater.play();


        Timeline aiTimeline = new Timeline(new KeyFrame(Duration.ZERO, e ->
            aiMove()
        ), new KeyFrame(Duration.seconds(2)));
        // Set the cycle count to indefinite, so the animation repeats forever
        aiTimeline.setCycleCount(Animation.INDEFINITE);
// Start the animation
        aiTimeline.play();


        logger.info("user is playing against AI == " + match.isPlayingAgainstAI());


        if (startedViaLoad) {

            if (blackTimeToLoad <= 0 || redTimeToLoad <= 0) {

                logger.severe("Invalid file! Time<=zero!");

                throw new LoadException("FILE LOAD FAILED");

            }



            for (String moveString : movesToLoad) {
                // Extract old and new column letters
                String oldColLetter = moveString.substring(0, 1);
                String newColLetter = moveString.substring(2, 3);

                // Convert column letters to column numbers
                int oldColNumber = oldColLetter.charAt(0) - 'a';
                int newColNumber = newColLetter.charAt(0) - 'a';

                // Extract row numbers
                int oldRowNumber = Integer.parseInt(moveString.substring(1, 2));
                int newRowNumber = Integer.parseInt(moveString.substring(3, 4));

                System.out.println("Old Row: " + oldRowNumber);
                System.out.println("Old Col: " + oldColNumber);
                System.out.println("New Row: " + newRowNumber);
                System.out.println("New Col: " + newColNumber);


                Piece pieceToMove = board.getCell(oldRowNumber, oldColNumber).getPieceOnCell();


                //moves piece
                if (pieceToMove.moveIfValid(newRowNumber, newColNumber)) {
                    match.startTurn();
                }
                else
                {

                    logger.severe("failed to load file, invalid move included");
                    //exit to menu

                   throw new LoadException("FILE LOAD FAILED");
                }

            }

        }

        updateBoard();
    }

    private void bindBoardSizeToCenter() {
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
    }

    private void aiMove() {

        Board board = match.getGameBoard();
        if (match.getAiColor() != null) {
            try {

                if((!board.isRedTurn() && match.getAiColor().equals("red"))
                        ||
                    board.isRedTurn() && match.getAiColor().equals("black"))
                {
                    return;
                }

                if(match.getGameClock().isPaused()){
                    return;
                }

                System.out.println(board.isRedTurn());
                System.out.println(match.getAiColor());

                match.randomAIMove();

                board = match.getGameBoard();

                updateBoard();


            } catch (IOException ex) {
                throw new RuntimeException(ex);
            } catch (CloneNotSupportedException ex) {
                throw new RuntimeException(ex);
            }


        }
    }

    private void updateClock() {
        // Get the current time and format it as hh:mm:ss

        String redTime = null;
        String blackTime = null;


        if (gameTime.getBlackRemainingTime() > 0 && gameTime.getRedRemainingTime() > 0) {
//                redTime = LocalTime.ofSecondOfDay(gameTime.getRedRemainingTime() / 1000)
//                        .format(formatter);
//                blackTime = LocalTime.ofSecondOfDay(gameTime.getBlackRemainingTime() / 1000)
//                        .format(formatter);
            // Calculate minutes and seconds
            int redMinutes = (int) (gameTime.getRedRemainingTime() / 1000 / 60);
            int redSeconds = (int) ((gameTime.getRedRemainingTime() / 1000) % 60);

            int blackMinutes = (int) (gameTime.getBlackRemainingTime() / 1000 / 60);
            int blackSeconds = (int) ((gameTime.getBlackRemainingTime() / 1000) % 60);

// Format minutes and seconds as "mm:ss"
            redTime = String.format("%02d:%02d", redMinutes, redSeconds);
            blackTime = String.format("%02d:%02d", blackMinutes, blackSeconds);


        } else {
            match.setGameDraw(true);
        }

        // Set the text of the label to the formatted time
        clockDisplay.setText("red time: " + redTime + "\nblack time: " + blackTime);
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

        private void pieceAdding (Piece pieceOnCell, StackPane stackPane){

            Board board = match.getViewingBoard();

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


                    if (gameTime.isPaused() || match.getViewingPast() || (match.getVictor() != null)) {
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

                    if ((pieceOnCell.getColor().equals("black") && board.isRedTurn()) ||
                            (pieceOnCell.getColor().equals("red") && !board.isRedTurn())) {
                        return;
                    }
                    if (gameTime.isPaused() || match.getViewingPast() || (match.getVictor() != null)) {
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
                    if ((pieceOnCell.getColor().equals("black") && board.isRedTurn()) ||
                            (pieceOnCell.getColor().equals("red") && !board.isRedTurn())) {
                        return;
                    }
                    if (gameTime.isPaused() || match.getViewingPast() || (match.getVictor() != null)) {
                        return;
                    }
                    if ((pieceOnCell.getColor().equals("black") && board.isRedTurn()) ||
                            (pieceOnCell.getColor().equals("red") && !board.isRedTurn())) {
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
                        logger.info("User tried placing piece at " + GridPane.getRowIndex(closestNode) +
                                " and " + GridPane.getColumnIndex(closestNode));

                        // Get the row and column indices of the closest node
                        int row = GridPane.getRowIndex(closestNode);
                        int col = GridPane.getColumnIndex(closestNode);

                        // Check if the cell is empty
                        // Move the piece to the empty cell

                        if (pieceOnCell.moveIfValid(row, col)) {
                            // Remove the piece from the old cell

                            try {
                                match.startTurn();

                            } catch (IOException e) {

                                logger.severe("An IO error occurred: " + e.getMessage());
                                throw new RuntimeException(e);
                            } catch (CloneNotSupportedException e) {
                                logger.severe("A cloning error occurred: " + e.getMessage());
                                throw new RuntimeException(e);
                            }

                            stackPane.getChildren().clear();
                        } else {
                            pieceImageView.setTranslateX(originalX);
                            pieceImageView.setTranslateY(originalY);
                        }

                    }

                    updateBoard();
                });


            } else {
                stackPane.getChildren().clear();

            }
        }

        public void viewOlderBoard () {

            match.getViewingBoard1TurnBack();
            updateBoard();
        }

        public void viewNewerBoard () {
            match.getViewingBoard1TurnAhead();
            updateBoard();
        }

        public void pauseClock () {

            if (match.getVictor() != null) {
                return;
            }

            if (!gameTime.isPaused()) {
                gameTime.pauseCountdown();
                return;
            }
            gameTime.resumeCountdown();
        }

        public void resign (ActionEvent actionEvent){
            Board board = match.getGameBoard();

            match.gameOver();

            if (board.isRedTurn()) {
                match.setBlackWins(true);
            } else {
                match.setRedWins(true);
            }
        }

        public void updateEverything () {
            updateInfoDisplay();
            updateHistory();

        }

        public void updateInfoDisplay () {
            Board board = match.getGameBoard();


            if (match.getVictor() != null && match.getVictor().equals("red")) {
                infoDisplay.setText("Game over!\n Red wins!");
            } else if (match.getVictor() != null && match.getVictor().equals("black")) {

                infoDisplay.setText("Game over!\n Black wins!");
            } else if (gameTime.isPaused()) {
                infoDisplay.setText("P A U S E D");
            } else if (board.isRedTurn()) {


                infoDisplay.setText("Red's turn to play");
            } else {
                infoDisplay.setText("Black's turn to play");
            }


            int viewingBoardTurn = match.getMoveHistory().indexOf(match.getViewingBoard());
            viewingBoardTurn = viewingBoardTurn / 2;
            turnDisplay.setText("viewing turn " + viewingBoardTurn);

        }

        @FXML
        public void exitAndSwitchToMainMenu (ActionEvent event) throws IOException {


            pauseClock();

            root = FXMLLoader.load(getClass().getResource("/scenes/MainMenu.fxml"));


            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }

        public void saveOngoingMatch () throws IOException {

            if ((match.getVictor() != null)) {
                return;
            }
            SaveManager saveManager = match.getSaveManager();
            saveManager.saveGame(matchHistoryString.toString(), match);
        }


    /**
     * Updates history in the right bottom corner.
     *
     */
    public void updateHistory () {
            ArrayList<Board> moveHistory = match.getMoveHistory();

            int i = moveHistory.size() - 1;

            if (moveHistory.size() <= 2 || lastWrittenIndex == i) {
                return;
            }

            String movePerformed = moveHistory.get(i).getMovePerformedThisTurn();

            if (!moveHistory.get(i).isRedTurn()) {

                realTurnNum = (i / 2);
                matchHistoryString.append(realTurnNum + ". " + movePerformed + " ");

            } else {
                matchHistoryString.append(movePerformed);
                matchHistoryString.append(System.lineSeparator());
            }

            lastWrittenIndex = i;

            moveHistoryDisplay.setText(matchHistoryString.toString());

        }

    }



