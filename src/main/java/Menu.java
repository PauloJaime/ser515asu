<<<<<<< HEAD
=======


>>>>>>> b06ae31098d60b6d5f72eccb1ba2fb6ce7ee2630
import io.MenuSaveAgent;
import io.OpenFileAgent;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
<<<<<<< HEAD
import javax.swing.JMenuItem;
=======
>>>>>>> b06ae31098d60b6d5f72eccb1ba2fb6ce7ee2630

public class Menu {

	private JFrame frame;
	private JMenuBar menuBar = new JMenuBar();
	private JMenu fileMenu = new JMenu("Menu");
	private JMenuItem openFile = new JMenuItem("OPEN");
	private JMenuItem mntmSave = new JMenuItem("SAVE");

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
		
		mntmSaveAs.addActionListener(new MenuSaveAgent(this, "saveAs"));
		mntmSave.addActionListener(new MenuSaveAgent(this, "save"));
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
		
<<<<<<< HEAD
		//JMenu fileMenu = new JMenu("Menu");
		menuBar.add(fileMenu);
	
		fileMenu.add(openFile);

		openFile.addActionListener(new OpenFileAgent());
=======
		//JMenu mnMenu = new JMenu("Menu");
		menuBar.add(mnMenu);
		
		mnMenu.add(mntmOpen);
>>>>>>> b06ae31098d60b6d5f72eccb1ba2fb6ce7ee2630
		
		/*
		 * save action
		 */
		//JMenuItem mntmSave = new JMenuItem("SAVE");
		
		fileMenu.add(mntmSave);
	}

}
