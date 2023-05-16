package cz.cvut.fel.strobad1.XiangQi.model;

import java.io.*;
import java.util.ArrayList;

public class SaveManager {


    private ArrayList<Match> saveFileList;
    File saveFile;


    public SaveManager() throws IOException {

        saveFile = new File("C:/Users/Adam/Desktop/FEL CVUT/Semestralka JAVA/XiangQi/SaveFile.txt");
        if(!saveFile.exists()){


            saveFile.createNewFile();

            System.out.println("new savefile created");
        }
    }

    /**
     * Loads game from file
     * @param match
     */
    public void loadGame(Match match){

    }


    /**
     * Saves game to file
     * @param match
     */
    public void saveGame(Match match) throws IOException {

        OutputStream os = new FileOutputStream(saveFile);
        OutputStreamWriter osw = new OutputStreamWriter(os);

        ArrayList<Board> moveHistory = match.getMoveHistory();

        for (int i = 0; i < moveHistory.size(); i++) {
            String[]     movesPerformed = moveHistory.get(i).getMovesPerformedThisTurn();
            String orderedMovesPerformed = (i+1) +"." + movesPerformed[0] + " " +movesPerformed[1] + " ";
            osw.write(orderedMovesPerformed);

        }
        

        osw.close();

    }


}