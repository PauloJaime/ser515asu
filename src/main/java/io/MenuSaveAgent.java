package io;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;

import javax.swing.*;

public class MenuSaveAgent implements ActionListener {
	private JTabbedPane tabManager;

	public MenuSaveAgent(JTabbedPane tabMgr) {
	    tabManager = tabMgr;
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
        int size = tabManager.getTabCount();
        for (int i = 0; i < size; i++) {
            if (tabManager.isEnabledAt(i)) {
                String title = tabManager.getTitleAt(i);
                Component component = tabManager.getComponentAt(i);
                JScrollPane scrollPane = (JScrollPane) ((JPanel) component).getComponents()[0];
                JTextArea textArea = (JTextArea) scrollPane.getComponent(0);
                save(title, textArea.getText());
                break;
            }
        }

	}
	
	// Work later
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
	
	private void save(String title, String text) {

	}

}
