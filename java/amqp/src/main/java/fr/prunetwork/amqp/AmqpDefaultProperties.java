package fr.prunetwork.amqp;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;

/**
 * @author Jean-Pierre PRUNARET
 * @since 11/12/14
 */
public class AmqpDefaultProperties {

    @NotNull
    public static final String URI = "amqp://jpierre03:toto@172.16.201.201";
    @NotNull
    public static final String EXCHANGE = "dev.tmp";
    @NotNull
    public static final String ROUTING_KEY = "";
    @NotNull
    public static final List<String> ROUTING_KEYS = Arrays.asList("#");

    public AmqpDefaultProperties() {
    }
}
