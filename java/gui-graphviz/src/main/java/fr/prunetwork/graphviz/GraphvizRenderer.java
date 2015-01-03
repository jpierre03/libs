package fr.prunetwork.graphviz;

import fr.prunetwork.graphviz.utilities.OSValidator;

/**
 * @author Jean-Pierre PRUNARET
 */
public enum GraphvizRenderer {
    DOT(
            "/usr/bin/dot",
            "c:/Program Files/Graphviz2.30.1/bin/dot.exe",
            "/usr/local/bin/dot"
    ),
    NEATO(
            "/usr/bin/neato",
            "c:/Program Files/Graphviz2.30.1/bin/neato.exe",
            "/usr/local/bin/neato"
    );
    private final String linuxPath;
    private final String windowsPath;
    private final String macPath;

    private GraphvizRenderer(String linuxPath, String windowsPath, String macPath) {
        this.linuxPath = linuxPath;
        this.windowsPath = windowsPath;
        this.macPath = macPath;
    }

    public String getPath() {
        String path;
        if (OSValidator.isWindows()) {
            path = windowsPath;
        } else if (OSValidator.isUnix()) {
            path = linuxPath;
        } else if (OSValidator.isMac()) {
            path = macPath;
        } else {
            throw new RuntimeException("Graphviz: Operating System not supported, ask support");
        }

        return path;
    }
}
