package fr.prunetwork.ping;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.Timer;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import static javax.swing.JFrame.EXIT_ON_CLOSE;

/**
 * @author Jean-Pierre PRUNARET
 * @since 19/04/2014
 */
public class Main {

    private static final Map<String, String> RUNNING_TASKS = new ConcurrentHashMap<>();
    private static final Map<String, String> LABELS = new TreeMap<>();
    private static Executor EXECUTOR_SERVICE = Executors.newFixedThreadPool(10);
    private final int relativeValue;
    private final Timer timer = new Timer("timer", true);
    private final JPanel panel = new JPanel();
    private final Map<String, StatusPanel> statusPanels = new TreeMap<>();
    private final int matrixSize;

    static {
        LABELS.put("www.google.com", "Le classique Google");
        LABELS.put("172.16.201.11", "Internet Access");
        LABELS.put("172.16.201.148", "OSN 1");
        LABELS.put("172.16.201.197", "IAO / OSN 2");
        LABELS.put("172.16.201.201", "Palpatine");
        LABELS.put("bc.antalios.com", "Serveur Application");
    }

    public Main(Collection<String> hostnames) {

        matrixSize = (int) Math.sqrt(hostnames.size()) + 1;
        relativeValue = Math.min(matrixSize, 10);
        EXECUTOR_SERVICE = Executors.newFixedThreadPool(relativeValue);

        panel.setLayout(new GridLayout(matrixSize, matrixSize, 5, 5));

        for (final String hostname : hostnames) {

            buildGuiPanel(hostname);

            buildScheduledTask(hostname);
        }

        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                JFrame frame = new JFrame("Status");
                frame.setDefaultCloseOperation(EXIT_ON_CLOSE);

                frame.add(panel);
                frame.pack();
                frame.setSize(600, 400);
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });
    }

    public static void main(String... args) {
        Collection<String> hostnames = new ArrayList<>();

        if (args.length >= 1) {
            hostnames.addAll(Arrays.asList(args));
        } else {
            hostnames.addAll(Arrays.asList(
                    "172.16.201.148",
                    "172.16.201.197",
                    "172.16.201.201",
                    "172.16.201.11",
                    "www.google.com",
                    "bc.antalios.com"
            ));
        }

        new Main(hostnames);

//        try {
//            Thread.sleep(30 * 1000);
//        } catch (InterruptedException e) {
//        }
        //System.exit(0);
    }

    private void buildGuiPanel(final String hostname) {
        final String HTML = "<html>";
        final String HTML_END = "</html>";
        final String CENTER = "<center>";
        final String CENTER_END = "</center>";
        final String BOLD = "<bold>";
        final String BOLD_END = "</bold>";

        if (!statusPanels.containsKey(hostname)) {
            try {
                SwingUtilities.invokeAndWait(new Runnable() {

                    @Override
                    public void run() {

                        final StatusPanel statusPanel = new StatusPanel();
                        statusPanel.setToolTipText(hostname);
                        statusPanel.setHostname(HTML + CENTER + BOLD + hostname + BOLD_END + CENTER_END + HTML_END);

                        final String label = LABELS.get(hostname);
                        if (label != null) {
                            statusPanel.setLabel(HTML + label + HTML_END);
                        }

                        statusPanels.put(hostname, statusPanel);

                        panel.add(hostname, statusPanel);
                    }
                });
            } catch (InterruptedException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }

    private void buildScheduledTask(final String hostname) {
/*
        timer.schedule(new TimerTask() {

            @Override
            public void run() {
                final StatusPanel statusPanel = statusPanels.get(hostname);

                EXECUTOR_SERVICE.execute(new SimplePing(hostname, statusPanel, statusPanel));
            }
        },
                1 * 1000,
                4 * 1000);
        */

        timer.scheduleAtFixedRate(
                new TimerTask() {

                    @Override
                    public void run() {
                        final StatusPanel statusPanel = statusPanels.get(hostname);
                        final boolean isTaskRunning = RUNNING_TASKS.get(hostname) == null;

                        //EXECUTOR_SERVICE.execute(new SimplePing(hostname, new SimpleStatusHookImpl(hostname), new LongTaskListenerImpl()));
                        if (isTaskRunning) {
                            EXECUTOR_SERVICE.execute(
                                    new SimplePing(
                                            hostname,
                                            statusPanel,
                                            new ListenerWrapper(
                                                    statusPanel,
                                                    statusPanel)));
                        }
                    }
                },
                relativeValue * 1000,
                relativeValue * 1000);
    }

    static class SimpleStatusHookImpl implements StatusHook {

        private final String hostname;

        public SimpleStatusHookImpl(String hostname) {
            this.hostname = hostname;
        }

        @Override
        public void setStatus(boolean status) {
            final String reacheabilityStatus = status ? "" : "not ";
            System.out.println("host: " + hostname + " is " + reacheabilityStatus + "reacheable");
        }
    }

    static class LongTaskListenerImpl implements LongTaskListener {

        @Override
        public void setWorking(boolean isWorking) {
            // Do nothing
        }
    }

    static class ListenerWrapper implements LongTaskListener {

        private static final Object MUTEX = new Object();
        private final WithHostname withHostname;
        private final LongTaskListener listener;

        ListenerWrapper(WithHostname withHostname, LongTaskListener listener) {
            this.withHostname = withHostname;
            this.listener = listener;
        }

        @Override
        public void setWorking(boolean isWorking) {
            synchronized (MUTEX) {
                listener.setWorking(isWorking);

                if (isWorking) {
                    RUNNING_TASKS.put(withHostname.getHostname(), withHostname.getHostname());
                } else {
                    RUNNING_TASKS.remove(withHostname.getHostname());
                }
            }
        }
    }
}
