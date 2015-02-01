package fr.prunetwork.amqp;

import fr.prunetwork.amqp.receiver.SimpleAmqpReceiver;
import org.jetbrains.annotations.NotNull;

import static fr.prunetwork.amqp.AmqpDefaultProperties.*;

public class AmqpReceiverMain {

    public static void main(String... argv) throws Exception {

        @NotNull AmqpReceiver receiver = new SimpleAmqpReceiver(URI, EXCHANGE, ROUTING_KEYS, ExchangeType.topic, false);
        receiver.configure();

        while (true) {
            receiver.consume();
        }
    }
}
