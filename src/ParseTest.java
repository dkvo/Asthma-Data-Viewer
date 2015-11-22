import data.DataParser;

/**
 * Created by christopherbachner on 11/13/15.
 *
 * @author christopherbachner
 */
public class ParseTest {

    public static void main (String[] args)
    {
        new DataParser().parseData("health.csv", DataParser.HEALTH);
        new DataParser().parseData("city.csv", DataParser.REGION);
        new DataParser().parseData("weather.csv", DataParser.WEATHER);
    }


}
