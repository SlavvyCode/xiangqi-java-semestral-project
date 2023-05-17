package cz.cvut.fel.strobad1.XiangQi.model;

import cz.cvut.fel.strobad1.XiangQi.controller.GameController;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.Parent;

import java.io.IOException;
import java.time.Duration;


import java.io.*;
import java.util.logging.Logger;

public class SaveManager {

    private Match match;
    String matchHistoryString;
    // Get a logger for the current class
    Logger logger = Logger.getLogger(SaveManager.class.getName());

    private static Match matchToLoad;
    private Stage stage;
    private Scene scene;
    private Parent root;
    public void saveGame(String matchHistoryString) {
        this.matchHistoryString = matchHistoryString;
        Platform.runLater(this::chooseFileToSave);
    }

    private void chooseFileToSave() {
        // Create a FileChooser instance
        FileChooser fileChooser = new FileChooser();

        // Set the title of the file chooser
        fileChooser.setTitle("Select Save File from the program or XiChess");

        // Set the initial directory of the file chooser
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));

        // Set the extension filters of the file chooser
        fileChooser.getExtensionFilters().addAll(

                new FileChooser.ExtensionFilter("Text Files", "*.txt"),
//                new FileChooser.ExtensionFilter("XiangQi save files", "*.XiangQi"),
                new FileChooser.ExtensionFilter("All Files", "*.*")
        );

        // Show the file save dialog and get the selected file
        File selectedFile = fileChooser.showSaveDialog(new Stage());


        // Check if the user has selected a file
        if (selectedFile != null) {
            // Save the file using your own logic
            String filePath = selectedFile.getAbsolutePath();

            try (FileWriter writer = new FileWriter(filePath)) {


                writer.write("Red's remaining time:");
                writer.write((int) match.getGameClock().getRedRemainingTime());
                writer.write("\n");
                writer.write("Black's remaining time:");
                writer.write((int) match.getGameClock().getBlackRemainingTime());


                writer.write("Move history:");
                writer.write("\n");

                // Loop through each character of the string
                for (int i = 0; i < matchHistoryString.length(); i++) {
                    // Write the character to the file
                    writer.write(matchHistoryString.charAt(i));
                }
                // Flush the writer to ensure all data is written
                writer.flush();



            } catch (IOException e) {
                // Handle any exceptions
                e.printStackTrace();
            }
        }



    }

    public void prepareMatchForLoading(ActionEvent event) {


//        Interpret remainingTime

//        whose turn it is

        boolean isRedTurn;
        //in seconds most likely
        Duration blackRemainingTime;
        Duration redRemainingTime;

//            if VALID FILE


            // Create a FileChooser instance
            FileChooser fileChooser = new FileChooser();
            // Set the title of the file chooser
            fileChooser.setTitle("Load File");
            // Set the initial directory of the file chooser
            fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
            // Set the extension filters of the file chooser
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Text Files", "*.txt"),
                    new FileChooser.ExtensionFilter("All Files", "*.*")
            );
            // Show the file open dialog and get the selected file
            File selectedFile = fileChooser.showOpenDialog(new Stage());
            // Check if the user has selected a file
            if (selectedFile != null) {
                // Load the file using your own logic
                String filePath = selectedFile.getAbsolutePath();
                try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        // Parse and process each line of the file


                        String move = "d9e8";

                        String movePerformed = move; // Replace with the desired move string

// Extract old and new column letters
                        String oldColLetter = movePerformed.substring(0, 1);
                        String newColLetter = movePerformed.substring(2, 3);

// Convert column letters to column numbers
                        int oldColNumber = oldColLetter.charAt(0) - 'a';
                        int newColNumber = newColLetter.charAt(0) - 'a';

// Extract row numbers
                        int oldRowNumber = Integer.parseInt(movePerformed.substring(1, 2));
                        int newRowNumber = Integer.parseInt(movePerformed.substring(3, 4));

                        System.out.println("Old Row: " + oldRowNumber);
                        System.out.println("Old Col: " + oldColNumber);
                        System.out.println("New Row: " + newRowNumber);
                        System.out.println("New Col: " + newColNumber);



                        //split into

                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }



        return null;
    }




    @FXML
    public void loadGame(ActionEvent event) throws IOException {


//            GameController gameController = new GameController(match,remainingTimeRed,remainingTimeBlack);
            match = new Match();
            GameController gameController = new GameController(match,5);

            match.gameClock.;


//            match.setAiColor();



            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/scenes/XiangQiBoard.fxml"));
            // Set the controller factory to create and inject the GameController object
            loader.setControllerFactory(c -> new GameController(match, 5));
            // Load the root node from the FXML file
            root = loader.load();
            // Switch to the new scene
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }

    }
}



