package dataGUI;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import retrieveData.Health;

import javax.swing.JTabbedPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

public class ShowSearch extends JFrame {

	String[] columnNames = {"zipcode", "county", "city", "state", "year", "month","ageGroup",
            "numOfVisits", "MonthlyMax", "MonthlyMin", "MonthlyNor"};
	private DefaultTableModel model;
	
	private JPanel contentPane;
	private JTextField textField;
	Health health = new Health();


	/**
	 * Create the frame.
	 * @param health 
	 */
	public ShowSearch(Health health) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 800, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		this.health = health;
		textField = new JTextField();
		textField.setBounds(0, 32, 794, 26);
		contentPane.add(textField);
		textField.setColumns(10);
		
		textField.setText(Integer.toString(health.getZipCode()) +  " | " +health.getCounty() +  " | " + health.getCity() +  " | " + health.getState() +  " | " + Integer.toString(health.getYear()) +  " | " +
				Integer.toString(health.getMonth()) +  " | " + health.getAgeGroup() +  " | " + Integer.toString(health.getNumOfVisits()) +  " | " + Float.toString(health.getMMax()) +  " | " + Float.toString(health.getMMin()) +  " | " + Float.toString(health.getMNor()));
		
	}
}
