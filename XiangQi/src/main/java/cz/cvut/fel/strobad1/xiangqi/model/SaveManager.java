package cz.cvut.fel.strobad1.xiangqi.model;

import cz.cvut.fel.strobad1.xiangqi.controller.GameController;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Logger;

public class SaveManager {

    String matchHistoryString;
    // Get a logger for the current class
    Logger logger = Logger.getLogger(SaveManager.class.getName());
    ArrayList<String> movesToLoad;
    int redTimeToLoad;
    int blackTimeToLoad;
    private Match matchToSave;
    private Match matchToLoad;
    private Stage stage;
    private Scene scene;
    private Parent root;

    public void saveGame(String matchHistoryString, Match matchToSave) {
        this.matchHistoryString = matchHistoryString;
        this.matchToSave = matchToSave;
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
//                new FileChooser.ExtensionFilter("xiangqi save files", "*.xiangqi"),
                new FileChooser.ExtensionFilter("All Files", "*.*")
        );

        // Show the file save dialog and get the selected file
        File selectedFile = fileChooser.showSaveDialog(new Stage());


        // Check if the user has selected a file
        if (selectedFile != null) {
            // Save the file using your own logic
            String filePath = selectedFile.getAbsolutePath();

            try (FileWriter writer = new FileWriter(filePath)) {


                Duration redRemainingTime = Duration.ofSeconds(matchToSave.getGameClock().getRedRemainingTime() / 1000);
                long redRemainingTimeSeconds = redRemainingTime.toSeconds();
                String redRemainingTimeString = String.valueOf(redRemainingTimeSeconds);


                Duration blackRemainingTime = Duration.ofSeconds(matchToSave.getGameClock().getBlackRemainingTime() / 1000);
                long blackRemainingTimeSeconds = blackRemainingTime.toSeconds();
                String blackRemainingTimeString = String.valueOf(blackRemainingTimeSeconds);


                writer.write("Red's remaining time: ");
                writer.write(redRemainingTimeString);
                writer.write("\n");

                writer.write("Black's remaining time: ");
                writer.write(blackRemainingTimeString);
                writer.write("\n");


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

    public void prepareMatchForLoading() {


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
            String filePath = selectedFile.getAbsolutePath();
            try (Scanner scanner = new Scanner(new File(filePath))) {
                // Initialize an array list to store the moves
                movesToLoad = new ArrayList<>();
                // Initialize variables to store the remaining time values
                redTimeToLoad = 0;
                blackTimeToLoad = 0;
                // Initialize a boolean flag to indicate the start of the move history
                boolean start = false;
                while (scanner.hasNextLine()) {
                    // Read and process each line of the file
                    String line = scanner.nextLine();
                    if (line.startsWith("Red's remaining time:")) {
                        redTimeToLoad = Integer.parseInt(line.substring(line.indexOf(":") + 1).trim());
                    } else if (line.startsWith("Black's remaining time:")) {
                        blackTimeToLoad = Integer.parseInt(line.substring(line.indexOf(":") + 1).trim());
                    } else if (line.equals("Move history:")) {
                        // Set the flag to true
                        start = true;
                    } else if (start && !line.isEmpty()) {
                        // Split the line by space and add the moves to the array list
                        String[] tokens = line.split(" ");
                        movesToLoad.add(tokens[1]);
                        movesToLoad.add(tokens[2]);
                    }
                }
                // Print the results
                logger.info("FILE INFO: Red's remaining time: " + redTimeToLoad);
                logger.info("FILE INFO: Black's remaining time: " + blackTimeToLoad);
                logger.info("FILE INFO: Move history: " + movesToLoad);


            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            // Throw an error if the file is null

            logger.severe("File wasn't selected");
            throw new IllegalArgumentException("No file selected");
        }
        return;
    }



    public ArrayList<String> getMovesToLoad() {
        return movesToLoad;
    }

    public int getRedTimeToLoad() {
        return redTimeToLoad;
    }

    public int getBlackTimeToLoad() {
        return blackTimeToLoad;
    }



}




