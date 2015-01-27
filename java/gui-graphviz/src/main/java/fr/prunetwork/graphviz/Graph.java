package fr.prunetwork.graphviz;

import org.jetbrains.annotations.NotNull;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Graph {
    private static final String NEWLINE = System.getProperty("line.separator");
    private final Map<String, Node> nodes = new ConcurrentHashMap<>();
    private boolean orientedGraph;
    private final StringBuilder customInstruction = new StringBuilder();
    private final StringBuilder globalProperties = new StringBuilder();
    @NotNull
    private GraphvizRenderer renderer = GraphvizRenderer.DOT;

    public synchronized JPanel getPanel(@NotNull final Dimension dimension) {
        JPanel panel;
        try {
            @NotNull final GraphvizExportType extension = GraphvizExportType.PNG;
            @NotNull final String filename = "tmp_out_graphviz." + extension.toString();
            Graphviz.writeGraphToFile(getGraphviz().getGraph(extension, renderer), filename);
            final BufferedImage image = ImageIO.read(new File(filename));

            panel = new JPanel() {
                @Override
                public void paint(@NotNull Graphics g) {
                    double xScaleFactor = (double) getWidth() / image.getWidth();
                    double yScaleFactor = (double) getHeight() / image.getHeight();

                    double scaleFactor = xScaleFactor < yScaleFactor ? xScaleFactor : yScaleFactor;
                    scaleFactor = scaleFactor > 0.0 ? scaleFactor : 0.1;
                    scaleFactor = scaleFactor < 1.0 ? scaleFactor : 1.0;

                    int newW = (int) (image.getWidth() * scaleFactor);
                    int newH = (int) (image.getHeight() * scaleFactor);

                    g.drawImage(image, 0, 0, newW, newH, null);
                }
            };
            panel.setSize(dimension);
            panel.repaint();
        } catch (IOException ie) {
            System.err.println("Error:" + ie.getMessage());
            panel = new JPanel();
        }
        return panel;
    }

    @NotNull
    public Graphviz getGraphviz() {
        @NotNull Graphviz gv = new Graphviz();
        gv.addln(Graphviz.start_graph(isOrientedGraph()));
        gv.add(generateDot());
        gv.addln(Graphviz.end_graph());
        return gv;
    }

    public Node getNode(@NotNull final String identifier) {

        @NotNull final String localIdentifier = Node.formatIdentifier(identifier);


        Node node = nodes.get(localIdentifier);

        if (node == null) {
            node = new Node(localIdentifier, identifier);
            nodes.put(node.getIdentifier(), node);
        }

        return node;
    }

    @NotNull
    public String generateDot() {
        @NotNull final StringBuilder sb = new StringBuilder();

        makeGlobalProperties(sb);
        makeNodeDeclarations(sb);
        makeCustomInstructions(sb);
        makeNodeLinks(sb);

        return sb.toString();
    }

    void makeNodeDeclarations(@NotNull StringBuilder sb) {
        for (@NotNull Node node : nodes.values()) {

            sb.append(node.getIdentifier());
            sb.append('[');
            sb.append("label=\"").append(node.getLabel()).append("\"");
            for (@NotNull Map.Entry<NodePropertie, String> entry : node.getProperties().entrySet()) {
                sb.append(" ");
                sb.append(entry.getKey()).append("=\"").append(entry.getValue()).append("\"");
            }
            sb.append(']').append(NEWLINE);
        }
    }

    void makeNodeLinks(@NotNull StringBuilder sb) {
        for (@NotNull Node node : nodes.values()) {

            for (@NotNull Node linkedNode : node.getLinkedNodes()) {
                String linkSymbol;
                if (isOrientedGraph()) {
                    linkSymbol = " -> ";

                } else {
                    linkSymbol = " -- ";
                }

                sb.append(node.getIdentifier()).append(linkSymbol).append(linkedNode.getIdentifier()).append(NEWLINE);
            }
        }
    }

    void makeCustomInstructions(@NotNull StringBuilder sb) {
        sb.append(customInstruction);
    }

    void makeGlobalProperties(@NotNull StringBuilder sb) {
        sb.append(globalProperties);
    }

    public void appendGlobalPropertie(@NotNull String instruction) {
        globalProperties.append(instruction).append(NEWLINE);
    }

    public void appendCustomInstruction(@NotNull String instruction) {
        customInstruction.append(instruction).append(NEWLINE);
    }

    public void clear() {
        nodes.clear();
    }

    public void setOrientedGraph(boolean orientedGraph) {
        this.orientedGraph = orientedGraph;
    }

    public boolean isOrientedGraph() {
        return orientedGraph;
    }

    public void useRender(@NotNull GraphvizRenderer renderer) {

        this.renderer = renderer;
    }
}
