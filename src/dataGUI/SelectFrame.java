package dataGUI;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import retrieveData.Health;
import retrieveData.HealthData;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JButton;

public class SelectFrame extends JFrame {

	private JPanel contentPane;
	private JTextField zipText;
	JComboBox<String> agegroupBox;
	HealthData data = new HealthData();
	Health health = new Health();
	private boolean isinserted;
	String[] agegroupTypes = {"All Ages", "Children (0-17)", "Adults (18+)"};
	private JButton btnAnalyze;

	/**
	 * Create the frame.
	 */
	public SelectFrame() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 150);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		agegroupBox = new JComboBox();
		for (int i = 0; i < agegroupTypes.length; i++)
			agegroupBox.addItem(agegroupTypes[i]);
		
		zipText = new JTextField();
		zipText.setColumns(10);
			
		JLabel lblZipcode = new JLabel("Zipcode");
		
		JLabel lblAgegroup = new JLabel("AgeGroup");
		
		JButton btnSubmit = new JButton("Submit");
		btnSubmit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	if(zipText.getText() == null)
            	{
            		JOptionPane.showMessageDialog(null, "ZipCode field is empty.", "Error", JOptionPane.ERROR_MESSAGE);
            		new SelectFrame();
            	}
            	if(zipText.getText() != null)
            	{
            		try {
            			int zipcode = Integer.parseInt(zipText.getText()); 
            			String ageGroup = agegroupBox.getSelectedItem().toString();
            			health.setZipCode(zipcode);
            			health.setAgeGroup(ageGroup);           			
            			ShowSearch show = new ShowSearch(data.searchData(health));
            			show.setVisible(true);
            		}catch (NumberFormatException n) {
            			JOptionPane.showMessageDialog(null, "Zipcode entered is invalid", "Error", JOptionPane.ERROR_MESSAGE);
            			new SelectFrame();
            		}
            	}
            	
            }
        });
		
		btnAnalyze = new JButton("analyze");
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(lblZipcode)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(zipText, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
							.addContainerGap(131, Short.MAX_VALUE)
							.addComponent(btnSubmit)
							.addPreferredGap(ComponentPlacement.RELATED)))
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.RELATED, 41, Short.MAX_VALUE)
							.addComponent(lblAgegroup)
							.addGap(18)
							.addComponent(agegroupBox, GroupLayout.PREFERRED_SIZE, 127, GroupLayout.PREFERRED_SIZE)
							.addContainerGap())
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(69)
							.addComponent(btnAnalyze)
							.addContainerGap())))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(19)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblZipcode)
						.addComponent(zipText, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblAgegroup)
						.addComponent(agegroupBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED, 27, Short.MAX_VALUE)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnAnalyze)
						.addComponent(btnSubmit))
					.addGap(15))
		);
		contentPane.setLayout(gl_contentPane);
	}




}
