package fr.prunetwork.communication;

import java.io.IOException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ObjectClientTest {

    ObjectClient oc;
    /**
     * Détermine le nombre de threads qui sont exécutés en même temps
     */
    private static Executor exec = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    public static void main(String argv[]) {
        new ObjectClientTest();
    }

    private ObjectClientTest() {
        try {

            //oc = new ObjectClient("2001:470:c92d::1:2", Constants.DEFAULT_SERVER_PORT_NUMBER);
            oc = new ObjectClient("localhost", 4444);
            exec.execute(oc);
            for (int i = 0; i < 10; i++) {
                oc.write(i);
            }
        } catch (IOException ex) {
            Logger.getLogger(ObjectClientTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        oc.disconnect();
        //-----------
        try {
            Thread.sleep(10 * 1000);
        } catch (InterruptedException ex) {
            Logger.getLogger(ObjectClientTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.exit(0);
    }
}
