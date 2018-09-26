package io;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.*;

public class MenuSaveAgent implements ActionListener {
	private JTabbedPane tabManager;
	private int index; // current tab index
	JTextArea textArea;
	
	public MenuSaveAgent(JTabbedPane tabMgr) {
	    tabManager = tabMgr;
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
        int size = tabManager.getTabCount();
        for (int i = 0; i < size; i++) {
            if (tabManager.isEnabledAt(i)) {
                String title = tabManager.getTitleAt(i);
                // save index
                index = i; 
                Component component = tabManager.getComponentAt(i);
                JScrollPane scrollPane = (JScrollPane) ((JPanel) component).getComponents()[0];
                textArea = (JTextArea) scrollPane.getComponent(0);
                save(title, textArea.getText());
                break;
            }
        }

	}
	
	
	
	private void save(String title, String text){
		String path = PathDB.getPath(index);
		
		if(path == null) {
			saveAs(); // file no exist
		}
		
		String content = textArea.getText();
		try {
			
			FileWriter fw = new FileWriter(path + title + ".txt");
			fw.write(content);
			fw.flush();
			fw.close();
        
		} catch (Exception ex) {
			
			ex.printStackTrace();
	    }
	}
	
	// Work later
	public void saveAs() {
	    //String sb = "TEST CONTENT";
		String content = textArea.getText();
		
	    JFileChooser chooser = new JFileChooser();
	    //chooser.setCurrentDirectory(new File("/Users/zelinbao"));
	    
	    JFrame saveAsFrame = new JFrame();
	    
	    int retrival = chooser.showSaveDialog(saveAsFrame);
	    
	    if (retrival == JFileChooser.APPROVE_OPTION) {
	        try {
	        	System.out.println("write");
	        	FileWriter fw = new FileWriter(chooser.getSelectedFile()+".txt");
	        	
	            fw.write(content.toString());
	            fw.flush();
	            fw.close();
	            
	            // add a new path in pathDB();
	            String path = chooser.getSelectedFile()+".txt";
	            if (!PathDB.containsPath(path)) {
	              
	                PathDB.addPath(path);
	            }
	            
	        } catch (Exception ex) {
	            ex.printStackTrace();
	        }
	    }
	}
	
	

}
