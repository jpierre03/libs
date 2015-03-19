package fr.prunetwork.mail;

import fr.prunetwork.amqp.AmqpConfiguration;
import fr.prunetwork.amqp.ExchangeType;
import org.jetbrains.annotations.NotNull;

import static fr.prunetwork.amqp.AmqpDefaultProperties.*;

/**
 * @author Jean-Pierre PRUNARET
 * @since 13/12/14
 */
public final class MailDefaultProperties {

    @NotNull
    public final static String MAILER_VERSION = "Prunetwork-libs-mail-1.0-SNAPSHOT";
    @NotNull
    public static final AmqpConfiguration DEFAULT_AMQP_CONFIGURATION = new AmqpConfiguration(URI, EXCHANGE, ROUTING_KEYS, ExchangeType.topic, false);

    private MailDefaultProperties() {
    }
}
