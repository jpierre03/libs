package fr.prunetwork.graphviz.utilities;

/**
 * @author Jean-Pierre PRUNARET
 */
public enum OperatingSystemName {
    WINDOWS("This is Windows"),
    MAC("This is Mac"),
    UNIX("This is Unix or Linux"),
    SOLARIS("This is Solaris"),
    UNKNOWN("Your OS is not supported !!");

    private final String comment;

    private OperatingSystemName(String comment) {
        this.comment = comment;
    }

    public String getComment() {
        return comment;
    }
}
