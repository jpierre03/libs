package com.cor.cep.event;


import com.cor.cep.util.Event;
import org.jetbrains.annotations.NotNull;

import java.util.Date;

/**
 * Immutable Temperature Event class. The process control system creates these events. The
 * TemperatureEventHandler picks these up and processes them.
 */
public class TemperatureEvent implements Event {

    /**
     * Temperature in Celcius.
     */
    final private int temperature;
    /**
     * Time temperature reading was taken.
     */
    @NotNull
    final private Date timeOfReading;

    /**
     * Temperature constructor.
     *
     * @param temperature   Temperature in Celsius
     * @param timeOfReading Time of Reading
     */
    public TemperatureEvent(int temperature, @NotNull final Date timeOfReading) {
        this.temperature = temperature;
        this.timeOfReading = timeOfReading;
    }

    @Override
    @NotNull
    public String getDescription() {
        return "This event represent a real temperature";
    }

    @Override
    @NotNull
    public Date getCreationDate() {
        return timeOfReading;
    }

    /**
     * Get the Temperature.
     *
     * @return Temperature in Celsius
     */
    public int getTemperature() {
        return temperature;
    }

    /**
     * Get time Temperature reading was taken.
     *
     * @return Time of Reading
     */
    @NotNull
    public Date getTimeOfReading() {
        return timeOfReading;
    }

    @Override
    @NotNull
    public String toString() {
        return "TemperatureEvent [" + temperature + "C]";
    }
}
