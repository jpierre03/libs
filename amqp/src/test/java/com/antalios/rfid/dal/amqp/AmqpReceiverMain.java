package com.antalios.rfid.dal.amqp;

import fr.prunetwork.amqp.AmqpReceiver;
import fr.prunetwork.amqp.receiver.SimpleAmqpReceiver;

import java.util.Arrays;

public class AmqpReceiverMain {

    static final String AMQP_TEST_URL = "amqp://localhost";
    static final String AMQP_TEST_EXCHANGE = "test";

    public static void main(String... argv) throws Exception {

        AmqpReceiver receiver = new SimpleAmqpReceiver(AMQP_TEST_URL, AMQP_TEST_EXCHANGE, Arrays.asList("#"));
        receiver.configure();

        while (true) {
            receiver.consume();
        }
    }
}
