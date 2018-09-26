package ui;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class TextEditorUI extends JFrame {

    private JFileChooser fileChooser=new JFileChooser();
    JTabbedPane tabbedPane=new JTabbedPane();
    public TextEditorUI(){
        setSize(new Dimension(600, 400));
        add(tabbedPane);

        //add menu bar
        JMenuBar menuBar=new JMenuBar();
        setJMenuBar(menuBar);
        JMenu file=new JMenu("File");
        menuBar.add(file);
        JMenu edit=new JMenu("Edit");
        menuBar.add(edit);
        JMenu format=new JMenu("Format");
        menuBar.add(format);
        JMenu view =new JMenu("View");
        menuBar.add(view);
        JMenu windows=new JMenu("Windows");
        menuBar.add(windows);
        JMenu language=new JMenu("Language");
        menuBar.add(language);
        JMenu setting=new JMenu("Settings");
        menuBar.add(setting);
        JMenu help=new JMenu("Help");
        menuBar.add(help);

        //Icons for edit
        ImageIcon iconPaste = new ImageIcon("src/main/java/ui/icons/paste.png");
        ImageIcon iconCut = new ImageIcon("src/main/java/ui/icons/cut.png");
        ImageIcon iconCopy = new ImageIcon("src/main/java/ui/icons/copy.png");
        ImageIcon iconExit = new ImageIcon("src/main/java/ui/icons/exit.png");
        //Icons for Languages
        ImageIcon iconEnglish = new ImageIcon("src/main/java/ui/icons/England.png");
        ImageIcon iconFrench = new ImageIcon("src/main/java/ui/icons/France.png");
        ImageIcon iconSpanish = new ImageIcon("src/main/java/ui/icons/Spain.png");
        ImageIcon iconPortuguese = new ImageIcon("src/main/java/ui/icons/Portugal.png");
        ImageIcon iconChinese = new ImageIcon("src/main/java/ui/icons/China.png");
        //icons for file
        ImageIcon iconNew= new ImageIcon("src/main/java/ui/icons/new.png");
        ImageIcon iconOpen = new ImageIcon("src/main/java/ui/icons/open.png");
        ImageIcon iconSave = new ImageIcon("src/main/java/ui/icons/save.png");
        ImageIcon iconCloseTab = new ImageIcon("src/main/java/ui/icons/closetab.png");

		
        //define actions
        Action New=new AbstractAction("New", iconNew) {
            @Override
            public void actionPerformed(ActionEvent e) {
                JPanel jPanel=new JPanel();
                jPanel.setLayout(new BorderLayout());
                tabbedPane.addTab("new", jPanel);
                JTextArea textArea=new JTextArea();
                JScrollPane scrollPane=new JScrollPane(textArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
                jPanel.add(scrollPane, BorderLayout.CENTER);
            }
        };
		
        Action Open=new AbstractAction("Open", iconOpen) {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(fileChooser.showOpenDialog(null)==JFileChooser.APPROVE_OPTION){
                    String inputPath=fileChooser.getSelectedFile().getAbsolutePath();
                    String filename=fileChooser.getSelectedFile().getName();
                    JPanel jPanel=new JPanel();
                    jPanel.setLayout(new BorderLayout());
                    tabbedPane.addTab(filename, jPanel);
                    JTextArea textArea=new JTextArea();
                    String content=getContent(inputPath);
                    textArea.setText(content);
                    JScrollPane scrollPane=new JScrollPane(textArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
                    jPanel.add(scrollPane, BorderLayout.CENTER);
                    //saveFile();

                }
            }
        };

        Action Save=new AbstractAction("Save file", iconSave) {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(fileChooser.showSaveDialog(null)==JFileChooser.APPROVE_OPTION){
                    saveFile();
                }
            }
        };

        Action CloseCTab=new AbstractAction("Close current tab", iconCloseTab) {
            @Override
            public void actionPerformed(ActionEvent e) {
                //close current active tab
                Component selected = tabbedPane.getSelectedComponent();
                if (selected != null) {
                    tabbedPane.remove(selected);
                }
            }
        };

        Action Exit=new AbstractAction("Exit", iconExit) {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        };

        Action Copy =  new AbstractAction("Copy", iconCopy) {
            @Override
            public void actionPerformed(ActionEvent e) {
                //
            }
        };

        Action Paste =  new AbstractAction("Paste", iconPaste) {
            @Override
            public void actionPerformed(ActionEvent e) {
                //
            }
        };

        Action Cut =  new AbstractAction("Cut", iconCut) {
            @Override
            public void actionPerformed(ActionEvent e) {
                //
            }
        };

        Action English =  new AbstractAction("English", iconEnglish) {
            @Override
            public void actionPerformed(ActionEvent e) {
                //
            }
        };

        Action French =  new AbstractAction("French", iconFrench) {
            @Override
            public void actionPerformed(ActionEvent e) {
                //
            }
        };

        Action Spanish =  new AbstractAction("Spanish", iconSpanish) {
            @Override
            public void actionPerformed(ActionEvent e) {
                //
            }
        };

        Action Portuguese =  new AbstractAction("Portuguese", iconPortuguese) {
            @Override
            public void actionPerformed(ActionEvent e) {
                //
            }
        };

        Action Chinese =  new AbstractAction("Chinese", iconChinese) {
            @Override
            public void actionPerformed(ActionEvent e) {
                //
            }
        };

        //add actions in the menubar
        file.add(New);
        file.add(Open);
        file.add(Save);
        file.add(CloseCTab);
        file.add(Exit);

        edit.add(Copy);
        edit.add(Cut);
        edit.add(Paste);

        language.add(Portuguese);
        language.add(Chinese);
        language.add(English);
        language.add(Spanish);
        language.add(French);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);

    }
	
	//file I/O methods
    public void openFile(String fileName){
        FileReader fr=null;
        try{
            fr=new FileReader(fileName);
            //textArea.read(fr,null);//print data in textarea
            fr.close();;
            setTitle(fileName);
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    public void saveFile(){
        if(fileChooser.showSaveDialog(null)==JFileChooser.APPROVE_OPTION){
            FileWriter fw=null;
            try{
                fw=new FileWriter(fileChooser.getSelectedFile().getAbsolutePath());
                //textArea.write(fw);/read data from textarea
                fw.close();
            }catch(IOException e){
                e.getStackTrace();
            }
        }
    }
    public String getContent(String filename){
        return "haha";//sample return value
    };

    public static void main(String[] args) {
        TextEditorUI test=new TextEditorUI();

    }

}
