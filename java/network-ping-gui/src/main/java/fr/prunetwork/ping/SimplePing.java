package fr.prunetwork.ping;

import java.io.IOException;
import java.net.InetAddress;

/**
 * @author Jean-Pierre PRUNARET
 * @since 18/04/2014
 */
public final class SimplePing
        implements Runnable {

    private final String hostname;
    private final StatusHook hooks;
    private final LongTaskListener taskListener;
    private boolean DEBUG = false;

    public SimplePing(String hostname, StatusHook hooks, LongTaskListener taskListener) {
        this.hostname = hostname;
        this.hooks = hooks;
        this.taskListener = taskListener;

        assert (hostname != null) && !hostname.trim().isEmpty();
        assert hooks != null;
        assert taskListener != null;
    }

    @Override
    public void run() {
        taskListener.setWorking(true);
        final boolean result = pingHostByCommand(hostname);
        hooks.setStatus(result);
        taskListener.setWorking(false);
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
    public boolean pingHostByJavaClass(String host, int timeout) {
        boolean isreachable = false;

        try {
            isreachable = InetAddress.getByName(host).isReachable(timeout);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        if (DEBUG) {
            final String reacheabilityStatus = isreachable ? "" : "not ";
            System.out.println("host: " + host + " is " + reacheabilityStatus + "reacheable");
        }
        return isreachable;
    }

    public boolean pingHostByCommand(String host) {
        assert host != null : "no hostname defined";
        assert !host.trim().isEmpty() : "hostname empty";
        boolean isReachable = false;

        try {
            final String strCommand = buildCommand(host);

            isReachable = executeCommand(strCommand);

            if (DEBUG) {
                final String reacheabilityStatus = isReachable ? "" : "not ";
                System.out.println("host: " + host + " is " + reacheabilityStatus + "reacheable");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return isReachable;
    }

    private String buildCommand(String host) {
        String command = "";

        final String operatingSystemName = System.getProperty("os.name");
        final boolean isRunningOnWindows = operatingSystemName.startsWith("Windows");
        final boolean isRunningOnMac = operatingSystemName.startsWith("Mac OS");

        /** Build command line, with system specific commands */
        if (isRunningOnWindows) {
            command = "ping -n 1 " + host;
        }
        if (isRunningOnMac) {
            command = "ping -t 2 -c 1 " + host;
            //command = "ping -c 1 " + host;
        }
        if (!isRunningOnWindows
                && !isRunningOnMac) {
            command = "ping -c 1 " + host;
        }

        if (DEBUG) {
            System.out.println("My OS :" + operatingSystemName);
            System.out.println("Command: " + command);
        }

        return command;
    }

    private boolean executeCommand(String strCommand) throws IOException, InterruptedException {
        final boolean reachable;

        /** Execute the command constructed */
        Process myProcess = Runtime.getRuntime().exec(strCommand);
        myProcess.waitFor();
        if (myProcess.exitValue() == 0) {
            reachable = true;
        } else {
            reachable = false;
        }

        return reachable;
    }
}
