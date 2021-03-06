package fr.prunetwork.amqp;

import org.jetbrains.annotations.NotNull;

import java.io.PrintStream;
import java.io.Serializable;
import java.util.Date;

/**
 * A generic message exchanged with AMQP.
 *
 * @author Jean-Pierre PRUNARET
 * @since 2014-08-28
 */
public interface AmqpReceivedMessage extends Serializable {

    /**
     * @return the message content
     */
    @NotNull
    String getBody();

    /**
     * @return the key used to route message.
     */
    @NotNull
    String getRoutingKey();

    /**
     * Print message with default formatting
     * @param os where to print. Maybe "System.out".
     */
    void printMessage(@NotNull final PrintStream os);

    /**
     * @return a date object created at reception time.
     */
    @NotNull
    Date getReceivedDate();
}
