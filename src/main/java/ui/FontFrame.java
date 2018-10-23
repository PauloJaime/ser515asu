package ui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.BoxLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.AbstractListModel;
import javax.swing.JSpinner;
import javax.swing.JTextPane;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import java.awt.Font;

public class FontFrame extends JFrame {

	private JPanel contentPane;
	
	JComboBox familyChooseBox;
	JComboBox styleChooseBox;
	JComboBox sizeChooseBox;

	/**
	 * Launch the application.
	 */
	/*
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FontFrame frame = new FontFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
		*/
	/**
	 * Create the frame.
	 */
	public FontFrame(JTextPane currentPane) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 296, 312);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton yesButton = new JButton("Yes");
		yesButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String family = familyChooseBox.getSelectedItem().toString();
				int size = Integer.parseInt(sizeChooseBox.getSelectedItem().toString());
				String style = styleChooseBox.getSelectedItem().toString();
				
				 switch (style) {
                 case "Plain":
                	 Font f1= new Font(family, Font.BOLD, size);
                	 currentPane.setFont(f1);
                     break;
                 case "Bold":
                	 Font f2= new Font(family, Font.BOLD, size);
                	 currentPane.setFont(f2);
                     break;
                 case "Italic":                   
                	 Font f3= new Font(family, Font.BOLD, size);
                	 currentPane.setFont(f3);              	 
                     break;        
             }
				 FontFrame.this.dispose();
				 
        	}
		});
		
		yesButton.setBounds(34, 241, 75, 29);
		contentPane.add(yesButton);
		
		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FontFrame.this.dispose();
			}
		});
		cancelButton.setBounds(185, 241, 75, 29);
		contentPane.add(cancelButton);
		
		JLabel sizeLable = new JLabel("Size");
		sizeLable.setFont(new Font("Lucida Grande", Font.PLAIN, 18));
		sizeLable.setBounds(34, 66, 58, 23);
		contentPane.add(sizeLable);
		
		sizeChooseBox = new JComboBox();
		sizeChooseBox.setModel(new DefaultComboBoxModel(new String[] {"5", "6", "7", "8", "9", "10", "11", "12", "14", "16", "18", "20", "22", "24", "26", "28", "30"}));
		sizeChooseBox.setBounds(104, 67, 111, 27);
		contentPane.add(sizeChooseBox);
		
		JLabel styleLable = new JLabel("Style");
		styleLable.setFont(new Font("Lucida Grande", Font.PLAIN, 18));
		styleLable.setBounds(33, 121, 58, 23);
		contentPane.add(styleLable);
		
		styleChooseBox = new JComboBox();
		styleChooseBox.setModel(new DefaultComboBoxModel(new String[] {"Plain", "Bold", "Italic"}));
		styleChooseBox.setBounds(104, 122, 111, 27);
		contentPane.add(styleChooseBox);
		
		JLabel familylabel = new JLabel("Family");
		familylabel.setFont(new Font("Lucida Grande", Font.PLAIN, 18));
		familylabel.setBounds(34, 176, 58, 23);
		contentPane.add(familylabel);
		
		familyChooseBox = new JComboBox();
		familyChooseBox.setModel(new DefaultComboBoxModel(new String[] {"Times New Roman", "Microsoft Yahei", "SimHei", "SimSun", "Lucida Grande"}));
		familyChooseBox.setBounds(104, 177, 111, 27);
		contentPane.add(familyChooseBox);
		
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				FontFrame.this.dispose();
			}
		
		});
	}
}
