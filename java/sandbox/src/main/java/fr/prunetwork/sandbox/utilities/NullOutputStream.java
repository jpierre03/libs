package fr.prunetwork.sandbox.utilities;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Define a stream where all data are pushed into the void.
 *
 * @author Jean-Pierre PRUNARET
 * @Date 22/09/13 17:35
 */
public class NullOutputStream
        extends OutputStream {

    @Override
    public void write(int i) throws IOException {
        //do nothing
    }
}
