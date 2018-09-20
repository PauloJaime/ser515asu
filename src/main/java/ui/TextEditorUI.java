package ui;

import io.CreateFileAgent;
import io.OpenFileAgent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class TextEditorUI extends JFrame {
    private JTabbedPane tabbedPane;
    private CreateFileAgent createFileAgent;
    private OpenFileAgent openFileAgent;


    public TextEditorUI(){
        tabbedPane = new JTabbedPane();
        createFileAgent = new CreateFileAgent();
        openFileAgent = new OpenFileAgent();
        setSize(new Dimension(600, 400));
        add(tabbedPane);
        JMenuBar menuBar=new JMenuBar();
        setJMenuBar(menuBar);
        JMenu fileMenu=new JMenu("File");
        menuBar.add(fileMenu);
        JMenu editMenu=new JMenu("Edit");
        menuBar.add(editMenu);
        JMenu formatMenu=new JMenu("Format");
        menuBar.add(formatMenu);

        JMenuItem newFileItem = new JMenuItem("New");
        JMenuItem openFileItem = new JMenuItem("Open");
        JMenuItem saveFileItem = new JMenuItem("Save");
        JMenuItem closeFileItem = new JMenuItem("Close");

        newFileItem.addActionListener(e -> {
            addNewTab();
            createFileAgent.createANewFile();
        });

        openFileItem.addActionListener(e -> {
            String[] titleAndText = openFileAgent.read();
            addNewTab(titleAndText[0], titleAndText[1]);
        });

        Action Save=new AbstractAction("Save fileMenu") {
            @Override
            public void actionPerformed(ActionEvent e) {
//                if(fileChooser.showSaveDialog(null)==JFileChooser.APPROVE_OPTION){
//
//                }
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


        fileMenu.add(newFileItem);
        fileMenu.add(openFileItem);
        fileMenu.add(Save);
        fileMenu.add(Close);
        fileMenu.add(Quit);


        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);


    }

    public static void main(String[] args) {
        TextEditorUI test=new TextEditorUI();

    }

    private void addNewTab() {
        addNewTab("Untitled", "");
    }

    private void addNewTab(String title, String text) {
        JPanel jPanel = new JPanel();
        jPanel.setLayout(new BorderLayout());
        JButton closeTab = new JButton("close");
        jPanel.add(closeTab);
        tabbedPane.addTab(title, jPanel);
        JTextArea textArea = new JTextArea();
        textArea.append(text);
        JScrollPane scrollPane = new JScrollPane(textArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        jPanel.add(scrollPane, BorderLayout.CENTER);
        createFileAgent.createANewFile();
    }

}
