package fr.prunetwork.amqp;

import java.util.Arrays;
import java.util.List;

/**
 * @author Jean-Pierre PRUNARET
 * @since 11/12/14
 */
public class AmqpDefaultProperties {

    public static final String URI = "amqp://localhost";
    public static final String EXCHANGE = "dev.tmp";
    public static final String ROUTING_KEY = "";
    public static final List<String> ROUTING_KEYS = Arrays.asList("#");

    public AmqpDefaultProperties() {
    }
}
