package fr.prunetwork.graphviz.utilities;

import org.jetbrains.annotations.NotNull;

/**
 * @author Jean-Pierre PRUNARET
 */
public enum OperatingSystemName {
    WINDOWS("This is Windows"),
    MAC("This is Mac"),
    UNIX("This is Unix or Linux"),
    SOLARIS("This is Solaris"),
    UNKNOWN("Your OS is not supported !!");

    @NotNull
    private final String comment;

    OperatingSystemName(@NotNull String comment) {
        this.comment = comment;
    }

    @NotNull
    public String getComment() {
        return comment;
    }
}
