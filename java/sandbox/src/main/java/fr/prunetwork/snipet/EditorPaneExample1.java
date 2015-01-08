package fr.prunetwork.snipet;

/*
Core SWING Advanced Programming
By Kim Topley
ISBN: 0 13 083292 8
Publisher: Prentice Hall
*/

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

public class EditorPaneExample1 extends JFrame {
    public EditorPaneExample1() {
        super("JEditorPane Example 1");

        pane = new JEditorPane();
        pane.setEditable(false); // Read-only
        getContentPane().add(new JScrollPane(pane), "Center");

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout(4, 4));
        JLabel urlLabel = new JLabel("URL: ", JLabel.RIGHT);
        panel.add(urlLabel, "West");
        textField = new JTextField(32);
        panel.add(textField, "Center");

        getContentPane().add(panel, "South");

        // Change page based on text field
        textField.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                String url = textField.getText();
                try {
                    // Try to display the page
                    pane.setPage(url);
                } catch (IOException e) {
                    JOptionPane.showMessageDialog(pane, new String[]{
                            "Unable to open file", url}, "File Open Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch (Exception evt) {
        }

        JFrame f = new EditorPaneExample1();

        f.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent evt) {
                System.exit(0);
            }
        });
        f.setSize(500, 400);
        f.setVisible(true);
    }

    private JEditorPane pane;

    private JTextField textField;
}
