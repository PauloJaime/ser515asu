package ui;



import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JTextPane;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import java.util.logging.Logger;

public class FontFrame extends JFrame {

	private JPanel contentPane;
	private JComboBox familyChooseBox;
	private JComboBox styleChooseBox;
	private JComboBox sizeChooseBox;
	private JComboBox colorChooseBox;

	private static final Logger log = Logger.getLogger("Log");

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
		JButton cancelButton = new JButton("Cancel");
		JLabel sizeLable = new JLabel("Size");
		JLabel styleLable = new JLabel("Style");
		JLabel familylabel = new JLabel("Family");
		JLabel colorLabel = new JLabel("Color");

		sizeChooseBox = new JComboBox();
		styleChooseBox = new JComboBox();
		familyChooseBox = new JComboBox();
		colorChooseBox = new JComboBox();

		yesButton.setBounds(34, 241, 75, 29);
		cancelButton.setBounds(185, 241, 75, 29);
		sizeLable.setFont(new Font("Lucida Grande", Font.PLAIN, 14));
		sizeLable.setBounds(34, 67, 58, 23);

		contentPane.add(yesButton);
		contentPane.add(cancelButton);
		contentPane.add(sizeLable);
		contentPane.add(colorLabel);


		sizeChooseBox.setModel(new DefaultComboBoxModel(new String[] {"5", "6", "7", "8", "9", "10", "11", "12", "14", "16", "18", "20", "22", "24", "26", "28", "30"}));
		sizeChooseBox.setBounds(104, 67, 111, 27);
		contentPane.add(sizeChooseBox);

		colorChooseBox.setModel(new DefaultComboBoxModel(new String[] {"Black", "Green", "Blue", "Magenta", "Cyan", "Yellow", "Red", "White", "Grey", "Dark Grey", "Light Grey", "Orange", "Pink"}));
		colorChooseBox.setBounds(104, 12, 111, 27);
		contentPane.add(colorChooseBox);

		colorLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 14));
		colorLabel.setBounds(33, 12, 58, 23);
		contentPane.add(colorLabel);

		styleLable.setFont(new Font("Lucida Grande", Font.PLAIN, 14));
		styleLable.setBounds(33, 121, 58, 23);
		contentPane.add(styleLable);

		styleChooseBox.setModel(new DefaultComboBoxModel(new String[] {"Plain", "Bold", "Italic"}));
		styleChooseBox.setBounds(104, 122, 111, 27);
		contentPane.add(styleChooseBox);

		familylabel.setFont(new Font("Lucida Grande", Font.PLAIN, 14));
		familylabel.setBounds(33, 176, 58, 23);
		contentPane.add(familylabel);

		familyChooseBox.setModel(new DefaultComboBoxModel(new String[] {"Times New Roman", "Microsoft Yahei", "SimHei", "SimSun", "Lucida Grande"}));
		familyChooseBox.setBounds(104, 176, 111, 27);
		contentPane.add(familyChooseBox);

		yesButton.addActionListener(e -> {
				try {
					String family = familyChooseBox.getSelectedItem().toString();
					int size = Integer.parseInt(sizeChooseBox.getSelectedItem().toString());
					String style = styleChooseBox.getSelectedItem().toString();
					String selectedColor = colorChooseBox.getSelectedItem().toString();

					switch (style) {
						case "Plain":
							Font f1 = new Font(family, Font.BOLD, size);
							currentPane.setFont(f1);
							break;
						case "Bold":
							Font f2 = new Font(family, Font.BOLD, size);
							currentPane.setFont(f2);
							break;
						case "Italic":
							Font f3 = new Font(family, Font.BOLD, size);
							currentPane.setFont(f3);
							break;
					}

					switch (selectedColor){
						case "Black":
							currentPane.setForeground(Color.BLACK);
							break;
						case "Green":
							currentPane.setForeground(Color.GREEN);
							break;
						case "Blue":
							currentPane.setForeground(Color.BLUE);
							break;
						case "Magenta":
							currentPane.setForeground(Color.MAGENTA);
							break;
						case "Cyan":
							currentPane.setForeground(Color.MAGENTA);
							break;
						case "Yellow":
							currentPane.setForeground(Color.MAGENTA);
							break;
						case "Red":
							currentPane.setForeground(Color.MAGENTA);
							break;
						case "White":
							currentPane.setForeground(Color.MAGENTA);
							break;
						case "Dark Grey":
							currentPane.setForeground(Color.MAGENTA);
							break;
						case "Grey":
							currentPane.setForeground(Color.MAGENTA);
							break;
						case "Light Grey":
							currentPane.setForeground(Color.MAGENTA);
							break;
						case "Orange":
							currentPane.setForeground(Color.MAGENTA);
							break;
						case "Pink":
							currentPane.setForeground(Color.MAGENTA);
							break;
					}

					FontFrame.this.dispose();
				} catch (NullPointerException npe) {
					log.info("No textpane");
				}
		});

		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FontFrame.this.dispose();
			}
		});

		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				FontFrame.this.dispose();
			}
		});
	}
}
