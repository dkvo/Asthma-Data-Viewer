package dataGUI;

import javax.swing.JButton;

public class Connector extends JButton{
	WeatherFrame weather;
	//HealthFrame health;
	//RegionFrame region;
	boolean wIsOpen;
	boolean hIsOpen;
	boolean rIsOpen;
	
	public Connector() {
	    weather = new WeatherFrame();
	    //health = new healthFrame();
	    //region = new RegionFrame();
	    
	    weather.setVisible(true);
	    //health.setVisible(false);
	    // region.setVisible(false);
	    wIsOpen = true;
	    hIsOpen = false;
	    rIsOpen = false;
	}
	
	public void showWeatherFrame() {
		if(wIsOpen = false) {
			weather.setVisible(true);
		    //health.setVisible(false);
		    // region.setVisible(false);
		}
	}
	
	public void showHealthFrame() {
		if(hIsOpen = false) {
			weather.setVisible(false);
		    //health.setVisible(true);
		    // region.setVisible(false);
		}
	}
	
	public void showRegionFrame() {
		if(rIsOpen = false) {
			weather.setVisible(false);
		    //health.setVisible(false);
		    // region.setVisible(true);
		}
	}

}
