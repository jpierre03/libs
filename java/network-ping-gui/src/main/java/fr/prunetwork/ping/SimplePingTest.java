package fr.prunetwork.ping;

/**
 * @author Jean-Pierre PRUNARET
 * @since 19/04/2014
 */
public class SimplePingTest {

    private SimplePingTest() {
    }

    public static void main(String... args) {
        final LongTaskListenerImpl listener = new LongTaskListenerImpl();

        String google = "www.google.com";
        final String localhost = "localhost";
        final String localhostIP = "127.0.0.1";
        final String unreachable = "255.255.255.255";

        new SimplePing(google, new SimpleStatusHookImpl(google), listener).run();
        new SimplePing(localhost, new SimpleStatusHookImpl(localhost), listener).run();
        new SimplePing(localhostIP, new SimpleStatusHookImpl(localhostIP), listener).run();
        new SimplePing(unreachable, new SimpleStatusHookImpl(unreachable), listener).run();
    }

    static class SimpleStatusHookImpl implements StatusHook {

        private final String hostname;

        SimpleStatusHookImpl(String hostname) {
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
