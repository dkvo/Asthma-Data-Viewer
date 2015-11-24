package data;

import config.MySQLConfig;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by christopherbachner on 11/11/15.
 *
 * @author christopherbachner
 */
public class DataParser implements MySQLConfig {

    public static final int WEATHER = 1;
    public static final int HEALTH = 2;
    public static final int REGION = 3;
    private final boolean DEBUG = false;
    private Connection connection = null;
    private Statement statement = null;
    private ArrayList<String> dataList = null;

    public DataParser() {
        try {
            String jdbcURL = "jdbc:mysql://" + MySQLConfig.host + "/" + MySQLConfig.database;
            connection = DriverManager.getConnection(jdbcURL, MySQLConfig.user, MySQLConfig.password);
            statement = connection.createStatement();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Parses Data and puts the data into the SQL Database.
     *
     * @param filePath Path to .CSV file
     * @param dataType Use static fields of this class (e.g. DataParser.WEATHER)
     */
    public void parseData(String filePath, int dataType) {

        switch (dataType) {
            case WEATHER:
                this.parseWeather(filePath);
                break;
            case HEALTH:
                this.parseHealth(filePath);
                break;
            case REGION:
                this.parseCity(filePath);
                break;
            default:
        }

    }

    /**
     * Parses the climate/weather data and puts data into SQL. Drops existing table.
     *
     * @param filePath Path to file.
     */
    private void parseWeather(String filePath) {

        String parseQuery = null;
        Iterator<String> dataIterator;

        try {
            this.dataList = new CSVReader().readFile(filePath);
            dataIterator = dataList.iterator();

            statement.execute("drop table if exists weather");
            statement.execute("create table weather(city varchar(1024), year int, month int, monthlyMax float, MonthlyMin float, monthlyNor float)");
            //statement.execute("create index weatherInd on weather(city)");

            while (dataIterator.hasNext()) {
                parseQuery = parseWeatherLine(dataIterator.next());
                if (parseQuery != null) {
                    statement.execute(parseQuery);
                }
            }

            statement.execute("COMMIT ");
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Parses each line of the weather.csv
     * @param line Line of CSV file
     * @return Valid SQL insert statement
     */
    //STATION	STATION_NAME	ELEVATION	LATITUDE	LONGITUDE	DATE	MMXT	MMNT	MNTM
    private String parseWeatherLine(String line) {

        String[] columns = line.split(",");

        try {
            String city = columns[1].toUpperCase().replace("\'", "''").replace(" CA US", "");
            String year = columns[5].substring(0, 4);
            String month = columns[5].substring(4, 6);
            float monthMax = Float.parseFloat(columns[6]) / 10;
            float monthMin = Float.parseFloat(columns[7]) / 10;
            float monthNor = Float.parseFloat(columns[8]) / 10;

            return "INSERT INTO weather(city, year, month, monthlyMax, monthlyMin, monthlyNor) VALUES('" + city + "'," + year + "," + month + "," + monthMax + "," + monthMin + "," + monthNor + ");";
        } catch (NumberFormatException e) {
            if (DEBUG) {
                System.out.printf("(INFO)WEATHER data omitted due to invalid parse: " + e.getMessage() + "\n");
            }
            return null;
        } catch (IndexOutOfBoundsException e) {
            if (DEBUG) {
                System.out.printf("(INFO)WEATHER data omitted due to invalid parse: " + e.getMessage() + "\n");
            }
            return null;
        }


    }

    /**
     * Parses data from a health.CSV
     *
     * @param filePath Path to health.csv
     */
    private void parseHealth(String filePath) {

        String parseQuery = null;
        Iterator<String> dataIterator;


        this.dataList = new CSVReader().readFile(filePath);
        dataIterator = dataList.iterator();


        try {
            statement.execute("drop table if exists health");
            statement.execute("create table health(zipCode int, county varchar(100), year int, ageGroup varchar(100), numberOfVisits int)");
            statement.execute("create index zipInd on health(county)");

            while (dataIterator.hasNext()) {
                parseQuery = parseHealthLine(dataIterator.next() + "," + dataIterator.next());
                if (parseQuery != null) {
                    statement.execute(parseQuery);
                }
            }

            statement.execute("COMMIT ");
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Converts a line from the CSV file to a valid SQL insert statement. Omits inserts that are invalid.
     *
     * @param line line from dataLine ArrayList
     * @return Returns a valid SQL insert statement.
     */
    //ZIP code,COORDINATES,County,Year,Age Group,Number of Visits,County Fips code,Age-adjusted rate
    private String parseHealthLine(String line) {
        String[] columns = line.replace("\"", "").split(",");


        try {
            int zipCode = Integer.parseInt(columns[0]);
            String county = columns[3];
            int year = Integer.parseInt(columns[4]);
            String ageGroup = columns[5];
            int numberOfVisits = Integer.parseInt(columns[6]);

            //Necessary because there seems to be an "AllAges" and an "All Ages" in the source
            if (ageGroup.equals("AllAges")) {
                ageGroup = "All Ages";
            }


            return "INSERT INTO health(zipCode, county, year, ageGroup, numberOfVisits) VALUES(" + zipCode + ",'" + county + "'," + year + ",'" + ageGroup + "'," + numberOfVisits + ")";
        } catch (NumberFormatException e) {
            if (DEBUG) {
                System.out.printf("(INFO)Health data omitted due to invalid parse: " + e.getMessage() + "\n");
            }
            return null;
        }
    }



    /**
     * Parses data from a city.CSV
     *
     * @param filePath Path to city.csv
     */
    private void parseCity(String filePath) {

        String parseQuery = null;
        Iterator<String> dataIterator;


        this.dataList = new CSVReader().readFile(filePath);
        dataIterator = dataList.iterator();


        try {
            statement.execute("drop table if exists region");
            statement.execute("create table region(county varchar(100), zipCode int, city varChar(100), state varchar(100))");
            statement.execute("create index countyIND on region(zipCode)");

            while (dataIterator.hasNext()) {
                parseQuery = parseCityLine(dataIterator.next());
                if (parseQuery != null) {
                    statement.execute(parseQuery);
                }
            }

            statement.execute("COMMIT ");
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Parses each line of the city.csv
     * @param line Line of CSV file
     * @return Valid SQL insert statement
     */
    //"zip_code","latitude","longitude","city","state","county"
    private String parseCityLine(String line) {
        String[] columns = line.replace("\"", "").split(",");

        try {
            String zipCode = columns[0];
            String city = columns[3].toUpperCase().replace("\'", "''");
            String state = columns[4].toUpperCase().replace("\'", "''");
            String county = columns[5].toUpperCase().replace("\'", "''");

            return "INSERT INTO region(county, zipCode, city, state) VALUES('" + county + "','" + zipCode + "','" + city + "','" + state + "')";
        } catch (NumberFormatException e) {
            if (DEBUG) {
                System.out.printf("(INFO)Region data omitted due to invalid parse: " + e.getMessage() + "\n");
            }
            return null;
        } catch (IndexOutOfBoundsException e) {
            if (DEBUG) {
                System.out.printf("(INFO)Region data omitted due to invalid parse: " + e.getMessage() + "\n");
            }
            return null;
        }


    }


}
