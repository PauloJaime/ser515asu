

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JMenuItem;
import java.awt.event.ActionListener;
import java.io.File;
import java.awt.event.ActionEvent;

public class Menu {

	private JFrame frame;
	private JMenuBar menuBar = new JMenuBar();
	private JMenu mnMenu = new JMenu("Menu");
	private JMenuItem mntmOpen = new JMenuItem("OPEN");
	private JMenuItem mntmSave = new JMenuItem("SAVE");
	private JMenuItem mntmCreate = new JMenuItem("CREATE");
	private JMenuItem mntmSaveAs = new JMenuItem("SAVE AS");

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Menu window = new Menu();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	
	
	/**
	 * Create the application.
	 */
	public Menu() {
		initialize();
		
		mntmSave.addActionListener(new MenuSaveAgent(this, "saveAs"));
		
		//chooser.showOpenDialog(null);
		
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		
		//JMenu mnMenu = new JMenu("Menu");
		menuBar.add(mnMenu);
		
		mnMenu.add(mntmOpen);
		
		/*
		 * save action
		 */
		//JMenuItem mntmSave = new JMenuItem("SAVE");
		
		mnMenu.add(mntmSave);
		
		/*
		 *  create action
		 */
		
		//JMenuItem mntmCreate = new JMenuItem("CREATE");
		
		mnMenu.add(mntmCreate);
		
		/*
		 * save as action
		 */
		//JMenuItem mntmSaveAs = new JMenuItem("SAVE AS");
		
		mnMenu.add(mntmSaveAs);
	}

}
