package fr.prunetwork.sandbox.utilities;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * This class is a tool to simplify stack trace display
 *
 * @author Jean-Pierre PRUNARET
 */
public class StackTraceDisplay {

    private final String s;

    StackTraceDisplay(Throwable t) {
        if (t != null) {
            StringWriter errors = new StringWriter();
            t.printStackTrace(new PrintWriter(errors));
            s = errors.toString();
        } else {
            s = "";
        }
    }

    public StackTraceDisplay(Exception e) {
        this(e.getCause());
    }

    @Override
    public String toString() {
        return s;
    }
}
