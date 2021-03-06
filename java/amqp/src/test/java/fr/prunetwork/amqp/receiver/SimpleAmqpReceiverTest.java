package fr.prunetwork.amqp.receiver;

import fr.prunetwork.amqp.AmqpReceiver;
import fr.prunetwork.amqp.ExchangeType;
import fr.prunetwork.amqp.message.SimpleMessage;
import fr.prunetwork.amqp.producer.AmqpProducer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static fr.prunetwork.amqp.AmqpDefaultProperties.*;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class SimpleAmqpReceiverTest {
    AmqpProducer producer;
    AmqpReceiver<SimpleMessage> receiver;

    @Before
    public void setUp() throws Exception {
        final String localExchangeSuffix = ".sdfghjkl";
        producer = new AmqpProducer(URI, EXCHANGE + localExchangeSuffix, ROUTING_KEY, ExchangeType.topic, false);
        receiver = new SimpleAmqpReceiver(URI, EXCHANGE + localExchangeSuffix, ROUTING_KEYS, ExchangeType.topic, false);
        receiver.configure();
    }

    @After
    public void tearDown() throws Exception {
        receiver.close();
        receiver = null;

        producer.close();
        producer = null;
    }

    @Test
    public void shouldConnect() {
        assertTrue(receiver != null);
        assertTrue(producer != null);
    }

    @Test(timeout = 500)
    public void shouldReceiveOne() {
        final String s = "a nice message";
        try {
            producer.publish(s);
        } catch (IOException e) {
        }
        try {
            final SimpleMessage message = receiver.consume();
            assertTrue(message.getBody().contentEquals(s));
        } catch (Exception e) {
            fail(e.getLocalizedMessage());
        }
    }

    @Test(timeout = 2500)
    public void shouldReceiveThousands() {
        final String s = "a nice message";
        try {
            for (int i = 0; i < 1000; i++) {
                producer.publish(s);
            }
        } catch (IOException e) {
        }

        for (int i = 0; i < 1000; i++) {
            try {

                final SimpleMessage message = receiver.consume();
                assertTrue(message.getBody().contentEquals(s));

            } catch (Exception e) {
                fail(e.getLocalizedMessage());
            }
        }
    }

}
