package fr.prunetwork.graphviz;

import java.io.File;

final class GraphvizTest {
    private GraphvizTest() {
    }

    public static void main(String[] args) {
        start();
        start_v2();
//      p.start2();
    }

    /**
     * Construct a DOT graph in memory, convert it to image and store the image in the file system.
     */
    private static void start() {
        Graphviz gv = new Graphviz();
        gv.addln(Graphviz.start_graph());
        gv.addln("A -> B;");
        gv.addln("A -> C;");
        gv.addln(gv.end_graph());
        System.out.println(gv.getDotSource());

//		String type = "gif";
//		String type = "dot";
//		String type = "fig";    // open with xfig
        String type = "pdf";
//		String type = "ps";
//		String type = "svg";    // open with inkscape
//		String type = "png";
//		String type = "plain";
        File out = new File("/tmp/out." + type);   // Linux
//		File out = new File("c:/eclipse.ws/graphviz-java-api/out." + type);    // Windows
        Graphviz.writeGraphToFile(gv.getGraph(type, GraphvizRenderer.DOT), out);
    }

    /**
     * Construct a DOT graph in memory, convert it to image and store the image in the file system.
     */
    private static void start_v2() {
        Graphviz gv = new Graphviz();
        gv.addln(Graphviz.start_graph());
        gv.addln("A -> B;");
        gv.addln("A -> C;");
        gv.addln();
        gv.addln("subgraph custer_1{");
        gv.addln("C -> D;");
        gv.addln("D -> E;");
        gv.addln("E -> F;");
        gv.addln("F -> G;");
        gv.addln("};");
        gv.addln("G -> C;");
        gv.addln(Graphviz.end_graph());
        System.out.println(gv.getDotSource());

//		String type = "gif";
//		String type = "dot";
//		String type = "fig";    // open with xfig
//		String type = "pdf";
//		String type = "ps";
//		String type = "svg";    // open with inkscape
        String type = "png";
//		String type = "plain";
        File out = new File("/tmp/out_v2." + type);   // Linux
//		File out = new File("c:/eclipse.ws/graphviz-java-api/out." + type);    // Windows
        Graphviz.writeGraphToFile(gv.getGraph(type, GraphvizRenderer.DOT), out);

        try {
            ShowImage.main(new String[]{"file://" + out.getPath()});
        } catch (Exception e) {
        }
    }

    /**
     * Read the DOT source from a file, convert to image and store the image in the file system.
     */
    private static void start2() {
        String dir = "/home/jabba/eclipse2/laszlo.sajat/graphviz-java-api";     // Linux
        String input = dir + "/sample/simple.dot";
//		String input = "c:/eclipse.ws/graphviz-java-api/sample/simple.dot";    // Windows

        Graphviz gv = new Graphviz();
        gv.readSource(input);
        System.out.println(gv.getDotSource());

        String type = "gif";
//		String type = "dot";
//		String type = "fig";    // open with xfig
//		String type = "pdf";
//		String type = "ps";
//		String type = "svg";    // open with inkscape
//		String type = "png";
//		String type = "plain";
        File out = new File("/tmp/simple." + type);   // Linux
//		File out = new File("c:/eclipse.ws/graphviz-java-api/tmp/simple." + type);   // Windows
        Graphviz.writeGraphToFile(gv.getGraph(type, GraphvizRenderer.DOT), out);
    }
}

