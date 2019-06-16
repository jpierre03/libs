package fr.prunetwork.amqp.gui;

/**
 * @author Jean-Pierre PRUNARET
 * @since 2016-11-22
 */
public interface CommandPanelAction {

    void sendHelloWorld();

    void sendAddRunningTimeMinute(int duration);

    void sendRandomTemperature();
}
