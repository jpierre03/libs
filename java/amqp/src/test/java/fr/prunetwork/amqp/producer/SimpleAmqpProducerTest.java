package fr.prunetwork.amqp.producer;

import fr.prunetwork.amqp.ExchangeType;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static fr.prunetwork.amqp.AmqpDefaultProperties.*;
import static java.util.Objects.nonNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class SimpleAmqpProducerTest {
    AmqpProducer producer;

    @Before
    public void setUp() throws Exception {
        producer = new AmqpProducer(URI, EXCHANGE, ROUTING_KEY, ExchangeType.topic, false);
    }

    @After
    public void tearDown() throws Exception {
        if (nonNull(producer)) {
            producer.close();
            producer = null;
        }
    }

    @Test
    public void shouldConnect() {
        assertTrue(producer != null);
    }

    @Test
    public void shouldSendOne() {
        try {
            producer.publish("Test");
        } catch (IOException e) {
            fail("Exception");
        }
    }

    @Test
    public void shouldSendThousand() {
        try {
            for (int i = 0; i < 1000; i++) {
                producer.publish("Test " + i);
            }
        } catch (IOException e) {
            fail("Exception");
        }
    }
}
