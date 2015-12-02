import data.DataParser;
import retrieveData.Health;
import retrieveData.HealthData;

import java.sql.SQLException;

/**
 * Created by christopherbachner on 11/13/15.
 *
 * @author christopherbachner
 */
public class ParseTest {

    public static void main (String[] args)
    {
        System.out.printf("Welcome! Let's create our database...\n");
       //new DataParser().parseData("health.csv", DataParser.HEALTH);
       // new DataParser().parseData("city.csv", DataParser.REGION);
        //new DataParser().parseData("weather.csv", DataParser.WEATHER);
       Health test = new Health(95134, 1000, 1, "None", "No Country", "TestCity", "No State", 2, 1, 3,4);
        Health test2 = new Health(95134,2012,34, "Adults (18+)", "SANTA CLARA", "SANTA JOSE", "CA", 1, (float)61.8, (float)39.9, (float)50.9);
        //'SANTA CLARA', '95134', 'SAN JOSE', 'CA', '95134', 'SANTA CLARA', '2012', 'Adults (18+)', '34', 'SAN JOSE', '2010', '1', '58.1', '43.9', '51'
        //'SANTA CLARA','95134','SAN JOSE','CA','95134','SANTA CLARA','2012','Adults (18+)','34','SAN JOSE','2012','1','61.8','39.9','50.9'


        //new HealthData().insertData(test);
           new HealthData().deleteData(test2);

    }


}
