package edu.princeton.cs.algs4; /*************************************************************************
 *  Compilation:  javac edu.princeton.cs.algs4.FileIndex.java
 *  Execution:    java edu.princeton.cs.algs4.FileIndex file1.txt file2.txt file3.txt ...
 *  Dependencies: edu.princeton.cs.algs4.ST.java edu.princeton.cs.algs4.SET.java edu.princeton.cs.algs4.In.java edu.princeton.cs.algs4.StdIn.java edu.princeton.cs.algs4.StdOut.java
 *  Data files:   http://algs4.cs.princeton.edu/35applications/ex1.txt
 *                http://algs4.cs.princeton.edu/35applications/ex2.txt
 *                http://algs4.cs.princeton.edu/35applications/ex3.txt
 *                http://algs4.cs.princeton.edu/35applications/ex4.txt
 *
 *  % java edu.princeton.cs.algs4.FileIndex ex*.txt
 *  age
 *   ex3.txt
 *   ex4.txt
 * best
 *   ex1.txt
 * was
 *   ex1.txt
 *   ex2.txt
 *   ex3.txt
 *   ex4.txt
 *
 *  % java edu.princeton.cs.algs4.FileIndex *.txt
 *
 *  % java edu.princeton.cs.algs4.FileIndex *.java
 *
 *************************************************************************/

import java.io.File;

public class FileIndex {

    public static void main(String[] args) {

        // key = word, value = set of files containing that word
        ST<String, SET<File>> st = new ST<String, SET<File>>();

        // create inverted index of all files
        StdOut.println("Indexing files");
        for (String filename : args) {
            StdOut.println("  " + filename);
            File file = new File(filename);
            In in = new In(file);
            while (!in.isEmpty()) {
                String word = in.readString();
                if (!st.contains(word)) st.put(word, new SET<File>());
                SET<File> set = st.get(word);
                set.add(file);
            }
        }


        // read queries from standard input, one per line
        while (!StdIn.isEmpty()) {
            String query = StdIn.readString();
            if (st.contains(query)) {
                SET<File> set = st.get(query);
                for (File file : set) {
                    StdOut.println("  " + file.getName());
                }
            }
        }

    }

}
