package fr.prunetwork.snipet.html;
/*
 * HtmlSwingPreview.java
 *
 * Created on December 29, 2001, 9:25 PM
 */

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;

/**
 * HtmlSwingPreview
 * little tool for testing the Swing HTML rendering
 *
 * @author Gordon Mohr gojomo@bitzi.com gojomo@usa.net
 */
public class HtmlSwingPreview extends javax.swing.JFrame implements DocumentListener {
    JTextArea top;
    JEditorPane bottom;

    /**
     * Creates a new instance of HtmlSwingPreview
     */
    private HtmlSwingPreview() {
    }

    public static void main(String args[]) {
        HtmlSwingPreview instance = new HtmlSwingPreview();
        instance.init();
        instance.show();
    }

    public void init() {
        Container content = getContentPane();
        content.setBackground(Color.white);
        content.setLayout(new GridLayout(2, 1));

        top = new JTextArea();
        top.setEditable(true);
        top.setLineWrap(true);
        top.setWrapStyleWord(true);
        top.getDocument().addDocumentListener(this);
        JScrollPane topScrollPane = new JScrollPane(top);
        topScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        topScrollPane.setBorder(BorderFactory.createTitledBorder("Raw HTML"));

        bottom = new JEditorPane();
        bottom.setEditable(false);
        bottom.setContentType("text/html");
        JScrollPane bottomScrollPane = new JScrollPane(bottom);
        bottomScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        bottomScrollPane.setBorder(BorderFactory.createTitledBorder("Swing Rendered"));

        content.add(topScrollPane);
        content.add(bottomScrollPane);
        setTitle("HtmlSwingPreview");
        setSize(400, 400);
    }

    public void insertUpdate(DocumentEvent e) {
        copyContents(e);
    }

    public void removeUpdate(DocumentEvent e) {
        copyContents(e);
    }

    public void changedUpdate(DocumentEvent e) {
        copyContents(e);
    }

    private void copyContents(DocumentEvent e) {
        String backup = bottom.getText();
        // some bad HTML causes the JEditorPane to choke;
        // ignoring the error isn't fatal but causes visual
        // glitching until error is fixed
        // SO, catch it and give clue (via red insertion caret)
        // that something is amiss
        try {
            bottom.setText(top.getText());
            top.setCaretColor(Color.black);
        } catch (Exception ex) {
            bottom.setText(backup);
            top.setCaretColor(Color.red);
        }
    }
}
