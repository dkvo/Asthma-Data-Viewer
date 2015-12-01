package dataGUI;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;


import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.event.ActionListener;

import java.awt.event.ActionEvent;

import retrieveData.HealthData;

public class WeatherFrame extends JFrame {
    
	private JPanel contentPane;
	private JTable table;
	HealthData health = new HealthData();
	private DefaultTableModel model;
	String[] columnNames = {"zipcode", "county", "city", "state", "year", "month","ageGroup",
			                "numOfVisits", "MonthlyMax", "MonthlyMin", "MonthlyNor"};

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					WeatherFrame frame = new WeatherFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public WeatherFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 300);
		contentPane = new JPanel();
		contentPane.setBounds(100, 100,750, 200);
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(6, 25, 788, 180);
		contentPane.add(scrollPane);
		
		model = new DefaultTableModel();
		populateTable();
		table = new JTable(model);
		
		scrollPane.setViewportView(table);
		
		JButton btnInsert = new JButton("insert");
		btnInsert.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnInsert.setBounds(315, 217, 117, 29);
		contentPane.add(btnInsert);
		
		JButton btnDelete = new JButton("delete");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnDelete.setBounds(560, 217, 117, 29);
		contentPane.add(btnDelete);
		
		JButton btnSearch = new JButton("search");
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnSearch.setBounds(436, 217, 117, 29);
		contentPane.add(btnSearch);
		
		JLabel lblWeatherTable = new JLabel("Weather Table");
		lblWeatherTable.setBounds(149, 6, 107, 16);
		contentPane.add(lblWeatherTable);
		
		JButton btnNext = new JButton("update");
		btnNext.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnNext.setBounds(198, 217, 117, 29);
		contentPane.add(btnNext);
		
		JButton btnRefresh = new JButton("refresh");
		btnRefresh.setBounds(677, 217, 117, 29);
		contentPane.add(btnRefresh);
	}
	
	public void populateTable() {
		
		for(String name: columnNames)
			model.addColumn(name);
		for (int i = 0; i < health.showAllData().size(); i++) {
			int zipcode = health.showAllData().get(i).getZipCode();
			String county = health.showAllData().get(i).getCounty();
			String city = health.showAllData().get(i).getCity();
			String state = health.showAllData().get(i).getState();
			int year = health.showAllData().get(i).getYear();
			int month = health.showAllData().get(i).getMonth();
			String ageGroup = health.showAllData().get(i).getAgeGroup();
			int numVisit = health.showAllData().get(i).getNumOfVisits();
			float max = health.showAllData().get(i).getMMax();
			float min = health.showAllData().get(i).getMMin();
			float nor = health.showAllData().get(i).getMNor();
			
			
			Object[] data = {zipcode, county, city, state, year, month, ageGroup, numVisit, max, min, nor};
			model.addRow(data);
		}
		
			
	}
}
