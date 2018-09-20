package io;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class OpenFileAgent{

    public String[] read() {
        JFileChooser jFileChooser = new JFileChooser();
        jFileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        jFileChooser.showOpenDialog(null);
        File file = jFileChooser.getSelectedFile();
        String path = file.getPath();
        if (PathDB.containsPath(path)) {
            return null;
        } else {
            PathDB.addPath(path);
        }

        StringBuilder sb = new StringBuilder();
        try {
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line).append('\n');
            }

            bufferedReader.close();
            fileReader.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return new String[] {file.getName(), sb.toString()};
    }

}
