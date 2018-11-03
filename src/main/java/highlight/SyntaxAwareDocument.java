package highlight;

import keyword.KeywordDB;
import javax.swing.text.*;
import java.awt.*;
import java.util.ArrayList;
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
    private static final Color COMMENT_COLOR = Color.green;

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
            log.warning("SyntaxAwareDocument.java switch syntax failed");
            throw new RuntimeException(e.getCause());
        }

    }

    @Override
    public void insertString(int offset, String str, AttributeSet a) throws BadLocationException {
        super.insertString(offset, str, a);
        String text = getText(0, getLength());

        int startIdx = findStartPos(text, Math.max(0, offset - 1), i -> !isVarValidChar(text.charAt(i)));
        int endIdx = findEndPos(text, offset + str.length(), i -> !isVarValidChar(text.charAt(i)));
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

            doHighlight(sIdx + startIdx, eIdx + startIdx, text);
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

        int startIdx = findStartPos(text, Math.max(0, offs - 1), i -> !isVarValidChar(text.charAt(i)));
        int endIdx = findEndPos(text, offs, i -> !isVarValidChar(text.charAt(i)));
        String word = text.substring(startIdx, endIdx);
        Color color = keywordDB.matchColor(word);
        setCharacterAttributes(startIdx, endIdx - startIdx, getAttributeSet(color), false);
    }

    private boolean isVarValidChar(char c) {
        return Character.isAlphabetic(c) || Character.isDigit(c) || c == '_';
    }

//    Reserved for later updating
//    String word = fullSentence.substring(interval[0], interval[1]);
//    Color color = keywordDB.matchColor(word);
//    setCharacterAttributes(interval[0] + startIdx, interval[1] - interval[0], getAttributeSet(color), false);
//    System.out.println("from " + interval[0] + " to " + interval[1]);
//    Element element = getCharacterElement(interval[0]);
//    AttributeSet attr = element.getAttributes();
//    System.out.println(((Color) attr.getAttribute(StyleConstants.Foreground)).getBlue());
    private void doHighlight(int begin, int end, String fullText) {
        Color color = keywordDB.matchColor(fullText.substring(begin, end));
        doHighlight(begin, end, fullText, color);
    }

    private void doHighlight(int begin, int end, String fullText, Color color) {
        setCharacterAttributes(begin, end - begin, getAttributeSet(color), false);
    }

    /**
     * Do comment & string detection pre-process.
     * @param startIdx
     * @param fullText
     */
    private void highlight(int startIdx, int endIdx, String insertString, String fullText) {
        int startCommentPos = findStartPos(fullText, startIdx - 1, i -> {
            Color color = getPrevTextColor(i, fullText, c -> (c == ' ' || c == '\t'));
            return !color.equals(Color.green);
        });

        int startStringPos = findStartPos(fullText, startIdx - 1, i -> {
            Color color = getPrevTextColor(i, fullText, c -> (c == ' ' || c == '\t'));
            return !color.equals(Color.orange);
        });

        if (startCommentPos < startIdx && startStringPos < startIdx) {
            log.severe("SyntaxAwareDocument.java has attrSet acquire exception");
            throw new RuntimeException("AttrSet retrieve exception");
        }

        if (startCommentPos < startIdx) {
            int commentTagPos = commentTag == null ? -1 : fullText.indexOf(commentTag, startCommentPos);
            int mCommentStartPos = mCommentPair == null ? -1 : fullText.indexOf(mCommentPair[0], startCommentPos);
            if (commentTagPos == startCommentPos) {
                int lineEndPos = findEndPos(fullText, startCommentPos, i -> (fullText.charAt(i) == '\n'));
                doHighlight(startCommentPos, lineEndPos, fullText);
                if (lineEndPos < endIdx) {
                    highlight(lineEndPos + 1, endIdx, insertString.substring(lineEndPos - startIdx + 1), fullText);
                }

            } else if (mCommentStartPos == startCommentPos) {
                if (insertString.contains(mCommentPair[1])) {
                    int rightPairPos = insertString.indexOf(mCommentPair[1]) + startIdx;
                    doHighlight(startIdx, rightPairPos + 1, fullText, COMMENT_COLOR);
                    String restStr = fullText.substring(rightPairPos + 1);
                    try {
                        remove(rightPairPos + 1, getLength() - rightPairPos - 1);
                        insertString(getLength(), restStr, null);
                    } catch (BadLocationException e) {
                        log.severe("Offset error in comment highlight");
                        throw new RuntimeException(e.getCause());
                    }

                } else {
                    doHighlight(startIdx, endIdx + 1, fullText, COMMENT_COLOR);
                }

            } else {
                log.severe("SyntaxAwareDocument.java comment highlight error");
                throw new RuntimeException("Comment highlight error");
            }

        } else if (startStringPos < startIdx) {
            /* Write your string processing code here */

            /* Return a List<int[]>, int[] is pair of indexes ---- [start, end], which means that
              the substring from start to end is not highlighted and need to be parse later */
        } else {
            int commentTagPos = commentTag == null ? Integer.MAX_VALUE : insertString.indexOf(commentTag) + startIdx;
            int mCommentPairStartPos = mCommentPair == null ? Integer.MAX_VALUE: insertString.indexOf(mCommentPair[0]) + startIdx;
            /* Change to your statement */
            int stringDelimiter = Integer.MAX_VALUE;

            switch (getSpecialCondition(commentTagPos, mCommentPairStartPos, stringDelimiter)) {
                case 1:
                    // code to process one line comment
                    break;
                case 2:
                    // code to process multi-line comments
                    break;
                case 3:
                    highlightString(stringDelimiter, endIdx, insertString.substring(stringDelimiter), fullText);
                    break;
                default:
                    // do normal highlight
            }

        }

    }

    private void highlightString(int startIdx, int endIdx, String insertString, String fullText) {
        /* Write your code here */

    }


    private int getSpecialCondition(int comment, int mComment, int stringDelimiter) {
        if (comment < mComment && comment < stringDelimiter) {
            return 1;
        } else if (mComment < comment && mComment < stringDelimiter) {
            return 2;
        } else if (stringDelimiter < comment && stringDelimiter < mComment) {
            return 3;
        } else {
            return 0;
        }

    }

    /**
     * Get the previous word's color style.
     * E.g. If it is comment, it will return Color.green
     * @param startIdx
     * @param text
     * @return the text color
     */
    private Color getPrevTextColor(int startIdx, String text, Function<Character, Boolean> charFilter) {
        if (startIdx != 0) {
            int pos = startIdx;
            while (pos >= 0 && charFilter.apply(text.charAt(pos))) {
                pos--;
            }

            pos = Math.max(0, pos);
            if (isVarValidChar(text.charAt(pos))) {
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


    private boolean isPairCommentTag(int[] interval, int startIdx, int endIdx) {
        return false;
    }

    private int findStartPos(String text, int initOffset, Function<Integer, Boolean> isValidChar) {
        int startIdx = initOffset;

        boolean isFind = false;
        for (; startIdx >= 0 && startIdx < text.length(); startIdx--) {
            if (isValidChar.apply(startIdx)) {
                isFind = true;
                break;
            }

        }

        if (isFind) {
            startIdx++;
        }

        return Math.max(0, startIdx);
    }

    private int findEndPos(String text, int startIdx, Function<Integer, Boolean> isValidChar) {
        for (; startIdx >= 0 && startIdx < text.length(); startIdx++) {
            if (isValidChar.apply(startIdx)) {
                break;
            }

        }

        return startIdx;
    }

}
