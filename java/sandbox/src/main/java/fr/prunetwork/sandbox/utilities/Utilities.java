package fr.prunetwork.sandbox.utilities;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Amira Methni
 * @author Jean-Pierre PRUNARET
 */
public final class Utilities {

    private Utilities() {
    }

    public static final String NEWLINE = System.getProperty("line.separator");

    /**
     * méthode permettant de retourner la un hashmap contenant la liste des epa voisines en prenant
     * comme entrée l'identicateur de la porte
     */
    public static Map<String, String> hashmapEpaVoisines(Map<String, String> epaMap, String epaIdentifier) {
        final String[] roomIdentifiers = getRoomIdentifiers(epaIdentifier);
        final Map<String, String> hmEpaV = new HashMap<>();

        for (Map.Entry<String, String> me : epaMap.entrySet()) {

            for (String aIdP : roomIdentifiers) {
                String[] idPindice = getRoomIdentifiers(me.getKey());
                for (String anIdPindice : idPindice) {
                    if (aIdP != null && aIdP.equals(anIdPindice)) {
                        //L'EPA en cours est envoyée avec
                        hmEpaV.put(me.getKey(), me.getValue());
                    }
                }
            }
        }

        return hmEpaV;
    }

    /**
     * Fournir les identifiants des deux pièces d'une EPA
     */
    public static String[] getRoomIdentifiers(String epaIdentifier) {
        String[] idP = new String[2];
        if (epaIdentifier.contains("-")) {
            String[] IdpTemp = epaIdentifier.split("-");
            idP[0] = IdpTemp[0].substring(1);
            idP[1] = IdpTemp[1];
        } else {
            //Il s'agit d'une pièce n'ayant pas de voisins
            idP[0] = epaIdentifier.substring(1);
        }

        return idP;
    }
}
