import data.DataParser;
import dataGUI.WeatherFrame;
import retrieveData.Health;
import retrieveData.HealthData;

import java.awt.*;
import java.sql.SQLException;

/**
 * Created by christopherbachner on 11/13/15.
 *
 * @author christopherbachner
 */
public class ParseLauncher {

    public static void main (String[] args)
    {
        System.out.printf("Welcome! Let's create our database...\n");
        new DataParser().parseData("health.csv", DataParser.HEALTH);
        new DataParser().parseData("city.csv", DataParser.REGION);
        new DataParser().parseData("weather.csv", DataParser.WEATHER);
        System.out.printf("Launching GUI...\n");
        //LAUNCH GUI
        EventQueue.invokeLater(new Runnable() {
                    public void run() {
                        try {
                            WeatherFrame frame = new WeatherFrame();
                            frame.setVisible(true);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
    }


}
