package fr.prunetwork.snipet.html;
/*
Core SWING Advanced Programming
By Kim Topley
ISBN: 0 13 083292 8
Publisher: Prentice Hall
*/

import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.ChangedCharSetException;
import javax.swing.text.Document;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.net.URLConnection;
import java.util.StringTokenizer;

public class HtmlPanelDemo2 extends JFrame {
    static final String spaces = "                    ";
    static final String LOAD_TIME = "Load time: ";
    private JCheckBox onlineLoad;
    private HTMLDocumentLoader loader;
    private JLabel loadingState;
    private JLabel timeLabel;
    private JLabel loadedType;
    private JTextField textField;
    private JEditorPane pane;
    private long startTime;

    public HtmlPanelDemo2() {
        super("JEditorPane Example 9");
        pane = new JEditorPane();
        pane.setEditable(false); // Read-only
        getContentPane().add(new JScrollPane(pane), "Center");

        // Build the panel of controls
        JPanel panel = new JPanel();

        panel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.gridwidth = 1;
        c.gridheight = 1;
        c.anchor = GridBagConstraints.EAST;
        c.fill = GridBagConstraints.NONE;
        c.weightx = 0.0;
        c.weighty = 0.0;

        JLabel urlLabel = new JLabel("URL: ", JLabel.RIGHT);
        panel.add(urlLabel, c);
        JLabel loadingLabel = new JLabel("State: ", JLabel.RIGHT);
        c.gridy = 1;
        panel.add(loadingLabel, c);
        JLabel typeLabel = new JLabel("Type: ", JLabel.RIGHT);
        c.gridy = 2;
        panel.add(typeLabel, c);
        c.gridy = 3;
        panel.add(new JLabel(LOAD_TIME), c);

        c.gridy = 4;
        c.gridwidth = 2;
        c.weightx = 1.0;
        c.anchor = GridBagConstraints.WEST;
        onlineLoad = new JCheckBox("Online Load");
        panel.add(onlineLoad, c);
        onlineLoad.setSelected(true);
        onlineLoad.setForeground(typeLabel.getForeground());

        c.gridx = 1;
        c.gridy = 0;
        c.anchor = GridBagConstraints.EAST;
        c.fill = GridBagConstraints.HORIZONTAL;

        textField = new JTextField(32);
        panel.add(textField, c);
        loadingState = new JLabel(spaces, JLabel.LEFT);
        loadingState.setForeground(Color.black);
        c.gridy = 1;
        panel.add(loadingState, c);
        loadedType = new JLabel(spaces, JLabel.LEFT);
        loadedType.setForeground(Color.black);
        c.gridy = 2;
        panel.add(loadedType, c);
        timeLabel = new JLabel("");
        c.gridy = 3;
        panel.add(timeLabel, c);

        getContentPane().add(panel, "South");

        // Change page based on text field
        textField.addActionListener(evt -> {
            String url = textField.getText();

            try {
                // Check if the new page and the old
                // page are the same.
                URL newURL = new URL(url);
                URL loadedURL = pane.getPage();
                if (loadedURL != null && loadedURL.sameFile(newURL)) {
                    return;
                }

                // Try to display the page
                textField.setEnabled(false); // Disable input
                textField.paintImmediately(0, 0, textField.getSize().width,
                        textField.getSize().height);
                setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                // Busy cursor
                loadingState.setText("Loading...");
                loadingState.paintImmediately(0, 0,
                        loadingState.getSize().width,
                        loadingState.getSize().height);
                loadedType.setText("");
                loadedType.paintImmediately(0, 0,
                        loadedType.getSize().width,
                        loadedType.getSize().height);

                timeLabel.setText("");
                timeLabel.paintImmediately(0, 0,
                        timeLabel.getSize().width,
                        timeLabel.getSize().height);

                startTime = System.currentTimeMillis();

                // Choose the loading method
                if (onlineLoad.isSelected()) {
                    // Usual load via setPage
                    pane.setPage(url);
                    loadedType.setText(pane.getContentType());
                } else {
                    pane.setContentType("text/html");
                    loadedType.setText(pane.getContentType());
                    if (loader == null) {
                        loader = new HTMLDocumentLoader();
                    }
                    HTMLDocument doc = loader.loadDocument(new URL(url));
                    loadComplete();
                    pane.setDocument(doc);
                    displayLoadTime();
                }
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(
                        pane,
                        new String[]{"Unable to open file", url},
                        "File Open Error",
                        JOptionPane.ERROR_MESSAGE);
                loadingState.setText("Failed");
                textField.setEnabled(true);
                setCursor(Cursor.getDefaultCursor());
            }
        });

        // Listen for page load to complete
        pane.addPropertyChangeListener(e -> {
            if (e.getPropertyName().equals("page")) {
                loadComplete();
                displayLoadTime();
            }
        });
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception evt) {
        }

