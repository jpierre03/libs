package com.cor.cep;

import fr.prunetwork.amqp.AmqpDefaultProperties;
import fr.prunetwork.amqp.ExchangeType;
import fr.prunetwork.amqp.producer.AmqpProducer;

import java.util.Random;

/**
 * Entry point for the Demo. Run this from your IDE, or from the command line using 'mvn exec:java'.
 */
public class ProduceTemperatureAmqp {

    public static void main(String[] args) throws Exception {
        AmqpProducer producer = new AmqpProducer(
                AmqpDefaultProperties.URI,
                AmqpDefaultProperties.EXCHANGE,
                "#",
                ExchangeType.topic,
                false
        );

        while (true) {
            producer.publish(new Random().nextInt(500) + "");
            //Thread.sleep(1);
        }
    }
}
