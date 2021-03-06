package fr.prunetwork.ping.core;

import fr.prunetwork.ping.PingTaskListener;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.net.InetAddress;

/**
 * @author Jean-Pierre PRUNARET
 * @since 18/04/2014
 */
public final class SimplePingTask implements Runnable {

    @NotNull
    private final MonitoringTarget target;
    @NotNull
    private final PingTaskListener taskListener;
    private final boolean DEBUG = false;

    public SimplePingTask(@NotNull final MonitoringTarget target, @NotNull final PingTaskListener taskListener) {
        this.target = target;
        this.taskListener = taskListener;

        if (target.getHostname().trim().isEmpty()) {
            throw new IllegalArgumentException("Hostname must be defined");
        }
    }

    @Override
    public void run() {
        taskListener.interrogationStarted();

        final boolean result;
        final boolean resultJava = pingHostByJavaClass(target.getHostname());

        if (resultJava) {
            result = true;
        } else {
            result = pingHostByCommand(target.getHostname());
        }

        taskListener.interrogationFinished(result);
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
    public boolean pingHostByJavaClass(@NotNull final String hostname, int timeout) {
        boolean isreachable = false;

        try {
            isreachable = InetAddress.getByName(hostname).isReachable(timeout);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        if (DEBUG) {
            final String reachabilityStatus = isreachable ? "" : "not ";
            System.out.println("host: " + hostname + " is " + reachabilityStatus + "reachable");
        }
        return isreachable;
    }

    public boolean pingHostByJavaClass(@NotNull final String hostname) {
        return pingHostByJavaClass(hostname, 3 * 1000);
    }

    public boolean pingHostByCommand(@NotNull final String host) {
        if (host.trim().isEmpty()) {
            throw new AssertionError("target empty");
        }
        boolean isReachable = false;

        try {
            final String strCommand = buildCommand(host);

            isReachable = executeCommand(strCommand);

            if (DEBUG) {
                final String reacheabilityStatus = isReachable ? "" : "not ";
                System.out.printf("host: %s is %s reacheable%n", host, reacheabilityStatus);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return isReachable;
    }

    @NotNull
    private String buildCommand(@NotNull String host) {
        if (host.trim().isEmpty()) {
            throw new AssertionError("target empty");
        }
        final String command;

        final String operatingSystemName = System.getProperty("os.name");
        final boolean isRunningOnLinux = operatingSystemName.startsWith("Linux");
        final boolean isRunningOnWindows = operatingSystemName.startsWith("Windows");
        final boolean isRunningOnMac = operatingSystemName.startsWith("Mac OS");

        /** Build command line, with system specific commands */
        if (isRunningOnLinux) {
            command = "ping -w 1 -w 1 -c 1 " + host;
        } else if (isRunningOnWindows) {
            command = "ping -w 500 -n 1 " + host;
        } else if (isRunningOnMac) {
            command = "ping -t 2 -c 1 " + host;
        } else {
            command = "ping -c 1 " + host;
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
