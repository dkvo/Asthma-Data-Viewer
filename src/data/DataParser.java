package data;

import config.MySQLConfig;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by christopherbachner on 11/11/15.
 *
 * @author christopherbachner
 */
public class DataParser implements MySQLConfig {

    public static final int WEATHER = 1;
    public static final int HEALTH = 2;
    public static final int REGION = 3;


    /**
     * Parses Data and puts the data into the SQL Database.
     *
     * @param filePath Path to .CSV file
     * @param dataType Use static fields of this class (e.g. DataParser.WEATHER)
     */
    public void parseData(String filePath, int dataType) {

        switch (dataType)
        {
            case WEATHER:
                this.parseWeather(filePath);
                break;
            case HEALTH:
                break;
            case REGION:
                break;
            default:
        }

    }

    /**
     * Parses the climate/weather data and puts data into SQL. Drops existing table.
     * @param filePath Path to file.
     */

    private void parseWeather (String filePath)
    {
        Connection connection = null;
        Statement statement = null;
        String jdbcURL = "jdbc:mysql://" + MySQLConfig.host + "/" + MySQLConfig.database;
        try {
            connection = DriverManager.getConnection(jdbcURL, MySQLConfig.user, MySQLConfig.password);
            statement = connection.createStatement();
            statement.execute("drop table weather");

        } catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

}
