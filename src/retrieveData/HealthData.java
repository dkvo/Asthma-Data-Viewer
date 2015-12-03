package retrieveData;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

import com.mysql.jdbc.PreparedStatement;

import config.MySQLConfig;

public class HealthData {
	String jdbcURL = "jdbc:mysql://" + MySQLConfig.host + ":" + MySQLConfig.port + "/" + MySQLConfig.database;
	private static final String SELECT_ALL_QUERY = "select health.zipcode, health.county, region.city, state, weather.year, weather.month, ageGroup, numberofvisits, monthlyMax, monthlyMin, monthlyNor "
			+ "from health, region, weather " + "where health.zipcode = region.zipcode and region.city = weather.city and weather.year = health.year "
			+ "order by zipcode, weather.month, ageGroup";
	private static String UPDATE_SET_VALUE = "";
	private static String CONDITION = "";
	private static String SEARCH_QUERY = "select * from (select health.zipcode, health.county, region.city, state, weather.year, weather.month, ageGroup, numberofvisits, monthlyMax, monthlyMin, monthlyNor "
			+ "from health, region, weather where health.zipcode = region.zipcode and region.city = weather.city and weather.year = health.year "
			+ "order by zipcode, weather.month, ageGroup) as tab where ";
	private static final String SELECT_HEALTH_QUERY = "select * from health";
	private static final String SELECT_REGION_QUERY = "select * from region";
	private static final String SELECT_WEATHER_QUERY = "select * from weather";
	private static final String INSERT_HEALTH_QUERY = "insert into health (zipcode, county, year, ageGroup, numberOfVisits) values (?, ?, ?, ?, ?)";
	private static final String INSERT_REGION_QUERY = "insert into region (county, zipcode, city, state) values (?,?,?,?)";
	private static final String INSERT_WEATHER_QUERY = "insert into weather (city, year, month, monthlyMax, monthlyMin, monthlyNor) values (?,?,?,?,?,?)";
	private static String DELETE_HEALTH_QUERY = "delete from health where ";
	private static String DELETE_REGION_QUERY = "delete from region where ";
	private static String DELETE_WEATHER_QUERY = "delete from weather where ";
	
	private static String UPDATE_HEALTH_QUERY = "update health set " + UPDATE_SET_VALUE + " where " + CONDITION +";";
	private static String UPDATE_REGION_QUERY = "update region set " + UPDATE_SET_VALUE + " where " + CONDITION ;
	private static String UPDATE_WEATHER_QUERY = "update weather set " + UPDATE_SET_VALUE + " where " + CONDITION +";";
	private static String ANALYZE_QUEURY = " select distinct county,year, avg(numberofvisits) as avgVisits, avg(monthlyNor)  from " + "(" +
"select health.zipcode, health.county, region.city, state, weather.year, weather.month, ageGroup, numberofvisits, monthlyMax, monthlyMin, monthlyNor " +
"from health, region, weather where health.zipcode = region.zipcode and region.city = weather.city and weather.year = health.year and monthlyNor > -999.9 " +
"order by zipcode, weather.month, ageGroup) as test group by county, year order by avgVisits desc;";

	private ArrayList<Health> data;
	Connection connection = null;
	Statement statement = null;
	
	public HealthData(){
		try {
			/* Connect to database */
	        connection = DriverManager.getConnection(jdbcURL, MySQLConfig.user, MySQLConfig.password);
	        statement = connection.createStatement();
	        data = new ArrayList<Health>();
	    } catch (Exception e)
	    {
	        e.printStackTrace();
	    }
	}
	
	public ArrayList<Health> getData(){return data;}
	
	/* INSERT FUNCTION */
	public void insertData(Health h) throws SQLException{
		

		/* Insert data into health table : zipcode, county, year, ageGroup, numberOfVisits */
		PreparedStatement stmt = (PreparedStatement) connection.prepareStatement(INSERT_HEALTH_QUERY);
		stmt.setInt(1, h.getZipCode());
		stmt.setString(2, h.getCounty());
		stmt.setInt(3, h.getYear());
		stmt.setString(4, h.getAgeGroup());
		stmt.setInt(5, h.getNumOfVisits());
		
		int rowsInserted = stmt.executeUpdate();
		if (rowsInserted > 0) {
		    System.out.println("A new row is added in health table");
		}
		/* Insert data into region table */
		stmt = (PreparedStatement) connection.prepareStatement(INSERT_REGION_QUERY);
		stmt.setString(1, h.getCounty());
		stmt.setInt(2, h.getZipCode());
		stmt.setString(3, h.getCity());
		stmt.setString(4, h.getState());
		
		rowsInserted = stmt.executeUpdate();
		if (rowsInserted > 0) {
		    System.out.println("A new row is added in health table");
		}
		
		/*Insert data into weather table */
		stmt = (PreparedStatement) connection.prepareStatement(INSERT_WEATHER_QUERY);
		stmt.setString(1, h.getCity());
		stmt.setInt(2, h.getYear());
		stmt.setInt(3, h.getMonth());
		stmt.setFloat(4, h.getMMax());
		stmt.setFloat(5, h.getMMin());
		stmt.setFloat(6, h.getMNor());
		
		rowsInserted = stmt.executeUpdate();
		if (rowsInserted > 0) {
		    System.out.println("A new row is added in health table");
		}
	}
	
