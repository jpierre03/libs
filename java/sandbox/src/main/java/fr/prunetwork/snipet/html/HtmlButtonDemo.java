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

import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.net.MalformedURLException;
import java.net.URL;

/*
 * HtmlButtonDemo.java
 */
public class HtmlButtonDemo extends JPanel
        implements ActionListener {
    final JButton b1, b2, b3;

    HtmlButtonDemo() {
        final ImageIcon leftButtonIcon = createImageIconURL(
                "http://upload.wikimedia.org/wikipedia/commons/thumb/b/b5/France_road_sign_B21-2.svg/240px-France_road_sign_B21-2.svg.png?uselang=fr");
        final ImageIcon middleButtonIcon = createImageIconURL(
                "http://upload.wikimedia.org/wikipedia/commons/thumb/0/09/France_road_sign_B16.svg/240px-France_road_sign_B16.svg.png?uselang=fr");
        final ImageIcon rightButtonIcon = createImageIconURL(
                "http://upload.wikimedia.org/wikipedia/commons/thumb/9/9c/France_road_sign_B21-1.svg/240px-France_road_sign_B21-1.svg.png?uselang=fr");

        b1 = new JButton(
                ""
                        + "<html>"
                        + "<center>"
                        + "<b><u>D</u>isable</b>"
                        + "<center/>"
                        + "<br />"
                        + "<font color=#ffffdd>middle button</font>"
                        + "</html>",
                leftButtonIcon
        );
        Font font = b1.getFont().deriveFont(Font.PLAIN);
        b1.setFont(font);
        b1.setVerticalTextPosition(AbstractButton.CENTER);
        b1.setHorizontalTextPosition(AbstractButton.LEADING); /** aka LEFT, for left-to-right locales */
        b1.setMnemonic(KeyEvent.VK_D);
        b1.setActionCommand("disable");

        b2 = new JButton("middle button", middleButtonIcon);
        b2.setFont(font);
        b2.setForeground(new Color(0xffffdd));
        b2.setVerticalTextPosition(AbstractButton.BOTTOM);
        b2.setHorizontalTextPosition(AbstractButton.CENTER);
        b2.setMnemonic(KeyEvent.VK_M);

        b3 = new JButton(""
                + "<html>"
                + "<center>"
                + "<b><u>E</u>nable</b>"
                + "</center>"
                + "<br />"
                + "<font color=#ffffdd>middle button</font>"
                + "</html>",
                rightButtonIcon
        );
        b3.setFont(font);
        /** Use the default text position of CENTER, TRAILING (RIGHT). */
        b3.setMnemonic(KeyEvent.VK_E);
        b3.setActionCommand("enable");
        b3.setEnabled(false);

        /** Listen for actions on buttons 1 and 3. */
        b1.addActionListener(this);
        b3.addActionListener(this);

        b1.setToolTipText("Click this button to disable the middle button.");
        b2.setToolTipText("This middle button does nothing when you click it.");
        b3.setToolTipText("Click this button to enable the middle button.");

        /** Add Components to this container, using the default FlowLayout. */
        add(b1);
        add(b2);
        add(b3);
    }

    /**
     * Returns an ImageIcon, or null if the path was invalid.
     */
    @Nullable
    protected static ImageIcon createImageIcon(String path) {
        java.net.URL imgURL = HtmlButtonDemo.class.getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL);
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }

    /**
     * Returns an ImageIcon, or null if the path was invalid.
     */
    @Nullable
    private static ImageIcon createImageIconURL(String url) {
        URL imgURL = null;
        try {
            imgURL = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        if (imgURL != null) {
            return new ImageIcon(imgURL);
        } else {
            System.err.println("Couldn't find file: " + url);
            return null;
        }
    }

    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event dispatch thread.
     */
    private static void createAndShowGUI() {
        /** Create and set up the window. */
        JFrame frame = new JFrame("HtmlButtonDemo");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        /** Add content to the window. */
        frame.add(new HtmlButtonDemo());

        /** Display the window. */
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        /** Schedule a job for the event dispatch thread: creating and showing this application's GUI. */
        javax.swing.SwingUtilities.invokeLater(HtmlButtonDemo::createAndShowGUI);
    }

    public void actionPerformed(ActionEvent e) {
        if ("disable".equals(e.getActionCommand())) {
            b2.setEnabled(false);
            b1.setEnabled(false);
            b3.setEnabled(true);
        } else {
            b2.setEnabled(true);
            b1.setEnabled(true);
            b3.setEnabled(false);
        }
    }
}
