import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.*;


public class TextEditorUI extends JFrame{
    private JTextArea textArea=new JTextArea();
    public TextEditorUI(){
        setSize(new Dimension(600, 400));

        JMenuBar menuBar=new JMenuBar();
        setJMenuBar(menuBar);
        JMenu file=new JMenu("File");
        menuBar.add(file);
        JMenu edit=new JMenu("Edit");
        menuBar.add(edit);
        JMenu format=new JMenu("Format");
        menuBar.add(format);
        JMenu help=new JMenu("Help");
        menuBar.add(help);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);


        Action Close=new AbstractAction("Close file") {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        };

        file.add(Close);

    }

    public static void main(String[] args) {
        TextEditorUI test=new TextEditorUI();

    }

}
