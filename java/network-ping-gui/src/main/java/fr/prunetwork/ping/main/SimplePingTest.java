package fr.prunetwork.ping.main;

import fr.prunetwork.ping.LongTaskListener;
import fr.prunetwork.ping.core.SimplePing;
import fr.prunetwork.ping.StatusHook;
import org.jetbrains.annotations.NotNull;

/**
 * @author Jean-Pierre PRUNARET
 * @since 19/04/2014
 */
public class SimplePingTest {

    private SimplePingTest() {
    }

    public static void main(String... args) {
        @NotNull final LongTaskListenerImpl listener = new LongTaskListenerImpl();

        final String google = "www.google.com";
        final String localhost = "localhost";
        final String localhostIP = "127.0.0.1";
        final String unreachable = "255.255.255.255";

        new SimplePing(google, new SimpleStatusHookImpl(google), listener).run();
        new SimplePing(localhost, new SimpleStatusHookImpl(localhost), listener).run();
        new SimplePing(localhostIP, new SimpleStatusHookImpl(localhostIP), listener).run();
        new SimplePing(unreachable, new SimpleStatusHookImpl(unreachable), listener).run();
    }

    static class SimpleStatusHookImpl implements StatusHook {

        @NotNull
        private final String hostname;

        SimpleStatusHookImpl(@NotNull final String hostname) {
            this.hostname = hostname;
        }

        @Override
        public void setStatus(boolean status) {
            final String reachStatus = status ? "" : "not ";
            System.out.println(hostname + " is " + reachStatus + "reachable");
        }
    }

    static class LongTaskListenerImpl implements LongTaskListener {

        @Override
        public void setWorking(boolean isWorking) {
            // Do nothing
        }
    }
}
