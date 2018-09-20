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

        Action New=new AbstractAction("New file") {
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
        Action Open=new AbstractAction("Open file") {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(fileChooser.showOpenDialog(null)==JFileChooser.APPROVE_OPTION){
                    saveFile();

                }
            }
        };
        Action Save=new AbstractAction("Save file") {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(fileChooser.showSaveDialog(null)==JFileChooser.APPROVE_OPTION){

                }
            }
        };
        Action Close=new AbstractAction("CloseTab") {
            @Override
            public void actionPerformed(ActionEvent e) {
                //close current active tab
            }
        };
        Action Quit=new AbstractAction("Quit") {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        };


        file.add(New);
        file.add(Open);
        file.add(Save);
        file.add(Close);
        file.add(Quit);


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
