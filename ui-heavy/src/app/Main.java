package app;

import amqp.consumer.AmqpReceivedMessage;
import amqp.consumer.AmqpReceivedMessageImpl;
import amqp.consumer.AmqpReceiver;
import amqp.consumer.MessageConsumer;
import com.rabbitmq.client.QueueingConsumer;
import gui.table.DateCellRenderer;
import gui.table.MessageTableModel;

import javax.swing.*;
import javax.swing.table.TableModel;
import java.awt.*;
import java.util.Arrays;
import java.util.Date;

/**
 * @author Jean-Pierre PRUNARET
 * @since 01/07/2014
 */
public class Main {

    public static void main(String... args) throws Exception {
        JFrame frame = new JFrame();
        frame.setTitle("Application");
        frame.setPreferredSize(new Dimension(600, 400));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        final MessageTableModel messageTableModel = new MessageTableModel();

        TableModel model = messageTableModel;
        JTable table = new JTable(model);

        table.setDefaultRenderer(Date.class, new DateCellRenderer());

        table.setAutoCreateRowSorter(true);
        frame.getContentPane().add(new JScrollPane(table));
        frame.pack();


        MyMessageConsumer consumer = new MyMessageConsumer(messageTableModel);
        AmqpReceiver receiver = new AmqpReceiver("amqp://jpierre03:toto@localhost", "dev.tmp", Arrays.asList("#"), consumer);
        receiver.configure();

        System.out.println("*******");
        while (consumer.isConnected() == false) {
            try {
                Thread.sleep(100);
            } catch (Exception e) {
                // do nothing
            }
        }

        frame.setVisible(true);

        System.out.println("*******");
        while (true) {
            consumer.consume();
        }
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
