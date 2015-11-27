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
			+ "order by zipcode, weather.month;";
	private static String UPDATE_SET_VALUE = "";
	private static String CONDITION = "";
	private static String SEARCH_QUERY = "select * from (" + SELECT_ALL_QUERY + ") as tab where " + CONDITION;
	private static final String SELECT_HEALTH_QUERY = "select * from health";
	private static final String SELECT_REGION_QUERY = "select * from region";
	private static final String SELECT_WEATHER_QUERY = "select * from weather";
	private static final String INSERT_HEALTH_QUERY = "insert into health (zipcode, county, year, ageGroup, numberOfVisits) values (?, ?, ?, ?, ?)";
	private static final String INSERT_REGION_QUERY = "insert into region (county, zipcode, city, state) values (?,?,?,?)";
	private static final String INSERT_WEATHER_QUERY = "insert into weather (city, year, month, monthlyMax, monthlyMin, monthlyNor) values (?,?,?,?,?,?)";
	private static String DELETE_HEALTH_QUERY = "delete from health where ";
	private static String DELETE_REGION_QUERY = "delete from region where ";
	private static String DELETE_WEATHER_QUERY = "delete from weather where ";
	
	private static String UPDATE_HEALTH_QUERY = "update health set " + UPDATE_SET_VALUE + " where " + CONDITION ;
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
		Scanner sc = new Scanner(System.in);

		/* Insert data into health table : zipcode, county, year, ageGroup, numberOfVisits */
		PreparedStatement stmt = (PreparedStatement) connection.prepareStatement(INSERT_HEALTH_QUERY);
		System.out.println("Test: " + h.getZipCode());
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
			Scanner sc = new Scanner(System.in);
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
			System.out.println(stmt);
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
	public void showAllData(){
		try {
			connection = DriverManager.getConnection(jdbcURL, MySQLConfig.user, MySQLConfig.password);
			Statement stmt = connection.createStatement();
			ResultSet result = stmt.executeQuery(SELECT_ALL_QUERY);
			int count = 0;
			
			/* Get and print out data from health table : zipcode, county, city, state, year, month, ageGroup, numberOfVisits, MMax, MMin, MNor */
			while(result.next()){
				int zipcode = result.getInt(1);
				String county = result.getString(2);
				String city = result.getString(3);
				String state = result.getString(4);
				int year = result.getInt(5);
				int month = result.getInt(6);
				String ageGroup = result.getString(7);
				int numOfVisits = result.getInt(8);
				float MMax = result.getFloat(9);
				float MMin = result.getFloat(10);
				float MNor = result.getFloat(11);
				
				System.out.println(zipcode + " , " + county + " , " + city + " , " + state + " , " + year + " , " + month + " , " + ageGroup + " , " + numOfVisits + " , " + MMax + " , " + MMin + " , " + MNor);
				count++;
			}	
			System.out.println("Total = " + count);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
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
			Scanner sc = new Scanner(System.in);
			
			/* Update value in health table */
			UPDATE_SET_VALUE += "year = " + h.getYear() + ", numberofvisits = " + h.getNumOfVisits();	
			CONDITION += "zipcode = " + h.getZipCode() + ", agegroup = " + h.getAgeGroup();
			
			PreparedStatement statement = (PreparedStatement) connection.prepareStatement(UPDATE_HEALTH_QUERY);
			statement.executeUpdate();
			UPDATE_SET_VALUE = "";
			CONDITION = "";
			
			/* Update value in weather table */
			UPDATE_SET_VALUE += "year = " + h.getYear() + ", month = " + h.getMonth() + ", monthlyMax = " + h.getMMax()
					+ ", monthlyMin = " + h.getMMin() + ", monthlyNor = " + h.getMNor();
			CONDITION += "city = " + h.getCity();
			statement = (PreparedStatement) connection.prepareStatement(UPDATE_HEALTH_QUERY);
			statement.executeUpdate();
			UPDATE_SET_VALUE = "";
			CONDITION = "";
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void searchData(Health h){
		boolean comma = false;
		try {
			connection = DriverManager.getConnection(jdbcURL, MySQLConfig.user, MySQLConfig.password);
			if(h.getZipCode() != 0){
				CONDITION += "zipcode = " + h.getZipCode();
				comma = true;
			}
			
			if(!h.getCounty().equals("") && comma == true){
				CONDITION += ", county = \"" + h.getCounty() + "\"";
			}
			else CONDITION += "county = \"" + h.getCounty() + "\"";
			
			if(!h.getCity().equals("") && comma == true){
				CONDITION += ", city = \"" + h.getCity() + "\"";
			}
			else CONDITION += "city = \"" + h.getCity() + "\"";
			
			if(!h.getState().equals("") && comma == true){
				CONDITION += ", state = \"" + h.getState() + "\"";
			}
			else CONDITION += "state = \"" + h.getState() + "\"";
			
			if(h.getYear() != 0 && comma == true){
				CONDITION += ", year = " + h.getYear();
			}
			else CONDITION += "year = " + h.getYear();
			
			if(h.getMonth() != 0 && comma == true){
				CONDITION += ", month = " + h.getMonth();
			}
			else CONDITION += "month = " + h.getMonth();
			
			if(!h.getAgeGroup().equals("") && comma == true){
				CONDITION += ", agegroup = \"" + h.getAgeGroup() + "\"";
			}
			else CONDITION += "agegroup = \"" + h.getAgeGroup() + "\"";
			
			if(h.getNumOfVisits() != 0 && comma == true){
				CONDITION += ", numberofvisits = " + h.getNumOfVisits();
			}
			else CONDITION += "numberofvisits = " + h.getNumOfVisits();
			
			if(h.getMMax() != 0 && comma == true){
				CONDITION += ", monthlymax = " + h.getMMax();
			}
			else CONDITION += "monthlymax = " + h.getMMax();
			
			if(h.getMMin() != 0 && comma == true){
				CONDITION += ", monthlymin = " + h.getMMin();
			}
			else CONDITION += "monthlymin = " + h.getMMin();
			
			if(h.getMNor() != 0 && comma == true){
				CONDITION += ", monthlynor = " + h.getMNor();
			}
			else CONDITION += "monthlynor = " + h.getMNor();
			
			Statement stmt = connection.createStatement();
			ResultSet result = stmt.executeQuery(SEARCH_QUERY);
			CONDITION = "";
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	
}


