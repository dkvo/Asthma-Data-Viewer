package retrieveData;
import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

import config.MySQLConfig;

public class retrieveData implements MySQLConfig{
	
	
	public static void main(String[] args) throws SQLException {
		HealthData data = new HealthData();
		Scanner sc = new Scanner(System.in);
		
		
		//System.out.println(data.getData().size());
		
	
			data.insert();
		
		
	}

}
       
