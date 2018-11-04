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
    private static final Color COMMENT_COLOR = Color.green.darker();
    private static final Color STRING_COLOR = Color.orange.darker();

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
        String fullText = getText(0, getLength());

        int startIdx = findStartPos(fullText, Math.max(0, offset - 1), i -> !(fullText.charAt(i) != ' ' && fullText.charAt(i) != '\t' && fullText.charAt(i) != '\n'));
        int endIdx = findEndPos(fullText, offset + str.length(), i -> !(fullText.charAt(i) != ' ' && fullText.charAt(i) != '\t' && fullText.charAt(i) != '\n'));
        highlight(startIdx, endIdx, fullText);
    }

    @Override
    public void remove(int offs, int len) throws BadLocationException {
        super.remove(offs, len);
        String fullText = getText(0, getLength());

        if (fullText.isEmpty()) {
            return;
        }

        int startIdx = findStartPos(fullText, Math.max(0, offs - 1), i -> !(fullText.charAt(i) != ' ' && fullText.charAt(i) != '\t' && fullText.charAt(i) != '\n'));
        int endIdx = findEndPos(fullText, offs, i -> !(fullText.charAt(i) != ' ' && fullText.charAt(i) != '\t' && fullText.charAt(i) != '\n'));
        highlight(startIdx, endIdx, fullText);
    }

    private void doHighlight(int begin, int end, String fullText) {
        Color color = keywordDB.matchColor(fullText.substring(begin, end));
        doHighlight(begin, end, fullText, color);
    }

    private void doHighlight(int begin, int end, String fullText, Color color) {
        setCharacterAttributes(begin, end - begin, getAttributeSet(color), false);
    }

    private void keywordsHighlight(int startIdx, int endIdx, String fullText) {
        String fullSentence = fullText.substring(startIdx, endIdx);
        Pattern pattern = Pattern.compile("(\\w)");
        Matcher matcher = pattern.matcher(fullSentence);
        int ptr = startIdx;
        while (matcher.find()) {
            int sIdx = matcher.start();
            int eIdx = sIdx;
            while (eIdx < fullSentence.length()
                    && (Character.isAlphabetic(fullSentence.charAt(eIdx))
                    || Character.isDigit(fullSentence.charAt(eIdx))
                    || fullSentence.charAt(eIdx) == '_')) {
                eIdx++;
            }

            if (ptr < sIdx + startIdx) {
                doHighlight(ptr, sIdx + startIdx, fullText);
            }

            ptr = eIdx + startIdx;
            doHighlight(sIdx + startIdx, eIdx + startIdx, fullText);
            if (eIdx >= fullSentence.length()) {
                break;
            }

            matcher = matcher.region(eIdx, fullSentence.length());
        }

        doHighlight(ptr, endIdx, fullText);
    }

    /**
     * Do comment & string detection pre-process.
     * The interval is [startIdx, endIdx)
     * Steps:
     *  1. Normal highlight
     *  2. Left part text detection
     *  3. Right part text detection
     * @param startIdx
     * @param endIdx
     * @param fullText
     */
    private void highlight(int startIdx, int endIdx, String fullText) {
        if (startIdx >= endIdx) {
            return;
        }

        keywordsHighlight(startIdx, endIdx, fullText);
        processMultiComments(startIdx, endIdx, fullText);
        int newEndIdx = processSingleComment(startIdx, endIdx, fullText);
        newEndIdx = newEndIdx == -1 ? endIdx : newEndIdx;
        recoverNormalHighlight(newEndIdx, fullText);
    }

    private void processMultiComments(int startIdx, int endIdx, String fullText) {
        String beforeString = fullText.substring(0, endIdx);
        int lastMCommentStartPos = mCommentPair == null ? -1 : beforeString.lastIndexOf(mCommentPair[0]);
        int firstMCommentEndPos = mCommentPair == null || lastMCommentStartPos == -1 ? -1 : fullText.indexOf(mCommentPair[1], lastMCommentStartPos);

        while (lastMCommentStartPos != -1) {
            firstMCommentEndPos = firstMCommentEndPos == -1 ? getLength() : firstMCommentEndPos;
            doHighlight(lastMCommentStartPos, firstMCommentEndPos + mCommentPair[1].length(), fullText, COMMENT_COLOR);
            if (firstMCommentEndPos > endIdx || firstMCommentEndPos == getLength()) {
                break;
            }

            lastMCommentStartPos = fullText.indexOf(mCommentPair[0], firstMCommentEndPos);
            firstMCommentEndPos = lastMCommentStartPos == -1 ? getLength() : fullText.indexOf(mCommentPair[1], lastMCommentStartPos);
        }

    }

    private int processSingleComment(int startIdx, int endIdx, String fullText) {
        if (commentTag == null) {
            return -1;
        }

        if (startIdx != 0) {
            String beforeString = fullText.substring(0, startIdx);
            int lineChangePos = beforeString.lastIndexOf('\n');
            startIdx = lineChangePos == -1 ? startIdx : lineChangePos;
        }

        if (endIdx != getLength()) {
            int lineChangePos = fullText.indexOf('\n', endIdx);
            endIdx = lineChangePos == -1 ? endIdx : lineChangePos;
        }

        String strBlock = fullText.substring(startIdx, endIdx);
        String[] lines = strBlock.split("\n");
        int offset = startIdx;
        for (String line : lines) {
            int commentPos = line.indexOf(commentTag);
            if (commentPos != -1) {
                doHighlight(offset + commentPos, offset + line.length(), fullText, COMMENT_COLOR);
            }

            offset += line.length();
        }

        return endIdx;
    }

    private void recoverNormalHighlight(int startIdx, String fullText) {
        int mCommentPos = mCommentPair == null ? -1 : fullText.indexOf(mCommentPair[0], startIdx);
        int commentPos = commentTag == null ? -1 : fullText.indexOf(commentTag, startIdx);
        if (commentPos != -1 && mCommentPos != -1) {
            highlight(startIdx, Math.min(commentPos, mCommentPos), fullText);
        } else if (mCommentPos != -1) {
            highlight(startIdx, mCommentPos, fullText);
        } else if (commentPos != -1) {
            highlight(startIdx, commentPos, fullText);
        }

    }

    private int findStartPos(String text, int startIdx, Function<Integer, Boolean> isValidChar) {
        for (; startIdx >= 0 && startIdx < text.length(); startIdx--) {
            if (isValidChar.apply(startIdx)) {
                break;
            }

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
