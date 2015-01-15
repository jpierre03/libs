package fr.prunetwork.sandbox.utilities.color;

/**
 * @author Jean-Pierre PRUNARET
 * @since 09/05/2014
 */
public final class StringColorUtilities {

    private static final boolean disableColor = false;
    private static final boolean enableColor = (!System.getProperty("os.name").startsWith("Windows")) && !disableColor;

    private StringColorUtilities() {
    }

    public static String cyan() {
        return coloredString(ConsoleColor.ANSI_CYAN);
    }

    public static String green() {
        return coloredString(ConsoleColor.ANSI_GREEN);
    }

    public static String reset() {
        return coloredString(ConsoleColor.ANSI_RESET);
    }

    public static String yellow() {
        return coloredString(ConsoleColor.ANSI_YELLOW);
    }

    public static String red() {
        return coloredString(ConsoleColor.ANSI_RED);
    }

    public static String coloredString(ConsoleColor color) {
        return enableColor ? color.toString() : "";
    }
}
