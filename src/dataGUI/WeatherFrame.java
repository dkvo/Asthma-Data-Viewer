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
import java.util.ArrayList;
import java.awt.event.ActionEvent;

import retrieveData.Health;
import retrieveData.HealthData;

@SuppressWarnings("serial")
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

        populateTable();
        table = new JTable(model);

        scrollPane.setViewportView(table);

        JButton btnInsert = new JButton("insert");
        btnInsert.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	InsertFrame frame = new InsertFrame();
				frame.setVisible(true);
            }
        });
        btnInsert.setBounds(315, 217, 117, 29);
        contentPane.add(btnInsert);

        JButton btnDelete = new JButton("delete");
        btnDelete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	DeleteFrame delete = new DeleteFrame();
            	delete.setVisible(true);
            }
        });
        btnDelete.setBounds(444, 217, 117, 29);
        contentPane.add(btnDelete);

        JButton btnSearch = new JButton("search");
        btnSearch.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	SelectFrame search = new SelectFrame();
            	search.setVisible(true);
            }
        });
        btnSearch.setBounds(573, 217, 117, 29);
        contentPane.add(btnSearch);

        JLabel lblWeatherTable = new JLabel("Weather Table");
        lblWeatherTable.setBounds(149, 6, 107, 16);
        contentPane.add(lblWeatherTable);

        JButton btnNext = new JButton("update");
        btnNext.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	UpdateFrame update = new UpdateFrame();
            	update.setVisible(true);
            }
        });
        btnNext.setBounds(198, 217, 117, 29);
        contentPane.add(btnNext);

        JButton btnRefresh = new JButton("refresh");
        btnRefresh.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	new WeatherFrame();
            }
        });
        btnRefresh.setBounds(81, 217, 117, 29);
        contentPane.add(btnRefresh);
    }

    @SuppressWarnings("serial")
	public void populateTable() {
    	
    	model = new DefaultTableModel(){
    	
            @Override
            public boolean isCellEditable(int row, int column) {
               //all cells false
               return false;
            }
        };
        
        for(String name: columnNames)
            model.addColumn(name);
        ArrayList<Health> temp = new ArrayList<Health>();
        temp = health.showAllData();
        for(int i = 0; i< temp.size(); i++) {
        	Object[] data = {temp.get(i).getZipCode(), temp.get(i).getCounty(), temp.get(i).getCounty(), temp.get(i).getState(),temp.get(i).getYear(),
        			         temp.get(i).getMonth(), temp.get(i).getAgeGroup(), temp.get(i).getNumOfVisits(), temp.get(i).getMMax(), temp.get(i).getMMin(), temp.get(i).getMNor()};
        	model.addRow(data);
        }
    }
    
}