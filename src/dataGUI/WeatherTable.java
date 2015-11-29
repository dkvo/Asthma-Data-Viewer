package dataGUI;

import javax.swing.table.AbstractTableModel;

public class WeatherTable extends AbstractTableModel{

	private String[] column = {"zipCode", "county",
            "year", "ageGroup",	
            "numberofVisits"
           };
	 
	 public String getColumnName(int col) {
	        return column[col].toString();
	    }
	 
	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		// TODO Auto-generated method stub
		return null;
	}

}
