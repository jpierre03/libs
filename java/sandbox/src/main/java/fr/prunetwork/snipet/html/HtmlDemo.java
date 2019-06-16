/*
 * Copyright (c) 1995, 2008, Oracle and/or its affiliates. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *
 *   - Neither the name of Oracle or the names of its
 *     contributors may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package fr.prunetwork.snipet.html;

/* HtmlDemo.java needs no other files. */

import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import static javax.swing.BorderFactory.createCompoundBorder;
import static javax.swing.BorderFactory.createEmptyBorder;
import static javax.swing.BorderFactory.createTitledBorder;

public class HtmlDemo extends JPanel
        implements ActionListener {

    @NotNull
    private final static String INITIAL_TEXT = "<html>\n" +
            "Color and font test:\n" +
            "<ul>\n" +
            "<li><font color=red>red</font>\n" +
            "<li><font color=blue>blue</font>\n" +
            "<li><font color=green>green</font>\n" +
            "<li><font size=-2>small</font>\n" +
            "<li><font size=+2>large</font>\n" +
            "<li><i>italic</i>\n" +
            "<li><b>bold</b>\n" +
            "</ul>\n";
    @NotNull
    private final JLabel theLabel = buildInputArea(INITIAL_TEXT);
    @NotNull
    private final JTextArea htmlTextArea;

    public HtmlDemo() {
        setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));

        htmlTextArea = buildHtmlArea();

        buildLayout(theLabel, htmlTextArea, this, this);
    }

    @NotNull
    private static JTextArea buildHtmlArea() {
        JTextArea area = new JTextArea(10, 20);
        area.setText(INITIAL_TEXT);

        return area;
    }

    private static void buildLayout(@NotNull final JLabel inputText,
                                    @NotNull final JTextArea htmlArea,
                                    @NotNull final ActionListener listener,
                                    @NotNull final JPanel container) {
        JScrollPane scrollPane = new JScrollPane(htmlArea);

        JButton changeTheLabel = buildUpdateButton(listener);


        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.PAGE_AXIS));
        leftPanel.setBorder(createCompoundBorder(
                createTitledBorder(
                        "Edit the HTML, then click the button"),
                createEmptyBorder(10, 10, 10, 10)));
        leftPanel.add(scrollPane);
        leftPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        leftPanel.add(changeTheLabel);

        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.PAGE_AXIS));
        rightPanel.setBorder(
                createCompoundBorder(
                        createTitledBorder("A label with HTML"),
                        createEmptyBorder(10, 10, 10, 10))
        );
        rightPanel.add(inputText);

        container.setBorder(createEmptyBorder(10, 10, 10, 10));
        container.add(leftPanel);
        container.add(Box.createRigidArea(new Dimension(10, 0)));
        container.add(rightPanel);
    }

    @NotNull
    private static JButton buildUpdateButton(@NotNull final ActionListener listener) {
        JButton button = new JButton("Change the label");

        button.setMnemonic(KeyEvent.VK_C);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.addActionListener(listener);

        return button;
    }

    @NotNull
    private static JLabel buildInputArea(final String initialText) {
        JLabel label = new JLabel(initialText) {
            public Dimension getPreferredSize() {
                return new Dimension(200, 200);
            }

            public Dimension getMinimumSize() {
                return new Dimension(200, 200);
            }

            public Dimension getMaximumSize() {
                return new Dimension(200, 200);
            }
        };

        label.setVerticalAlignment(SwingConstants.CENTER);
        label.setHorizontalAlignment(SwingConstants.CENTER);

        return label;

    }

    /**
     * React to the user pushing the Change button.
     */
    public void actionPerformed(@NotNull final ActionEvent e) {
        theLabel.setText(htmlTextArea.getText());
    }

    /**
     * Create the GUI and show it.
     * For thread safety, this method should be invoked from the event dispatch thread.
     */
    private static void createAndShowGUI() {
        //Create and set up the window.
        final JFrame frame = new JFrame("HtmlDemo");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        //Add content to the window.
        frame.add(new HtmlDemo());

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        /** Schedule a job for the event dispatch thread: creating and showing this application's GUI. */
        SwingUtilities.invokeLater(() -> {
            /** Turn off metal's use of bold fonts */
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (ClassNotFoundException | UnsupportedLookAndFeelException | IllegalAccessException | InstantiationException e) {
                // Do nothing
            }
            createAndShowGUI();
        });
    }
}
