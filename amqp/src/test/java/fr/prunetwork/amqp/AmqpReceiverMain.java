package com.antalios.rfid.dal.amqp;

import com.antalios.rfid.conf.Properties;
import com.antalios.rfid.dal.amqp.receiver.ElaAmqpReceiver;

import java.util.Arrays;

public class AmqpReceiverMain {

    public static void main(String... argv) throws Exception {


        AmqpReceiver receiver = new ElaAmqpReceiver(Properties.AMQP_TEST_URL, Properties.AMQP_TEST_EXCHANGE, Arrays.asList("#"));
        receiver.configure();

        while (true) {
            receiver.consume();
        }
    }
}
