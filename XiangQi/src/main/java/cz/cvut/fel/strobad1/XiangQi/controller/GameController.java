package cz.cvut.fel.strobad1.XiangQi.controller;
import cz.cvut.fel.strobad1.XiangQi.model.Match;
import cz.cvut.fel.strobad1.XiangQi.model.Pieces.*;

import cz.cvut.fel.strobad1.XiangQi.model.Cell;
import cz.cvut.fel.strobad1.XiangQi.model.Main;
import cz.cvut.fel.strobad1.XiangQi.model.Piece;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.DoubleBinding;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

import java.io.IOException;


public class GameController {


    private static Match match;

    @FXML
    private GridPane boardGrid;
    @FXML
    private BorderPane gameScene;


    @FXML
    public void initialize() throws IOException {




        //RED STARTS

        //start game method, switches sides of players
        //set default positions



//        NEEDED TO START GAME:

        match = new Match();
        match.startGame();




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



//        Pane pane = new Pane();
//        pane.getStyleClass().add("redChariot");
//
//
//        boardGrid.add(pane,1,1);
//
//        updateBoard();

    }

    public void updateBoard() {

        //#region BIND stackPane SIZE TO cell size
        // create a stackpane to wrap the gridpane








        Cell[][] cellList = match.getViewingBoard().getCellList();


        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 9; j++) {


                boardGrid.add(new StackPane(),i,j);


                Piece pieceOnCell = cellList[i][j].getPieceOnCell();

                // Get the node from the grid cell
                Node node = getNodeFromGridPane(boardGrid, i, j);




//                StackPane cellPane = new StackPane(node);
//
//                // bind the stackpane's size to the scene's size
//                cellPane.prefWidthProperty().bind(boardGrid.getRowConstraints().get(i).maxHeightProperty());
//
//                cellPane.prefWidthProperty().bind(boardGrid.getColumnConstraints().get(j).maxWidthProperty());
//
//
//
//                DoubleBinding minSize = (DoubleBinding) Bindings.min(cellPane.widthProperty(), cellPane.heightProperty());
//
//                DoubleBinding ratioSize = minSize.multiply(1);
//
//                 boardGrid.maxWidthProperty().bind(ratioSize);
//                 boardGrid.maxHeightProperty().bind(minSize);
//                 boardGrid.minWidthProperty().bind(ratioSize.multiply(0.3));
//                 boardGrid.minHeightProperty().bind(minSize.multiply(0.3));





                // Check if the node is a StackPane
                if (node instanceof StackPane stackPane) {
                    // Check if there is a piece on the cell
                    pieceAdding(pieceOnCell, stackPane);
                } else {
                    // If the node is not a StackPane, create a new one and add it to the grid cell
                    StackPane newStackPane = new StackPane();
                    boardGrid.add(newStackPane, j, i);

                    pieceAdding(pieceOnCell, newStackPane);
                }

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
            pieceImageView.setImage((new Image(getClass().getResource("/images/Pieces/Western/Red-Western_Xiangqi_Advisor_(Trad).png").toExternalForm())));

            // Add the ImageView to the StackPane
            stackPane.getChildren().add(pieceImageView);

        }
        else
        {
            // If there is no piece on the cell, create a new StackPane and add it to the grid cell
            stackPane.getChildren().removeAll();
        }
    }

//    private Node getNodeFromGridPane(GridPane boardGrid, int row, int col) {
//        for (Node node : boardGrid.getChildren()) {
//
//            Integer columnIndex = GridPane.getColumnIndex(node);
//            Integer rowIndex = GridPane.getRowIndex(node);
//
//            if (columnIndex != null && rowIndex != null && columnIndex.intValue() == col && rowIndex.intValue() == row) {
//                return node;
//            }
//
//            return node;
//        }
//
//        System.err.println(row + " " + col + " are Out of bounds.");
//
//        System.err.println("...");
//        return null;
//    }

    private Node getNodeFromGridPane(GridPane boardGrid, int row, int col) {
        // Get the list of children nodes from the GridPane
        ObservableList<Node> children = boardGrid.getChildren();

        // Check if the row and column indices are valid

        if (row < 0 || col < 0 || row >= boardGrid.getRowConstraints().size() || col >= boardGrid.getColumnConstraints().size()) {
//        if (row < 0 || col < 0 || row >= 10 || col >= 9) {
            // If not, print an error message and return null
            System.err.println(row + " " + col + " are Out of bounds.");
            System.err.println("...");
            return null;
        }

        // Loop through the children nodes
        for (Node node : children) {
            // Get the row and column index of the node
            Integer rowIndex = GridPane.getRowIndex(node);
            Integer columnIndex = GridPane.getColumnIndex(node);

            // Check if the node has the same row and column index as the parameters
            if (rowIndex != null && columnIndex != null && rowIndex.intValue() == row && columnIndex.intValue() == col) {
                // If yes, return the node
                return node;
            }
        }

        System.err.println(row + " " + col + " no Node Found.");

        // If no node is found, return null
        return null;
    }



}
