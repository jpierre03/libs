package fr.prunetwork;

import fr.prunetwork.amqp.AmqpConfiguration;
import fr.prunetwork.amqp.ExchangeType;
import fr.prunetwork.mail.AmqpToMailForwarderService;

import static fr.prunetwork.amqp.AmqpDefaultProperties.*;

/**
 * @author Jean-Pierre PRUNARET
 * @since 13/12/14
 */
public class ForwardAmqpToMailTest {

    public static void main(String... argv) throws Exception {

        final AmqpConfiguration amqpConfiguration = new AmqpConfiguration(URI, EXCHANGE, ROUTING_KEYS, ExchangeType.topic, false);
        final AmqpToMailForwarderService service = new AmqpToMailForwarderService(amqpConfiguration);

        service.start();

        Thread.sleep(1 * 1000);

        service.stop();
    }
}
