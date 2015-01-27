package fr.prunetwork.graphviz;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Jean-Pierre PRUNARET
 */
public class Node {
    private final String identifier;
    private final Collection<Node> linkedNodes = new ArrayList<>();
    private String label;
    private final Map<NodePropertie, String> properties = new ConcurrentHashMap<>();

    Node(String identifier, String label) {
        this.identifier = identifier;
        this.label = label;
    }

    @NotNull
    public String getIdentifier() {
        return formatIdentifier(identifier);
    }

    public String originalIdentifier() {
        return identifier;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(@NotNull String label) {
        this.label = label;
    }

    public void linksWith(@NotNull Node linkedNode) {
        linkedNodes.add(linkedNode);
    }

    @NotNull
    public Collection<Node> getLinkedNodes() {
        return linkedNodes;
    }

    public void clearLinkedNodes() {
        linkedNodes.clear();
    }

    public void setPropertie(@NotNull NodePropertie propertie, String value) {
        properties.put(propertie, value);
    }

    @NotNull
    public Map<NodePropertie, String> getProperties() {
        return properties;
    }

    @NotNull
    public static String formatIdentifier(@NotNull String identifier) {
        return identifier.replace('-', '_').toLowerCase().trim();
    }
}
