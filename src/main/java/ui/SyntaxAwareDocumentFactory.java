package ui;

import keyword.InterfaceDefine;

import javax.swing.text.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SyntaxAwareDocumentFactory {
    private static InterfaceDefine keywordDB;

    static {
        keywordDB = new InterfaceDefine();
    }

    private SyntaxAwareDocumentFactory() {

    }

    public static DefaultStyledDocument createDocument() {
        return createDocument("Plain Text");
    }

    public static DefaultStyledDocument createDocument(String grammar) {
        // Do Grammar & Keywords acquisition

        // Set grammar here
        // maybe 'keywordDB.setGrammar(grammar);'

        final StyleContext context = StyleContext.getDefaultStyleContext();
        // Temporary hard code

        return new DefaultStyledDocument() {
            // Will use after DB becomes stable
            private Map<Color, AttributeSet> attrMap = new HashMap<>();

            public AttributeSet getAttributeSet(Color color) {
                return attrMap.computeIfAbsent(color,
                        c -> context.addAttribute(context.getEmptySet(), StyleConstants.Foreground, color));
            }

            public void insertString(int offset, String str, AttributeSet a) throws BadLocationException {
                super.insertString(offset, str, a);
                String text = getText(0, getLength());

                int startIdx = findStartPos(text, offset);
                int endIdx = findEndPos(text, offset, str);
                String fullSentence = text.substring(startIdx, endIdx);
                Pattern pattern = Pattern.compile("(\\w)");
                Matcher matcher = pattern.matcher(fullSentence);
                List<int[]> allPos = new ArrayList<>();
                while (matcher.find()) {
                    int sIdx = matcher.start();
                    int eIdx = sIdx;
                    while (eIdx < fullSentence.length()
                            && (Character.isAlphabetic(fullSentence.charAt(eIdx)) || Character.isDigit(fullSentence.charAt(eIdx)))) {
                        eIdx++;
                    }

                    allPos.add(new int[] {sIdx, eIdx});
                    if (eIdx >= fullSentence.length()) {
                        break;
                    }

                    matcher = matcher.region(eIdx, fullSentence.length());
                    System.out.println(sIdx + " " + eIdx + " " + fullSentence.substring(sIdx, eIdx));
                }

                for (int[] interval : allPos) {
                    String word = fullSentence.substring(interval[0], interval[1]);
                    Color color = keywordDB.matchColor(word);
                    setCharacterAttributes(interval[0] + startIdx, interval[1] - interval[0], getAttributeSet(color), false);
                }

            }

            public void remove(int offs, int len) throws BadLocationException {
                super.remove(offs, len);
                String text = getText(0, getLength());

                if (text.isEmpty()) {
                    return;
                }

                int startIdx = findStartPos(text, Math.max(0, offs - 1));
                int endIdx = findEndPos(text, offs, "");
                String word = text.substring(startIdx, endIdx);
                Color color = keywordDB.matchColor(word);
                setCharacterAttributes(startIdx, endIdx - startIdx, getAttributeSet(color), false);
            }

            private int findStartPos(String text, int initOffset) {
                int startIdx = initOffset;

                boolean isFind = false;
                for (; startIdx >= 0 && startIdx < text.length(); startIdx--) {
                    if (!Character.isAlphabetic(text.charAt(startIdx))) {
                        isFind = true;
                        break;
                    }

                }

                if (isFind) {
                    startIdx++;
                }

                return Math.max(0, startIdx);
            }

            private int findEndPos(String text, int initOffset, String insertText) {
                int endIdx = initOffset + insertText.length();
                for (; endIdx >= 0 && endIdx < text.length(); endIdx++) {
                    if (!Character.isAlphabetic(text.charAt(endIdx))) {
                        break;
                    }

                }

                return endIdx;
            }

        };

    }

}
