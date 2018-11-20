package search;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FindDialog extends JDialog {
    private JTextPane textPane;
    private JFrame owner;
    private JTextField textField;
    private JLabel textLabel;
    private JButton findNextBtn;
    private JLabel status;

    public FindDialog(JFrame frame, JTextPane tp, int mode) {
        super(frame, "Find");
        textPane = tp;
        owner = frame;
        initUI();
        render(mode);
    }

    private void render(int mode) {
        if (mode == 0) {
            getContentPane().setBackground(UIManager.getColor("Panel.background"));
            getContentPane().setForeground(Color.black);

            textField.setBackground(UIManager.getColor("Panel.background"));
            textField.setForeground(Color.black);

            textLabel.setBackground(UIManager.getColor("Panel.background"));
            textLabel.setForeground(Color.black);

            findNextBtn.setOpaque(true);
            findNextBtn.setBackground(UIManager.getColor("Panel.background"));
            findNextBtn.setForeground(Color.black);

            status.setBackground(UIManager.getColor("Panel.background"));
            status.setForeground(Color.black);
        } else {
            getContentPane().setBackground(Color.darkGray);
            getContentPane().setForeground(Color.white);

            textField.setBackground(Color.darkGray);
            textField.setForeground(Color.white);

            textLabel.setBackground(Color.darkGray);
            textLabel.setForeground(Color.white);

            findNextBtn.setOpaque(true);
            findNextBtn.setBackground(Color.darkGray);
            findNextBtn.setForeground(Color.white);

            status.setBackground(Color.darkGray);
            status.setForeground(Color.white);
        }

    }

    private void initUI() {
        setLocation((int) (owner.getLocationOnScreen().getX() + owner.getWidth()) / 2,
                (int) (owner.getLocationOnScreen().getY() + owner.getHeight()) / 2);
        setSize(owner.getWidth() / 2, owner.getWidth() / 4);
        setLayout(null);

        textField = new JTextField();
        textLabel = new JLabel("Find: ");
        textLabel.setBounds(10, 10, 100, 20);
        textField.setBounds(textLabel.getX() + 100, textLabel.getY(), 150, textLabel.getHeight());
        add(textLabel);
        add(textField);

        status = new JLabel();
        status.setBounds(10, 50, 150, 20);
        add(status);

        findNextBtn = new JButton("Find Next");
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
