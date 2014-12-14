package fr.prunetwork.amqp.consumer;

import com.rabbitmq.client.QueueingConsumer;
import fr.prunetwork.amqp.message.SimpleMessage;

import static fr.prunetwork.amqp.AmqpDefaultProperties.*;

/**
 * @author Jean-Pierre PRUNARET
 * @since 01/07/2014
 */
class AmqpReceiverTest {

    public AmqpReceiverTest() {
    }

    public static void main(String... argv) throws Exception {

        MyMessageConsumer consumer = new MyMessageConsumer();
        AmqpReceiver receiver = new AmqpReceiver(URI, EXCHANGE, ROUTING_KEYS, consumer);
        receiver.configure();

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
            consumer.consume();
        }
    }

    static class MyMessageConsumer implements MessageConsumer<SimpleMessage> {
        private QueueingConsumer consumer;

        @Override
        public SimpleMessage consume() throws InterruptedException {
            final QueueingConsumer.Delivery delivery = consumer.nextDelivery();
            final String message = new String(delivery.getBody());
            final String routingKey = delivery.getEnvelope().getRoutingKey();

            final SimpleMessage receivedMessage = new SimpleMessage(routingKey, message);

            receivedMessage.displayFullMessage();
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
