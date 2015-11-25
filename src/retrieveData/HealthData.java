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
	private static final String SELECT_ALL_QUERY = "select health.zipcode, health.county, year, ageGroup, "
			+ "numberofvisits, city, state from health " + "inner join region "
			+ "on health.zipcode = region.zipcode;";
	private static final String SELECT_HEALTH_QUERY = "select * from health";
	private static final String SELECT_REGION_QUERY = "select * from region";
	private static final String SELECT_WEATHER_QUERY = "select * from weather";
	private static final String INSERT_HEALTH_QUERY = "insert into health (zipcode, county, year, ageGroup, numberOfVisits) values (?, ?, ?, ?, ?)";
	private static final String INSERT_REGION_QUERY = "insert into region (county, zipcode, city, state) values (?,?,?,?)";
	private static final String INSERT_WEATHER_QUERY = "insert into weather (city, year, month, monthlyMax, monthlyMin, monthlyNor) values (?,?,?,?,?,?)";
	private static String DELETE_HEALTH_QUERY = "delete from health where ";
	private static String DELETE_REGION_QUERY = "delete from region where ";
	private static String DELETE_WEATHER_QUERY = "delete from weather where ";
	private ArrayList<Health> data;
	Connection connection = null;
	Statement statement = null;
	
	public HealthData(){
		try {
			/* Connect to database */
	        connection = DriverManager.getConnection(jdbcURL, MySQLConfig.user, MySQLConfig.password);
	        statement = connection.createStatement();
	        data = new ArrayList<Health>();
	        
	        /* Retrieve Data */
	        /*
	        System.out.println("Connected to the database");
	        ResultSet rs = statement.executeQuery(SELECT_ALL_QUERY);
	        while (rs.next())
	        {
	        	Health temp = new Health();
	            temp.setZipCode(rs.getInt("zipCode"));
	            temp.setAgeGroup(rs.getString("ageGroup"));
	            temp.setCounty(rs.getString("county"));
	            temp.setNumOfVisits(rs.getInt("numberOfVisits"));
	            temp.setYear(rs.getInt("year"));
	            temp.setCity(rs.getString("city"));
	            temp.setState(rs.getString("state"));
	            data.add(temp);
	        }    
	        
	        System.out.println("Done.");
	        */
	    } catch (Exception e)
	    {
	        e.printStackTrace();
	    }
	}
	
	public ArrayList<Health> getData(){return data;}
	
	/* INSERT FUNCTION FOR HEALTH TABLE */
	public void insertHealthTable() throws SQLException{
		Scanner sc = new Scanner(System.in);
	
		System.out.println("Enter your input below.");
		System.out.println("Enter zipCode:");
		int zipCode = Integer.parseInt(sc.nextLine());
		System.out.println("Enter county: ");
		String county = sc.nextLine();
		System.out.println("Enter year: ");
		int year = Integer.parseInt(sc.nextLine());
		
		System.out.println("Enter group of age(1 for \"AllAges\" / 2 for \"Children (0-17)\" / 3 for \"Adult (18+)\"): ");
		int input = sc.nextInt();
		String ageGroup;
		if (input == 1) ageGroup = "AllAges";
		else if (input == 2) ageGroup = "Children (0-17)";
		else if (input == 3) ageGroup = "Adult (18+)";
		else ageGroup = "AllAges";
		
		System.out.println("Enter number of visits: ");
		int numOfVisits = Integer.parseInt(sc.nextLine());
		
		/* Insert data into health table : zipcode, county, year, ageGroup, numberOfVisits */
		PreparedStatement stmt = (PreparedStatement) connection.prepareStatement(INSERT_HEALTH_QUERY);
		stmt.setInt(1, zipCode);
		stmt.setString(2, county);
		stmt.setInt(3, year);
		stmt.setString(4, ageGroup);
		stmt.setInt(5, numOfVisits);
		
		int rowsInserted = stmt.executeUpdate();
		if (rowsInserted > 0) {
		    System.out.println("A new row is added in health table");
		}
	}
	
	/* INSERT FUNCTION FOR REGION TABLE */
	public void insertRegionTable() throws SQLException{
		Scanner sc = new Scanner(System.in);
	
		System.out.println("Enter your input below.");
		System.out.println("Enter county: ");
		String county = sc.nextLine();
		System.out.println("Enter zipCode:");
		int zipCode = Integer.parseInt(sc.nextLine());
		System.out.println("Enter city: ");
		String city = sc.nextLine();
		System.out.println("Enter state: ");
		String state = sc.nextLine();
		
		
		/* Insert data into region table : county, zipCode, city, state */
		PreparedStatement stmt = (PreparedStatement) connection.prepareStatement(INSERT_REGION_QUERY);
		stmt.setString(1, county);
		stmt.setInt(2, zipCode);
		stmt.setString(3, city);
		stmt.setString(4, state);
		
		int rowsInserted = stmt.executeUpdate();
		if (rowsInserted > 0) {
		    System.out.println("A new row is added in health table");
		}
	}
	
	/* INSERT FUNCTION FOR WEATHER TABLE */
	public void insertWeatherTable() throws SQLException{
		Scanner sc = new Scanner(System.in);
	
		System.out.println("Enter your input below.");
		System.out.println("Enter city: ");
		String city = sc.nextLine();
		System.out.println("Enter year:");
		int year = Integer.parseInt(sc.nextLine());
		System.out.println("Enter month:");
		int month = Integer.parseInt(sc.nextLine());
		System.out.println("Enter monthly max temperature:");
		float monthlyMax = Float.parseFloat(sc.nextLine());
		System.out.println("Enter monthly min temperature:");
		float monthlyMin = Float.parseFloat(sc.nextLine());
		System.out.println("Enter monthly average temperature:");
		float monthlyNor = Float.parseFloat(sc.nextLine());
		
		
		/* Insert data into region table : city, year, month, monthlyMax, monthlyMin, monthlyNor */
		PreparedStatement stmt = (PreparedStatement) connection.prepareStatement(INSERT_WEATHER_QUERY);
		stmt.setString(1, city);
		stmt.setInt(2, year);
		stmt.setInt(3, month);
		stmt.setFloat(4, monthlyMax);
		stmt.setFloat(5, monthlyMin);
		stmt.setFloat(6, monthlyNor);
		
		int rowsInserted = stmt.executeUpdate();
		if (rowsInserted > 0) {
		    System.out.println("A new row is added in health table");
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
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/* SELECT FUNCTION FOR HEALTH TABLE */
	public void selectHealthTable(){
		try {
			connection = DriverManager.getConnection(jdbcURL, MySQLConfig.user, MySQLConfig.password);
			Statement stmt = connection.createStatement();
			ResultSet result = stmt.executeQuery(SELECT_HEALTH_QUERY);
			
			/* Get and print out data from health table : zipcode, county, year, ageGroup, numberOfVisits */
			while(result.next()){
				int zipcode = result.getInt(1);
				String county = result.getString(2);
				int year = result.getInt(3);
				String ageGroup = result.getString(4);
				int numOfVisits = result.getInt(5);
				
				System.out.println(zipcode + " , " + county + " , " + year + " , " + ageGroup + " , " + numOfVisits);
			}	
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
	/* SELECT FUNCTION FOR REGION TABLE */
	public void selectRegionTable(){
		try {
			connection = DriverManager.getConnection(jdbcURL, MySQLConfig.user, MySQLConfig.password);
			Statement stmt = connection.createStatement();
			ResultSet result = stmt.executeQuery(SELECT_REGION_QUERY);
			
			/* Get and print out data from region table : county, zipCode, city, state */
			while(result.next()){
				String county = result.getString(1);
				int zipcode = result.getInt(2);
				String city = result.getString(3);
				String state = result.getString(4);
				
				System.out.println(county + " , " + zipcode + " , " + city + " , " + state);
			}	
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/* SELECT FUNCTION FOR WEATHER TABLE */
	public void selectWeatherTable(){
		try {
			connection = DriverManager.getConnection(jdbcURL, MySQLConfig.user, MySQLConfig.password);
			Statement stmt = connection.createStatement();
			ResultSet result = stmt.executeQuery(SELECT_WEATHER_QUERY);
			
			/* Get and print out data from weather table : city, year, month, monthlyMax, monthlyMin, monthlyNor */
			while(result.next()){
				String city = result.getString(1);
				int year = result.getInt(2);
				int month = result.getInt(3);
				float monthlyMax = result.getFloat(4);
				float monthlyMin = result.getFloat(5);
				float monthlyNor = result.getFloat(6);
				
				System.out.println(city + " , " + year + " , " + month + " , " + monthlyMax + " , " + monthlyMin + " , " + monthlyNor);
			}	
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
