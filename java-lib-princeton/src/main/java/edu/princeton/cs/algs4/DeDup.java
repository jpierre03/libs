package edu.princeton.cs.algs4;

/**
 * **********************************************************************
 * Compilation:  javac edu.princeton.cs.algs4.DeDup.java
 * Execution:    java edu.princeton.cs.algs4.DeDup < input.txt
 * Dependencies: edu.princeton.cs.algs4.SET edu.princeton.cs.algs4.StdIn.java edu.princeton.cs.algs4.StdOut.java
 * Data files:   http://algs4.cs.princeton.edu/35applications/tinyTale.txt
 * <p/>
 * Read in a list of words from standard input and print out
 * each word, removing any duplicates.
 * <p/>
 * % more tinyTale.txt
 * it was the best of times it was the worst of times
 * it was the age of wisdom it was the age of foolishness
 * it was the epoch of belief it was the epoch of incredulity
 * it was the season of light it was the season of darkness
 * it was the spring of hope it was the winter of despair
 * <p/>
 * % java edu.princeton.cs.algs4.DeDup < tinyTale.txt
 * it
 * was
 * the
 * best
 * of
 * times
 * worst
 * age
 * wisdom
 * ...
 * winter
 * despair
 * <p/>
 * ***********************************************************************
 */

public class DeDup {

    public static void main(String[] args) {
        SET<String> set = new SET<String>();

        // read in strings and add to set
        while (!StdIn.isEmpty()) {
            String key = StdIn.readString();
            if (!set.contains(key)) {
                set.add(key);
                StdOut.println(key);
            }
        }
    }
}
