package fr.prunetwork.ping;

/**
 * @author Jean-Pierre PRUNARET
 * @since 19/04/2014
 */
public class SimplePingTest {

    private SimplePingTest() {

    }

    public static void main(String... args) {
        final SimpleStatusHookImpl hook = new SimpleStatusHookImpl();
        final LongTaskListenerImpl listener = new LongTaskListenerImpl();
        new SimplePing("www.google.com", hook, listener).run();
        new SimplePing("172.16.201.11", hook, listener).run();
        new SimplePing("localhost", hook, listener).run();
        new SimplePing("127.0.0.1", hook, listener).run();
    }

    static class SimpleStatusHookImpl implements StatusHook {
        @Override
        public void setStatus(boolean status) {
            final String reacheabilityStatus = status ? "" : "not ";
            System.out.println(" is " + reacheabilityStatus + "reacheable");
        }
    }

    static class LongTaskListenerImpl implements LongTaskListener {

        @Override
        public void setWorking(boolean isWorking) {
            // Do nothing
        }
    }
}
