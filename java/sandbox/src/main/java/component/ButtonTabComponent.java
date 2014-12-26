package component;

import javax.swing.*;
import javax.swing.plaf.basic.BasicButtonUI;
import java.awt.*;
import java.awt.event.*;

/**
 * Component to be used as tabComponent; Contains a JLabel to show the text and
 * a JButton to close the tab it belongs to.
 * <p/>
 * The closeablility (the capacity of being closed with a small X button) of a tab is defined at construction time.
 */
public class ButtonTabComponent
        extends JPanel {

    private static final MouseListener buttonMouseListener = new MouseAdapter() {
        @Override
        public void mouseEntered(MouseEvent e) {
            Component component = e.getComponent();
            if (component instanceof AbstractButton) {
                AbstractButton button = (AbstractButton) component;
                button.setBorderPainted(true);
            }
        }
    };
    private final JTabbedPane tabbedPane;

    public ButtonTabComponent(final JTabbedPane tabbedPane, boolean isCloseable) {
        /** unset default FlowLayout' gaps */
        super(new FlowLayout(FlowLayout.LEFT, 0, 0));
        if (tabbedPane == null) {
            throw new IllegalArgumentException("TabbedPane is null");
        }
        this.tabbedPane = tabbedPane;
        setOpaque(false);

        /** make JLabel read titles from JTabbedPane */
        JLabel label = new JLabel() {
            @Override
            public String getText() {
                int i = tabbedPane.indexOfTabComponent(ButtonTabComponent.this);
                if (i != -1) {
                    return tabbedPane.getTitleAt(i);
                }
                return null;
            }
        };

        add(label);
        /** add more space between the label and the button */
        label.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 5));
        /** tab button */
        if (isCloseable) {
            JButton button = new TabButton();
            add(button);
        }
        /** add more space to the top of the component */
        setBorder(BorderFactory.createEmptyBorder(2, 0, 0, 0));
    }

    private class TabButton
            extends JButton
            implements ActionListener {

        TabButton() {
            int size = 17;
            setPreferredSize(new Dimension(size, size));
            setToolTipText("close this tab");
            /** Make the button looks the same for all Laf's */
            setUI(new BasicButtonUI());
            /** Make it transparent */
            setContentAreaFilled(false);
            /** No need to be focusable */
            setFocusable(false);
            setBorder(BorderFactory.createEtchedBorder());
            setBorderPainted(false);
            /** Making nice rollover effect */
            /** we use the same listener for all buttons */
            addMouseListener(buttonMouseListener);
            setRolloverEnabled(true);
            /** Close the proper tab by clicking the button */
            addActionListener(this);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            int i = tabbedPane.indexOfTabComponent(ButtonTabComponent.this);
            if (i != -1) {
                tabbedPane.remove(i);
            }
        }

        /**
         * We doesn't want to update UI for this button
         */
        @Override
        public void updateUI() {
        }

        /**
         * paint the cross
         */
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g.create();
            /** shift the image for pressed buttons */
            if (getModel().isPressed()) {
                g2.translate(1, 1);
            }
            g2.setStroke(new BasicStroke(2));
            g2.setColor(Color.BLACK);
            if (getModel().isRollover()) {
                g2.setColor(Color.MAGENTA);
            }
            int delta = 6;
            g2.drawLine(delta, delta, getWidth() - delta - 1, getHeight() - delta - 1);
            g2.drawLine(getWidth() - delta - 1, delta, delta, getHeight() - delta - 1);
            //g2.dispose();
        }
    }
}
