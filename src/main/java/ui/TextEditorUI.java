import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.*;

public class TextEditorUI extends JFrame {

    private JFileChooser fileChooser=new JFileChooser();
    JTabbedPane tabbedPane=new JTabbedPane();

    public TextEditorUI(){
        setSize(new Dimension(600, 400));
        add(tabbedPane);
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
        ImageIcon iconPaste = new ImageIcon("src/icons/paste.png");
        ImageIcon iconCut = new ImageIcon("src/icons/cut.png");
        ImageIcon iconCopy = new ImageIcon("src/icons/copy.png");
        ImageIcon iconExit = new ImageIcon("src/icons/exit.png");

        //Icons for Languages
        ImageIcon iconEnglish = new ImageIcon("src/icons/England.png");
        ImageIcon iconFrench = new ImageIcon("src/icons/France.png");
        ImageIcon iconSpanish = new ImageIcon("src/icons/Spain.png");
        ImageIcon iconPortuguese = new ImageIcon("src/icons/Portugal.png");
        ImageIcon iconChinese = new ImageIcon("src/icons/China.png");



        //icons for file
        ImageIcon iconNew= new ImageIcon("src/icons/new.png");
        ImageIcon iconOpen = new ImageIcon("src/icons/open.png");
        ImageIcon iconSave = new ImageIcon("src/icons/save.png");
        ImageIcon iconCloseTab = new ImageIcon("src/icons/closetab.png");


        Action New=new AbstractAction("New", iconNew) {
            @Override
            public void actionPerformed(ActionEvent e) {
                JPanel jPanel=new JPanel();
                jPanel.setLayout(new BorderLayout());
                JButton closeTab=new JButton("close");
                jPanel.add(closeTab);
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
                    saveFile();

                }
            }
        };

        Action Save=new AbstractAction("Save", iconSave) {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(fileChooser.showSaveDialog(null)==JFileChooser.APPROVE_OPTION){

                }
            }
        };
        Action Close=new AbstractAction("CloseTab", iconCloseTab) {
            @Override
            public void actionPerformed(ActionEvent e) {
                //close current active tab
            }
        };
        Action Quit=new AbstractAction("Quit",iconExit) {
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

        file.add(New);
        file.add(Open);
        file.add(Save);
        file.add(Close);
        file.add(Quit);

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

    public void openFile(String fileName){
        FileReader fr=null;
        try{
            fr=new FileReader(fileName);
            //textArea.read(fr,null);
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
                //textArea.write(fw);
                fw.close();
            }catch(IOException e){
                e.getStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        TextEditorUI test=new TextEditorUI();

    }

}