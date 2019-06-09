package fr.prunetwork.ping.main;

import fr.prunetwork.ping.PingTaskListener;
import fr.prunetwork.ping.core.MonitoringTarget;
import fr.prunetwork.ping.core.SimplePingTask;
import org.jetbrains.annotations.NotNull;

/**
 * @author Jean-Pierre PRUNARET
 * @since 19/04/2014
 */
public class SimplePingTest {

    private SimplePingTest() {
        // Do nothing
    }

    public static void main(String... args) {
        final MonitoringTarget google = new MonitoringTarget("www.google.com");
        final MonitoringTarget localhost = new MonitoringTarget("localhost");
        final MonitoringTarget localhostIP = new MonitoringTarget("127.0.0.1");
        final MonitoringTarget unreachable = new MonitoringTarget("255.255.255.255");

        new SimplePingTask(google, new PingTaskListenerImpl(google)).run();
        new SimplePingTask(localhost, new PingTaskListenerImpl(localhost)).run();
        new SimplePingTask(localhostIP, new PingTaskListenerImpl(localhostIP)).run();
        new SimplePingTask(unreachable, new PingTaskListenerImpl(unreachable)).run();
    }

    static class PingTaskListenerImpl implements PingTaskListener {

        @NotNull
        private final String hostname;

        PingTaskListenerImpl(@NotNull final MonitoringTarget target) {
            this.hostname = target.getHostname();
        }

        @Override
        public void interrogationStarted() {
            // Do nothing
        }

        public void interrogationFinished(boolean isReachable) {
            final String reachStatus = isReachable ? "" : "not ";
            System.out.printf("%s is %s reachable%n", hostname, reachStatus);
        }
    }
}
