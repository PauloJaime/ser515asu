package ui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextPane;
import javax.swing.JLabel;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.Font;

/**
 * The class used to show the introduction panel
 *
 * @author Zelin Bao
 * @version 1.0
 */
public class IntroFrame extends JFrame {

	private JPanel contentPane;

	public IntroFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JTextPane txtpnThisIsA = new JTextPane();
		txtpnThisIsA.setText("This is a Java text editor. There are some functionailities:\nopen a new file\nsava a file\nsave a file as a new path\njava key words highlights\n");
		txtpnThisIsA.setBounds(5, 36, 440, 237);
		txtpnThisIsA.setEditable(false);
		contentPane.add(txtpnThisIsA);
		
		JLabel lblIntroduction = new JLabel("Introduction");
		lblIntroduction.setFont(new Font("Lucida Grande", Font.BOLD, 13));
		lblIntroduction.setBounds(5, 5, 440, 28);
		contentPane.add(lblIntroduction);
		
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				IntroFrame.this.dispose();
			}
		
		});
	}

}
