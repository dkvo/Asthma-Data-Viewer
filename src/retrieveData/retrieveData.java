package retrieveData;
import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

import config.MySQLConfig;

public class retrieveData implements MySQLConfig{
	
	
	public static void main(String[] args) throws SQLException {
		HealthData data = new HealthData();
		
		Scanner sc = new Scanner(System.in);
		System.out.println("1 = INSERT / 2 = DELETE / 3 = SELECT");
		int choice = Integer.parseInt(sc.nextLine());
		while(choice != 0){
			if(choice == 1){
				System.out.println("1 = HEALTH / 2=REGION / 3=WEATHER");
				int c = Integer.parseInt(sc.nextLine());
				if(c == 1) 
					data.insertHealthTable();
				else if (c == 2) 
					data.insertRegionTable();
				else 
					data.insertWeatherTable();
			}
			else if(choice == 2){
				System.out.println("1 = HEALTH / 2=REGION / 3=WEATHER");
				int c = Integer.parseInt(sc.nextLine());
				if(c == 1) 
					data.deleteHealthTable();
				else if (c == 2) 
					data.deleteRegionTable();
				else 
					data.deleteWeatherTable();
			}
			else if(choice == 3){
				System.out.println("1 = HEALTH / 2=REGION / 3=WEATHER");
				int c = Integer.parseInt(sc.nextLine());
				if(c == 1) 
					data.selectHealthTable();
				else if (c == 2) 
					data.selectRegionTable();
				else 
					data.selectWeatherTable();
			}
			System.out.println("1 = INSERT / 2 = DELETE / 3 = SELECT");
			choice = Integer.parseInt(sc.nextLine());
		}
	}
	
}
       
