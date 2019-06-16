package fr.prunetwork.sandbox.utilities;

import java.io.IOException;
import java.net.InetAddress;

/**
 * @author Jean-Pierre PRUNARET
 * @since 18/04/2014
 */
public final class PingUtilities {

    private PingUtilities() {

    }

    /**
     * Test whether that address is reachable. Best effort is made by the
     * implementation to try to reach the host, but firewalls and server
     * configuration may block requests resulting in a unreachable status
     * while some specific ports may be accessible.
     * A typical implementation will use ICMP ECHO REQUESTs if the
     * privilege can be obtained, otherwise it will try to establish
     * a TCP connection on port 7 (Echo) of the destination host.
     * <p/>
     * The timeout value, in milliseconds, indicates the maximum amount of time
     * the try should take. If the operation times out before getting an
     * answer, the host is deemed unreachable. A negative value will result
     * in an IllegalArgumentException being thrown.
     *
     * @param timeout the time, in milliseconds, before the call aborts
     * @return a <code>boolean</code> indicating if the address is reachable.
     */
    public static boolean pingHostByJavaClass(String host, int timeout) {
        boolean isreachable = false;

        try {
            isreachable = InetAddress.getByName(host).isReachable(timeout);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        final String reacheabilityStatus = isreachable ? "" : "not ";
        System.out.println("host: " + host + " is " + reacheabilityStatus + "reacheable");
        return isreachable;
    }

    public static boolean pingHostByCommand(String host) {
        boolean isReachable = false;
        try {
            String strCommand = "";
            //System.out.println("My OS :" + System.getProperty("os.name"));
            if (System.getProperty("os.name").startsWith("Windows")) {
                /** construct command for Windows Operating system */
                strCommand = "ping -n 1 " + host;
            } else {
                /** construct command for Linux and OSX */
                strCommand = "ping -c 1 " + host;
            }
            //System.out.println("Command: " + strCommand);
            // Execute the command constructed
            Process myProcess = Runtime.getRuntime().exec(strCommand);
            myProcess.waitFor();
            isReachable = myProcess.exitValue() == 0;
        } catch (Exception e) {
            e.printStackTrace();
            isReachable = false;
        }

        final String reacheabilityStatus = isReachable ? "" : "not ";
        System.out.println("host: " + host + " is " + reacheabilityStatus + "reacheable");
        return isReachable;
    }

    public static void main(String... args) {
        pingHostByJavaClass("www.google.com", 1000);
        pingHostByJavaClass("172.16.201.11", 1000);

        pingHostByCommand("www.google.com");
        pingHostByCommand("172.16.201.11");
    }
}
