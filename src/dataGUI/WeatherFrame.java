package dataGUI;



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
        
        
        table = new JTable(model);
        populateTable();
        scrollPane.setViewportView(table);
        
        JButton btnInsert = new JButton("insert");
        btnInsert.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                InsertFrame frame = new InsertFrame();
                frame.setVisible(true);
            }
        });
        btnInsert.setBounds(279, 217, 117, 29);
        contentPane.add(btnInsert);
        
        JButton btnDelete = new JButton("delete");
        btnDelete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                DeleteFrame delete = new DeleteFrame();
                delete.setVisible(true);
            }
        });
        btnDelete.setBounds(412, 217, 117, 29);
        contentPane.add(btnDelete);
        
        JButton btnSearch = new JButton("search");
        btnSearch.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                SelectFrame search = new SelectFrame();
                search.setVisible(true);
            }
        });
        btnSearch.setBounds(530, 217, 117, 29);
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
        btnNext.setBounds(150, 217, 117, 29);
        contentPane.add(btnNext);
        
        JButton btnRefresh = new JButton("refresh");
        btnRefresh.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                populateTable();
                
            }
        });
        btnRefresh.setBounds(29, 217, 117, 29);
        contentPane.add(btnRefresh);
        
        JButton btnAnalyze = new JButton("Analyze");
        btnAnalyze.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ShowAnalyze analyze = new ShowAnalyze(health.analyze());
                analyze.setVisible(true);
            }
        });
        btnAnalyze.setBounds(662, 217, 117, 29);
        contentPane.add(btnAnalyze);
        
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
        ArrayList<Health> temp = new ArrayList<Health>();
        temp = health.showAllData();
        for(int i = 0; i< temp.size(); i++) {
            Object[] data = {temp.get(i).getZipCode(), temp.get(i).getCounty(), temp.get(i).getCounty(), temp.get(i).getState(),temp.get(i).getYear(),
                temp.get(i).getMonth(), temp.get(i).getAgeGroup(), temp.get(i).getNumOfVisits(), temp.get(i).getMMax(), temp.get(i).getMMin(), temp.get(i).getMNor()};
            model.addRow(data);
        }
        table.setModel(model);
    }
}