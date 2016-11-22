package fr.prunetwork.amqp.gui;

/**
 * @author Jean-Pierre PRUNARET
 * @since 2016-11-22
 */
public interface CommandPanelAction {

    void sendHelloWorld() throws Exception;

    void sendAddRunningTimeMinute(int duration) throws Exception;

    void sendRandomTemperature() throws Exception;
}
