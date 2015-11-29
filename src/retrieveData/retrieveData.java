package retrieveData;
import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

import config.MySQLConfig;

public class retrieveData implements MySQLConfig{
	
	
	public static void main(String[] args) throws SQLException {
		HealthData data = new HealthData();
		Health element = new Health();
		
		Scanner sc = new Scanner(System.in);
		System.out.println("1 = INSERT / 2 = DELETE / 3 = SELECT / 4 = UPDATE");
		int choice = Integer.parseInt(sc.nextLine());
		while(choice != 0){
			if(choice == 1){
				System.out.println("Enter zipcode: ");
				//int zipcode = Integer.parseInt(sc.nextLine());
				element.setZipCode(Integer.parseInt(sc.nextLine()));
				System.out.println("Enter group of age(1 for \"AllAges\" / 2 for \"Children (0-17)\" / 3 for \"Adult (18+)\"): ");
				int input = Integer.parseInt(sc.nextLine());
				String ageGroup;
				if (input == 1) ageGroup = "AllAges";
				else if (input == 2) ageGroup = "Children (0-17)";
				else if (input == 3) ageGroup = "Adult (18+)";
				else ageGroup = "AllAges";
				element.setAgeGroup(ageGroup);
				
				System.out.println("Enter county: ");
				String county = sc.nextLine();
				element.setCounty(county);
				
				System.out.println("Enter city: ");
				String city = sc.nextLine();
				element.setCity(city);
				
				System.out.println("Enter state: ");
				String state = sc.nextLine();
				element.setState(state);
				
				System.out.println("Enter year: ");
				int year = Integer.parseInt(sc.nextLine());
				element.setYear(year);
				
				System.out.println("Enter month:");
				int month = Integer.parseInt(sc.nextLine());
				element.setMonth(month);
				
				System.out.println("Enter number of visits: ");
				int numOfVisits = Integer.parseInt(sc.nextLine());
				element.setNumOfVisits(numOfVisits);
				
				System.out.println("Enter monthly max temperature:");
				float monthlyMax = Float.parseFloat(sc.nextLine());
				element.setMMax(monthlyMax);
				
				System.out.println("Enter monthly min temperature:");
				float monthlyMin = Float.parseFloat(sc.nextLine());
				element.setMMin(monthlyMin);
				
				System.out.println("Enter monthly average temperature:");
				float monthlyNor = Float.parseFloat(sc.nextLine());
				element.setMNor(monthlyNor);

				data.insertData(element);
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
				data.showAllData();
			}
			else if(choice == 4){
				System.out.println("1 = HEALTH / 2 = WEATHER");
				int c = Integer.parseInt(sc.nextLine());
				if(c == 1) 
					data.updateHealthTable();
				else if (c == 2) 
					data.updateWeatherTable();
			}
			System.out.println("1 = INSERT / 2 = DELETE / 3 = SELECT");
			choice = Integer.parseInt(sc.nextLine());
		}
	}
	
}
       
