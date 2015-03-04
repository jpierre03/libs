package fr.prunetwork.ping;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.net.InetAddress;

/**
 * @author Jean-Pierre PRUNARET
 * @since 18/04/2014
 */
public final class SimplePing
        implements Runnable {

    @NotNull
    private final String hostname;
    @NotNull
    private final StatusHook hooks;
    @NotNull
    private final LongTaskListener taskListener;
    private final boolean DEBUG = false;

    public SimplePing(@NotNull final String hostname,
                      @NotNull final StatusHook hooks,
                      @NotNull final LongTaskListener taskListener) {
        this.hostname = hostname;
        this.hooks = hooks;
        this.taskListener = taskListener;

        if (hostname.trim().isEmpty()) {
            throw new AssertionError();
        }
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
     * <p>
     * The timeout value, in milliseconds, indicates the maximum amount of time
     * the try should take. If the operation times out before getting an
     * answer, the host is deemed unreachable. A negative value will result
     * in an IllegalArgumentException being thrown.
     *
     * @param timeout the time, in milliseconds, before the call aborts
     * @return a <code>boolean</code> indicating if the address is reachable.
     */
    public boolean pingHostByJavaClass(@NotNull final String host, int timeout) {
        boolean isreachable = false;

        try {
            isreachable = InetAddress.getByName(host).isReachable(timeout);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        if (DEBUG) {
            final String reachabilityStatus = isreachable ? "" : "not ";
            System.out.println("host: " + host + " is " + reachabilityStatus + "reachable");
        }
        return isreachable;
    }

    public boolean pingHostByCommand(@NotNull final String host) {
        if (host.trim().isEmpty()) {
            throw new AssertionError("hostname empty");
        }
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

    @NotNull
    private String buildCommand(@NotNull String host) {
        if (host.trim().isEmpty()) {
            throw new AssertionError("hostname empty");
        }
        final String command;

        final String operatingSystemName = System.getProperty("os.name");
        final boolean isRunningOnWindows = operatingSystemName.startsWith("Windows");
        final boolean isRunningOnMac = operatingSystemName.startsWith("Mac OS");

        /** Build command line, with system specific commands */
        if (isRunningOnWindows) {
            command = "ping -w 500 -n 1 " + host;
        } else if (isRunningOnMac) {
            command = "ping -t 2 -c 1 " + host;
        } else {
            command = "ping -c 1 " + host;
        }

        if (DEBUG) {
            System.out.println("My OS :" + operatingSystemName);
            System.out.println("Command: " + command);
        }

        return command;
    }

    private boolean executeCommand(String strCommand) throws IOException, InterruptedException {
        final boolean isReachable;

        /** Execute command by Operating System*/
        Process myProcess = Runtime.getRuntime().exec(strCommand);
        myProcess.waitFor();

        isReachable = (myProcess.exitValue() == 0);

        return isReachable;
    }
}
