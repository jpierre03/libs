package edu.princeton.cs.algs4; /*************************************************************************
 *  Compilation:  javac edu.princeton.cs.algs4.Whitelist.java
 *  Execution:    java edu.princeton.cs.algs4.Whitelist whitelist.txt < data.txt
 *  Dependencies: StaticSetOfInts.java edu.princeton.cs.algs4.In.java edu.princeton.cs.algs4.StdOut.java
 *
 *  Data files:   http://algs4.cs.princeton.edu/11model/tinyW.txt
 *                http://algs4.cs.princeton.edu/11model/tinyT.txt
 *                http://algs4.cs.princeton.edu/11model/largeW.txt
 *                http://algs4.cs.princeton.edu/11model/largeT.txt
 *
 *  edu.princeton.cs.algs4.Whitelist filter.
 *
 *
 *  % java edu.princeton.cs.algs4.Whitelist tinyW.txt < tinyT.txt
 *  50
 *  99
 *  13
 *
 *  % java edu.princeton.cs.algs4.Whitelist largeW.txt < largeT.txt | more
 *  499569
 *  984875
 *  295754
 *  207807
 *  140925
 *  161828
 *  [367,966 total values]
 *
 *************************************************************************/

/**
 * The <tt>edu.princeton.cs.algs4.Whitelist</tt> class provides a client for reading in
 * a set of integers from a file; reading in a sequence of integers
 * from standard input; and printing to standard output those
 * integers not in the whitelist.
 * <p/>
 * For additional documentation, see <a href="http://algs4.cs.princeton.edu/12oop">Section 1.2</a> of
 * <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
 *
 * @author Robert Sedgewick
 * @author Kevin Wayne
 */
public class Whitelist {

    /**
     * Reads in a sequence of integers from the whitelist file, specified as
     * a command-line argument. Reads in integers from standard input and
     * prints to standard output those integers that are not in the file.
     */
    public static void main(String[] args) {
        In in = new In(args[0]);
        int[] white = in.readAllInts();
        StaticSETofInts set = new StaticSETofInts(white);

        // Read key, print if not in whitelist.
        while (!StdIn.isEmpty()) {
            int key = StdIn.readInt();
            if (!set.contains(key))
                StdOut.println(key);
        }
    }
}
