package highlight;

import keyword.KeywordDB;
import javax.swing.text.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Custom Document, extends DefaultStyledDocument
 * Add syntax aware property
 *
 * @author Zitong Wei
 * @version 2.0
 */

public class SyntaxAwareDocument extends DefaultStyledDocument {
    private static final Logger log = Logger.getLogger("Log");
    private final StyleContext context;
    private Map<Color, AttributeSet> attrMap;
    private KeywordDB keywordDB;
    private String commentTag;
    private String[] mCommentPair;

    public SyntaxAwareDocument(String syntax) {
        keywordDB = new KeywordDB(syntax);
        context = StyleContext.getDefaultStyleContext();
        attrMap = new HashMap<>();
        commentTag = keywordDB.getCommentTag();
        mCommentPair = keywordDB.getMCommentTags();
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
            log.warning("Something wrong here...");
            log.warning("SyntaxAwareDocument.java line: 43");
            throw new RuntimeException(e.getCause());
        }

    }

    @Override
    public void insertString(int offset, String str, AttributeSet a) throws BadLocationException {
        super.insertString(offset, str, a);
        String text = getText(0, getLength());

        int startIdx = findStartPos(text, Math.max(0, offset - 1), c -> !isVarValidChar(c));
        int endIdx = findEndPos(text, offset, str);
        String fullSentence = text.substring(startIdx, endIdx);
        Pattern pattern = Pattern.compile("(\\w)");
        Matcher matcher = pattern.matcher(fullSentence);
        while (matcher.find()) {
            int sIdx = matcher.start();
            int eIdx = sIdx;
            while (eIdx < fullSentence.length()
                    && (Character.isAlphabetic(fullSentence.charAt(eIdx))
                        || Character.isDigit(fullSentence.charAt(eIdx))
                        || fullSentence.charAt(eIdx) == '_')) {
                eIdx++;
            }

            highlightSentence(new int[] {sIdx, eIdx}, fullSentence, startIdx, endIdx);
            if (eIdx >= fullSentence.length()) {
                break;
            }

            matcher = matcher.region(eIdx, fullSentence.length());
        }

    }

    @Override
    public void remove(int offs, int len) throws BadLocationException {
        super.remove(offs, len);
        String text = getText(0, getLength());

        if (text.isEmpty()) {
            return;
        }

        int startIdx = findStartPos(text, Math.max(0, offs - 1), c -> !isVarValidChar(c));
        int endIdx = findEndPos(text, offs, "");
        String word = text.substring(startIdx, endIdx);
        Color color = keywordDB.matchColor(word);
        setCharacterAttributes(startIdx, endIdx - startIdx, getAttributeSet(color), false);
    }

    private boolean isVarValidChar(char c) {
        return Character.isAlphabetic(c) || Character.isDigit(c) || c == '_';
    }

    private int findStartPos(String text, int initOffset, Function<Character, Boolean> isValidChar) {
        int startIdx = initOffset;

        boolean isFind = false;
        for (; startIdx >= 0 && startIdx < text.length(); startIdx--) {
            if (isValidChar.apply(text.charAt(startIdx))) {
                isFind = true;
                break;
            }

        }

        if (isFind) {
            startIdx++;
        }

        return Math.max(0, startIdx);
    }

//    Reserved for later updating
//    String word = fullSentence.substring(interval[0], interval[1]);
//    Color color = keywordDB.matchColor(word);
//    setCharacterAttributes(interval[0] + startIdx, interval[1] - interval[0], getAttributeSet(color), false);
//    System.out.println("from " + interval[0] + " to " + interval[1]);
//    Element element = getCharacterElement(interval[0]);
//    AttributeSet attr = element.getAttributes();
//    System.out.println(((Color) attr.getAttribute(StyleConstants.Foreground)).getBlue());
    private void highlightSentence(int[] interval, String fullSentence, int startIdx, int endIdx) {

    }

    private Color getPrevTextColor(int startIdx) {
        String text;
        try {
            text = getText(0, getLength());
        } catch (BadLocationException e) {
            log.severe(e.getMessage());
            throw new RuntimeException(e.getCause());
        }

        if (startIdx != 0) {
            int pos = startIdx - 1;
            while (pos >= 0 && !isWordLetter(text.charAt(pos))) {
                pos--;
            }

            while (pos >= 0 && isWordLetter(text.charAt(pos))) {
                pos--;
            }

            pos = pos + 1;
            if (isWordLetter(text.charAt(pos))) {
                Object obj = getCharacterElement(pos).getAttributes().getAttribute(StyleConstants.Foreground);
                if (obj instanceof Color) {
                    return (Color) obj;
                } else {
                    return Color.black;
                }

            }

        }

        return Color.black;
    }

    private boolean isWordLetter(char c) {
        return c == '_' || Character.isDigit(c) || Character.isAlphabetic(c);
    }

    private boolean isPairCommentTag(int[] interval, int startIdx, int endIdx) {
        return false;
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

    private Color rgb2Color(String rgbString) {
        String[] strs = rgbString.split("-");
        return new Color(Integer.parseInt(strs[0]), Integer.parseInt(strs[1]), Integer.parseInt(strs[2]));
    }

    private String color2rgb(Color color) {
        return "" + color.getRed() + "-" + color.getGreen() + "-" + color.getBlue();
    }

}
