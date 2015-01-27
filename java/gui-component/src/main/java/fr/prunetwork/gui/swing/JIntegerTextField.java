package fr.prunetwork.gui.swing;

/**
 * @author Jean-Pierre PRUNARET
 */

import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.Document;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

/**
 * A JTextField which only accepts integer values as input.
 * <p>
 * Use methods setValue/getValue to retrieve the integer value of the
 * JTextField.
 *
 * @author Werner Randelshofer
 * @version 1.0 August 1, 2007 Created.
 */
public class JIntegerTextField extends JTextField {
    private int value;
    private int minimum = Integer.MIN_VALUE;
    private int maximum = Integer.MAX_VALUE;
    private DocumentHandler documentHandler;

    /**
     * Creates new instance.
     */
    public JIntegerTextField() {
        initComponents();

        addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                selectAll();
            }

            @Override
            public void focusLost(FocusEvent e) {
                updateText();
            }

        });
    }

    @Override
    public void setDocument(@NotNull Document doc) {
        Document oldValue = getDocument();
        super.setDocument(doc);

        if (documentHandler == null) {
            documentHandler = new DocumentHandler();
        }

        if (oldValue != null) {
            oldValue.removeDocumentListener(documentHandler);
        }

        doc.addDocumentListener(documentHandler);
        updateValue();
    }

    private void updateValue() {
        try {
            int newValue = Integer.decode(getText());
            if (newValue >= minimum && newValue <= maximum) {
                setValue(newValue);
            }
        } catch (NumberFormatException ex) {
            // Do nothing
        }
    }

    private void updateText() {
        SwingUtilities.invokeLater(() -> {
            if (!isFocusOwner()) {
                setText(Integer.toString(value));
            }
        });
    }

    private void setValue(int newValue) {
        int oldValue = value;
        value = newValue;

        if (newValue != oldValue) {
            firePropertyChange("value", oldValue, newValue);
            updateText();
        }
    }

    public int getValue() {
        return value;
    }

    public void setMinimum(int newValue) {
        int oldValue = value;
        minimum = newValue;
        firePropertyChange("minimum", oldValue, newValue);
    }

    public int getMinimum() {
        return minimum;
    }

    public void setMaximum(int newValue) {
        int oldValue = value;
        maximum = newValue;
        firePropertyChange("maximum", oldValue, newValue);
    }

    public int getMaximum() {
        return maximum;
    }

    private void initComponents() {
    }

    private class DocumentHandler implements DocumentListener {
        @Override
        public void insertUpdate(DocumentEvent e) {
            updateValue();
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            updateValue();
        }

        @Override
        public void changedUpdate(DocumentEvent e) {
            updateValue();
        }

    }
}
