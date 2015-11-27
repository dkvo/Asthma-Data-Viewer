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
	private static final String SELECT_HEALTH_QUERY = "select * from health";
	private static final String SELECT_REGION_QUERY = "select * from region";
	private static final String SELECT_WEATHER_QUERY = "select * from weather";
	private static final String INSERT_HEALTH_QUERY = "insert into health (zipcode, county, year, ageGroup, numberOfVisits) values (?, ?, ?, ?, ?)";
	private static final String INSERT_REGION_QUERY = "insert into region (county, zipcode, city, state) values (?,?,?,?)";
	private static final String INSERT_WEATHER_QUERY = "insert into weather (city, year, month, monthlyMax, monthlyMin, monthlyNor) values (?,?,?,?,?,?)";
	private static String DELETE_HEALTH_QUERY = "delete from health where ";
	private static String DELETE_REGION_QUERY = "delete from region where ";
	private static String DELETE_WEATHER_QUERY = "delete from weather where ";
	private static String UPDATE_SET_VALUE = "";
	private static String CONDITION = "";
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
	
	/* UPDATE DATA IN HEALTH TABLE */
	/* update either year or numberOfVisits */
	public void updateHealthTable(){
		try {
			connection = DriverManager.getConnection(jdbcURL, MySQLConfig.user, MySQLConfig.password);
			Scanner sc = new Scanner(System.in);
			
			System.out.println("Do you want to update (1) year or (2) number of visits?");
			int choice = Integer.parseInt(sc.nextLine());
			if(choice == 1){
				/* get update value */
				System.out.println("Enter new value for year: ");
				int year = Integer.parseInt(sc.nextLine());
				UPDATE_SET_VALUE += year;
				
				/* get condition */
				System.out.println("Enter zipcode: ");
				int zipCode = Integer.parseInt(sc.nextLine());
				System.out.println("Enter county: ");
				String county = sc.nextLine();		
				System.out.println("Enter group of age(1 for \"AllAges\" / 2 for \"Children (0-17)\" / 3 for \"Adult (18+)\"): ");
				int input = Integer.parseInt(sc.nextLine());
				String ageGroup;
				if (input == 1) ageGroup = "AllAges";
				else if (input == 2) ageGroup = "Children (0-17)";
				else if (input == 3) ageGroup = "Adult (18+)";
				else ageGroup = "AllAges";
				
				CONDITION = CONDITION + "zipcode = " + zipCode + ", county = " + county + ", agegroup = " + ageGroup;
			}
			else if (choice == 2){
				/* get update value */
				System.out.println("Enter new value for number of visits: ");
				int numOfVisits = Integer.parseInt(sc.nextLine());
				UPDATE_SET_VALUE += numOfVisits;
				
				/* get condition */
				System.out.println("Enter zipcode: ");
				int zipCode = Integer.parseInt(sc.nextLine());
				System.out.println("Enter year: ");
				int year = Integer.parseInt(sc.nextLine());	
				System.out.println("Enter group of age(1 for \"AllAges\" / 2 for \"Children (0-17)\" / 3 for \"Adult (18+)\"): ");
				int input = sc.nextInt();
				String ageGroup;
				if (input == 1) ageGroup = "AllAges";
				else if (input == 2) ageGroup = "Children (0-17)";
				else if (input == 3) ageGroup = "Adult (18+)";
				else ageGroup = "AllAges";
				
				CONDITION = CONDITION + "zipcode = " + zipCode + ", year = " + year + ", agegroup = " + ageGroup;
			}	
			
			PreparedStatement statement = (PreparedStatement) connection.prepareStatement(UPDATE_HEALTH_QUERY);
			int rowsUpdated = statement.executeUpdate();
			if (rowsUpdated > 0) {
			    System.out.println("Updated successfully!");
			}
			UPDATE_SET_VALUE = "";
			CONDITION = "";
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void updateWeatherTable(){
		try {
			connection = DriverManager.getConnection(jdbcURL, MySQLConfig.user, MySQLConfig.password);
			Scanner sc = new Scanner(System.in);
			
			System.out.println("Do you want to update (1) monthlyMax or (2) monthlyMin or (3) monthlyNormal?");
			int choice = Integer.parseInt(sc.nextLine());
			if(choice == 1){
				/* get update value */
				System.out.println("Enter new value for max temperature of the month: ");
				float monthlyMax = Float.parseFloat(sc.nextLine());
				UPDATE_SET_VALUE += monthlyMax;
				
				/* get condition */
				System.out.println("Enter city: ");
				String city = sc.nextLine();
				System.out.println("Enter year: ");
				int year = Integer.parseInt(sc.nextLine());		
				System.out.println("Enter month: ");
				int month = Integer.parseInt(sc.nextLine());	
				
				CONDITION = CONDITION + "city = " + city + ", year = " + year + ", month = " + month;
			}
			else if (choice == 2){
				/* get update value */
				System.out.println("Enter new value for min temperature of the month: ");
				float monthlyMin = Float.parseFloat(sc.nextLine());
				UPDATE_SET_VALUE += monthlyMin;
				
				/* get condition */
				System.out.println("Enter city: ");
				String city = sc.nextLine();
				System.out.println("Enter year: ");
				int year = Integer.parseInt(sc.nextLine());		
				System.out.println("Enter month: ");
				int month = Integer.parseInt(sc.nextLine());	
				
				CONDITION = CONDITION + "city = " + city + ", year = " + year + ", month = " + month;
			}	
			else if (choice == 3){
				/* get update value */
				System.out.println("Enter new value for normal temperature of the month: ");
				float monthlyNor = Float.parseFloat(sc.nextLine());
				UPDATE_SET_VALUE += monthlyNor;
				
				/* get condition */
				System.out.println("Enter city: ");
				String city = sc.nextLine();
				System.out.println("Enter year: ");
				int year = Integer.parseInt(sc.nextLine());		
				System.out.println("Enter month: ");
				int month = Integer.parseInt(sc.nextLine());	
				
				CONDITION = CONDITION + "city = " + city + ", year = " + year + ", month = " + month;
			}	
			
			PreparedStatement statement = (PreparedStatement) connection.prepareStatement(UPDATE_HEALTH_QUERY);
			int rowsUpdated = statement.executeUpdate();
			if (rowsUpdated > 0) {
			    System.out.println("Updated successfully!");
			}
			UPDATE_SET_VALUE = "";
			CONDITION = "";
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/* DELETE FUNCTION FOR HEALTH TABLE */
	public void deleteHealthTable(){
		try {
			connection = DriverManager.getConnection(jdbcURL, MySQLConfig.user, MySQLConfig.password);
			Scanner sc = new Scanner(System.in);
			String choice = "";
			boolean notFirst = false;
			
			/* zipcode, county, year, ageGroup, numberOfVisits */
			System.out.println("Want to delete data by zipcode? (Y/N):");
			choice = sc.nextLine();
			if(choice.equals("Y")){
				System.out.println("Enter which zipcode you want to delete: ");
				int zipcode = Integer.parseInt(sc.nextLine());
				DELETE_HEALTH_QUERY += "zipcode =" + zipcode;
				notFirst = true;
			}
			System.out.println("Want to delete data by county? (Y/N):");
			choice = sc.nextLine();
			if(choice.equals("Y")){
				System.out.println("Enter which county you want to delete: ");
				String county = sc.nextLine();
				if(notFirst == true) {DELETE_HEALTH_QUERY += ", ";} else {notFirst = true;}
					DELETE_HEALTH_QUERY += "county = \"" + county + "\"";
				
			}
			System.out.println("Want to delete data by year? (Y/N):");
			choice = sc.nextLine();
			if(choice.equals("Y")){
				System.out.println("Enter which zipcode you want to delete: ");
				int year = Integer.parseInt(sc.nextLine());
				if(notFirst == true) {DELETE_HEALTH_QUERY += ", ";} else {notFirst = true;}
					DELETE_HEALTH_QUERY += "year =" + year;
			}
			System.out.println("Want to delete data by ageGroup? (Y/N):");
			choice = sc.nextLine();
			if(choice.equals("Y")){
				System.out.println("Enter which zipcode you want to delete: ");
				String ageGroup = sc.nextLine();
				if(notFirst == true) {DELETE_HEALTH_QUERY += ", ";} else {notFirst = true;}
					DELETE_HEALTH_QUERY += "agegroup = \"" + ageGroup + "\"";
			}
			System.out.println("Want to delete data by numOfVisits? (Y/N):");
			choice = sc.nextLine();
			if(choice.equals("Y")){
				System.out.println("Enter which zipcode you want to delete: ");
				int numOfVisits = Integer.parseInt(sc.nextLine());
				if(notFirst == true) {DELETE_HEALTH_QUERY += ", ";} else {notFirst = true;}
					DELETE_HEALTH_QUERY += "numberOfVisits =" + numOfVisits;
			}
			
			PreparedStatement stmt = (PreparedStatement) connection.prepareStatement(DELETE_HEALTH_QUERY);
			int rowsInserted = stmt.executeUpdate();
			if (rowsInserted > 0) {
			    System.out.println("The row has been deleted in health table");
			}
			DELETE_HEALTH_QUERY = "delete from health where ";
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/* DELETE FUNCTION FOR REGION TABLE */
	public void deleteRegionTable(){
		try {
			connection = DriverManager.getConnection(jdbcURL, MySQLConfig.user, MySQLConfig.password);
			Scanner sc = new Scanner(System.in);
			String choice = "";
			boolean notFirst = false;
			
			/* county, zipCode, city, state */
			System.out.println("Want to delete data by county? (Y/N):");
			choice = sc.nextLine();
			if(choice.equals("Y")){
				System.out.println("Enter which county you want to delete: ");
				String county = sc.nextLine();
				DELETE_REGION_QUERY += "county = \"" + county + "\"";
				notFirst = true;
			}
			System.out.println("Want to delete data by zipcode? (Y/N):");
			choice = sc.nextLine();
			if(choice.equals("Y")){
				System.out.println("Enter which zipcode you want to delete: ");
				int zipCode = Integer.parseInt(sc.nextLine());
				if(notFirst == true) {DELETE_REGION_QUERY += ", ";} else {notFirst = true;}
					DELETE_REGION_QUERY += "zipcode =" + zipCode;
			}
			System.out.println("Want to delete data by city? (Y/N):");
			choice = sc.nextLine();
			if(choice.equals("Y")){
				System.out.println("Enter which city you want to delete: ");
				String city = sc.nextLine();
				if(notFirst == true) {DELETE_REGION_QUERY += ", ";} else {notFirst = true;}
					DELETE_REGION_QUERY += "city = \"" + city + "\"";
			}
			System.out.println("Want to delete data by state? (Y/N):");
			choice = sc.nextLine();
			if(choice.equals("Y")){
				System.out.println("Enter which state you want to delete: ");
				String state = sc.nextLine();
				if(notFirst == true) {DELETE_REGION_QUERY += ", ";} else {notFirst = true;}
					DELETE_REGION_QUERY += "state = \"" + state + "\"";
			}
			PreparedStatement stmt = (PreparedStatement) connection.prepareStatement(DELETE_REGION_QUERY);
			System.out.println(stmt);
			int rowsInserted = stmt.executeUpdate();
			if (rowsInserted > 0) {
			    System.out.println("The row has been deleted in region table");
			}
			DELETE_REGION_QUERY = "delete from region where ";
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/* DELETE FUNCTION FOR WEATHER TABLE */
	public void deleteWeatherTable(){
		try {
			connection = DriverManager.getConnection(jdbcURL, MySQLConfig.user, MySQLConfig.password);
			Scanner sc = new Scanner(System.in);
			String choice = "";
			boolean notFirst = false;
			
			/* city, year, month, monthlyMax, monthlyMin, monthlyNor */
			System.out.println("Want to delete data by city? (Y/N):");
			choice = sc.nextLine();
			if(choice.equals("Y")){
				System.out.println("Enter which city you want to delete: ");
				String city = sc.nextLine();
				DELETE_WEATHER_QUERY += "city = \"" + city + "\"";
				notFirst = true;
			}
			System.out.println("Want to delete data by year? (Y/N):");
			choice = sc.nextLine();
			if(choice.equals("Y")){
				System.out.println("Enter which year you want to delete: ");
				int year = Integer.parseInt(sc.nextLine());
				if(notFirst == true) {DELETE_WEATHER_QUERY += ", ";} else {notFirst = true;}
					DELETE_WEATHER_QUERY += "year =" + year;
				
			}
			System.out.println("Want to delete data by month? (Y/N):");
			choice = sc.nextLine();
			if(choice.equals("Y")){
				System.out.println("Enter which city you want to delete: ");
				int month = Integer.parseInt(sc.nextLine());
				if(notFirst == true) {DELETE_WEATHER_QUERY += ", ";} else {notFirst = true;}
					DELETE_WEATHER_QUERY += "month =" + month;
			}
			System.out.println("Want to delete data by monthly max temperature? (Y/N):");
			choice = sc.nextLine();
			if(choice.equals("Y")){
				System.out.println("Enter which max temperature you want to delete: ");
				Float monthlyMax = Float.parseFloat(sc.nextLine());
				if(notFirst == true) {DELETE_WEATHER_QUERY += ", ";} else {notFirst = true;}
					DELETE_WEATHER_QUERY += "MonthlyMax =" + monthlyMax;
			}
			System.out.println("Want to delete data by monthly min temperature? (Y/N):");
			choice = sc.nextLine();
			if(choice.equals("Y")){
				System.out.println("Enter which min temperature you want to delete: ");
				Float monthlyMin = Float.parseFloat(sc.nextLine());
				if(notFirst == true) {DELETE_WEATHER_QUERY += ", ";} else {notFirst = true;}
					DELETE_WEATHER_QUERY += "MonthlyMin =" + monthlyMin;
			}
			System.out.println("Want to delete data by monthly normal temperature? (Y/N):");
			choice = sc.nextLine();
			if(choice.equals("Y")){
				System.out.println("Enter which normal temperature you want to delete: ");
				Float monthlyNor = Float.parseFloat(sc.nextLine());
				if(notFirst == true) {DELETE_WEATHER_QUERY += ", ";} else {notFirst = true;}
					DELETE_WEATHER_QUERY += "monthlyNor =" + monthlyNor;
			}
			PreparedStatement stmt = (PreparedStatement) connection.prepareStatement(DELETE_WEATHER_QUERY);
			int rowsInserted = stmt.executeUpdate();
			if (rowsInserted > 0) {
			    System.out.println("The row has been deleted in weather table");
			}
			DELETE_WEATHER_QUERY = "delete from weather where ";
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}


