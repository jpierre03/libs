package fr.onet.ae.amqp.consumer;

import com.rabbitmq.client.QueueingConsumer;

import java.util.Arrays;

import static fr.onet.ae.common.Configuration.AMQP_DEFAULT_EXCHANGE;
import static fr.onet.ae.common.Configuration.AMQP_DEFAULT_URL;

/**
 * @author Jean-Pierre PRUNARET
 * @since 01/07/2014
 */
class AmqpReceiverTest {

    public AmqpReceiverTest() {
    }

    public static void main(String... argv) throws Exception {

        MyMessageConsumer consumer = new MyMessageConsumer();
        AmqpReceiver receiver = new AmqpReceiver(AMQP_DEFAULT_URL, AMQP_DEFAULT_EXCHANGE, Arrays.asList("#"), consumer);
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

    static class MyMessageConsumer implements MessageConsumer {
        private QueueingConsumer consumer;

        @Override
        public AmqpReceivedMessage consume() throws InterruptedException {
            final QueueingConsumer.Delivery delivery = consumer.nextDelivery();
            final String message = new String(delivery.getBody());
            final String routingKey = delivery.getEnvelope().getRoutingKey();

            final AmqpReceivedMessageImpl receivedMessage = new AmqpReceivedMessageImpl(routingKey, message);

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
