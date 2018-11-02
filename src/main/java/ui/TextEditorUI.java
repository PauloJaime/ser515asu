package ui;

import io.IOAgent;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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
 * @author Major: Hongfei Ju, Paulo Jaime, Zitong Wei, Zelin Bao
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

    private JMenu langMenu;
    private JMenuItem engLangAction;
    private JMenuItem frnLangAction;
    private JMenuItem spaLangAction;
    private JMenuItem porLangAction;
    private JMenuItem chnLangAction;

    private JMenu settingsMenu;
    private JMenu helpMenu;
    private JTabbedPane tabbedPane;
    private IOAgent ioAgent;
    private JMenuItem openIntroductionAction;
    private JMenuItem openCooperationAction;
    private JMenuItem minimizeAction;
    private JMenuItem zoomAction;
    private JMenuItem fontAction;

    private JMenu modeMenu;
    private JMenuItem nightModeAction;
    private JMenuItem dayModeAction;


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
            e.printStackTrace();
        }

        resource.put("paste" +
                "", new ImageIcon(prop.getProperty("PasteIcon")));
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
        //modeMenu.add(nightModeAction);
        //modeMenu.add(dayModeAction);

        editMenu.add(copyAction);
        editMenu.add(pasteAction);

        syntaxMenu.add(javaAction);
        syntaxMenu.add(plainTextAction);

        langMenu.add(engLangAction);
        langMenu.add(frnLangAction);
        langMenu.add(spaLangAction);
        langMenu.add(porLangAction);
        langMenu.add(chnLangAction);
        //modeMenu.add(nightModeAction);
        //modeMenu.add(dayModeAction);

        menuBar.add(fileMenu);
        menuBar.add(editMenu);
        menuBar.add(syntaxMenu);
        menuBar.add(windowMenu);
        menuBar.add(langMenu);
        menuBar.add(settingsMenu);
        menuBar.add(helpMenu);
        menuBar.add(modeMenu);


    }

    /**
     * Init all UI components
     */
    private void initUI() {
        setSize(new Dimension(600, 400));

        Map<String, ImageIcon> iconMap = readIconRes();
        menuBar = new JMenuBar();
        fileMenu = new JMenu("File");
        newFileAction = new JMenuItem("New                            Ctrl+N", iconMap.get("new"));
        openFileAction = new JMenuItem("Open                          Ctrl+O", iconMap.get("open"));
        saveFileAction = new JMenuItem("Save                           Ctrl+S", iconMap.get("save"));
        closeCurTabAction = new JMenuItem("Close Current Tab    Ctrl+T", iconMap.get("closeTab"));
        exitAction = new JMenuItem("Exit                           Ctrl+E", iconMap.get("exit"));

        editMenu = new JMenu("Edit ");
        copyAction = new JMenuItem("Copy    Ctrl+C", iconMap.get("copy"));
        pasteAction = new JMenuItem("Paste    Ctrl+V", iconMap.get("paste"));

        syntaxMenu = new JMenu("Syntax");
        javaAction = new JMenuItem("Java                    Ctrl+J", iconMap.get("java"));
        plainTextAction = new JMenuItem("Plain text           Ctrl+P", iconMap.get("plain"));

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

        modeMenu = new JMenu("Mode");
        //nightModeAction = new JMenuItem("Night");
        //dayModeAction = new JMenuItem("Day");



        helpMenu.add(openIntroductionAction);
        helpMenu.add(openCooperationAction);
        windowMenu.add(minimizeAction);
        windowMenu.add(zoomAction);
        settingsMenu.add(fontAction);
        //modeMenu.add(nightModeAction);
        //modeMenu.add(dayModeAction);

        ButtonGroup myGroup = new ButtonGroup();
        JRadioButtonMenuItem myItem = new JRadioButtonMenuItem("Night");
        myItem.setSelected(true);
        myGroup.add(myItem);
        modeMenu.add(myItem);
        myItem = new JRadioButtonMenuItem("Day");
        myGroup.add(myItem);
        modeMenu.add(myItem);

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
            EmptyBorder eb = new EmptyBorder(new Insets(10, 10, 10, 10));
            textPane.setBorder(eb);
            JScrollPane scrollPane = new JScrollPane(textPane, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
            jPanel.add(scrollPane, BorderLayout.CENTER);
        });

        openFileAction.addActionListener(e -> {
            JPanel jPanel = new JPanel();
            jPanel.setLayout(new BorderLayout());
            Map<String, String> titleAndContent = ioAgent.read();
            JTextPane textPane;

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
                EmptyBorder eb = new EmptyBorder((new Insets(10,10,10,10)));
                textPane.setBorder(eb);
                textPane.setText(titleAndContent.get("content"));

                JScrollPane scrollPane = new JScrollPane(textPane, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
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

        //nightModeAction.addActionListener(e -> {});

        //dayModeAction.addActionListener(e -> {});

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

    private String getSelectedTextFromTextPane() {
        return getCurrentTextPane().getSelectedText();
    }

}
