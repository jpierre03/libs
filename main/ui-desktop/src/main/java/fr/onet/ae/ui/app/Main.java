package fr.onet.ae.ui.app;

import com.rabbitmq.client.QueueingConsumer;
import fr.onet.ae.amqp.consumer.AmqpReceivedMessage;
import fr.onet.ae.amqp.consumer.AmqpReceivedMessageImpl;
import fr.onet.ae.amqp.consumer.AmqpReceiver;
import fr.onet.ae.amqp.consumer.MessageConsumer;
import fr.onet.ae.ui.component.table.DateCellRenderer;
import fr.onet.ae.ui.component.table.HistoryTable;
import fr.onet.ae.ui.component.table.MessageTableModel;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static fr.onet.ae.common.Configuration.AMQP_DEFAULT_EXCHANGE;
import static fr.onet.ae.common.Configuration.AMQP_DEFAULT_URL;

/**
 * @author Jean-Pierre PRUNARET
 * @since 01/07/2014
 */
public class Main {

    private static final ExecutorService EXECUTOR = Executors.newFixedThreadPool(2);

    public static void main(String... args) throws Exception {
        JFrame frame = new JFrame();
        frame.setTitle("Application");
        frame.setPreferredSize(new Dimension(600, 400));
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(1, 2));

        {
            final MessageTableModel messageTableModel = new MessageTableModel();
            HistoryTable table = new HistoryTable(messageTableModel);

            table.setDefaultRenderer(Date.class, new DateCellRenderer());

            panel.add(new JScrollPane(table));


            final MyMessageConsumer consumer = new MyMessageConsumer(messageTableModel);
            AmqpReceiver receiver = new AmqpReceiver(AMQP_DEFAULT_URL, AMQP_DEFAULT_EXCHANGE, Arrays.asList("#"), consumer);
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
            AmqpReceiver receiver = new AmqpReceiver(AMQP_DEFAULT_URL, AMQP_DEFAULT_EXCHANGE, Arrays.asList("#"), consumer);
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

        frame.getContentPane().add(panel);
        frame.pack();
        frame.setVisible(true);


    }

    static class MyMessageConsumer implements MessageConsumer {
        private final MessageTableModel tableModel;
        private QueueingConsumer consumer;

        public MyMessageConsumer(MessageTableModel tableModel) {
            this.tableModel = tableModel;
        }

        @Override
        public AmqpReceivedMessage consume() throws InterruptedException {
            final QueueingConsumer.Delivery delivery = consumer.nextDelivery();
            final String message = new String(delivery.getBody());
            final String routingKey = delivery.getEnvelope().getRoutingKey();

            final AmqpReceivedMessageImpl receivedMessage = new AmqpReceivedMessageImpl(routingKey, message);

            //receivedMessage.displayFullMessage();
            tableModel.add(receivedMessage);
            tableModel.fireTableRowsInserted(tableModel.getRowCount() - 1, tableModel.getRowCount() - 1);

            return receivedMessage;
        }

        @Override
        public void init(QueueingConsumer consumer) {
            this.consumer = consumer;
        }

        @Override
        public boolean isConnected() {
            return consumer != null;
        }
    }
}
