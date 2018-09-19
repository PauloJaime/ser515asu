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
	    String sb = "TEST CONTENT";
	    JFileChooser chooser = new JFileChooser();
	    chooser.setCurrentDirectory(new File("/Users/zelinbao"));
	    
	    JFrame saveAsFrame = new JFrame();
	    //int retrival = chooser.showSaveDialog(null);
	    int retrival = chooser.showSaveDialog(saveAsFrame);
	    
	    if (retrival == JFileChooser.APPROVE_OPTION) {
	        try {
	        	System.out.println("write");
	        	FileWriter fw = new FileWriter(chooser.getSelectedFile()+".txt");
	            fw.write(sb.toString());
	            fw.flush();
	            fw.close();
	        } catch (Exception ex) {
	            ex.printStackTrace();
	        }
	    }
	}
	
	public void save() {
	    
	    }
	}
}
