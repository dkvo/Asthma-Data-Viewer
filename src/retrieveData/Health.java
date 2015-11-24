package retrieveData;

public class Health {
	private int zipCode;
	private int year;
	private int numberOfVisits;
	private String ageGroup;
	private String county;
	private String city;
	private String state;

	/*Constructors*/
	public Health(){
		zipCode = 0;
		year = 0;
		numberOfVisits = 0;
		ageGroup = "";
		county ="";
		city = "";
		state = "";
	}
	
	public Health(int zipCode, int year, int numberOfVisits, String ageGroup, String country){
		this.zipCode = zipCode;
		this.year = year;
		this.numberOfVisits = numberOfVisits;
		this.ageGroup = ageGroup;
		this.county = country;
	}

	/*Getter*/
	public int getZipCode(){return zipCode;}
	public int getYear(){return year;}
	public int getNumOfVisits(){return numberOfVisits;}
	public String getAgeGroup(){return ageGroup;}
	public String getCounty(){return county;}
	public String getCity(){return city;}
	public String getState(){return state;}
	
	/*Setter*/
	public void setZipCode(int zc){zipCode = zc;}
	public void setYear(int year){this.year = year;}
	public void setNumOfVisits(int num){numberOfVisits = num;}
	public void setAgeGroup(String group){ageGroup = group;}
	public void setCounty(String county){this.county = county;}
	public void setCity(String city){this.city = city;}
	public void setState(String state){this.state = county;}
	
}
