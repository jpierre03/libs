package fr.prunetwork;

import fr.prunetwork.amqp.ExchangeType;
import fr.prunetwork.amqp.consumer.AmqpReceiver;
import fr.prunetwork.amqp.consumer.SimpleMessageConsumer;
import fr.prunetwork.amqp.message.SimpleMessage;
import fr.prunetwork.amqp.producer.AmqpProducer;
import fr.prunetwork.gui.CommandPanel;
import fr.prunetwork.gui.CommandPanelActionner;
import fr.prunetwork.gui.swing.table.DateCellRenderer;
import fr.prunetwork.gui.table.HistoryTable;
import fr.prunetwork.gui.table.MessageTableModel;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.Date;
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
        JFrame frame = new JFrame();
        frame.setTitle("Application");
        frame.setPreferredSize(new Dimension(600, 400));
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(1, 3));

        {
            final MessageTableModel messageTableModel = new MessageTableModel();
            HistoryTable table = new HistoryTable(messageTableModel);

            table.setDefaultRenderer(Date.class, new DateCellRenderer());

            panel.add(new JScrollPane(table));


            final MyMessageConsumer consumer = new MyMessageConsumer(messageTableModel);
            AmqpReceiver receiver = new AmqpReceiver(
                    URI,
                    EXCHANGE,
                    ROUTING_KEYS,
                    consumer
            );
            receiver.configure();

            final Runnable runnable = new Runnable() {

                @Override
                public void run() {
                    System.out.println("*******");
                    while (!consumer.isConnected()) {
                        try {
                            Thread.sleep(100);
                        } catch (Exception e) {
                            // do nothing
                        }
                    }

                    System.out.println("*******");
                    while (true) {
                        try {
                            consumer.consume();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            };
            EXECUTOR.submit(runnable);
        }

        {
            final MessageTableModel messageTableModel = new MessageTableModel();
            HistoryTable table = new HistoryTable(messageTableModel);

            table.setDefaultRenderer(Date.class, new DateCellRenderer());

            panel.add(new JScrollPane(table));

            final MyMessageConsumer consumer = new MyMessageConsumer(messageTableModel);
            AmqpReceiver receiver = new AmqpReceiver(
                    URI,
                    EXCHANGE,
                    Arrays.asList("etage.#"),
                    consumer
            );
            receiver.configure();

            final Runnable runnable = new Runnable() {

                @Override
                public void run() {
                    System.out.println("*******");
                    while (consumer.isConnected() == false) {
                        try {
                            Thread.sleep(100);
                        } catch (Exception e) {
                            // do nothing
                        }
                    }

                    System.out.println("*******");
                    while (true) {
                        try {
                            consumer.consume();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            };
            EXECUTOR.submit(runnable);
        }
        {
            final MessageTableModel messageTableModel = new MessageTableModel();
            HistoryTable table = new HistoryTable(messageTableModel);

            table.setDefaultRenderer(Date.class, new DateCellRenderer());

            panel.add(new JScrollPane(table));


            final MyMessageConsumer consumer = new MyMessageConsumer(messageTableModel);
            AmqpReceiver receiver = new AmqpReceiver(
                    URI,
                    EXCHANGE,
                    Arrays.asList("#.rdc.#"),
                    consumer
            );
            receiver.configure();

            final Runnable runnable = new Runnable() {

                @Override
                public void run() {
                    System.out.println("*******");
                    while (!consumer.isConnected()) {
                        try {
                            Thread.sleep(100);
                        } catch (Exception e) {
                            // do nothing
                        }
                    }

                    System.out.println("*******");
                    while (true) {
                        try {
                            consumer.consume();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            };
            EXECUTOR.submit(runnable);
        }

        {
            panel.add(
                    new CommandPanel(
                            new CommandPanelActionner(
                                    new AmqpProducer(
                                            URI,
                                            EXCHANGE,
                                            ROUTING_KEY,
                                            ExchangeType.topic
                                    )
                            )
                    )
            );
        }

        frame.getContentPane().add(panel);
        frame.pack();
        frame.setVisible(true);


    }

    static class MyMessageConsumer extends SimpleMessageConsumer {
        private final MessageTableModel tableModel;

        public MyMessageConsumer(MessageTableModel tableModel) {
            this.tableModel = tableModel;
        }

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
