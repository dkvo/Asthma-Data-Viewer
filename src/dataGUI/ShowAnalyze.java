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
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		this.list  = new ArrayList<AnalyzeData>();
		for (int i = 0; i < arrayList.size(); i++)
			list.add(arrayList.get(i));
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(6, 6, 438, 266);
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
        	Object[] data = {list.get(i).getCounty(), list.get(i).getYear(), list.get(i).getAvgVisit(), list.get(i).getAVG()};
        	model.addRow(data);
        }
        table.setModel(model);
    }
}
