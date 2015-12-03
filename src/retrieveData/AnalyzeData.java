package retrieveData;

public class AnalyzeData {
	private String county;
	private int year;
	private float avgvisit;
	private float avg;
	public AnalyzeData ()
	{
		county = "";
		year = 0;
		avgvisit = 0;
		avg = 0;
	}
	
	public String getCounty(){return county;}
	public int getYear(){return year;}
	public float getAvgVisit(){return avgvisit;}
	public float getAVG(){return avg;}
	
	public void setCounty (String c)
	{
		county = c;
	}
	public void setYear (int y)
	{
		year = y;
	}
	public void setAvgVisit (Float a)
	{
		avgvisit= a;
	}
	public void setAVG (float b)
	{
		avg = b;
	}
}
