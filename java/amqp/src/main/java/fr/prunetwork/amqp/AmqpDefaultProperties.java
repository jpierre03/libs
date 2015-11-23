package fr.prunetwork.amqp;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;

/**
 * Provide default properties used by AMQP module.
 *
 * @author Jean-Pierre PRUNARET
 * @since 2014-12-11
 */
public class AmqpDefaultProperties {

    @NotNull
    public static final String URI = "amqp://jpierre03:toto@localhost";
    @NotNull
    public static final String EXCHANGE = "dev.tmp";
    @NotNull
    public static final String ROUTING_KEY = "";
    @NotNull
    public static final List<String> ROUTING_KEYS = Arrays.asList("#");
    @NotNull
    public  static final AmqpConfiguration AMQP_CONFIGURATION = new AmqpConfiguration(URI, EXCHANGE, ROUTING_KEYS, ExchangeType.topic, false);

    public AmqpDefaultProperties() {
    }
}
