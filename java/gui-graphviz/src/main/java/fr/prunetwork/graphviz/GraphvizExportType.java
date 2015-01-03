package fr.prunetwork.graphviz;

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
    private final String s;

    GraphvizExportType(String s) {
        this.s = s;
    }

    @Override
    public String toString() {
        return s;
    }
}
