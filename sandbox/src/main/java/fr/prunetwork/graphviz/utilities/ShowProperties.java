package fr.prunetwork.graphviz.utilities;

import java.util.Map;

/**
 * @author Jean-Pierre PRUNARET
 */

final class ShowProperties {

    public static void main(String[] args) {
        for (Map.Entry<Object, Object> e : System.getProperties().entrySet()) {
            System.out.println(e);
        }
    }
}
