package ui;


import highlight.SyntaxAwareDocument;
import io.IOAgent;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Logger;
import javax.swing.JTextPane;

import javax.swing.border.EmptyBorder;
import javax.swing.text.*;

/**
 * Main class for launching the application
 *
 * @author Major: Hongfei Ju, Paulo Jaime, Zitong Wei, Zelin Bao, Binbin Yan
 * @version 2.2
 */

public class TextEditorUI extends JFrame {
    private JMenuBar menuBar;
    private JMenu fileMenu;
    private JMenuItem newFileAction;
    private JMenuItem openFileAction;
    private JMenuItem saveFileAction;
    private JMenuItem closeCurTabAction;
    private JMenuItem exitAction;

    private JMenu editMenu;
    private JMenuItem copyAction;
    private JMenuItem pasteAction;

    private JMenu syntaxMenu;
    private JMenuItem javaAction;
    private JMenuItem plainTextAction;

    private JMenu windowMenu;
    private JMenuItem minimizeAction;
    private JMenuItem zoomAction;

    private JMenu langMenu;
    private JMenuItem engLangAction;
    private JMenuItem frnLangAction;
    private JMenuItem spaLangAction;
    private JMenuItem porLangAction;
    private JMenuItem chnLangAction;

    private JMenu settingsMenu;
    private JMenuItem fontAction;

    private JMenu modeMenu;
    private ButtonGroup modeGroup;

    public JRadioButtonMenuItem dayModeAction;
    public JRadioButtonMenuItem nightModeAction;


    private JMenu helpMenu;
    private JMenuItem openIntroductionAction;
    private JMenuItem openCooperationAction;

    private JTabbedPane tabbedPane;
    private IOAgent ioAgent;

    private TerminalUI terminal;

    private static final Logger log = Logger.getLogger("Log");

    private enum LANG {
        ENG, FRA, SPA, POR, CHN
    }

    /**
     * Constructor init steps:
     * 1. Init UI -- All UI components
     * 2. Init Agent -- The I/O Agent to manipulate underlying I/O operations
     * 3. Init Actions -- Action Listeners
     * 4. Assemble All UI components -- Add UI components into UI containers
     */
    private TextEditorUI() {
        initUI();
        initAgent();
        initActions();
        assembleUIComponents();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
    }


    public static void main(String[] args) {
        new TextEditorUI();

    }

    /**
     * Read icon resources from properties file
     * @return return a Map contains icon resources
     */
    private Map<String, ImageIcon> readIconRes() {
        Map<String, ImageIcon> resource = new HashMap<>();
        Properties prop = new Properties();
        InputStream input;

        try {
            input = Thread.currentThread().getContextClassLoader().getResourceAsStream("resPath.properties");
            prop.load(input);
            input.close();
        } catch (IOException e) {
            log.warning("Read icons failed");
        }

        resource.put("paste", new ImageIcon(getIconPath(prop.getProperty("PasteIcon"))));
        resource.put("cut", new ImageIcon(getIconPath(prop.getProperty("CutIcon"))));
        resource.put("copy", new ImageIcon(getIconPath(prop.getProperty("CopyIcon"))));
        resource.put("exit", new ImageIcon(getIconPath(prop.getProperty("ExitIcon"))));

        resource.put("langEng", new ImageIcon(getIconPath(prop.getProperty("LangEngIcon"))));
        resource.put("langFrn", new ImageIcon(getIconPath(prop.getProperty("LangFrnIcon"))));
        resource.put("langSpa", new ImageIcon(getIconPath(prop.getProperty("LangSpaIcon"))));
        resource.put("langPor", new ImageIcon(getIconPath(prop.getProperty("LangPorIcon"))));
        resource.put("langChn", new ImageIcon(getIconPath(prop.getProperty("LangChnIcon"))));

        resource.put("new", new ImageIcon(getIconPath(prop.getProperty("NewIcon"))));
        resource.put("open", new ImageIcon(getIconPath(prop.getProperty("OpenIcon"))));
        resource.put("save", new ImageIcon(getIconPath(prop.getProperty("SaveIcon"))));
        resource.put("closeTab", new ImageIcon(getIconPath(prop.getProperty("CloseTab"))));

        return resource;
    }

    private URL getIconPath(String iconProperty) {
        URL url = Thread.currentThread().getContextClassLoader().getResource(iconProperty);
        log.info("" + url);
        return url;
    }


