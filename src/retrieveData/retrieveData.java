package retrieveData;
import java.sql.*;
import java.util.ArrayList;

import config.MySQLConfig;

public class retrieveData implements MySQLConfig{
	public static void main(String[] args) {
		Connection connection = null;
		Statement statement = null;
		ArrayList<Health> healthList = new ArrayList<Health>();
		String SELECT_HEALTH_QUERY = "select * from health";
		 
		try {
            String jdbcURL = "jdbc:mysql://" + MySQLConfig.host + ":" + MySQLConfig.port + "/" + MySQLConfig.database;
            connection = DriverManager.getConnection(jdbcURL, MySQLConfig.user, MySQLConfig.password);
            statement = connection.createStatement();

            System.out.println("Connected to the database");
            ResultSet rs = statement.executeQuery(SELECT_HEALTH_QUERY);
            while (rs.next())
            {
            	Health healthData = new Health();
                healthData.setZipCode(rs.getInt("zipCode"));
                healthData.setAgeGroup(rs.getString("ageGroup"));
                healthData.setCounty(rs.getString("county"));
                healthData.setNumOfVisits(rs.getInt("numberOfVisits"));
                healthData.setYear(rs.getInt("year"));
                healthList.add(healthData);
            }
            connection.close();
            System.out.println("Disconnected from database");
        } catch (Exception e)
        {
            e.printStackTrace();
        }
		
	}

}
       
