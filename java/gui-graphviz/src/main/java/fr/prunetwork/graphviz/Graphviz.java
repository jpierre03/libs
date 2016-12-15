package fr.prunetwork.graphviz;

/* ****************************************************************************
 *                                                                            *
 *              (c) Copyright 2003 Laszlo Szathmary                           *
 *                                                                            *
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms of the GNU Lesser General Public License as published by   *
 * the Free Software Foundation; either version 2.1 of the License, or        *
 * (at your option) any later version.                                        *
 *                                                                            *
 * This program is distributed in the hope that it will be useful, but        *
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY *
 * or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public    *
 * License for more details.                                                  *
 *                                                                            *
 * You should have received a copy of the GNU Lesser General Public License   *
 * along with this program; if not, write to the Free Software Foundation,    *
 * Inc., 675 Mass Ave, Cambridge, MA 02139, USA.                              *
 *                                                                            *
 *****************************************************************************/

import fr.prunetwork.graphviz.utilities.OSValidator;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.*;

/**
 * <dl> <dt>Purpose: Graphviz Java API <dd>
 * <p>
 * <dt>Description: <dd> With this Java class you can simply call dot from your Java programs <dt>Example usage: <dd>
 * <pre>
 *    Graphviz gv = new Graphviz();
 *    gv.addln(gv.start_graph());
 *    gv.addln("A -> B;");
 *    gv.addln("A -> C;");
 *    gv.addln(gv.end_graph());
 *    System.out.println(gv.getDotSource());
 *
 *    String type = "png";
 *    File out = new File("out." + type);   // out.gif in this example
 *    gv.writeGraphToFile( gv.getGraph(type ), out );
 * </pre>
 * </dd>
 * <p>
 * </dl>
 *
 * @author Laszlo Szathmary (<a href="jabba.laci@gmail.com">jabba.laci@gmail.com</a>)
 * @version v0.1, 2003/12/04 (December) -- first release
 */
public class Graphviz {
    /**
     * Local newline symbol
     */
    private static final String NEWLINE = System.getProperty("line.separator");
    /**
     * The dir. where temporary files will be created.
     */
    private static String TEMP_DIR = "/tmp";
    /**
     * The source of the graph written in dot language.
     */
    private final StringBuilder graph = new StringBuilder();

    static {
        if (OSValidator.isWindows()) {
            TEMP_DIR = "c:/temp";
        } else if (OSValidator.isUnix()) {
            TEMP_DIR = "/tmp";
        } else if (OSValidator.isMac()) {
            TEMP_DIR = System.getProperty("java.io.tmpdir");
        } else {
            throw new RuntimeException("Graphviz: Operating System not supported, ask support");
        }
    }

    /**
     * Constructor: creates a new Graphviz object that will contain a graph.
     */
    public Graphviz() {
    }

    /**
     * Writes the graph's image in a file.
     *
     * @param img  A byte array containing the image of the graph.
     * @param file Name of the file to where we want to write.
     * @return Success: 1, Failure: -1
     */
    public static int writeGraphToFile(@NotNull byte[] img, @NotNull String file) {
        @NotNull File to = new File(file);
        return writeGraphToFile(img, to);
    }

    /**
     * Writes the graph's image in a file.
     *
     * @param img A byte array containing the image of the graph.
     * @param to  A File object to where we want to write.
     * @return Success: 1, Failure: -1
     */
    public static int writeGraphToFile(@NotNull byte[] img, @NotNull File to) {
        try (@NotNull FileOutputStream fos = new FileOutputStream(to)) {
            fos.write(img);
        } catch (IOException ioe) {
            return -1;
        }
        return 1;
    }

