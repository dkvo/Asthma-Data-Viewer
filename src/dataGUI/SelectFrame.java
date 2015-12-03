package dataGUI;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import retrieveData.HealthData;

import javax.swing.DefaultComboBoxModel;
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
    private boolean isinserted;
    ArrayList<String> list;
    
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
        
        zipText = new JTextField();
        zipText.setColumns(10);
        zipText.getDocument().addDocumentListener(new DocumentListener() {
            
            @Override
            public void insertUpdate(DocumentEvent e) {
                
                if(zipText.getText().length() == 5)
                {
                    if(validation())
                    {
                        int zipcode = Integer.parseInt(zipText.getText());
                        list = new ArrayList<String>(data.selectAgeGroup(zipcode));
                    }
                }
            }
            
            @Override
            public void removeUpdate(DocumentEvent e) {
                // TODO Auto-generated method stub
                
            }
            
            @Override
            public void changedUpdate(DocumentEvent e) {
                if(zipText.getText().length() == 5)
                {
                    agegroupBox.addActionListener(new ActionListener () {
                        public void actionPerformed(ActionEvent e) {
                            if(isinserted = true)
                            {
                                for(int i = 0; i < list.size(); i++) {
                                    System.out.println(list.get(i).toString());
                                    agegroupBox.addItem(list.get(i));
                                }
                                
                            }
                        }
                    });
                }
            }
            public boolean validation()
            {
                try {
                    int zipcode = Integer.parseInt(zipText.getText());
                }catch (NumberFormatException a) {
                    JOptionPane.showMessageDialog(null, "Zipcode entered is invalid", "Error", JOptionPane.ERROR_MESSAGE);
                    new SelectFrame();
                }
                return true;
            }
        });
        
        JLabel lblZipcode = new JLabel("Zipcode");
        
        JLabel lblAgegroup = new JLabel("AgeGroup");
        
        JButton btnSubmit = new JButton("Submit");
        btnSubmit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                
            }
        });
        GroupLayout gl_contentPane = new GroupLayout(contentPane);
        gl_contentPane.setHorizontalGroup(
                                          gl_contentPane.createParallelGroup(Alignment.LEADING)
                                          .addGroup(gl_contentPane.createSequentialGroup()
                                                    .addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
                                                              .addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
                                                                        .addComponent(lblZipcode)
                                                                        .addPreferredGap(ComponentPlacement.RELATED)
                                                                        .addComponent(zipText, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                                        .addPreferredGap(ComponentPlacement.RELATED, 53, Short.MAX_VALUE)
                                                                        .addComponent(lblAgegroup)
                                                                        .addGap(18)
                                                                        .addComponent(agegroupBox, GroupLayout.PREFERRED_SIZE, 127, GroupLayout.PREFERRED_SIZE))
                                                              .addGroup(gl_contentPane.createSequentialGroup()
                                                                        .addGap(171)
                                                                        .addComponent(btnSubmit)))
                                                    .addContainerGap())
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
                                                  .addPreferredGap(ComponentPlacement.UNRELATED)
                                                  .addComponent(btnSubmit)
                                                  .addContainerGap(80, Short.MAX_VALUE))
                                        );
        contentPane.setLayout(gl_contentPane);
    }
    
    
    
    
}
