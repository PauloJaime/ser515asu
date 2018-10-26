package ui;

import keyword.KeywordDB;
import javax.swing.text.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Custom Document, extends DefaultStyledDocument
 * Add syntax aware property
 *
 * @author Zitong Wei
 * @version 1.0
 */

public class SyntaxAwareDocument extends DefaultStyledDocument {
    private static final Logger log = Logger.getLogger("Log");
    private final StyleContext context;
    private Map<Color, AttributeSet> attrMap;
    private KeywordDB keywordDB;

    public SyntaxAwareDocument(String syntax) {
        keywordDB = new KeywordDB(syntax);
        context = StyleContext.getDefaultStyleContext();
        attrMap = new HashMap<>();
    }


    private AttributeSet getAttributeSet(Color color) {
        return attrMap.computeIfAbsent(color,
                c -> context.addAttribute(context.getEmptySet(), StyleConstants.Foreground, c));
    }

    public void switchSyntax(String syntax) {
        keywordDB.switchSyntax(syntax);
        try {
            String previousText = getText(0, getLength());
            remove(0, getLength());
            insertString(0, previousText, null);
        } catch (BadLocationException e) {
            log.warning("Something terrible here...");
            log.warning("SyntaxAwareDocument.java line: 43");
            throw new RuntimeException(e.getCause());
        }

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
                    && (Character.isAlphabetic(fullSentence.charAt(eIdx))
                        || Character.isDigit(fullSentence.charAt(eIdx))
                        || fullSentence.charAt(eIdx) == '_')) {
                eIdx++;
            }

            allPos.add(new int[] {sIdx, eIdx});
            if (eIdx >= fullSentence.length()) {
                break;
            }

            matcher = matcher.region(eIdx, fullSentence.length());
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

}
