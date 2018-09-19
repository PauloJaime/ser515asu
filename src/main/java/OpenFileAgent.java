import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class OpenFileAgent implements ActionListener {
    private String path;

    private JTextArea textArea;

    public OpenFileAgent(JTextArea text) {
        textArea = text;
    }

    public String getPath() {
        return path;
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        JFileChooser jFileChooser = new JFileChooser();
        jFileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        jFileChooser.showOpenDialog(null);
        File file = jFileChooser.getSelectedFile();
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

        textArea.append(sb.toString());
        path = file.getPath();
    }

}
