package fr.prunetwork.graphviz;

import org.jetbrains.annotations.NotNull;

/**
 * @author Jean-Pierre PRUNARET
 * @since 03/01/15
 */
public enum GraphvizExportType {

    GIF("gif"),
    DOT("dot"),
    FIG("fig"),    // open with xfig
    PDF("pdf"),
    PS("ps"),
    SVG("svg"),    // open with inkscape
    PNG("png"),
    PLAIN("plain");
    @NotNull
    private final String s;

    GraphvizExportType(@NotNull String s) {
        this.s = s;
    }

    @NotNull
    @Override
    public String toString() {
        return s;
    }
}
