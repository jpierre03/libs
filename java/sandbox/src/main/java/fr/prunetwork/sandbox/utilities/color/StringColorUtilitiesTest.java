package fr.prunetwork.sandbox.utilities.color;

import static fr.prunetwork.sandbox.utilities.color.StringColorUtilities.*;

/**
 * @author Jean-Pierre PRUNARET
 * @date 15/01/2015.
 */
public class StringColorUtilitiesTest {
    private StringColorUtilitiesTest() {
    }

    public static void main(String args[]) {

        StringColorUtilities.setEnableColor(true);
        printMessageWithColoredParts();

        StringColorUtilities.setEnableColor(false);
        printMessageWithColoredParts();
    }

    private static void printMessageWithColoredParts() {
        final StringBuilder sb = new StringBuilder();
        sb.append("Bonjour,").append(System.lineSeparator());
        {
            sb.append(blue()).append("un texte bleu").append(reset()).append(" et normal").append(System.lineSeparator());
            sb.append(red()).append("un texte rouge").append(reset()).append(" et normal").append(System.lineSeparator());
            sb.append(green()).append("un texte vert").append(reset()).append(" et normal").append(System.lineSeparator());
            sb.append(yellow()).append("un texte jaune").append(reset()).append(" et normal").append(System.lineSeparator());
            sb.append(cyan()).append("un texte cyan").append(reset()).append(" et normal").append(System.lineSeparator());
            sb.append(purple()).append("un texte violet").append(reset()).append(" et normal").append(System.lineSeparator());
        }

        System.out.print(sb);
    }
}
