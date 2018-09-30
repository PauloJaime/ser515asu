package ui;


import io.IOAgent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class TextEditorUI extends JFrame {
    private JMenuBar menuBar;
    private JMenu fileMenu;
    private JMenu editMenu;
    private JMenu formatMenu;
    private JMenu viewMenu;
    private JMenu windowMenu;
    private JMenu langMenu;
    private JMenu settingsMenu;
    private JMenu helpMenu;
    private JTabbedPane tabbedPane;
    private IOAgent ioAgent;
    private JMenuItem mntmIntro;
    private JMenuItem mntmCoop;
    private JMenuItem mntmMini;
    private JMenuItem mntmZoom;
   

    public TextEditorUI(){
        initUI();
        initAgent();
        initActions();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);

    }


    public static void main(String[] args) {
        TextEditorUI t = new TextEditorUI();

    }


    private Map<String, ImageIcon> readIconRes() {
        Map<String, ImageIcon> resource = new HashMap<>();

        Properties prop = new Properties();
        InputStream input;

        try {
            input = Thread.currentThread().getContextClassLoader().getResourceAsStream("resPath.properties");
            prop.load(input);
        } catch (IOException e) {
            e.printStackTrace();
        }

        resource.put("paste", new ImageIcon(prop.getProperty("PasteIcon")));
        resource.put("cut", new ImageIcon(prop.getProperty("CutIcon")));
        resource.put("copy", new ImageIcon(prop.getProperty("CopyIcon")));
        resource.put("exit", new ImageIcon(prop.getProperty("ExitIcon")));

        resource.put("langEng", new ImageIcon(prop.getProperty("LangEngIcon")));
        resource.put("langFrn", new ImageIcon(prop.getProperty("LangFrnIcon")));
        resource.put("langSpa", new ImageIcon(prop.getProperty("LangSpaIcon")));
        resource.put("langPor", new ImageIcon(prop.getProperty("LangPorIcon")));
        resource.put("langChn", new ImageIcon(prop.getProperty("LangChnIcon")));

        resource.put("new", new ImageIcon(prop.getProperty("NewIcon")));
        resource.put("open", new ImageIcon(prop.getProperty("OpenIcon")));
        resource.put("save", new ImageIcon(prop.getProperty("SaveIcon")));
        resource.put("closeTab", new ImageIcon(prop.getProperty("CloseTab")));

        return resource;
    }

    private void initUI() {
        menuBar = new JMenuBar();
        fileMenu = new JMenu("File");
        editMenu = new JMenu("Edit");
        formatMenu = new JMenu("Format");
        viewMenu = new JMenu("View");
        windowMenu = new JMenu("Window");
        langMenu = new JMenu("Language");
        settingsMenu = new JMenu("Settings");
        helpMenu = new JMenu("Help");
        tabbedPane = new JTabbedPane();
        getContentPane().add(tabbedPane);
        setJMenuBar(menuBar);
        setSize(new Dimension(600, 400));
        menuBar.add(fileMenu);
        menuBar.add(editMenu);
        menuBar.add(formatMenu);
        menuBar.add(viewMenu);
        menuBar.add(windowMenu);
        menuBar.add(langMenu);
        menuBar.add(settingsMenu);
        menuBar.add(helpMenu);
        
        mntmIntro = new JMenuItem("Introduction");
        mntmCoop = new JMenuItem("Cooperators");
        mntmMini = new JMenuItem("Minimize");
        mntmZoom = new JMenuItem("Zoom");
        
        helpMenu.add(mntmIntro);
        helpMenu.add(mntmCoop);
        windowMenu.add(mntmMini);
        windowMenu.add(mntmZoom);
        
   
    }

    private void initAgent() {
        ioAgent = new IOAgent(tabbedPane); 
    }
    

    private void initActions() {
        Map<String, ImageIcon> iconMap = readIconRes();
        fileMenu.add(new AbstractAction("New", iconMap.get("new")) {
            @Override
            public void actionPerformed(ActionEvent e) {
                JPanel jPanel = new JPanel();
                jPanel.setLayout(new BorderLayout());
                tabbedPane.addTab("new", jPanel);
                JTextArea textArea = new JTextArea();
                JScrollPane scrollPane = new JScrollPane(textArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
                jPanel.add(scrollPane, BorderLayout.CENTER);
            }

        });

        fileMenu.add(new AbstractAction("Open", iconMap.get("open")) {
            @Override
            public void actionPerformed(ActionEvent e) {
                Map<String, String> titleAndContent = ioAgent.read();
                JPanel jPanel=new JPanel();
                jPanel.setLayout(new BorderLayout());
                tabbedPane.addTab(titleAndContent.get("name"), jPanel);
                JTextArea textArea = new JTextArea();
                textArea.setText(titleAndContent.get("content"));
                JScrollPane scrollPane = new JScrollPane(textArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
                jPanel.add(scrollPane, BorderLayout.CENTER);
            }

        });

        fileMenu.add(new AbstractAction("Save file", iconMap.get("save")) {
            @Override
            public void actionPerformed(ActionEvent e) {
                ioAgent.save();
            }

        });

        fileMenu.add(new AbstractAction("Close current tab", iconMap.get("closeTab")) {
            @Override
            public void actionPerformed(ActionEvent e) {
                //close current active tab
                Component selected = tabbedPane.getSelectedComponent();
                if (selected != null) {
                    ioAgent.delete();
                    tabbedPane.remove(selected);
                }
            }

        });

        fileMenu.add(new AbstractAction("Exit", iconMap.get("exit")) {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }

        });

        editMenu.add(new AbstractAction("Copy", iconMap.get("copy")) {
            @Override
            public void actionPerformed(ActionEvent e) {
                throw new UnsupportedOperationException();
            }

        });

        editMenu.add(new AbstractAction("Paste", iconMap.get("paste")) {
            @Override
            public void actionPerformed(ActionEvent e) {
                throw new UnsupportedOperationException();
            }
        });

        editMenu.add(new AbstractAction("Cut", iconMap.get("cut")) {
            @Override
            public void actionPerformed(ActionEvent e) {
                throw new UnsupportedOperationException();
            }
        });

        langMenu.add(new AbstractAction("English", iconMap.get("langEng")) {
            @Override
            public void actionPerformed(ActionEvent e) {
                throw new UnsupportedOperationException();
            }
        });

        langMenu.add(new AbstractAction("French", iconMap.get("langFrn")) {
            @Override
            public void actionPerformed(ActionEvent e) {
                throw new UnsupportedOperationException();
            }
        });

        langMenu.add(new AbstractAction("Spanish", iconMap.get("langSpa")) {
            @Override
            public void actionPerformed(ActionEvent e) {
                throw new UnsupportedOperationException();
            }
        });

        langMenu.add(new AbstractAction("Portuguese", iconMap.get("langPor")) {
            @Override
            public void actionPerformed(ActionEvent e) {
                throw new UnsupportedOperationException();
            }
        });

        langMenu.add(new AbstractAction("Chinese", iconMap.get("langChn")) {
            @Override
            public void actionPerformed(ActionEvent e) {
                throw new UnsupportedOperationException();
            }

        });
        
        mntmZoom.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		setExtendedState(JFrame.MAXIMIZED_BOTH);
        	}
        });
        
        mntmMini.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		setExtendedState(JFrame.ICONIFIED);
        	}
        });
        
        mntmIntro.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		IntroFrame t = new IntroFrame();
        		t.setVisible(true);
        		
        	}
        });
        
        mntmCoop.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		JOptionPane.showMessageDialog(null, "Weizi Tong\n" 
        				+ "Binbin Yan\n"
        				+ "Yiru Hu\n"
        				+ "Hongfei Ju\n"
        				+ "Paulo Jaime", "Cooperators",JOptionPane.INFORMATION_MESSAGE);
        	}
        });
    }





	
}
