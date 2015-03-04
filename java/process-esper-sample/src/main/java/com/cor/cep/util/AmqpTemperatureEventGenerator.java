package com.cor.cep.util;

import com.cor.cep.event.TemperatureEvent;
import com.cor.cep.handler.TemperatureEventHandler;
import fr.prunetwork.amqp.AmqpDefaultProperties;
import fr.prunetwork.amqp.AmqpReceivedMessage;
import fr.prunetwork.amqp.consumer.AmqpReceiver;
import fr.prunetwork.amqp.consumer.SimpleMessageConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Just a simple class to create a number of Random TemperatureEvents and pass them off to the
 * TemperatureEventHandler.
 */
@Component
public class AmqpTemperatureEventGenerator {

    /**
     * Logger
     */
    private static Logger LOG = LoggerFactory.getLogger(AmqpTemperatureEventGenerator.class);
    /**
     * The TemperatureEventHandler - wraps the Esper engine and processes the Events
     */
    @Autowired
    private TemperatureEventHandler temperatureEventHandler;

    /**
     * Creates simple random Temperature events and lets the implementation class handle them.
     */
    public void startSendingTemperatureReadings() {

        ExecutorService xrayExecutor = Executors.newSingleThreadExecutor();

        xrayExecutor.submit(() -> {

            LOG.debug(getStartingMessage());

            try {
                SimpleMessageConsumer consumer = new SimpleMessageConsumer();
                AmqpReceiver receiver = new AmqpReceiver(
                        AmqpDefaultProperties.URI,
                        AmqpDefaultProperties.EXCHANGE,
                        Arrays.asList("#"),
                        consumer
                );

                receiver.configure();


                System.out.println("*******");
                while (consumer.isConnected() == false) {
                    try {
                        Thread.sleep(100);
                    } catch (Exception e) {
                        // do nothing
                    }
                }
                System.out.println("*******");
                while (true) {
                    try {
                        final AmqpReceivedMessage message = consumer.consume();

                        TemperatureEvent ve = new TemperatureEvent(Integer.parseInt(message.getBody()), new Date());
                        temperatureEventHandler.handle(ve);
                    } catch (Exception e) {
                        LOG.error("AMQP Error", e);
                    }
                }
            } catch (Exception e) {
                LOG.error("AMQP Configuration fail", e);
            }
        });
    }

    private String getStartingMessage() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n\n************************************************************");
        sb.append("\n* STARTING - ");
        sb.append("\n* PLEASE WAIT - TEMPERATURES ARE RANDOM SO MAY TAKE");
        sb.append("\n* A WHILE TO SEE WARNING AND CRITICAL EVENTS!");
        sb.append("\n************************************************************\n");
        return sb.toString();
    }
}
