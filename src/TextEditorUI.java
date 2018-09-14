import javax.swing.*;
import java.awt.*;


public class TextEditorUI extends JFrame{
    private JTextArea textArea=new JTextArea();
    public TextEditorUI(){
        setSize(new Dimension(600, 400));
        JScrollPane scrollPane=new JScrollPane(textArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        add(scrollPane);
        JMenuBar menuBar=new JMenuBar();
        setJMenuBar(menuBar);
        JMenu file=new JMenu("Menu");
        menuBar.add(file);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    public static void main(String[] args) {
        TextEditorUI test=new TextEditorUI();

    }

}
