package fr.prunetwork.amqp.gui;

import fr.prunetwork.amqp.ExchangeType;
import fr.prunetwork.amqp.consumer.AmqpReceiver;
import fr.prunetwork.amqp.consumer.SimpleMessageConsumer;
import fr.prunetwork.amqp.gui.table.HistoryTable;
import fr.prunetwork.amqp.gui.table.MessageTableModel;
import fr.prunetwork.amqp.message.SimpleMessage;
import fr.prunetwork.amqp.producer.AmqpProducer;
import fr.prunetwork.gui.swing.table.DateCellRenderer;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static fr.prunetwork.amqp.AmqpDefaultProperties.*;

/**
 * @author Jean-Pierre PRUNARET
 * @since 01/07/2014
 */
public class AmqpGuiMain {

    private static final ExecutorService EXECUTOR = Executors.newFixedThreadPool(10);

    public static void main(String... args) throws Exception {
        @NotNull JFrame frame = new JFrame();
        frame.setTitle("Application");
        frame.setPreferredSize(new Dimension(600, 400));
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);


        @NotNull JPanel panel = new JPanel(new GridLayout(1, 3));

        addPanel(panel, Arrays.asList("#"));
        //addPanel(panel, Arrays.asList("etage.#"));
        //addPanel(panel, Arrays.asList("#.rdc.#"));

        @NotNull final JPanel commandPanel;
        {

            commandPanel = new CommandPanel(
                    new CommandPanelActionner(
                            new AmqpProducer(
                                    URI,
                                    EXCHANGE,
                                    ROUTING_KEY,
                                    ExchangeType.topic,
                                    true
                            )
                    )
            );
        }

        @NotNull JSplitPane splitPane = new JSplitPane(
                JSplitPane.HORIZONTAL_SPLIT,
                commandPanel,
                panel
        );
        splitPane.setOneTouchExpandable(true);
        splitPane.setDividerLocation(150);

        frame.getContentPane().add(splitPane);
        frame.pack();
        frame.setVisible(true);
    }

    private static void addPanel(JPanel panel, List<String> routingKeys) throws Exception {
        @NotNull final MessageTableModel messageTableModel = new MessageTableModel();
        @NotNull HistoryTable table = new HistoryTable(messageTableModel);

        table.setDefaultRenderer(Date.class, new DateCellRenderer());

        panel.add(new JScrollPane(table));

        @NotNull final MyMessageConsumer consumer = new MyMessageConsumer(messageTableModel);
        @NotNull AmqpReceiver receiver = new AmqpReceiver(
                URI,
                EXCHANGE,
                routingKeys,
                consumer,
                true
        );
        receiver.configure();

        @NotNull final Runnable runnable = () -> {
            System.out.println("******* Wait to connect");
            while (!consumer.isConnected()) {
                try {
                    Thread.sleep(100);
                } catch (Exception e) {
                    // do nothing
                }
            }

            System.out.println("******* Connected");
            while (true) {
                try {
                    consumer.consume();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        EXECUTOR.submit(runnable);
    }

    static class MyMessageConsumer extends SimpleMessageConsumer {
        private final MessageTableModel tableModel;

        public MyMessageConsumer(MessageTableModel tableModel) {
            this.tableModel = tableModel;
        }

        @NotNull
        @Override
        public SimpleMessage consume() throws InterruptedException {

            final SimpleMessage receivedMessage = super.consume();

            //receivedMessage.displayFullMessage();
            tableModel.add(receivedMessage);
            tableModel.fireTableRowsInserted(tableModel.getRowCount() - 1, tableModel.getRowCount() - 1);

            return receivedMessage;
        }
    }
}
