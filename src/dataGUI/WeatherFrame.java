package dataGUI;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;



public class WeatherFrame extends JFrame {
	WeatherTable table;
	JPanel panel;
	JScrollPane scroller;
	JButton insert;
	JButton delete;
	JButton search;
	
	public WeatherFrame() {
		

		table = new WeatherTable();
		//table.getColumnName(col);
		//table.setPreferredScrollableViewportSize(table.getPreferredSize());
		//JScrollPane scroller = new JScrollPane(table);
		panel = new JPanel();
		panel.add(scroller, BorderLayout.CENTER);
		setSize(400, 500);
	}
}
