package ui;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.border.EmptyBorder;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class TerminalUI extends JPanel {
	private JTextPane terminalPane;
	int lastIndex;
	String executedContent;
	JScrollPane scrollPane;
	/**
	 * Create the panel.
	 */
	public TerminalUI() {
		setLayout(null);
		lastIndex = 0;

		terminalPane = new JTextPane();

		//enter listener
		terminalPane.addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent e) {
				Document docs = terminalPane.getDocument();
				SimpleAttributeSet attrset = new SimpleAttributeSet();
				StyleConstants.setFontSize(attrset,12);
				StyleConstants.setForeground(attrset,Color.BLACK);

				if(e.getKeyChar()==KeyEvent.VK_ENTER ){
					System.out.println(terminalPane.getText());

					try {

						String cmd = terminalPane.getText().substring(lastIndex, terminalPane.getText().length());

						executedContent = executeCmd(cmd);

						docs.insertString(docs.getLength(), "\n" + executedContent, attrset);
						lastIndex = terminalPane.getText().length();


					} catch (Exception e1) {
						// TODO Auto-generated catch block

						try {
							SimpleAttributeSet attrError = new SimpleAttributeSet();
							StyleConstants.setFontSize(attrError,12);
							StyleConstants.setForeground(attrError, Color.RED);

							docs.insertString(docs.getLength(), "\n" + e1.getMessage(), attrError);

							lastIndex = terminalPane.getText().length();
							StyleConstants.setForeground(attrset,Color.BLACK);

							docs.insertString(docs.getLength(), "\n", attrset);

						} catch (BadLocationException e2) {

							e2.printStackTrace();
						}
						e1.printStackTrace();
					}
					//StyleConstants.setForeground(attrset,Color.BLACK);
				}
			}
		});

		//terminalPane.setBounds(65, 30, 458, 224);
		//terminalPane.s
		//add(terminalPane);

		//EmptyBorder eb = new EmptyBorder(new Insets(10, 10, 10, 10));
		//setBorder(eb);
		setLayout(new BorderLayout());
		JScrollPane scrollPane = new JScrollPane(terminalPane, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		//scrollPane = new JScrollPane(terminalPane);
		//scrollPane.setBounds(45, 20, 508, 253);
		add(scrollPane, BorderLayout.CENTER);
	}

	public static String executeCmd(String strCmd)throws Exception{

		if(strCmd.length() > 1) {
			Process p = Runtime.getRuntime().exec(strCmd);
			StringBuilder sbCmd = new StringBuilder();
			BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream(),"GBK"));
			String line;
			while ((line = br.readLine()) != null) {
				sbCmd.append(line + "\n");
			}
			return sbCmd.toString();
		}
		return "";
	}


	public static void main(String[] args) {
		JFrame j = new JFrame();
		j.setSize(new Dimension(600, 400));
		TerminalUI t = new TerminalUI();
		//JPanel p = new JPanel();
		//p.add(t);
		//p.setVisible(true);
		j.setVisible(true);
		j.getContentPane().add(t);

	}
}
