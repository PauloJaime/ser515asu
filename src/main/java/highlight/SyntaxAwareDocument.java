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
        keywordsHighlight(fullText);
        processCommentAndString(fullText);
    }

    @Override
    public void remove(int offs, int len) throws BadLocationException {
        super.remove(offs, len);
        String fullText = getText(0, getLength());

        if (fullText.isEmpty()) {
            return;
        }

        keywordsHighlight(fullText);
        processCommentAndString(fullText);
    }

    private void doHighlight(int begin, int end, String fullText) {
        Color color = keywordDB.matchColor(fullText.substring(begin, end));
        doHighlight(begin, end, fullText, color);
    }

    private void doHighlight(int begin, int end, String fullText, Color color) {
        setCharacterAttributes(begin, end - begin, getAttributeSet(color), false);
    }

    private void processCommentAndString(String fullText) {
        int startIdx = 0;
        while (startIdx < getLength()) {
            int commentTagPos = commentTag == null ? -1 : fullText.indexOf(commentTag, startIdx);
            int mCommentTagPos = mCommentPair == null ? -1 : fullText.indexOf(mCommentPair[0], startIdx);
            /* Overwrite string pos acquisition here */
            int stringTagPos = -1;

            switch(findSmallestElem(commentTagPos, mCommentTagPos, stringTagPos)) {
                case 1:
                    int lineChangePos = fullText.indexOf('\n', commentTagPos);
                    lineChangePos = lineChangePos == -1 ? getLength() : lineChangePos;
                    doHighlight(commentTagPos, lineChangePos, fullText, COMMENT_COLOR);
                    startIdx = lineChangePos;
                    break;
                case 2:
                    startIdx = processMultiComments(mCommentTagPos, fullText);
                    break;
                case 3:
                    /* Write your string process code here */
                    break;
                default:
                    startIdx = getLength();
            }

        }

    }

    private int findSmallestElem(int... options) {
        int res = 0;
        int min = Integer.MAX_VALUE;
        for (int i = 0 ; i < options.length; i++) {
            int option = options[i];
            if (option == - 1) {
                continue;
            }

            if (min > option) {
                min = option;
                res = i + 1;
            }

        }

        return res;
    }

    private void keywordsHighlight(String fullText) {
        Pattern pattern = Pattern.compile("(\\w)");
        Matcher matcher = pattern.matcher(fullText);
        int ptr = 0;
        while (matcher.find()) {
            int sIdx = matcher.start();
            int eIdx = sIdx;
            while (eIdx < fullText.length()
                    && (Character.isAlphabetic(fullText.charAt(eIdx))
                    || Character.isDigit(fullText.charAt(eIdx))
                    || fullText.charAt(eIdx) == '_')) {
                eIdx++;
            }

            if (ptr < sIdx) {
                doHighlight(ptr, sIdx, fullText);
            }

            ptr = eIdx;
            doHighlight(sIdx, eIdx, fullText);
            if (eIdx >= fullText.length()) {
                break;
            }

            matcher = matcher.region(eIdx, fullText.length());
        }

        doHighlight(ptr, getLength(), fullText);
    }

    private int processMultiComments(int startIdx, String fullText) {
        int mCommentEndPos = fullText.indexOf(mCommentPair[1], startIdx);
        mCommentEndPos = mCommentEndPos == -1 ? getLength() : mCommentEndPos;
        doHighlight(startIdx, mCommentEndPos + mCommentPair[1].length(), fullText, COMMENT_COLOR);
        return mCommentEndPos;
    }

}
