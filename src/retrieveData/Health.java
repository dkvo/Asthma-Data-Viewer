package retrieveData;

public class Health {
	private int zipCode;
	private int year;
	private int numberOfVisits;
	private String ageGroup;
	private String county;
	private String city;
	private String state;
	private int month;
	private float monthlyMax;
	private float monthlyMin;
	private float monthlyNor;

	/*Constructors*/
	public Health(){
		zipCode = 0;
		year = 0;
		numberOfVisits = 0;
		ageGroup = "";
		county ="";
		city = "";
		state = "";
		monthlyMax = 0;
		monthlyMin = 0;
		monthlyNor = 0;
	}
	
	public Health(int zipCode, int year, int numberOfVisits, String ageGroup, String country, String city, String state, int month, float max, float min, float nor){
		this.zipCode = zipCode;
		this.year = year;
		this.numberOfVisits = numberOfVisits;
		this.ageGroup = ageGroup;
		this.county = country;
		this.city = city;
		this.state = state;
		this.month = month;
		monthlyMax = max;
		monthlyMin = min;
		monthlyNor = nor;
	}

	/*Getter*/
	public int getZipCode(){return zipCode;}
	public int getYear(){return year;}
	public int getNumOfVisits(){return numberOfVisits;}
	public String getAgeGroup(){return ageGroup;}
	public String getCounty(){return county;}
	public String getCity(){return city;}
	public String getState(){return state;}
	public int getMonth(){return month;}
	public float getMMax(){return monthlyMax;}
	public float getMMin(){return monthlyMin;}
	public float getMNor(){return monthlyNor;}
	
	
	/*Setter*/
	public void setZipCode(int zc){zipCode = zc;}
	public void setYear(int year){this.year = year;}
	public void setNumOfVisits(int num){numberOfVisits = num;}
	public void setAgeGroup(String group){ageGroup = group;}
	public void setCounty(String county){this.county = county;}
	public void setCity(String city){this.city = city;}
	public void setState(String state){this.state = state;}
	public void setMonth(int month){this.month = month;}
	public void setMMax(float max){monthlyMax = max;}
	public void setMMin(float min){monthlyMin = min;}
	public void setMNor(float nor){monthlyNor = nor;}
	
}
