package cz.cvut.fel.strobad1.XiangQi.model;

import javafx.application.Platform;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;
import java.util.logging.Logger;

public class SaveManager {

    private Match match;
    // Get a logger for the current class
    Logger logger = Logger.getLogger(SaveManager.class.getName());

    public void saveGame(Match match) {
        this.match = match;
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

                new FileChooser.ExtensionFilter("XiangQi save files", "*.XiangQi"),
                new FileChooser.ExtensionFilter("Text Files", "*.txt"),
                new FileChooser.ExtensionFilter("All Files", "*.*")
        );

        // Show the file save dialog and get the selected file
        File selectedFile = fileChooser.showSaveDialog(new Stage());

        // Check if the user has selected a file
        if (selectedFile != null) {
            // Save the file using your own logic
            String filePath = selectedFile.getAbsolutePath();

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
                ArrayList<Board> moveHistory = match.getMoveHistory();
                for (int i = 0; i < moveHistory.size(); i++) {
//                    String[] movesPerformed = moveHistory.get(i).getMovePerformedThisTurn();
//                    String orderedMovesPerformed = (i + 1) + "." + movesPerformed[0] + " " + movesPerformed[1] + " ";
//                    writer.write(orderedMovesPerformed);
//                    writer.newLine();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public Match  getMatchFromSaveFile() {


            // Create a FileChooser instance
            FileChooser fileChooser = new FileChooser();
            // Set the title of the file chooser
            fileChooser.setTitle("Load File");
            // Set the initial directory of the file chooser
            fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
            // Set the extension filters of the file chooser
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Text Files", "*.txt"),
                    new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"),
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


                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }



        return null;
    }


}



