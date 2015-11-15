package retrieveData;
import java.sql.*;
import java.util.ArrayList;

public class retrieveData {
	public static void main(String[] args) {
		Connection conn = null;
		String url = "jdbc:mysql://localhost:3306/cs157";
		String driver = "com.mysql.jdbc.Driver";
		String userName = "root";
	    String password = "";
	    Statement stmt = null;
	    String SELECT_QUERY = "select * from health";  
	    
	    /*Lists store data retrieved from database*/
	    ArrayList<Health> healthList = new ArrayList<Health>();
	    try
        {
            Class.forName(driver);
            conn = DriverManager.getConnection(url, userName, password);
            stmt = conn.createStatement();
            System.out.println("Connected to the database");
            ResultSet rs = stmt.executeQuery(SELECT_QUERY);
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
            conn.close();
            System.out.println("Disconnected from database");
        } catch (Exception e)
        {
            e.printStackTrace();
        }
		
	}

}
       
