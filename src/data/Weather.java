package data;

/**
 * Created by christopherbachner on 11/12/15.
 */
public class Weather {

    //STATION,STATION_NAME,ELEVATION,LATITUDE,LONGITUDE,DATE,MLY-TMIN-NORMAL,MLY-TMAX-NORMAL,MLY-PRCP-NORMAL
    //GHCND:USC00327027,PETERSBURG 2 N ND US,466.3,48.0355,-98.01,201001,-43,145,55

    private String location = null;
    private String date = null;
    private float monthlyMax = null;
    private float monthlyMin = null;

    public Weather(String location, String date, float monthlyMax, float monthlyMin) {
        this.location = location;
        this.date = date;
        this.monthlyMax = monthlyMax;
        this.monthlyMin = monthlyMin;
    }
}
