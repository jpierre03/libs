package fr.prunetwork.ping.main;

import fr.prunetwork.ping.PingTaskListener;
import fr.prunetwork.ping.core.MonitoringTarget;
import fr.prunetwork.ping.core.SimplePingTask;
import fr.prunetwork.ping.impl.PingTaskListenerEDTProxy;
import fr.prunetwork.ping.ui.StatusPanel;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.io.PrintStream;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static java.lang.String.format;
import static java.util.Objects.requireNonNull;

/**
 * @author Jean-Pierre PRUNARET
 * @since 19/04/2014
 */
public class PingGuiMain {

    @NotNull
    private static ScheduledExecutorService EXECUTOR_SERVICE = Executors.newScheduledThreadPool(10);
    private final int relativeValue;
    @NotNull
    private final JPanel panel = new JPanel();
    @NotNull
    private final Map<MonitoringTarget, StatusPanel> statusPanels = new TreeMap<>();

    private PingGuiMain(@NotNull final Collection<MonitoringTarget> targets) {

        final int matrixSize = (int) Math.sqrt(targets.size()) + 1;
        relativeValue = Math.min(matrixSize, 10);
        EXECUTOR_SERVICE = Executors.newScheduledThreadPool(relativeValue);

        panel.setLayout(new GridLayout(matrixSize, matrixSize, 5, 5));

        for (final MonitoringTarget target : targets) {

            buildGuiPanel(target);

            buildScheduledTask(target);
        }

        SwingUtilities.invokeLater(() -> {
            @NotNull JFrame frame = new JFrame("Status");
            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

            frame.add(panel);
            frame.pack();
            frame.setSize(600, 400);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }

    public static void main(@NotNull String... args) {
        final Collection<MonitoringTarget> targets = new ArrayList<>();
        final Collection<String> hostnames = new ArrayList<>();

        if (args.length >= 1) {
            hostnames.addAll(Arrays.asList(args));
        } else {
            hostnames.addAll(Arrays.asList(
                    "free.fr",
                    "www.google.com",
                    "www.antalios.com"
            ));

            for (int i = 100; i < 101; i++) {
                targets.add(new MonitoringTarget("172.16.201." + i, "ANTA." + i));
            }
        }

        hostnames.forEach(hostname -> targets.add(new MonitoringTarget(hostname)));

        showSmokepingConfiguration(targets, System.out);

        new PingGuiMain(targets);

        scheduleStop(10, TimeUnit.SECONDS);
    }

    // TODO use better approach
    private static void scheduleStop(int duration, TimeUnit unit) {
        try {
            Thread.sleep(TimeUnit.MILLISECONDS.convert(duration, unit));
        } catch (InterruptedException e) {
            // TODO logger & remove static
            e.printStackTrace();
        } finally {
            System.exit(0);
        }
    }

    // TODO extract as a MonitoringTarger Visitor (Visitor Pattern)
    private static void showSmokepingConfiguration(final Iterable<MonitoringTarget> targets, final PrintStream out) {
        final StringBuilder sb = new StringBuilder();

        for (MonitoringTarget target : targets) {
            sb.append(format("%n"));
            sb.append(format("++ %s%n", target.getHostname().replace(".", "_")));
            sb.append(format("menu = %s%n", target.getLabel()));
            sb.append(format("title = %s%n", target.getLabel()));
            sb.append(format("host = %s%n", target.getHostname()));
        }

        out.print(sb.toString());
    }

    private void buildGuiPanel(final MonitoringTarget target) {
        @NotNull final String HTML = "<html>";
        @NotNull final String HTML_END = "</html>";
        @NotNull final String CENTER = "<center>";
        @NotNull final String CENTER_END = "</center>";
        @NotNull final String BOLD = "<bold>";
        @NotNull final String BOLD_END = "</bold>";

        if (!statusPanels.containsKey(target)) {
            try {
                SwingUtilities.invokeAndWait(() -> {

                    @NotNull final StatusPanel statusPanel = new StatusPanel();
                    statusPanel.setLabel(HTML + target.getLabel() + HTML_END);
                    statusPanel.setToolTipText(target.getHostname() + " -> " + target.getLabel());
                    statusPanel.setHostname(HTML + CENTER + BOLD + target.getHostname() + BOLD_END + CENTER_END + HTML_END);

                    statusPanels.put(target, statusPanel);

                    panel.add(statusPanel);
                });
            } catch (@NotNull Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void buildScheduledTask(@NotNull final MonitoringTarget target) {
        final StatusPanel statusPanel = statusPanels.get(target);
        requireNonNull(statusPanel);

        final PingTaskListener taskEdtProxy = new PingTaskListenerEDTProxy(statusPanel);
        final SimplePingTask pingTask = new SimplePingTask(target, taskEdtProxy);

        EXECUTOR_SERVICE.scheduleAtFixedRate(pingTask, 1, 10, TimeUnit.SECONDS);
    }
}