        JFrame f = new HtmlPanelDemo2();
        f.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent evt) {
                System.exit(0);
            }
        });
        f.setSize(500, 400);
        f.setVisible(true);
    }

    private void loadComplete() {
        loadingState.setText("Page loaded.");
        textField.setEnabled(true); // Allow entry of new URL
        setCursor(Cursor.getDefaultCursor());
    }

    private void displayLoadTime() {
        double loadingTime = ((double) (System.currentTimeMillis() - startTime)) / 1000d;
        timeLabel.setText(loadingTime + " seconds");
    }
}

class HTMLDocumentLoader {
    private static HTMLEditorKit kit;
    private static HTMLEditorKit.Parser parser;

    static {
        kit = new HTMLEditorKit();
    }

    private HTMLDocument loadDocument(HTMLDocument doc, URL url, String charSet)
            throws IOException {
        doc.putProperty(Document.StreamDescriptionProperty, url);

    /*
     * This loop allows the document read to be retried if the character
     * encoding changes during processing.
     */
        InputStream in = null;
        boolean ignoreCharSet = false;

        for (; ; ) {
            try {
                // Remove any document content
                doc.remove(0, doc.getLength());

                URLConnection urlc = url.openConnection();
                in = urlc.getInputStream();

                @NotNull final Reader reader;
                if (charSet == null) {
                    reader = new InputStreamReader(in);
                } else {
                    reader = new InputStreamReader(in, charSet);
                }

                HTMLEditorKit.Parser parser = getParser();
                HTMLEditorKit.ParserCallback htmlReader = getParserCallback(doc);
                parser.parse(reader, htmlReader, ignoreCharSet);
                htmlReader.flush();

                // All done
                break;
            } catch (BadLocationException ex) {
                // Should not happen - throw an IOException
                throw new IOException(ex.getMessage());
            } catch (ChangedCharSetException e) {
                // The character set has changed - restart
                charSet = getNewCharSet(e);

                // Prevent recursion by suppressing further exceptions
                ignoreCharSet = true;

                // Close original input stream
                in.close();

                // Continue the loop to read with the correct encoding
            }
        }

        return doc;
    }

    public HTMLDocument loadDocument(URL url, String charSet)
            throws IOException {
        return loadDocument((HTMLDocument) kit.createDefaultDocument(), url,
                charSet);
    }

    public HTMLDocument loadDocument(URL url) throws IOException {
        return loadDocument(url, null);
    }

    // Methods that allow customization of the parser and the callback
    public synchronized HTMLEditorKit.Parser getParser() {
        if (parser == null) {
            try {
                Class c = Class
                        .forName("javax.swing.text.html.parser.ParserDelegator");
                parser = (HTMLEditorKit.Parser) c.newInstance();
            } catch (Throwable e) {
            }
        }
        return parser;
    }

    public synchronized HTMLEditorKit.ParserCallback getParserCallback(HTMLDocument doc) {
        return doc.getReader(0);
    }

    protected String getNewCharSet(ChangedCharSetException e) {
        String spec = e.getCharSetSpec();
        if (e.keyEqualsCharSet()) {
            // The event contains the new CharSet
            return spec;
        }

        // The event contains the content type
        // plus ";" plus qualifiers which may
        // contain a "charset" directive. First
        // remove the content type.
        int index = spec.indexOf(";");
        if (index != -1) {
            spec = spec.substring(index + 1);
        }

        // Force the string to lower case
        spec = spec.toLowerCase();

        StringTokenizer st = new StringTokenizer(spec, " \t=", true);
        boolean foundCharSet = false;
        boolean foundEquals = false;
        while (st.hasMoreTokens()) {
            String token = st.nextToken();
            if (token.equals(" ") || token.equals("\t")) {
                continue;
            }
            if (!foundCharSet && !foundEquals
                    && token.equals("charset")) {
                foundCharSet = true;
                continue;
            } else if (!foundEquals && token.contentEquals("=")) {
                foundEquals = true;
                continue;
            } else if (foundEquals && foundCharSet) {
                return token;
            }

            // Not recognized
            foundCharSet = false;
            foundEquals = false;
        }

        // No charset found - return a guess
        return "8859_1";
    }
}

