package dataGUI;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import net.miginfocom.swing.MigLayout;
import retrieveData.Health;
import retrieveData.HealthData;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.awt.event.ActionEvent;

public class InsertFrame extends JFrame {
	
	private JPanel contentPane;
	private JTextField zipcodeText;
	private JTextField countyText;
	private JTextField cityText;
	private JTextField stateText;
	private JTextField yearText;
	private JTextField monthText;
	private JTextField agegroupText;
	private JLabel numvisitLabel;
	private JTextField numvisitText;
	private JLabel maxLabel;
	private JTextField maxText;
	private JLabel minLabel;
	private JLabel norLabel;
	private JTextField minText;
	private JTextField norText;
	private JButton btnSubmit;
	Health health= new Health();
	HealthData healthData = new HealthData();

	

	/**
	 * Create the frame.
	 */
	public InsertFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 500, 225);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new MigLayout("", "[][grow][]", "[][][][][][]"));
		
		JLabel zipcodeLabel = new JLabel("Zipcode");
		contentPane.add(zipcodeLabel, "cell 0 0,alignx left");
		
		zipcodeText = new JTextField();
		contentPane.add(zipcodeText, "flowx,cell 1 0,alignx left");
		zipcodeText.setColumns(10);
		
		JLabel countyLabel = new JLabel("County");
		contentPane.add(countyLabel, "cell 0 1,alignx left");
		
		countyText = new JTextField();
		contentPane.add(countyText, "flowx,cell 1 1,alignx left");
		countyText.setColumns(10);
		
		JLabel cityLabel = new JLabel("City");
		contentPane.add(cityLabel, "cell 0 2,alignx left");
		
		cityText = new JTextField();
		contentPane.add(cityText, "flowx,cell 1 2,alignx left");
		cityText.setColumns(10);
		
		JLabel stateLabel = new JLabel("State");
		contentPane.add(stateLabel, "cell 0 3,alignx left");
		
		stateText = new JTextField();
		contentPane.add(stateText, "flowx,cell 1 3,alignx left");
		stateText.setColumns(10);
		
		JLabel yearLabel = new JLabel("year");
		contentPane.add(yearLabel, "cell 0 4,alignx left");
		
		yearText = new JTextField();
		contentPane.add(yearText, "flowx,cell 1 4,alignx left");
		yearText.setColumns(10);
		
		JLabel monthLabel = new JLabel("Month");
		contentPane.add(monthLabel, "cell 0 5,alignx left");
		
		monthText = new JTextField();
		contentPane.add(monthText, "flowx,cell 1 5,alignx left");
		monthText.setColumns(10);
		
		JLabel agegroupLabel = new JLabel("AgeGroup");
		contentPane.add(agegroupLabel, "cell 1 0");
		
		agegroupText = new JTextField();
		contentPane.add(agegroupText, "cell 1 0,alignx left");
		agegroupText.setColumns(10);
		
		numvisitLabel = new JLabel("NumVisits");
		contentPane.add(numvisitLabel, "cell 1 1");
		
		numvisitText = new JTextField();
		contentPane.add(numvisitText, "cell 1 1,alignx left");
		numvisitText.setColumns(10);
		
		maxLabel = new JLabel("MonthlyMax");
		contentPane.add(maxLabel, "cell 1 2");
		
		maxText = new JTextField();
		contentPane.add(maxText, "cell 1 2,alignx left");
		maxText.setColumns(10);
		
		minLabel = new JLabel("MonthlyMin");
		contentPane.add(minLabel, "cell 1 3");
		
		norLabel = new JLabel("MonthlyNor");
		contentPane.add(norLabel, "cell 1 4");
		
		minText = new JTextField();
		contentPane.add(minText, "cell 1 3");
		minText.setColumns(10);
		
		norText = new JTextField();
		contentPane.add(norText, "cell 1 4,alignx left");
		norText.setColumns(10);
		
		btnSubmit = new JButton("Submit");
		btnSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(validation())
				{
					int zipcode = Integer.parseInt(zipcodeText.getText()); 
					int year = Integer.parseInt(yearText.getText()); 
					int month = Integer.parseInt(monthText.getText()); 
					int numvisit = Integer.parseInt(numvisitText.getText());
					float max = Float.parseFloat(maxText.getText()); 
					float min = Float.parseFloat(minText.getText()); 
					float nor = Float.parseFloat(norText.getText()); 
					String county = countyText.getText();
					String city = cityText.getText();
					String state = stateText.getText();
					String ageGroup = agegroupText.getText();
					
					health.setZipCode(zipcode);
					health.setCity(city);
					health.setYear(year);
					health.setState(state);
					health.setAgeGroup(ageGroup);
					health.setCounty(county);
					health.setMonth(month);
					health.setNumOfVisits(numvisit);
					health.setMMax(max);
					health.setMMin(min);
					health.setMNor(nor);
					
					try {
						healthData.insertData(health);
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					setVisible(false);
				}
			}
		});
		contentPane.add(btnSubmit, "cell 1 5,alignx right");
	}



	protected boolean validation() {
		boolean state = false;
		if(zipcodeText != null)
			if(isDigit("zipcode"))
				state = true;
		if(yearText != null)
			if(isDigit("year"))
				state = true;
		if(monthText != null)
			if(isDigit("month"))
				state = true;
		if(numvisitText != null)
			if(isDigit("numvisit"))
				state = true;
		if(maxText != null)
			if(isDigit("max"))
				state = true;
		if(minText != null)
			if(isDigit("min"))
				state = true;
		if(norText != null)
			if(isDigit("nor"))
				state = true;
		if(!(countyText.getText().trim().length() == 0))
			return state = true;
		else {
			JOptionPane.showMessageDialog(null, "Country field is empty.", "Error", JOptionPane.ERROR_MESSAGE);
			new InsertFrame();
		}
		
		if(!(cityText.getText().trim().length() == 0))
			return state = true;
		else {
			JOptionPane.showMessageDialog(null, "City field is empty.", "Error", JOptionPane.ERROR_MESSAGE);
			new InsertFrame();
		}
		
		if(!(stateText.getText().trim().length() == 0))
			return state = true;
		else {
			JOptionPane.showMessageDialog(null, "State field is empty.", "Error", JOptionPane.ERROR_MESSAGE);
			new InsertFrame();
		}
		
		if(!(agegroupText.getText().trim().length() == 0))
			return state = true;
		else {
			JOptionPane.showMessageDialog(null, "AgeGroup field is empty.", "Error", JOptionPane.ERROR_MESSAGE);
			new InsertFrame();
		}
		return state;		
	}



	private boolean isDigit(String s) {
		if(s == "zipcode")
			
		try {
			int zipcode = Integer.parseInt(zipcodeText.getText()); 
		}catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(null, "Zipcode entered is invalid", "Error", JOptionPane.ERROR_MESSAGE);
			new InsertFrame();
			return false;
		}
		
		if(s == "year")
			
			try {
				int year = Integer.parseInt(yearText.getText()); 
			}catch (NumberFormatException e) {
				JOptionPane.showMessageDialog(null, "Year entered is invalid", "Error", JOptionPane.ERROR_MESSAGE);
				new InsertFrame();
				return false;
			}
			if(s == "month")
			
				try {
					int month = Integer.parseInt(monthText.getText()); 
				}catch (NumberFormatException e) {
					JOptionPane.showMessageDialog(null, "Month entered is invalid", "Error", JOptionPane.ERROR_MESSAGE);
					new InsertFrame();
					return false;
				}
			if(s == "numvisit")
				
				try {
					int numvisit = Integer.parseInt(numvisitText.getText()); 
				}catch (NumberFormatException e) {
					JOptionPane.showMessageDialog(null, "Numvisit entered is invalid", "Error", JOptionPane.ERROR_MESSAGE);
					new InsertFrame();
					return false;
				}
			if(s == "max")
				
				try {
					float max = Float.parseFloat(maxText.getText()); 
				}catch (NumberFormatException e) {
					JOptionPane.showMessageDialog(null, "MonthlyMax entered is invalid", "Error", JOptionPane.ERROR_MESSAGE);
					new InsertFrame();
					return false;
				}
			if(s == "min")
				
				try {
					float min = Float.parseFloat(minText.getText()); 
				}catch (NumberFormatException e) {
					JOptionPane.showMessageDialog(null, "MonthlyMin entered is invalid", "Error", JOptionPane.ERROR_MESSAGE);
					new InsertFrame();
					return false;
				}
			if(s == "nor")
					
				try {
					float nor = Float.parseFloat(norText.getText()); 
				}catch (NumberFormatException e) {
					JOptionPane.showMessageDialog(null, "MonthlyNor entered is invalid", "Error", JOptionPane.ERROR_MESSAGE);
					new InsertFrame();
					return false;
				}
		return true;
	}

}