    /**
     * Assemble UI components into containers
     */
    private void assembleUIComponents() {
        add(tabbedPane);


        setJMenuBar(menuBar);

        fileMenu.add(newFileAction);
        fileMenu.add(openFileAction);
        fileMenu.add(saveFileAction);
        fileMenu.add(closeCurTabAction);
        fileMenu.add(exitAction);

        editMenu.add(copyAction);
        editMenu.add(pasteAction);

        syntaxMenu.add(javaAction);
        syntaxMenu.add(plainTextAction);

        langMenu.add(engLangAction);
        langMenu.add(frnLangAction);
        langMenu.add(spaLangAction);
        langMenu.add(porLangAction);
        langMenu.add(chnLangAction);

        dayModeAction.setSelected(true);
        modeGroup.add(dayModeAction);
        modeGroup.add(nightModeAction);
        modeMenu.add(dayModeAction);
        modeMenu.add(nightModeAction);

        menuBar.add(fileMenu);
        menuBar.add(editMenu);
        menuBar.add(syntaxMenu);
        menuBar.add(windowMenu);
        menuBar.add(langMenu);
        menuBar.add(settingsMenu);
        menuBar.add(modeMenu);
        menuBar.add(helpMenu);
    }

    /**
     * Init all UI components
     */
    private void initUI() {
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            log.severe("Failed to set UIManager LAF");
        }

        setSize(new Dimension(600, 400));

        Map<String, ImageIcon> iconMap = readIconRes();
        menuBar = new JMenuBar();
        fileMenu = new JMenu("File");
        newFileAction = new JMenuItem("New                            Ctrl+N", iconMap.get("new"));
        openFileAction = new JMenuItem("Open                          Ctrl+O", iconMap.get("open"));
        saveFileAction = new JMenuItem("Save                           Ctrl+S", iconMap.get("save"));
        closeCurTabAction = new JMenuItem("Close Current Tab    Ctrl+T", iconMap.get("closeTab"));
        exitAction = new JMenuItem("Exit                           Ctrl+E", iconMap.get("exit"));

        editMenu = new JMenu("Edit");
        copyAction = new JMenuItem("Copy    Ctrl+C", iconMap.get("copy"));
        pasteAction = new JMenuItem("Paste    Ctrl+V", iconMap.get("paste"));

        syntaxMenu = new JMenu("Syntax");
        javaAction = new JMenuItem("Java                    Ctrl+J");
        plainTextAction = new JMenuItem("Plain text           Ctrl+P");

        windowMenu = new JMenu("Window");

        langMenu = new JMenu("Language");
        engLangAction = new JMenuItem("English", iconMap.get("langEng"));
        frnLangAction = new JMenuItem("Français", iconMap.get("langFrn"));
        spaLangAction = new JMenuItem("Español", iconMap.get("langSpa"));
        porLangAction = new JMenuItem("Português", iconMap.get("langPor"));
        chnLangAction = new JMenuItem("中文", iconMap.get("langChn"));
        fontAction = new JMenuItem("Font        Ctrl+F");

        settingsMenu = new JMenu("Settings");
        helpMenu = new JMenu("Help");

        tabbedPane = new JTabbedPane();

        openIntroductionAction = new JMenuItem("Introduction        Ctrl+I");
        openCooperationAction = new JMenuItem("Cooperators        Ctrl+R");
        minimizeAction = new JMenuItem("Minimize          Ctrl+M");
        zoomAction = new JMenuItem("Zoom                Ctrl+M");

        helpMenu.add(openIntroductionAction);
        helpMenu.add(openCooperationAction);
        windowMenu.add(minimizeAction);
        windowMenu.add(zoomAction);
        settingsMenu.add(fontAction);

        modeMenu = new JMenu("Mode");
        modeGroup = new ButtonGroup();
        dayModeAction = new JRadioButtonMenuItem("Day");
        nightModeAction = new JRadioButtonMenuItem("Night");

