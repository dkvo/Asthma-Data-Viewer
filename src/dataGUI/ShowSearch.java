package dataGUI;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.util.ArrayList;

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
	Health health = new Health();
	private JTable table;
	ArrayList<Health> list;

	/**
	 * Create the frame.
	 * @param arrayList 
	 */
	public ShowSearch(ArrayList<Health> arrayList) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 800, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		this.list  = new ArrayList<Health>();
		for (int i = 0; i < arrayList.size(); i++)
			list.add(arrayList.get(i));
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(6, 6, 788, 266);
		contentPane.add(scrollPane);
		table = new JTable(model);
		populateTable();
		scrollPane.setViewportView(table);
		
		
	}
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
        for(int i = 0; i< list.size(); i++) {
        	System.out.println(list.get(i));
        	Object[] data = {list.get(i).getZipCode(), list.get(i).getCounty(), list.get(i).getCounty(), list.get(i).getState(),list.get(i).getYear(),
        			         list.get(i).getMonth(), list.get(i).getAgeGroup(), list.get(i).getNumOfVisits(), list.get(i).getMMax(), list.get(i).getMMin(), list.get(i).getMNor()};
        	model.addRow(data);
        }
        table.setModel(model);
    }
}
