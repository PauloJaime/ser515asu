package io;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * The class is used to manipulating the I/O operations
 *
 * @author Zitong Wei, Zelin Bao
 * @version 1.0
 */
public class IOAgent {
    private JTabbedPane tabManager;

    public IOAgent(JTabbedPane tabMgr) {
        tabManager = tabMgr;
    }

    public void save() {
        Map<String, String> nameAndContent = acquireTabContent(tabManager.getSelectedIndex());
        doSave(nameAndContent.get("name"), nameAndContent.get("content"));
    }

    public Map<String, String> read() {
        Map<String, String> result = new HashMap<>();
        JFileChooser jFileChooser = new JFileChooser();
        jFileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        jFileChooser.showOpenDialog(null);
        File file = jFileChooser.getSelectedFile();
        String path = file.getPath();
        if (PathDB.containsPath(path)) {
            return result;
        }

        PathDB.addPath(path);
        StringBuilder sb = new StringBuilder();
        try {
            FileReader fileReader = new FileReader(file);
            BufferedReader br = new BufferedReader(fileReader);
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line).append('\n');
            }

            br.close();
            fileReader.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        result.put("name", file.getName());
        result.put("content", sb.toString());
        return result;
    }

    public void delete() {
        PathDB.delete(tabManager.getTitleAt(tabManager.getSelectedIndex()));
    }

    private void doSave(String title, String content){
        String path = PathDB.getPath(title);

        if (path == null) {
            saveAs(content); // file no exist
        } else {
            try {
                FileWriter fw = new FileWriter(path);
                fw.write(content);
                fw.flush();
                fw.close();

            } catch (IOException ex) {
                ex.printStackTrace();
            }

        }

    }

    private void saveAs(String content) {
        JFileChooser chooser = new JFileChooser();
        JFrame saveAsFrame = new JFrame();
        int res = chooser.showSaveDialog(saveAsFrame);
        if (res == JFileChooser.APPROVE_OPTION) {
            try {
                File file = chooser.getSelectedFile();
                tabManager.setTitleAt(tabManager.getSelectedIndex(), file.getName());
                PathDB.addPath(file.getPath());
                FileWriter fw = new FileWriter(file.getPath());
                fw.write(content);
                fw.flush();
                fw.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }

        }

    }

    private Map<String, String> acquireTabContent(int idx) {
        Map<String, String> result = new HashMap<>();
        Component component = tabManager.getComponentAt(idx);
        JScrollPane scrollPane = (JScrollPane) ((JPanel) component).getComponents()[0];
        JViewport viewport = (JViewport) scrollPane.getComponent(0);
        JTextArea textArea = (JTextArea) viewport.getComponent(0);
        result.put("name", tabManager.getTitleAt(idx));
        result.put("content", textArea.getText());
        return result;
    }

}
