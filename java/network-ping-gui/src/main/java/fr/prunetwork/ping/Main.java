package fr.prunetwork.ping;

import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.Timer;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * @author Jean-Pierre PRUNARET
 * @since 19/04/2014
 */
public class Main {

    @NotNull
    private static final Map<String, String> RUNNING_TASKS = new ConcurrentHashMap<>();
    @NotNull
    private static final Map<String, String> LABELS = new TreeMap<>();
    @NotNull
    private static Executor EXECUTOR_SERVICE = Executors.newFixedThreadPool(10);
    private final int relativeValue;
    @NotNull
    private final Timer timer = new Timer("timer", true);
    @NotNull
    private final JPanel panel = new JPanel();
    @NotNull
    private final Map<String, StatusPanel> statusPanels = new TreeMap<>();

    static {
        LABELS.put("www.google.com", "Le classique Google");
        LABELS.put("172.16.201.1", "Serveur Video");
        LABELS.put("172.16.201.11", "Internet Access");
        LABELS.put("172.16.201.20", "Camera");
        LABELS.put("172.16.201.21", "Camera");
        LABELS.put("172.16.201.22", "Camera");
        LABELS.put("172.16.201.23", "Camera");
        LABELS.put("172.16.201.24", "Camera");
        LABELS.put("172.16.201.25", "Camera");
        LABELS.put("172.16.201.26", "Camera");
        LABELS.put("172.16.201.31", "Copieur Etage Antalios");
        LABELS.put("172.16.201.33", "TELEM Imprimante à Picot");
        LABELS.put("172.16.201.86", "TELEM Imprimante Couleur");
        LABELS.put("172.16.201.148", "OSN 1");
        LABELS.put("172.16.201.196", "IAO bureau Thamer");
        LABELS.put("172.16.201.197", "IAO bureau JPP");
        LABELS.put("172.16.201.201", "Palpatine");
        LABELS.put("bc.antalios.com", "Serveur Application");
    }

    private Main(@NotNull final Collection<String> hostnames) {

        final int matrixSize = (int) Math.sqrt(hostnames.size()) + 1;
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
                @NotNull JFrame frame = new JFrame("Status");
                frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

                frame.add(panel);
                frame.pack();
                frame.setSize(600, 400);
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });
    }

    public static void main(@NotNull String... args) {
        @NotNull Collection<String> hostnames = new ArrayList<>();

        if (args.length >= 1) {
            hostnames.addAll(Arrays.asList(args));
        } else {
            hostnames.addAll(Arrays.asList(
                    "172.16.201.1",
                    "172.16.201.11",
                    "172.16.201.20",
                    "172.16.201.21",
                    "172.16.201.22",
                    "172.16.201.23",
                    "172.16.201.24",
                    "172.16.201.25",
                    "172.16.201.26",
                    "172.16.201.30",
                    "172.16.201.31",
                    "172.16.201.32",
                    "172.16.201.33",
                    "172.16.201.40",
                    "172.16.201.50",
                    "172.16.201.86",
                    /*"172.16.201.148",*/
                    "172.16.201.196",
                    "172.16.201.197",
                    "172.16.201.201",
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
        @NotNull final String HTML = "<html>";
        @NotNull final String HTML_END = "</html>";
        @NotNull final String CENTER = "<center>";
        @NotNull final String CENTER_END = "</center>";
        @NotNull final String BOLD = "<bold>";
        @NotNull final String BOLD_END = "</bold>";

        if (!statusPanels.containsKey(hostname)) {
            try {
                SwingUtilities.invokeAndWait(() -> {

                    @NotNull final StatusPanel statusPanel = new StatusPanel();
                    statusPanel.setToolTipText(hostname);
                    statusPanel.setHostname(HTML + CENTER + BOLD + hostname + BOLD_END + CENTER_END + HTML_END);

                    final String label = LABELS.get(hostname);
                    if (label != null) {
                        statusPanel.setLabel(HTML + label + HTML_END);
                    }

                    statusPanels.put(hostname, statusPanel);

                    panel.add(hostname, statusPanel);
                });
            } catch (@NotNull Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void buildScheduledTask(@NotNull final String hostname) {

        timer.scheduleAtFixedRate(
                new TimerTask() {

                    @Override
                    public void run() {
                        final StatusPanel statusPanel = statusPanels.get(hostname);
                        final boolean isTaskRunning = RUNNING_TASKS.get(hostname) == null;

                        if (isTaskRunning) {
                            EXECUTOR_SERVICE.execute(
                                    new SimplePing(
                                            hostname,
                                            statusPanel,
                                            new ListenerWrapper(
                                                    statusPanel,
                                                    statusPanel
                                            )
                                    )
                            );
                        }
                    }
                },
                relativeValue * 1000,
                relativeValue * 1000);
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
