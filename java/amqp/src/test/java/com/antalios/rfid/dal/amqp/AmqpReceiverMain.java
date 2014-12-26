package com.antalios.rfid.dal.amqp;

import fr.prunetwork.amqp.AmqpReceiver;
import fr.prunetwork.amqp.ExchangeType;
import fr.prunetwork.amqp.receiver.SimpleAmqpReceiver;

import static fr.prunetwork.amqp.AmqpDefaultProperties.*;

public class AmqpReceiverMain {

    public static void main(String... argv) throws Exception {

        AmqpReceiver receiver = new SimpleAmqpReceiver(URI, EXCHANGE, ROUTING_KEYS, ExchangeType.topic);
        receiver.configure();

        while (true) {
            receiver.consume();
        }
    }
}