        //terminal = new TerminalUI();
    }

    /**
     * Init Agent
     */
    private void initAgent() {
        ioAgent = new IOAgent(tabbedPane);
    }

    private void setTabs(JTextPane textPane) {
        FontMetrics fm = textPane.getFontMetrics(textPane.getFont());
        int charWidth = fm.charWidth(' ');
        int tabWidth = charWidth * 4;
        TabStop[] tabs = new TabStop[5];

        for (int j = 0; j < tabs.length; j++) {
            int tab = j + 1;
            tabs[j] = new TabStop(tab * tabWidth);
        }

        TabSet tabSet = new TabSet(tabs);
        SimpleAttributeSet attributes = new SimpleAttributeSet();
        StyleConstants.setTabSet(attributes, tabSet);
        int length = textPane.getDocument().getLength();
        textPane.getStyledDocument().setParagraphAttributes(0, length, attributes, false);
    }

    /**
     * Init ActionListeners
     */
    private void initActions() {
        newFileAction.addActionListener(e -> {
            JPanel jPanel = new JPanel();
            jPanel.setLayout(new BorderLayout());
            tabbedPane.addTab("new", jPanel);
            JTextPane textPane = new JTextPane(new SyntaxAwareDocument("Java"));
            setTabs(textPane);


            terminal =  new TerminalUI();
            jPanel.add(terminal,BorderLayout.SOUTH);


            if(dayModeAction.isSelected() == true){
                textPane.setBackground(Color.white);
            }
            else {textPane.setBackground(Color.darkGray);}

            EmptyBorder eb = new EmptyBorder(new Insets(10, 10, 10, 10));
            textPane.setBorder(eb);
            JScrollPane scrollPane = new JScrollPane(textPane, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
            TextLineNumber tln = new TextLineNumber(textPane);

            if(dayModeAction.isSelected() == true){
                tln.setBackground(Color.white);
                tln.setForeground(Color.gray);
            }
            else {
                tln.setBackground(Color.darkGray);
                tln.setForeground(Color.white);
            }

            scrollPane.setRowHeaderView( tln );
            jPanel.add(scrollPane, BorderLayout.CENTER);


        });

        openFileAction.addActionListener(e -> {
            JPanel jPanel = new JPanel();
            jPanel.setLayout(new BorderLayout());
            Map<String, String> titleAndContent = ioAgent.read();
            JTextPane textPane;

            terminal =  new TerminalUI();
            jPanel.add(terminal,BorderLayout.SOUTH);

            if (titleAndContent == null) {
                jPanel = null;
                textPane = null;
                titleAndContent = null;
            } else {
                String name = titleAndContent.get("name");
                tabbedPane.addTab(name, jPanel);
                int pos = name.lastIndexOf('.');
                String syntax = pos == -1 ? "Plain text" : name.substring(pos + 1);
                textPane = new JTextPane(new SyntaxAwareDocument(syntax));
                setTabs(textPane);

                if(dayModeAction.isSelected() == true){
                    textPane.setBackground(Color.white);
                }
                else {textPane.setBackground(Color.darkGray);}

                EmptyBorder eb = new EmptyBorder((new Insets(10,10,10,10)));
                textPane.setBorder(eb);
                textPane.setText(titleAndContent.get("content"));

                JScrollPane scrollPane = new JScrollPane(textPane, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
                TextLineNumber tln = new TextLineNumber(textPane);

                if(dayModeAction.isSelected() == true){
                    tln.setBackground(Color.white);
                    tln.setForeground(Color.gray);
                }
                else {
                    tln.setBackground(Color.darkGray);
                    tln.setForeground(Color.white);
                }

                scrollPane.setRowHeaderView( tln );
                jPanel.add(scrollPane, BorderLayout.CENTER);
            }

        });

        saveFileAction.addActionListener(e -> ioAgent.save());

        closeCurTabAction.addActionListener(e -> {
            Component selected = tabbedPane.getSelectedComponent();
            if (selected != null) {
                ioAgent.delete();
                tabbedPane.remove(selected);
            }
        });

        exitAction.addActionListener(e -> System.exit(0));

        copyAction.addActionListener(e -> {
            String str = getSelectedTextFromTextPane();
            StringSelection stringSelection = new StringSelection (str);
            Clipboard clipboard = Toolkit.getDefaultToolkit ().getSystemClipboard ();
            clipboard.setContents (stringSelection, null);
        });


        pasteAction.addActionListener(e -> {
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            DataFlavor flavor = DataFlavor.stringFlavor;
            if (clipboard.isDataFlavorAvailable(flavor)) {
                JTextPane textPane = getCurrentTextPane();
                textPane.paste();
            }

        });

        javaAction.addActionListener(e -> {
            JTextPane pane = getCurrentTextPane();
            assert pane.getDocument() instanceof SyntaxAwareDocument;
            SyntaxAwareDocument doc = (SyntaxAwareDocument) pane.getDocument();
            doc.switchSyntax("Java");
        });

        plainTextAction.addActionListener(e -> {
            JTextPane pane = getCurrentTextPane();
            assert pane.getDocument() instanceof SyntaxAwareDocument;
            SyntaxAwareDocument doc = (SyntaxAwareDocument) pane.getDocument();
            doc.switchSyntax("Plain text");

        });

        engLangAction.addActionListener(e -> changeUIText(LANG.ENG));

        frnLangAction.addActionListener(e -> changeUIText(LANG.FRA));

        spaLangAction.addActionListener(e -> changeUIText(LANG.SPA));

        porLangAction.addActionListener(e -> changeUIText(LANG.POR));

        chnLangAction.addActionListener(e -> changeUIText(LANG.CHN));

        zoomAction.addActionListener(e -> setExtendedState(JFrame.MAXIMIZED_BOTH));

        minimizeAction.addActionListener(e -> setExtendedState(JFrame.ICONIFIED));


        openIntroductionAction.addActionListener(e -> {
            IntroFrame t = new IntroFrame();
            t.setVisible(true);
        });

        openCooperationAction.addActionListener(e -> {
            JOptionPane.showMessageDialog(null, "Zitong Wei\n"
                    + "Binbin Yan\n"
                    + "Paulo Jaime\n"
                    + "Yiru Hu\n"
                    + "Hongfei Ju\n"
                    + "Zelin Bao", "Cooperators",JOptionPane.INFORMATION_MESSAGE);
        });


        fontAction.addActionListener(e -> {
                FontFrame fontFrame = new FontFrame(getCurrentTextPane());
                fontFrame.setVisible(true);
        });

        dayModeAction.addActionListener(e -> {

            changeMenuAndButtonMode(Color.white, Color.black);
            changeTextArea(Color.white, Color.black);
        });

        nightModeAction.addActionListener(e -> {

            changeMenuAndButtonMode(Color.darkGray, Color.white);
            changeTextArea(Color.darkGray, Color.white);
        });
    }

    /**
     * Change the UI label displaying language
     * @param lang use LANG Enum to specify which lang
     */
    private void changeUIText(LANG lang) {
        String language;

        switch (lang) {
            case ENG:
                language = "EN";
                break;
            case FRA:
                language = "FR";
                break;
            case SPA:
                language = "SP";
                break;
            case POR:
                language = "PR";
                break;
            case CHN:
                language = "CN";
                break;
            default:
                language = "EN";
        }

        Properties prop = new Properties();
        try {
            prop.load(new InputStreamReader(Thread.currentThread().getContextClassLoader().getResourceAsStream("langProp.properties"), "UTF-8" ));
        } catch (IOException ioe) {
            log.warning("Read language properties failed");
            throw new RuntimeException(ioe.getMessage());
        }

        fileMenu.setText(prop.getProperty("File" + language));
        newFileAction.setText(prop.getProperty("NewFile" + language));
        openFileAction.setText(prop.getProperty("OpenFile" + language));
        saveFileAction.setText(prop.getProperty("SaveFile" + language));
        closeCurTabAction.setText(prop.getProperty("CloseTab" + language));
        exitAction.setText(prop.getProperty("Exit" + language));
        editMenu.setText(prop.getProperty("Edit" + language));
        copyAction.setText(prop.getProperty("Copy" + language));
        pasteAction.setText(prop.getProperty("Paste" + language));
        syntaxMenu.setText(prop.getProperty("Syntax" + language));
        windowMenu.setText(prop.getProperty("Window" + language));
        langMenu.setText(prop.getProperty("Language" + language));
        settingsMenu.setText(prop.getProperty("Settings" + language));
        helpMenu.setText(prop.getProperty("Help" + language));
    }

    /**
     * Read Current text from TextEditor
     * @return
     */
    private JTextPane getCurrentTextPane() {
        try {
            Component component = tabbedPane.getSelectedComponent();
            JScrollPane scrollPane = (JScrollPane) ((JPanel) component).getComponents()[0];
            JViewport viewport = (JViewport) scrollPane.getComponent(0);
            return (JTextPane) viewport.getComponent(0);
        } catch (NullPointerException npe) {
            log.info("No tab found");
            return null;
        }

    }

    private void changeMenuAndButtonMode(Color background, Color foreground) {
        getContentPane().setBackground(background);
        getContentPane().setForeground(foreground);
        ((JPanel) getContentPane()).setOpaque(true);

        getGlassPane().setBackground(background);
        getGlassPane().setForeground(foreground);
        ((JPanel) getGlassPane()).setOpaque(true);

        menuBar.setBackground(background);
        menuBar.setForeground(foreground);
        fileMenu.setBackground(background);
        editMenu.setBackground(background);
        syntaxMenu.setBackground(background);
        windowMenu.setBackground(background);
        langMenu.setBackground(background);
        settingsMenu.setBackground(background);
        modeMenu.setBackground(background);
        helpMenu.setBackground(background);
        fileMenu.setForeground(foreground);
        editMenu.setForeground(foreground);
        syntaxMenu.setForeground(foreground);
        windowMenu.setForeground(foreground);
        langMenu.setForeground(foreground);
        settingsMenu.setForeground(foreground);
        modeMenu.setForeground(foreground);
        helpMenu.setForeground(foreground);

        newFileAction.setBackground(background);
        openFileAction.setBackground(background);
        saveFileAction.setBackground(background);;
        closeCurTabAction.setBackground(background);;
        exitAction.setBackground(background);
        copyAction.setBackground(background);
        pasteAction.setBackground(background);
        javaAction.setBackground(background);
        plainTextAction.setBackground(background);
        engLangAction.setBackground(background);
        frnLangAction.setBackground(background);
        spaLangAction.setBackground(background);
        porLangAction.setBackground(background);
        chnLangAction.setBackground(background);
        openIntroductionAction.setBackground(background);
        openCooperationAction.setBackground(background);
        minimizeAction.setBackground(background);
        zoomAction.setBackground(background);
        fontAction.setBackground(background);
        dayModeAction.setBackground(background);
        nightModeAction.setBackground(background);
        newFileAction.setForeground(foreground);
        openFileAction.setForeground(foreground);
        saveFileAction.setForeground(foreground);;
        closeCurTabAction.setForeground(foreground);
        exitAction.setForeground(foreground);
        copyAction.setForeground(foreground);
        pasteAction.setForeground(foreground);
        javaAction.setForeground(foreground);
        plainTextAction.setForeground(foreground);
        engLangAction.setForeground(foreground);
        frnLangAction.setForeground(foreground);
        spaLangAction.setForeground(foreground);
        porLangAction.setForeground(foreground);
        chnLangAction.setForeground(foreground);
        tabbedPane.setForeground(foreground);
        openIntroductionAction.setForeground(foreground);
        openCooperationAction.setForeground(foreground);
        minimizeAction.setForeground(foreground);
        zoomAction.setForeground(foreground);
        fontAction.setForeground(foreground);
        dayModeAction.setForeground(foreground);
        nightModeAction.setForeground(foreground);
    }

    private void changeTextArea(Color background, Color foreground) {
        tabbedPane.setForeground(foreground);
        tabbedPane.setBackground(background);
        int totalTabs = tabbedPane.getTabCount();
        for(int i = 0; i <totalTabs; i++){
            Component tab = tabbedPane.getComponentAt(i);
            JScrollPane scrollPane = (JScrollPane) ((JPanel) tab).getComponent(0);
            JViewport viewport = (JViewport) scrollPane.getComponent(0);
            JTextPane pane = (JTextPane) viewport.getComponent(0);
            TextLineNumber tln = new TextLineNumber(pane);
            tln.setBackground(background);
            if(background == Color.white){
                tln.setForeground(Color.gray);
            }
            else{ tln.setForeground(foreground);}

            scrollPane.setRowHeaderView(tln);
            pane.setForeground(foreground);
            pane.setBackground(background);

            assert pane.getDocument() instanceof SyntaxAwareDocument;
            SyntaxAwareDocument doc = (SyntaxAwareDocument) pane.getDocument();
            if(background == Color.darkGray && doc.mode == SyntaxAwareDocument.MODE.dark || background == Color.white && doc.mode == SyntaxAwareDocument.MODE.bright ) {

            }
            else{
                doc.switchMode();
            }
        }
    }

    private String getSelectedTextFromTextPane() {
        return getCurrentTextPane().getSelectedText();
    }

}
