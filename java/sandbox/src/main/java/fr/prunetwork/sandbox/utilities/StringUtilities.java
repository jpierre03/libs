package fr.prunetwork.sandbox.utilities;

/**
 * @author Jean-Pierre PRUNARET
 * @Date 26/11/2013
 */
public final class StringUtilities {

    private StringUtilities() {
    }

    /**
     * Thus methods replace a string according to a matching pattern
     * <p/>
     * Example :
     * <p/>
     * <code>recursiveReplacer(line, ":  :", ":");</code> replace all consecutive ": :" (exemple ": : : : :") with ":"
     * <p/>
     * <code>recursiveReplacer(tmpLine, "\\s+", " ");</code> replace all consecutive blank space by only one space " ".
     *
     * @param string             the string to be analysed
     * @param matchPattern       a pattern
     * @param replacementPattern
     * @return
     */
    public static String recursiveReplacer(String string, String matchPattern, String replacementPattern) {
        boolean isIdempotent = false;

        while (!isIdempotent) {
            String replaced = string.replaceAll(matchPattern, replacementPattern);
            if (replaced.contentEquals(string)) {
                isIdempotent = true;
            }
            string = replaced;
        }
        return string;
    }
}
