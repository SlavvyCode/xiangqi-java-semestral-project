package cz.cvut.fel.strobad1.XiangQi.Model;

import java.io.*;
import java.util.ArrayList;

public class SaveManager {


    private ArrayList<Match> saveFileList;
    File saveFile;


    public SaveManager() throws IOException {
        saveFile = new File("/SaveFile.txt");
        System.out.println(saveFile.exists());
        InputStream is = new FileInputStream(saveFile);
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);
        String s;
        while ((s = br.readLine()) != null) {
            System.out.println(s);
        }
        br.close();



    }

    /**
     * Loads game from file
     * @param board
     */
    public void loadGame(Board board){

    }


    /**
     * Saves game to file
     * @param board
     */
    public void saveGame(Board board) throws IOException {

        OutputStream os = new FileOutputStream(saveFile);
        OutputStreamWriter osw = new OutputStreamWriter(os);

        ArrayList<Board> moveHistory = Main.getMatch().getMoveHistory();

        for (int i = 0; i < moveHistory.size()-1; i++) {
            String[]     movesPerformed = moveHistory.get(i).getMovesPerformedThisTurn();
            String orderedMovesPerformed = (i+1) +"." + movesPerformed[0] + " " +movesPerformed[1] + " ";
            osw.write(orderedMovesPerformed);

        }
        

        osw.close();

    }


}