	/* DELETE FUNCTION */
	/*As selecting a row, you will get all the value in that row and put it into an object Health as a parameter to the function.
	 * delete function will then delete the data from database.
	 */
	public void deleteData(Health h){
		try {
			connection = DriverManager.getConnection(jdbcURL, MySQLConfig.user, MySQLConfig.password);
			
			String choice = "";
			boolean notFirst = false;
			
			
			DELETE_HEALTH_QUERY += "zipcode =" + h.getZipCode() + ", county = \"" + h.getCounty() + "\"" + ", year = " + h.getYear()
					+ ",agegroup = \"" + h.getAgeGroup() + "\"" + ", numberOfVisits =" + h.getNumOfVisits();
		
			PreparedStatement stmt = (PreparedStatement) connection.prepareStatement(DELETE_HEALTH_QUERY);
			int rowsInserted = stmt.executeUpdate();
			if (rowsInserted > 0) {
			    System.out.println("The row has been deleted in health table");
			}
			DELETE_HEALTH_QUERY = "delete from health where ";
		/* Delete in region table */
			DELETE_REGION_QUERY += "county = \"" + h.getCounty() + "\"" + ", zipcode =" + h.getZipCode() + ", city = \"" + h.getCity() + "\"" 
					+ ", state = \"" + h.getState() + "\"";
			stmt = (PreparedStatement) connection.prepareStatement(DELETE_REGION_QUERY);
			rowsInserted = stmt.executeUpdate();
			if (rowsInserted > 0) {
			    System.out.println("The row has been deleted in region table");
			}
			DELETE_REGION_QUERY = "delete from region where ";
			
			/* Delete from weather table */
			DELETE_WEATHER_QUERY += "city = \"" + h.getCity() + "\"" + ", year =" + h.getYear() + ", month =" + h.getMonth()
					+ ", MonthlyMax =" + h.getMMax() + ", MonthlyMin =" + h.getMMin() + ", monthlyNor =" + h.getMNor();
			stmt = (PreparedStatement) connection.prepareStatement(DELETE_WEATHER_QUERY);
			rowsInserted = stmt.executeUpdate();
			if (rowsInserted > 0) {
			    System.out.println("The row has been deleted in weather table");
			}
			DELETE_WEATHER_QUERY = "delete from weather where ";
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/* SHOW ALL COLUMNS IN DATABASE */
	public ArrayList<Health> showAllData(){
		ArrayList<Health> list = new ArrayList<Health>();
		try {
			connection = DriverManager.getConnection(jdbcURL, MySQLConfig.user, MySQLConfig.password);
			Statement stmt = connection.createStatement();
			ResultSet result = stmt.executeQuery(SELECT_ALL_QUERY);
			int count = 0;
			
			
			/* Get and print out data from health table : zipcode, county, city, state, year, month, ageGroup, numberOfVisits, MMax, MMin, MNor */
			while(result.next()){
				Health data = new Health();
				int zipcode = result.getInt(1);
				data.setZipCode(zipcode);
				String county = result.getString(2);
				data.setCounty(county);
				String city = result.getString(3);
				data.setCity(city);
				String state = result.getString(4);
				data.setState(state);
				int year = result.getInt(5);
				data.setYear(year);
				int month = result.getInt(6);
				data.setMonth(month);
				String ageGroup = result.getString(7);
				data.setAgeGroup(ageGroup);
				int numOfVisits = result.getInt(8);
				data.setNumOfVisits(numOfVisits);
				float MMax = result.getFloat(9);
				data.setMMax(MMax);
				float MMin = result.getFloat(10);
				data.setMMin(MMin);
				float MNor = result.getFloat(11);
				data.setMNor(MNor);
				list.add(data);
				
				count++;
			}	
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	
	/* UPDATE FUNCTION */
	/*Assume that when user selects a row, he/she will get all the data(DATA1) and store it in a Health object -> then click "Update" button, 
	 * a new window with a list of textfields will show up. each value of the object will be set as initial text for each textfield accordingly.
	 * User will then enter new value into the textfield and choose "OK", then the program update value in the object Health(use setter) which 
	 * contains the updated info, then pass to this function as a parameter
	 */
	public void updateData(Health h){
		try {
			connection = DriverManager.getConnection(jdbcURL, MySQLConfig.user, MySQLConfig.password);
			
			UPDATE_SET_VALUE = "";
			CONDITION = "";
			
			
			/* Update value in health table */
			/*
			UPDATE_SET_VALUE += "year = " + h.getYear() + ", numberofvisits = " + h.getNumOfVisits();	
			CONDITION += "zipcode = " + h.getZipCode() + " and agegroup = " + "'" + h.getAgeGroup() + "'";
			
			
			UPDATE_HEALTH_QUERY = "update health set " + UPDATE_SET_VALUE + " where " + CONDITION +";";
			System.out.println(UPDATE_HEALTH_QUERY);
			PreparedStatement statement = (PreparedStatement) connection.prepareStatement(UPDATE_HEALTH_QUERY);
			statement.executeUpdate();
			UPDATE_SET_VALUE = "";
			CONDITION = "";
			
			// Update value in weather table 
			UPDATE_SET_VALUE += "year = " + h.getYear() + ", month = " + h.getMonth() + ", monthlyMax = " + h.getMMax()
					+ ", monthlyMin = " + h.getMMin() + ", monthlyNor = " + h.getMNor();
			CONDITION += "city = " + "'" + h.getCity() + "'";
			statement = (PreparedStatement) connection.prepareStatement(UPDATE_WEATHER_QUERY);
			statement.executeUpdate();
			UPDATE_SET_VALUE = "";
			CONDITION = "";
			*/
			Statement statement = connection.createStatement();
			
			String healthUpdate = "UPDATE health set county = '" + h.getCounty() + "', numberOfVisits = " + h.getNumOfVisits() + " where zipCode = " + h.getZipCode() + " and ageGroup = '" + h.getAgeGroup() + "' and year = " + h.getYear() ;
            String regionUpdate = "UPDATE region set county = '" + h.getCounty() + "', state = '" + h.getState() + "' where zipCode =" + h.getZipCode() + " and city = '" + h.getCity() + "'";
            String weatherUpdate = "UPDATE weather set city = '" + h.getCity() + "', year = '" + h.getYear() + "', month = '" + h.getMonth() + "', monthlyMax = '" + h.getMMax() + "', MonthlyMin = '" + h.getMMin() +"', monthlyNor = '" + h.getMNor() + "' where city = '" + h.getCity() + "'";
            statement.execute(healthUpdate);
            statement.execute(regionUpdate);
            statement.execute(weatherUpdate);
            statement.execute("COMMIT");
            
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public ArrayList<Health> searchData(Health h){
		ArrayList<Health> list = new ArrayList<Health>();
		
		try {
			connection = DriverManager.getConnection(jdbcURL, MySQLConfig.user, MySQLConfig.password);
			String query = SEARCH_QUERY + " zipcode = " + h.getZipCode() + " and agegroup = '" + h.getAgeGroup() + "'";
			Statement stmt = connection.createStatement();
			ResultSet result = stmt.executeQuery(query);	
			System.out.println(query);
			
			while(result.next()){
				Health data = new Health();
				int zipcode = result.getInt(1);
				data.setZipCode(zipcode);
				String county = result.getString(2);
				data.setCounty(county);
				String city = result.getString(3);
				data.setCity(city);
				String state = result.getString(4);
				data.setState(state);
				int year = result.getInt(5);
				data.setYear(year);
				int month = result.getInt(6);
				data.setMonth(month);
				//System.out.println(data.getMonth());
				String ageGroup = result.getString(7);
				data.setAgeGroup(ageGroup);
				int numOfVisits = result.getInt(8);
				data.setNumOfVisits(numOfVisits);
				float MMax = result.getFloat(9);
				data.setMMax(MMax);
				float MMin = result.getFloat(10);
				data.setMMin(MMin);
				float MNor = result.getFloat(11);
				data.setMNor(MNor);
				list.add(data);
			}	
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return list;
	}
	
	public ArrayList<AnalyzeData> analyze()
    {
            ArrayList<AnalyzeData> list = new ArrayList<AnalyzeData>();
            String query = "select distinct county,year, avg(numberofvisits) as avgVisits, avg(monthlyNor) from (" +
            SELECT_ALL_QUERY + ") as test group by county, year order by avgVisits desc";

            try {
                    connection = DriverManager.getConnection(jdbcURL, MySQLConfig.user, MySQLConfig.password);
                    Statement stmt = connection.createStatement();
                    ResultSet result = stmt.executeQuery(ANALYZE_QUEURY);
                   
                    while(result.next()){
                            AnalyzeData data = new AnalyzeData();
                            String county = result.getString(1);
                            data.setCounty(county);
                            int year = result.getInt(2);
                            data.setYear(year);
                            float avgvisit = result.getFloat("avgVisits");
                            data.setAvgVisit(avgvisit);
                            float avg = result.getFloat("avg(monthlyNor)");
                            data.setAVG(avg);
                            list.add(data);
                    }
                    return list;
            }catch (Exception e) {
                    e.printStackTrace();
            }
            return list;
           
    }
	
}


