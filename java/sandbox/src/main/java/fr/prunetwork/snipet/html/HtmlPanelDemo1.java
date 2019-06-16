package fr.prunetwork.snipet.html;

/*
Core SWING Advanced Programming
By Kim Topley
ISBN: 0 13 083292 8
Publisher: Prentice Hall
*/

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

public class HtmlPanelDemo1 extends JFrame {
    private JEditorPane pane;
    private JTextField textField;

    private HtmlPanelDemo1() {
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
        textField.addActionListener(evt -> {
            String url = textField.getText();
            try {
                // Try to display the page
                pane.setPage(url);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(
                        pane,
                        new String[]{"Unable to open file", url},
                        "File Open Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception evt) {
            // Do nothing
        }

        JFrame f = new HtmlPanelDemo1();

        f.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent evt) {
                System.exit(0);
            }
        });
        f.setSize(500, 400);
        f.setVisible(true);
    }
}
