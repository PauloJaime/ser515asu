package ui;

import javax.swing.text.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class SyntaxAwareDocumentFactory {
    private SyntaxAwareDocumentFactory() {

    }

    public static DefaultStyledDocument createDocument() {
        return createDocument("Plain Text");
    }

    public static DefaultStyledDocument createDocument(String grammar) {
        // Do Grammar & Keywords acquisition

        // Hard code: Only consider Java file

        final StyleContext context = StyleContext.getDefaultStyleContext();
        // Temporary hard code
        final AttributeSet attr = context.addAttribute(context.getEmptySet(), StyleConstants.Foreground, Color.RED);
        final AttributeSet attrBlack = context.addAttribute(context.getEmptySet(), StyleConstants.Foreground, Color.BLACK);

        return new DefaultStyledDocument() {
            // Will use after DB becomes stable
            private Map<String, AttributeSet> attrMap = new HashMap<>();

            public void insertString(int offset, String str, AttributeSet a) throws BadLocationException {
                super.insertString(offset, str, a);
                String text = getText(0, getLength());

                if (!Character.isAlphabetic(text.charAt(offset))) {
                    int leftPointer = offset - 1;
                    int rightPointer = offset + 1;
                    while (isValidIdx(text, leftPointer) && !Character.isAlphabetic(text.charAt(leftPointer))) {
                        leftPointer--;
                    }

                    while (isValidIdx(text, rightPointer) && !Character.isAlphabetic(text.charAt(rightPointer))) {
                        rightPointer++;
                    }

                    if (isValidIdx(text, leftPointer)) {
                        doInsertParse(text, leftPointer);
                    }

                    if (isValidIdx(text, rightPointer)) {
                        doInsertParse(text, rightPointer);
                    }

                    setCharacterAttributes(offset, 1, attrBlack, false);
                } else {
                    doInsertParse(text, offset);
                }

            }

            private boolean isValidIdx(String text, int idx) {
                return idx >= 0 && idx < text.length();
            }

            private void doInsertParse(String text, int offset) {
                int startIdx = findStartPos(text, offset);
                int endIdx = findEndPos(text, offset);
                System.out.println("idxes: " + startIdx + " " + endIdx);
                String word = text.substring(startIdx, endIdx);
                System.out.println(word);
                if (word.equals("public") || word.equals("private")) {
                    setCharacterAttributes(startIdx, endIdx - startIdx, attr, false);
                } else {
                    setCharacterAttributes(startIdx, Math.max(1, endIdx - startIdx), attrBlack, false);
                }
            }

            public void remove(int offs, int len) throws BadLocationException {
                super.remove(offs, len);
                String text = getText(0, getLength());

                if (text.isEmpty()) {
                    return;
                }

                int startIdx = findStartPos(text, Math.max(0, offs - 1));
                int endIdx = findEndPos(text, offs);
                String word = text.substring(startIdx, endIdx);
                System.out.println("idxes: " + startIdx + " " + endIdx);
                if (word.equals("public") || word.equals("private")) {
                    setCharacterAttributes(startIdx, endIdx - startIdx, attr, false);
                } else {
                    setCharacterAttributes(startIdx, Math.max(1, endIdx - startIdx), attrBlack, false);
                }
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

            private int findEndPos(String text, int initOffset) {
                int endIdx = initOffset;
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
