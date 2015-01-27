package fr.prunetwork.graphviz;

import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.io.File;

/**
 * @author Jean-Pierre PRUNARET
 */
final class GraphTest {
    private GraphTest() {
    }

    private static void test() throws Exception {
        @NotNull Graph graph = new Graph();
        @NotNull final GraphvizExportType type = GraphvizExportType.PNG;
        @NotNull final GraphvizRenderer renderer = GraphvizRenderer.NEATO;

        Node a = graph.getNode("A");
        Node b = graph.getNode("b");
        Node c = graph.getNode("c");
        Node d = graph.getNode("d");
        Node e = graph.getNode("e");
        Node f = graph.getNode("f");
        Node g = graph.getNode("g");

        a.linksWith(b);
        b.linksWith(c);
        c.linksWith(a);
        c.linksWith(d);
        d.linksWith(e);
        e.linksWith(f);
        g.linksWith(e);
        g.linksWith(a);

        @NotNull final Graphviz gv = graph.getGraphviz();
        @NotNull File out = new File("/tmp/out_v2." + type);   // Linux
//		File out = new File("c:/eclipse.ws/graphviz-java-api/out." + type);    // Windows
        Graphviz.writeGraphToFile(gv.getGraph(type, renderer), out);

        try {
            ShowImage.main(new String[]{"file://" + out.getPath()});
        } catch (Exception ex) {
        }
    }

    private static void test2() {
        @NotNull Graph graph = new Graph();

        Node a = graph.getNode("A");
        Node b = graph.getNode("b");
        Node c = graph.getNode("c");
        Node d = graph.getNode("d");
        Node e = graph.getNode("e");
        Node f = graph.getNode("f");
        Node g = graph.getNode("g");

        a.linksWith(b);
        b.linksWith(c);
        c.linksWith(a);
        c.linksWith(d);
        d.linksWith(e);
        e.linksWith(f);
        g.linksWith(e);
        g.linksWith(a);


        @NotNull JFrame frame = new JFrame("Display image - test2");
        @NotNull Dimension dimension = new Dimension(500, 500);
        frame.getContentPane().add(graph.getPanel(dimension));
        frame.setSize(dimension);
        frame.setVisible(true);
    }

    public static void main(String args[]) throws Exception {
        test();
        test2();
    }
}