    /**
     * It will call the external dot program, and return the image in binary format.
     *
     * @param dot  Source of the graph (in dot language).
     * @param type Type of the output image to be produced, e.g.: gif, dot, fig, pdf, ps, svg, png.
     * @return The image of the graph in .gif format.
     */
    @NotNull
    private static byte[] get_img_stream(@NotNull File dot,
                                         @NotNull GraphvizExportType type,
                                         @NotNull GraphvizRenderer renderer) throws Exception {
        @NotNull byte[] img_stream;

        try {
            @NotNull File img = File.createTempFile("graph_", "." + type, new File(Graphviz.TEMP_DIR));
            Runtime rt = Runtime.getRuntime();

            // patch by Mike Chenault
            @NotNull String[] args = {renderer.getPath(), "-T" + type, dot.getAbsolutePath(), "-o", img.getAbsolutePath()};
            Process p = rt.exec(args);

            p.waitFor();

            try (@NotNull FileInputStream in = new FileInputStream(img.getAbsolutePath())) {
                img_stream = new byte[in.available()];
                in.read(img_stream);
            }

            if (!img.delete()) {
                System.err.println("Warning: " + img.getAbsolutePath() + " could not be deleted!");
            }
        } catch (IOException ex) {
            System.err.println("Error:    in I/O processing of tempfile in dir " + Graphviz.TEMP_DIR);
            System.err.println("       or in calling external command");
            ex.printStackTrace();
            throw ex;
        } catch (InterruptedException ex) {
            System.err.println("Error: the execution of the external program was interrupted");
            ex.printStackTrace();
            throw ex;
        }

        return img_stream;
    }

    /**
     * Writes the source of the graph in a file, and returns the written file as a File object.
     *
     * @param str Source of the graph (in dot language).
     * @return The file (as a File object) that contains the source of the graph.
     */
    @NotNull
    private static File writeDotSourceToFile(@NotNull String str) throws Exception {
        @Nullable File temp;

        try {
            temp = File.createTempFile("graph_", ".dot.tmp", new File(Graphviz.TEMP_DIR));

            try (@NotNull FileWriter fout = new FileWriter(temp)) {
                fout.write(str);
            }
        } catch (IOException e) {
            System.err.println("Error: I/O error while writing the dot source to temp file!");
            throw e;
        }

        return temp;
    }

    /**
     * Returns a string that is used to start an oriented graph.
     *
     * @return A string to begin a graph.
     */
    public static String start_graph() {
        return start_graph(true);
    }

    public static String start_graph(boolean isOriented) {
        String value;
        if (isOriented) {
            value = "digraph G {";
        } else {
            value = "graph G {";
        }
        return value;
    }

    /**
     * Returns a string that is used to end a graph.
     *
     * @return A string to close a graph.
     */
    @NotNull
    public static String end_graph() {
        return "}";
    }

    /**
     * Returns the graph's source description in dot language.
     *
     * @return Source of the graph in dot language.
     */
    @NotNull
    public String getDotSource() {
        return graph.toString();
    }

    /**
     * Adds a string to the graph's source (without newline).
     */
    public void add(String line) {
        graph.append(line);
    }

    /**
     * Adds a string to the graph's source (with newline).
     */
    public void addln(String line) {
        add(line);
        addln();
    }

    /**
     * Adds a newline to the graph's source.
     */
    public void addln() {
        graph.append(NEWLINE);
    }

    /**
     * Returns the graph as an image in binary format.
     *
     * @param type Type of the output image to be produced, e.g.: gif, dot, fig, pdf, ps, svg, png.
     * @return A byte array containing the image of the graph.
     */
    @NotNull
    public byte[] getGraph(@NotNull GraphvizExportType type, @NotNull GraphvizRenderer renderer) throws Exception {
        @NotNull File dot = writeDotSourceToFile(getDotSource());

        @NotNull byte[] img_stream = get_img_stream(dot, type, renderer);

        if (!dot.delete()) {
            System.err.println("Warning: " + dot.getAbsolutePath() + " could not be deleted!");
        }
        return img_stream;
    }

    /**
     * Read a DOT graph from a text file.
     *
     * @param input Input text file containing the DOT graph source.
     */
    public void readSource(@NotNull String input) {
        @NotNull StringBuilder sb = new StringBuilder();

        try (
                @NotNull FileInputStream fis = new FileInputStream(input);
                @NotNull DataInputStream dis = new DataInputStream(fis);
                @NotNull BufferedReader br = new BufferedReader(new InputStreamReader(dis));
        ) {

            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }

        graph.setLength(0);
        graph.append(sb);
    }
}

