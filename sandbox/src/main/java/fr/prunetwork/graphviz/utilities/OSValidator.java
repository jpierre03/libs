package fr.prunetwork.graphviz.utilities;

/**
 * @author Jean-Pierre PRUNARET
 */
public final class OSValidator {

    private static final String OS = System.getProperty("os.name").toLowerCase();

    private OSValidator() {
    }

    public static void main(String[] args) {
        System.out.println(getOS().getComment());
    }

    private static OperatingSystemName getOS() {

        OperatingSystemName value;

        if (isWindows()) {
            value = OperatingSystemName.WINDOWS;
        } else if (isMac()) {
            value = OperatingSystemName.MAC;
        } else if (isUnix()) {
            value = OperatingSystemName.UNIX;
        } else if (isSolaris()) {
            value = OperatingSystemName.SOLARIS;
        } else {
            value = OperatingSystemName.UNKNOWN;
        }
        return value;
    }

    public static boolean isWindows() {
        return (OS.contains("win"));
    }

    public static boolean isMac() {
        return (OS.contains("mac"));
    }

    public static boolean isUnix() {
        return (OS.contains("nix")
                || OS.contains("nux")
                || OS.contains("aix"));
    }

    public static boolean isSolaris() {
        return (OS.contains("sunos"));
    }
}
