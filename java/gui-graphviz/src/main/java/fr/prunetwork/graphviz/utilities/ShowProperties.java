package fr.prunetwork.graphviz.utilities;

/**
 * @author Jean-Pierre PRUNARET
 */

final class ShowProperties {

    public static void main(String[] args) {
        System.getProperties()
                .entrySet()
                .forEach(System.out::println);
    }
}
