package fr.prunetwork.graphviz;

import javax.swing.*;
import java.awt.*;
import java.io.File;

/**
 * @author Jean-Pierre PRUNARET
 */
final class GraphTest {
    private GraphTest() {
    }

    private static void test() {
        Graph graph = new Graph();

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

        final Graphviz gv = graph.getGraphviz();
        File out = new File("/tmp/out_v2." + GraphvizExportType.PNG);   // Linux
//		File out = new File("c:/eclipse.ws/graphviz-java-api/out." + type);    // Windows
        Graphviz.writeGraphToFile(gv.getGraph(GraphvizExportType.PNG, GraphvizRenderer.NEATO), out);

        try {
            ShowImage.main(new String[]{"file://" + out.getPath()});
        } catch (Exception ex) {
        }
    }

    private static void test2() {
        Graph graph = new Graph();

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


        JFrame frame = new JFrame("Display image - test2");
        Dimension dimension = new Dimension(500, 500);
        frame.getContentPane().add(graph.getPanel(dimension));
        frame.setSize(dimension);
        frame.setVisible(true);
    }

    public static void main(String args[]) {
        test();
        test2();
    }
}
