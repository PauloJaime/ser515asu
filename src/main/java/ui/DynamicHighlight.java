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
import java.util.Random;


public class DynamicHighlight implements Runnable {
    JTextPane textPane;
    public DynamicHighlight(JTextPane textPane){
        this.textPane=textPane;
    }
    public void run() {
        this.textPane.addKeyListener(new KeyListener() {
            public void color(int i) {
                Boolean blankEnd=false;
                String text=textPane.getText();
                if(text.charAt(text.length()-1)==' ') blankEnd=true;
                if(blankEnd){
                    String[] words=text.split(" ");
                    int secondLastBlankIndex=textPane.getDocument().getLength()-words[words.length-1].length()-2;
                    try{
                        textPane.getDocument().remove(secondLastBlankIndex, textPane.getDocument().getLength() - secondLastBlankIndex);
                    }catch(Exception e){
                        throw null;
                    }
                    Random r=new Random();
                    String preBlank=" ";
                    if(words.length==1) preBlank="";
                    appendToPane(textPane, preBlank+words[words.length-1]+" ",
                            new Color(r.nextInt(256), r.nextInt(256), r.nextInt(256)));
                }
                //Map<String, String> words=IOAgent.wordColorPairs(text);

                Map<String, Color> coloredWords=new HashMap<>();
                /*
                for(int j=0;j<words.length-1;j++){
                    appendToPane(textPane, words[j]+" ", new Color(r.nextInt(red%256), r.nextInt(blue%256), r.nextInt(green%256)));
                    red=red+20; blue=blue+20; green=green+20;
                }
                */

                /*
                for(String word:coloredWords.keySet()){
                    appendToPane(textPane, word, coloredWords.get(word));
                }
                */
            }
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {
                color(0);
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
