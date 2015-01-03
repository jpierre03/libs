package edu.princeton.cs.algs4;

/**
 * **********************************************************************
 * Compilation:  javac edu.princeton.cs.algs4.LookupIndex.java
 * Execution:    java edu.princeton.cs.algs4.LookupIndex movies.txt "/"
 * Dependencies: edu.princeton.cs.algs4.ST.java edu.princeton.cs.algs4.Queue.java edu.princeton.cs.algs4.In.java edu.princeton.cs.algs4.StdIn.java edu.princeton.cs.algs4.StdOut.java
 * Data files:   http://algs4.cs.princeton.edu/35applications/aminoI.csv
 * http://algs4.cs.princeton.edu/35applications/movies.txt
 * <p/>
 * % java edu.princeton.cs.algs4.LookupIndex aminoI.csv ","
 * Serine
 * TCT
 * TCA
 * TCG
 * AGT
 * AGC
 * TCG
 * Serine
 * <p/>
 * % java edu.princeton.cs.algs4.LookupIndex movies.txt "/"
 * Bacon, Kevin
 * Animal House (1978)
 * Apollo 13 (1995)
 * Beauty Shop (2005)
 * Diner (1982)
 * Few Good Men, A (1992)
 * Flatliners (1990)
 * Footloose (1984)
 * Friday the 13th (1980)
 * ...
 * Tin Men (1987)
 * DeBoy, David
 * Blumenfeld, Alan
 * ...
 * <p/>
 * ***********************************************************************
 */

public class LookupIndex {

    public static void main(String[] args) {
        String filename = args[0];
        String separator = args[1];
        In in = new In(filename);

        ST<String, Queue<String>> st = new ST<String, Queue<String>>();
        ST<String, Queue<String>> ts = new ST<String, Queue<String>>();

        while (in.hasNextLine()) {
            String line = in.readLine();
            String[] fields = line.split(separator);
            String key = fields[0];
            for (int i = 1; i < fields.length; i++) {
                String val = fields[i];
                if (!st.contains(key)) st.put(key, new Queue<String>());
                if (!ts.contains(val)) ts.put(val, new Queue<String>());
                st.get(key).enqueue(val);
                ts.get(val).enqueue(key);
            }
        }

        StdOut.println("Done indexing");

        // read queries from standard input, one per line
        while (!StdIn.isEmpty()) {
            String query = StdIn.readLine();
            if (st.contains(query))
                for (String vals : st.get(query))
                    StdOut.println("  " + vals);
            if (ts.contains(query))
                for (String keys : ts.get(query))
                    StdOut.println("  " + keys);
        }

    }

}
