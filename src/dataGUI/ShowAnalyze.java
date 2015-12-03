package dataGUI;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import retrieveData.AnalyzeData;
import retrieveData.Health;
import javax.swing.JTable;
import javax.swing.JScrollPane;

public class ShowAnalyze extends JFrame {

	private JPanel contentPane;
	String[] columnNames = {"county", "year", "avgVisits", "avg(monthlyNor)"};
	private DefaultTableModel model;
	ArrayList<AnalyzeData> list;
	private JTable table;
	

	/**
	 * Create the frame.
	 * @param arrayList 
	 */
	public ShowAnalyze(ArrayList<AnalyzeData> arrayList) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(6, 6, 438, 266);
		contentPane.add(scrollPane);
		this.list  = new ArrayList<AnalyzeData>();
		for (int i = 0; i < arrayList.size(); i++)
			list.add(arrayList.get(i));
		
		populateTable();
		table = new JTable();
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
        	Object[] data = {list.get(i).getCounty(), list.get(i).getYear(), list.get(i).getAvgVisit(), list.get(i).getAVG()};
        	model.addRow(data);
        }
    }
}
