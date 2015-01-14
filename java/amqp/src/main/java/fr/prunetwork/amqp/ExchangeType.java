package fr.prunetwork.amqp;

/**
 * This enum defines all exchange types supported through AMQP.
 *
 * Others may exist but are currently not supported.
 * 
 * @author Jean-Pierre PRUNARET
 * @since 10/07/2014
 */
public enum ExchangeType {
    topic, fanout, direct;
}
