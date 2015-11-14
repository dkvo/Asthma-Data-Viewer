package data;

import java.io.*;
import java.util.ArrayList;

/**
 * Created by christopherbachner on 11/13/15.
 *
 * @author christopherbachner
 */
public class CSVReader {


    /**
     * Reads the .CSV file into an ArrayList so that any of the parse methods can parse it.
     *
     * @param filePath Path to .CSV file
     * @return ArrayList with one line per index
     */
    public ArrayList<String> readFile(String filePath) {
        String currentLine = null;
        ArrayList<String> dataLines = new ArrayList<String>();

        try {
            File dataFile = new File(filePath);
            BufferedReader bufferedReader = new BufferedReader(new FileReader(dataFile));

            //skip first line in CSV file because it's just column descriptor
            bufferedReader.readLine();

            while ((currentLine = bufferedReader.readLine()) != null) {
                dataLines.add(new String(currentLine));
            }

            return dataLines;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}