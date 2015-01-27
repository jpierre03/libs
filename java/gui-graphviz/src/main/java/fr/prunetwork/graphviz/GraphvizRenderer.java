package fr.prunetwork.graphviz;

import fr.prunetwork.graphviz.utilities.OSValidator;
import org.jetbrains.annotations.NotNull;

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
    @NotNull
    private final String linuxPath;
    @NotNull
    private final String windowsPath;
    @NotNull
    private final String macPath;

    private GraphvizRenderer(@NotNull String linuxPath, @NotNull String windowsPath, @NotNull String macPath) {
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
