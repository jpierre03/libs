package fr.prunetwork.sandbox.utilities.color;

import static fr.prunetwork.sandbox.utilities.color.ConsoleColor.*;

/**
 * @author Jean-Pierre PRUNARET
 * @since 09/05/2014
 */
public final class StringColorUtilities {

    //private static boolean disableColor = false;
    // private static boolean enableColor = (!System.getProperty("os.name").startsWith("Windows")) && !disableColor;
    private static boolean enableColor = true;

    private StringColorUtilities() {
    }

    public static String black() {
        return coloredString(ANSI_BLACK);
    }

    public static String blue() {
        return coloredString(ANSI_BLUE);
    }

    public static String purple() {
        return coloredString(ANSI_PURPLE);
    }

    public static String white() {
        return coloredString(ANSI_WHITE);
    }

    public static String cyan() {
        return coloredString(ANSI_CYAN);
    }

    public static String green() {
        return coloredString(ANSI_GREEN);
    }

    public static String reset() {
        return coloredString(ANSI_RESET);
    }

    public static String yellow() {
        return coloredString(ANSI_YELLOW);
    }

    public static String red() {
        return coloredString(ANSI_RED);
    }

    public static String coloredString(final ConsoleColor color) {
        return enableColor ? color.toString() : "";
    }

    public static void setEnableColor(final boolean enableColor) {
        StringColorUtilities.enableColor = enableColor;
    }
}
