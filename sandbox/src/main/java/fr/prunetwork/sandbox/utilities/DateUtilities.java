package fr.prunetwork.sandbox.utilities;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Utility class to manipulate Date(s).
 *
 * @author Thamer LOUATI
 * @author Jean-Pierre PRUNARET
 */
public final class DateUtilities {
    private DateUtilities() {
    }

    /**
     * méthode permettant de convertir un objet de type Date en un String
     */
    public static String dateToFormattedString(Date date) {
        final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy-(HH:mm:ss)");
        //Format pour extraire juste l'heure en string
        String formattedString = sdf.format(date);

        return formattedString;
    }

    /**
     * méthode permettant de convertir un objet de type Date en un String (pour le nom du fichier historique)
     */
    public static String dateToFormattedHistoricString(Date date) {
        final SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yy (HH-mm-ss)");
        //Format pour extraire juste l'heure en string
        String formattedString = sdf.format(date);

        return formattedString;
    }


    /**
     * méthode permettant de convertir un objet de type Date en un String
     */
    public static String shortFormattedDate(Date date) {
        final SimpleDateFormat sdf = new SimpleDateFormat("(HH:mm:ss)");
        //Format pour extraire juste l'heure en string
        String formattedString = sdf.format(date);

        return formattedString;
    }


    /**
     * méthode permettant de convertir un objet de type String en Date du jour avec l'heure indiquée
     */
    public static Date formattedHourToDate(String formattedString) {
        final SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        Date date = new Date();
        try {
            date = sdf.parse(formattedString);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * méthode permettant de convertir un objet de type String en Date et heure indiquées
     */
    public static Date formattedStringToDate(String formattedString) {
        final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy-(HH:mm:ss)");
        Date date = new Date();
        try {
            date = sdf.parse(formattedString);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date;
    }
}
