package app;

import amqp.consumer.AmqpReceivedMessageImpl;
import amqp.consumer.AmqpReceiver;
import amqp.consumer.MessageConsumer;
import com.rabbitmq.client.QueueingConsumer;
import gui.table.MessageTableModel;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableModel;
import java.awt.*;
import java.text.SimpleDateFormat;
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

        table.setDefaultRenderer(Date.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                final Date date = (Date) value;
                final Date now = new Date();

                if (now.getTime() - date.getTime() > 60 * 1000) {
                    setForeground(Color.black);
                    setBackground(Color.lightGray);
                } else {
                    setForeground(Color.black);
                    setBackground(Color.white);
                }

                SimpleDateFormat simpleFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                setValue(simpleFormat.format(date));
                return this;
            }
        });

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
        public void consume() throws InterruptedException {
            final QueueingConsumer.Delivery delivery = consumer.nextDelivery();
            final String message = new String(delivery.getBody());
            final String routingKey = delivery.getEnvelope().getRoutingKey();

            final AmqpReceivedMessageImpl receivedMessage = new AmqpReceivedMessageImpl(routingKey, message);

            receivedMessage.displayFullMessage();
            tableModel.add(receivedMessage);
            tableModel.fireTableRowsInserted(tableModel.getRowCount() - 1, tableModel.getRowCount() - 1);
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
