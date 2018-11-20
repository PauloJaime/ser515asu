package search;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FindDialog extends JDialog {
    private JTextPane textPane;
    private JFrame owner;

    public FindDialog(JFrame frame, JTextPane tp) {
        super(frame, "Find");
        textPane = tp;
        owner = frame;
        initUI();
    }

    private void initUI() {
        setLocation((int) (owner.getLocationOnScreen().getX() + owner.getWidth()) / 2,
                (int) (owner.getLocationOnScreen().getY() + owner.getHeight()) / 2);
        setSize(owner.getWidth() / 2, owner.getWidth() / 4);
        setLayout(null);

        JTextField textField = new JTextField();
        JLabel textLabel = new JLabel("Find: ");
        textLabel.setBounds(10, 10, 100, 20);
        textField.setBounds(textLabel.getX() + 100, textLabel.getY(), 150, textLabel.getHeight());
        add(textLabel);
        add(textField);

        JLabel status = new JLabel();
        status.setBounds(10, 50, 150, 20);
        add(status);

        JButton findNextBtn = new JButton("Find Next");
        findNextBtn.setBounds(10, 100, 120, 20);
        findNextBtn.addActionListener(new ActionListener() {
            private String prevText = null;
            private Pattern pattern = null;
            private Matcher matcher = null;
            private int offset = 0;

            @Override
            public void actionPerformed(ActionEvent e) {
                String word = textField.getText();
                if (word.equals("") || textPane == null) {
                    return;
                }

                if (prevText == null || !prevText.equals(word)) {
                    offset = 0;
                    prevText = word;
                    pattern = Pattern.compile(word);
                }

                matcher = pattern.matcher(textPane.getText());
                while (!matcher.find(offset)) {
                    if (offset == 0) {
                        status.setText("Cannot find: " + word);
                        status.repaint();
                        return;
                    } else {
                        offset = 0;
                    }

                }

                textPane.setSelectionStart(matcher.start());
                textPane.setSelectionEnd(offset = matcher.end());
                status.setText("");
                status.repaint();
            }

        });

        add(findNextBtn);
        setVisible(true);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
    }

}
