package ui;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.AttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;
import java.util.Map;


public class DynamicHighlight implements Runnable {
    JTextPane textPane;
    public DynamicHighlight(JTextPane textPane){
        this.textPane=textPane;
    }
    public void run() {
        this.textPane.addKeyListener(new KeyListener() {
            public void color(int i) {
                String text=textPane.getText();
                //textPane.setText("");
                //Map<String, String> words=IOAgent.wordColorPairs(text);

                Map<String, String> words=new HashMap<>();
                words.put("variable", "#000000");
                words.put("keyWord", "#FF0000");
                words.put("function", "#0000FF");

                switch(i){
                    case 0:
                        for(String word:words.keySet()){
                            appendToPane(textPane, word, Color.decode(words.get(word)));
                        }
                    case 1:appendToPane(textPane, text, Color.decode("#FF0000"));break;
                    case 2:
                        JOptionPane.showMessageDialog(null,
                            text, "change",
                            JOptionPane.ERROR_MESSAGE);;break;
                }
            }
            @Override
            public void keyTyped(KeyEvent e) {
                color(2);
            }

            @Override
            public void keyPressed(KeyEvent e) {
                color(0);
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });
        /*
        textPane.getDocument().addDocumentListener(new DocumentListener() {
            public void color(int i) {
                String text=textPane.getText();
                //textPane.setText("");
                //Map<String, String> words=IOAgent.wordColorPairs(text);

                Map<String, String> words=new HashMap<>();
                words.put("variable", "#000000");
                words.put("keyWord", "#FF0000");
                words.put("function", "#0000FF");

                switch(i){
                    case 0:textPane.setText("");
                        for(String word:words.keySet()){
                            appendToPane(textPane, word, Color.decode(words.get(word)));
                        }
                    case 1:textPane.setText("");appendToPane(textPane, text, Color.decode("#FF0000"));break;
                    case 2:JOptionPane.showMessageDialog(null,
                            text, "change",
                            JOptionPane.ERROR_MESSAGE);;break;
                }
            }
            @Override
            public void insertUpdate(DocumentEvent e) {
                color(0);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                color(1);
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                color(2);
            }
        });
        */
    }
    private void appendToPane(JTextPane pane, String text, Color color) {

        StyleContext sc = StyleContext.getDefaultStyleContext();
        AttributeSet aset = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground,
                color);

        aset = sc.addAttribute(aset, StyleConstants.Alignment, StyleConstants.ALIGN_JUSTIFIED);
        int len = pane.getDocument().getLength();

        String selection = pane.getSelectedText();
        pane.setCaretPosition(len);
        pane.setCharacterAttributes(aset, false);
        pane.replaceSelection(text);
    }
}
