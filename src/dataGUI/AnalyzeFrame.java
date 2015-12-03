package dataGUI;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridLayout;
import net.miginfocom.swing.MigLayout;
import retrieveData.Health;
import retrieveData.HealthData;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class AnalyzeFrame extends JFrame {

	private JPanel contentPane;
	private JTextField zipcodeText;
	private JTextField yearText;
	private JTextField cityText;
	private JTextField norText;
	private JButton btnSubmit;
	Health health = new Health();
	HealthData data = new HealthData();


	/**
	 * Create the frame.
	 */
	public AnalyzeFrame() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 300, 200);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new MigLayout("", "[][grow]", "[][][][][]"));
		
		JLabel lblNewLabel = new JLabel("Zipcode");
		contentPane.add(lblNewLabel, "cell 0 0,alignx left");
		
		zipcodeText = new JTextField();
		contentPane.add(zipcodeText, "cell 1 0,alignx left");
		zipcodeText.setColumns(10);
		
		JLabel lblYear = new JLabel("Year");
		contentPane.add(lblYear, "cell 0 1,alignx left");
		
		yearText = new JTextField();
		contentPane.add(yearText, "cell 1 1,alignx left");
		yearText.setColumns(10);
		
		JLabel lblCity = new JLabel("City");
		contentPane.add(lblCity, "cell 0 2,alignx left");
		
		cityText = new JTextField();
		contentPane.add(cityText, "cell 1 2,alignx left");
		cityText.setColumns(10);
		
		JLabel lblMonthlynor = new JLabel("MonthlyNor");
		contentPane.add(lblMonthlynor, "cell 0 3,alignx trailing");
		
		norText = new JTextField();
		contentPane.add(norText, "cell 1 3,alignx left");
		norText.setColumns(10);
		
		btnSubmit = new JButton("Submit");
		btnSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(validation())
				{
					int zipcode = Integer.parseInt(zipcodeText.getText()); 
					int year = Integer.parseInt(yearText.getText()); 
					float nor = Float.parseFloat(norText.getText()); 

					String city = cityText.getText();
					
					health.setZipCode(zipcode);
					health.setCity(city);
					health.setYear(year);
					health.setMNor(nor);
					ShowAnalyze analyze = new ShowAnalyze(data.analyze(health));
					setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				}
			}
		});
		contentPane.add(btnSubmit, "cell 1 4,alignx center");
	}

	protected boolean validation() {
		boolean state = false;
		if(zipcodeText != null)
			if(isDigit("zipcode"))
				state = true;
		if(yearText != null)
			if(isDigit("year"))
				state = true;
		if(norText != null)
			if(isDigit("nor"))
				state = true;
		
		if(!(cityText.getText().trim().length() == 0))
			return state = true;
		else {
			JOptionPane.showMessageDialog(null, "City field is empty.", "Error", JOptionPane.ERROR_MESSAGE);
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
