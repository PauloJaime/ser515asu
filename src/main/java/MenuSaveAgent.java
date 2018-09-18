import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

public class MenuSaveAgent implements ActionListener {
	
	private Menu menuItem;
	private String type;
	
	
	
	public MenuSaveAgent(Menu menu, String item) {
	    this.menuItem = menu;
	    this.type = item;
	  }
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		System.out.println("open");
		
		if(type == "saveAs") {
			
			saveAs();
		}
		
		if(type == "save") {
			//save();
		}
		
	}
	
	
	public void saveAs() {
		JFrame saveAsFrame = new JFrame();
		 
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setDialogTitle("Save as");   
		 
		int Selector = fileChooser.showSaveDialog(saveAsFrame);
		 
		if (Selector == JFileChooser.APPROVE_OPTION) {
		    File fileToSave = fileChooser.getSelectedFile();
		    System.out.println("Save as file: " + fileToSave.getAbsolutePath());
		}
	}
	
	
	public void save() {
	    
	    }
	}
}
