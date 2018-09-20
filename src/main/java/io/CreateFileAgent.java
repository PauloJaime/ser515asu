package io;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CreateFileAgent implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        PathDB.addPath(null);
    }

}
